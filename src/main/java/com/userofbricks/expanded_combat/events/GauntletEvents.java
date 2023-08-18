package com.userofbricks.expanded_combat.events;

import com.google.common.collect.Multimap;
import com.userofbricks.expanded_combat.client.renderer.GauntletRenderer;
import com.userofbricks.expanded_combat.item.ECGauntletItem;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.client.event.RenderArmEvent;
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

@Mod.EventBusSubscriber(modid = "expanded_combat", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class GauntletEvents
{
    @SubscribeEvent
    public static void onEquipmentChange(LivingHurtEvent ev) {
        Entity entity = ev.getSource().getEntity();
        if (!(entity instanceof LivingEntity causingEntity)) return;
        Entity directEntity = ev.getSource().getDirectEntity();
        if (entity != directEntity) return;

        CuriosApi.getCuriosHelper().findFirstCurio(causingEntity, stack -> stack.getItem() instanceof ECGauntletItem).ifPresent(slotResult -> {
            ECGauntletItem gauntlet = (ECGauntletItem) slotResult.stack().getItem();
            boolean hasWeaponInHand = false;
            Multimap<Attribute, AttributeModifier> mainHandAttributes = causingEntity.getMainHandItem().getAttributeModifiers(EquipmentSlot.MAINHAND);
            Multimap<Attribute, AttributeModifier> offHandAttributes = causingEntity.getOffhandItem().getAttributeModifiers(EquipmentSlot.OFFHAND);

            if (mainHandAttributes.containsKey(Attributes.ATTACK_DAMAGE)) {
                for (AttributeModifier modifier :
                        mainHandAttributes.get(Attributes.ATTACK_DAMAGE)) {
                    if (modifier.getAmount() > 1) hasWeaponInHand = true;
                }
            }
            if (offHandAttributes.containsKey(Attributes.ATTACK_DAMAGE)) {
                for (AttributeModifier modifier :
                        offHandAttributes.get(Attributes.ATTACK_DAMAGE)) {
                    if (modifier.getAmount() > 1) hasWeaponInHand = true;
                }
            }


            if (!hasWeaponInHand) {
                float attackDamage = (float) Math.max(gauntlet.getAttackDamage(), 0.5);
                float extraDamage = gauntlet.getMaterial().getAdditionalDamageAfterEnchantments().apply(attackDamage);
                ev.setAmount(ev.getAmount() + ((attackDamage + Math.round(attackDamage / 2.0d * EnchantmentHelper.getTagEnchantmentLevel(Enchantments.PUNCH_ARROWS, slotResult.stack())) + extraDamage)/2));
            }
        });
    }

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
            }
        });
    }
}
