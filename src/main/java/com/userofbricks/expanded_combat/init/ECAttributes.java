package com.userofbricks.expanded_combat.init;

import com.userofbricks.expanded_combat.ExpandedCombat;
import com.userofbricks.expanded_combat.entity.attributes.RandedDamageAttribute;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.userofbricks.expanded_combat.ExpandedCombat.modLoc;

public class ECAttributes {
    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, ExpandedCombat.MODID);

    public static final RegistryObject<RandedDamageAttribute> GAUNTLET_DMG_WITHOUT_WEAPON = ATTRIBUTES.register("dmg_no_weapon", () -> new RandedDamageAttribute(modLoc("dmg_no_weapon"), 0, 2048));
    public static final RegistryObject<RandedDamageAttribute> HEAT_DMG = ATTRIBUTES.register("heat_dmg", () -> new RandedDamageAttribute(modLoc("heat_dmg"), 0, 2048));
    public static final RegistryObject<RandedDamageAttribute> COLD_DMG = ATTRIBUTES.register("cold_dmg", () -> new RandedDamageAttribute(modLoc("cold_dmg"), 0, 2048));
    public static final RegistryObject<RandedDamageAttribute> VOID_DMG = ATTRIBUTES.register("void_dmg", () -> new RandedDamageAttribute(modLoc("void_dmg"), 0, 2048));
    public static final RegistryObject<RandedDamageAttribute> SOUL_DMG = ATTRIBUTES.register("soul_dmg", () -> new RandedDamageAttribute(modLoc("soul_dmg"), 0, 2048));
}
