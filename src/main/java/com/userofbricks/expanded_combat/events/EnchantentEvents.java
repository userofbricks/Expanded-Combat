package com.userofbricks.expanded_combat.events;

import com.userofbricks.expanded_combat.init.ECEnchantments;
import com.userofbricks.expanded_combat.item.ECGauntletItem;
import com.userofbricks.expanded_combat.item.ECWeaponItem;
import com.userofbricks.expanded_combat.plugins.VanillaECPlugin;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.ItemAttributeModifierEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

import java.util.UUID;

public class EnchantentEvents {

    //TODO: when mobs can use mods weapons as well
    public void HealthStealEvent(LivingDamageEvent event) {
        DamageSource damageSource = event.getSource();
        if (!damageSource.isIndirect() && damageSource.getEntity() instanceof LivingEntity livingEntity && (
                damageSource.is(DamageTypes.GENERIC) || damageSource.is(DamageTypes.GENERIC_KILL)
                ) && livingEntity.getItemInHand(livingEntity.getUsedItemHand()).getItem() instanceof ECWeaponItem weaponItem && weaponItem.getWeapon() == VanillaECPlugin.KATANA) {
            ItemStack weapon = livingEntity.getItemInHand(livingEntity.getUsedItemHand());
            int healthStealLvl = weapon.getEnchantmentLevel(ECEnchantments.BLOCKING.get());
        }
    }


    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void agilityMovementEvent(ItemAttributeModifierEvent event) {
        EquipmentSlot equipmentSlot = event.getSlotType();
        ItemStack stack = event.getItemStack();
        if (stack.getEnchantmentLevel(ECEnchantments.AGILITY.get()) > 0 && Mob.getEquipmentSlotForItem(stack) == equipmentSlot) {
            int level = stack.getEnchantmentLevel(ECEnchantments.AGILITY.get());
            switch (equipmentSlot) {
                case FEET -> event.addModifier(Attributes.MOVEMENT_SPEED, new AttributeModifier(UUID.fromString("33dad864-864b-4dbd-acae-88b72cc358cf"), "Movement Speed", (level * 0.2), AttributeModifier.Operation.MULTIPLY_BASE));
                case LEGS -> event.addModifier(ForgeMod.SWIM_SPEED.get(), new AttributeModifier(UUID.fromString("33dad864-864b-4dbd-acae-88b72cc358cf"), "Jump Strength", (level * 0.2), AttributeModifier.Operation.MULTIPLY_BASE));
                default -> {}
            }
        }
    }

    @SubscribeEvent
    public static void miningSpeed(PlayerEvent.BreakSpeed event) {
        float speedIncrease = 0;
        LazyOptional<ICuriosItemHandler> optionalCuriosInventory = CuriosApi.getCuriosInventory(event.getEntity());
        if(optionalCuriosInventory.resolve().isEmpty()) return;
        ICuriosItemHandler entityCuriosIventory = optionalCuriosInventory.resolve().get();
        for (SlotResult slotResult : entityCuriosIventory.findCurios(stack -> stack.getItem() instanceof ECGauntletItem)) {
            speedIncrease += slotResult.stack().getEnchantmentLevel(ECEnchantments.AGILITY.get()) * 0.2f;
        }
        event.setNewSpeed(event.getOriginalSpeed() - speedIncrease);
    }

    @SubscribeEvent
    public static void agilityDoge(LivingAttackEvent event) {
        LivingEntity entity = event.getEntity();
        ItemStack chestplate = entity.getItemBySlot(EquipmentSlot.CHEST);
        int agility = chestplate.getEnchantmentLevel(ECEnchantments.AGILITY.get());
        if (!chestplate.isEmpty() && agility > 0 && entity.getRandom().nextIntBetweenInclusive(1, Math.round((20 + agility)-(10 * ((float)Math.sqrt(agility)-1)))) == 1) {
            //TODO: movement when
            float move = 0.5f / agility;
            double xMove = entity.getRandom().nextInt(0, 1) == 1 ? -1 : 1;
            double yMove = entity.getRandom().nextInt(0, 1) == 1 ? -1 : 1;
            //entity.push(entity.getRandom().nextInt(0, 1) == 1 ? xMove  : 0, 0.1, entity.getRandom().nextInt(0, 1) == 1 ? yMove  : 0);
            entity.moveRelative(move, entity.position().add(xMove, 0.5d, yMove));
            event.setCanceled(true);
        }
    }
}
