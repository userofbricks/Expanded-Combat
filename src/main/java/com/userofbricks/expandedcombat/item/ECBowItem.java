package com.userofbricks.expandedcombat.item;

import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.util.text.ITextComponent;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.stats.Stats;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.item.ArrowItem;
import net.minecraft.util.IItemProvider;
import net.minecraft.item.Items;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.item.BowItem;

public class ECBowItem extends BowItem
{
    private final int multishotLevel;
    private final int bowPower;
    private final float velocityMultiplyer;
    float mendingBonus;
    
    public ECBowItem( float mendingBonus,  float velocityMultiplyer,  Item.Properties builder) {
        super(builder);
        this.velocityMultiplyer = velocityMultiplyer;
        this.multishotLevel = 0;
        this.bowPower = 0;
        this.mendingBonus = mendingBonus;
    }
    
    public ECBowItem( float mendingBonus,  float velocityMultiplyer,  int bowPower,  Item.Properties builder) {
        super(builder);
        this.velocityMultiplyer = velocityMultiplyer;
        this.multishotLevel = 0;
        this.bowPower = bowPower;
        this.mendingBonus = mendingBonus;
    }
    
    public ECBowItem( float mendingBonus,  float velocityMultiplyer,  int bowPower,  int multishotLevel,  Item.Properties builder) {
        super(builder);
        this.velocityMultiplyer = velocityMultiplyer;
        this.multishotLevel = multishotLevel;
        this.bowPower = bowPower;
        this.mendingBonus = mendingBonus;
    }
    
    public ECBowItem( float velocityMultiplyer,  Item.Properties builder) {
        super(builder);
        this.velocityMultiplyer = velocityMultiplyer;
        this.multishotLevel = 0;
        this.bowPower = 0;
        this.mendingBonus = 0.0f;
    }
    
    public ECBowItem( float velocityMultiplyer,  int bowPower,  Item.Properties builder) {
        super(builder);
        this.velocityMultiplyer = velocityMultiplyer;
        this.multishotLevel = 0;
        this.bowPower = bowPower;
        this.mendingBonus = 0.0f;
    }
    
    public ECBowItem( float velocityMultiplyer,  int bowPower,  int multishotLevel,  Item.Properties builder) {
        super(builder);
        this.velocityMultiplyer = velocityMultiplyer;
        this.multishotLevel = multishotLevel;
        this.bowPower = bowPower;
        this.mendingBonus = 0.0f;
    }
    
    public int getMultishotLevel() {
        return this.multishotLevel;
    }
    
    private int getBowPower() {
        return this.bowPower;
    }
    
    public void releaseUsing( ItemStack stack,  World worldIn,  LivingEntity entityLiving,  int timeLeft) {
        if (entityLiving instanceof PlayerEntity) {
             PlayerEntity playerentity = (PlayerEntity)entityLiving;
             boolean useInfiniteAmmo = playerentity.abilities.instabuild || EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, stack) > 0;
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
    
    public void fireArrows( ItemStack stack,  World worldIn,  PlayerEntity playerentity,  ItemStack itemstack,  float arrowVelocity) {
         int multishotLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.MULTISHOT, stack) + this.getMultishotLevel();
        for (int arrowsToFire = 1 + multishotLevel * 2, arrowNumber = 0; arrowNumber < arrowsToFire; ++arrowNumber) {
            if (arrowVelocity >= 0.1) {
                 boolean hasInfiniteAmmo = playerentity.abilities.instabuild || (itemstack.getItem() instanceof ArrowItem && ((ArrowItem)itemstack.getItem()).isInfinite(itemstack, stack, playerentity));
                 boolean isAdditionalShot = arrowNumber > 0;
                if (!worldIn.isClientSide) {
                    this.createBowArrow(stack, worldIn, playerentity, itemstack, arrowVelocity, arrowNumber, hasInfiniteAmmo, isAdditionalShot);
                }
                worldIn.playSound((PlayerEntity)null, playerentity.getX(), playerentity.getY(), playerentity.getZ(), SoundEvents.ARROW_SHOOT, SoundCategory.PLAYERS, 1.0f, 1.0f / (ECBowItem.random.nextFloat() * 0.4f + 1.2f) + arrowVelocity * 0.5f);
                if (!hasInfiniteAmmo && !playerentity.abilities.instabuild && !isAdditionalShot) {
                    itemstack.shrink(1);
                    if (itemstack.isEmpty()) {
                        playerentity.inventory.removeItem(itemstack);
                    }
                }
                playerentity.awardStat(Stats.ITEM_USED.get(this));
            }
        }
    }
    
