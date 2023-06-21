package com.userofbricks.expanded_combat.events;

import com.google.common.collect.Multimap;
import com.userofbricks.expanded_combat.item.ECGauntletItem;
import com.userofbricks.expanded_combat.item.ECWeaponItem;
import com.userofbricks.expanded_combat.item.materials.MaterialInit;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.SlotResult;

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
                float nagaDamage = gauntlet.getMaterial() == MaterialInit.NAGASCALE ? (float) (attackDamage / 2.0d * 3) : 0;
                float yetiDamage = gauntlet.getMaterial() == MaterialInit.YETI ? (float) (attackDamage / 2.0d) : 0;
                ev.setAmount(ev.getAmount() + ((attackDamage + Math.round(attackDamage / 2.0d * EnchantmentHelper.getTagEnchantmentLevel(Enchantments.PUNCH_ARROWS, slotResult.stack())) + nagaDamage + yetiDamage)/2));
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

    //TODO:does not work and needs its own class
    /*
    public void FirstPersonGuantlets(RenderHandEvent event) {
        AbstractClientPlayer abstractclientplayerentity = Minecraft.getInstance().player;
        CuriosApi.getCuriosHelper().getCuriosHandler(abstractclientplayerentity).ifPresent(curios -> {
            ItemStack curiosStack = CuriosApi.getCuriosHelper().findEquippedCurio(ExpandedCombat.hands_predicate, abstractclientplayerentity).map(stringIntegerItemStackImmutableTriple -> stringIntegerItemStackImmutableTriple.right).orElse(ItemStack.EMPTY);
            if (!(curiosStack.getItem() instanceof GauntletItem)) return;
            GauntletItem gauntletItem = (GauntletItem) curiosStack.getItem();
            ItemStack itemStack = event.getItemStack();
            Hand hand = event.getHand();
            MatrixStack matrixStack = event.getMatrixStack();
            boolean flag = hand == Hand.MAIN_HAND;
            HandSide handside = flag ? abstractclientplayerentity.getMainArm() : abstractclientplayerentity.getMainArm().getOpposite();
            matrixStack.pushPose();
            if (itemStack.isEmpty() && (flag && !abstractclientplayerentity.isInvisible())) {
                IRenderTypeBuffer renderTypeBuffer = event.getBuffers();
                int light = event.getLight();
                float equipProgress = event.getEquipProgress();
                float swingProgress = event.getSwingProgress();
                renderPlayerGauntlets(matrixStack, renderTypeBuffer, light, equipProgress, swingProgress, handside, gauntletItem);
            }
        });
    }

    private void renderPlayerGauntlets(MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int light, float equipProgress, float swingProgress, HandSide handSide, GauntletItem guantletItem) {
        boolean flag = handSide != HandSide.LEFT;
        float f = flag ? 1.0F : -1.0F;
        float f1 = MathHelper.sqrt(swingProgress);
        float f2 = -0.3F * MathHelper.sin(f1 * (float)Math.PI);
        float f3 = 0.4F * MathHelper.sin(f1 * ((float)Math.PI * 2F));
        float f4 = -0.4F * MathHelper.sin(swingProgress * (float)Math.PI);
        matrixStack.translate(f * (f2 + 0.64000005F), f3 + -0.6F + equipProgress * -0.6F, (double)(f4 + -0.71999997F));
        matrixStack.mulPose(Vector3f.YP.rotationDegrees(f * 45.0F));
        float f5 = MathHelper.sin(swingProgress * swingProgress * (float)Math.PI);
        float f6 = MathHelper.sin(f1 * (float)Math.PI);
        matrixStack.mulPose(Vector3f.YP.rotationDegrees(f * f6 * 70.0F));
        matrixStack.mulPose(Vector3f.ZP.rotationDegrees(f * f5 * -20.0F));
        AbstractClientPlayer abstractclientplayerentity = Minecraft.getInstance().player;
        Minecraft.getInstance().getTextureManager().bind(guantletItem.getGAUNTLET_TEXTURE());
        matrixStack.translate(f * -1.0F, 3.6F, 3.5D);
        matrixStack.mulPose(Vector3f.ZP.rotationDegrees(f * 120.0F));
        matrixStack.mulPose(Vector3f.XP.rotationDegrees(200.0F));
        matrixStack.mulPose(Vector3f.YP.rotationDegrees(f * -135.0F));
        matrixStack.translate(f * 5.6F, 0.0D, 0.0D);
        if (flag) {
            guantletItem.renderRightHand(matrixStack, renderTypeBuffer, light, abstractclientplayerentity, guantletItem.getGAUNTLET_TEXTURE());
        } else {
            guantletItem.renderLeftHand(matrixStack, renderTypeBuffer, light, abstractclientplayerentity, guantletItem.getGAUNTLET_TEXTURE());
        }

    }
     */
}
