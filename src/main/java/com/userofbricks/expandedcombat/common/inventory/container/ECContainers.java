package com.userofbricks.expandedcombat.common.inventory.container;

import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

@Mod.EventBusSubscriber(
        modid = "expanded_combat",
        bus = Mod.EventBusSubscriber.Bus.MOD
)
public class ECContainers {

    @ObjectHolder("expanded_combat:ec_curios_container")
    public static final ContainerType<ECCuriosContainer> CONTAINER_TYPE = null;
    public ECContainers () {
    }

    //@SubscribeEvent
    public static void registerContainer(RegistryEvent.Register<ContainerType<?>> evt) {
        evt.getRegistry().register(IForgeContainerType.create(ECCuriosContainer::new).setRegistryName("ec_curios_container"));
    }
}
