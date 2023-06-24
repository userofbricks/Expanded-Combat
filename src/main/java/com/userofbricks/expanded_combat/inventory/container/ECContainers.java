package com.userofbricks.expanded_combat.inventory.container;

import com.userofbricks.expanded_combat.ExpandedCombat;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ECContainers {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(ForgeRegistries.MENU_TYPES, ExpandedCombat.MODID);

    public static final RegistryObject<MenuType<FletchingTableMenu>> FLETCHING = MENU_TYPES.register("ec_fletching", () -> IForgeMenuType.create(FletchingTableMenu::new));
    public static final RegistryObject<MenuType<ShieldSmithingMenu>> SHIELD_SMITHING = MENU_TYPES.register("shield_smithing", () -> IForgeMenuType.create(ShieldSmithingMenu::new));
}
