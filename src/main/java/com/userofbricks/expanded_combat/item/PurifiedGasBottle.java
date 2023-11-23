package com.userofbricks.expanded_combat.item;

import com.userofbricks.expanded_combat.init.ECBlocks;
import com.userofbricks.expanded_combat.init.LangStrings;
import net.minecraft.ChatFormatting;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.function.Supplier;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class PurifiedGasBottle extends Item implements DispensibleContainerItem {
    public final Supplier<Block> block;
    public PurifiedGasBottle(Properties p_41383_, Supplier<Block> block) {
        super(p_41383_);
        this.block = block;
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        BlockHitResult blockhitresult = getPlayerPOVHitResult(level, player, ClipContext.Fluid.NONE);
        if (blockhitresult.getType() == HitResult.Type.MISS) {
            return InteractionResultHolder.pass(itemstack);
        } else if (blockhitresult.getType() != HitResult.Type.BLOCK) {
            return InteractionResultHolder.pass(itemstack);
        } else {
            BlockPos blockpos = blockhitresult.getBlockPos();
            Direction direction = blockhitresult.getDirection();
            BlockPos blockpos1 = blockpos.relative(direction);
            if (level.mayInteract(player, blockpos) && player.mayUseItemAt(blockpos1, direction, itemstack)) {
                if (this.emptyContents(player, level, blockpos1, blockhitresult, itemstack)) {
                    if (player instanceof ServerPlayer) {
                        CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayer)player, blockpos1, itemstack);
                    }

                    player.awardStat(Stats.ITEM_USED.get(this));
                    return InteractionResultHolder.sidedSuccess(getEmptySuccessItem(itemstack, player), level.isClientSide());
                } else {
                    return InteractionResultHolder.fail(itemstack);
                }
            } else {
                return InteractionResultHolder.fail(itemstack);
            }
        }
    }
    public static ItemStack getEmptySuccessItem(ItemStack p_40700_, Player p_40701_) {
        return !p_40701_.getAbilities().instabuild ? new ItemStack(Items.GLASS_BOTTLE) : p_40700_;
    }

    @Override
    public boolean emptyContents(@Nullable Player player, Level level, BlockPos blockPos, @Nullable BlockHitResult hitResult) {
        return this.emptyContents(player, level, blockPos, hitResult, null);
    }

    @Override
    public boolean emptyContents(@Nullable Player player, Level level, BlockPos pos, @Nullable BlockHitResult hitResult, @Nullable ItemStack container) {
        BlockState blockstate = level.getBlockState(pos);
        boolean flag1 = blockstate.isAir();
        if (!flag1) {
            return hitResult != null && this.emptyContents(player, level, hitResult.getBlockPos().relative(hitResult.getDirection()), null, container);
        } else {
            if (!level.setBlock(pos, block.get().defaultBlockState(), 11)) {
                return false;
            } else {
                this.playEmptySound(player, level, pos);
                return true;
            }
        }

    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> componentList, TooltipFlag tooltipFlag) {
        if(tooltipFlag.isAdvanced() && block.get() == ECBlocks.GAS_BLOCK.get()) {
            componentList.add(Component.translatable(LangStrings.FOUND_AT_HEIGHT_LIMIT).withStyle(ChatFormatting.GRAY));
        }
        super.appendHoverText(stack, level, componentList, tooltipFlag);
    }

    protected void playEmptySound(@Nullable Player p_40696_, LevelAccessor p_40697_, BlockPos p_40698_) {
        SoundEvent soundevent = SoundEvents.ARROW_SHOOT;
        p_40697_.playSound(p_40696_, p_40698_, soundevent, SoundSource.BLOCKS, 1.0F, 1.0F);
        p_40697_.gameEvent(p_40696_, GameEvent.BLOCK_PLACE, p_40698_);
    }
}
