package com.userofbricks.expanded_combat.data.weapon_type;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.ExtraCodecs;

/**
 * @param potionDippable weather the hardcoded weapon dipping crafting recipe will accept this weapon type (should probably move this to an item tag)
 * @param durabilityMultiplier multiplies the durability provided by the material of the weapon
 * @param baseAttackDamage Material tool damage is added to this
 * @param attackSpeed
 * @param mendingBonus added to the vanilla mending bonus of 2
 * @param knockback
 * @param attackRange The range in Blocks
 * @param gripType
 */
public record WeaponType(
        boolean potionDippable,
        double durabilityMultiplier,
        int baseAttackDamage,
        float attackSpeed,
        float mendingBonus,
        float knockback,
        double attackRange,
        GripType gripType
) {
    public static final Codec<WeaponType> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Codec.BOOL.optionalFieldOf("potion_dippable", false).forGetter(WeaponType::potionDippable),
                    Codec.doubleRange(0.1d, 10d).optionalFieldOf("durability_multiplier", 1d).forGetter(WeaponType::durabilityMultiplier),
                    ExtraCodecs.POSITIVE_INT.fieldOf("base_attack_damage").forGetter(WeaponType::baseAttackDamage),
                    Codec.FLOAT.fieldOf("attack_speed").forGetter(WeaponType::attackSpeed),
                    Codec.FLOAT.optionalFieldOf("mending_bonus", 0f).forGetter(WeaponType::mendingBonus),
                    Codec.FLOAT.optionalFieldOf("knockback", 0f).forGetter(WeaponType::knockback),
                    Codec.DOUBLE.optionalFieldOf("attack_range", 0d).forGetter(WeaponType::attackRange),
                    GripType.CODEC.optionalFieldOf("grip_type", GripType.ONEHANDED).forGetter(WeaponType::gripType)
            ).apply(instance, WeaponType::new)
    );
    public static final Codec<WeaponType> CODEC_CLIENT_SYNC = CODEC;
}
