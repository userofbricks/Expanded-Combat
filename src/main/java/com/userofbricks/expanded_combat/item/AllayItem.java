package com.userofbricks.expanded_combat.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.common.ForgeSpawnEggItem;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class AllayItem extends ForgeSpawnEggItem {
    public AllayItem(Properties p_43210_) {
        super(() -> EntityType.ALLAY, 0, 0, p_43210_);
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext p_43223_) {
        Level level = p_43223_.getLevel();
        if (!(level instanceof ServerLevel)) {
            return InteractionResult.SUCCESS;
        } else {
            ItemStack itemstack = p_43223_.getItemInHand();
            BlockPos blockpos = p_43223_.getClickedPos();
            Direction direction = p_43223_.getClickedFace();
            BlockState blockstate = level.getBlockState(blockpos);

            BlockPos blockpos1;
            if (blockstate.getCollisionShape(level, blockpos).isEmpty()) {
                blockpos1 = blockpos;
            } else {
                blockpos1 = blockpos.relative(direction);
            }

            EntityType<?> entitytype = this.getType(itemstack.getTag());
            if (entitytype.spawn((ServerLevel)level, itemstack, p_43223_.getPlayer(), blockpos1, MobSpawnType.SPAWN_EGG, true, !Objects.equals(blockpos, blockpos1) && direction == Direction.UP) != null) {
                itemstack.shrink(1);
                level.gameEvent(p_43223_.getPlayer(), GameEvent.ENTITY_PLACE, blockpos);
            }

            return InteractionResult.CONSUME;
        }
    }

    @Override
    public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
        if (stack.getCount() >= 1 && entity.level() instanceof ServerLevel level) {
            EntityType<?> entitytype = this.getType(stack.getTag());
            if (entitytype.spawn(level, entity.blockPosition(), MobSpawnType.SPAWN_EGG) != null) {
                stack.shrink(1);
                level.gameEvent(entity, GameEvent.ENTITY_PLACE, entity.blockPosition());
            }
        }
        return super.onEntityItemUpdate(stack, entity);
    }
}
