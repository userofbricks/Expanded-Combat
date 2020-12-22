package com.userofbricks.expandedcombat.item;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.stats.Stats;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.ForgeEventFactory;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

public class ECBowItem extends BowItem {
    private int multishotLevel;
    private int bowPower;
    private final float velocityMultiplyer;
    float mendingBonus;
    public ECBowItem(float mendingBonus, float velocityMultiplyer, Properties builder) {
        super(builder);
        this.velocityMultiplyer = velocityMultiplyer;
        this.multishotLevel = 0;
        this.bowPower = 0;
        this.mendingBonus = mendingBonus;
    }
    public ECBowItem(float mendingBonus, float velocityMultiplyer, int bowPower, Properties builder) {
        super(builder);
        this.velocityMultiplyer = velocityMultiplyer;
        this.multishotLevel = 0;
        this.bowPower = bowPower;
        this.mendingBonus = mendingBonus;
    }
    public ECBowItem(float mendingBonus, float velocityMultiplyer, int bowPower, int multishotLevel, Properties builder) {
        super(builder);
        this.velocityMultiplyer = velocityMultiplyer;
        this.multishotLevel = multishotLevel;
        this.bowPower = bowPower;
        this.mendingBonus = mendingBonus;
    }

    public ECBowItem(float velocityMultiplyer, Properties builder) {
        super(builder);
        this.velocityMultiplyer = velocityMultiplyer;
        this.multishotLevel = 0;
        this.bowPower = 0;
        this.mendingBonus = 0;
    }
    public ECBowItem(float velocityMultiplyer, int bowPower, Properties builder) {
        super(builder);
        this.velocityMultiplyer = velocityMultiplyer;
        this.multishotLevel = 0;
        this.bowPower = bowPower;
        this.mendingBonus = 0;
    }
    public ECBowItem(float velocityMultiplyer, int bowPower, int multishotLevel, Properties builder) {
        super(builder);
        this.velocityMultiplyer = velocityMultiplyer;
        this.multishotLevel = multishotLevel;
        this.bowPower = bowPower;
        this.mendingBonus = 0;
    }

    public int getMultishotLevel(){
        return this.multishotLevel;
    }

