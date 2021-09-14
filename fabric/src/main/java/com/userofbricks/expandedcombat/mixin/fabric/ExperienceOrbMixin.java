package com.userofbricks.expandedcombat.mixin.fabric;

import com.userofbricks.expandedcombat.item.ICustomMendingRatio;
import com.userofbricks.expandedcombat.util.ItemAndTagsUtil;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(ExperienceOrb.class)
public abstract class ExperienceOrbMixin extends Entity {
    @Shadow private int value;

    @Shadow protected abstract int durabilityToXp(int i);

    public ExperienceOrbMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = { "playerTouch" }, at = { @At("HEAD") }, cancellable = true)
    private void getMaxDamage(Player player, CallbackInfo ci) {
        if (!this.level.isClientSide && player.takeXpDelay == 0) {
            Map.Entry<EquipmentSlot, ItemStack> entry = EnchantmentHelper.getRandomItemWith(Enchantments.MENDING, player, ItemStack::isDamaged);
            if (entry != null) {
                ItemStack itemstack = entry.getValue();
                boolean customMending = false;
                double mendingMultiplier = 2;
                if (ItemAndTagsUtil.doesGoldMendingContainItem(itemstack)) {
                    customMending = true;
                    mendingMultiplier = 4;
                } else if (itemstack.getItem() instanceof ICustomMendingRatio) {
                    mendingMultiplier = ((ICustomMendingRatio)itemstack.getItem()).getXpRepairRatio(itemstack);
                }
                if (customMending) {
                    player.takeXpDelay = 2;
                    player.take(this, 1);
                    if (!itemstack.isEmpty() && itemstack.isDamaged()) {
                        int repairedDamage = (int)Math.min((double)this.value * mendingMultiplier, itemstack.getDamageValue());
                        this.value -= durabilityToXp(repairedDamage);
                        itemstack.setDamageValue(itemstack.getDamageValue() - repairedDamage);
                    }
                    if (this.value > 0) {
                        player.giveExperiencePoints(this.value);
                    }
                    this.kill();
                }
            }
        }
    }
}
