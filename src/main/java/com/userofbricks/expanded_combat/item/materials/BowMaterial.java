package com.userofbricks.expanded_combat.item.materials;

import com.google.common.base.Preconditions;
import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.util.entry.RegistryEntry;
import com.userofbricks.expanded_combat.ExpandedCombat;
import com.userofbricks.expanded_combat.config.ECConfig;
import com.userofbricks.expanded_combat.item.ECBowItem;
import com.userofbricks.expanded_combat.item.ECCrossBowItem;
import com.userofbricks.expanded_combat.item.ECItemTags;
import com.userofbricks.expanded_combat.item.ECItems;
import com.userofbricks.expanded_combat.item.recipes.ECConfigBooleanCondition;
import com.userofbricks.expanded_combat.item.recipes.ECConfigBowRecipeTypeCondition;
import com.userofbricks.expanded_combat.util.IngredientUtil;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.ConditionalAdvancement;
import net.minecraftforge.common.crafting.ConditionalRecipe;
import net.minecraftforge.common.crafting.conditions.NotCondition;
import net.minecraftforge.common.crafting.conditions.OrCondition;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Locale;

import static com.userofbricks.expanded_combat.ExpandedCombat.MODID;

public class BowMaterial {
    @NotNull
    private final String name;

    private final boolean halfBow;
    public final boolean smithingOnly;
    @NotNull
    private final ECConfig.BowMaterialConfig bowMaterialConfig;

    private final BowMaterial crafted_from;
    private RegistryEntry<ECBowItem> bowEntry;
    @Nullable
    private RegistryEntry<ECCrossBowItem> crossbowEntry;

    public BowMaterial(@NotNull String name, boolean halfBow, boolean smithingOnly, @NotNull ECConfig.BowMaterialConfig bowMaterialConfig, BowMaterial crafted_from, List<BowMaterial> bowMaterials) {
        this.name = name;
        this.halfBow = halfBow;
        this.bowMaterialConfig = bowMaterialConfig;
        this.crafted_from = crafted_from;
        this.smithingOnly = smithingOnly;
        bowMaterials.add(this);
    }

