package com.userofbricks.expanded_combat.item;

import com.google.common.collect.Lists;
import com.userofbricks.expanded_combat.item.materials.Material;
import com.userofbricks.expanded_combat.util.IngredientUtil;
import com.userofbricks.expanded_combat.util.LangStrings;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.CrossbowAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.List;
import java.util.Objects;

import static com.userofbricks.expanded_combat.ExpandedCombat.CONFIG;

public class ECCrossBowItem extends CrossbowItem {
    public final Material material;

    private boolean startSoundPlayed = false;
    private boolean midLoadSoundPlayed = false;

    public ECCrossBowItem(Material material, Item.Properties builder) {
        super(builder);
        this.material = material;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level worldIn, Player playerIn, @NotNull InteractionHand handIn) {
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        if (isCharged(itemstack)) {
            this.fireCrossbowProjectiles(worldIn, playerIn, handIn, itemstack, this.getProjectileVelocity(itemstack), 1.0f);
            setCharged(itemstack, false);
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

    public float getProjectileVelocity( ItemStack stack) {
        if (containsChargedProjectile(stack, Items.FIREWORK_ROCKET)) {
            return 1.6f * (this.material.getConfig().offense.velocityMultiplier + CONFIG.crossbowVelocityBonus);
        }
        return 3.2f * (this.material.getConfig().offense.velocityMultiplier + CONFIG.crossbowVelocityBonus);
    }

    @Override
    public void releaseUsing(@NotNull ItemStack stack, @NotNull Level worldIn, @NotNull LivingEntity entityLiving, int timeLeft) {
        int i = this.getUseDuration(stack) - timeLeft;
        float f = getPowerForTime(i, stack);
        if (f >= 1.0f && !isCharged(stack) && this.tryLoadProjectiles(entityLiving, stack)) {
            setCharged(stack, true);
            SoundSource soundcategory = (entityLiving instanceof Player) ? SoundSource.PLAYERS : SoundSource.HOSTILE;
            worldIn.playSound(null, entityLiving.getX(), entityLiving.getY(), entityLiving.getZ(), SoundEvents.CROSSBOW_LOADING_END, soundcategory, 1.0f, 1.0f / (worldIn.getRandom().nextFloat() * 0.5f + 1.0f) + 0.2f);
        }
    }

    public boolean tryLoadProjectiles(@NotNull LivingEntity entityIn, ItemStack stack) {
        int multishotLevel = stack.getEnchantmentLevel(Enchantments.MULTISHOT) + this.material.getConfig().offense.multishotLevel;
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

    private static List<ItemStack> getChargedProjectiles(ItemStack stack) {
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

    private static void clearProjectiles( ItemStack stack) {
        CompoundTag compoundnbt = stack.getTag();
        if (compoundnbt != null) {
            ListTag listnbt = compoundnbt.getList("ChargedProjectiles", 9);
            listnbt.clear();
            compoundnbt.put("ChargedProjectiles", listnbt);
        }
    }

    void shootProjectile( Level worldIn,  LivingEntity shooter,  InteractionHand handIn,  ItemStack crossbow,  ItemStack projectile,  float soundPitch,  boolean isCreativeMode,  float velocity,  float inaccuracy,  float projectileAngle) {
        if (!worldIn.isClientSide) {
            boolean flag = projectile.getItem() == Items.FIREWORK_ROCKET;
            Projectile projectileentity;
            if (flag) {
                projectileentity = new FireworkRocketEntity(worldIn, projectile, shooter, shooter.getX(), shooter.getEyeY() - 0.15f, shooter.getZ(), true);
            } else {
                projectileentity = this.createCrossbowArrow(worldIn, shooter, crossbow, projectile);
                if (isCreativeMode || projectileAngle != 0.0f) {
                    ((AbstractArrow)projectileentity).pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                }
            }
            if (shooter instanceof CrossbowAttackMob crossbowattackmob) {
                crossbowattackmob.shootCrossbowProjectile(Objects.requireNonNull(crossbowattackmob.getTarget()), crossbow, projectileentity, projectileAngle);
            }
            else {
                Vec3 vec31 = shooter.getUpVector(1.0f);
                Quaternionf quaternion = (new Quaternionf()).setAngleAxis(projectileAngle * ((float)Math.PI / 180F), vec31.x, vec31.y, vec31.z);
                Vec3 vector3d2 = shooter.getViewVector(1.0f);
                Vector3f vector3f = vector3d2.toVector3f().rotate(quaternion);
                projectileentity.shoot(vector3f.x(), vector3f.y(), vector3f.z(), velocity, inaccuracy);
            }
            crossbow.hurtAndBreak(flag ? 3 : 1, shooter, p_220017_1_ -> p_220017_1_.broadcastBreakEvent(handIn));
            worldIn.addFreshEntity(projectileentity);
            worldIn.playSound(null, shooter.getX(), shooter.getY(), shooter.getZ(), SoundEvents.CROSSBOW_SHOOT, SoundSource.PLAYERS, 1.0f, soundPitch);
        }
    }

    private AbstractArrow createCrossbowArrow(Level world, LivingEntity livingEntity, ItemStack stack, ItemStack stack1) {
        ArrowItem arrowItem = (ArrowItem)((stack1.getItem() instanceof ArrowItem) ? stack1.getItem() : Items.ARROW);
        AbstractArrow abstractArrowEntity = arrowItem.createArrow(world, stack1, livingEntity);
        if (livingEntity instanceof Player) {
            abstractArrowEntity.setCritArrow(true);
        }
        abstractArrowEntity.setSoundEvent(SoundEvents.CROSSBOW_HIT);
        abstractArrowEntity.setShotFromCrossbow(true);
        int piercingLevel = stack.getEnchantmentLevel(Enchantments.PIERCING);
        if (piercingLevel > 0) {
            abstractArrowEntity.setPierceLevel((byte)piercingLevel);
        }
        int powerLevel = stack.getEnchantmentLevel(Enchantments.POWER_ARROWS) + this.material.getConfig().offense.bowPower;
        if (powerLevel > 0) {
            abstractArrowEntity.setBaseDamage(abstractArrowEntity.getBaseDamage() + powerLevel * 0.5 + 0.5);
        }
        int punchLevel = stack.getEnchantmentLevel(Enchantments.PUNCH_ARROWS);
        if (punchLevel > 0) {
            abstractArrowEntity.setKnockback(punchLevel);
        }
        return abstractArrowEntity;
    }

    public void fireCrossbowProjectiles( Level world,  LivingEntity livingEntity,  InteractionHand hand,  ItemStack stack,  float velocityIn,  float inaccuracyIn) {
        List<ItemStack> list = getChargedProjectiles(stack);
        float[] randomSoundPitches = getShotPitches(livingEntity.getRandom());
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

    private static float[] getShotPitches(RandomSource p_220024_) {
        boolean flag = p_220024_.nextBoolean();
        return new float[]{1.0F, getRandomShotPitch(flag, p_220024_), getRandomShotPitch(!flag, p_220024_)};
    }

    private static float getRandomShotPitch(boolean p_220026_, RandomSource p_220027_) {
        float f = p_220026_ ? 0.63F : 0.43F;
        return 1.0F / (p_220027_.nextFloat() * 0.5F + 1.8F) + f;
    }

    private static void fireProjectilesAfter( Level worldIn,  LivingEntity shooter,  ItemStack stack) {
        if (shooter instanceof ServerPlayer serverplayerentity) {
            if (!worldIn.isClientSide) {
                CriteriaTriggers.SHOT_CROSSBOW.trigger(serverplayerentity, stack);
            }
            serverplayerentity.awardStat(Stats.ITEM_USED.get(stack.getItem()));
        }
        clearProjectiles(stack);
    }

    @Override
    public void onUseTick(Level world, @NotNull LivingEntity livingEntity, @NotNull ItemStack stack, int timeLeft) {
        if (!world.isClientSide) {
            int quickChargeLevel = stack.getEnchantmentLevel(Enchantments.QUICK_CHARGE);
            SoundEvent quickChargeSoundEvent = this.getCrossbowSoundEvent(quickChargeLevel);
            SoundEvent loadingMiddleSoundEvent = (quickChargeLevel == 0) ? SoundEvents.CROSSBOW_LOADING_MIDDLE : null;
            float chargeTime = (stack.getUseDuration() - timeLeft) / (float) getChargeDuration(stack);
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

    public SoundEvent getCrossbowSoundEvent( int i) {
        switch (i) {
            case 1 -> {
                return SoundEvents.CROSSBOW_QUICK_CHARGE_1;
            }
            case 2 -> {
                return SoundEvents.CROSSBOW_QUICK_CHARGE_2;
            }
            case 3 -> {
                return SoundEvents.CROSSBOW_QUICK_CHARGE_3;
            }
            default -> {
                return SoundEvents.CROSSBOW_LOADING_START;
            }
        }
    }

    private static float getPowerForTime(int p_40854_, ItemStack p_40855_) {
        float f = (float)p_40854_ / (float)getChargeDuration(p_40855_);
        if (f > 1.0F) {
            f = 1.0F;
        }

        return f;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, Level world, @NotNull List<Component> list, @NotNull TooltipFlag flag) {
        super.appendHoverText(stack, world, list, flag);
        if (this.material.getConfig().mendingBonus != 0.0f) {
            if (this.material.getConfig().mendingBonus > 0.0f) {
                list.add(1, Component.translatable(LangStrings.GOLD_MENDING_TOOLTIP).withStyle(ChatFormatting.GREEN).append(Component.literal(ChatFormatting.GREEN + " +" + ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(this.material.getConfig().mendingBonus))));
            }
            else if (this.material.getConfig().mendingBonus < 0.0f) {
                list.add(1, Component.translatable(LangStrings.GOLD_MENDING_TOOLTIP).withStyle(ChatFormatting.RED).append(Component.literal(ChatFormatting.RED + " " + ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(this.material.getConfig().mendingBonus))));
            }
        }
    }

    @Override
    public float getXpRepairRatio( ItemStack stack) {
        return 2.0f + this.material.getConfig().mendingBonus;
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

    @Override
    public boolean makesPiglinsNeutral(ItemStack stack, LivingEntity wearer) {
        return ((ECCrossBowItem) stack.getItem()).getMaterial().getName().equals("Gold");
    }
}
