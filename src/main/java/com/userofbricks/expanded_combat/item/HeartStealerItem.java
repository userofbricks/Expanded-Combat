package com.userofbricks.expanded_combat.item;

import com.userofbricks.expanded_combat.init.ECBasePlugin;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.item.ItemExpireEvent;

import javax.annotation.ParametersAreNonnullByDefault;

import static com.userofbricks.expanded_combat.ExpandedCombat.modLoc;
import static com.userofbricks.expanded_combat.init.DataAttachments.ADDED_HEALTH;
import static com.userofbricks.expanded_combat.init.DataAttachments.STOLEN_HEALTH;
import static com.userofbricks.expanded_combat.init.ItemDataComponents.CHARGE;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
@EventBusSubscriber
public class HeartStealerItem extends ECWeaponItem{
    public HeartStealerItem(Properties properties) {
        super(ECBasePlugin.HEART_STEALER, ECBasePlugin.CLAYMORE, properties.component(CHARGE, 0), 2);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (this.getMaxDamage(stack) - this.getDamage(stack) <= 1) return false;
        int charge = stack.getOrDefault(CHARGE, 0);
        if (charge >= 500 && target.getMaxHealth() >= this.getDamage() && attacker.level().random.nextInt((int)(Math.round(Math.sqrt(attacker.getData(ADDED_HEALTH)+Math.pow(attacker.getData(STOLEN_HEALTH), 3))))+1) <= 1) {
            stack.set(CHARGE, 0);
            attacker.setData(STOLEN_HEALTH, attacker.getData(STOLEN_HEALTH) + 1);
            if (target instanceof Player && (target.getMaxHealth() + target.getData(ADDED_HEALTH) > 10)) {
                attacker.setData(ADDED_HEALTH, attacker.getData(ADDED_HEALTH) - 1);
            }
        } else {
            stack.set(CHARGE, charge + 1);
        }
        return super.hurtEnemy(stack, target, attacker);
    }
    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack stack = pPlayer.getItemInHand(pUsedHand);
        if (this.getMaxDamage(stack) - this.getDamage(stack) <= 1)
            return InteractionResultHolder.fail(stack);
        return super.use(pLevel, pPlayer, pUsedHand);
    }

    int tick = 0;
    public static ResourceLocation IncreasedHeartSteelerDmg = modLoc("heart_stealer_dmg");
    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int pi, boolean pb) {
        super.inventoryTick(stack, level, entity, pi, pb);
        if (entity instanceof Player player && tick % 5 == 0) {
            int stolenHearts = player.getData(STOLEN_HEALTH);
            int addedDmg = stolenHearts / 5;
            ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder();
            ItemAttributeModifiers itemMods = stack.get(DataComponents.ATTRIBUTE_MODIFIERS);
            if (itemMods != null) {
                for (ItemAttributeModifiers.Entry entry : itemMods.modifiers()) {
                    if (entry.matches(Attributes.ATTACK_DAMAGE, IncreasedHeartSteelerDmg))
                        continue;
                    builder.add(entry.attribute(), entry.modifier(), entry.slot());
                }
            }
            builder.add(Attributes.ATTACK_DAMAGE, new AttributeModifier(IncreasedHeartSteelerDmg, addedDmg, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);

            stack.set(DataComponents.ATTRIBUTE_MODIFIERS,  builder.build());
        }
        tick++;
    }

    @SubscribeEvent
    public static void onItemExpire(ItemExpireEvent event) {
        if (event.getEntity().getItem().getItem() instanceof HeartStealerItem) {
            event.addExtraLife(12000);
        }
    }
}
