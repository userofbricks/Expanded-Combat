package com.userofbricks.expanded_combat.data.material;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.userofbricks.expanded_combat.init.Registries;
import io.netty.buffer.ByteBuf;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.Arrays;
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
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public record Material(
        Durabilities durabilities,
        EnchantingRelated enchantingRelated,
        Offense offense,
        Defense defense,
        Ingredient repairItem,
        boolean isSingleAddition,
        Optional<List<ResourceLocation>> onlyReplaceResource,
        Ingredient smithingTemplate

) {

    public static final Codec<Material> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Durabilities.CODEC.optionalFieldOf("durability", Durabilities.DEFAULT).forGetter(Material::durabilities),
                    EnchantingRelated.CODEC.optionalFieldOf("enchant_related", EnchantingRelated.DEFAULT).forGetter(Material::enchantingRelated),
                    Offense.CODEC.optionalFieldOf("offence", Offense.DEFAULT).forGetter(Material::offense),
                    Defense.CODEC.optionalFieldOf("defence", Defense.DEFAULT).forGetter(Material::defense),
                    Ingredient.CODEC.optionalFieldOf("repair_item", Ingredient.of(Items.AIR)).forGetter(Material::repairItem),
                    Codec.BOOL.optionalFieldOf("is_single_addition", false).forGetter(Material::isSingleAddition),
                    Codec.optionalField("only_replace", ResourceLocation.CODEC.listOf(), false).forGetter(Material::onlyReplaceResource),
                    Ingredient.CODEC.optionalFieldOf("smithing_template", Ingredient.of(Items.AIR)).forGetter(Material::smithingTemplate)
            ).apply(instance, Material::new)
    );
    public static final StreamCodec<RegistryFriendlyByteBuf, Material> STREAM_CODEC = new StreamCodec<>() {
        final StreamCodec<ByteBuf, Optional<List<ResourceLocation>>> REPLACE_STREAM_CODEC = ByteBufCodecs.optional(ByteBufCodecs.collection(ArrayList::new, ResourceLocation.STREAM_CODEC).map(
                arrayList -> Arrays.asList(arrayList.toArray(new ResourceLocation[0])), list -> (ArrayList<ResourceLocation>) list
        ));

        @Override
        public Material decode(RegistryFriendlyByteBuf pBuffer) {
            Durabilities durabilities = Durabilities.STREAM_CODEC.decode(pBuffer);
            EnchantingRelated enchantingRelated = EnchantingRelated.STREAM_CODEC.decode(pBuffer);
            Offense offense = Offense.STREAM_CODEC.decode(pBuffer);
            Defense defense = Defense.STREAM_CODEC.decode(pBuffer);
            Ingredient repairItem = Ingredient.CONTENTS_STREAM_CODEC.decode(pBuffer);
            boolean isSingleAddition = ByteBufCodecs.BOOL.decode(pBuffer);
            Optional<List<ResourceLocation>> onlyReplaceResource = REPLACE_STREAM_CODEC.decode(pBuffer);
            Ingredient smithingTemplate = Ingredient.CONTENTS_STREAM_CODEC.decode(pBuffer);
            return new Material(durabilities, enchantingRelated, offense, defense, repairItem, isSingleAddition, onlyReplaceResource, smithingTemplate);
        }

        @Override
        public void encode(RegistryFriendlyByteBuf pBuffer, Material pValue) {
            Durabilities.STREAM_CODEC.encode(pBuffer, pValue.durabilities);
            EnchantingRelated.STREAM_CODEC.encode(pBuffer, pValue.enchantingRelated);
            Offense.STREAM_CODEC.encode(pBuffer, pValue.offense);
            Defense.STREAM_CODEC.encode(pBuffer, pValue.defense);
            Ingredient.CONTENTS_STREAM_CODEC.encode(pBuffer, pValue.repairItem);
            ByteBufCodecs.BOOL.encode(pBuffer, pValue.isSingleAddition);
            REPLACE_STREAM_CODEC.encode(pBuffer, pValue.onlyReplaceResource);
            Ingredient.CONTENTS_STREAM_CODEC.encode(pBuffer, pValue.smithingTemplate);
        }
    };
    public static final Codec<Material> HOLDER_CODEC2 = Registries.MATERIAL_REGISTRY.byNameCodec();
    public static final StreamCodec<RegistryFriendlyByteBuf, Material> HOLDER_STREAM_CODEC2 = ByteBufCodecs.registry(Registries.MATERIAL_REGISTRY_KEY);

    public static final Codec<Holder<Material>> HOLDER_CODEC = RegistryFileCodec.create(Registries.MATERIAL_REGISTRY_KEY, CODEC);
    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<Material>> HOLDER_STREAM_CODEC = ByteBufCodecs.holder(
            Registries.MATERIAL_REGISTRY_KEY, STREAM_CODEC
    );

    public Material(
            Durabilities durabilities,
            EnchantingRelated enchantingRelated,
            Offense offense,
            Defense defense,
            Ingredient repairItem) {
        this(durabilities, enchantingRelated, offense, defense, repairItem, false, Optional.empty(), Ingredient.of(Items.AIR));
    }

    /**
     * @param toolBaseDurability This is used as the base durability for weapons, which is added to the weapons durability adjustment to get the final durability
     * @param gauntletDurability This is used as the gauntlet's durability. plain and simple
     * @param bowCrossbowDurability again self-explanatory
     * @param addedShieldDurability this is the amount of durability added by each of the five sections of a shield
     */
    public record Durabilities(int toolBaseDurability, int gauntletDurability, int bowCrossbowDurability, int addedShieldDurability) {
        public static final Durabilities DEFAULT = new Durabilities(0,0,0,0);
        public static final Codec<Durabilities> CODEC = RecordCodecBuilder.create(
                instance -> instance.group(
                                Codec.intRange(1, Integer.MAX_VALUE).optionalFieldOf("tool_base", 0).forGetter(Durabilities::toolBaseDurability),
                                Codec.intRange(1, Integer.MAX_VALUE).optionalFieldOf("gauntlet", 0).forGetter(Durabilities::gauntletDurability),
                                Codec.intRange(1, Integer.MAX_VALUE).optionalFieldOf("bow_and_crossbow", 0).forGetter(Durabilities::bowCrossbowDurability),
                                Codec.intRange(1, Integer.MAX_VALUE).optionalFieldOf("added_to_shield", 0).forGetter(Durabilities::addedShieldDurability)
                )
                .apply(instance, Durabilities::new)
        );
        public static final StreamCodec<RegistryFriendlyByteBuf, Durabilities> STREAM_CODEC = StreamCodec.composite(
                ByteBufCodecs.INT,
                Durabilities::toolBaseDurability,
                ByteBufCodecs.INT,
                Durabilities::gauntletDurability,
                ByteBufCodecs.INT,
                Durabilities::bowCrossbowDurability,
                ByteBufCodecs.INT,
                Durabilities::addedShieldDurability,
                Durabilities::new
        );
        public static Durabilities shieldGauntlet(int gauntletDurability, int addedShieldDurability) {
            return new Durabilities(0, gauntletDurability, 0, addedShieldDurability);
        }
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
        public static final StreamCodec<RegistryFriendlyByteBuf, EnchantingRelated> STREAM_CODEC = StreamCodec.composite(
                ByteBufCodecs.INT,
                EnchantingRelated::offenseEnchantability,
                ByteBufCodecs.INT,
                EnchantingRelated::defenseEnchantability,
                ByteBufCodecs.FLOAT,
                EnchantingRelated::mendingBonus,
                EnchantingRelated::new
        );
    }

    /**
     * @param addedAttackDamage used for gauntlet damage and also added to melee weapon base damage
     * @param arrowDamage the Damage an arrow of the material does
     * @param flaming weather the arrow for the material act like it has the flame enchantment all the time
     * @param canBeTipped weather the arrow can be tipped with potions
     * @param velocityMultiplier multiplies the base velocity of an arrow when shot from the bow or crossbow of the material
     */
    public record Offense(double addedAttackDamage, float arrowDamage, double defaultArrowGravity, boolean flaming, boolean canBeTipped, float velocityMultiplier, int quiverSlots) {
        public static final Offense DEFAULT = new Offense(0,0, 0.05,false, true, 1f, 0);
        public static final Codec<Offense> CODEC = RecordCodecBuilder.create(
                instance -> instance.group(
                        Codec.doubleRange(0d, Double.MAX_VALUE).optionalFieldOf("added_attack_damage", 0d).forGetter(Offense::addedAttackDamage),
                        Codec.floatRange(0, Float.MAX_VALUE).optionalFieldOf("arrow_damage", 0f).forGetter(Offense::arrowDamage),
                        Codec.doubleRange(0d, Double.MAX_VALUE).optionalFieldOf("default_arrow_gravity", 0.05d).forGetter(Offense::defaultArrowGravity),
                        Codec.BOOL.optionalFieldOf("flaming_arrow", false).forGetter(Offense::flaming),
                        Codec.BOOL.optionalFieldOf("can_be_tipped", true).forGetter(Offense::canBeTipped),
                        //might want to change to an arrow gravity value that gets set by the bow (in other words how strait the arrow flies)
                        Codec.FLOAT.optionalFieldOf("arrow_velocity_multiplier", 0f).forGetter(Offense::velocityMultiplier),
                        Codec.intRange(0, 32).optionalFieldOf("quiver_slots", 0).forGetter(Offense::quiverSlots)
                )
                .apply(instance, Offense::new)
        );
        public static final StreamCodec<RegistryFriendlyByteBuf, Offense> STREAM_CODEC = new StreamCodec<>() {
            @Override
            public Offense decode(RegistryFriendlyByteBuf pBuffer) {
                return new Offense(
                        ByteBufCodecs.DOUBLE.decode(pBuffer),
                        ByteBufCodecs.FLOAT.decode(pBuffer),
                        ByteBufCodecs.DOUBLE.decode(pBuffer),
                        ByteBufCodecs.BOOL.decode(pBuffer),
                        ByteBufCodecs.BOOL.decode(pBuffer),
                        ByteBufCodecs.FLOAT.decode(pBuffer),
                        ByteBufCodecs.INT.decode(pBuffer)
                );
            }

            @Override
            public void encode(RegistryFriendlyByteBuf pBuffer, Offense pValue) {
                ByteBufCodecs.DOUBLE.encode(pBuffer, pValue.addedAttackDamage);
                ByteBufCodecs.FLOAT.encode(pBuffer, pValue.arrowDamage);
                ByteBufCodecs.DOUBLE.encode(pBuffer, pValue.defaultArrowGravity);
                ByteBufCodecs.BOOL.encode(pBuffer, pValue.flaming);
                ByteBufCodecs.BOOL.encode(pBuffer, pValue.canBeTipped);
                ByteBufCodecs.FLOAT.encode(pBuffer, pValue.velocityMultiplier);
                ByteBufCodecs.INT.encode(pBuffer, pValue.quiverSlots);
            }
        };
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
        public static final Defense DEFAULT = new Defense(PlacementInShield.NONE, new ResourceLocation("item.armor.equip_generic"), false, false, 0, 0d, 0d, 0f, 0f);
        public static final Codec<Defense> CODEC = RecordCodecBuilder.create(
                instance -> instance.group(
                        PlacementInShield.CODEC.optionalFieldOf("placement_in_shield", PlacementInShield.NONE).forGetter(Defense::placementInShield),
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
        public static final StreamCodec<RegistryFriendlyByteBuf, Defense> STREAM_CODEC = new StreamCodec<>() {
            @Override
            public Defense decode(RegistryFriendlyByteBuf pBuffer) {
                return new Defense(
                        PlacementInShield.STREAM_CODEC.decode(pBuffer),
                        ResourceLocation.STREAM_CODEC.decode(pBuffer),
                        ByteBufCodecs.BOOL.decode(pBuffer),
                        ByteBufCodecs.BOOL.decode(pBuffer),
                        ByteBufCodecs.INT.decode(pBuffer),
                        ByteBufCodecs.DOUBLE.decode(pBuffer),
                        ByteBufCodecs.DOUBLE.decode(pBuffer),
                        ByteBufCodecs.FLOAT.decode(pBuffer),
                        ByteBufCodecs.FLOAT.decode(pBuffer)
                );
            }

            @Override
            public void encode(RegistryFriendlyByteBuf pBuffer, Defense pValue) {
                PlacementInShield.STREAM_CODEC.encode(pBuffer, pValue.placementInShield);
                ResourceLocation.STREAM_CODEC.encode(pBuffer, pValue.equipSound);
                ByteBufCodecs.BOOL.encode(pBuffer, pValue.fireResistant);
                ByteBufCodecs.BOOL.encode(pBuffer, pValue.makesPiglinsNeutral);
                ByteBufCodecs.INT.encode(pBuffer, pValue.gauntletArmorAmount);
                ByteBufCodecs.DOUBLE.encode(pBuffer, pValue.armorToughness);
                ByteBufCodecs.DOUBLE.encode(pBuffer, pValue.knockbackResistance);
                ByteBufCodecs.FLOAT.encode(pBuffer, pValue.baseProtectionAmmount);
                ByteBufCodecs.FLOAT.encode(pBuffer, pValue.afterBasePercentReduction);
            }
        };

        public Defense(PlacementInShield placementInShield, boolean fireResistant, boolean makesPiglinsNeutral, int gauntletArmorAmount, double armorToughness,
                       double knockbackResistance, float baseProtectionAmmount, float afterBasePercentReduction) {
            this(placementInShield, DEFAULT.equipSound, fireResistant, makesPiglinsNeutral, gauntletArmorAmount, armorToughness, knockbackResistance, baseProtectionAmmount, afterBasePercentReduction);
        }
    }
}
