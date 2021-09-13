package com.userofbricks.expandedcombat.registries;

import com.userofbricks.expandedcombat.ExpandedCombat;
import net.minecraft.core.Registry;
import net.minecraft.world.inventory.MenuType;

public class ECContainers {
    public static final ECDeferredRegister<MenuType<?>> CONTAINER_TYPES = ECDeferredRegister.create(ExpandedCombat.MOD_ID, Registry.MENU_REGISTRY);

    //public static final RegistrySupplier<MenuType<FlechingTableContainer>> FLETCHING = CONTAINER_TYPES.register("ec_fletching", () -> IForgeContainerType.create(FlechingTableContainer::new));
    //public static final RegistrySupplier<MenuType<ECCuriosQuiverContainer>> EC_QUIVER_CURIOS = CONTAINER_TYPES.register("quiver_curios", () -> IForgeContainerType.create(ECCuriosQuiverContainer::new));
    //public static final RegistrySupplier<MenuType<ShieldSmithingContainer>> SHIELD_SMITHING = CONTAINER_TYPES.register("shield_smithing", () -> IForgeContainerType.create(ShieldSmithingContainer::new));
}
