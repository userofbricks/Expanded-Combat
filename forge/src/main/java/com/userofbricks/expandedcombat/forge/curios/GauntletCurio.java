package com.userofbricks.expandedcombat.forge.curios;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.userofbricks.expandedcombat.item.ECGauntletItem;
import com.userofbricks.expandedcombat.item.GauntletMaterials;
import com.userofbricks.expandedcombat.registries.ECEnchantments;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

import java.util.UUID;

public class GauntletCurio implements ICurio {
    private final ItemStack stack;

    public GauntletCurio(ItemStack stack) {this.stack = stack;}

    @Override
    public ItemStack getStack() {
        return stack;
    }

    public ECGauntletItem getGauntlet() {
        return (ECGauntletItem) stack.getItem();
    }

    public void onEquipFromUse(SlotContext slotContext) {
        LivingEntity livingEntity = slotContext.entity();
        livingEntity.level.playSound(null, new BlockPos(livingEntity.position()), getGauntlet().getMaterial().getSoundEvent(), SoundSource.NEUTRAL, 1.0f, 1.0f);
    }

    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        String identifier = slotContext.identifier();
        Multimap<Attribute, AttributeModifier> atts = HashMultimap.create();
        if (CuriosApi.getCuriosHelper().getCurioTags(stack.getItem()).contains(identifier) && stack.getItem() instanceof ECGauntletItem) {
            double attackDamage = ((ECGauntletItem)stack.getItem()).getAttackDamage();
            double nagaDamage = ((ECGauntletItem)stack.getItem()).getMaterial() == GauntletMaterials.naga ? (attackDamage/2.0d*3) : 0;
            double yetiDamage = ((ECGauntletItem)stack.getItem()).getMaterial() == GauntletMaterials.yeti ? (attackDamage/2.0d) : 0;
            int armorAmount = ((ECGauntletItem)stack.getItem()).getArmorAmount();
            double knockbackResistance = ((ECGauntletItem)stack.getItem()).getMaterial().getKnockbackResistance();
            double toughness = ((ECGauntletItem)stack.getItem()).getMaterial().getToughness();
            if (((ECGauntletItem) stack.getItem()).hasWeaponInHand) {
                atts.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(ECGauntletItem.ATTACK_UUID, "Attack damage bonus", (attackDamage + Math.round(attackDamage / 2.0d * EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PUNCH_ARROWS, stack)) + nagaDamage + yetiDamage) / 2d, AttributeModifier.Operation.ADDITION));
            } else {
                atts.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(ECGauntletItem.ATTACK_UUID, "Attack damage bonus", (attackDamage + Math.round(attackDamage / 2.0d * EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PUNCH_ARROWS, stack)) + nagaDamage + yetiDamage), AttributeModifier.Operation.ADDITION));
            }
            atts.put(Attributes.ARMOR, new AttributeModifier(ECGauntletItem.ARMOR_UUID, "Armor bonus", armorAmount, AttributeModifier.Operation.ADDITION));
            atts.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(ECGauntletItem.ARMOR_UUID, "Armor Toughness bonus", toughness, AttributeModifier.Operation.ADDITION));
            atts.put(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(ECGauntletItem.KNOCKBACK_RESISTANCE_UUID, "Knockback resistance bonus", (knockbackResistance + EnchantmentHelper.getItemEnchantmentLevel(ECEnchantments.KNOCKBACK_RESISTANCE.get(), stack) / 5.0f), AttributeModifier.Operation.ADDITION));
            atts.put(Attributes.ATTACK_KNOCKBACK, new AttributeModifier(ECGauntletItem.KNOCKBACK_UUID, "Knockback bonus", EnchantmentHelper.getItemEnchantmentLevel(Enchantments.KNOCKBACK, stack), AttributeModifier.Operation.ADDITION));
        }
        return atts;
    }

    public boolean canEquipFromUse(SlotContext slotContext) {
        return true;
    }
}
