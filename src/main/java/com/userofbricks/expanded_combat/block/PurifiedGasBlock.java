package com.userofbricks.expanded_combat.block;

import com.userofbricks.expanded_combat.item.ECItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class PurifiedGasBlock extends AbstractGasBlock {
    public PurifiedGasBlock(Properties properties) {
        super(properties.replaceable(), () -> ParticleTypes.MYCELIUM);
    }

    public boolean blockCatalistItemConversions(BlockState thisState, BlockState catalystState, ServerLevel world, BlockPos thisPos, BlockPos catalystPos, RandomSource random) {
        if (catalystState.is(BlockTags.ICE)) {
            ItemEntity entityToSpawn = new ItemEntity(world, thisPos.getX(), thisPos.getY(), thisPos.getZ(), new ItemStack(ECItems.SOLIDIFIED_PURIFICATION.get()));
            entityToSpawn.setPickUpDelay(10);
            entityToSpawn.setUnlimitedLifetime();
            world.addFreshEntity(entityToSpawn);
            return true;
        }
        return false;
    }
}
