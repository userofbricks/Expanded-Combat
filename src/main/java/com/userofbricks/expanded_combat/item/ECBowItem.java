package com.userofbricks.expanded_combat.item;

import com.userofbricks.expanded_combat.item.materials.Material;
import com.userofbricks.expanded_combat.util.IngredientUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.ForgeEventFactory;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

public class ECBowItem extends BowItem {
    public final Material material;
    @Nullable
    public final Material previosMaterial;

    public ECBowItem(Material material, @Nullable Material previosMaterial, Item.Properties builder) {
        super(builder);
        this.material = material;
        this.previosMaterial = previosMaterial;
    }

    @Override
    public void releaseUsing(@NotNull ItemStack stack, @NotNull Level worldIn, @NotNull LivingEntity entityLiving, int timeLeft) {
        if (entityLiving instanceof Player playerentity) {
            boolean useInfiniteAmmo = playerentity.getAbilities().instabuild || stack.getEnchantmentLevel(Enchantments.INFINITY_ARROWS) > 0;
            ItemStack itemstack = playerentity.getProjectile(stack);
            int charge = this.getUseDuration(stack) - timeLeft;
            charge = ForgeEventFactory.onArrowLoose(stack, worldIn, playerentity, charge, !itemstack.isEmpty() || useInfiniteAmmo);
            if (charge < 0) {
                return;
            }
            if (!itemstack.isEmpty() || useInfiniteAmmo) {
                if (itemstack.isEmpty()) {
                    itemstack = new ItemStack(Items.ARROW);
                }
                float arrowVelocity = this.getBowArrowVelocity(stack, charge);
                this.fireArrows(stack, worldIn, playerentity, itemstack, arrowVelocity);
            }
        }
    }

    private int getMultishotLevel() {
        if (previosMaterial != null) return (this.material.getConfig().offense.multishotLevel / 2) + (this.previosMaterial.getConfig().offense.multishotLevel / 2);
        return this.material.getConfig().offense.multishotLevel;
    }

    public void fireArrows( ItemStack stack,  Level worldIn,  Player playerentity,  ItemStack itemstack,  float arrowVelocity) {
        int multishotLevel = stack.getEnchantmentLevel(Enchantments.MULTISHOT) + this.getMultishotLevel();
        for (int arrowNumber = 0; arrowNumber < 1 + multishotLevel * 2; ++arrowNumber) {
            if (arrowVelocity >= 0.1) {
                boolean hasInfiniteAmmo = playerentity.getAbilities().instabuild || (itemstack.getItem() instanceof ArrowItem && ((ArrowItem)itemstack.getItem()).isInfinite(itemstack, stack, playerentity));
                boolean isAdditionalShot = arrowNumber > 0;
                if (!worldIn.isClientSide) {
                    this.createBowArrow(stack, worldIn, playerentity, itemstack, arrowVelocity, arrowNumber, hasInfiniteAmmo, isAdditionalShot);
                }
                worldIn.playSound(null, playerentity.getX(), playerentity.getY(), playerentity.getZ(), SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 1.0f, 1.0f / (worldIn.getRandom().nextFloat() * 0.4f + 1.2f) + arrowVelocity * 0.5f);
                if (!hasInfiniteAmmo && !playerentity.getAbilities().instabuild && !isAdditionalShot) {
                    itemstack.shrink(1);
                    if (itemstack.isEmpty()) {
                        playerentity.getInventory().removeItem(itemstack);
                    }
                }
                playerentity.awardStat(Stats.ITEM_USED.get(this));
            }
        }
    }

    private int getBowPower() {
        if (previosMaterial != null) return (this.material.getConfig().offense.bowPower / 2) + (this.previosMaterial.getConfig().offense.bowPower / 2);
        return this.material.getConfig().offense.bowPower;
    }

    public void createBowArrow( ItemStack stack,  Level worldIn,  Player playerentity,  ItemStack itemstack,  float arrowVelocity,  int i,  boolean hasInfiniteAmmo,  boolean isAdditionalShot) {
        ArrowItem arrowitem = (ArrowItem)((itemstack.getItem() instanceof ArrowItem) ? itemstack.getItem() : Items.ARROW);
        AbstractArrow abstractarrowentity = arrowitem.createArrow(worldIn, itemstack, playerentity);
        abstractarrowentity = this.customArrow(abstractarrowentity);
        this.setArrowTrajectory(playerentity, arrowVelocity, i, abstractarrowentity);
        if (arrowVelocity == 1.0f) {
            abstractarrowentity.setCritArrow(true);
        }
        int powerLevel = stack.getEnchantmentLevel(Enchantments.POWER_ARROWS) + this.getBowPower();
        if (powerLevel > 0) {
            abstractarrowentity.setBaseDamage(abstractarrowentity.getBaseDamage() + powerLevel * 0.5 + 0.5);
        }
        int k = stack.getEnchantmentLevel(Enchantments.PUNCH_ARROWS);
        if (k > 0) {
            abstractarrowentity.setKnockback(k);
        }
        if (stack.getEnchantmentLevel(Enchantments.FLAMING_ARROWS) > 0) {
            abstractarrowentity.setSecondsOnFire(100);
        }
        stack.hurtAndBreak(1, (LivingEntity)playerentity, p_220009_1_ -> p_220009_1_.broadcastBreakEvent(playerentity.getUsedItemHand()));
        if (hasInfiniteAmmo || (playerentity.getAbilities().instabuild && (itemstack.getItem() == Items.SPECTRAL_ARROW || itemstack.getItem() == Items.TIPPED_ARROW))) {
            abstractarrowentity.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
        }
        if (isAdditionalShot) {
            abstractarrowentity.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
        }
        worldIn.addFreshEntity(abstractarrowentity);
    }

