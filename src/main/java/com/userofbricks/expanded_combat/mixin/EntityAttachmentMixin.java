package com.userofbricks.expanded_combat.mixin;

import com.userofbricks.expanded_combat.init.DataAttachments;
import com.userofbricks.expanded_combat.network.server.PacketIntAttachment;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.network.PacketDistributor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(Entity.class)
public class EntityAttachmentMixin {
    @Inject(method = {"setData"}, at = @At("RETURN"))
    public <T> void setData(AttachmentType<T> type, T data, CallbackInfoReturnable<T> cir) {
        if ((type == DataAttachments.STOLEN_HEALTH.get() || type == DataAttachments.ARROW_SLOT.get())
        && ((Entity)(Object)this) instanceof ServerPlayer serverPlayer) {
            PacketDistributor.sendToPlayer(serverPlayer, new PacketIntAttachment((AttachmentType<Integer>) type, (Integer) data));
        }
    }
}
