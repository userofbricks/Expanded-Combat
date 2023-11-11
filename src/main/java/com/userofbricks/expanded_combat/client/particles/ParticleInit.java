package com.userofbricks.expanded_combat.client.particles;

import com.mojang.serialization.Codec;
import net.minecraft.core.particles.ParticleType;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import static com.userofbricks.expanded_combat.ExpandedCombat.MODID;

@Mod.EventBusSubscriber(modid = MODID)
public class ParticleInit {
    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, MODID);

    public static final RegistryObject<ParticleType<MultiSlashParticleOption>> SHOCKWAVE_PARTICLE = PARTICLES.register("multi_slash", () -> new ParticleType<>(true, MultiSlashParticleOption.DESERIALIZER) {
        @Override
        public @NotNull Codec<MultiSlashParticleOption> codec() {
            return MultiSlashParticleOption.CODEC;
        }
    });

    @SubscribeEvent
    public static void registerParticleProviders(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(SHOCKWAVE_PARTICLE.get(), MultiSlashParticle.Provider::new);
    }
}
