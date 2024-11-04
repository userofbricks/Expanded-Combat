package com.userofbricks.expanded_combat.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import static com.userofbricks.expanded_combat.ExpandedCombat.modLoc;

public class ECDamageInit {
    public static final ResourceKey<DamageType> GAUNTLET_DMG = ResourceKey.create(Registries.DAMAGE_TYPE, modLoc("dmg_no_weapon"));
    public static final ResourceKey<DamageType> HEAT_DMG = ResourceKey.create(Registries.DAMAGE_TYPE, modLoc("heat_dmg"));
    public static final ResourceKey<DamageType> COLD_DMG = ResourceKey.create(Registries.DAMAGE_TYPE, modLoc("cold_dmg"));
    public static final ResourceKey<DamageType> VOID_DMG = ResourceKey.create(Registries.DAMAGE_TYPE, modLoc("void_dmg"));
    public static final ResourceKey<DamageType> SOUL_DMG = ResourceKey.create(Registries.DAMAGE_TYPE, modLoc("soul_dmg"));

    public static DamageSource gauntletAttack(Level level, @Nullable Entity attacker) {
        return new DamageSource(level.registryAccess().lookupOrThrow(Registries.DAMAGE_TYPE).getOrThrow(GAUNTLET_DMG), attacker);
    }
    public static DamageSource heatDmgAttack(Level level, @Nullable Entity attacker) {
        return new DamageSource(level.registryAccess().lookupOrThrow(Registries.DAMAGE_TYPE).getOrThrow(HEAT_DMG), attacker);
    }
    public static DamageSource coldDmgAttack(Level level, @Nullable Entity attacker) {
        return new DamageSource(level.registryAccess().lookupOrThrow(Registries.DAMAGE_TYPE).getOrThrow(COLD_DMG), attacker);
    }
    public static DamageSource voidDmgAttack(Level level, @Nullable Entity attacker) {
        return new DamageSource(level.registryAccess().lookupOrThrow(Registries.DAMAGE_TYPE).getOrThrow(VOID_DMG), attacker);
    }
    public static DamageSource soulDmgAttack(Level level, @Nullable Entity attacker) {
        return new DamageSource(level.registryAccess().lookupOrThrow(Registries.DAMAGE_TYPE).getOrThrow(SOUL_DMG), attacker);
    }
}
