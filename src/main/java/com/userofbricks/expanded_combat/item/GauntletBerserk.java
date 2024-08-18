package com.userofbricks.expanded_combat.item;

import com.userofbricks.expanded_combat.client.renderer.MaulersRenderer;
import com.userofbricks.expanded_combat.data.material.Material;
import com.userofbricks.expanded_combat.init.ItemDataComponents;
import net.minecraft.core.Holder;
import net.neoforged.neoforge.registries.DeferredHolder;
import top.theillusivec4.curios.api.client.ICurioRenderer;

import java.util.function.Supplier;

public class GauntletBerserk extends GauntletItem {

    public GauntletBerserk(Properties properties, DeferredHolder<Material, Material> materialIn, Layer... layers) {
        super(properties.component(ItemDataComponents.CHARGE, 0), materialIn, layers);
    }
    @Override
    public Supplier<ICurioRenderer> getGauntletRenderer() {
        return MaulersRenderer::new;
    }
}
