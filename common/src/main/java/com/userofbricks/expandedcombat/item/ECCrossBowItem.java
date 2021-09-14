package com.userofbricks.expandedcombat.item;

import com.google.common.collect.Lists;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.CrossbowAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Objects;
import java.util.Random;

public class ECCrossBowItem extends CrossbowItem implements ICustomMendingRatio
{
    private int multishotLevel;
    private int bowPower;
    private final float velocityMultiplyer;
    float mendingBonus;
    public boolean startSoundPlayed;
    public boolean midLoadSoundPlayed;
    
    public ECCrossBowItem(float mendingBonus, float velocityMultiplyer, Properties builder) {
        super(builder);
        this.startSoundPlayed = false;
        this.midLoadSoundPlayed = false;
        this.velocityMultiplyer = velocityMultiplyer;
        this.multishotLevel = 0;
        this.bowPower = 0;
        this.mendingBonus = mendingBonus;
    }
    
    public ECCrossBowItem(float mendingBonus, float velocityMultiplyer, int bowPower, Properties builder) {
        super(builder);
        this.startSoundPlayed = false;
        this.midLoadSoundPlayed = false;
        this.velocityMultiplyer = velocityMultiplyer;
        this.multishotLevel = 0;
        this.bowPower = bowPower;
        this.mendingBonus = mendingBonus;
    }
    
    public ECCrossBowItem(float mendingBonus, float velocityMultiplyer, int bowPower, int multishotLevel, Properties builder) {
        super(builder);
        this.startSoundPlayed = false;
        this.midLoadSoundPlayed = false;
        this.velocityMultiplyer = velocityMultiplyer;
        this.multishotLevel = multishotLevel;
        this.bowPower = bowPower;
        this.mendingBonus = mendingBonus;
    }
    
    public ECCrossBowItem(float velocityMultiplyer, Properties builder) {
        super(builder);
        this.startSoundPlayed = false;
        this.midLoadSoundPlayed = false;
        this.velocityMultiplyer = velocityMultiplyer;
        this.multishotLevel = 0;
        this.bowPower = 0;
        this.mendingBonus = 0.0f;
    }
    
    public ECCrossBowItem(float velocityMultiplyer, int bowPower, Properties builder) {
        super(builder);
        this.startSoundPlayed = false;
        this.midLoadSoundPlayed = false;
        this.velocityMultiplyer = velocityMultiplyer;
        this.multishotLevel = 0;
        this.bowPower = bowPower;
        this.mendingBonus = 0.0f;
    }
    
