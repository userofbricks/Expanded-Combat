package com.userofbricks.expanded_combat.item;

import com.userofbricks.expanded_combat.init.Materials;
import com.userofbricks.expanded_combat.init.WeaponTypes;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.item.ItemExpireEvent;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import static com.userofbricks.expanded_combat.init.DataAttachments.ADDED_HEALTH;
import static com.userofbricks.expanded_combat.init.DataAttachments.STOLEN_HEALTH;
import static com.userofbricks.expanded_combat.init.ItemDataComponents.CHARGE;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
@EventBusSubscriber
public class HeartStealerItem extends ECWeaponItem{
    public static final String chargeString = "charge";
    public HeartStealerItem(Properties properties) {
        super(Materials.HEART_STEALER, WeaponTypes.CLAYMORE, properties.component(CHARGE, 0), 2);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (this.getMaxDamage(stack) - this.getDamage(stack) <= 1) return false;
        int charge = stack.getOrDefault(CHARGE, 0);
        if (charge >= 500 && target.getMaxHealth() >= this.getDamage() && attacker.level().random.nextInt((int)(Math.round(Math.sqrt((ECVariables.getAddedHealth(attacker)+ECVariables.getStolenHealth(attacker))^3)))+1) == 0) {
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
    public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, @Nullable T entity, Runnable onBroken) {
        if (this.getMaxDamage(stack) - this.getDamage(stack) <= 1) return 0;
        return super.damageItem(stack, amount, entity, onBroken);
    }
    public boolean onDroppedByPlayer(ItemStack item, Player player) {
        return player.getData(STOLEN_HEALTH) <= 5;
    }

    @SubscribeEvent
    public static void onItemExpire(ItemExpireEvent event) {
        if (event.getEntity().getItem().getItem() instanceof HeartStealerItem) {
            event.setCanceled(true);
        }
    }
}