    public final void registerElements() {
        String recourseName = this.name.toLowerCase(Locale.ROOT).replace(' ', '_');

        ItemBuilder<ECBowItem, Registrate> itemBuilder = ExpandedCombat.REGISTRATE.get().item(recourseName + "_bow", (p) -> new ECBowItem(this, p));
        itemBuilder.model((ctx, prov) -> prov.generated(ctx, new ResourceLocation(MODID, "item/bow/" + recourseName))
                .transforms()
                    .transform(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND).rotation(-80, 260, -40).translation(-1, -2, 2.5f).scale(0.9f).end()
                    .transform(ItemDisplayContext.THIRD_PERSON_LEFT_HAND).rotation(-80, -280, 40).translation(-1, -2, 2.5f).scale(0.9f).end()
                    .transform(ItemDisplayContext.FIRST_PERSON_RIGHT_HAND).rotation(0, -90, 25).translation(1.13f, 3.2f, 1.13f).scale(0.68f).end()
                    .transform(ItemDisplayContext.FIRST_PERSON_LEFT_HAND).rotation(0, 90, -25).translation(1.13f, 3.2f, 1.13f).scale(0.68f).end()
                .end()
                .override().predicate(new ResourceLocation("pulling"), 1).model(
                        prov.withExistingParent(ctx.getName()+"_pulling_0", new ResourceLocation("item/bow")).texture("layer0", new ResourceLocation(MODID, "item/bow/" + recourseName + "_pulling_0"))
                ).end()
                .override().predicate(new ResourceLocation("pulling"), 1).predicate(new ResourceLocation("pull"), 0.65f).model(
                        prov.withExistingParent(ctx.getName()+"_pulling_1", new ResourceLocation("item/bow")).texture("layer0", new ResourceLocation(MODID, "item/bow/" + recourseName + "_pulling_1"))
                ).end()
                .override().predicate(new ResourceLocation("pulling"), 1).predicate(new ResourceLocation("pull"), 0.9f).model(
                        prov.withExistingParent(ctx.getName()+"_pulling_2", new ResourceLocation("item/bow")).texture("layer0", new ResourceLocation(MODID, "item/bow/" + recourseName + "_pulling_2"))
                ).end());
        itemBuilder.tag(ECItemTags.BOWS);
        itemBuilder.recipe((ctx, prov) -> {
            //used for advancement trigger and recipe input item
            Ingredient ingredient = IngredientUtil.getIngrediantFromItemString(this.bowMaterialConfig.repairItem);
            //only register recipe if ingredient isn't empty
            if (!ingredient.isEmpty()) {
                //here only because it is needed in both the conditional and standard advancements
                InventoryChangeTrigger.TriggerInstance triggerInstance = InventoryChangeTrigger.TriggerInstance.hasItems(IngredientUtil.toItemLikeArray(ingredient));

                Advancement.Builder advancement = Advancement.Builder.advancement()
                        .addCriterion("has_item", triggerInstance)
                        .parent(RecipeBuilder.ROOT_RECIPE_ADVANCEMENT)
                        .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(ctx.getId()))
                        .rewards(AdvancementRewards.Builder.recipe(ctx.getId()))
                        .requirements(RequirementsStrategy.OR);

                ECConfigBooleanCondition enableHalfBows = new ECConfigBooleanCondition("half_bow");
                ECConfigBowRecipeTypeCondition smithingOnly = new ECConfigBowRecipeTypeCondition(ECConfig.BowRecipeType.SMITHING_ONLY);
                ECConfigBowRecipeTypeCondition craftingOnly = new ECConfigBowRecipeTypeCondition(ECConfig.BowRecipeType.CRAFTING_TABLE_ONLY);
                ECConfigBowRecipeTypeCondition craftingAndSmithing = new ECConfigBowRecipeTypeCondition(ECConfig.BowRecipeType.CRAFTING_TABLE_AND_SMITHING);
                OrCondition smithing_or_both = new OrCondition(smithingOnly, craftingAndSmithing);
                OrCondition crafting_or_both = new OrCondition(craftingOnly, craftingAndSmithing);

                ConditionalRecipe.Builder conditionalRecipe = ConditionalRecipe.builder().setAdvancement(ctx.getId().getNamespace(),
                        "recipes/" + RecipeCategory.COMBAT.getFolderName() + "/" + ctx.getId().getPath(),
                        ConditionalAdvancement.builder().addCondition(new ECConfigBooleanCondition("bow")).addAdvancement(advancement));
                ConditionalRecipe.Builder conditionalRecipe1 = ConditionalRecipe.builder().setAdvancement(ctx.getId().getNamespace(),
                        "recipes/" + RecipeCategory.COMBAT.getFolderName() + "/" + ctx.getId().getPath() + "_120",
                        ConditionalAdvancement.builder().addCondition(new ECConfigBooleanCondition("bow")).addAdvancement(advancement));
                ConditionalRecipe.Builder conditionalShapedRecipe = ConditionalRecipe.builder().setAdvancement(ctx.getId().getNamespace(),
                        "recipes/" + RecipeCategory.COMBAT.getFolderName() + "/" + ctx.getId().getPath() + "_half",
                        ConditionalAdvancement.builder().addCondition(new ECConfigBooleanCondition("bow")).addAdvancement(advancement));
                ConditionalRecipe.Builder conditionalShapedRecipe1 = ConditionalRecipe.builder().setAdvancement(ctx.getId().getNamespace(),
                        "recipes/" + RecipeCategory.COMBAT.getFolderName() + "/" + ctx.getId().getPath() + "_half_skip",
                        ConditionalAdvancement.builder().addCondition(new ECConfigBooleanCondition("bow")).addAdvancement(advancement));

                if(!this.smithingOnly) {
                    conditionalShapedRecipe.addCondition(enableHalfBows);
                    shapedRecipe(ctx,
                            halfBow ? new String[]{"b", "i"} : new String[]{"i", "b"}, ingredient,
                            crafted_from == null ? Ingredient.of(Items.BOW) : Ingredient.of(this.crafted_from.bowEntry.get()),
                            conditionalShapedRecipe,
                            crafting_or_both,
                            triggerInstance, "");
                    if (!halfBow)
                        shapedRecipe(ctx,
                                new String[]{"i", "b", "i"}, ingredient,
                                crafted_from == null || crafted_from.crafted_from == null ? Ingredient.of(Items.BOW) : Ingredient.of(this.crafted_from.crafted_from.bowEntry.get()),
                                conditionalShapedRecipe1,
                                crafting_or_both,
                                triggerInstance, "skip");
                }

                //1.19.4 and prior version
                if (!this.smithingOnly) conditionalRecipe.addCondition(enableHalfBows);
                legacySmithingRecipe(ctx,
                        ingredient,
                        //if doesn't have predefined upgrade path then start one
                        crafted_from == null ? Ingredient.of(Items.BOW) : Ingredient.of(crafted_from.bowEntry.get()),
                        conditionalRecipe,
                        smithing_or_both,
                        triggerInstance);
                if (!halfBow && !this.smithingOnly) {
                    conditionalRecipe.addCondition(new NotCondition(enableHalfBows));
                    legacySmithingRecipe(ctx,
                            ingredient,
                            //if doesn't have predefined upgrade path then start one
                            crafted_from == null || crafted_from.crafted_from == null ? Ingredient.of(Items.BOW) : Ingredient.of(crafted_from.crafted_from.bowEntry.get()),
                            conditionalRecipe,
                            smithing_or_both,
                            triggerInstance);
                }

                //1.20 version
                if (!this.smithingOnly) conditionalRecipe1.addCondition(enableHalfBows);
                smithing120Recipe(ctx,
                        Ingredient.of(ForgeRegistries.ITEMS.getValue(new ResourceLocation(this.bowMaterialConfig.smithingTemplate))),
                        ingredient,
                        //if doesn't have predefined upgrade path then start one
                        crafted_from == null ? Ingredient.of(Items.BOW) : Ingredient.of(crafted_from.bowEntry.get()),
                        conditionalRecipe1,
                        smithing_or_both,
                        triggerInstance);
                if (!halfBow && !this.smithingOnly) {
                    conditionalRecipe1.addCondition(new NotCondition(enableHalfBows));
                    smithing120Recipe(ctx,
                            Ingredient.of(ForgeRegistries.ITEMS.getValue(new ResourceLocation(this.bowMaterialConfig.smithingTemplate))),
                            ingredient,
                            //if doesn't have predefined upgrade path then start one
                            crafted_from == null || crafted_from.crafted_from == null ? Ingredient.of(Items.BOW) : Ingredient.of(crafted_from.crafted_from.bowEntry.get()),
                            conditionalRecipe1,
                            smithing_or_both,
                            triggerInstance);
                }

                conditionalRecipe.build(prov, ctx.getId());
                conditionalRecipe1.build(prov, ctx.getId().withSuffix("_120"));
                if(!this.smithingOnly) {
                    conditionalShapedRecipe.build(prov, ctx.getId().withSuffix("_half"));
                    if (!this.halfBow) conditionalShapedRecipe1.build(prov, ctx.getId().withSuffix("_half_skip"));
                }
            }
        });
        this.bowEntry = itemBuilder.register();
        ECItems.ITEMS.add(this.bowEntry);