    public ECCrossBowItem(float velocityMultiplyer, int bowPower, int multishotLevel, Properties builder) {
        super(builder);
        this.startSoundPlayed = false;
        this.midLoadSoundPlayed = false;
        this.velocityMultiplyer = velocityMultiplyer;
        this.multishotLevel = multishotLevel;
        this.bowPower = bowPower;
        this.mendingBonus = 0.0f;
    }
    
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
         ItemStack itemstack = playerIn.getItemInHand(handIn);
        if (isCharged(itemstack)) {
            this.fireCrossbowProjectiles(worldIn, playerIn, handIn, itemstack, this.getProjectileVelocity(itemstack), 1.0f);
            CrossbowItem.setCharged(itemstack, false);
            return InteractionResultHolder.consume(itemstack);
        }
        if (!playerIn.getProjectile(itemstack).isEmpty()) {
            if (!isCharged(itemstack)) {
                this.startSoundPlayed = false;
                this.midLoadSoundPlayed = false;
                playerIn.startUsingItem(handIn);
            }
            return InteractionResultHolder.consume(itemstack);
        }
        return InteractionResultHolder.fail(itemstack);
    }
    
    public void onUseTick(Level world, LivingEntity livingEntity, ItemStack stack, int timeLeft) {
        if (!world.isClientSide) {
             int quickChargeLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.QUICK_CHARGE, stack);
             SoundEvent quickChargeSoundEvent = this.getCrossbowSoundEvent(quickChargeLevel);
             SoundEvent loadingMiddleSoundEvent = (quickChargeLevel == 0) ? SoundEvents.CROSSBOW_LOADING_MIDDLE : null;
             float chargeTime = (stack.getUseDuration() - timeLeft) / (float)this.getCrossbowChargeTime(stack);
            if (chargeTime < 0.2f) {
                this.startSoundPlayed = false;
                this.midLoadSoundPlayed = false;
            }
            if (chargeTime >= 0.2f && !this.startSoundPlayed && chargeTime < 1.0f) {
                this.startSoundPlayed = true;
                world.playSound(null, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), quickChargeSoundEvent, SoundSource.PLAYERS, 0.5f, 1.0f);
            }
            if (chargeTime >= 0.5f && loadingMiddleSoundEvent != null && !this.midLoadSoundPlayed && chargeTime < 1.0f) {
                this.midLoadSoundPlayed = true;
                world.playSound(null, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), loadingMiddleSoundEvent, SoundSource.PLAYERS, 0.5f, 1.0f);
            }
        }
    }
    
    public void releaseUsing( ItemStack stack,  Level worldIn,  LivingEntity entityLiving,  int timeLeft) {
         int i = this.getUseDuration(stack) - timeLeft;
         float f = this.getCrossbowCharge(i, stack);
        if (f >= 1.0f && !isCharged(stack) && this.tryLoadProjectiles(entityLiving, stack)) {
            setCharged(stack, true);
            SoundSource soundcategory = (entityLiving instanceof Player) ? SoundSource.PLAYERS : SoundSource.HOSTILE;
            worldIn.playSound(null, entityLiving.getX(), entityLiving.getY(), entityLiving.getZ(), SoundEvents.CROSSBOW_LOADING_END, soundcategory, 1.0f, 1.0f / (worldIn.getRandom().nextFloat() * 0.5f + 1.0f) + 0.2f);
        }
    }
    
    public float getCrossbowCharge( int useTime,  ItemStack stack) {
        float f = useTime / (float)this.getCrossbowChargeTime(stack);
        if (f > 1.0f) {
            f = 1.0f;
        }
        return f;
    }
    
    public int getCrossbowChargeTime( ItemStack stack) {
         int quickChargeLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.QUICK_CHARGE, stack);
        return Math.max(25 - 5 * quickChargeLevel, 0);
    }
    
    public SoundEvent getCrossbowSoundEvent( int i) {
        switch (i) {
            case 1: {
                return SoundEvents.CROSSBOW_QUICK_CHARGE_1;
            }
            case 2: {
                return SoundEvents.CROSSBOW_QUICK_CHARGE_2;
            }
            case 3: {
                return SoundEvents.CROSSBOW_QUICK_CHARGE_3;
            }
            default: {
                return SoundEvents.CROSSBOW_LOADING_START;
            }
        }
    }
    
    public void fireCrossbowProjectiles( Level world,  LivingEntity livingEntity,  InteractionHand hand,  ItemStack stack,  float velocityIn,  float inaccuracyIn) {
         List<ItemStack> list = getChargedProjectiles(stack);
         float[] randomSoundPitches = getRandomSoundPitches(livingEntity.getRandom());
        for (int i = 0; i < list.size(); ++i) {
             ItemStack currentProjectile = list.get(i);
             boolean playerInCreativeMode = livingEntity instanceof Player && ((Player)livingEntity).getAbilities().instabuild;
            if (!currentProjectile.isEmpty()) {
                if (i == 0) {
                    this.shootProjectile(world, livingEntity, hand, stack, currentProjectile, randomSoundPitches[i], playerInCreativeMode, velocityIn, inaccuracyIn, 0.0f);
                }
                else if (i == 1) {
                    this.shootProjectile(world, livingEntity, hand, stack, currentProjectile, randomSoundPitches[i], playerInCreativeMode, velocityIn, inaccuracyIn, -10.0f);
                }
                else if (i == 2) {
                    this.shootProjectile(world, livingEntity, hand, stack, currentProjectile, randomSoundPitches[i], playerInCreativeMode, velocityIn, inaccuracyIn, 10.0f);
                }
                else if (i == 3) {
                    this.shootProjectile(world, livingEntity, hand, stack, currentProjectile, randomSoundPitches[i - 2], playerInCreativeMode, velocityIn, inaccuracyIn, -20.0f);
                }
                else if (i == 4) {
                    this.shootProjectile(world, livingEntity, hand, stack, currentProjectile, randomSoundPitches[i - 2], playerInCreativeMode, velocityIn, inaccuracyIn, 20.0f);
                }
                else if (i == 5) {
                    this.shootProjectile(world, livingEntity, hand, stack, currentProjectile, randomSoundPitches[i - 4], playerInCreativeMode, velocityIn, inaccuracyIn, -30.0f);
                }
                else if (i == 6) {
                    this.shootProjectile(world, livingEntity, hand, stack, currentProjectile, randomSoundPitches[i - 4], playerInCreativeMode, velocityIn, inaccuracyIn, 30.0f);
                }
            }
        }
        fireProjectilesAfter(world, livingEntity, stack);
    }
    
    private AbstractArrow createCrossbowArrow( Level world,  LivingEntity livingEntity,  ItemStack stack,  ItemStack stack1) {
         ArrowItem arrowItem = (ArrowItem)((stack1.getItem() instanceof ArrowItem) ? stack1.getItem() : Items.ARROW);
         AbstractArrow abstractArrowEntity = arrowItem.createArrow(world, stack1, livingEntity);
        if (livingEntity instanceof Player) {
            abstractArrowEntity.setCritArrow(true);
        }
        abstractArrowEntity.setSoundEvent(SoundEvents.CROSSBOW_HIT);
        abstractArrowEntity.setShotFromCrossbow(true);
         int piercingLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PIERCING, stack);
        if (piercingLevel > 0) {
            abstractArrowEntity.setPierceLevel((byte)piercingLevel);
        }
         int powerLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, stack) + this.bowPower;
        if (powerLevel > 0) {
            abstractArrowEntity.setBaseDamage(abstractArrowEntity.getBaseDamage() + powerLevel * 0.5 + 0.5);
        }
         int punchLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, stack) + this.bowPower;
        if (punchLevel > 0) {
            abstractArrowEntity.setKnockback(punchLevel);
        }
        return abstractArrowEntity;
    }
    
    public float getProjectileVelocity( ItemStack stack) {
        if (containsChargedProjectile(stack, Items.FIREWORK_ROCKET)) {
            return 1.6f * this.velocityMultiplyer;
        }
        return 3.2f * this.velocityMultiplyer;
    }
    
    public int getUseDuration( ItemStack stack) {
        return this.getCrossbowChargeTime(stack) + 3;
    }
    
    public boolean useOnRelease( ItemStack stack) {
        return true;
    }
    
    void shootProjectile( Level worldIn,  LivingEntity shooter,  InteractionHand handIn,  ItemStack crossbow,  ItemStack projectile,  float soundPitch,  boolean isCreativeMode,  float velocity,  float inaccuracy,  float projectileAngle) {
        if (!worldIn.isClientSide) {
             boolean flag = projectile.getItem() == Items.FIREWORK_ROCKET;
            Projectile projectileentity;
            if (flag) {
                projectileentity = (Projectile)new FireworkRocketEntity(worldIn, projectile, shooter, shooter.getX(), shooter.getEyeY() - 0.15000000596046448, shooter.getZ(), true);
            }
            else {
                projectileentity = this.createCrossbowArrow(worldIn, shooter, crossbow, projectile);
                if (isCreativeMode || projectileAngle != 0.0f) {
                    ((AbstractArrow)projectileentity).pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                }
            }
            if (shooter instanceof CrossbowAttackMob) {
                 CrossbowAttackMob icrossbowuser = (CrossbowAttackMob)shooter;
                icrossbowuser.shootCrossbowProjectile((LivingEntity)Objects.requireNonNull(icrossbowuser.getTarget()), crossbow, projectileentity, projectileAngle);
            }
            else {
                 Vec3 vector3d1 = shooter.getUpVector(1.0f);
                 Quaternion quaternion = new Quaternion(new Vector3f(vector3d1), projectileAngle, true);
                 Vec3 vector3d2 = shooter.getViewVector(1.0f);
                 Vector3f vector3f = new Vector3f(vector3d2);
                vector3f.transform(quaternion);
                projectileentity.shoot((double)vector3f.x(), (double)vector3f.y(), (double)vector3f.z(), velocity, inaccuracy);
            }
            crossbow.hurtAndBreak(flag ? 3 : 1, shooter, p_220017_1_ -> p_220017_1_.broadcastBreakEvent(handIn));
            worldIn.addFreshEntity((Entity)projectileentity);
            worldIn.playSound((Player)null, shooter.getX(), shooter.getY(), shooter.getZ(), SoundEvents.CROSSBOW_SHOOT, SoundSource.PLAYERS, 1.0f, soundPitch);
        }
    }
    
    static void fireProjectilesAfter( Level worldIn,  LivingEntity shooter,  ItemStack stack) {
        if (shooter instanceof ServerPlayer) {
             ServerPlayer serverplayerentity = (ServerPlayer)shooter;
            if (!worldIn.isClientSide) {
                CriteriaTriggers.SHOT_CROSSBOW.trigger(serverplayerentity, stack);
            }
            serverplayerentity.awardStat(Stats.ITEM_USED.get(stack.getItem()));
        }
        clearProjectiles(stack);
    }
    
    private static void clearProjectiles( ItemStack stack) {
         CompoundTag compoundnbt = stack.getTag();
        if (compoundnbt != null) {
             ListTag listnbt = compoundnbt.getList("ChargedProjectiles", 9);
            listnbt.clear();
            compoundnbt.put("ChargedProjectiles", listnbt);
        }
    }
    
    static List<ItemStack> getChargedProjectiles( ItemStack stack) {
         List<ItemStack> list = Lists.newArrayList();
         CompoundTag compoundnbt = stack.getTag();
        if (compoundnbt != null && compoundnbt.contains("ChargedProjectiles", 9)) {
             ListTag listnbt = compoundnbt.getList("ChargedProjectiles", 10);
            for (int i = 0; i < listnbt.size(); ++i) {
                 CompoundTag compoundnbt2 = listnbt.getCompound(i);
                list.add(ItemStack.of(compoundnbt2));
            }
        }
        return list;
    }
    
    public static float[] getRandomSoundPitches( Random rand) {
         boolean flag = rand.nextBoolean();
        return new float[] { 1.0f, getRandomSoundPitch(flag), getRandomSoundPitch(!flag) };
    }
    
    private static float getRandomSoundPitch( boolean flagIn) {
         float f = flagIn ? 0.63f : 0.43f;
        return 1.0f / (new Random().nextFloat() * 0.5f + 1.8f) + f;
    }
    
    public boolean tryLoadProjectiles( LivingEntity entityIn,  ItemStack stack) {
         int multishotLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.MULTISHOT, stack) + this.multishotLevel;
         int arrowsToFire = (multishotLevel == 0) ? 1 : (1 + multishotLevel * 2);
         boolean flag = entityIn instanceof Player && ((Player)entityIn).getAbilities().instabuild;
        ItemStack itemstack = entityIn.getProjectile(stack);
        ItemStack itemstack2 = itemstack.copy();
        for (int i = 0; i < arrowsToFire; ++i) {
            if (i > 0) {
                itemstack = itemstack2.copy();
            }
            if (itemstack.isEmpty() && flag) {
                itemstack = new ItemStack(Items.ARROW);
                itemstack2 = itemstack.copy();
            }
            if (!canAddChargedProjectile(entityIn, stack, itemstack, i > 0, flag)) {
                return false;
            }
        }
        return true;
    }
    
    private static boolean canAddChargedProjectile( LivingEntity livingEntity,  ItemStack stack,  ItemStack stack1,  boolean b,  boolean b1) {
        if (stack1.isEmpty()) {
            return false;
        }
         boolean flag = b1 && stack1.getItem() instanceof ArrowItem;
        ItemStack itemstack;
        if (!flag && !b1 && !b) {
            itemstack = stack1.split(1);
            if (stack1.isEmpty() && livingEntity instanceof Player) {
                ((Player)livingEntity).getInventory().removeItem(stack1);
            }
        }
        else {
            itemstack = stack1.copy();
        }
        addChargedProjectile(stack, itemstack);
        return true;
    }
    
    private static void addChargedProjectile( ItemStack crossbow,  ItemStack projectile) {
         CompoundTag compoundnbt = crossbow.getOrCreateTag();
        ListTag listnbt;
        if (compoundnbt.contains("ChargedProjectiles", 9)) {
            listnbt = compoundnbt.getList("ChargedProjectiles", 10);
        }
        else {
            listnbt = new ListTag();
        }
         CompoundTag compoundnbt2 = new CompoundTag();
        projectile.save(compoundnbt2);
        listnbt.add(compoundnbt2);
        compoundnbt.put("ChargedProjectiles", listnbt);
    }
    
    public float getXpRepairRatio( ItemStack stack) {
        return 2.0f + this.mendingBonus;
    }

    public void appendHoverText(ItemStack stack, Level world, List<Component> list, TooltipFlag flag) {
        if (this.mendingBonus != 0.0f) {
            if (this.mendingBonus > 0.0f) {
                list.add(0, new TranslatableComponent("tooltip.expanded_combat.mending_bonus").withStyle(ChatFormatting.GREEN).append(new TextComponent(ChatFormatting.GREEN + " +" + ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(this.mendingBonus))));
            }
            else if (this.mendingBonus < 0.0f) {
                list.add(0, new TranslatableComponent("tooltip.expanded_combat.mending_bonus").withStyle(ChatFormatting.RED).append(new TextComponent(ChatFormatting.RED + " +" + ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(this.mendingBonus))));
            }
        }
    }
}
