package com.userofbricks.expanded_combat.init;

import com.mojang.serialization.Codec;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

import static com.userofbricks.expanded_combat.ExpandedCombat.MODID;

public class DataAttachments {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, MODID);

    //Server and Client attachments
    public static final Supplier<AttachmentType<Integer>> STOLEN_HEALTH = ATTACHMENT_TYPES.register(
            "stolen_health", () -> AttachmentType.builder(() -> 0).serialize(Codec.INT).copyOnDeath().build());
    public static final Supplier<AttachmentType<Integer>> ARROW_SLOT = ATTACHMENT_TYPES.register(
            "added_health", () -> AttachmentType.builder(() -> 0).serialize(Codec.INT).copyOnDeath().build());

    //Server side only attachments
    public static final Supplier<AttachmentType<Integer>> WEAPON_BLOCK_COUNT = ATTACHMENT_TYPES.register(
            "added_health", () -> AttachmentType.builder(() -> 0).serialize(Codec.INT).copyOnDeath().build());
    public static final Supplier<AttachmentType<Integer>> TIME_SINCE_WEAPON_BLOCK = ATTACHMENT_TYPES.register(
            "added_health", () -> AttachmentType.builder(() -> 0).serialize(Codec.INT).copyOnDeath().build());
    public static final Supplier<AttachmentType<Integer>> ADDED_HEALTH = ATTACHMENT_TYPES.register(
            "added_health", () -> AttachmentType.builder(() -> 0).serialize(Codec.INT).copyOnDeath().build());
}
