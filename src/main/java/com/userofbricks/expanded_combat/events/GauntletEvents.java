package com.userofbricks.expanded_combat.events;

import com.google.common.collect.Multimap;
import com.userofbricks.expanded_combat.client.renderer.GauntletRenderer;
import com.userofbricks.expanded_combat.client.renderer.MaulersRenderer;
import com.userofbricks.expanded_combat.init.ECAttributes;
import com.userofbricks.expanded_combat.init.ECItems;
import com.userofbricks.expanded_combat.item.ECGauntletItem;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderArmEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.api.SlotTypePreset;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

import java.util.List;
import java.util.Optional;

@Mod.EventBusSubscriber(modid = "expanded_combat", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class GauntletEvents
{
    public static void DamageGauntletEvent(AttackEntityEvent event) {
        Player player = event.getEntity();
        if (player.isCreative()) return;
        List<SlotResult> slotResults = CuriosApi.getCuriosHelper().findCurios(player, itemStack -> itemStack.getItem() instanceof ECGauntletItem);
        if (slotResults.isEmpty()) return;
        for (SlotResult slotResult : slotResults) {
            ItemStack stack = slotResult.stack();
            SlotContext slotContext = slotResult.slotContext();
            if (stack.getItem() instanceof ECGauntletItem) {
                stack.hurtAndBreak(1, (LivingEntity) player, damager -> CuriosApi.getCuriosHelper().onBrokenCurio(slotContext));
            }
        }
    }
    @SubscribeEvent
    public static void moreDamageSources(LivingAttackEvent ev) {
        LivingEntity target = ev.getEntity();
        Optional<SlotResult> optionalSlotResult = CuriosApi.getCuriosHelper().findFirstCurio(target, ECItems.MAULERS.get());
        if (optionalSlotResult.isPresent()) {
            SlotResult slotResult = optionalSlotResult.get();
            int charge = slotResult.stack().getOrCreateTag().getInt("charge");
            if (charge >= 20) {
                target.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 5*20, 2));
            } else {
                slotResult.stack().getOrCreateTag().putInt("charge", charge + 1);
            }
        }
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    @SuppressWarnings("deprecation")
    public static void onRenderArm(RenderArmEvent event) {
        CuriosApi.getCuriosHelper().getCuriosHandler(event.getPlayer()).ifPresent(handler -> {
            ICurioStacksHandler stacksHandler = handler.getCurios().get(SlotTypePreset.HANDS.getIdentifier());
            if (stacksHandler != null) {
                IDynamicStackHandler stacks = stacksHandler.getStacks();
                IDynamicStackHandler cosmeticStacks = stacksHandler.getCosmeticStacks();
                ItemStack stack = cosmeticStacks.getStackInSlot(0);
                if (stack.isEmpty() && stacksHandler.getRenders().get(0)) {
                    stack = stacks.getStackInSlot(0);
                }

                GauntletRenderer renderer = GauntletRenderer.getGloveRenderer(stack);
                if (renderer != null) {
                    renderer.renderFirstPersonArm(stack, event.getPoseStack(), event.getMultiBufferSource(), event.getPackedLight(), event.getPlayer(), event.getArm(), stack.hasFoil());
                }

                MaulersRenderer maulers = MaulersRenderer.getGloveRenderer(stack);
                if (maulers != null) {
                    maulers.renderFirstPersonArm(stack, event.getPoseStack(), event.getMultiBufferSource(), event.getPackedLight(), event.getPlayer(), event.getArm(), stack.hasFoil());
                }
            }
        });
    }
}
