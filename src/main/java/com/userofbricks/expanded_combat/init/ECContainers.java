package com.userofbricks.expanded_combat.init;

import com.userofbricks.expanded_combat.ExpandedCombat;
import com.userofbricks.expanded_combat.inventory.container.FletchingTableMenu;
import com.userofbricks.expanded_combat.inventory.container.ShieldSmithingMenu;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ECContainers {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(BuiltInRegistries.MENU, ExpandedCombat.MODID);

    public static final DeferredHolder<MenuType<?>, MenuType<FletchingTableMenu>> FLETCHING = MENU_TYPES.register("ec_fletching", () -> IMenuTypeExtension.create(FletchingTableMenu::new));
    public static final DeferredHolder<MenuType<?>, MenuType<ShieldSmithingMenu>> SHIELD_SMITHING = MENU_TYPES.register("shield_smithing", () -> new MenuType<>(ShieldSmithingMenu::new, FeatureFlags.DEFAULT_FLAGS));
}
