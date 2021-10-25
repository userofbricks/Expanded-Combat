package com.userofbricks.expandedcombat.registries.forge;

import com.userofbricks.expandedcombat.inventory.container.ECCuriosQuiverContainer;
import com.userofbricks.expandedcombat.registries.ECContainers;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeContainerType;

public class ECContainersImpl {
    //TODO use @Expect... stuff unstead of this. this causes errors.
    public static final RegistrySupplier<MenuType<ECCuriosQuiverContainer>> EC_QUIVER_CURIOS = ECContainers.CONTAINER_TYPES.register("quiver_curios", () -> );

    public static <T extends AbstractContainerMenu> MenuType<T> createQuiverMenu() {
        return IForgeContainerType.create(ECCuriosQuiverContainer::new);
    }
}
