package com.userofbricks.expanded_combat.init;

import com.userofbricks.expanded_combat.data.material.Material;
import com.userofbricks.expanded_combat.data_components.ShieldMaterials;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.datamaps.DataMapType;
import net.neoforged.neoforge.registries.datamaps.RegisterDataMapTypesEvent;

import static com.userofbricks.expanded_combat.ExpandedCombat.MODID;
import static com.userofbricks.expanded_combat.ExpandedCombat.modLoc;

@EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD)
public class DataMaps {
    public static final DataMapType<Item, Holder<Material>> SHIELD_INGREDIENT_MAP = DataMapType.builder(modLoc("ingredient_to_shield_material"), Registries.ITEM, Material.HOLDER_CODEC)
            .synced(Material.HOLDER_CODEC, true).build();
    public static final DataMapType<Item, ShieldMaterials> SHIELD_MATERIALS = DataMapType.builder(modLoc("shield_materials"), Registries.ITEM, ShieldMaterials.CODEC)
            .synced(ShieldMaterials.CODEC, false).build();

    @SubscribeEvent
    public static void registerDataMaps(RegisterDataMapTypesEvent event) {
        event.register(SHIELD_INGREDIENT_MAP);
        event.register(SHIELD_MATERIALS);
    }
}
