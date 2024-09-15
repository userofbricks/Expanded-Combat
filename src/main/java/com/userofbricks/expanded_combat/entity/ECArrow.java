package com.userofbricks.expanded_combat.entity;

import com.userofbricks.expanded_combat.api.material.Material;
import com.userofbricks.expanded_combat.init.ECBasePlugin;
import com.userofbricks.expanded_combat.init.ECEntities;
import com.userofbricks.expanded_combat.item.ECTippedArrowItem;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.Level;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ECArrow extends AbstractArrow {
    private static final EntityDataAccessor<Integer> ID_EFFECT_COLOR = SynchedEntityData.defineId(ECArrow.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Material> MATERIAL = SynchedEntityData.defineId(ECArrow.class, ECEntities.MATERIAL.get());
    public ECArrow(EntityType<? extends ECArrow> entityEntityType, Level level) {
        super(entityEntityType, level);
    }
    public ECArrow(Level level, double x, double y, double z, ItemStack pPickupItemStack, Material material) {
        super(ECEntities.EC_ARROW.get(), x, y, z, level, pPickupItemStack);
        this.entityData.set(MATERIAL, material);
        this.updateColor();
    }

    public ECArrow(Level level, LivingEntity shooter, ItemStack pPickupItemStack, Material material) {
        super(ECEntities.EC_ARROW.get(), shooter, level, pPickupItemStack);
        this.entityData.set(MATERIAL, material);
        this.updateColor();
    }

    private PotionContents getPotionContents() {
        return this.getPickupItemStackOrigin().getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY);
    }

    private void setPotionContents(PotionContents pPotionContents) {
        this.getPickupItemStackOrigin().set(DataComponents.POTION_CONTENTS, pPotionContents);
        this.updateColor();
    }

    @Override
    protected void setPickupItemStack(ItemStack pPickupItemStack) {
        super.setPickupItemStack(pPickupItemStack);
        this.updateColor();
    }

    private void updateColor() {
        PotionContents potioncontents = this.getPotionContents();
        this.entityData.set(ID_EFFECT_COLOR, potioncontents.equals(PotionContents.EMPTY) ? -1 : potioncontents.getColor());
    }

    public void addEffect(MobEffectInstance pEffectInstance) {
        this.setPotionContents(this.getPotionContents().withEffectAdded(pEffectInstance));
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder pBuilder) {
        super.defineSynchedData(pBuilder);
        pBuilder.define(ID_EFFECT_COLOR, -1);
        pBuilder.define(MATERIAL, ECBasePlugin.IRON);
    }

    @Override
    public void tick() {
        super.tick();
        if (getMaterial().offense().flaming()) this.setRemainingFireTicks(100);
        if (this.level().isClientSide) {
            if (this.inGround) {
                if (this.inGroundTime % 5 == 0) {
                    this.makeParticle(1);
                }
            } else {
                this.makeParticle(2);
            }
        } else if (this.inGround && this.inGroundTime != 0 && !this.getPotionContents().equals(PotionContents.EMPTY) && this.inGroundTime >= 600) {
            this.level().broadcastEntityEvent(this, (byte)0);
            Item nonTipped = ((ECTippedArrowItem) getPickupItemStackOrigin().getItem()).getNotTipped();
            this.setPickupItemStack(new ItemStack(nonTipped));
        }
    }

    private void makeParticle(int pParticleAmount) {
        int i = this.getColor();
        if (i != -1 && pParticleAmount > 0) {
            for (int j = 0; j < pParticleAmount; j++) {
                this.level()
                        .addParticle(
                                ColorParticleOption.create(ParticleTypes.ENTITY_EFFECT, i),
                                this.getRandomX(0.5),
                                this.getRandomY(),
                                this.getRandomZ(0.5),
                                0.0,
                                0.0,
                                0.0
                        );
            }
        }
    }

    public int getColor() {
        return this.entityData.get(ID_EFFECT_COLOR);
    }

    @Override
    protected void doPostHurtEffects(LivingEntity pLiving) {
        super.doPostHurtEffects(pLiving);
        Entity entity = this.getEffectSource();
        PotionContents potioncontents = this.getPotionContents();
        if (potioncontents.potion().isPresent()) {
            for (MobEffectInstance mobeffectinstance : potioncontents.potion().get().value().getEffects()) {
                pLiving.addEffect(
                        new MobEffectInstance(
                                mobeffectinstance.getEffect(),
                                Math.max(mobeffectinstance.mapDuration(p_268168_ -> p_268168_ / 8), 1),
                                mobeffectinstance.getAmplifier(),
                                mobeffectinstance.isAmbient(),
                                mobeffectinstance.isVisible()
                        ),
                        entity
                );
            }
        }

        for (MobEffectInstance mobeffectinstance1 : potioncontents.customEffects()) {
            pLiving.addEffect(mobeffectinstance1, entity);
        }
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return new ItemStack(Items.ARROW);
    }

    @Override
    protected double getDefaultGravity() {
        return getMaterial().offense().defaultArrowGravity();
    }
    public double getBaseDamage() {
        return getMaterial().offense().arrowDamage();
    }

    @Override
    public void handleEntityEvent(byte pId) {
        if (pId == 0) {
            int i = this.getColor();
            if (i != -1) {
                float f = (float)(i >> 16 & 0xFF) / 255.0F;
                float f1 = (float)(i >> 8 & 0xFF) / 255.0F;
                float f2 = (float)(i >> 0 & 0xFF) / 255.0F;

                for (int j = 0; j < 20; j++) {
                    this.level()
                            .addParticle(
                                    ColorParticleOption.create(ParticleTypes.ENTITY_EFFECT, f, f1, f2),
                                    this.getRandomX(0.5),
                                    this.getRandomY(),
                                    this.getRandomZ(0.5),
                                    0.0,
                                    0.0,
                                    0.0
                            );
                }
            }
        } else {
            super.handleEntityEvent(pId);
        }
    }

    public Material getMaterial() {
        return this.entityData.get(MATERIAL);
    }

    public void setArrowType(Material arrowMaterial) {
        this.entityData.set(MATERIAL, arrowMaterial);
    }
}
