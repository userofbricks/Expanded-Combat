package com.userofbricks.expanded_combat.values;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.util.entry.RegistryEntry;
import com.userofbricks.expanded_combat.ExpandedCombat;
import com.userofbricks.expanded_combat.item.ECGauntletItem;
import com.userofbricks.expanded_combat.item.ECItemTags;
import com.userofbricks.expanded_combat.item.recipes.ECConfigBooleanCondition;
import com.userofbricks.expanded_combat.util.IngredientUtil;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.crafting.ConditionalAdvancement;
import net.minecraftforge.common.crafting.ConditionalRecipe;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Locale;
import java.util.Map;

public class GauntletMaterial {
    private final String name;
    private final ForgeConfigSpec.IntValue durability;
    private final ForgeConfigSpec.IntValue armorAmount;
    private final ForgeConfigSpec.DoubleValue attackDamage;
    private final ForgeConfigSpec.IntValue enchantability;
    private final ForgeConfigSpec.ConfigValue<String> equipSound;
    private final ForgeConfigSpec.DoubleValue armorToughness;
    private final ForgeConfigSpec.DoubleValue knockbackResistance;
    private final ForgeConfigSpec.ConfigValue<String> repairItem;
    private final ForgeConfigSpec.DoubleValue mendingBonus;
    private final ForgeConfigSpec.BooleanValue fireResistant;
    private RegistryEntry<ECGauntletItem> gauntletEntry;

    GauntletMaterial(ForgeConfigSpec.Builder builder, String name, int durability, int enchantability, double mendingBonus, int armorAmount, double attackDamage, String repairItem, ResourceLocation equipSound, double armorToughness, double knockbackResistance, boolean fireResistant, List<GauntletMaterial> gauntletMaterials) {
        builder.push(name +" Gauntlet");
        this.name =                 name;
        this.durability =           builder.comment("Default value: " + durability)         .translation(ECConfig.CONFIG_PREFIX + name.toLowerCase(Locale.ROOT) + "GauntletDurability")         .defineInRange(name.toLowerCase(Locale.ROOT)+"GauntletDurability",              durability,          0, Integer.MAX_VALUE);
        this.enchantability =       builder.comment("Default value: " + enchantability)     .translation(ECConfig.CONFIG_PREFIX + name.toLowerCase(Locale.ROOT) + "GauntletEnchantability")     .defineInRange(name.toLowerCase(Locale.ROOT)+"GauntletEnchantability",          enchantability,      0, Integer.MAX_VALUE);
        this.mendingBonus =         builder.comment("Default Value: " + mendingBonus)       .translation(ECConfig.CONFIG_PREFIX + name.toLowerCase(Locale.ROOT) + "GauntletMendingBonus")       .defineInRange(name.toLowerCase(Locale.ROOT) + "GauntletMendingBonus",          mendingBonus,        -Double.MAX_VALUE, Double.MAX_VALUE);
        this.armorAmount =          builder.comment("Default value: " + armorAmount)        .translation(ECConfig.CONFIG_PREFIX + name.toLowerCase(Locale.ROOT) + "GauntletArmorAmount")        .defineInRange(name.toLowerCase(Locale.ROOT) +"GauntletArmorAmount",            armorAmount,         0, Integer.MAX_VALUE);
        this.attackDamage =         builder.comment("Default value: " + attackDamage)       .translation(ECConfig.CONFIG_PREFIX + name.toLowerCase(Locale.ROOT) + "GauntletDamage")             .defineInRange(name.toLowerCase(Locale.ROOT)+"GauntletDamage",                  attackDamage,        0d, Double.MAX_VALUE);
        this.equipSound =           builder
                .comment("Default Value: " + equipSound)
                .translation(ECConfig.CONFIG_PREFIX + name.toLowerCase(Locale.ROOT) + "GauntletEquipSound")
                .define(name.toLowerCase(Locale.ROOT) + "GauntletEquipSound", equipSound.toString());
        this.repairItem =           builder
                .comment("default Value: " + repairItem)
                .translation(ECConfig.CONFIG_PREFIX + name.toLowerCase(Locale.ROOT) + "GauntletIngredientItems")
                .define(name.toLowerCase(Locale.ROOT) + "GauntletIngredientItems", repairItem);
        this.armorToughness =       builder.comment("Default value: " + armorToughness)     .translation(ECConfig.CONFIG_PREFIX + name.toLowerCase(Locale.ROOT) + "GauntletArmorToughness")     .defineInRange(name.toLowerCase(Locale.ROOT) + "GauntletArmorToughness",        armorToughness,      0d, Double.MAX_VALUE);
        this.knockbackResistance =  builder.comment("Default value: " + knockbackResistance).translation(ECConfig.CONFIG_PREFIX + name.toLowerCase(Locale.ROOT) + "GauntletKnockBackResistance").defineInRange(name.toLowerCase(Locale.ROOT) + "GauntletKnockBackResistance",   knockbackResistance, 0d, 10d);
        this.fireResistant =        builder.comment("Default value: " + fireResistant)      .translation(ECConfig.CONFIG_PREFIX + name.toLowerCase(Locale.ROOT) + "GauntletFireResistance")     .define(name.toLowerCase(Locale.ROOT) + "GauntletFireResistance", fireResistant);
        builder.pop(1);

        if (gauntletMaterials != null) gauntletMaterials.add(this);
    }

