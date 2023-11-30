package com.userofbricks.expanded_combat.item;

import com.userofbricks.expanded_combat.api.material.Material;
import com.userofbricks.expanded_combat.client.renderer.GauntletRenderer;
import com.userofbricks.expanded_combat.plugins.VanillaECPlugin;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.client.ICurioRenderer;

import java.util.function.Supplier;

import static com.userofbricks.expanded_combat.ExpandedCombat.modLoc;

public class FightersBindings extends ECGauntletItem{
    public FightersBindings(Properties properties, Material material) {
        super(properties, material);
    }
    @Override
    public ResourceLocation getGauntletTexture(ItemStack stack) {
        return modLoc("textures/model/gauntlet/fighters_bindings.png");
    }
    @Override
    public boolean hasEmissiveTexture(ItemStack stack) {
        return true;
    }
    @Override
    public ResourceLocation getEmissiveTexture(ItemStack stack) {
        return modLoc("textures/model/gauntlet/fighters_bindings_emissive.png");
    }
}
