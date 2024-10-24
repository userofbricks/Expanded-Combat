package com.userofbricks.expanded_combat.events;

import com.userofbricks.expanded_combat.ExpandedCombat;
import com.userofbricks.expanded_combat.api.client.IGauntletRenderer;
import com.userofbricks.expanded_combat.init.ECItems;
import com.userofbricks.expanded_combat.item.GauntletItem;
import com.userofbricks.expanded_combat.item.QuiverItem;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.BundleContents;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderArmEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.api.client.ICurioRenderer;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

import java.util.List;
import java.util.Optional;

import static com.userofbricks.expanded_combat.init.ItemDataComponents.CHARGE;
import static net.minecraft.core.component.DataComponents.BUNDLE_CONTENTS;

@EventBusSubscriber(modid = "expanded_combat", bus = EventBusSubscriber.Bus.GAME)
@SuppressWarnings("unused")
public class GauntletEvents
{
    public static void DamageGauntletEvent(AttackEntityEvent event) {
        Player player = event.getEntity();
        if (player.isCreative()) return;
        Optional<ICuriosItemHandler> handlerOptional = CuriosApi.getCuriosInventory(event.getEntity());
        if (handlerOptional.isPresent()) {
            List<SlotResult> slotResults = handlerOptional.get().findCurios(itemStack -> itemStack.getItem() instanceof GauntletItem);
            if (slotResults.isEmpty()) return;
            for (SlotResult slotResult : slotResults) {
                ItemStack stack = slotResult.stack();
                SlotContext slotContext = slotResult.slotContext();
                if (stack.getItem() instanceof GauntletItem) {
                    stack.hurtAndBreak(1, (ServerLevel) player.level(), player, (item) -> CuriosApi.broadcastCurioBreakEvent(slotContext));
                }
            }
        }
    }
    @SubscribeEvent
    public static void moreDamageSources(LivingIncomingDamageEvent ev) {
        LivingEntity target = ev.getEntity();
        Optional<SlotResult> optionalSlotResult = CuriosApi.getCuriosInventory(target).flatMap(curiosInventory -> curiosInventory.findFirstCurio(ECItems.BERSERK_GAUNTLETS.get()));
        if (optionalSlotResult.isPresent()) {
            SlotResult slotResult = optionalSlotResult.get();
            int charge = slotResult.stack().getOrDefault(CHARGE, 0);
            if (charge >= 20) {
                target.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 5*20, 2));
                slotResult.stack().set(CHARGE, charge - 20);
            } else {
                slotResult.stack().set(CHARGE, charge + 1);
            }
        }
    }

    @SubscribeEvent
    public static void pulOutArrow(PlayerInteractEvent.RightClickEmpty event) {
        Player player = event.getEntity();
        Optional<ICuriosItemHandler> handlerOptional = CuriosApi.getCuriosInventory(player);
        Optional<SlotResult> optionalSlotResult = handlerOptional.flatMap(curiosInventory -> curiosInventory.findFirstCurio(ECItems.FIGHTERS_GAUNTLETS.get()));
        if (optionalSlotResult.isPresent() && player.getArrowCount() >= 1) {
            Optional<SlotResult> optionalQuiverSlotResult = handlerOptional.get().findFirstCurio(stack -> stack.getItem() instanceof QuiverItem);
            if (optionalQuiverSlotResult.isPresent()) {
                ItemStack quiverStack = optionalQuiverSlotResult.get().stack();
                QuiverItem quiverItem = (QuiverItem) quiverStack.getItem();

                BundleContents bundlecontents = quiverStack.getOrDefault(BUNDLE_CONTENTS, BundleContents.EMPTY);

                QuiverItem.MutableQuiverContents bundleContents$mutable = new QuiverItem.MutableQuiverContents(bundlecontents, quiverItem.getMaterial().offense().quiverStacks());

                int i = bundleContents$mutable.tryInsert(new ItemStack(Items.ARROW));
                if (i > 0) {
                    quiverStack.set(BUNDLE_CONTENTS, bundleContents$mutable.toImmutable());
                    quiverItem.playInsert(player);
                    player.awardStat(Stats.ITEM_PICKED_UP.get(Items.ARROW), i);
                } else {
                    player.getInventory().placeItemBackInInventory(new ItemStack(Items.ARROW));
                }
            } else {
                player.getInventory().placeItemBackInInventory(new ItemStack(Items.ARROW));
            }
            player.setArrowCount(player.getArrowCount() - 1);
        }
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void onRenderArm(RenderArmEvent event) {
        CuriosApi.getCuriosInventory(event.getPlayer()).ifPresent(handler -> {
            ICurioStacksHandler stacksHandler = handler.getCurios().get(ExpandedCombat.GAUNTLET_CURIOS_IDENTIFIER);
            if (stacksHandler != null) {
                IDynamicStackHandler stacks = stacksHandler.getStacks();
                IDynamicStackHandler cosmeticStacks = stacksHandler.getCosmeticStacks();
                ItemStack stack = cosmeticStacks.getStackInSlot(0);

                if (stack.isEmpty() && stacksHandler.getRenders().get(0)) {
                    stack = stacks.getStackInSlot(0);
                }

                if (stack.getItem() instanceof GauntletItem gauntletItem) {
                    ICurioRenderer iCurioRenderer = gauntletItem.getGauntletRenderer().get();
                    if (iCurioRenderer instanceof IGauntletRenderer gauntletRenderer) {
                        gauntletRenderer.renderFirstPersonArm(stack, event.getPoseStack(), event.getMultiBufferSource(), event.getPackedLight(), event.getPlayer(), event.getArm(), stack.hasFoil());
                    }
                }
            }
        });
    }
}
