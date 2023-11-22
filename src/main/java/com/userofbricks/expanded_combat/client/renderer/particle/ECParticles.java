package com.userofbricks.expanded_combat.client.renderer.particle;

import net.minecraft.client.particle.SuspendedTownParticle;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.userofbricks.expanded_combat.ExpandedCombat.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ECParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_OPTIONS = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, MODID);

    public static final RegistryObject<SimpleParticleType> PURIFIED_GAS = PARTICLE_OPTIONS.register("pure_gas", () -> new SimpleParticleType(false));

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void registerToParticleEngine(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(PURIFIED_GAS.get(), SuspendedTownParticle.Provider::new);
    }
}
