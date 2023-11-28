package com.userofbricks.expanded_combat.item;

import com.userofbricks.expanded_combat.api.material.Material;
import com.userofbricks.expanded_combat.plugins.VanillaECPlugin;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import static com.userofbricks.expanded_combat.ExpandedCombat.modLoc;

public class SoulFist extends ECGauntletItem{
    public SoulFist(Properties properties) {
        super(properties, VanillaECPlugin.NETHERITE);
    }
    @Override
    public ResourceLocation getGauntletTexture(ItemStack stack) {
        return modLoc("textures/model/gauntlet/gauntlet.png");
    }
}
