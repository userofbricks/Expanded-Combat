package com.userofbricks.expandedcombat.entity.projectile;

import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraft.network.IPacket;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraft.util.IItemProvider;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.registry.Registry;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundNBT;
import java.util.Iterator;
import java.util.Collection;
import net.minecraft.potion.PotionUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.LivingEntity;
import com.userofbricks.expandedcombat.entity.ECEntities;
import com.google.common.collect.Sets;
import net.minecraft.potion.Potions;
import net.minecraft.world.World;
import net.minecraft.entity.EntityType;
import com.userofbricks.expandedcombat.item.ArrowType;
import net.minecraft.potion.EffectInstance;
import java.util.Set;
import net.minecraft.potion.Potion;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.entity.projectile.AbstractArrowEntity;

import javax.annotation.Nonnull;

public class ECArrowEntity extends AbstractArrowEntity
{
    private static final DataParameter<Integer> COLOR;
    private Potion potion;
    private final Set<EffectInstance> customPotionEffects = Sets.newHashSet();
    private boolean fixedColor;
    private ArrowType arrowType;
    
    public ECArrowEntity(EntityType<? extends ECArrowEntity> p_i50172_1_, World p_i50172_2_) {
        super(p_i50172_1_, p_i50172_2_);
        this.potion = Potions.EMPTY;
        this.arrowType = ArrowType.IRON;
    }
    
    public ECArrowEntity(World worldIn, double x, double y, double z) {
        super(ECEntities.EC_ARROW_ENTITY.get(), x, y, z, worldIn);
        this.potion = Potions.EMPTY;
        this.arrowType = ArrowType.IRON;
    }
    
    public ECArrowEntity(World worldIn, LivingEntity shooter) {
        super(ECEntities.EC_ARROW_ENTITY.get(), shooter, worldIn);
        this.potion = Potions.EMPTY;
        this.arrowType = ArrowType.IRON;
    }
    
    public void setPotionEffect(ItemStack stack) {
        if (stack.getItem() == this.arrowType.getTippedArrow()) {
            this.potion = PotionUtils.getPotion(stack);
            Collection<EffectInstance> collection = PotionUtils.getCustomEffects(stack);
            if (!collection.isEmpty()) {
                for (EffectInstance effectinstance : collection) {
                    this.customPotionEffects.add(new EffectInstance(effectinstance));
                }
            }
            int i = getCustomColor(stack);
            if (i == -1) {
                this.refreshColor();
            }
            else {
                this.setFixedColor(i);
            }
        }
        else if (stack.getItem() == this.arrowType.getArrow()) {
            this.potion = Potions.EMPTY;
            this.customPotionEffects.clear();
            this.entityData.set(ECArrowEntity.COLOR, -1);
        }
    }
    
    public static int getCustomColor(ItemStack itemStack) {
        CompoundNBT compoundnbt = itemStack.getTag();
        return (compoundnbt != null && compoundnbt.contains("CustomPotionColor", 99)) ? compoundnbt.getInt("CustomPotionColor") : -1;
    }
    
    private void refreshColor() {
        this.fixedColor = false;
        if (this.potion == Potions.EMPTY && this.customPotionEffects.isEmpty()) {
            this.entityData.set(ECArrowEntity.COLOR, -1);
        }
        else {
            this.entityData.set(ECArrowEntity.COLOR, PotionUtils.getColor(PotionUtils.getAllEffects(this.potion, this.customPotionEffects)));
        }
    }
    
