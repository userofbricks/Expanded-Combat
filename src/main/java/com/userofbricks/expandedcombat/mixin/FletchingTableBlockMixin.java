package com.userofbricks.expandedcombat.mixin;

import com.userofbricks.expandedcombat.ExpandedCombat;
import com.userofbricks.expandedcombat.inventory.container.FlechingTableContainer;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.CraftingTableBlock;
import net.minecraft.block.FletchingTableBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@Mixin(FletchingTableBlock.class)
public class FletchingTableBlockMixin extends CraftingTableBlock {

    public FletchingTableBlockMixin(AbstractBlock.Properties p_i49985_1_) {
        super(p_i49985_1_);
    }
    private static final ITextComponent CONTAINER_TITLE = new TranslationTextComponent("container." + ExpandedCombat.MODID + ".fletching");

    @Override
    public INamedContainerProvider getMenuProvider(BlockState blockState, World world, BlockPos pos) {
        return new SimpleNamedContainerProvider((i, playerInventory, playerEntity) -> {
            return new FlechingTableContainer(i, playerInventory, IWorldPosCallable.create(world, pos));
        }, CONTAINER_TITLE);
    }

    /**
     * @author Userofbricks
     * @reason for some odd reason couldn't get it to work with Events so this is temporary
     */
    @Overwrite
    public ActionResultType use(BlockState blockState, World world, BlockPos pos, PlayerEntity playerEntity, Hand hand, BlockRayTraceResult blockRayTraceResult) {
        if (world.isClientSide) {
            return ActionResultType.SUCCESS;
        } else {
            playerEntity.openMenu(blockState.getMenuProvider(world, pos));
            return ActionResultType.CONSUME;
        }
    }
}
