package com.userofbricks.expanded_combat.mixin;

import com.userofbricks.expanded_combat.init.DataAttachments;
import com.userofbricks.expanded_combat.network.server.PacketIntAttachment;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.network.PacketDistributor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(Entity.class)
public abstract class EntityAttachmentMixin {
    @Shadow
    private int id;


    @Shadow public abstract Level level();

    @Inject(method = {"setData"}, at = @At("RETURN"))
    public <T> void setData(AttachmentType<T> type, T data, CallbackInfoReturnable<T> cir) {
        if (type == DataAttachments.STOLEN_HEALTH.get() && level() instanceof ServerLevel && ((Entity)(Object)this) instanceof ServerPlayer player) {
            PacketDistributor.sendToPlayer(player, new PacketIntAttachment(id, DataAttachments.STOLEN_HEALTH.get(), (Integer) data));
        }
    }
}