    private int getBowPower() {
        return bowPower;
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
        if (entityLiving instanceof PlayerEntity) {
            PlayerEntity playerentity = (PlayerEntity)entityLiving;
            boolean useInfiniteAmmo = playerentity.abilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, stack) > 0;
            ItemStack itemstack = playerentity.findAmmo(stack);

            int charge = this.getUseDuration(stack) - timeLeft;
            charge = ForgeEventFactory.onArrowLoose(stack, worldIn, playerentity, charge, !itemstack.isEmpty() || useInfiniteAmmo);
            if (charge < 0) return;

            if (!itemstack.isEmpty() || useInfiniteAmmo) {
                if (itemstack.isEmpty()) {
                    itemstack = new ItemStack(Items.ARROW);
                }

                float arrowVelocity = this.getBowArrowVelocity(stack, charge);
                this.fireArrows(stack, worldIn, playerentity, itemstack, arrowVelocity);
            }
        }
    }

    public void fireArrows(ItemStack stack, World worldIn, PlayerEntity playerentity, ItemStack itemstack, float arrowVelocity) {
        int multishotLevel = EnchantmentHelper.getEnchantmentLevel(Enchantments.MULTISHOT, stack) + this.getMultishotLevel();
        int arrowsToFire = 1 + (multishotLevel * 2);

        for(int arrowNumber = 0; arrowNumber < arrowsToFire; arrowNumber++){
            if (!((double)arrowVelocity < 0.1D)) {
                boolean hasInfiniteAmmo = playerentity.abilities.isCreativeMode || (itemstack.getItem() instanceof ArrowItem && ((ArrowItem)itemstack.getItem()).isInfinite(itemstack, stack, playerentity));
                boolean isAdditionalShot = arrowNumber > 0;
                if (!worldIn.isRemote) {
                    this.createBowArrow(stack, worldIn, playerentity, itemstack, arrowVelocity, arrowNumber, hasInfiniteAmmo, isAdditionalShot);
                }

                worldIn.playSound((PlayerEntity)null, playerentity.getPosX(), playerentity.getPosY(), playerentity.getPosZ(), SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F) + arrowVelocity * 0.5F);
                if (!hasInfiniteAmmo && !playerentity.abilities.isCreativeMode && !isAdditionalShot) {
                    itemstack.shrink(1);
                    if (itemstack.isEmpty()) {
                        playerentity.inventory.deleteStack(itemstack);
                    }
                }

                playerentity.addStat(Stats.ITEM_USED.get(this));
            }
        }
    }

    public void createBowArrow(ItemStack stack, World worldIn, PlayerEntity playerentity, ItemStack itemstack, float arrowVelocity, int i, boolean hasInfiniteAmmo, boolean isAdditionalShot) {
        ArrowItem arrowitem = (ArrowItem)(itemstack.getItem() instanceof ArrowItem ? itemstack.getItem() : Items.ARROW);
        AbstractArrowEntity abstractarrowentity = arrowitem.createArrow(worldIn, itemstack, playerentity);
        abstractarrowentity = this.customArrow(abstractarrowentity);
        this.setArrowTrajectory(playerentity, arrowVelocity, i, abstractarrowentity);
        if (arrowVelocity == 1.0F) {
            abstractarrowentity.setIsCritical(true);
        }

        int powerLevel = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, stack) + this.getBowPower();
        if (powerLevel > 0) {
            abstractarrowentity.setDamage(abstractarrowentity.getDamage() + (double)powerLevel * 0.5D + 0.5D);
        }

        int k = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH, stack);
        if (k > 0) {
            abstractarrowentity.setKnockbackStrength(k);
        }

        if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME, stack) > 0) {
            abstractarrowentity.setFire(100);
        }

        stack.damageItem(1, playerentity, (p_220009_1_) -> {
            p_220009_1_.sendBreakAnimation(playerentity.getActiveHand());
        });
        if (hasInfiniteAmmo || playerentity.abilities.isCreativeMode && (itemstack.getItem() == Items.SPECTRAL_ARROW || itemstack.getItem() == Items.TIPPED_ARROW)) {
            abstractarrowentity.pickupStatus = AbstractArrowEntity.PickupStatus.CREATIVE_ONLY;
        }
        if(isAdditionalShot){
            abstractarrowentity.pickupStatus = AbstractArrowEntity.PickupStatus.CREATIVE_ONLY;
        }

        worldIn.addEntity(abstractarrowentity);
    }

    public void setArrowTrajectory(PlayerEntity playerentity, float arrowVelocity, int i, AbstractArrowEntity abstractarrowentity) {
        if(i == 0) abstractarrowentity.func_234612_a_(playerentity, playerentity.rotationPitch, playerentity.rotationYaw + 00.0F, 0.0F, arrowVelocity * velocityMultiplyer, 1.0F);
        if(i == 1) abstractarrowentity.func_234612_a_(playerentity, playerentity.rotationPitch, playerentity.rotationYaw + 10.0F, 0.0F, arrowVelocity * velocityMultiplyer, 1.0F);
        if(i == 2) abstractarrowentity.func_234612_a_(playerentity, playerentity.rotationPitch, playerentity.rotationYaw - 10.0F, 0.0F, arrowVelocity * velocityMultiplyer, 1.0F);
        if(i == 3) abstractarrowentity.func_234612_a_(playerentity, playerentity.rotationPitch, playerentity.rotationYaw + 20.0F, 0.0F, arrowVelocity * velocityMultiplyer, 1.0F);
        if(i == 4) abstractarrowentity.func_234612_a_(playerentity, playerentity.rotationPitch, playerentity.rotationYaw - 20.0F, 0.0F, arrowVelocity * velocityMultiplyer, 1.0F);
        if(i == 5) abstractarrowentity.func_234612_a_(playerentity, playerentity.rotationPitch, playerentity.rotationYaw + 30.0F, 0.0F, arrowVelocity * velocityMultiplyer, 1.0F);
        if(i == 6) abstractarrowentity.func_234612_a_(playerentity, playerentity.rotationPitch, playerentity.rotationYaw - 30.0F, 0.0F, arrowVelocity * velocityMultiplyer, 1.0F);
    }

    public float getBowArrowVelocity(ItemStack stack, int charge) {
        float bowChargeTime = getBowChargeTime(stack);
        if(bowChargeTime <= 0){
            bowChargeTime = 1;
        }
        float arrowVelocity = (float)charge / bowChargeTime;
        arrowVelocity = (arrowVelocity * arrowVelocity + arrowVelocity * 2.0F) / 3.0F;
        if (arrowVelocity > 1.0F) {
            arrowVelocity = 1.0F;
        }

        return arrowVelocity;
    }

    public float getBowChargeTime(ItemStack stack){
        int quickChargeLevel = EnchantmentHelper.getEnchantmentLevel(Enchantments.QUICK_CHARGE, stack);
        return Math.max(20 - 5 * quickChargeLevel, 0);
    }

    @Override
    public float getXpRepairRatio(ItemStack stack) {
        return 2 + mendingBonus;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    @ParametersAreNonnullByDefault
    public void addInformation(ItemStack stack, @Nullable World world, List<ITextComponent> list, ITooltipFlag flag)
    {
        if (mendingBonus != 0) {
            if(mendingBonus > 0){
                list.add(new StringTextComponent(TextFormatting.GREEN + ("Mending Bonus +" + ItemStack.DECIMALFORMAT.format(mendingBonus))));
            }else if (mendingBonus < 0) {
                list.add(new StringTextComponent(TextFormatting.RED + ("Mending Bonus " + ItemStack.DECIMALFORMAT.format(mendingBonus))));
            }
        }
    }
}
