package com.userofbricks.expanded_combat.plugins;

import com.userofbricks.expanded_combat.api.registry.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;

import static com.userofbricks.expanded_combat.ExpandedCombat.modLoc;
import static com.userofbricks.expanded_combat.init.Materials.IRON;
import static com.userofbricks.expanded_combat.init.Materials.WOOD_PLANK;

@ECPlugin
public class VanillaECPlugin implements IExpandedCombatPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        return modLoc("vanilla");
    }
    @Override
    public void registerShieldToMaterials(ShieldMaterialRegisterator registrationHandler) {
        registrationHandler.registerShieldToMaterials(new ShieldToMaterials(() -> Items.SHIELD, WOOD_PLANK, WOOD_PLANK, IRON, WOOD_PLANK, WOOD_PLANK));
    }
    @Override
    public int loadOrder() {
        return 0;
    }
}