    private float getVelocitiMultiplier() {
        if (previosMaterial != null) return (this.material.getConfig().offense.velocityMultiplier / 2) + (this.previosMaterial.getConfig().offense.velocityMultiplier / 2);
        return this.material.getConfig().offense.velocityMultiplier;
    }

    public void setArrowTrajectory(Player playerentity, float arrowVelocity, int i, AbstractArrow abstractarrowentity) {
        if (i == 0) {
            abstractarrowentity.shootFromRotation(playerentity, playerentity.getXRot(), playerentity.getYRot() + 0.0f, 0.0f, arrowVelocity * this.getVelocitiMultiplier(), 1.0f);
        }
        if (i == 1) {
            abstractarrowentity.shootFromRotation(playerentity, playerentity.getXRot(), playerentity.getYRot() + 10.0f, 0.0f, arrowVelocity * this.getVelocitiMultiplier(), 1.0f);
        }
        if (i == 2) {
            abstractarrowentity.shootFromRotation(playerentity, playerentity.getXRot(), playerentity.getYRot() - 10.0f, 0.0f, arrowVelocity * this.getVelocitiMultiplier(), 1.0f);
        }
        if (i == 3) {
            abstractarrowentity.shootFromRotation(playerentity, playerentity.getXRot(), playerentity.getYRot() + 20.0f, 0.0f, arrowVelocity * this.getVelocitiMultiplier(), 1.0f);
        }
        if (i == 4) {
            abstractarrowentity.shootFromRotation(playerentity, playerentity.getXRot(), playerentity.getYRot() - 20.0f, 0.0f, arrowVelocity * this.getVelocitiMultiplier(), 1.0f);
        }
        if (i == 5) {
            abstractarrowentity.shootFromRotation(playerentity, playerentity.getXRot(), playerentity.getYRot() + 30.0f, 0.0f, arrowVelocity * this.getVelocitiMultiplier(), 1.0f);
        }
        if (i == 6) {
            abstractarrowentity.shootFromRotation(playerentity, playerentity.getXRot(), playerentity.getYRot() - 30.0f, 0.0f, arrowVelocity * this.getVelocitiMultiplier(), 1.0f);
        }
    }

    public float getBowArrowVelocity( ItemStack stack,  int charge) {
        float bowChargeTime = this.getBowChargeTime(stack);
        if (bowChargeTime <= 0.0f) {
            bowChargeTime = 1.0f;
        }
        float arrowVelocity = charge / bowChargeTime;
        arrowVelocity = (arrowVelocity * arrowVelocity + arrowVelocity * 2.0f) / 3.0f;
        if (arrowVelocity > 1.0f) {
            arrowVelocity = 1.0f;
        }
        return arrowVelocity;
    }

    public float getBowChargeTime( ItemStack stack) {
        int quickChargeLevel = stack.getEnchantmentLevel(Enchantments.QUICK_CHARGE);
        return (float)Math.max(20 - 5 * quickChargeLevel, 0);
    }

    private float getMendingBonus() {
        if (previosMaterial != null) return (this.material.getConfig().mendingBonus / 2) + (this.previosMaterial.getConfig().mendingBonus / 2);
        return this.material.getConfig().mendingBonus;
    }

    @Override
    public float getXpRepairRatio( ItemStack stack) {
        return 2.0f + getMendingBonus();
    }

    @OnlyIn(Dist.CLIENT)
    @ParametersAreNonnullByDefault
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> list, TooltipFlag flag) {
        if (this.getMendingBonus() != 0.0f) {
            if (this.getMendingBonus() > 0.0f) {
                list.add(1, Component.translatable("tooltip.expanded_combat.mending_bonus").withStyle(ChatFormatting.GREEN).append(Component.literal(ChatFormatting.GREEN + " +" + ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(this.getMendingBonus()))));
            }
            else if (this.getMendingBonus() < 0.0f) {
                list.add(1, Component.translatable("tooltip.expanded_combat.mending_bonus").withStyle(ChatFormatting.RED).append(Component.literal(ChatFormatting.RED + " " + ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(this.getMendingBonus()))));
            }
        }
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return this.material.getConfig().durability.bowDurability;
    }

    @Override
    public boolean canBeDepleted() {
        return true;
    }

    @Override
    public int getEnchantmentValue(ItemStack stack) {
        return this.material.getConfig().enchanting.offenseEnchantability;
    }

    @Override
    public boolean isValidRepairItem(@NotNull ItemStack toRepair, @NotNull ItemStack repair) {
        return IngredientUtil.getIngrediantFromItemString(this.material.getConfig().crafting.repairItem).test(repair) || super.isValidRepairItem(toRepair, repair);
    }

    @Override
    public boolean isFireResistant() {
        return this.material.getConfig().fireResistant;
    }

    @Override
    public boolean canBeHurtBy(@NotNull DamageSource damageSource) {
        return !this.material.getConfig().fireResistant || !damageSource.is(DamageTypeTags.IS_FIRE);
    }

    public Material getMaterial() {
        return this.material;
    }
    public @Nullable Material getPreviosMaterial() {
        return this.previosMaterial;
    }

    @Override
    public boolean makesPiglinsNeutral(ItemStack stack, LivingEntity wearer) {
        ECBowItem bowItem = ((ECBowItem) stack.getItem());
        return bowItem.getMaterial().getName().equals("Gold") || (bowItem.getPreviosMaterial() != null && bowItem.getPreviosMaterial().getName().equals("Gold"));
    }
}
