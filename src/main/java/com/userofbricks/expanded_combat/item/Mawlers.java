package com.userofbricks.expanded_combat.item;

import com.userofbricks.expanded_combat.client.renderer.MaulersRenderer;
import com.userofbricks.expanded_combat.plugins.VanillaECPlugin;
import top.theillusivec4.curios.api.client.ICurioRenderer;

import java.util.function.Supplier;

public class Mawlers extends ECGauntletItem{
    public Mawlers(Properties properties) {
        super(properties, VanillaECPlugin.DIAMOND);
    }

    @Override
    public Supplier<ICurioRenderer> getGauntletRenderer() {
        return MaulersRenderer::new;
    }
}
