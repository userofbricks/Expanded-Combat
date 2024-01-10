package com.userofbricks.expanded_combat.config;

import com.userofbricks.expanded_combat.ExpandedCombat;
import com.userofbricks.expanded_combat.util.IngredientUtil;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class MaterialConfig {

    @ConfigEntry.Gui.CollapsibleObject
    @ConfigName("Durability")
    public Durability durability;
    @ConfigEntry.Gui.CollapsibleObject
    @ConfigName("Enchanting")
    public Enchanting enchanting;
    @ConfigName("Equip Sound")
    public String equipSound;
    @ConfigName("Mending Bonus")
    public float mendingBonus;
    @ConfigName("Fire Resistant")
    public boolean fireResistant;
    @ConfigEntry.Gui.CollapsibleObject
    @ConfigName("Offence")
    public Offense offense;
    @ConfigEntry.Gui.CollapsibleObject
    @ConfigName("Defence")
    public Defense defense;
    @ConfigEntry.Gui.CollapsibleObject
    @ConfigName("Crafting")
    public Crafting crafting;
    @ConfigName("InventorySlots")
    @ConfigEntry.BoundedDiscrete(min = 1, max = 24)
    public int quiverSlots;

    MaterialConfig(int toolDurability, int addedShieldDurability, int bowDurability, int offenseEnchantability, int defenseEnchantability, String equipSound, List<String> repairItem,
                   String craftingItem, float mendingBonus, boolean fireResistant, double gauntletAttackDamage, float arrowDamage, boolean flaming, boolean canBeTipped, int multishotLevel,
                   int bowPower, float velocityMultiplier, int gauntletArmorAmount, double armorToughness, double knockbackResistance, float baseProtectionAmmount, float afterBasePercentReduction,
                   boolean isSingleAddition, List<String> onlyReplaceResource, String smithingTemplate, int quiverSlots) {
        this.durability = new Durability(toolDurability, addedShieldDurability, bowDurability);
        this.enchanting = new Enchanting(offenseEnchantability, defenseEnchantability);
        this.equipSound = equipSound;
        this.mendingBonus = mendingBonus;
        this.fireResistant = fireResistant;
        this.offense = new Offense(gauntletAttackDamage, arrowDamage, flaming, canBeTipped, multishotLevel, bowPower, velocityMultiplier);
        this.defense = new Defense(gauntletArmorAmount, armorToughness, knockbackResistance, baseProtectionAmmount, afterBasePercentReduction);
        this.crafting = new Crafting(repairItem, isSingleAddition, onlyReplaceResource, smithingTemplate, craftingItem);
        this.quiverSlots = quiverSlots;
    }

    public static class Durability {
        @ConfigEntry.BoundedDiscrete(max = Integer.MAX_VALUE)
        @ConfigName("Tool Durability")
        @ConfigEntry.Gui.Tooltip
        @TooltipFrase("This is used as the gauntlet durability as well as the base durability for weapons")
        public int toolDurability;
        @ConfigEntry.BoundedDiscrete(max = Integer.MAX_VALUE)
        @ConfigName("Bow/Crossbow Durability")
        public int bowDurability;
        @ConfigEntry.BoundedDiscrete(max = Integer.MAX_VALUE)
        @ConfigName("Added Shield Durability")
        @ConfigEntry.Gui.Tooltip
        @TooltipFrase("this is the amount of durability added by each of the five sections, onto the base wood shield durability")
        public int addedShieldDurability;

        Durability(int toolDurability, int addedShieldDurability, int bowDurability) {
            this.toolDurability = toolDurability;
            this.addedShieldDurability = addedShieldDurability;
            this.bowDurability = bowDurability;
        }
    }

    public static class Enchanting {
        @ConfigEntry.BoundedDiscrete(max = 512)
        @ConfigName("Weapon Enchantability")
        public int offenseEnchantability;
        @ConfigEntry.BoundedDiscrete(max = 512)
        @ConfigName("Weapon Enchantability")
        public int defenseEnchantability;

        public Enchanting(int offenseEnchantability, int defenseEnchantability) {
            this.offenseEnchantability = offenseEnchantability;
            this.defenseEnchantability = defenseEnchantability;
        }
    }

    public static class Offense {
        public Offense(double addedAttackDamage, float arrowDamage, boolean flaming, boolean canBeTipped, int multishotLevel, int bowPower, float velocityMultiplier) {
            this.addedAttackDamage = addedAttackDamage;
            this.arrowDamage = arrowDamage;
            this.flaming = flaming;
            this.canBeTipped = canBeTipped;
            this.multishotLevel = multishotLevel;
            this.bowPower = bowPower;
            this.velocityMultiplier = velocityMultiplier;
        }

        @ConfigName("Added Attack Damage")
        @ConfigEntry.Gui.Tooltip
        @TooltipFrase("used for gauntlet damage and also added to melee weapon base damage")
        public double addedAttackDamage;
        @ConfigName("Arrow Damage")
        public float arrowDamage;
        @ConfigName("Flaming Arrow")
        public boolean flaming;
        @ConfigName("Can Arrow Be Tipped With Potions")
        public boolean canBeTipped;
        @ConfigEntry.BoundedDiscrete(max = 3)
        @ConfigName("Multishot Level")
        public int multishotLevel;
        @ConfigEntry.BoundedDiscrete(max = 100)
        @ConfigName("Base Power level")
        @ConfigEntry.Gui.Tooltip
        @TooltipFrase("Added to power enchantment level on the bow or crossbow")
        public int bowPower;
        @ConfigName("Arrow Velocity Multiplier")
        @ConfigEntry.Gui.Tooltip
        @TooltipFrase("used when firing a bow or crossbow")
        public float velocityMultiplier;
    }

    public static class Defense {
        public Defense(int gauntletArmorAmount, double armorToughness, double knockbackResistance, float baseProtectionAmmount, float afterBasePercentReduction) {
            this.gauntletArmorAmount = gauntletArmorAmount;
            this.armorToughness = armorToughness;
            this.knockbackResistance = knockbackResistance;
            this.baseProtectionAmmount = baseProtectionAmmount;
            this.afterBasePercentReduction = afterBasePercentReduction;
        }

        @ConfigEntry.BoundedDiscrete(max = 512)
        @ConfigName("Gauntlet Armor Amount")
        public int gauntletArmorAmount;
        @ConfigName("Armor Toughness")
        public double armorToughness;
        @ConfigName("Knockback Resistance")
        public double knockbackResistance;
        @ConfigEntry.Gui.Tooltip(count = 2)
        @ConfigName("Base Protection Amount")
        @TooltipFrase("Defines the amount of Damage a shield entirely made of this material will block")
        @TooltipFrase(line = 1, value = "Only works if PREDEFINED_AMMOUNT is selected in the Shield Protection Settings")
        public float baseProtectionAmmount;
        @ConfigEntry.Gui.Tooltip(count = 2)
        @ConfigName("After Base Percent Protection")
        @TooltipFrase("Defines the percent of Damage a shield entirely made of this material will block after the Base amount has been blocked")
        @TooltipFrase(line = 1, value = "Only works if Shield Protection Percentage is enabled in the Shield Protection Settings")
        public float afterBasePercentReduction;
    }

    public static class Crafting {
        public Crafting(List<String> repairItem, boolean isSingleAddition, List<String> onlyReplaceResource, String smithingTemplate, String craftingItem) {
            this.repairItem = repairItem;
            this.isSingleAddition = isSingleAddition;
            this.onlyReplaceResource = onlyReplaceResource;
            this.smithingTemplate = smithingTemplate;
            this.craftingItem = craftingItem;
        }

        @ConfigName("Repair Item")
        public List<String> repairItem;
        @ConfigName("Crafting Item")
        @ConfigEntry.Gui.Tooltip(count = 2)
        @TooltipFrase("If left empty will use repair item")
        @TooltipFrase(line = 1, value = "Only changes anything in a MDK when running a datagen process")
        @ConfigEntry.Gui.Excluded
        public String craftingItem;
        @ConfigName("Is Single Addition")
        public boolean isSingleAddition;
        @ConfigName("Only Replaced On Shield By This")
        public List<String> onlyReplaceResource;
        @ConfigName("Smithing Template")
        @ConfigEntry.Gui.Tooltip(count = 2)
        @TooltipFrase("1.20 feature")
        @TooltipFrase(line = 1, value = "Only Used if material is single addition or in bow crafting")
        public String smithingTemplate;
    }

    public static class Builder {
        private int toolDurability = 0;
        private int bowDurability = 0;
        private int addedShieldDurability = 0;
        private int offenseEnchantability = 0;
        private int defenseEnchantability = 0;
        private String equipSound = new ResourceLocation("item.armor.equip_generic").toString();
        private List<String> repairItem = new ArrayList<>();
        private String craftingItem = "";
        private float mendingBonus = 0;
        private boolean fireResistant = false;
        private float gauntletAttackDamage = 0;
        private float arrowDamage = 0;
        private boolean flaming = false;
        private boolean canBeTipped = true;
        private int multishotLevel = 0;
        private int bowPower = 0;
        private float velocityMultiplier = 1;
        private int gauntletArmorAmount = 0;
        private double armorToughness = 0;
        private double knockbackResistance = 0;
        private float baseProtectionAmmount = 0;
        private float afterBasePercentReduction = 0;
        private boolean isSingleAddition = false;
        private final List<String> onlyReplaceResource = new ArrayList<>();
        private String smithingTemplate = "minecraft:air";
        private int quiverSlots = 0;


        public Builder fromTier(Tier tier) {
            return this.toolDurability(tier.getUses())
                    .offenseEnchantability(tier.getEnchantmentValue())
                    .repairItem(tier.getRepairIngredient())
                    .gauntletAttackDamage(tier.getAttackDamageBonus());
        }
        public Builder fromTierNoIngredient(Tier tier) {
            return this.toolDurability(tier.getUses())
                    .offenseEnchantability(tier.getEnchantmentValue())
                    .gauntletAttackDamage(tier.getAttackDamageBonus());
        }

        public Builder fromArmorMaterial(ArmorMaterial armorMaterial) {
            return this.defenseEnchantability(armorMaterial.getEnchantmentValue())
                    .equipSound(armorMaterial.getEquipSound())
                    .repairItem(armorMaterial.getRepairIngredient())
                    .gauntletArmorAmount(armorMaterial.getDefenseForType(ArmorItem.Type.BOOTS))
                    .armorToughness(armorMaterial.getToughness())
                    .knockbackResistance(armorMaterial.getKnockbackResistance());
        }

        public Builder fromArmorMaterialNoIngredient(ArmorMaterial armorMaterial) {
            return this.defenseEnchantability(armorMaterial.getEnchantmentValue())
                    .equipSound(armorMaterial.getEquipSound())
                    .gauntletArmorAmount(armorMaterial.getDefenseForType(ArmorItem.Type.BOOTS))
                    .armorToughness(armorMaterial.getToughness())
                    .knockbackResistance(armorMaterial.getKnockbackResistance());
        }

        public Builder toolDurability(int durability) {
            this.toolDurability = durability;
            return this;
        }

        public Builder bowDurability(int durability) {
            this.bowDurability = durability;
            return this;
        }

        public Builder addedShieldDurability(int durability) {
            this.addedShieldDurability = durability;
            return this;
        }

        public Builder offenseEnchantability(int enchantability) {
            this.offenseEnchantability = enchantability;
            return this;
        }

        public Builder defenseEnchantability(int enchantability) {
            this.defenseEnchantability = enchantability;
            return this;
        }

        public Builder equipSound(String equipSound) {
            this.equipSound = equipSound;
            return this;
        }

        public Builder equipSound(ResourceLocation equipSound) {
            this.equipSound = equipSound.toString();
            return this;
        }

        public Builder equipSound(SoundEvent equipSound) {
            this.equipSound = equipSound.getLocation().toString();
            return this;
        }

        public Builder craftingItem(String item) {
            this.craftingItem = item;
            return this;
        }

        public Builder craftingItem(ResourceLocation item) {
            this.craftingItem = item.toString();
            return this;
        }

        public Builder repairItem(String... items) {
            this.repairItem = Arrays.asList(items);
            return this;
        }

        public Builder repairAddItem(String... items) {
            this.repairItem.addAll(Arrays.asList(items));
            return this;
        }

        public Builder repairItem(ResourceLocation... items) {
            this.repairItem = Arrays.stream(items).map(ResourceLocation::toString).toList();
            return this;
        }

        public Builder repairAddItem(ResourceLocation... items) {
            this.repairItem.addAll(Arrays.stream(items).map(ResourceLocation::toString).toList());
            return this;
        }

        public Builder repairItem(Ingredient ingredient) {
            this.repairItem = IngredientUtil.getItemStringFromIngrediant(ingredient);
            return this;
        }

        public Builder mendingBonus(float mendingBonus) {
            this.mendingBonus = mendingBonus;
            return this;
        }

        public Builder fireResistant() {
            this.fireResistant = true;
            return this;
        }

        public Builder gauntletAttackDamage(float damage) {
            this.gauntletAttackDamage = damage;
            return this;
        }

        public Builder arrowDamage(float damage) {
            this.arrowDamage = damage;
            return this;
        }

        public Builder flaming() {
            this.flaming = true;
            return this;
        }

        public Builder noTippedArrows() {
            this.canBeTipped = false;
            return this;
        }

        public Builder multishotLevel(int multishotLevel) {
            this.multishotLevel = multishotLevel;
            return this;
        }

        public Builder bowPower(int power) {
            this.bowPower = power;
            return this;
        }

        public Builder velocityMultiplier(float velocityMultiplier) {
            this.velocityMultiplier = velocityMultiplier;
            return this;
        }

        public Builder gauntletArmorAmount(int armor) {
            this.gauntletArmorAmount = armor;
            return this;
        }

        public Builder armorToughness(double toughness) {
            this.armorToughness = toughness;
            return this;
        }

        public Builder knockbackResistance(double resistance) {
            this.knockbackResistance = resistance;
            return this;
        }

        public Builder baseProtectionAmmount(float damage) {
            this.baseProtectionAmmount = damage;
            return this;
        }

        public Builder afterBasePercentReduction(float percent) {
            this.afterBasePercentReduction = percent;
            return this;
        }

        public Builder singleAddition() {
            this.isSingleAddition = true;
            return this;
        }

        public Builder onlyReplaceResource(String... materials) {
            this.onlyReplaceResource.addAll(Arrays.asList(materials));
            return this;
        }

        public Builder smithingTemplate(String template) {
            this.smithingTemplate = template;
            return this;
        }

        public Builder smithingTemplate(ResourceLocation template) {
            this.smithingTemplate = template.toString();
            return this;
        }

        public Builder smithingTemplate(Item template) {
            this.smithingTemplate = Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(template)).toString();
            return this;
        }

        public Builder quiverSlots(int slots) {
            this.quiverSlots = slots;
            ExpandedCombat.maxQuiverSlots = Math.max(slots, ExpandedCombat.maxQuiverSlots);
            return this;
        }

        public MaterialConfig build() {
            return new MaterialConfig(toolDurability, addedShieldDurability, bowDurability, offenseEnchantability, defenseEnchantability, equipSound, repairItem, craftingItem,
                    mendingBonus, fireResistant, gauntletAttackDamage, arrowDamage, flaming, canBeTipped, multishotLevel, bowPower,
                    velocityMultiplier, gauntletArmorAmount, armorToughness, knockbackResistance, baseProtectionAmmount, afterBasePercentReduction,
                    isSingleAddition, onlyReplaceResource, smithingTemplate, quiverSlots);
        }
    }
}
