package com.userofbricks.expandedcombat.entity.projectile;

import com.google.common.collect.Sets;
import com.userofbricks.expandedcombat.entity.ECEntities;
import com.userofbricks.expandedcombat.item.ArrowType;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraftforge.fmllegacy.network.NetworkHooks;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Set;

public class ECArrowEntity extends AbstractArrow
{
    private static final int EXPOSED_POTION_DECAY_TIME = 600;
    private static final int NO_EFFECT_COLOR = -1;
    private static final EntityDataAccessor<Integer> ID_EFFECT_COLOR = SynchedEntityData.defineId(Arrow.class, EntityDataSerializers.INT);
    private Potion potion = Potions.EMPTY;
    private final Set<MobEffectInstance> effects = Sets.newHashSet();
    private boolean fixedColor;
    private ArrowType arrowType;
    
    public ECArrowEntity(EntityType<? extends ECArrowEntity> p_i50172_1_, Level p_i50172_2_) {
        super(p_i50172_1_, p_i50172_2_);
        this.potion = Potions.EMPTY;
        this.arrowType = ArrowType.IRON;
    }
    
    public ECArrowEntity(Level worldIn, double x, double y, double z) {
        super(ECEntities.EC_ARROW_ENTITY.get(), x, y, z, worldIn);
        this.potion = Potions.EMPTY;
        this.arrowType = ArrowType.IRON;
    }
    
    public ECArrowEntity(Level worldIn, LivingEntity shooter) {
        super(ECEntities.EC_ARROW_ENTITY.get(), shooter, worldIn);
        this.potion = Potions.EMPTY;
        this.arrowType = ArrowType.IRON;
    }

    public void setPotionEffect(ItemStack stack) {
        //use setEffectFromItem instead
    }

    public void setEffectsFromItem(ItemStack stack) {
        if (stack.is(this.arrowType.getTippedArrow())) {
            this.potion = PotionUtils.getPotion(stack);
            Collection<MobEffectInstance> collection = PotionUtils.getCustomEffects(stack);
            if (!collection.isEmpty()) {
                for(MobEffectInstance mobeffectinstance : collection) {
                    this.effects.add(new MobEffectInstance(mobeffectinstance));
                }
            }

            int i = getCustomColor(stack);
            if (i == -1) {
                this.updateColor();
            } else {
                this.setFixedColor(i);
            }
        } else if (stack.is(this.arrowType.getArrow())) {
            this.potion = Potions.EMPTY;
            this.effects.clear();
            this.entityData.set(ID_EFFECT_COLOR, -1);
        }

    }

    public static int getCustomColor(ItemStack p_36885_) {
        CompoundTag compoundtag = p_36885_.getTag();
        return compoundtag != null && compoundtag.contains("CustomPotionColor", 99) ? compoundtag.getInt("CustomPotionColor") : -1;
    }

    private void updateColor() {
        this.fixedColor = false;
        if (this.potion == Potions.EMPTY && this.effects.isEmpty()) {
            this.entityData.set(ID_EFFECT_COLOR, -1);
        } else {
            this.entityData.set(ID_EFFECT_COLOR, PotionUtils.getColor(PotionUtils.getAllEffects(this.potion, this.effects)));
        }

    }

