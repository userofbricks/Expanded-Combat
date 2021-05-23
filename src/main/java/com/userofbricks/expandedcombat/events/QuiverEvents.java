package com.userofbricks.expandedcombat.events;

import com.userofbricks.expandedcombat.ExpandedCombat;
import com.userofbricks.expandedcombat.inventory.container.FlechingTableContainer;
import com.userofbricks.expandedcombat.item.QuiverItem;
import com.userofbricks.expandedcombat.mixin.ContainerAccessor;
import io.netty.buffer.Unpooled;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.NetworkHooks;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;
import top.theillusivec4.curios.common.inventory.CurioSlot;
import top.theillusivec4.curios.common.inventory.container.CuriosContainer;

import java.util.Map;
import java.util.Optional;
import java.util.Random;


@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class QuiverEvents {
    public static Random rand;

    public static void onCurioContainerCreated(CuriosContainer curiosContainer, PlayerEntity player) {
        curiosContainer.curiosHandler.ifPresent((iCuriosItemHandler) -> {
            Map<String, ICurioStacksHandler> curioMap = iCuriosItemHandler.getCurios();

            for (String identifier : curioMap.keySet()) {
                ICurioStacksHandler stackHandler = curioMap.get(identifier);
                IDynamicStackHandler iDynamicStackHandler = stackHandler.getStacks();

                if (identifier.equals("quiver")) {
                    ((ContainerAccessor) curiosContainer).$addSlot(new CurioSlot(player, iDynamicStackHandler, 0, identifier, 78, 20, stackHandler.getRenders()));
                }
                if (identifier.equals("arrows")) {
                    ((ContainerAccessor) curiosContainer).$addSlot(new CurioSlot(player, iDynamicStackHandler, 0, identifier, 78, 38, stackHandler.getRenders()));
                }
            }
        });
    }

    @SubscribeEvent
    public void arrowPickup( EntityItemPickupEvent e) {
         ItemStack[] pickedUpStack = {e.getItem().getItem().copy()};
        int afterCount;
         int beforeCount = afterCount = pickedUpStack[0].getCount();
        PlayerEntity player = e.getPlayer();
        if (player.containerMenu instanceof CuriosContainer) {
            return;
        }
         Optional<ImmutableTriple<String, Integer, ItemStack>> quiver = CuriosApi.getCuriosHelper().findEquippedCurio(stack -> stack.getItem() instanceof QuiverItem, (LivingEntity)player);
         boolean hasQuiver = quiver.isPresent();
        if (!pickedUpStack[0].isEmpty() && hasQuiver) {
            CuriosApi.getCuriosHelper().getCuriosHandler(player).ifPresent(iCurioItemHandler -> {
                IDynamicStackHandler quiverHandler = iCurioItemHandler.getCurios().get("arrows").getStacks();
                for (int i = 0; i < quiverHandler.getSlots(); ++i) {
                    pickedUpStack[0] = quiverHandler.insertItem(i, pickedUpStack[0], false);
                    if (pickedUpStack[0].isEmpty()) {
                        break;
                    }
                }
            });
        }
        afterCount = pickedUpStack[0].getCount();
        if (afterCount < beforeCount) {
            //player.onItemPickup(e.getItem(), beforeCount - afterCount);
            e.getItem().getItem().setCount(afterCount);
            player.level.playSound((PlayerEntity) null, e.getItem().getX(), e.getItem().getY(), e.getItem().getZ(), SoundEvents.ITEM_PICKUP, SoundCategory.PLAYERS, 0.2f, (QuiverEvents.rand.nextFloat() - QuiverEvents.rand.nextFloat()) * 0.7f + 0.0f);
        }
    }


    private static final ITextComponent CONTAINER_TITLE = new TranslationTextComponent("container." + ExpandedCombat.MODID + ".fletching");

    @SubscribeEvent
    public void onRightClickBlock(final PlayerInteractEvent.RightClickBlock event) {
        final World world = event.getWorld();
        final BlockPos pos = event.getPos();
        if (world.getBlockState(pos).getBlock() == Blocks.FLETCHING_TABLE) {
            //if (world.isClientSide) {
                //event.setCancellationResult(ActionResultType.SUCCESS);
                //event.setCanceled(true);
            //} else {
                final PlayerEntity player = event.getPlayer();
                player.swing(Hand.MAIN_HAND, true);
                if (player instanceof ServerPlayerEntity) {
                    NetworkHooks.openGui((ServerPlayerEntity) player, new INamedContainerProvider() {
                        public ITextComponent getDisplayName() {
                            return CONTAINER_TITLE;
                        }

                        public Container createMenu(final int id, final PlayerInventory inventory, final PlayerEntity player) {
                            return new FlechingTableContainer(id, inventory, new PacketBuffer(Unpooled.buffer()).writeBlockPos(pos));
                        }
                    }, pos);
                    //player.openMenu(new SimpleNamedContainerProvider((i, playerInventory, playerEntity) -> new FlechingTableContainer(i, playerInventory, IWorldPosCallable.create(world, pos)), CONTAINER_TITLE));
                    //event.setCancellationResult(ActionResultType.CONSUME);
                    //event.setCanceled(true);
                }
            //}
        }
    }
}
