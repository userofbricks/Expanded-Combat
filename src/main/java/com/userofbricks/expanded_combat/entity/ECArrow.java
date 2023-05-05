package com.userofbricks.expanded_combat.entity;

import com.userofbricks.expanded_combat.item.ECTippedArrowItem;
import com.userofbricks.expanded_combat.item.materials.ArrowMaterial;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public class ECArrow extends Arrow {
    private ArrowMaterial arrowMaterial;
    @SuppressWarnings("unchecked")
    public ECArrow(EntityType<? extends Entity> entityEntityType, Level level) {
        super((EntityType<? extends ECArrow>) entityEntityType, level);
    }
    public ECArrow(Level level, double x, double y, double z) {
        this(ECEntities.EC_ARROW.get(), level);
        this.setPos(x, y, z);
    }

    public ECArrow(Level level, LivingEntity shooter) {
        this(ECEntities.EC_ARROW.get(), level);
        this.setPos(shooter.getX(), shooter.getEyeY() - (double)0.1F, shooter.getZ());
        this.setOwner(shooter);
        if (shooter instanceof Player) {
            this.pickup = Pickup.ALLOWED;
        }
    }

    @Override
    public void setEffectsFromItem(ItemStack p_36879_) {
        if (p_36879_.is(Items.TIPPED_ARROW) || p_36879_.getItem() instanceof ECTippedArrowItem) {
            this.potion = PotionUtils.getPotion(p_36879_);
            Collection<MobEffectInstance> collection = PotionUtils.getCustomEffects(p_36879_);
            if (!collection.isEmpty()) {
                for(MobEffectInstance mobeffectinstance : collection) {
                    this.effects.add(new MobEffectInstance(mobeffectinstance));
                }
            }

            int i = getCustomColor(p_36879_);
            if (i == -1) {
                this.updateColor();
            } else {
                this.setFixedColor(i);
            }
        } else if (p_36879_.is(this.arrowMaterial.getArrowEntry().get())) {
            this.potion = Potions.EMPTY;
            this.effects.clear();
            this.entityData.set(ID_EFFECT_COLOR, -1);
        }

    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putString("ArrowMaterial", this.arrowMaterial.getName());
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compound) {
        if (compound.contains("ArrowMaterial")) {
            String type = compound.getString("ArrowMaterial");
            this.arrowMaterial = ArrowMaterial.valueOf(type);
        }
    }

    @Override
    protected @NotNull ItemStack getPickupItem() {
        if (this.effects.isEmpty() && this.potion == Potions.EMPTY) {
            return new ItemStack(this.arrowMaterial.getArrowEntry().get());
        }
        ItemStack itemstack = new ItemStack(this.arrowMaterial.getTippedArrowEntry().get());
        PotionUtils.setPotion(itemstack, this.potion);
        PotionUtils.setCustomEffects(itemstack, this.effects);
        if (this.fixedColor) {
            itemstack.getOrCreateTag().putInt("CustomPotionColor", this.getColor());
        }
        return itemstack;
    }

    public ArrowMaterial getArrowMaterial() {
        return  arrowMaterial;
    }

    public void setArrowType(ArrowMaterial arrowMaterial) {
        this.arrowMaterial = arrowMaterial;
    }
}
