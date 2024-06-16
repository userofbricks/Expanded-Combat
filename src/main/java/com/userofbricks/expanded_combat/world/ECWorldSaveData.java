package com.userofbricks.expanded_combat.world;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;
import org.jetbrains.annotations.NotNull;

public class ECWorldSaveData extends SavedData {
    public static final String DATA_NAME = "expanded_combat_world_save_data";
    public static final String heartstealerCountNBTName = "heartStealerCount";
    protected int heartstealerCount = 0;

    public ECWorldSaveData() {
    }

    public static ECWorldSaveData load(CompoundTag tag, HolderLookup.@NotNull Provider pRegistries) {
        int heartstealerCount = tag.getInt(heartstealerCountNBTName);
        ECWorldSaveData data = new ECWorldSaveData();
        data.heartstealerCount = heartstealerCount;
        return data;
    }
    @Override
    public @NotNull CompoundTag save(@NotNull CompoundTag pTag, HolderLookup.@NotNull Provider pRegistries) {
        pTag.putInt(heartstealerCountNBTName, heartstealerCount);
        return pTag;
    }
    public static ECWorldSaveData get(ServerLevel world) {
        return world.getDataStorage().computeIfAbsent(new Factory<>(ECWorldSaveData::new, ECWorldSaveData::load), DATA_NAME);
    }
    public static double getHeartStealerCount(ServerLevel world) {
        return get(world).heartstealerCount;
    }
    public static void increaseHeartStealerCount(ServerLevel world) {
        get(world).heartstealerCount += 1;
    }
}