    public void addEffect(EffectInstance effect) {
        this.customPotionEffects.add(effect);
        this.getEntityData().set(ECArrowEntity.COLOR, PotionUtils.getColor(PotionUtils.getAllEffects(this.potion, this.customPotionEffects)));
    }
    
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ECArrowEntity.COLOR, (-1));
    }
    
    public void tick() {
        super.tick();
        if (this.level.isClientSide) {
            if (this.inGround) {
                if (this.inGroundTime % 5 == 0) {
                    this.spawnPotionParticles(1);
                }
            }
            else {
                this.spawnPotionParticles(2);
            }
        }
        else if (this.inGround && this.inGroundTime != 0 && !this.customPotionEffects.isEmpty() && this.inGroundTime >= 600) {
            this.level.broadcastEntityEvent((Entity)this, (byte)0);
            this.potion = Potions.EMPTY;
            this.customPotionEffects.clear();
            this.entityData.set(ECArrowEntity.COLOR, (-1));
        }
    }
    
    private void spawnPotionParticles(int particleCount) {
        int i = this.getColor();
        if (i != -1 && particleCount > 0) {
            double d0 = (i >> 16 & 0xFF) / 255.0;
            double d2 = (i >> 8 & 0xFF) / 255.0;
            double d3 = (i & 0xFF) / 255.0;
            for (int j = 0; j < particleCount; ++j) {
                this.level.addParticle(ParticleTypes.ENTITY_EFFECT, this.getRandomX(0.5), this.getRandomY(), this.getRandomZ(0.5), d0, d2, d3);
            }
        }
    }
    
    public int getColor() {
        return this.entityData.get(ECArrowEntity.COLOR);
    }
    
    private void setFixedColor(int p_191507_1_) {
        this.fixedColor = true;
        this.entityData.set(ECArrowEntity.COLOR, p_191507_1_);
    }
    
    public void addAdditionalSaveData(@Nonnull CompoundNBT compound) {
        super.addAdditionalSaveData(compound);
        if (this.potion != Potions.EMPTY && this.potion != null) {
            compound.putString("Potion", Registry.POTION.getKey(this.potion).toString());
        }
        if (this.fixedColor) {
            compound.putInt("Color", this.getColor());
        }
        if (!this.customPotionEffects.isEmpty()) {
            ListNBT listnbt = new ListNBT();
            for(EffectInstance effectinstance : this.customPotionEffects) {
                listnbt.add(effectinstance.save(new CompoundNBT()));
            }
            compound.put("CustomPotionEffects", listnbt);
        }
        CompoundNBT arrowTypenbt = new CompoundNBT();
        compound.putString("ArrowType", this.arrowType.name());
    }
    
    public void readAdditionalSaveData(@Nonnull CompoundNBT compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("Potion", 8)) {
            this.potion = PotionUtils.getPotion(compound);
        }
        for (EffectInstance effectinstance : PotionUtils.getCustomEffects(compound)) {
            this.addEffect(effectinstance);
        }
        if (compound.contains("Color", 99)) {
            this.setFixedColor(compound.getInt("Color"));
        }
        else {
            this.refreshColor();
        }
        if (compound.contains("ArrowType")) {
            String type = compound.getString("ArrowType");
            this.arrowType = ArrowType.valueOf(type);
        }
    }
    
    protected void doPostHurtEffects(@Nonnull LivingEntity living) {
        super.doPostHurtEffects(living);
        for (EffectInstance effectinstance : this.potion.getEffects()) {
            living.addEffect(new EffectInstance(effectinstance.getEffect(), Math.max(effectinstance.getDuration() / 8, 1), effectinstance.getAmplifier(), effectinstance.isAmbient(), effectinstance.isVisible()));
        }
        if (!this.customPotionEffects.isEmpty()) {
            for (EffectInstance effectinstance2 : this.customPotionEffects) {
                living.addEffect(effectinstance2);
            }
        }
    }
    
    @Nonnull
    protected ItemStack getPickupItem() {
        if (this.customPotionEffects.isEmpty() && this.potion == Potions.EMPTY) {
            return new ItemStack(this.arrowType.getArrow());
        }
        ItemStack itemstack = new ItemStack(this.arrowType.getTippedArrow());
        PotionUtils.setPotion(itemstack, this.potion);
        PotionUtils.setCustomEffects(itemstack, this.customPotionEffects);
        if (this.fixedColor) {
            itemstack.getOrCreateTag().putInt("CustomPotionColor", this.getColor());
        }
        return itemstack;
    }
    
    @OnlyIn(Dist.CLIENT)
    public void handleEntityEvent(byte id) {
        if (id == 0) {
            int i = this.getColor();
            if (i != -1) {
                double d0 = (i >> 16 & 0xFF) / 255.0;
                double d2 = (i >> 8 & 0xFF) / 255.0;
                double d3 = (i & 0xFF) / 255.0;
                for (int j = 0; j < 20; ++j) {
                    this.level.addParticle(ParticleTypes.ENTITY_EFFECT, this.getRandomX(0.5), this.getRandomY(), this.getRandomZ(0.5), d0, d2, d3);
                }
            }
        }
        else {
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
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
    
    static {
        COLOR = EntityDataManager.defineId(ECArrowEntity.class, DataSerializers.INT);
    }
}
