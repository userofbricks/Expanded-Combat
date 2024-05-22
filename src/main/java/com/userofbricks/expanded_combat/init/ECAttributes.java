package com.userofbricks.expanded_combat.init;

import com.userofbricks.expanded_combat.ExpandedCombat;
import com.userofbricks.expanded_combat.entity.attributes.RangedDamageAttribute;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.userofbricks.expanded_combat.ExpandedCombat.modLoc;

public class ECAttributes {
    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(BuiltInRegistries.ATTRIBUTE, ExpandedCombat.MODID);

    public static final DeferredHolder<Attribute, RangedDamageAttribute> GAUNTLET_DMG_WITHOUT_WEAPON = ATTRIBUTES.register("dmg_no_weapon", () -> new RangedDamageAttribute(modLoc("dmg_no_weapon"), 0, 2048));
    public static final DeferredHolder<Attribute, RangedDamageAttribute> HEAT_DMG = ATTRIBUTES.register("heat_dmg", () -> new RangedDamageAttribute(modLoc("heat_dmg"), 0, 2048));
    public static final DeferredHolder<Attribute, RangedDamageAttribute> COLD_DMG = ATTRIBUTES.register("cold_dmg", () -> new RangedDamageAttribute(modLoc("cold_dmg"), 0, 2048));
    public static final DeferredHolder<Attribute, RangedDamageAttribute> VOID_DMG = ATTRIBUTES.register("void_dmg", () -> new RangedDamageAttribute(modLoc("void_dmg"), 0, 2048));
    public static final DeferredHolder<Attribute, RangedDamageAttribute> SOUL_DMG = ATTRIBUTES.register("soul_dmg", () -> new RangedDamageAttribute(modLoc("soul_dmg"), 0, 2048));
}
