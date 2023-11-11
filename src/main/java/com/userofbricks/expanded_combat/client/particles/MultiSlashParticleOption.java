package com.userofbricks.expanded_combat.client.particles;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.BlockPositionSource;
import net.minecraft.world.level.gameevent.PositionSource;
import net.minecraft.world.level.gameevent.PositionSourceType;
import net.minecraft.world.phys.Vec3;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Locale;
import java.util.Optional;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class MultiSlashParticleOption implements ParticleOptions {
    public static final Codec<MultiSlashParticleOption> CODEC = RecordCodecBuilder.create(
            (optionInstance) -> optionInstance.group(PositionSource.CODEC.fieldOf("target_point").forGetter(
                    (particleOption) -> particleOption.target_point)).apply(optionInstance, MultiSlashParticleOption::new));
    public static final ParticleOptions.Deserializer<MultiSlashParticleOption> DESERIALIZER = new ParticleOptions.Deserializer<>() {
        public MultiSlashParticleOption fromCommand(ParticleType<MultiSlashParticleOption> optionParticleType, StringReader stringReader) throws CommandSyntaxException {
            stringReader.expect(' ');
            float f = (float) stringReader.readDouble();
            stringReader.expect(' ');
            float f1 = (float) stringReader.readDouble();
            stringReader.expect(' ');
            float f2 = (float) stringReader.readDouble();
            BlockPos blockpos = BlockPos.containing(f, f1, f2);
            return new MultiSlashParticleOption(new BlockPositionSource(blockpos));
        }

        public MultiSlashParticleOption fromNetwork(ParticleType<MultiSlashParticleOption> optionParticleType, FriendlyByteBuf friendlyByteBuf) {
            PositionSource positionsource = PositionSourceType.fromNetwork(friendlyByteBuf);
            return new MultiSlashParticleOption(positionsource);
        }
    };
    private final PositionSource target_point;

    public MultiSlashParticleOption(PositionSource p_235975_) {
        this.target_point = p_235975_;
    }
    @Override
    public ParticleType<?> getType() {
        return ParticleInit.SHOCKWAVE_PARTICLE.get();
    }

    @Override
    public void writeToNetwork(FriendlyByteBuf byteBuf) {
        PositionSourceType.toNetwork(this.target_point, byteBuf);
    }

    @Override
    public String writeToString() {
        Vec3 vec3 = this.target_point.getPosition(null).get();
        double d0 = vec3.x();
        double d1 = vec3.y();
        double d2 = vec3.z();
        return String.format(Locale.ROOT, "%s %.2f %.2f %.2f", BuiltInRegistries.PARTICLE_TYPE.getKey(this.getType()), d0, d1, d2);
    }

    public PositionSource getTarget_point() {
        return this.target_point;
    }
}
