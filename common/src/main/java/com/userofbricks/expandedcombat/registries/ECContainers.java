package com.userofbricks.expandedcombat.registries;

import com.userofbricks.expandedcombat.ExpandedCombat;
import com.userofbricks.expandedcombat.inventory.container.FlechingTableContainer;
import com.userofbricks.expandedcombat.inventory.container.ShieldSmithingContainer;
import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.architectury.injectables.annotations.PlatformOnly;
import dev.architectury.registry.menu.MenuRegistry;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

public class ECContainers {
    public static final ECDeferredRegister<MenuType<?>> CONTAINER_TYPES = ECDeferredRegister.create(ExpandedCombat.MOD_ID, Registry.MENU_REGISTRY);

    public static final RegistrySupplier<MenuType<FlechingTableContainer>> FLETCHING = CONTAINER_TYPES.register("ec_fletching", () -> MenuRegistry.of(FlechingTableContainer::new));
    public static final RegistrySupplier<MenuType<ShieldSmithingContainer>> SHIELD_SMITHING = CONTAINER_TYPES.register("shield_smithing", () -> MenuRegistry.of(ShieldSmithingContainer::new));
    public static final RegistrySupplier<MenuType<?>> EC_QUIVER_MENU = ECContainers.CONTAINER_TYPES.register("quiver_menu", ECContainers::createQuiverMenu);

    @ExpectPlatform
    private static <T extends AbstractContainerMenu> MenuType<T> createQuiverMenu() {
        return null;
    }
}
