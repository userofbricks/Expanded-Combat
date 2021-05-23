package com.userofbricks.expandedcombat.inventory.container;

import com.userofbricks.expandedcombat.ExpandedCombat;
import cpw.mods.modlauncher.LaunchPluginHandler;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ECContainers {
    public static final DeferredRegister<ContainerType<?>> CONTAINER_TYPES = DeferredRegister.create(ForgeRegistries.CONTAINERS, ExpandedCombat.MODID);

    public static final RegistryObject<ContainerType<FlechingTableContainer>> FLETCHING = CONTAINER_TYPES.register("ec_fletching", () -> IForgeContainerType.create(FlechingTableContainer::new));
}
