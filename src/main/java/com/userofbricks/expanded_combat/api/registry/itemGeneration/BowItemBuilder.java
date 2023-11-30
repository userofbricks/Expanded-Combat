package com.userofbricks.expanded_combat.api.registry.itemGeneration;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.util.entry.RegistryEntry;
import com.tterrag.registrate.util.nullness.NonNullBiFunction;
import com.tterrag.registrate.util.nullness.NonNullConsumer;
import com.userofbricks.expanded_combat.api.NonNullTriConsumer;
import com.userofbricks.expanded_combat.api.NonNullTriFunction;
import com.userofbricks.expanded_combat.api.material.Material;
import com.userofbricks.expanded_combat.api.material.MaterialBuilder;
import com.userofbricks.expanded_combat.config.ECConfig;
import com.userofbricks.expanded_combat.item.ECItemTags;
import com.userofbricks.expanded_combat.item.recipes.conditions.ECConfigBooleanCondition;
import com.userofbricks.expanded_combat.item.recipes.conditions.ECConfigBowRecipeTypeCondition;
import com.userofbricks.expanded_combat.item.recipes.conditions.ECMaterialBooleanCondition;
import com.userofbricks.expanded_combat.util.IngredientUtil;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.NotCondition;
import net.minecraftforge.common.crafting.conditions.OrCondition;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class BowItemBuilder extends MaterialItemBuilder {
    public final MaterialBuilder materialBuilder;
    public final Material material, craftedFrom;
    public final ItemBuilder<? extends BowItem, Registrate> itemBuilder, halfItemBuilder;
    private String lang = "";
    private NonNullTriConsumer<ItemBuilder<? extends BowItem, Registrate>, Material, Boolean> modelBuilder, halfModelBuilder;
    private NonNullTriConsumer<ItemBuilder<? extends BowItem, Registrate>, Material, @Nullable Material> recipeBuilder, halfRecipeBuilder;
    private NonNullConsumer<ItemBuilder<? extends BowItem, Registrate>> colorBuilder, halfColorBuilder;

    public BowItemBuilder(MaterialBuilder materialBuilder, Registrate registrate, Material material, Material craftedFrom, NonNullTriFunction<Item.Properties, Material, Material, ? extends BowItem> constructor) {
        ItemBuilder<? extends BowItem, Registrate> itemBuilder = registrate.item(material.getLocationName().getPath() + "_bow", (p) -> constructor.apply(p, material, craftedFrom));

        itemBuilder.properties(properties -> properties.stacksTo(1));
        itemBuilder.tag(ECItemTags.BOWS);

        this.material = material;
        this.itemBuilder = itemBuilder;
        this.halfItemBuilder = null;
        this.materialBuilder = materialBuilder;
        this.craftedFrom = craftedFrom;
        lang = material.getName() + " Gauntlet";
        modelBuilder = (registrateItemBuilder, material1, aBoolean) -> genModel(itemBuilder, material.getLocationName().getPath(), "", aBoolean);
        recipeBuilder = BowItemBuilder::generateRecipes;
        colorBuilder = BowItemBuilder::colors;
    }

    public BowItemBuilder(MaterialBuilder materialBuilder, Registrate registrate, Material material, Material craftedFrom, NonNullTriFunction<Item.Properties, Material, Material, ? extends BowItem> constructor, NonNullTriFunction<Item.Properties, Material, Material, ? extends BowItem> halfConstructor) {
        ItemBuilder<? extends BowItem, Registrate> itemBuilder = registrate.item(material.getLocationName().getPath() + "_bow", (p) -> constructor.apply(p, material, craftedFrom));
        ItemBuilder<? extends BowItem, Registrate> halfItemBuilder = registrate.item("half_" + material.getLocationName().getPath() + "_bow", (p) -> constructor.apply(p, material, craftedFrom));

        itemBuilder.properties(properties -> properties.stacksTo(1));
        halfItemBuilder.properties(properties -> properties.stacksTo(1));
        itemBuilder.tag(ECItemTags.BOWS);
        halfItemBuilder.tag(ECItemTags.BOWS);

        this.material = material;
        this.itemBuilder = itemBuilder;
        this.halfItemBuilder = halfItemBuilder;
        this.materialBuilder = materialBuilder;
        this.craftedFrom = craftedFrom;
        lang = material.getName() + " Gauntlet";
        modelBuilder = (registrateItemBuilder, material1, aBoolean) -> genModel(itemBuilder, material.getLocationName().getPath(), "", aBoolean);
        recipeBuilder = BowItemBuilder::generateRecipes;
        colorBuilder = BowItemBuilder::colors;
        halfModelBuilder = (registrateItemBuilder, material1, aBoolean) -> genModel(itemBuilder, material.getLocationName().getPath(), "half_", aBoolean);
        halfRecipeBuilder = BowItemBuilder::generateHalfRecipes;
        halfColorBuilder = BowItemBuilder::colors;
    }
    public BowItemBuilder lang(String englishName) {
        lang = englishName;
        return this;
    }
    public BowItemBuilder model(NonNullTriConsumer<ItemBuilder<? extends BowItem, Registrate>, Material, Boolean> modelBuilder) {
        this.modelBuilder = modelBuilder;
        return this;
    }
    public BowItemBuilder recipes(NonNullTriConsumer<ItemBuilder<? extends BowItem, Registrate>, Material, Material> recipeBuilder) {
        this.recipeBuilder = recipeBuilder;
        return this;
    }

    public BowItemBuilder color(NonNullConsumer<ItemBuilder<? extends BowItem, Registrate>> colorBuilder) {
        this.colorBuilder = colorBuilder;
        return this;
    }
    public BowItemBuilder halfModel(NonNullTriConsumer<ItemBuilder<? extends BowItem, Registrate>, Material, Boolean> modelBuilder) {
        this.halfModelBuilder = modelBuilder;
        return this;
    }
    public BowItemBuilder halfRecipes(NonNullTriConsumer<ItemBuilder<? extends BowItem, Registrate>, Material, Material> recipeBuilder) {
        this.halfRecipeBuilder = recipeBuilder;
        return this;
    }

    public BowItemBuilder halfColor(NonNullConsumer<ItemBuilder<? extends BowItem, Registrate>> colorBuilder) {
        this.halfColorBuilder = colorBuilder;
        return this;
    }

    public MaterialBuilder build(boolean dyeable) {
        if (halfItemBuilder != null) {
            halfItemBuilder.lang(lang);
            halfModelBuilder.apply(itemBuilder, material, dyeable);
            halfRecipeBuilder.apply(itemBuilder, material, craftedFrom);
            if (dyeable) halfColorBuilder.accept(itemBuilder);

            materialBuilder.halfbow(m -> itemBuilder.register());
        }

        itemBuilder.lang(lang);
        modelBuilder.apply(itemBuilder, material, dyeable);
        recipeBuilder.apply(itemBuilder, material, craftedFrom);
        if (dyeable) colorBuilder.accept(itemBuilder);

        materialBuilder.bow(m -> itemBuilder.register());
        return materialBuilder;
    }

    public static void colors(ItemBuilder<? extends Item, Registrate> itemBuilder) {
        itemBuilder.color(() -> () -> (stack, itemLayer) -> (itemLayer == 0) ? ((DyeableLeatherItem)stack.getItem()).getColor(stack) : -1);
    }
    public static void generateRecipes(ItemBuilder<? extends BowItem, Registrate> itemBuilder, Material material, @Nullable Material craftedFrom) {
        itemBuilder.recipe((ctx, prov) -> {

            if (!material.getConfig().crafting.repairItem.isEmpty()) {
                InventoryChangeTrigger.TriggerInstance triggerInstance = getTriggerInstance(material.getConfig().crafting.repairItem);

                ECConfigBooleanCondition enableBows = new ECConfigBooleanCondition("bow");
                ECConfigBooleanCondition enableHalfBows = new ECConfigBooleanCondition("half_bow");
                ECConfigBowRecipeTypeCondition smithingOnly = new ECConfigBowRecipeTypeCondition(ECConfig.BowRecipeType.SMITHING_ONLY);
                ECConfigBowRecipeTypeCondition craftingOnly = new ECConfigBowRecipeTypeCondition(ECConfig.BowRecipeType.CRAFTING_TABLE_ONLY);
                ECConfigBowRecipeTypeCondition craftingAndSmithing = new ECConfigBowRecipeTypeCondition(ECConfig.BowRecipeType.CRAFTING_TABLE_AND_SMITHING);
                OrCondition smithing_or_both = new OrCondition(smithingOnly, craftingAndSmithing);
                OrCondition crafting_or_both = new OrCondition(craftingOnly, craftingAndSmithing);
                ECMaterialBooleanCondition isSingleAddition = new ECMaterialBooleanCondition(material.getName(), "config", "crafting", "is_single_addition");

                //Shaped Crafting
                Map<Character, Ingredient> ingredientMap = new HashMap<>();
                ingredientMap.put('i', IngredientUtil.getIngrediantFromItemString(material.getConfig().crafting.repairItem));
                if (material.halfbow) {
                    ingredientMap.put('b', material.getHalfBowEntry() == null ? Ingredient.of(Items.BOW) : Ingredient.of(material.getHalfBowEntry().get()));
                    conditionalShapedRecipe(ctx, prov, new String[]{"i", "b"}, ingredientMap, 1, new ICondition[]{crafting_or_both, enableBows, enableHalfBows}, triggerInstance, "");
                }
                ingredientMap.remove('b');
                ingredientMap.put('b', craftedFrom == null ? Ingredient.of(Items.BOW) : Ingredient.of(craftedFrom.getBowEntry().get()));
                conditionalShapedRecipe(ctx, prov, new String[]{"i", "b", "i"}, ingredientMap, 1, new ICondition[]{crafting_or_both, enableBows}, triggerInstance, "_skip");

                //1.20
                conditionalSmithing120Recipe(ctx, prov, material,
                        material.getHalfBowEntry() == null ? Ingredient.of(Items.BOW) : Ingredient.of(material.getHalfBowEntry().get()),
                        new ICondition[]{smithing_or_both, enableBows, enableHalfBows, new NotCondition(isSingleAddition)}, "");
                conditionalSmithing120Recipe(ctx, prov, material,
                        craftedFrom == null ? Ingredient.of(Items.BOW) : Ingredient.of(craftedFrom.getBowEntry().get()),
                        new ICondition[]{smithing_or_both, enableBows, new OrCondition(new NotCondition(enableHalfBows), isSingleAddition)}, "_singleton");
            }
        });
    }
    public static void generateHalfRecipes(ItemBuilder<? extends BowItem, Registrate> itemBuilder, Material material, @Nullable Material craftedFrom) {
        itemBuilder.recipe((ctx, prov) -> {
            if (!material.getConfig().crafting.repairItem.isEmpty()) {
                InventoryChangeTrigger.TriggerInstance triggerInstance = getTriggerInstance(material.getConfig().crafting.repairItem);

                ECConfigBooleanCondition enableBows = new ECConfigBooleanCondition("bow");
                ECConfigBooleanCondition enableHalfBows = new ECConfigBooleanCondition("half_bow");
                ECConfigBowRecipeTypeCondition smithingOnly = new ECConfigBowRecipeTypeCondition(ECConfig.BowRecipeType.SMITHING_ONLY);
                ECConfigBowRecipeTypeCondition craftingOnly = new ECConfigBowRecipeTypeCondition(ECConfig.BowRecipeType.CRAFTING_TABLE_ONLY);
                ECConfigBowRecipeTypeCondition craftingAndSmithing = new ECConfigBowRecipeTypeCondition(ECConfig.BowRecipeType.CRAFTING_TABLE_AND_SMITHING);
                OrCondition smithing_or_both = new OrCondition(smithingOnly, craftingAndSmithing);
                OrCondition crafting_or_both = new OrCondition(craftingOnly, craftingAndSmithing);

                //Shaped Crafting
                Map<Character, Ingredient> ingredientMap = new HashMap<>();
                ingredientMap.put('i', IngredientUtil.getIngrediantFromItemString(material.getConfig().crafting.repairItem));
                ingredientMap.put('b', material.getHalfBowEntry() == null ? Ingredient.of(Items.BOW) : Ingredient.of(material.getHalfBowEntry().get()));
                conditionalShapedRecipe(ctx, prov, new String[]{"b", "i"}, ingredientMap, 1, new ICondition[]{crafting_or_both, enableBows, enableHalfBows}, triggerInstance, "");

                //1.20
                conditionalSmithing120Recipe(ctx, prov, material,
                        craftedFrom == null ? Ingredient.of(Items.BOW) : Ingredient.of(craftedFrom.getBowEntry().get()),
                        new ICondition[]{smithing_or_both, enableBows, enableHalfBows}, "");
            }
        });
    }

    private static void genModel(ItemBuilder<? extends BowItem, Registrate> itemBuilder, String locationName, String prefix, boolean dyeable) {
        String modid = itemBuilder.getParent().getModid();
        itemBuilder.model((ctx, prov) -> {
            ItemModelBuilder itemModelBuilder;
            if (dyeable) {
                itemModelBuilder = prov.generated(ctx, new ResourceLocation(modid, "item/bow/" + prefix + locationName),
                        new ResourceLocation(modid, "item/bow/" + prefix + locationName + "_overlay"));
            } else {
                itemModelBuilder = prov.generated(ctx, new ResourceLocation(modid, "item/bow/" + prefix + locationName));
            }

            itemModelBuilder.transforms()
                .transform(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND).rotation(-80, 260, -40).translation(-1, -2, 2.5f).scale(0.9f).end()
                .transform(ItemDisplayContext.THIRD_PERSON_LEFT_HAND).rotation(-80, -280, 40).translation(-1, -2, 2.5f).scale(0.9f).end()
                .transform(ItemDisplayContext.FIRST_PERSON_RIGHT_HAND).rotation(0, -90, 25).translation(1.13f, 3.2f, 1.13f).scale(0.68f).end()
                .transform(ItemDisplayContext.FIRST_PERSON_LEFT_HAND).rotation(0, 90, -25).translation(1.13f, 3.2f, 1.13f).scale(0.68f).end()
                .end()
                .override().predicate(new ResourceLocation("pulling"), 1).model(
                        !dyeable ? prov.withExistingParent(ctx.getName()+"_pulling_0", new ResourceLocation("item/bow"))
                                .texture("layer0", new ResourceLocation(modid, "item/bow/" + prefix + locationName + "_pulling_0")) :
                                prov.withExistingParent(ctx.getName()+"_pulling_0", new ResourceLocation("item/bow"))
                                .texture("layer0", new ResourceLocation(modid, "item/bow/" + prefix + locationName + "_pulling_0"))
                                .texture("layer1", new ResourceLocation(modid, "item/bow/" + prefix + locationName + "_pulling_0_overlay"))
                ).end()
                .override().predicate(new ResourceLocation("pulling"), 1).predicate(new ResourceLocation("pull"), 0.65f).model(
                        !dyeable ? prov.withExistingParent(ctx.getName()+"_pulling_1", new ResourceLocation("item/bow"))
                                .texture("layer0", new ResourceLocation(modid, "item/bow/" + prefix + locationName + "_pulling_1")) :
                                prov.withExistingParent(ctx.getName()+"_pulling_1", new ResourceLocation("item/bow"))
                                .texture("layer0", new ResourceLocation(modid, "item/bow/" + prefix + locationName + "_pulling_1"))
                                .texture("layer1", new ResourceLocation(modid, "item/bow/" + prefix + locationName + "_pulling_1_overlay"))
                ).end()
                .override().predicate(new ResourceLocation("pulling"), 1).predicate(new ResourceLocation("pull"), 0.9f).model(
                        !dyeable ? prov.withExistingParent(ctx.getName()+"_pulling_2", new ResourceLocation("item/bow"))
                                .texture("layer0", new ResourceLocation(modid, "item/bow/" + prefix + locationName + "_pulling_2")) :
                                prov.withExistingParent(ctx.getName()+"_pulling_2", new ResourceLocation("item/bow"))
                                .texture("layer0", new ResourceLocation(modid, "item/bow/" + prefix + locationName + "_pulling_2"))
                                .texture("layer1", new ResourceLocation(modid, "item/bow/" + prefix + locationName + "_pulling_2_overlay"))
                ).end();
        });
    }
}
