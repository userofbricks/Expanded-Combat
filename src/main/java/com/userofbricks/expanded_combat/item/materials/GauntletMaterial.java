package com.userofbricks.expanded_combat.item.materials;

import com.google.common.base.Preconditions;
import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.util.entry.RegistryEntry;
import com.userofbricks.expanded_combat.config.ECConfig.GauntletMaterialConfig;
import com.userofbricks.expanded_combat.ExpandedCombat;
import com.userofbricks.expanded_combat.item.ECGauntletItem;
import com.userofbricks.expanded_combat.item.ECItemTags;
import com.userofbricks.expanded_combat.item.ECItems;
import com.userofbricks.expanded_combat.item.recipes.ECConfigBooleanCondition;
import com.userofbricks.expanded_combat.util.IngredientUtil;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.ConditionalAdvancement;
import net.minecraftforge.common.crafting.ConditionalRecipe;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Locale;

public class GauntletMaterial {
    @NotNull
    private final String name;
    @NotNull
    private final GauntletMaterialConfig gauntletMaterialConfig;
    private RegistryEntry<ECGauntletItem> gauntletEntry;

    public GauntletMaterial(@NotNull String name, @NotNull GauntletMaterialConfig gauntletMaterialConfig, List<GauntletMaterial> gauntletMaterials) {
        this.name = name;
        this.gauntletMaterialConfig = gauntletMaterialConfig;
        gauntletMaterials.add(this);
    }

    public final void registerElements() {
        //register item
        ItemBuilder<ECGauntletItem, Registrate> itemBuilder = ExpandedCombat.REGISTRATE.get().item(this.name.toLowerCase(Locale.ROOT) + "_gauntlet", (p) -> new ECGauntletItem(this, p));
        itemBuilder.defaultModel();
        if (name.equals(MaterialInit.NAGA_GAUNTLET.name)) itemBuilder.lang("Naga Scale Gauntlet");
        itemBuilder.tag(ECItemTags.GAUNTLETS);
        itemBuilder.recipe((ctx, prov) -> {
            //used for advancement trigger and recipe input item
            Ingredient ingredient = IngredientUtil.getIngrediantFromItemString(this.gauntletMaterialConfig.repairItem);
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

                ECConfigBooleanCondition enableGauntlets = new ECConfigBooleanCondition("gauntlet");

                ConditionalRecipe.Builder conditionalRecipe = ConditionalRecipe.builder()
                        .addCondition(enableGauntlets)
                        .setAdvancement(ctx.getId().getNamespace(), "recipes/" + RecipeCategory.COMBAT.getFolderName() + "/" + ctx.getId().getPath() + "_conditional",
                                ConditionalAdvancement.builder()
                                        .addCondition(enableGauntlets)
                                        .addAdvancement(advancement));

                if (!name.equals(MaterialInit.NETHERITE_GAUNTLET.name)) {
                    ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ctx.get())
                            .pattern("bb")
                            .pattern("b ")
                            .define('b', ingredient)
                            .unlockedBy("has_item", triggerInstance)
                            .save(conditionalRecipe::addRecipe);
                } else {
                    LegacyUpgradeRecipeBuilder.smithing(Ingredient.of(MaterialInit.DIAMOND_GAUNTLET.gauntletEntry.get()), ingredient, RecipeCategory.COMBAT, ctx.get())
                            .unlocks("has_item", triggerInstance)
                            .save(conditionalRecipe::addRecipe, ctx.getId() + "_smithing");
                    conditionalRecipe
                            .addCondition(enableGauntlets);
                    SmithingTransformRecipeBuilder.smithing(Ingredient.of(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE), Ingredient.of(MaterialInit.DIAMOND_GAUNTLET.gauntletEntry.get()), ingredient, RecipeCategory.COMBAT, ctx.get())
                            .unlocks("has_item", triggerInstance)
                            .save(conditionalRecipe::addRecipe, ctx.getId() + "_future_smithing");
                }

                conditionalRecipe.build(prov, ctx.getId());
            }
        });
        this.gauntletEntry = itemBuilder.register();
        ECItems.ITEMS.add(this.gauntletEntry);
    }

    public int getEnchantability() {
        return this.gauntletMaterialConfig.enchantability;
    }

    public int getDurability() {
        return this.gauntletMaterialConfig.durability;
    }

    public double getAttackDamage() {
        return this.gauntletMaterialConfig.attackDamage;
    }

    public int getArmorAmount() {
        return this.gauntletMaterialConfig.armorAmount;
    }

    public @NotNull String getName() {
        return this.name;
    }

    public String getTextureName() {
        return this.name.toLowerCase(Locale.ROOT);
    }

    public SoundEvent getSoundEvent() {
        return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(this.gauntletMaterialConfig.equipSound));
    }

    public Ingredient getRepairIngredient() {
        return IngredientUtil.getIngrediantFromItemString(this.gauntletMaterialConfig.repairItem);
    }

    public double getArmorToughness() {
        return this.gauntletMaterialConfig.armorToughness;
    }

    public double getKnockbackResistance() {
        return this.gauntletMaterialConfig.knockbackResistance;
    }

    public double getMendingBonus() {
        return this.gauntletMaterialConfig.mendingBonus;
    }

    public boolean getFireResistant() {
        return this.gauntletMaterialConfig.fireResistant;
    }

    public RegistryEntry<ECGauntletItem> getGauntletEntry() {
        Preconditions.checkNotNull(gauntletEntry, "Cannot access value of gauntlet registry entry before its creation");
        return gauntletEntry;
    }
}
