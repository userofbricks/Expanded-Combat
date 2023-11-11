package com.userofbricks.expanded_combat.client.particles;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.util.Mth;
import net.minecraft.world.level.gameevent.PositionSource;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.Optional;
import java.util.function.Consumer;

public class MultiSlashParticle extends TextureSheetParticle {
    private final PositionSource target;
    private float rot;
    private float rotO;
    private float pitch;
    private float pitchO;
    protected MultiSlashParticle(ClientLevel clientLevel, double xo, double yo, double zo, PositionSource positionSource) {
        super(clientLevel, xo, yo, zo, 0, 0, 0);
        this.quadSize = 0.3F;
        this.target = positionSource;
        this.lifetime = 8;
        Optional<Vec3> optional = positionSource.getPosition(clientLevel);
        if (optional.isPresent()) {
            Vec3 vec3 = optional.get();
            double d0 = xo - vec3.x();
            double d1 = yo - vec3.y();
            double d2 = zo - vec3.z();
            this.rotO = this.rot = (float) Mth.atan2(d0, d2);
            this.pitchO = this.pitch = (float)Mth.atan2(d1, Math.sqrt(d0 * d0 + d2 * d2));
        }
    }

    public void render(@NotNull VertexConsumer vertexConsumer, @NotNull Camera camera, float partialTicks) {
        float f = Mth.sin(((float)this.age + partialTicks - ((float)Math.PI * 2F)) * 0.05F) * 2.0F;
        float f1 = Mth.lerp(partialTicks, this.rotO, this.rot);
        float f2 = Mth.lerp(partialTicks, this.pitchO, this.pitch) + ((float)Math.PI / 2F);
        this.renderSignal(vertexConsumer, camera, partialTicks, (p_253355_) -> p_253355_.rotateY(f1).rotateX(-f2).rotateY(f));
        this.renderSignal(vertexConsumer, camera, partialTicks, (p_253351_) -> p_253351_.rotateY(-(float)Math.PI + f1).rotateX(f2).rotateY(f));
    }

    private void renderSignal(VertexConsumer vertexConsumer, Camera camera, float partialTicks, Consumer<Quaternionf> quaternionfConsumer) {
        Vec3 vec3 = camera.getPosition();
        float f = (float)(Mth.lerp(partialTicks, this.xo, this.x) - vec3.x());
        float f1 = (float)(Mth.lerp(partialTicks, this.yo, this.y) - vec3.y());
        float f2 = (float)(Mth.lerp(partialTicks, this.zo, this.z) - vec3.z());
        Vector3f vector3f = (new Vector3f(0.5F, 0.5F, 0.5F)).normalize();
        Quaternionf quaternionf = (new Quaternionf()).setAngleAxis(0.0F, vector3f.x(), vector3f.y(), vector3f.z());
        quaternionfConsumer.accept(quaternionf);
        Vector3f[] avector3f = new Vector3f[]{new Vector3f(-1.0F, -1.0F, 0.0F), new Vector3f(-1.0F, 1.0F, 0.0F), new Vector3f(1.0F, 1.0F, 0.0F), new Vector3f(1.0F, -1.0F, 0.0F)};
        float f3 = this.getQuadSize(partialTicks);

        for(int i = 0; i < 4; ++i) {
            Vector3f vector3f1 = avector3f[i];
            vector3f1.rotate(quaternionf);
            vector3f1.mul(f3);
            vector3f1.add(f, f1, f2);
        }

        float f6 = this.getU0();
        float f7 = this.getU1();
        float f4 = this.getV0();
        float f5 = this.getV1();
        int j = this.getLightColor(partialTicks);
        vertexConsumer.vertex(avector3f[0].x(), avector3f[0].y(), avector3f[0].z()).uv(f7, f5).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(j).endVertex();
        vertexConsumer.vertex(avector3f[1].x(), avector3f[1].y(), avector3f[1].z()).uv(f7, f4).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(j).endVertex();
        vertexConsumer.vertex(avector3f[2].x(), avector3f[2].y(), avector3f[2].z()).uv(f6, f4).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(j).endVertex();
        vertexConsumer.vertex(avector3f[3].x(), avector3f[3].y(), avector3f[3].z()).uv(f6, f5).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(j).endVertex();
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (this.age++ >= this.lifetime) {
            this.remove();
        } else {
            Optional<Vec3> optional = this.target.getPosition(this.level);
            if (optional.isEmpty()) {
                this.remove();
            } else {
                Vec3 vec3 = optional.get();
                double d1 = this.x - vec3.x();
                double d2 = this.y - vec3.y();
                double d3 = this.z - vec3.z();
                this.rotO = this.rot;
                this.rot = (float)Mth.atan2(d1, d3);
                this.pitchO = this.pitch;
                this.pitch = (float)Mth.atan2(d2, Math.sqrt(d1 * d1 + d3 * d3));
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<MultiSlashParticleOption> {
        private final SpriteSet sprite;

        public Provider(SpriteSet spriteSet) {
            this.sprite = spriteSet;
        }

        public Particle createParticle(MultiSlashParticleOption particleOption, @NotNull ClientLevel clientLevel, double xo, double yo, double zo, double xd, double yd, double zd) {
            MultiSlashParticle multiSlashParticle = new MultiSlashParticle(clientLevel, xo, yo, zo, particleOption.getTarget_point());
            multiSlashParticle.pickSprite(this.sprite);
            multiSlashParticle.setAlpha(1.0F);
            return multiSlashParticle;
        }
    }
}
