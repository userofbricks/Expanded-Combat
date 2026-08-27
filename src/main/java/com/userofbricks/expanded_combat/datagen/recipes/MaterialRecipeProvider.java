package com.userofbricks.expanded_combat.datagen.recipes;

import com.google.gson.JsonObject;
import com.userofbricks.expanded_combat.init.ECItems;
import com.userofbricks.expanded_combat.item.recipes.builders.FletchingRecipeBuilder;
import com.userofbricks.expanded_combat.item.recipes.builders.TippedArrowRecipeBuilder;
import com.userofbricks.expanded_combat.item.recipes.conditions.ECConfigBooleanCondition;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.conditions.ModLoadedCondition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public abstract class MaterialRecipeProvider extends RecipeProvider {
    private final List<CompletableFuture<?>> overgearedRecipeSaves = new ArrayList<>();
    private CachedOutput cache;

    public MaterialRecipeProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pRegistries) {
        super(pOutput, pRegistries);
    }

    @Override
    protected CompletableFuture<?> run(CachedOutput cache, HolderLookup.Provider registries) {
        this.cache = cache;
        this.overgearedRecipeSaves.clear();
        List<CompletableFuture<?>> saves = new ArrayList<>();
        saves.add(super.run(cache, registries));
        saves.addAll(this.overgearedRecipeSaves);
        return CompletableFuture.allOf(saves.toArray(CompletableFuture[]::new));
    }

    protected void saveOvergearedRecipe(ResourceLocation recipeId, JsonObject json) {
        this.overgearedRecipeSaves.add(DataProvider.saveStable(this.cache, json, this.recipePathProvider.json(recipeId)));
    }

    public static void battleStaff(RecipeOutput recipeOutput, ItemLike staff, Ingredient material) {
        battleStaff(recipeOutput, staff, ECItems.LEATHER_STICK.get(), material);
    }
    public static void battleStaff(RecipeOutput recipeOutput, ItemLike staff, ItemLike stick, Ingredient material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, staff)
                .define('i', material)
                .define('s', stick)
                .pattern("  i")
                .pattern(" s ")
                .pattern("i  ")
                .unlockedBy(getHasName(stick), has(stick))
                .unlockedBy("has_material", inventoryTrigger(ItemPredicate.Builder.item().of(Arrays.stream(material.getItems()).map(ItemStack::getItem).toArray(ItemLike[]::new))))
                .save(recipeOutput.withConditions(new ECConfigBooleanCondition("weapon")));
    }
    public static void broadSword(RecipeOutput recipeOutput, ItemLike broadSword, ItemLike sword, Ingredient material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, broadSword)
                .define('i', material)
                .define('s', sword)
                .pattern(" i ")
                .pattern("isi")
                .unlockedBy(getHasName(sword), has(sword))
                .unlockedBy("has_material", inventoryTrigger(ItemPredicate.Builder.item().of(Arrays.stream(material.getItems()).map(ItemStack::getItem).toArray(ItemLike[]::new))))
                .save(recipeOutput.withConditions(new ECConfigBooleanCondition("weapon")));
    }
    public static void claymore(RecipeOutput recipeOutput, ItemLike claymore, ItemLike sword, Ingredient material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, claymore)
                .define('i', material)
                .define('s', sword)
                .pattern("i")
                .pattern("i")
                .pattern("s")
                .unlockedBy(getHasName(sword), has(sword))
                .unlockedBy("has_material", inventoryTrigger(ItemPredicate.Builder.item().of(Arrays.stream(material.getItems()).map(ItemStack::getItem).toArray(ItemLike[]::new))))
                .save(recipeOutput.withConditions(new ECConfigBooleanCondition("weapon")));
    }
    public static void cutlass(RecipeOutput recipeOutput, ItemLike cutlass, Ingredient material) {
        cutlass(recipeOutput, cutlass, ECItems.GOLD_STICK.get(), material);
    }
    public static void cutlass(RecipeOutput recipeOutput, ItemLike cutlass, ItemLike stick, Ingredient material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, cutlass)
                .define('i', material)
                .define('s', stick)
                .pattern("i")
                .pattern("i")
                .pattern("s")
                .unlockedBy(getHasName(stick), has(stick))
                .unlockedBy("has_material", inventoryTrigger(ItemPredicate.Builder.item().of(Arrays.stream(material.getItems()).map(ItemStack::getItem).toArray(ItemLike[]::new))))
                .save(recipeOutput.withConditions(new ECConfigBooleanCondition("weapon")));
    }
    public static void dagger(RecipeOutput recipeOutput, ItemLike dagger, Ingredient material) {
        dagger(recipeOutput, dagger, ECItems.IRON_STICK.get(), material);
    }
    public static void dagger(RecipeOutput recipeOutput, ItemLike dagger, ItemLike stick, Ingredient material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, dagger)
                .define('i', material)
                .define('s', stick)
                .pattern("i")
                .pattern("s")
                .unlockedBy(getHasName(stick), has(stick))
                .unlockedBy("has_material", inventoryTrigger(ItemPredicate.Builder.item().of(Arrays.stream(material.getItems()).map(ItemStack::getItem).toArray(ItemLike[]::new))))
                .save(recipeOutput.withConditions(new ECConfigBooleanCondition("weapon")));
    }
    public static void dancersSword(RecipeOutput recipeOutput, ItemLike dancersSword, ItemLike sword) {
        dancersSword(recipeOutput, dancersSword, sword, ECItems.IRON_STICK.get());
    }
    public static void dancersSword(RecipeOutput recipeOutput, ItemLike dancersSword, ItemLike sword, ItemLike stick) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, dancersSword)
                .define('p', sword)
                .define('s', stick)
                .pattern("  p")
                .pattern(" s ")
                .pattern("p  ")
                .unlockedBy(getHasName(sword), has(sword))
                .unlockedBy(getHasName(stick), has(stick))
                .save(recipeOutput.withConditions(new ECConfigBooleanCondition("weapon")));
    }
    public static void flail(RecipeOutput recipeOutput, ItemLike flail, ItemLike block) {
        flail(recipeOutput, flail, block, Items.CHAIN, Items.STICK);
    }
    public static void flail(RecipeOutput recipeOutput, ItemLike flail, ItemLike block, ItemLike chain, ItemLike stick) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, flail)
                .define('b', block)
                .define('c', chain)
                .define('s', stick)
                .pattern("b")
                .pattern("c")
                .pattern("s")
                .unlockedBy(getHasName(block), has(block))
                .unlockedBy(getHasName(chain), has(chain))
                .unlockedBy(getHasName(stick), has(stick))
                .save(recipeOutput.withConditions(new ECConfigBooleanCondition("weapon")));
    }
    public static void glaive(RecipeOutput recipeOutput, ItemLike glaive, ItemLike sword) {
        glaive(recipeOutput, glaive, sword, Items.STICK);
    }
    public static void glaive(RecipeOutput recipeOutput, ItemLike glaive, ItemLike sword, ItemLike stick) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, glaive)
                .define('p', sword)
                .define('s', stick)
                .pattern("  p")
                .pattern(" s ")
                .pattern("s  ")
                .unlockedBy(getHasName(sword), has(sword))
                .unlockedBy(getHasName(stick), has(stick))
                .save(recipeOutput.withConditions(new ECConfigBooleanCondition("weapon")));
    }
    public static void greatHammer(RecipeOutput recipeOutput, ItemLike greatHammer, ItemLike block) {
        greatHammer(recipeOutput, greatHammer, block, Items.STICK);
    }
    public static void greatHammer(RecipeOutput recipeOutput, ItemLike greatHammer, ItemLike block, ItemLike stick) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, greatHammer)
                .define('b', block)
                .define('s', stick)
                .pattern("  b")
                .pattern(" s ")
                .pattern("s  ")
                .unlockedBy(getHasName(block), has(block))
                .unlockedBy(getHasName(stick), has(stick))
                .save(recipeOutput.withConditions(new ECConfigBooleanCondition("weapon")));
    }
    public static void katana(RecipeOutput recipeOutput, ItemLike katana, ItemLike sword, Ingredient material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, katana)
                .define('s', sword)
                .define('i', material)
                .pattern("i")
                .pattern("s")
                .unlockedBy(getHasName(sword), has(sword))
                .unlockedBy("has_material", inventoryTrigger(ItemPredicate.Builder.item().of(Arrays.stream(material.getItems()).map(ItemStack::getItem).toArray(ItemLike[]::new))))
                .save(recipeOutput.withConditions(new ECConfigBooleanCondition("weapon")));
    }
    public static void mace(RecipeOutput recipeOutput, ItemLike mace, ItemLike block) {
        mace(recipeOutput, mace, block, Items.STICK);
    }
    public static void mace(RecipeOutput recipeOutput, ItemLike mace, ItemLike block, ItemLike stick) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, mace)
                .define('b', block)
                .define('s', stick)
                .pattern(" b")
                .pattern("s ")
                .unlockedBy(getHasName(block), has(block))
                .unlockedBy(getHasName(stick), has(stick))
                .save(recipeOutput.withConditions(new ECConfigBooleanCondition("weapon")));
    }
    public static void scythe(RecipeOutput recipeOutput, ItemLike scythe, ItemLike sword, Ingredient material) {
        scythe(recipeOutput, scythe, sword, material, Items.STICK);
    }
    public static void scythe(RecipeOutput recipeOutput, ItemLike scythe, ItemLike sword, Ingredient material, ItemLike stick) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, scythe)
                .define('p', sword)
                .define('i', material)
                .define('s', stick)
                .pattern("ip ")
                .pattern("  s")
                .pattern("  s")
                .unlockedBy(getHasName(sword), has(sword))
                .unlockedBy("has_material", inventoryTrigger(ItemPredicate.Builder.item().of(Arrays.stream(material.getItems()).map(ItemStack::getItem).toArray(ItemLike[]::new))))
                .unlockedBy(getHasName(stick), has(stick))
                .save(recipeOutput.withConditions(new ECConfigBooleanCondition("weapon")));
    }
    public static void sickle(RecipeOutput recipeOutput, ItemLike sickle, Ingredient material) {
        sickle(recipeOutput, sickle, material, Items.STICK);
    }
    public static void sickle(RecipeOutput recipeOutput, ItemLike sickle, Ingredient material, ItemLike stick) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, sickle)
                .define('i', material)
                .define('s', stick)
                .pattern("ii")
                .pattern("s ")
                .unlockedBy("has_material", inventoryTrigger(ItemPredicate.Builder.item().of(Arrays.stream(material.getItems()).map(ItemStack::getItem).toArray(ItemLike[]::new))))
                .unlockedBy(getHasName(stick), has(stick))
                .save(recipeOutput.withConditions(new ECConfigBooleanCondition("weapon")));
    }
    public static void spear(RecipeOutput recipeOutput, ItemLike spear, ItemLike sword) {
        spear(recipeOutput, spear, sword, Items.STICK);
    }
    public static void spear(RecipeOutput recipeOutput, ItemLike spear, ItemLike sword, ItemLike stick) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, spear)
                .define('p', sword)
                .define('s', stick)
                .pattern("p")
                .pattern("s")
                .pattern("s")
                .unlockedBy(getHasName(sword), has(sword))
                .unlockedBy(getHasName(stick), has(stick))
                .save(recipeOutput.withConditions(new ECConfigBooleanCondition("weapon")));
    }


    public static void arrow(RecipeOutput recipeOutput, ItemLike arrow, ItemLike tip) {
        arrow(recipeOutput, arrow, 4, tip);
    }
    public static void arrow(RecipeOutput recipeOutput, ItemLike arrow, int amount, ItemLike tip) {
        arrow(recipeOutput, arrow, amount, tip, Items.STICK, Items.FEATHER);
    }
    public static void arrow(RecipeOutput recipeOutput, ItemLike arrow, ItemLike tip, ItemLike stick, ItemLike feather) {
        arrow(recipeOutput, arrow, 4, tip, stick, feather);
    }
    public static void arrow(RecipeOutput recipeOutput, ItemLike arrow, int amount, ItemLike tip, ItemLike stick, ItemLike feather) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, arrow, amount)
                .define('x', tip)
                .define('#', stick)
                .define('y', feather)
                .pattern("x")
                .pattern("#")
                .pattern("y")
                .unlockedBy(getHasName(tip), has(tip))
                .unlockedBy(getHasName(stick), has(stick))
                .unlockedBy(getHasName(feather), has(feather))
                .save(recipeOutput.withConditions(new ECConfigBooleanCondition("arrow")));
    }

    public static void tippedArrow(RecipeOutput recipeOutput, ItemLike tipped_arrow, ItemLike arrow) {
        new TippedArrowRecipeBuilder(new ItemStack(tipped_arrow), Ingredient.of(arrow))
                .save(recipeOutput.withConditions(new ECConfigBooleanCondition("arrow")));
    }

    public static void fletching(RecipeOutput recipeOutput, ItemLike result, ItemLike addition, ItemLike previosItem, int resultCount) {
        fletching(recipeOutput, result, Ingredient.of(addition), previosItem, resultCount);
    }

    public static void fletching(RecipeOutput recipeOutput, ItemLike result, Ingredient addition, ItemLike previosItem, int resultCount) {

        FletchingRecipeBuilder.fletching(Ingredient.of(previosItem), addition, RecipeCategory.COMBAT, result.asItem(), resultCount)
                .unlockedBy("has_item", has(previosItem))
                .unlockedBy("has_material", inventoryTrigger(ItemPredicate.Builder.item().of(Arrays.stream(addition.getItems()).map(ItemStack::getItem).toArray(ItemLike[]::new))))
                .save(recipeOutput.withConditions(new ECConfigBooleanCondition("arrow")));
    }

    public static void variableFletching(RecipeOutput recipeOutput, ItemLike result, ItemLike addition, ItemLike previosItem, int maxResultCount) {

        FletchingRecipeBuilder.fletchingVarableResult(Ingredient.of(previosItem), Ingredient.of(addition), RecipeCategory.COMBAT, result.asItem(), maxResultCount)
                .unlockedBy("has_item", has(previosItem))
                .unlockedBy("has_material", has(addition))
                .save(recipeOutput.withConditions(new ECConfigBooleanCondition("arrow")),
                        RecipeBuilder.getDefaultRecipeId(result).withSuffix("_variable_fletching"));
    }

    public void overgearedFletching(HolderLookup.Provider registries, ItemLike result, ItemLike tippedResult, Ingredient material) {
        overgearedFletching(registries, result, tippedResult, 6, Items.STICK, Items.FEATHER, material);
    }
    public void overgearedFletching(HolderLookup.Provider registries, ItemLike result, ItemLike tippedResult, int resultCount, Ingredient material) {
        overgearedFletching(registries, result, tippedResult, resultCount, Items.STICK, Items.FEATHER, material);
    }

    public void overgearedFletching(HolderLookup.Provider registries, ItemLike result, ItemLike tippedResult, int resultCount, ItemLike stick, ItemLike feather, Ingredient material) {
        OvergearedFletchingRecipeBuilder.fletching(
                        material,
                        Ingredient.of(stick),
                        Ingredient.of(feather),
                        result,
                        resultCount)
                .withTippedResult(tippedResult)
                .withCondition(new ECConfigBooleanCondition("arrow"))
                .withCondition(new ModLoadedCondition("overgeared"))
                .save(registries, this::saveOvergearedRecipe);
    }

    public static void bow(RecipeOutput recipeOutput, ItemLike bow, Ingredient material) {
        bow(recipeOutput, bow, Items.STRING, ECItems.IRON_STICK.get(), material);
    }
    public static void bow(RecipeOutput recipeOutput, ItemLike bow, ItemLike string, ItemLike stick, Ingredient material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, bow, 1)
                .define('s', string)
                .define('/', stick)
                .define('m', material)
                .pattern(" ms")
                .pattern("/ s")
                .pattern(" ms")
                .unlockedBy(getHasName(string), has(string))
                .unlockedBy(getHasName(stick), has(stick))
                .unlockedBy("has_material", inventoryTrigger(ItemPredicate.Builder.item().of(Arrays.stream(material.getItems()).map(ItemStack::getItem).toArray(ItemLike[]::new))))
                .save(recipeOutput.withConditions(new ECConfigBooleanCondition("bow")));
    }

    public static void crossbow(RecipeOutput recipeOutput, ItemLike crossbow, Ingredient material) {
        crossbow(recipeOutput, crossbow, Items.CROSSBOW, material);
    }
    public static void crossbow(RecipeOutput recipeOutput, ItemLike crossbow, ItemLike previous, Ingredient material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, crossbow, 1)
                .define('p', previous)
                .define('m', material)
                .pattern(" m ")
                .pattern("mpm")
                .unlockedBy(getHasName(previous), has(previous))
                .unlockedBy("has_material", inventoryTrigger(ItemPredicate.Builder.item().of(Arrays.stream(material.getItems()).map(ItemStack::getItem).toArray(ItemLike[]::new))))
                .save(recipeOutput.withConditions(new ECConfigBooleanCondition("crossbow")));
    }
    public static void gauntlet(RecipeOutput recipeOutput, ItemLike gauntlet, Ingredient material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, gauntlet, 1)
                .define('b', material)
                .pattern("bb")
                .pattern("b ")
                .unlockedBy("has_material", inventoryTrigger(ItemPredicate.Builder.item().of(Arrays.stream(material.getItems()).map(ItemStack::getItem).toArray(ItemLike[]::new))))
                .save(recipeOutput.withConditions(new ECConfigBooleanCondition("gauntlet")));
    }

    public static void quiver(RecipeOutput recipeOutput, ItemLike quiver, Ingredient material) {
        quiver(recipeOutput, quiver, Items.LEATHER, Items.STRING, material);
    }
    public static void quiver(RecipeOutput recipeOutput, ItemLike quiver, ItemLike leather, ItemLike string, Ingredient material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, quiver, 1)
                .define('l', leather)
                .define('s', string)
                .define('m', material)
                .pattern("sl ")
                .pattern("l l")
                .pattern("ml ")
                .unlockedBy(getHasName(leather), has(leather))
                .unlockedBy(getHasName(string), has(string))
                .unlockedBy("has_material", inventoryTrigger(ItemPredicate.Builder.item().of(Arrays.stream(material.getItems()).map(ItemStack::getItem).toArray(ItemLike[]::new))))
                .save(recipeOutput.withConditions(new ECConfigBooleanCondition("quiver")));
    }
}
