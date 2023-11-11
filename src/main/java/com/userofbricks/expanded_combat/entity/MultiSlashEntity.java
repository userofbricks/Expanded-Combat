package com.userofbricks.expanded_combat.entity;

import com.userofbricks.expanded_combat.client.particles.MultiSlashParticleOption;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.BlockPositionSource;
import net.minecraft.world.level.gameevent.EntityPositionSource;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class MultiSlashEntity extends Entity implements TraceableEntity {
    @Nullable
    private UUID ownerUUID;
    @Nullable
    private Entity cachedOwner;
    private int timeTillSlash = 21;
    private int slashesLeft = 1;
    private float damage;
    private BlockPos direction;

    public MultiSlashEntity(EntityType<?> entityType, Level level) {
        super(entityType, level);
        this.noPhysics = true;
        this.noCulling = true;
    }

    @Override
    protected void defineSynchedData() {

    }

    protected void addAdditionalSaveData(@NotNull CompoundTag p_37265_) {
        if (this.ownerUUID != null) {
            p_37265_.putUUID("Owner", this.ownerUUID);
        }
        p_37265_.putInt("timeTillSlash", timeTillSlash);
        p_37265_.putInt("slashesLeft", slashesLeft);
        p_37265_.putFloat("damage", damage);
        p_37265_.putInt("directionX", direction.getX());
        p_37265_.putInt("directionY", direction.getY());
        p_37265_.putInt("directionZ", direction.getZ());
    }
    protected void readAdditionalSaveData(CompoundTag p_37262_) {
        if (p_37262_.hasUUID("Owner")) {
            this.ownerUUID = p_37262_.getUUID("Owner");
            this.cachedOwner = null;
        }
        this.timeTillSlash = p_37262_.getInt("timeTillSlash");
        this.slashesLeft = p_37262_.getInt("slashesLeft");
        this.damage = p_37262_.getFloat("damage");
        this.direction = new BlockPos(p_37262_.getInt("directionX"), p_37262_.getInt("directionY"), p_37262_.getInt("directionZ"));
    }
    public MultiSlashEntity setOwner(@Nullable Entity p_37263_) {
        if (p_37263_ != null) {
            this.ownerUUID = p_37263_.getUUID();
            this.cachedOwner = p_37263_;
        }
        return this;
    }
    public MultiSlashEntity setDamage(float damage) {
        this.damage = damage;
        return this;
    }
    public MultiSlashEntity setDirection(BlockPos direction) {
        this.direction = direction;
        return this;
    }
    public MultiSlashEntity setSlashesLeft(int slashesLeft) {
        this.slashesLeft = slashesLeft;
        return this;
    }
    public MultiSlashEntity setTimeTillSlash(int timeTillSlash) {
        this.timeTillSlash = timeTillSlash;
        return this;
    }
    protected boolean ownedBy(Entity p_150172_) {
        return p_150172_.getUUID().equals(this.ownerUUID);
    }
    @Nullable
    public Entity getOwner() {
        if (this.cachedOwner != null && !this.cachedOwner.isRemoved()) {
            return this.cachedOwner;
        } else if (this.ownerUUID != null && this.level() instanceof ServerLevel) {
            this.cachedOwner = ((ServerLevel)this.level()).getEntity(this.ownerUUID);
            return this.cachedOwner;
        } else {
            return null;
        }
    }

    protected boolean canHitEntity(Entity p_37250_) {
        Entity entity = this.getOwner();
        return entity == null || p_37250_ != entity;
    }

    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket() {
        Entity entity = this.getOwner();
        return new ClientboundAddEntityPacket(this, entity == null ? 0 : entity.getId());
    }

    public void recreateFromPacket(@NotNull ClientboundAddEntityPacket p_150170_) {
        super.recreateFromPacket(p_150170_);
        Entity entity = this.level().getEntity(p_150170_.getData());
        if (entity != null) {
            this.setOwner(entity);
        }

    }

    public boolean mayInteract(@NotNull Level p_150167_, @NotNull BlockPos p_150168_) {
        return false;
    }

    public void tick() {

        if (this.timeTillSlash == 3) {
            //level().addParticle(new MultiSlashParticleOption(new BlockPositionSource(direction)), this.getX(), this.getEyeY(), this.getZ(), 0, 0, 0);
        }
        if (this.timeTillSlash > 0) {
            this.timeTillSlash -= 1;
        } else {
            this.timeTillSlash = 21;
            List<Entity> list = this.level().getEntities(this, this.getBoundingBox(), EntitySelector.NO_SPECTATORS.and(this::canCollideWith).and(entity -> entity instanceof LivingEntity));
            for (Entity entity : list) {
                LivingEntity livingEntity = (LivingEntity) entity;
                Entity owner = this.getOwner();
                DamageSource damageSource = owner == null ? damageSources().generic() : owner instanceof Player ? damageSources().playerAttack((Player) owner) : damageSources().mobAttack((LivingEntity) owner);
                livingEntity.hurt(damageSource, damage);
            }
        }
        super.tick();
    }
}
