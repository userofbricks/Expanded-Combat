package com.userofbricks.expandedcombat.mixin;

import com.userofbricks.expandedcombat.ExpandedCombat;
import com.userofbricks.expandedcombat.inventory.container.FlechingTableContainer;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CraftingTableBlock;
import net.minecraft.world.level.block.FletchingTableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@MethodsReturnNonnullByDefault
@Mixin(FletchingTableBlock.class)
public class FletchingTableBlockMixin extends CraftingTableBlock {

    public FletchingTableBlockMixin(Properties p_i49985_1_) {
        super(p_i49985_1_);
    }
    private static final Component CONTAINER_TITLE = new TranslatableComponent("container." + ExpandedCombat.MOD_ID + ".fletching");

    @Override
    public MenuProvider getMenuProvider(BlockState blockState, Level world, BlockPos pos) {
        return new SimpleMenuProvider((i, playerInventory, playerEntity) -> new FlechingTableContainer(i, playerInventory, ContainerLevelAccess.create(world, pos)), CONTAINER_TITLE);
    }

    /**
     * @author Userofbricks
     * @reason for some odd reason couldn't get it to work with Events so this is temporary
     */
    @Overwrite
    public InteractionResult use(BlockState blockState, Level world, BlockPos pos, Player playerEntity, InteractionHand hand, BlockHitResult blockRayTraceResult) {
        if (world.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            playerEntity.openMenu(blockState.getMenuProvider(world, pos));
            return InteractionResult.CONSUME;
        }
    }
}