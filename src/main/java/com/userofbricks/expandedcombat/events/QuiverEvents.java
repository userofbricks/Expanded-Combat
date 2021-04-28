package com.userofbricks.expandedcombat.events;

import com.userofbricks.expandedcombat.ExpandedCombat;
import com.userofbricks.expandedcombat.item.QuiverItem;
import com.userofbricks.expandedcombat.mixin.ContainerAccessor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
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
    public void arrowPickup(final EntityItemPickupEvent e) {
        final ItemStack[] pickedUpStack = {e.getItem().getItem().copy()};
        int afterCount;
        final int beforeCount = afterCount = pickedUpStack[0].getCount();
        PlayerEntity player = e.getPlayer();
        if (player.openContainer instanceof CuriosContainer) {
            return;
        }
        final Optional<ImmutableTriple<String, Integer, ItemStack>> quiver = (Optional<ImmutableTriple<String, Integer, ItemStack>>)CuriosApi.getCuriosHelper().findEquippedCurio(stack -> stack.getItem() instanceof QuiverItem, (LivingEntity)player);
        final boolean hasQuiver = quiver.isPresent();
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
            player.onItemPickup((Entity) e.getItem(), beforeCount - afterCount);
            e.getItem().getItem().setCount(afterCount);
            player.world.playSound((PlayerEntity) null, e.getItem().getPosX(), e.getItem().getPosY(), e.getItem().getPosZ(), SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 0.2f, (QuiverEvents.rand.nextFloat() - QuiverEvents.rand.nextFloat()) * 0.7f + 0.0f);
        }
    }
}