    public void createBowArrow( ItemStack stack,  World worldIn,  PlayerEntity playerentity,  ItemStack itemstack,  float arrowVelocity,  int i,  boolean hasInfiniteAmmo,  boolean isAdditionalShot) {
         ArrowItem arrowitem = (ArrowItem)((itemstack.getItem() instanceof ArrowItem) ? itemstack.getItem() : Items.ARROW);
        AbstractArrowEntity abstractarrowentity = arrowitem.createArrow(worldIn, itemstack, (LivingEntity)playerentity);
        abstractarrowentity = this.customArrow(abstractarrowentity);
        this.setArrowTrajectory(playerentity, arrowVelocity, i, abstractarrowentity);
        if (arrowVelocity == 1.0f) {
            abstractarrowentity.setCritArrow(true);
        }
         int powerLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, stack) + this.getBowPower();
        if (powerLevel > 0) {
            abstractarrowentity.setBaseDamage(abstractarrowentity.getBaseDamage() + powerLevel * 0.5 + 0.5);
        }
         int k = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PUNCH_ARROWS, stack);
        if (k > 0) {
            abstractarrowentity.setKnockback(k);
        }
        if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FLAMING_ARROWS, stack) > 0) {
            abstractarrowentity.setSecondsOnFire(100);
        }
        stack.hurtAndBreak(1, (LivingEntity)playerentity, p_220009_1_ -> p_220009_1_.broadcastBreakEvent(playerentity.getUsedItemHand()));
        if (hasInfiniteAmmo || (playerentity.abilities.instabuild && (itemstack.getItem() == Items.SPECTRAL_ARROW || itemstack.getItem() == Items.TIPPED_ARROW))) {
            abstractarrowentity.pickup = AbstractArrowEntity.PickupStatus.CREATIVE_ONLY;
        }
        if (isAdditionalShot) {
            abstractarrowentity.pickup = AbstractArrowEntity.PickupStatus.CREATIVE_ONLY;
        }
        worldIn.addFreshEntity((Entity)abstractarrowentity);
    }
    
    public void setArrowTrajectory( PlayerEntity playerentity,  float arrowVelocity,  int i,  AbstractArrowEntity abstractarrowentity) {
        if (i == 0) {
            abstractarrowentity.shootFromRotation(playerentity, playerentity.xRot, playerentity.yRot + 0.0f, 0.0f, arrowVelocity * this.velocityMultiplyer, 1.0f);
        }
        if (i == 1) {
            abstractarrowentity.shootFromRotation(playerentity, playerentity.xRot, playerentity.yRot + 10.0f, 0.0f, arrowVelocity * this.velocityMultiplyer, 1.0f);
        }
        if (i == 2) {
            abstractarrowentity.shootFromRotation(playerentity, playerentity.xRot, playerentity.yRot - 10.0f, 0.0f, arrowVelocity * this.velocityMultiplyer, 1.0f);
        }
        if (i == 3) {
            abstractarrowentity.shootFromRotation(playerentity, playerentity.xRot, playerentity.yRot + 20.0f, 0.0f, arrowVelocity * this.velocityMultiplyer, 1.0f);
        }
        if (i == 4) {
            abstractarrowentity.shootFromRotation(playerentity, playerentity.xRot, playerentity.yRot - 20.0f, 0.0f, arrowVelocity * this.velocityMultiplyer, 1.0f);
        }
        if (i == 5) {
            abstractarrowentity.shootFromRotation(playerentity, playerentity.xRot, playerentity.yRot + 30.0f, 0.0f, arrowVelocity * this.velocityMultiplyer, 1.0f);
        }
        if (i == 6) {
            abstractarrowentity.shootFromRotation(playerentity, playerentity.xRot, playerentity.yRot - 30.0f, 0.0f, arrowVelocity * this.velocityMultiplyer, 1.0f);
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
         int quickChargeLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.QUICK_CHARGE, stack);
        return (float)Math.max(20 - 5 * quickChargeLevel, 0);
    }
    
    public float getXpRepairRatio( ItemStack stack) {
        return 2.0f + this.mendingBonus;
    }
    
    @OnlyIn(Dist.CLIENT)
    @ParametersAreNonnullByDefault
    public void appendHoverText( ItemStack stack, @Nullable  World world,  List<ITextComponent> list,  ITooltipFlag flag) {
        if (this.mendingBonus != 0.0f) {
            if (this.mendingBonus > 0.0f) {
                list.add(new StringTextComponent(TextFormatting.GREEN + "Mending Bonus +" + ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(this.mendingBonus)));
            }
            else if (this.mendingBonus < 0.0f) {
                list.add(new StringTextComponent(TextFormatting.RED + "Mending Bonus " + ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(this.mendingBonus)));
            }
        }
    }
}
