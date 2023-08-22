package com.userofbricks.expanded_combat.datagen;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.google.gson.JsonObject;
import com.userofbricks.expanded_combat.util.ModIDs;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public abstract class BetterCombatWeaponAttributesProvider implements DataProvider {

    private final PackOutput output;
    private final String modId;
    private final ExistingFileHelper helper;
    protected final Map<String, ResourceLocation> builders = Maps.newLinkedHashMap();

    public BetterCombatWeaponAttributesProvider(PackOutput output, final String modId, final ExistingFileHelper helper) {
        this.output = output;
        this.modId = modId;
        this.helper = helper;
    }

    public abstract void registerTransforms();

    @Override
    public @NotNull CompletableFuture<?> run(@NotNull CachedOutput cache) {
        this.builders.clear();
        this.registerTransforms();

        ImmutableList.Builder<CompletableFuture<?>> futuresBuilder = new ImmutableList.Builder<>();

        this.builders.forEach((name, transform) -> {
            List<String> list = builders.keySet().stream()
                    .filter(s -> ForgeRegistries.ITEMS.containsKey(new ResourceLocation(this.modId, name)))
                    .filter(s -> !this.builders.containsKey(s))
                    .filter(this::missing).toList();

            if (!list.isEmpty()) {
                throw new IllegalArgumentException(String.format("Duplicate Weapon Attributes: %s", list.stream().map(Objects::toString).collect(Collectors.joining(", "))));
            } else {
                JsonObject obj = serializeToJson(transform);
                Path path = createPath(new ResourceLocation(modId, name));
                futuresBuilder.add(DataProvider.saveStable(cache, obj, path));
            }
        });
        return CompletableFuture.allOf(futuresBuilder.build().toArray(CompletableFuture[]::new));
    }

    private boolean missing(String name) {
        return this.helper == null || !this.helper.exists(new ResourceLocation(this.modId, name), new ExistingFileHelper.ResourceType(net.minecraft.server.packs.PackType.SERVER_DATA, ".json", "weapon_attributes"));
    }

    private Path createPath(ResourceLocation name) {
        return this.output.getOutputFolder().resolve("data/" + name.getNamespace() + "/weapon_attributes/" + name.getPath() + ".json");
    }

    private JsonObject serializeToJson(ResourceLocation transform) {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("parent", transform.toString());
        return jsonObject;
    }

    @Override
    public @NotNull String getName() {
        return this.modId + " Better Combat Weapon Attributes";
    }

    //helper methods
    public void add(Item weapon, ResourceLocation weaponAtrributesParent) {
        this.builders.put(Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(weapon)).getPath(), weaponAtrributesParent);
    }

    public void add(Item weapon, String weaponAtrributesParent) {
        add(weapon, new ResourceLocation(ModIDs.BetterCombatMOD_ID, weaponAtrributesParent));
    }
}
