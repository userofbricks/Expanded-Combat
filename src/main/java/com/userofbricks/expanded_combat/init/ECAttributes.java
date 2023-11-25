package com.userofbricks.expanded_combat.init;

import com.userofbricks.expanded_combat.ExpandedCombat;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ECAttributes {
    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, ExpandedCombat.MODID);

    public static final RegistryObject<RangedAttribute> GAUNTLET_DMG_WITHOUT_WEAPON = ATTRIBUTES.register("dmg_no_weapon", () -> new RangedAttribute("attribute.expanded_combat.dmg_no_weapon.desc", 0, 0, 2048));
    public static final RegistryObject<RangedAttribute> HEAT_DMG = ATTRIBUTES.register("heat_dmg", () -> new RangedAttribute("attribute.expanded_combat.heat_dmg.desc", 0, 0, 2048));
    public static final RegistryObject<RangedAttribute> COLD_DMG = ATTRIBUTES.register("cold_dmg", () -> new RangedAttribute("attribute.expanded_combat.cold_dmg.desc", 0, 0, 2048));
    public static final RegistryObject<RangedAttribute> VOID_DMG = ATTRIBUTES.register("void_dmg", () -> new RangedAttribute("attribute.expanded_combat.void_dmg.desc", 0, 0, 2048));
}
