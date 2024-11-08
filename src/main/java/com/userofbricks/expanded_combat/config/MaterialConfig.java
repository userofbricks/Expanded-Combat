package com.userofbricks.expanded_combat.config;

import com.userofbricks.expanded_combat.api.material.PlacementInShield;
import com.userofbricks.expanded_combat.config.gui.ConfigEntryGui;
import com.userofbricks.expanded_combat.config.gui.ConfigName;
import com.userofbricks.expanded_combat.config.gui.TooltipFrase;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.ToolMaterial;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MaterialConfig {

    @ConfigEntry.Gui.CollapsibleObject @ConfigName("Durability") public Durability durability;
    @ConfigEntry.Gui.CollapsibleObject @ConfigName("Enchanting") public Enchanting enchanting;
    @ConfigEntry.Gui.CollapsibleObject @ConfigName("Offence") public Offense offense;
    @ConfigEntry.Gui.CollapsibleObject @ConfigName("Defence") public Defense defense;
    @ConfigEntry.Gui.CollapsibleObject @ConfigName("Crafting") public Crafting crafting;

    MaterialConfig(int toolDurability, int gauntletDurability, int addedShieldDurability, int bowDurability,
                   int offenseEnchantability, int defenseEnchantability, float mendingBonus,
                   double addedAttackDamage, float arrowDamage, double defaultArrowGravity, boolean flaming, boolean canBeTipped, float velocityMultiplier, int quiverStacks,
                   int gauntletArmorAmount, double armorToughness, double knockbackResistance, ResourceLocation equipSound, boolean fireResistant, boolean makesPiglinsNeutral, PlacementInShield placementInShield, float baseProtectionAmmount, float afterBasePercentReduction,
                   boolean isSingleAddition, List<ResourceLocation> onlyReplaceResource) {
        this.durability = new Durability(toolDurability, gauntletDurability, addedShieldDurability, bowDurability);
        this.enchanting = new Enchanting(offenseEnchantability, defenseEnchantability, mendingBonus);
        this.offense = new Offense(addedAttackDamage, arrowDamage, defaultArrowGravity, flaming, canBeTipped, velocityMultiplier, quiverStacks);
        this.defense = new Defense(gauntletArmorAmount, armorToughness, knockbackResistance, equipSound, fireResistant, makesPiglinsNeutral, placementInShield, baseProtectionAmmount, afterBasePercentReduction);
        this.crafting = new Crafting(isSingleAddition, onlyReplaceResource);
    }

    public static class Durability{
        @ConfigEntry.BoundedDiscrete(max = Integer.MAX_VALUE)
        @ConfigName("Tool Durability")
        @ConfigEntry.Gui.Tooltip
        @TooltipFrase("This is used as the base durability for weapons, which is multiplied with the weapons durability multiplier to get the final durability")
        int toolBaseDurability;
        @ConfigEntry.BoundedDiscrete(max = Integer.MAX_VALUE)
        @ConfigName("Gauntlet Durability")
        int gauntletDurability;
        @ConfigEntry.BoundedDiscrete(max = Integer.MAX_VALUE)
        @ConfigName("Bow/Crossbow Durability")
        int bowCrossbowDurability;

        @ConfigEntry.BoundedDiscrete(max = Integer.MAX_VALUE)
        @ConfigName("Added Shield Durability")
        @ConfigEntry.Gui.Tooltip
        @TooltipFrase("this is the amount of durability added by each of the five sections, onto the base wood shield durability")
        int addedShieldDurability;

        /**
         * @param toolBaseDurability This is used as the base durability for weapons, which is added to the weapons durability adjustment to get the final durability
         * @param gauntletDurability This is used as the gauntlet's durability. plain and simple
         * @param bowCrossbowDurability again self-explanatory
         * @param addedShieldDurability this is the amount of durability added by each of the five sections of a shield
         */
        public Durability(
                int toolBaseDurability,
                int gauntletDurability,
                int bowCrossbowDurability,
                int addedShieldDurability) {
            this.toolBaseDurability = toolBaseDurability;
            this.gauntletDurability = gauntletDurability;
            this.bowCrossbowDurability = bowCrossbowDurability;
            this.addedShieldDurability = addedShieldDurability;
        }

        public int addedShieldDurability() {
            return addedShieldDurability;
        }

        public int bowCrossbowDurability() {
            return bowCrossbowDurability;
        }

        public int gauntletDurability() {
            return gauntletDurability;
        }

        public int toolBaseDurability() {
            return toolBaseDurability;
        }
    }

    public static class Enchanting {
        @ConfigEntry.BoundedDiscrete(max = 512)
        @ConfigName("Offence Enchantability")
        @ConfigEntry.Gui.Tooltip
        @TooltipFrase("The enchantability of weapons and half of a gauntlet's")
        int offenseEnchantability;
        @ConfigEntry.BoundedDiscrete(max = 512)
        @ConfigName("Defence Enchantability")
        @ConfigEntry.Gui.Tooltip
        @TooltipFrase("The enchantability of shields and half of a gauntlet's")
        int defenseEnchantability;
        @ConfigName("Mending Bonus")
        @ConfigEntry.Gui.Tooltip
        @TooltipFrase("Added to the vanilla base mending multiplier of 2")
        float mendingBonus;

        public int offenseEnchantability() {
            return offenseEnchantability;
        }

        public int defenseEnchantability() {
            return defenseEnchantability;
        }

        public float mendingBonus() {
            return mendingBonus;
        }

        /**
         * @param offenseEnchantability the enchantability of weapons and half of a gauntlets
         * @param defenseEnchantability the enchantability of shields and other half of gauntlets
         * @param mendingBonus added to the vanilla base mending multiplier of 2
         */
        public Enchanting(
                int offenseEnchantability,
                int defenseEnchantability,
                float mendingBonus) {
            this.offenseEnchantability = offenseEnchantability;
            this.defenseEnchantability = defenseEnchantability;
            this.mendingBonus = mendingBonus;
        }
    }

    public static class Offense {
        @ConfigName("Added Attack Damage")
        @ConfigEntry.Gui.Tooltip
        @TooltipFrase("used for gauntlet damage and also added to melee weapon base damage")
        double addedAttackDamage;
        @ConfigName("Arrow Damage")
        float arrowDamage;
        @ConfigName("Arrow Gravity")
        double defaultArrowGravity;
        @ConfigName("Flaming Arrow")
        boolean flaming;
        @ConfigName("Can Arrow Be Tipped With Potions")
        boolean canBeTipped;
        @ConfigEntry.BoundedDiscrete(max = 100)
        @ConfigName("Arrow Velocity Multiplier")
        @ConfigEntry.Gui.Tooltip
        @TooltipFrase("used when firing a bow or crossbow")
        float velocityMultiplier;
        @ConfigName("QuiverStacks")
        @ConfigEntry.BoundedDiscrete(min = 1, max = 64)
        int quiverStacks;

        /**
         * @param addedAttackDamage used for gauntlet damage and also added to melee weapon base damage
         * @param arrowDamage the Damage an arrow of the material does
         * @param flaming weather the arrow for the material act like it has the flame enchantment all the time
         * @param canBeTipped weather the arrow can be tipped with potions
         * @param velocityMultiplier multiplies the base velocity of an arrow when shot from the bow or crossbow of the material
         * @param quiverStacks how many stacks of arrows the quiver can hold
         */
        public Offense(
                double addedAttackDamage,
                float arrowDamage,
                double defaultArrowGravity,
                boolean flaming,
                boolean canBeTipped,
                float velocityMultiplier,
                int quiverStacks) {
            this.addedAttackDamage = addedAttackDamage;
            this.arrowDamage = arrowDamage;
            this.defaultArrowGravity = defaultArrowGravity;
            this.flaming = flaming;
            this.canBeTipped = canBeTipped;
            this.velocityMultiplier = velocityMultiplier;
            this.quiverStacks = quiverStacks;
        }

        public double addedAttackDamage() {
            return addedAttackDamage;
        }

        public float arrowDamage() {
            return arrowDamage;
        }

        public double defaultArrowGravity() {
            return defaultArrowGravity;
        }

        public boolean flaming() {
            return flaming;
        }

        @SuppressWarnings("unused")
        public boolean canBeTipped() {
            return canBeTipped;
        }

        public float velocityMultiplier() {
            return velocityMultiplier;
        }

        public int quiverStacks() {
            return quiverStacks;
        }
    }

    public static class Defense {
        @ConfigName("Gauntlet Armor Amount") @ConfigEntry.BoundedDiscrete(max = 512) int gauntletArmorAmount;
        @ConfigName("Armor Toughness") double armorToughness;
        @ConfigName("Knockback Resistance") double knockbackResistance;
        @ConfigName("Equip Sound") @ConfigEntryGui.SoundEvent ResourceLocation equipSound;
        @ConfigName("Fire Resistant") boolean fireResistant;
        @ConfigName("Makes Piglins Neutral") boolean makesPiglinsNeutral;
        @ConfigName("Placement in Shield")
        @ConfigEntry.Gui.EnumHandler
        @ConfigEntry.Gui.Tooltip
        @TooltipFrase("this is the possible locations in a shield that this material can be placed when crafting")
        PlacementInShield placementInShield;
        @ConfigName("Base Protection Amount")
        @ConfigEntry.Gui.Tooltip(count = 2)
        @TooltipFrase("Defines the amount of Damage a shield entirely made of this material will block")
        @TooltipFrase(line = 1, value = "Only works if PREDEFINED_AMMOUNT is selected in the Shield Protection Settings")
        float baseProtectionAmmount;
        @ConfigEntry.Gui.Tooltip(count = 2)
        @ConfigName("After Base Percent Protection")
        @TooltipFrase("Defines the percent of Damage a shield entirely made of this material will block after the Base amount has been blocked")
        @TooltipFrase(line = 1, value = "Only works if Shield Protection Percentage is enabled in the Shield Protection Settings")
        float afterBasePercentReduction;

        /**
         * @param gauntletArmorAmount the amount of armor the gauntlet has
         * @param armorToughness how tough the armor provided by the gauntlet is
         * @param knockbackResistance how resistant to knockback the gauntlet makes you
         * @param equipSound used when a gauntlet is equipped
         * @param fireResistant weather it acts like netherite in lava and fire or not
         * @param placementInShield this is the possible locations in a shield that this material can be placed when crafting
         * @param baseProtectionAmmount Defines the amount of Damage a shield entirely made of this material will block. Only works if PREDEFINED_AMMOUNT is selected in the Shield Protection Settings.
         * @param afterBasePercentReduction Defines the percent of Damage a shield entirely made of this material will block after the Base amount has been blocked. Only works if Shield Protection Percentage is enabled in the Shield Protection Settings
         */
        public Defense(int gauntletArmorAmount, double armorToughness, double knockbackResistance, ResourceLocation equipSound, boolean fireResistant, boolean makesPiglinsNeutral,
                       PlacementInShield placementInShield,
                       float baseProtectionAmmount,
                       float afterBasePercentReduction) {
            this.gauntletArmorAmount = gauntletArmorAmount;
            this.armorToughness = armorToughness;
            this.knockbackResistance = knockbackResistance;
            this.equipSound = equipSound;
            this.fireResistant = fireResistant;
            this.makesPiglinsNeutral = makesPiglinsNeutral;
            this.placementInShield = placementInShield;
            this.baseProtectionAmmount = baseProtectionAmmount;
            this.afterBasePercentReduction = afterBasePercentReduction;
        }

        public int gauntletArmorAmount() {
            return gauntletArmorAmount;
        }

        public double armorToughness() {
            return armorToughness;
        }

        public double knockbackResistance() {
            return knockbackResistance;
        }

        public ResourceLocation equipSound() {
            return equipSound;
        }

        public boolean fireResistant() {
            return fireResistant;
        }

        public boolean makesPiglinsNeutral() {
            return makesPiglinsNeutral;
        }

        public PlacementInShield placementInShield() {
            return placementInShield;
        }

        public float baseProtectionAmmount() {
            return baseProtectionAmmount;
        }

        public float afterBasePercentReduction() {
            return afterBasePercentReduction;
        }
    }

    public static class Crafting {
            @ConfigName("Single Addition")
            @ConfigEntry.Gui.Tooltip
            @TooltipFrase("Weather this material is applied to every part of a shield\nwith one item verses using one for each of the five parts")
            boolean isSingleAddition;
            @ConfigName("Can Replace Materials")
            @ConfigEntry.Gui.Tooltip
            @TooltipFrase("A list of what materials this one can replace on a shield.\nLeave empty if it can replace anything")
            @ConfigEntryGui.Material
            List<ResourceLocation> onlyReplaceResource;


        /**
         * @param isSingleAddition weather this material is applied to every part of a shield with one item verses using one for each of the five parts
         * @param onlyReplaceResource a list of what materials this one can replace on a shield
         */
        public Crafting(
                boolean isSingleAddition,
                List<ResourceLocation> onlyReplaceResource) {
            this.isSingleAddition = isSingleAddition;
            this.onlyReplaceResource = onlyReplaceResource;
        }

        public boolean isSingleAddition() {
            return isSingleAddition;
        }

        public List<ResourceLocation> onlyReplaceResource() {
            return onlyReplaceResource;
        }
    }

    @SuppressWarnings("unused")
    public static class Builder {
        private int toolDurability = 0;
        private int gauntletDurability = 0;
        private int bowDurability = 0;
        private int addedShieldDurability = 0;

        private int offenseEnchantability = 0;
        private int defenseEnchantability = 0;
        private float mendingBonus = 0;

        private float addedAttackDamage = 0;
        private float arrowDamage = 0;
        private double defaultArrowGravity = 0.05;
        private boolean flaming = false;
        private boolean canBeTipped = true;
        private float velocityMultiplier = 1;
        private int quiverStacks = 0;

        private int gauntletArmorAmount = 0;
        private double armorToughness = 0;
        private double knockbackResistance = 0;
        private ResourceLocation equipSound = ResourceLocation.parse("item.armor.equip_generic");
        private boolean fireResistant = false;
        private boolean makesPiglinsNeutral = false;
        private PlacementInShield placementInShield = PlacementInShield.NONE;
        private float baseProtectionAmmount = 0;
        private float afterBasePercentReduction = 0;

        private boolean isSingleAddition = false;
        private final List<ResourceLocation> onlyReplaceResource = new ArrayList<>();

        public Builder fromTierNoIngredient(ToolMaterial tier) {
            return this.toolDurability(tier.durability())
                    .gauntletDurability(tier.durability())
                    .offenseEnchantability(tier.enchantmentValue())
                    .addedAttackDamage(tier.attackDamageBonus());
        }

        public Builder toolDurability(int durability) {
            this.toolDurability = durability;
            return this;
        }

        public Builder gauntletDurability(int durability) {
            this.gauntletDurability = durability;
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
            this.equipSound = ResourceLocation.parse(equipSound);
            return this;
        }

        public Builder equipSound(ResourceLocation equipSound) {
            this.equipSound = equipSound;
            return this;
        }

        public Builder equipSound(SoundEvent equipSound) {
            this.equipSound = equipSound.location();
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

        public Builder makesPiglinsNeutral() {
            this.makesPiglinsNeutral = true;
            return this;
        }

        public Builder shield(PlacementInShield placementInShield) {
            this.placementInShield = placementInShield;
            return this;
        }

        public Builder shield() {
            this.placementInShield = PlacementInShield.ALL;
            return this;
        }

        public Builder addedAttackDamage(float damage) {
            this.addedAttackDamage = damage;
            return this;
        }

        public Builder arrowDamage(float damage) {
            this.arrowDamage = damage;
            return this;
        }

        public Builder arrowGravity(double defaultArrowGravity) {
            this.defaultArrowGravity = defaultArrowGravity;
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
            this.onlyReplaceResource.addAll(Arrays.stream(materials).map(ResourceLocation::parse).toList());
            return this;
        }

        public Builder onlyReplaceResource(ResourceLocation... materials) {
            this.onlyReplaceResource.addAll(Arrays.asList(materials));
            return this;
        }

        public Builder quiverSlots(int slots) {
            this.quiverStacks = slots;
            return this;
        }

        public MaterialConfig build() {
            return new MaterialConfig(toolDurability, gauntletDurability, addedShieldDurability, bowDurability,
                    offenseEnchantability, defenseEnchantability, mendingBonus,
                    addedAttackDamage, arrowDamage, defaultArrowGravity, flaming, canBeTipped, velocityMultiplier, quiverStacks,
                    gauntletArmorAmount, armorToughness, knockbackResistance, equipSound, fireResistant, makesPiglinsNeutral, placementInShield, baseProtectionAmmount, afterBasePercentReduction,
                    isSingleAddition, onlyReplaceResource);
        }
    }
}
