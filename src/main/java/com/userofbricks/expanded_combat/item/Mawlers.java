package com.userofbricks.expanded_combat.item;

import com.userofbricks.expanded_combat.api.material.Material;
import com.userofbricks.expanded_combat.client.renderer.MaulersRenderer;
import com.userofbricks.expanded_combat.plugins.VanillaECPlugin;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.client.ICurioRenderer;

import java.util.function.Supplier;

public class Mawlers extends ECGauntletItem{
    public Mawlers(Properties properties, Material material) {
        super(properties, material);
    }

    @Override
    public @NotNull ItemStack getDefaultInstance() {
        ItemStack stack = super.getDefaultInstance();
        stack.getOrCreateTag().putInt("charge", 0);
        return stack;
    }

    @Override
    public Supplier<ICurioRenderer> getGauntletRenderer() {
        return MaulersRenderer::new;
    }
}