    GauntletMaterial(ForgeConfigSpec.Builder builder, String name, int durability, int enchantability, double mendingBonus, int armorAmount, double attackDamage, Ingredient repairItem, SoundEvent equipSound, double armorToughness, double knockbackResistance, boolean fireResistant, List<GauntletMaterial> gauntletMaterials) {
        this(   builder,
                name,
                durability,
                enchantability,
                mendingBonus,
                armorAmount,
                attackDamage,
                IngredientUtil.getItemStringFromIngrediant(repairItem),
                equipSound.getLocation(),
                armorToughness,
                knockbackResistance,
                fireResistant,
                gauntletMaterials);
    }

    GauntletMaterial(ForgeConfigSpec.Builder builder, String name, ArmorMaterial armorMaterial, int durability, double mendingBonus, double attackDamage, boolean fireResistant, List<GauntletMaterial> gauntletMaterials) {
        this(   builder,
                name,
                durability,
                armorMaterial.getEnchantmentValue(),
                mendingBonus,
                armorMaterial.getDefenseForSlot(EquipmentSlot.FEET),
                attackDamage,
                armorMaterial.getRepairIngredient(),
                armorMaterial.getEquipSound(),
                armorMaterial.getToughness(),
                armorMaterial.getKnockbackResistance(),
                fireResistant,
                gauntletMaterials);
    }

    GauntletMaterial(ForgeConfigSpec.Builder builder, String name, Tier tier, ArmorMaterial armorMaterial, double mendingBonus, boolean armorNotWeaponEnchantability, boolean fireResistant, List<GauntletMaterial> gauntletMaterials) {
        this(   builder,
                name,
                tier.getUses(),
                armorNotWeaponEnchantability ? armorMaterial.getEnchantmentValue() : tier.getEnchantmentValue(),
                mendingBonus,
                armorMaterial.getDefenseForSlot(EquipmentSlot.FEET),
                tier.getAttackDamageBonus(),
                armorMaterial.getRepairIngredient(),
                armorMaterial.getEquipSound(),
                armorMaterial.getToughness(),
                armorMaterial.getKnockbackResistance(),
                fireResistant,
                gauntletMaterials);
    }

    public final void registerElements() {
            //register item
            ItemBuilder<ECGauntletItem, Registrate> itemBuilder = ExpandedCombat.REGISTRATE.get().item(this.name.toLowerCase(Locale.ROOT) + "_gauntlet", (p) -> new ECGauntletItem(this, p));
            itemBuilder.defaultModel();
            itemBuilder.tag(ECItemTags.GAUNTLETS);
            itemBuilder.recipe((ctx, prov) -> {
                //used for advancement trigger and recipe input item
                Ingredient ingredient = IngredientUtil.getIngrediantFromItemString(this.repairItem.getDefault());
                //here only because it is needed in both the conditional and standard advancements
                InventoryChangeTrigger.TriggerInstance triggerInstance = InventoryChangeTrigger.TriggerInstance.hasItems(IngredientUtil.toItemLikeArray(ingredient));

                Advancement.Builder advancement = Advancement.Builder.advancement()
                        .addCriterion("has_item", triggerInstance)
                        .parent(RecipeBuilder.ROOT_RECIPE_ADVANCEMENT)
                        .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(ctx.getId()))
                        .rewards(AdvancementRewards.Builder.recipe(ctx.getId()))
                        .requirements(RequirementsStrategy.OR);

                ECConfigBooleanCondition enableGauntlets = new ECConfigBooleanCondition(ECConfig.SERVER.enableGauntlets);

                ConditionalRecipe.Builder conditionalRecipe = ConditionalRecipe.builder()
                        .addCondition(enableGauntlets)
                        .setAdvancement(ctx.getId().getNamespace(), "recipes/" + RecipeCategory.COMBAT.getFolderName() + "/" + ctx.getId().getPath(),
                                ConditionalAdvancement.builder()
                                .addCondition(enableGauntlets)
                                .addAdvancement(advancement));

                ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ctx.get())
                        .pattern("bb")
                        .pattern("b ")
                        .define('b', ingredient)
                        .unlockedBy("has_item", triggerInstance)
                        .save(conditionalRecipe::addRecipe);

                conditionalRecipe.build(prov, ctx.getId());
            });
            this.gauntletEntry = itemBuilder.register();
    }

    public int getEnchantability() {
        return this.enchantability.get();
    }

    public int getDurability() {
        return this.durability.get();
    }

    public double getAttackDamage() {
        return this.attackDamage.get();
    }

    public int getArmorAmount() {
        return this.armorAmount.get();
    }

    public String getTextureName() {
        return this.name.toLowerCase(Locale.ROOT);
    }

    public SoundEvent getSoundEvent() {
        return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(this.equipSound.get()));
    }

    public Ingredient getRepairMaterial() {
        return IngredientUtil.getIngrediantFromItemString(this.repairItem.get());
    }

    public double getArmorToughness() {
        return this.armorToughness.get();
    }

    public double getKnockbackResistance() {
        return this.knockbackResistance.get();
    }

    public double getMendingBonus() {
        return mendingBonus.get();
    }

    public boolean getFireResistant() {
        return fireResistant.get();
    }

    public RegistryEntry<ECGauntletItem> getGauntletEntry() {
        Preconditions.checkNotNull(gauntletEntry, "Cannot access value of gauntlet registry entry before its creation");
        return gauntletEntry;
    }
}
