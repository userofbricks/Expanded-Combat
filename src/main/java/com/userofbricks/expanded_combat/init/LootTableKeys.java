package com.userofbricks.expanded_combat.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootTable;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static com.userofbricks.expanded_combat.ExpandedCombat.modLoc;

public class LootTableKeys {
    private static final Set<ResourceKey<LootTable>> LOCATIONS = new HashSet<>();
    private static final Set<ResourceKey<LootTable>> IMMUTABLE_LOCATIONS = Collections.unmodifiableSet(LOCATIONS);

    public static final ResourceKey<LootTable> BASTION_TREASURE = register("chests/bastion_treasure");
    public static final ResourceKey<LootTable> END_CITY_TREASURE = register("chests/end_city_treasure");
    public static final ResourceKey<LootTable> SHIPWRECK_TREASURE = register("chests/shipwreck_treasure");
    public static final ResourceKey<LootTable> UNDERWATER_RUIN_BIG = register("chests/underwater_ruin_big");
    public static final ResourceKey<LootTable> WOODLAND_MANSION = register("chests/woodland_mansion");
    public static final ResourceKey<LootTable> BURIED_TREASURE = register("chests/buried_treasure");
    public static final ResourceKey<LootTable> DESERT_PYRAMID = register("chests/desert_pyramid");
    public static final ResourceKey<LootTable> PILLAGER_OUTPOST = register("chests/pillager_outpost");


    private static ResourceKey<LootTable> register(String p_78768_) {
        return register(ResourceKey.create(Registries.LOOT_TABLE, modLoc(p_78768_)));
    }

    private static ResourceKey<LootTable> register(ResourceKey<LootTable> p_335977_) {
        if (LOCATIONS.add(p_335977_)) {
            return p_335977_;
        } else {
            throw new IllegalArgumentException(p_335977_.location() + " is already a registered built-in loot table");
        }
    }

    public static Set<ResourceKey<LootTable>> all() {
        return IMMUTABLE_LOCATIONS;
    }
}
