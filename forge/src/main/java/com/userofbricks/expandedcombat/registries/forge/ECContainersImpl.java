package com.userofbricks.expandedcombat.registries.forge;

import com.userofbricks.expandedcombat.inventory.container.ECCuriosQuiverContainer;
import com.userofbricks.expandedcombat.registries.ECContainers;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeContainerType;

public class ECContainersImpl {
    public static final RegistrySupplier<MenuType<ECCuriosQuiverContainer>> EC_QUIVER_CURIOS = ECContainers.CONTAINER_TYPES.register("quiver_curios", () -> IForgeContainerType.create(ECCuriosQuiverContainer::new));
}
