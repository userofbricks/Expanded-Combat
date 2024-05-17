package com.userofbricks.expanded_combat.data.material;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;
import java.util.Optional;

/**
 * @param durabilities {@link Durabilities Durabilities}
 * @param enchantingRelated {@link EnchantingRelated EnchantingRelated}
 * @param repairItem a list of items that can be used to repair items of the material in an anvil
 * @param isSingleAddition weather this material is applied to every part of a shield with one item verses using one for each of the five parts
 * @param onlyReplaceResource a list of what materials this one can replace on a shield
 * @param smithingTemplate the smithing template used for single addition materials
 */
public record Material(
        Durabilities durabilities,
        EnchantingRelated enchantingRelated,
        Offense offense,
        Defense defense,
        Ingredient repairItem,
        boolean isSingleAddition,
        Optional<List<ResourceLocation>> onlyReplaceResource,
        ResourceLocation smithingTemplate,
        ItemGeneration itemGeneration

) {
    public static final Codec<Material> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Durabilities.CODEC.optionalFieldOf("durability", Durabilities.DEFAULT).forGetter(Material::durabilities),
                    EnchantingRelated.CODEC.optionalFieldOf("enchant_related", EnchantingRelated.DEFAULT).forGetter(Material::enchantingRelated),
                    Offense.CODEC.optionalFieldOf("offence", Offense.DEFAULT).forGetter(Material::offense),
                    Defense.CODEC.optionalFieldOf("defence", Defense.DEFAULT).forGetter(Material::defense),
                    Ingredient.CODEC.fieldOf("repair_item").forGetter(Material::repairItem),
                    Codec.BOOL.optionalFieldOf("is_single_addition", false).forGetter(Material::isSingleAddition),
                    Codec.optionalField("only_replace", ResourceLocation.CODEC.listOf(), false).forGetter(Material::onlyReplaceResource),
                    ResourceLocation.CODEC.optionalFieldOf("smithing_template", new ResourceLocation("air")).forGetter(Material::smithingTemplate),
                    ItemGeneration.CODEC.fieldOf("auto_generate").forGetter(Material::itemGeneration)
            ).apply(instance, Material::new)
    );
    public static final Codec<Material> CODEC_CLIENT_SYNC = CODEC;

    /**
     * @param toolBaseDurability This is used as the base durability for weapons, which is added to the weapons durability adjustment to get the final durability
     * @param gauntletDurability This is used as the gauntlet's durability. plain and simple
     * @param bowCrossbowDurability again self-explanatory
     * @param addedShieldDurability this is the amount of durability added by each of the five sections of a shield
     */
    public record Durabilities(int toolBaseDurability, int gauntletDurability, int bowCrossbowDurability, int addedShieldDurability) {
        public static final Durabilities DEFAULT = new Durabilities(1,1,1,1);
        public static final Codec<Durabilities> CODEC = RecordCodecBuilder.create(
                instance -> instance.group(
                                Codec.intRange(1, Integer.MAX_VALUE).optionalFieldOf("tool_base", 1).forGetter(Durabilities::toolBaseDurability),
                                Codec.intRange(1, Integer.MAX_VALUE).optionalFieldOf("gauntlet", 1).forGetter(Durabilities::gauntletDurability),
                                Codec.intRange(1, Integer.MAX_VALUE).optionalFieldOf("bow_and_crossbow", 1).forGetter(Durabilities::bowCrossbowDurability),
                                Codec.intRange(1, Integer.MAX_VALUE).optionalFieldOf("added_to_shield", 1).forGetter(Durabilities::addedShieldDurability)
                )
                .apply(instance, Durabilities::new)
        );
    }

    /**
     * @param offenseEnchantability the enchantability of weapons and half of a gauntlets
     * @param defenseEnchantability the enchantability of shields and other half of gauntlets
     * @param mendingBonus added to the vanilla base mending multiplier of 2
     */
    public record EnchantingRelated(int offenseEnchantability, int defenseEnchantability, float mendingBonus) {
        public static final EnchantingRelated DEFAULT = new EnchantingRelated(0,0,0f);
        public static final Codec<EnchantingRelated> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Codec.INT.optionalFieldOf("offense_enchantability", 0).forGetter(EnchantingRelated::offenseEnchantability),
                    Codec.INT.optionalFieldOf("defense_enchantability", 0).forGetter(EnchantingRelated::defenseEnchantability),
                    Codec.FLOAT.optionalFieldOf("mending_bonus", 0f).forGetter(EnchantingRelated::mendingBonus)
            )
            .apply(instance, EnchantingRelated::new)
        );
    }

    /**
     * @param addedAttackDamage used for gauntlet damage and also added to melee weapon base damage
     * @param arrowDamage the Damage an arrow of the material does
     * @param flaming weather the arrow for the material act like it has the flame enchantment all the time
     * @param canBeTipped weather the arrow can be tipped with potions
     * @param multishotLevel what level of multishot the bow and crossbow naturally have
     * @param bowPower what level of power the bow and crossbow naturally have
     * @param velocityMultiplier multiplies the base velocity of an arrow when shot from the bow or crossbow of the material
     */
    public record Offense(double addedAttackDamage, float arrowDamage, boolean flaming, boolean canBeTipped, int multishotLevel, int bowPower, float velocityMultiplier) {
        public static final Offense DEFAULT = new Offense(0,0,false, true, 0, 0, 1f);
        public static final Codec<Offense> CODEC = RecordCodecBuilder.create(
                instance -> instance.group(
                        Codec.doubleRange(0d, Double.MAX_VALUE).optionalFieldOf("added_attack_damage", 0d).forGetter(Offense::addedAttackDamage),
                        Codec.floatRange(0, Float.MAX_VALUE).optionalFieldOf("arrow_damage", 0f).forGetter(Offense::arrowDamage),
                        Codec.BOOL.optionalFieldOf("flaming_arrow", false).forGetter(Offense::flaming),
                        Codec.BOOL.optionalFieldOf("can_be_tipped", true).forGetter(Offense::canBeTipped),
                        //move multishot to its own bow/crossbow type(s) or json value for type
                        Codec.intRange(0, 3).optionalFieldOf("base_multishot_level", 0).forGetter(Offense::multishotLevel),
                        //move power to its own bow/crossbow type(s) or json value for type
                        Codec.intRange(0, 100).optionalFieldOf("base_bow_power", 0).forGetter(Offense::bowPower),
                        //might want to change to an arrow gravity value that gets set by the bow (in other words how strait the arrow flies)
                        Codec.FLOAT.optionalFieldOf("arrow_velocity_multiplier", 0f).forGetter(Offense::velocityMultiplier)
                )
                .apply(instance, Offense::new)
        );
    }

    /**
     * @param placementInShield this is the possible locations in a shield that this material can be placed when crafting
     * @param equipSound used when a gauntlet is equipped
     * @param fireResistant weather it acts like netherite in lava and fire or not
     * @param gauntletArmorAmount the amount of armor the gauntlet has
     * @param armorToughness how tough the armor provided by the gauntlet is
     * @param knockbackResistance how resistant to knockback the gauntlet makes you
     * @param baseProtectionAmmount Defines the amount of Damage a shield entirely made of this material will block. Only works if PREDEFINED_AMMOUNT is selected in the Shield Protection Settings.
     * @param afterBasePercentReduction Defines the percent of Damage a shield entirely made of this material will block after the Base amount has been blocked. Only works if Shield Protection Percentage is enabled in the Shield Protection Settings
     */
    public record Defense(PlacementInShield placementInShield, ResourceLocation equipSound, boolean fireResistant, boolean makesPiglinsNeutral, int gauntletArmorAmount, double armorToughness,
                          double knockbackResistance, float baseProtectionAmmount, float afterBasePercentReduction) {
        public static final Defense DEFAULT = new Defense(PlacementInShield.ALL, new ResourceLocation("item.armor.equip_generic"), false, false, 0, 0d, 0d, 0f, 0f);
        public static final Codec<Defense> CODEC = RecordCodecBuilder.create(
                instance -> instance.group(
                        PlacementInShield.CODEC.optionalFieldOf("placement_in_shield", PlacementInShield.ALL).forGetter(Defense::placementInShield),
                        ResourceLocation.CODEC.optionalFieldOf("equip_sound", new ResourceLocation("item.armor.equip_generic")).forGetter(Defense::equipSound),
                        Codec.BOOL.optionalFieldOf("fire_resistant", false).forGetter(Defense::fireResistant),
                        Codec.BOOL.optionalFieldOf("makes_piglins_neutral", false).forGetter(Defense::makesPiglinsNeutral),
                        Codec.intRange(0, 128).optionalFieldOf("gauntlet_armor", 0).forGetter(Defense::gauntletArmorAmount),
                        Codec.DOUBLE.optionalFieldOf("armor_toughness", 0d).forGetter(Defense::armorToughness),
                        Codec.DOUBLE.optionalFieldOf("knockback_resistance", 0d).forGetter(Defense::knockbackResistance),
                        Codec.floatRange(0, Float.MAX_VALUE).optionalFieldOf("shield_base_damage_protection", 0f).forGetter(Defense::baseProtectionAmmount),
                        Codec.floatRange(0f, 1f).optionalFieldOf("shield_protection_percentage", 0f).forGetter(Defense::afterBasePercentReduction)
                )
                .apply(instance, Defense::new)
        );
    }
}
