package com.userofbricks.expanded_combat.events;

import com.userofbricks.expanded_combat.ExpandedCombat;
import com.userofbricks.expanded_combat.api.registry.ApiHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.util.TriState;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

@EventBusSubscriber(modid = ExpandedCombat.MODID)
public class FletchingTableEvents {
    @SubscribeEvent(priority = EventPriority.HIGHEST, receiveCanceled = true)
    public static void openFletchingGUI(PlayerInteractEvent.RightClickBlock event) {
        if (event.getHand() != InteractionHand.MAIN_HAND)
            return;

        Level level = event.getLevel();
        BlockPos pos = event.getPos();
        BlockState blockState = level.getBlockState(pos);
        if (!blockState.is(Blocks.FLETCHING_TABLE))
            return;

        if (ApiHelper.doesAnyPluginDenyFletchingTableGui(ExpandedCombat.PLUGINS) || !ExpandedCombat.CONFIG.enableFletchingTable)
            return;

        if (!event.getEntity().isCrouching())
            return;
        // not crouching: mixin handles vanilla; other mods keep their GUI
        // crouching: open EC even if other mods already overwrote the GUI

        Player player = event.getEntity();

        if (!level.isClientSide)
            player.openMenu(blockState.getMenuProvider(level, pos));

        event.setUseItem(TriState.FALSE);
        event.setUseBlock(TriState.FALSE);
        event.setCancellationResult(InteractionResult.sidedSuccess(level.isClientSide()));
        event.setCanceled(true);
    }
}