    public void addEffect(MobEffectInstance p_36871_) {
        this.effects.add(p_36871_);
        this.getEntityData().set(ID_EFFECT_COLOR, PotionUtils.getColor(PotionUtils.getAllEffects(this.potion, this.effects)));
    }
    
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ID_EFFECT_COLOR, (-1));
    }

    public void tick() {
        super.tick();
        if (this.level.isClientSide) {
            if (this.inGround) {
                if (this.inGroundTime % 5 == 0) {
                    this.makeParticle(1);
                }
            } else {
                this.makeParticle(2);
            }
        } else if (this.inGround && this.inGroundTime != 0 && !this.effects.isEmpty() && this.inGroundTime >= 600) {
            this.level.broadcastEntityEvent(this, (byte)0);
            this.potion = Potions.EMPTY;
            this.effects.clear();
            this.entityData.set(ID_EFFECT_COLOR, -1);
        }

    }

    private void makeParticle(int p_36877_) {
        int i = this.getColor();
        if (i != -1 && p_36877_ > 0) {
            double d0 = (double)(i >> 16 & 255) / 255.0D;
            double d1 = (double)(i >> 8 & 255) / 255.0D;
            double d2 = (double)(i >> 0 & 255) / 255.0D;

            for(int j = 0; j < p_36877_; ++j) {
                this.level.addParticle(ParticleTypes.ENTITY_EFFECT, this.getRandomX(0.5D), this.getRandomY(), this.getRandomZ(0.5D), d0, d1, d2);
            }

        }
    }
    
    public int getColor() {
        return this.entityData.get(ID_EFFECT_COLOR);
    }
    
    private void setFixedColor(int p_191507_1_) {
        this.fixedColor = true;
        this.entityData.set(ID_EFFECT_COLOR, p_191507_1_);
    }
    
    public void addAdditionalSaveData(@Nonnull CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        if (this.potion != Potions.EMPTY && this.potion != null) {
            compound.putString("Potion", Registry.POTION.getKey(this.potion).toString());
        }
        if (this.fixedColor) {
            compound.putInt("Color", this.getColor());
        }
        if (!this.effects.isEmpty()) {
            ListTag listnbt = new ListTag();
            for(MobEffectInstance effectinstance : this.effects) {
                listnbt.add(effectinstance.save(new CompoundTag()));
            }
            compound.put("CustomPotionEffects", listnbt);
        }
        CompoundTag arrowTypenbt = new CompoundTag();
        compound.putString("ArrowType", this.arrowType.name());
    }
    
    public void readAdditionalSaveData(@Nonnull CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("Potion", 8)) {
            this.potion = PotionUtils.getPotion(compound);
        }
        for (MobEffectInstance effectinstance : PotionUtils.getCustomEffects(compound)) {
            this.addEffect(effectinstance);
        }
        if (compound.contains("Color", 99)) {
            this.setFixedColor(compound.getInt("Color"));
        }
        else {
            this.updateColor();
        }
        if (compound.contains("ArrowType")) {
            String type = compound.getString("ArrowType");
            this.arrowType = ArrowType.valueOf(type);
        }
    }

    protected void doPostHurtEffects(LivingEntity livingEntity) {
        super.doPostHurtEffects(livingEntity);
        Entity entity = this.getEffectSource();

        for(MobEffectInstance mobeffectinstance : this.potion.getEffects()) {
            livingEntity.addEffect(new MobEffectInstance(mobeffectinstance.getEffect(), Math.max(mobeffectinstance.getDuration() / 8, 1), mobeffectinstance.getAmplifier(), mobeffectinstance.isAmbient(), mobeffectinstance.isVisible()), entity);
        }

        if (!this.effects.isEmpty()) {
            for(MobEffectInstance mobeffectinstance1 : this.effects) {
                livingEntity.addEffect(mobeffectinstance1, entity);
            }
        }

    }
    
    @Nonnull
    protected ItemStack getPickupItem() {
        if (this.effects.isEmpty() && this.potion == Potions.EMPTY) {
            return new ItemStack(this.arrowType.getArrow());
        }
        ItemStack itemstack = new ItemStack(this.arrowType.getTippedArrow());
        PotionUtils.setPotion(itemstack, this.potion);
        PotionUtils.setCustomEffects(itemstack, this.effects);
        if (this.fixedColor) {
            itemstack.getOrCreateTag().putInt("CustomPotionColor", this.getColor());
        }
        return itemstack;
    }

    public void handleEntityEvent(byte id) {
        if (id == 0) {
            int i = this.getColor();
            if (i != -1) {
                double d0 = (double)(i >> 16 & 255) / 255.0D;
                double d1 = (double)(i >> 8 & 255) / 255.0D;
                double d2 = (double)(i >> 0 & 255) / 255.0D;

                for(int j = 0; j < 20; ++j) {
                    this.level.addParticle(ParticleTypes.ENTITY_EFFECT, this.getRandomX(0.5D), this.getRandomY(), this.getRandomZ(0.5D), d0, d1, d2);
                }
            }
        } else {
            super.handleEntityEvent(id);
        }

    }
    
    public ArrowType getArrowType() {
        return this.arrowType;
    }
    
    public void setArrowType(ArrowType arrowType) {
        this.arrowType = arrowType;
    }
    
    @Nonnull
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
