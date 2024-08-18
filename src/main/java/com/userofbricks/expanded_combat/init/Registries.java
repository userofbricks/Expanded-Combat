package com.userofbricks.expanded_combat.init;

import com.userofbricks.expanded_combat.data.material.Material;
import com.userofbricks.expanded_combat.data.weapon_type.WeaponType;
import com.userofbricks.expanded_combat.item.generators.*;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.RegistryBuilder;

import static com.userofbricks.expanded_combat.ExpandedCombat.MODID;

@EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD)
public class Registries {
    public static final ResourceKey<Registry<Material>> MATERIAL_REGISTRY_KEY = ResourceKey.createRegistryKey(new ResourceLocation(MODID, "materials"));
    public static final Registry<Material> MATERIAL_REGISTRY = new RegistryBuilder<>(MATERIAL_REGISTRY_KEY)
            .defaultKey(new ResourceLocation(MODID, "vanilla"))
            .create();
    public static final ResourceKey<Registry<WeaponType>> WEAPON_TYPE_REGISTRY_KEY = ResourceKey.createRegistryKey(new ResourceLocation(MODID, "weapon_type"));
    public static final Registry<WeaponType> WEAPON_TYPE_REGISTRY = new RegistryBuilder<>(WEAPON_TYPE_REGISTRY_KEY)
            .create();
    public static final ResourceKey<Registry<GauntletType>> GAUNTLET_TYPE_REGISTRY_KEY = ResourceKey.createRegistryKey(new ResourceLocation(MODID, "gauntlet_type"));
    public static final Registry<GauntletType> GAUNTLET_TYPE_REGISTRY = new RegistryBuilder<>(GAUNTLET_TYPE_REGISTRY_KEY)
            .defaultKey(new ResourceLocation(MODID, "standard"))
            .create();
    public static final ResourceKey<Registry<BowType>> BOW_TYPE_REGISTRY_KEY = ResourceKey.createRegistryKey(new ResourceLocation(MODID, "bow_type"));
    public static final Registry<BowType> BOW_TYPE_REGISTRY = new RegistryBuilder<>(BOW_TYPE_REGISTRY_KEY)
            .defaultKey(new ResourceLocation(MODID, "standard"))
            .create();
    public static final ResourceKey<Registry<ArrowType>> ARROW_TYPE_REGISTRY_KEY = ResourceKey.createRegistryKey(new ResourceLocation(MODID, "arrow_type"));
    public static final Registry<ArrowType> ARROW_TYPE_REGISTRY = new RegistryBuilder<>(ARROW_TYPE_REGISTRY_KEY)
            .defaultKey(new ResourceLocation(MODID, "standard"))
            .create();
    public static final ResourceKey<Registry<CrossBowType>> CROSSBOW_TYPE_REGISTRY_KEY = ResourceKey.createRegistryKey(new ResourceLocation(MODID, "crossbow_type"));
    public static final Registry<CrossBowType> CROSSBOW_TYPE_REGISTRY = new RegistryBuilder<>(CROSSBOW_TYPE_REGISTRY_KEY)
            .defaultKey(new ResourceLocation(MODID, "standard"))
            .create();
    public static final ResourceKey<Registry<QuiverType>> QUIVER_TYPE_REGISTRY_KEY = ResourceKey.createRegistryKey(new ResourceLocation(MODID, "quiver_type"));
    public static final Registry<QuiverType> QUIVER_TYPE_REGISTRY = new RegistryBuilder<>(QUIVER_TYPE_REGISTRY_KEY)
            .defaultKey(new ResourceLocation(MODID, "standard"))
            .create();
    public static final ResourceKey<Registry<WeaponGenerator>> WEAPON_GENERATOR_REGISTRY_KEY = ResourceKey.createRegistryKey(new ResourceLocation(MODID, "weapon_generator"));
    public static final Registry<WeaponGenerator> WEAPON_GENERATOR_REGISTRY = new RegistryBuilder<>(WEAPON_GENERATOR_REGISTRY_KEY)
            .defaultKey(new ResourceLocation(MODID, "standard"))
            .create();

    @SubscribeEvent
    public static void registerRegistries(NewRegistryEvent event){
        event.register(ARROW_TYPE_REGISTRY);
        event.register(BOW_TYPE_REGISTRY);
        event.register(CROSSBOW_TYPE_REGISTRY);
        event.register(GAUNTLET_TYPE_REGISTRY);
        event.register(QUIVER_TYPE_REGISTRY);
        event.register(WEAPON_GENERATOR_REGISTRY);
        event.register(MATERIAL_REGISTRY);
        event.register(WEAPON_TYPE_REGISTRY);
    }
}
