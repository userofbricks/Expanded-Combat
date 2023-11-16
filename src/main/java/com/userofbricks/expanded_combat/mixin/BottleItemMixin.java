package com.userofbricks.expanded_combat.mixin;

import com.userofbricks.expanded_combat.block.AbstractGasBlock;
import com.userofbricks.expanded_combat.block.ECBlocks;
import com.userofbricks.expanded_combat.block.PurifiedGasBlock;
import com.userofbricks.expanded_combat.item.ECItems;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BottleItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BottleItem.class)
public class BottleItemMixin extends Item {
    public BottleItemMixin(Properties p_41383_) {
        super(p_41383_);
    }

    @Inject(method = "use", at = @At("HEAD"), cancellable = true)
    public void collectGas(Level level, Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir) {
        BlockHitResult blockhitresult = AbstractGasBlock.getPlayerPOVHitResult(level, player, ClipContext.Fluid.ANY);
        if (blockhitresult.getType() == HitResult.Type.BLOCK) {
            BlockPos blockpos = blockhitresult.getBlockPos();
            Block block = level.getBlockState(blockpos).getBlock();
            if (block == ECBlocks.PURIFIED_GAS_BLOCK.get()) {
                level.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.BOTTLE_FILL_DRAGONBREATH, SoundSource.NEUTRAL, 1.0F, 1.0F);
                level.gameEvent(player, GameEvent.FLUID_PICKUP, blockpos);
                player.awardStat(Stats.ITEM_USED.get(this));
                level.setBlockAndUpdate(blockpos, Blocks.AIR.defaultBlockState());
                cir.setReturnValue(InteractionResultHolder.sidedSuccess(ItemUtils.createFilledResult(player.getItemInHand(hand), player, new ItemStack(ECItems.PURIFIED_GAS_BOTTLE.get())), level.isClientSide()));
            } else if (block == ECBlocks.GAS_BLOCK.get()) {
                level.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.BOTTLE_FILL_DRAGONBREATH, SoundSource.NEUTRAL, 1.0F, 1.0F);
                level.gameEvent(player, GameEvent.FLUID_PICKUP, blockpos);
                player.awardStat(Stats.ITEM_USED.get(this));
                level.setBlockAndUpdate(blockpos, Blocks.AIR.defaultBlockState());
                cir.setReturnValue(InteractionResultHolder.sidedSuccess(ItemUtils.createFilledResult(player.getItemInHand(hand), player, new ItemStack(ECItems.GAS_BOTTLE.get())), level.isClientSide()));
            }
        }
    }
}
