package com.userofbricks.expandedcombat.inventory.container;

import com.userofbricks.expandedcombat.ExpandedCombat;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ECContainers {
    public static final DeferredRegister<MenuType<?>> CONTAINER_TYPES = DeferredRegister.create(ForgeRegistries.CONTAINERS, ExpandedCombat.MODID);

    public static final RegistryObject<MenuType<FlechingTableContainer>> FLETCHING = CONTAINER_TYPES.register("ec_fletching", () -> IForgeContainerType.create(FlechingTableContainer::new));
    public static final RegistryObject<MenuType<ECCuriosQuiverContainer>> EC_QUIVER_CURIOS = CONTAINER_TYPES.register("quiver_curios", () -> IForgeContainerType.create(ECCuriosQuiverContainer::new));
    public static final RegistryObject<MenuType<ShieldSmithingContainer>> SHIELD_SMITHING = CONTAINER_TYPES.register("shield_smithing", () -> IForgeContainerType.create(ShieldSmithingContainer::new));
}
