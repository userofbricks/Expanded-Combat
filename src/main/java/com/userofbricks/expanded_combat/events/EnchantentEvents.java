package com.userofbricks.expanded_combat.events;

import com.userofbricks.expanded_combat.enchentments.ECEnchantments;
import com.userofbricks.expanded_combat.item.ECGauntletItem;
import com.userofbricks.expanded_combat.item.ECWeaponItem;
import com.userofbricks.expanded_combat.item.materials.plugins.VanillaECPlugin;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.ItemAttributeModifierEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

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


    /**
     * Might wnat agility to require presurized air or xp
     */
    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void agilityDashingEvent(ItemAttributeModifierEvent event) {
        EquipmentSlot equipmentSlot = event.getSlotType();
        ItemStack stack = event.getItemStack();
        if (stack.getEnchantmentLevel(ECEnchantments.AGILITY.get()) > 0) {
            int level = stack.getEnchantmentLevel(ECEnchantments.AGILITY.get());
            switch (equipmentSlot) {
                case FEET -> event.addModifier(Attributes.MOVEMENT_SPEED, new AttributeModifier("Movement Speed", level * 0.1, AttributeModifier.Operation.ADDITION));
                case LEGS -> event.addModifier(Attributes.JUMP_STRENGTH, new AttributeModifier("Jump Strength", level * 0.1, AttributeModifier.Operation.ADDITION));
                default -> {}
            }
        }
    }

    @SubscribeEvent
    public static void miningSpeed(PlayerEvent.BreakSpeed event) {
        float speedIncrease = 0;
        for (SlotResult slotResult : CuriosApi.getCuriosHelper().findCurios(event.getEntity(), stack -> stack.getItem() instanceof ECGauntletItem)) {
            speedIncrease += slotResult.stack().getEnchantmentLevel(ECEnchantments.AGILITY.get());// * 0.1f;
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
            //double move = 10.0 / agility;
            //double xMove = entity.getRandom().nextInt(0, 1) == 1 ? 0-move : move;
            //double yMove = entity.getRandom().nextInt(0, 1) == 1 ? 0-move : move;
            //entity.move(MoverType.SELF, new Vec3(entity.getRandom().nextInt(0, 1) == 1 ? xMove  : 0, 0.1, entity.getRandom().nextInt(0, 1) == 1 ? yMove  : 0));
            event.setCanceled(true);
        }
    }
}
