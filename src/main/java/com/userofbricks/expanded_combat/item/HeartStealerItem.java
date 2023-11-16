package com.userofbricks.expanded_combat.item;

import com.userofbricks.expanded_combat.item.materials.plugins.VanillaECPlugin;
import com.userofbricks.expanded_combat.network.ECVariables;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Consumer;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class HeartStealerItem extends ECWeaponItem{
    public static final String chargeString = "charge";
    public HeartStealerItem(Properties properties) {
        super(VanillaECPlugin.NETHERITE, VanillaECPlugin.CLAYMORE, properties, 2);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (this.getMaxDamage(stack) - this.getDamage(stack) <= 1) return false;
        int charge = stack.getOrCreateTag().getInt(chargeString);
        if (charge >= 500 && target.getMaxHealth() >= this.getDamage() && attacker.level().random.nextInt((int)(Math.round(Math.sqrt(ECVariables.getAddedHealth(attacker)^3)))+1) == 0) {
            stack.getOrCreateTag().putInt(chargeString, 0);
            ECVariables.addToStolenHealth(attacker, 1);
        } else {
            stack.getOrCreateTag().putInt(chargeString, charge + 1);
        }
        return super.hurtEnemy(stack, target, attacker);
    }
    @Override
    public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, T entity, Consumer<T> onBroken) {
        if (this.getMaxDamage(stack) - this.getDamage(stack) <= 1) return 0;
        return super.damageItem(stack, amount, entity, onBroken);
    }
    public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
        if (entity.getAge() != -32768) entity.setUnlimitedLifetime();
        return false;
    }
    public boolean onDroppedByPlayer(ItemStack item, Player player) {
        return ECVariables.getStolenHealth(player) <= 10;
    }
}
