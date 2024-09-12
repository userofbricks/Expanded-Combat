package com.userofbricks.expanded_combat.api.material;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.userofbricks.expanded_combat.config.MaterialConfig;
import com.userofbricks.expanded_combat.init.PluginInit;
import io.netty.buffer.ByteBuf;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Supplier;

/**
 * @param name the name of the material. used in the english lang generation and transcribed into the id.
 * @param id the identifier of the material used for storing the material on items and locating resources for the material.
 * @param durability {@link MaterialConfig.Durability Durability}
 * @param enchanting {@link MaterialConfig.Enchanting Enchanting}
 * @param offense {@link MaterialConfig.Offense Offence}
 * @param defense {@link MaterialConfig.Defense Defence}
 * @param crafting {@link MaterialConfig.Crafting Crafting}
 * @param repairItem items that can be used to repair items of the material in an anvil.
 * @param craftingItem what to use for crafting during datagen.
 * @param smithingTemplate the smithing template used for single addition materials
 *
 */
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public record Material(
        @NotNull String name,
        @NotNull ResourceLocation id,
        MaterialConfig.Durability durability,
        MaterialConfig.Enchanting enchanting,
        MaterialConfig.Offense offense,
        MaterialConfig.Defense defense,
        MaterialConfig.Crafting crafting,
        @Nullable Supplier<Ingredient> repairItem,
        @Nullable Supplier<Ingredient> craftingItem,
        @Nullable Supplier<Ingredient> smithingTemplate
){
    public Material(@NotNull String name, @NotNull ResourceLocation id, @NotNull MaterialConfig config,
                    @Nullable Supplier<Ingredient> repairItem,
                    @Nullable Supplier<Ingredient> craftingItem, @Nullable Supplier<Ingredient> smithingTemplate) {
        this(name, id, config.durability, config.enchanting, config.offense, config.defense, config.crafting, repairItem, craftingItem, smithingTemplate);
    }
    public Material(@NotNull String name, @NotNull ResourceLocation id,
                    @Nullable Supplier<Ingredient> repairItem, @NotNull MaterialConfig config) {
        this(name, id, config, repairItem, null, null);
    }
    public Material(@NotNull String name, @NotNull ResourceLocation id, @NotNull MaterialConfig config) {
        this(name, id, config, null, null, null);
    }

    public static final Codec<Material> CODEC = ResourceLocation.CODEC
            .comapFlatMap(
                    resourceLocation -> {
                        Material material = PluginInit.materials.get(resourceLocation);
                        if (material == null) return DataResult.error(() -> "Unknown material: " + resourceLocation);
                        else return DataResult.success(material);
                    },
                    material -> material.id
            );
    public static final StreamCodec<ByteBuf, Material> STREAM_CODEC = ByteBufCodecs.fromCodec(Material.CODEC);
}
