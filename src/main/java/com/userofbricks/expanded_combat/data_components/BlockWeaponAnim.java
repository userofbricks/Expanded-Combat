package com.userofbricks.expanded_combat.data_components;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

import java.util.function.IntFunction;

public enum BlockWeaponAnim implements StringRepresentable {
    BLOCK_1(0, "block_1"),
    BLOCK_2(1, "block_2"),
    BLOCK_3(2, "block_3"),
    BLOCK_4(3, "block_4"),
    BLOCK_5(4, "block_5");

    public static final Codec<BlockWeaponAnim> CODEC = StringRepresentable.fromValues(BlockWeaponAnim::values);
    public static final IntFunction<BlockWeaponAnim> BY_ID = ByIdMap.continuous(block -> block.id, values(), ByIdMap.OutOfBoundsStrategy.ZERO);
    public static final StreamCodec<ByteBuf, BlockWeaponAnim> STREAM_CODEC = ByteBufCodecs.idMapper(BY_ID, block -> block.id);
    public final int id;
    public final String name;
    BlockWeaponAnim(int pId, String pName) {
        this.id = pId;
        this.name = pName;
    }
    @Override
    public @NotNull String getSerializedName() {
        return name;
    }
}
