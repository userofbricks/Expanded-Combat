package com.userofbricks.expanded_combat.events;

import com.userofbricks.expanded_combat.api.events.registering.ShieldMaterialsRegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class MaterialRegister {

    //public static ShieldToMaterials VANILLA_SHIELD_MATERIALS;
    //public static ShieldToMaterials KNIGHT_METAL_SHIELD_MATERIALS;

    @SubscribeEvent
    public static void registerShieldMaterials(ShieldMaterialsRegistryEvent event) {
        //VANILLA_SHIELD_MATERIALS = event.register(new ShieldToMaterials(Items.SHIELD, WOOD, WOOD, IRON, WOOD, WOOD));
        //KNIGHT_METAL_SHIELD_MATERIALS = event.register(new ShieldToMaterials(TFItems.KNIGHTMETAL_SHIELD.get(), KNIGHTMETAL, KNIGHTMETAL, KNIGHTMETAL, KNIGHTMETAL, KNIGHTMETAL));
    }
}
