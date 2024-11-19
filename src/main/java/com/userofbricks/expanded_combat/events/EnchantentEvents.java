package com.userofbricks.expanded_combat.events;

import com.userofbricks.expanded_combat.init.ECEnchantments;
import com.userofbricks.expanded_combat.item.ArrowBlockWeaponItem;
import com.userofbricks.expanded_combat.item.GauntletItem;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.event.ItemAttributeModifierEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

import java.util.Optional;

import static com.userofbricks.expanded_combat.ExpandedCombat.modLoc;

@SuppressWarnings("unused")
public class EnchantentEvents {

    //TODO: when mobs can use mods weapons as well
    public void HealthStealEvent(LivingDamageEvent.Pre event) {
        DamageSource damageSource = event.getSource();
        if (damageSource.isDirect() && damageSource.getEntity() instanceof LivingEntity livingEntity && (
                damageSource.is(DamageTypes.GENERIC) || damageSource.is(DamageTypes.GENERIC_KILL)
                ) && livingEntity.getItemInHand(livingEntity.getUsedItemHand()).getItem() instanceof ArrowBlockWeaponItem) {
            ItemStack weapon = livingEntity.getItemInHand(livingEntity.getUsedItemHand());
            int healthStealLvl = weapon.getEnchantmentLevel(event.getEntity().level().registryAccess().registryOrThrow(Registries.ENCHANTMENT).getHolderOrThrow(ECEnchantments.BLOCKING));
        }
    }


    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void agilityAttributesEvent(ItemAttributeModifierEvent event) {
        ItemStack stack = event.getItemStack();
        assert Minecraft.getInstance().level != null;
        Registry<Enchantment> enchantmentRegistry = Minecraft.getInstance().level.registryAccess().registryOrThrow(Registries.ENCHANTMENT);
        if (stack.getEnchantmentLevel(enchantmentRegistry.getHolderOrThrow(ECEnchantments.AGILITY)) > 0 && stack.getItem() instanceof ArmorItem armorItem) {
            int level = stack.getEnchantmentLevel(enchantmentRegistry.getHolderOrThrow(ECEnchantments.AGILITY));
            if (armorItem.getEquipmentSlot() == EquipmentSlot.FEET)
                event.addModifier(Attributes.MOVEMENT_SPEED, new AttributeModifier(modLoc("movement_speed"), (level * 0.2), AttributeModifier.Operation.ADD_MULTIPLIED_BASE), EquipmentSlotGroup.FEET);
            if (armorItem.getEquipmentSlot() == EquipmentSlot.LEGS)
                event.addModifier(NeoForgeMod.SWIM_SPEED, new AttributeModifier(modLoc("jump_strength"), (level * 0.2), AttributeModifier.Operation.ADD_MULTIPLIED_BASE), EquipmentSlotGroup.LEGS);
        }
    }

    @SubscribeEvent
    public static void miningSpeed(PlayerEvent.BreakSpeed event) {
        Optional<ICuriosItemHandler> handlerOptional = CuriosApi.getCuriosInventory(event.getEntity());
        if (handlerOptional.isPresent()) {
            float speedIncrease = 0;
            Registry<Enchantment> enchantmentRegistry = event.getEntity().level().registryAccess().registryOrThrow(Registries.ENCHANTMENT);
            for (SlotResult slotResult : handlerOptional.get().findCurios(stack -> stack.getItem() instanceof GauntletItem)) {
                speedIncrease += slotResult.stack().getEnchantmentLevel(enchantmentRegistry.getHolderOrThrow(ECEnchantments.AGILITY)) * 0.2f;
            }
            event.setNewSpeed(event.getOriginalSpeed() - speedIncrease);
        }
    }

    @SubscribeEvent
    public static void agilityDoge(LivingIncomingDamageEvent event) {
        LivingEntity entity = event.getEntity();
        ItemStack chestplate = entity.getItemBySlot(EquipmentSlot.CHEST);
        Registry<Enchantment> enchantmentRegistry = event.getEntity().level().registryAccess().registryOrThrow(Registries.ENCHANTMENT);
        int agility = chestplate.getEnchantmentLevel(enchantmentRegistry.getHolderOrThrow(ECEnchantments.AGILITY));
        if (!chestplate.isEmpty() && agility > 0 && entity.getRandom().nextIntBetweenInclusive(1, Math.round((20 + agility)-(10 * ((float)Math.sqrt(agility)-1)))) == 1) {
            //TODO: movement when
            float move = 1f / agility;
            double xMove = entity.getRandom().nextInt(0, 1) == 1 ? -1 : 1;
            double yMove = entity.getRandom().nextInt(0, 1) == 1 ? -1 : 1;
            entity.addDeltaMovement(new Vec3(xMove * move, 0.5d, yMove * move));
            event.setCanceled(true);
        }
    }
}
