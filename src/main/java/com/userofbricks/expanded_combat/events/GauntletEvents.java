package com.userofbricks.expanded_combat.events;

import com.userofbricks.expanded_combat.item.ECGauntletItem;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
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
    /*
    @SubscribeEvent
    public void GauntletAnvilEnchanting(AnvilUpdateEvent event) {
        final ItemStack left = event.getLeft();
        final ItemStack right = event.getRight();
        final ItemStack output = event.getOutput();
        if (left.getItem() instanceof ECGauntletItem && (right.getItem() instanceof EnchantedBookItem || left.getItem() == right.getItem())) {
            int xpCost = 0;
            int nameCost = 0;
            int maximumCost = event.getCost();
            ItemStack stack1 = left.copy();
            final ItemStack stack2 = right.copy();
            final Map<Enchantment, Integer> map = EnchantmentHelper.getEnchantments(stack1);
            final int j = left.getBaseRepairCost() + (stack2.isEmpty() ? 0 : stack2.getBaseRepairCost());

            final boolean flag = stack2.getItem() == Items.ENCHANTED_BOOK && !EnchantedBookItem.getEnchantments(stack2).isEmpty();
            if (stack1.isDamageableItem() && !flag) {
                final int l = left.getMaxDamage() - left.getDamageValue();
                final int i1 = stack2.getMaxDamage() - stack2.getDamageValue();
                final int j2 = i1 + stack1.getMaxDamage() * 12 / 100;
                final int k1 = l + j2;
                int l2 = stack1.getMaxDamage() - k1;
                if (l2 < 0) {
                    l2 = 0;
                }
                if (l2 < stack1.getDamageValue()) {
                    stack1.setDamageValue(l2);
                    xpCost += 2;
                }
            }
            final Map<Enchantment, Integer> map2 = EnchantmentHelper.getEnchantments(stack2);
            boolean flag2 = false;
            boolean flag3 = false;
            for (final Enchantment enchantment1 : map2.keySet()) {
                if (enchantment1 != null) {
                    final int i2 = map.getOrDefault(enchantment1, 0);
                    int j3 = map2.get(enchantment1);
                    j3 = ((i2 == j3) ? (j3 + 1) : Math.max(j3, i2));
                    boolean flag4 = enchantment1.canEnchant(left);
                    if (left.getItem() == Items.ENCHANTED_BOOK) {
                        flag4 = true;
                    }
                    else if (enchantment1 == Enchantments.PUNCH_ARROWS || enchantment1 == Enchantments.KNOCKBACK) {
                        flag4 = true;
                    }
                    for (final Enchantment enchantment2 : map.keySet()) {
                        if (enchantment2 != enchantment1 && !enchantment1.isCompatibleWith(enchantment2)) {
                            flag4 = false;
                            ++xpCost;
                        }
                    }
                    if (!flag4) {
                        flag3 = true;
                    }
                    else {
                        flag2 = true;
                        if (j3 > enchantment1.getMaxLevel()) {
                            j3 = enchantment1.getMaxLevel();
                        }
                        map.put(enchantment1, j3);
                        int k2 = 0;
                        switch (enchantment1.getRarity()) {
                            case COMMON: {
                                k2 = 1;
                                break;
                            }
                            case UNCOMMON: {
                                k2 = 2;
                                break;
                            }
                            case RARE: {
                                k2 = 4;
                                break;
                            }
                            case VERY_RARE: {
                                k2 = 8;
                                break;
                            }
                        }
                        if (flag) {
                            k2 = Math.max(1, k2 / 2);
                        }
                        xpCost += k2 * j3;
                        if (left.getCount() <= 1) {
                            continue;
                        }
                        xpCost = 40;
                    }
                }
            }
            if (flag3 && !flag2) {
                event.setOutput(ItemStack.EMPTY);
                event.setCost(0);
                return;
            }
            if (StringUtils.isBlank(event.getName())) {
                if (left.hasCustomHoverName()) {
                    nameCost = 1;
                    xpCost += nameCost;
                    stack1.resetHoverName();
                }
            }
            else if (!event.getName().equals(left.getHoverName().getString())) {
                nameCost = 1;
                xpCost += nameCost;
                stack1.setHoverName(new TextComponent(event.getName()));
            }
            if (flag && !stack1.isBookEnchantable(stack2)) {
                stack1 = ItemStack.EMPTY;
            }
            maximumCost = j + xpCost;
            if (xpCost <= 0) {
                stack1 = ItemStack.EMPTY;
            }
            if (nameCost == xpCost && nameCost > 0 && maximumCost >= 40) {
                maximumCost = 39;
            }
            if (maximumCost >= 40) {
                stack1 = ItemStack.EMPTY;
            }
            if (!stack1.isEmpty()) {
                int k3 = stack1.getBaseRepairCost();
                if (!stack2.isEmpty() && k3 < stack2.getBaseRepairCost()) {
                    k3 = stack2.getBaseRepairCost();
                }
                if (nameCost != xpCost || nameCost == 0) {
                    k3 = getNewRepairCost(k3);
                }
                stack1.setRepairCost(k3);
                EnchantmentHelper.setEnchantments(map, stack1);
            }
            event.setOutput(stack1);
            event.setCost(stack1.getBaseRepairCost());
        }
    }
    
    public static int getNewRepairCost(int oldRepairCost) {
        return oldRepairCost * 2 + 1;
    }
    */

    @SubscribeEvent
    public static void onEquipmentChange(LivingEquipmentChangeEvent ev) {
        if ((ev.getSlot() == EquipmentSlot.MAINHAND || ev.getSlot() == EquipmentSlot.OFFHAND) && ev.getEntity() instanceof Player player) {
            ItemStack toStack = ev.getTo();
            List<SlotResult> slotResults = CuriosApi.getCuriosHelper().findCurios(player, itemStack -> itemStack.getItem() instanceof ECGauntletItem);
            for (SlotResult slotResult : slotResults) {
                ItemStack gauntlet = slotResult.stack();
                if(gauntlet != ItemStack.EMPTY && gauntlet.getItem() instanceof ECGauntletItem ) {
                    ((ECGauntletItem) gauntlet.getItem()).hasWeaponInHand = toStack.getItem() instanceof SwordItem || toStack.getItem() instanceof AxeItem;
                }
            }
        }
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