        if (!halfBow) {
            //register item
            ItemBuilder<ECCrossBowItem, Registrate> itemBuilder2 = ExpandedCombat.REGISTRATE.get().item(recourseName + "_crossbow", (p) -> new ECCrossBowItem(this, p));
            itemBuilder2.model((ctx, prov) -> prov.generated(ctx, new ResourceLocation(MODID, "item/crossbow/" + recourseName))
                    .transforms()
                    .transform(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND).rotation(-90, 0, -60).translation(2, 0.1f, -3f).scale(0.9f).end()
                    .transform(ItemDisplayContext.THIRD_PERSON_LEFT_HAND).rotation(-90, 0, 30).translation(2, 0.1f, -3f).scale(0.9f).end()
                    .transform(ItemDisplayContext.FIRST_PERSON_RIGHT_HAND).rotation(-90, 0, -55).translation(1.13f, 3.2f, 1.13f).scale(0.68f).end()
                    .transform(ItemDisplayContext.FIRST_PERSON_LEFT_HAND).rotation(-90, 0, 35).translation(1.13f, 3.2f, 1.13f).scale(0.68f).end()
                    .end()
                    .override().predicate(new ResourceLocation("pulling"), 1).model(
                            prov.withExistingParent(ctx.getName() + "_pulling_0", new ResourceLocation("item/crossbow")).texture("layer0", new ResourceLocation(MODID, "item/crossbow/" + recourseName + "_pulling_0"))
                    ).end()
                    .override().predicate(new ResourceLocation("pulling"), 1).predicate(new ResourceLocation("pull"), 0.58f).model(
                            prov.withExistingParent(ctx.getName() + "_pulling_1", new ResourceLocation("item/crossbow")).texture("layer0", new ResourceLocation(MODID, "item/crossbow/" + recourseName + "_pulling_1"))
                    ).end()
                    .override().predicate(new ResourceLocation("pulling"), 1).predicate(new ResourceLocation("pull"), 1f).model(
                            prov.withExistingParent(ctx.getName() + "_pulling_2", new ResourceLocation("item/crossbow")).texture("layer0", new ResourceLocation(MODID, "item/crossbow/" + recourseName + "_pulling_2"))
                    ).end()
                    .override().predicate(new ResourceLocation("charged"), 1).model(
                            prov.withExistingParent(ctx.getName() + "_arrow", new ResourceLocation("item/crossbow")).texture("layer0", new ResourceLocation(MODID, "item/crossbow/" + recourseName + "_arrow"))
                    ).end()
                    .override().predicate(new ResourceLocation("charged"), 1).predicate(new ResourceLocation("firework"), 1).model(
                            prov.withExistingParent(ctx.getName() + "_firework", new ResourceLocation("item/crossbow")).texture("layer0", new ResourceLocation(MODID, "item/crossbow/" + recourseName + "_firework"))
                    ).end()
            );
            itemBuilder2.tag(ECItemTags.CROSSBOWS);
            itemBuilder2.recipe((ctx, prov) -> {
                //used for advancement trigger and recipe input item
                Ingredient ingredient = IngredientUtil.getIngrediantFromItemString(this.bowMaterialConfig.repairItem);
                //only register recipe if ingredient isn't empty
                if (!ingredient.isEmpty()) {
                    //here only because it is needed in both the conditional and standard advancements
                    InventoryChangeTrigger.TriggerInstance triggerInstance = InventoryChangeTrigger.TriggerInstance.hasItems(IngredientUtil.toItemLikeArray(ingredient));

                    Advancement.Builder advancement = Advancement.Builder.advancement()
                            .addCriterion("has_item", triggerInstance)
                            .parent(RecipeBuilder.ROOT_RECIPE_ADVANCEMENT)
                            .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(ctx.getId()))
                            .rewards(AdvancementRewards.Builder.recipe(ctx.getId()))
                            .requirements(RequirementsStrategy.OR);

                    ECConfigBooleanCondition enableCrossBows = new ECConfigBooleanCondition("crossbow");

                    ConditionalRecipe.Builder shappedorLegacy = ConditionalRecipe.builder().setAdvancement(ctx.getId().getNamespace(),
                            "recipes/" + RecipeCategory.COMBAT.getFolderName() + "/" + ctx.getId().getPath(),
                            ConditionalAdvancement.builder().addCondition(enableCrossBows).addAdvancement(advancement));
                    ConditionalRecipe.Builder smithing120 = ConditionalRecipe.builder().setAdvancement(ctx.getId().getNamespace(),
                            "recipes/" + RecipeCategory.COMBAT.getFolderName() + "/" + ctx.getId().getPath(),
                            ConditionalAdvancement.builder().addCondition(enableCrossBows).addAdvancement(advancement));

                    if (!this.smithingOnly) {
                        shapedRecipe(ctx,
                                new String[]{"ibi", " i "}, ingredient,
                                crafted_from == null || crafted_from.crafted_from == null ? Ingredient.of(Items.BOW) : Ingredient.of(this.crafted_from.crafted_from.bowEntry.get()),
                                shappedorLegacy,
                                null,
                                triggerInstance, "");
                    } else {
                        //1.19.4 and prior version
                        legacySmithingRecipe(ctx,
                                ingredient,
                                //if doesn't have predefined upgrade path then start one
                                crafted_from == null ? Ingredient.of(Items.BOW) : Ingredient.of(crafted_from.bowEntry.get()),
                                shappedorLegacy,
                                null,
                                triggerInstance);

                        //1.20 version
                        smithing120Recipe(ctx,
                                Ingredient.of(ForgeRegistries.ITEMS.getValue(new ResourceLocation(this.bowMaterialConfig.smithingTemplate))),
                                ingredient,
                                //if doesn't have predefined upgrade path then start one
                                crafted_from == null ? Ingredient.of(Items.BOW) : Ingredient.of(crafted_from.bowEntry.get()),
                                smithing120,
                                null,
                                triggerInstance);
                    }

                    shappedorLegacy.build(prov, ctx.getId());
                    if (this.smithingOnly) smithing120.build(prov, ctx.getId().withSuffix("_120"));
                }
            });
            this.crossbowEntry = itemBuilder2.register();
            ECItems.ITEMS.add(this.crossbowEntry);
        }
    }

    private void shapedRecipe(DataGenContext<Item,? extends Item> ctx, String[] pattern, Ingredient ingredient, Ingredient prevBow, ConditionalRecipe.Builder conditionalRecipe, OrCondition orCondition, InventoryChangeTrigger.TriggerInstance triggerInstance, String nameSufix) {
        conditionalRecipe.addCondition(new ECConfigBooleanCondition("bow"));
        if (orCondition != null ) conditionalRecipe.addCondition(orCondition);

        ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ctx.get());
        for (String string:
                pattern) {
            builder.pattern(string);
        }
        builder.define('i', ingredient);
        builder.define('b', prevBow);
        builder.unlockedBy("has_item", triggerInstance);
        builder.save(conditionalRecipe::addRecipe, ctx.getId() + "_shaped" + nameSufix);
    }

    private void smithing120Recipe(DataGenContext<Item,? extends Item> ctx, Ingredient template, Ingredient ingredient, Ingredient prevBow, ConditionalRecipe.Builder conditionalRecipe, OrCondition orCondition, InventoryChangeTrigger.TriggerInstance triggerInstance) {
        conditionalRecipe.addCondition(new ECConfigBooleanCondition("bow"));
        if (orCondition != null ) conditionalRecipe.addCondition(orCondition);

        SmithingTransformRecipeBuilder.smithing( template, prevBow, ingredient, RecipeCategory.COMBAT, ctx.get())
                .unlocks("has_item", triggerInstance)
                .save(conditionalRecipe::addRecipe, ctx.getId() + "_future_smithing");
    }

    @Deprecated(forRemoval = true)
    private void legacySmithingRecipe(DataGenContext<Item,? extends Item> ctx, Ingredient ingredient, Ingredient prevBow, ConditionalRecipe.Builder conditionalRecipe, OrCondition orCondition, InventoryChangeTrigger.TriggerInstance triggerInstance) {
        conditionalRecipe.addCondition(new ECConfigBooleanCondition("bow"));
        if (orCondition != null ) conditionalRecipe.addCondition(orCondition);

        LegacyUpgradeRecipeBuilder.smithing( ingredient, prevBow, RecipeCategory.COMBAT, ctx.get())
                .unlocks("has_item", triggerInstance)
                .save(conditionalRecipe::addRecipe, ctx.getId() + "_smithing");
    }

    public int getDurability() {
        return this.bowMaterialConfig.durability;
    }

    public int getEnchantability() {
        return this.bowMaterialConfig.enchantability;
    }

    public int getMultishotLevel() {
        return this.bowMaterialConfig.multishotLevel;
    }

    public int getPowerLevel() {
        return this.bowMaterialConfig.bowPower;
    }

    public float getVelocityMultiplier() {
        return this.bowMaterialConfig.velocityMultiplyer;
    }

    public Ingredient getRepairIngredient() {
        return IngredientUtil.getIngrediantFromItemString(this.bowMaterialConfig.repairItem);
    }

    public float getMendingBonus() {
        return this.bowMaterialConfig.mendingBonus;
    }

    public boolean getFireResistant() {
        return this.bowMaterialConfig.fireResistant;
    }

    public @NotNull String getName() {
        return name;
    }

    public boolean isNotHalfBow() {
        return !halfBow;
    }

    public RegistryEntry<ECBowItem> getBowEntry() {
        Preconditions.checkNotNull(bowEntry, "Cannot access value of " + this.name + " bow registry entry before its creation");
        return bowEntry;
    }

    public @Nullable RegistryEntry<ECCrossBowItem> getCrossbowEntry() {
        return crossbowEntry;
    }
}
