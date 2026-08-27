package com.userofbricks.expanded_combat.datagen.recipes;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.JsonOps;
import com.userofbricks.expanded_combat.item.recipes.conditions.ECConfigBooleanCondition;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.common.conditions.ModLoadedCondition;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

/**
 * Datagen helper for {@code overgeared:fletching} recipes.
 * {@link net.minecraft.data.recipes.RecipeOutput} cannot emit another mod's serializer id,
 * so this writes the JSON object for the caller to save.
 */
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class OvergearedFletchingRecipeBuilder {
    private static final String OVERGEARED_MODID = "overgeared";
    private static final String RECIPE_TYPE = OVERGEARED_MODID + ":fletching";

    private final Ingredient tip;
    private final Ingredient shaft;
    private final Ingredient feather;
    private final ItemStack result;
    private Ingredient potion = Ingredient.EMPTY;
    private ItemStack resultTipped = ItemStack.EMPTY;
    private ItemStack resultLingering = ItemStack.EMPTY;
    @Nullable
    private String tippedTag;
    @Nullable
    private String lingeringTag;
    private final List<ICondition> conditions = new ArrayList<>();

    public OvergearedFletchingRecipeBuilder(Ingredient tip, Ingredient shaft, Ingredient feather, ItemStack result) {
        this.tip = tip;
        this.shaft = shaft;
        this.feather = feather;
        this.result = result;
        this.conditions.add(new ModLoadedCondition(OVERGEARED_MODID));
        this.conditions.add(new ECConfigBooleanCondition("arrow"));
    }

    public static OvergearedFletchingRecipeBuilder fletching(Ingredient tip, Ingredient shaft, Ingredient feather, ItemLike result) {
        return fletching(tip, shaft, feather, result, 1);
    }

    public static OvergearedFletchingRecipeBuilder fletching(Ingredient tip, Ingredient shaft, Ingredient feather, ItemLike result, int count) {
        return new OvergearedFletchingRecipeBuilder(tip, shaft, feather, new ItemStack(result, count));
    }

    public OvergearedFletchingRecipeBuilder withPotion(Ingredient potion) {
        this.potion = potion;
        return this;
    }

    public OvergearedFletchingRecipeBuilder withTippedResult(ItemLike result) {
        return withTippedResult(result, this.result.getCount());
    }

    public OvergearedFletchingRecipeBuilder withTippedResult(ItemLike result, int count) {
        this.resultTipped = new ItemStack(result, count);
        return this;
    }

    public OvergearedFletchingRecipeBuilder withLingeringResult(ItemLike result) {
        return withLingeringResult(result, this.result.getCount());
    }

    public OvergearedFletchingRecipeBuilder withLingeringResult(ItemLike result, int count) {
        this.resultLingering = new ItemStack(result, count);
        return this;
    }

    public OvergearedFletchingRecipeBuilder withTippedTag(String tag) {
        this.tippedTag = tag;
        return this;
    }

    public OvergearedFletchingRecipeBuilder withLingeringTag(String tag) {
        this.lingeringTag = tag;
        return this;
    }

    public OvergearedFletchingRecipeBuilder withCondition(ICondition condition) {
        this.conditions.add(condition);
        return this;
    }

    public Item getResult() {
        return this.result.getItem();
    }

    public void save(HolderLookup.Provider registries, BiConsumer<ResourceLocation, JsonObject> output) {
        this.save(registries, output, RecipeBuilder.getDefaultRecipeId(this.getResult()).withPrefix("overgeared/"));
    }

    public void save(HolderLookup.Provider registries, BiConsumer<ResourceLocation, JsonObject> output, ResourceLocation recipeId) {
        output.accept(recipeId, this.serialize(registries));
    }

    public JsonObject serialize(HolderLookup.Provider registries) {
        var ops = registries.createSerializationContext(JsonOps.INSTANCE);
        JsonObject json = new JsonObject();
        json.addProperty("type", RECIPE_TYPE);
        addIngredient(json, "tip", this.tip, ops);
        addIngredient(json, "shaft", this.shaft, ops);
        addIngredient(json, "feather", this.feather, ops);
        addIngredient(json, "potion", this.potion, ops);
        json.add("result", encode(ItemStack.CODEC, ops, this.result));
        if (!this.resultTipped.isEmpty()) {
            json.add("result_tipped", encode(ItemStack.CODEC, ops, this.resultTipped));
        }
        if (!this.resultLingering.isEmpty()) {
            json.add("result_lingering", encode(ItemStack.CODEC, ops, this.resultLingering));
        }
        if (this.tippedTag != null) {
            json.addProperty("tipped_tag", this.tippedTag);
        }
        if (this.lingeringTag != null) {
            json.addProperty("lingering_tag", this.lingeringTag);
        }
        ICondition.writeConditions(registries, json, this.conditions);
        return json;
    }

    private static void addIngredient(JsonObject json, String field, Ingredient ingredient, DynamicOps<JsonElement> ops) {
        if (!ingredient.isEmpty()) {
            json.add(field, encode(Ingredient.CODEC, ops, ingredient));
        }
    }

    private static <T> JsonElement encode(Codec<T> codec, DynamicOps<JsonElement> ops, T value) {
        return codec.encodeStart(ops, value).getOrThrow();
    }
}
