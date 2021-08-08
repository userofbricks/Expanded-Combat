package com.userofbricks.expandedcombat.client.renderer.model;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.IItemRenderProperties;

import javax.annotation.Nonnull;

public class TestingOfTheNewItemStackTileEntityRendererAssignment extends Item {
    public TestingOfTheNewItemStackTileEntityRendererAssignment(Properties p_41383_) {
        super(p_41383_);
    }
    public void initializeClient(@Nonnull java.util.function.Consumer<net.minecraftforge.client.IItemRenderProperties> consumer) {
        consumer.accept(new IItemRenderProperties() {
            /**
             * @return the new form of tile entity rendering for items
             */
            @Override
            public BlockEntityWithoutLevelRenderer getItemStackRenderer() {
                return IItemRenderProperties.super.getItemStackRenderer();
            }
        });
    }
}
