package com.userofbricks.expanded_combat.datagen.recipes;

import com.userofbricks.expanded_combat.init.ECItems;
import com.userofbricks.expanded_combat.item.recipes.builders.FletchingRecipeBuilder;
import com.userofbricks.expanded_combat.item.recipes.conditions.ECConfigBooleanCondition;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.conditions.ICondition;

import java.util.concurrent.CompletableFuture;

public abstract class MaterialRecipeProvider extends RecipeProvider {
    public MaterialRecipeProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pRegistries) {
        super(pOutput, pRegistries);
    }

    public static void battleStaff(RecipeOutput recipeOutput, ItemLike staff, ItemLike material) {
        battleStaff(recipeOutput, staff, ECItems.LEATHER_STICK.get(), material);
    }
    public static void battleStaff(RecipeOutput recipeOutput, ItemLike staff, ItemLike stick, ItemLike material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, staff)
                .define('i', material)
                .define('s', stick)
                .pattern("  i")
                .pattern(" s ")
                .pattern("i  ")
                .unlockedBy(getHasName(stick), has(stick))
                .unlockedBy(getHasName(material), has(material))
                .save(recipeOutput.withConditions(new ECConfigBooleanCondition("weapon")));
    }
    public static void broadSword(RecipeOutput recipeOutput, ItemLike broadSword, ItemLike sword, ItemLike material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, broadSword)
                .define('i', material)
                .define('s', sword)
                .pattern(" i ")
                .pattern("isi")
                .unlockedBy(getHasName(sword), has(sword))
                .unlockedBy(getHasName(material), has(material))
                .save(recipeOutput.withConditions(new ECConfigBooleanCondition("weapon")));
    }
    public static void claymore(RecipeOutput recipeOutput, ItemLike claymore, ItemLike sword, ItemLike material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, claymore)
                .define('i', material)
                .define('s', sword)
                .pattern("i")
                .pattern("i")
                .pattern("s")
                .unlockedBy(getHasName(sword), has(sword))
                .unlockedBy(getHasName(material), has(material))
                .save(recipeOutput.withConditions(new ECConfigBooleanCondition("weapon")));
    }
    public static void cutlass(RecipeOutput recipeOutput, ItemLike cutlass, ItemLike material) {
        cutlass(recipeOutput, cutlass, ECItems.GOLD_STICK.get(), material);
    }
    public static void cutlass(RecipeOutput recipeOutput, ItemLike cutlass, ItemLike stick, ItemLike material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, cutlass)
                .define('i', material)
                .define('s', stick)
                .pattern("i")
                .pattern("i")
                .pattern("s")
                .unlockedBy(getHasName(stick), has(stick))
                .unlockedBy(getHasName(material), has(material))
                .save(recipeOutput.withConditions(new ECConfigBooleanCondition("weapon")));
    }
    public static void dagger(RecipeOutput recipeOutput, ItemLike dagger, ItemLike material) {
        dagger(recipeOutput, dagger, ECItems.IRON_STICK.get(), material);
    }
    public static void dagger(RecipeOutput recipeOutput, ItemLike dagger, ItemLike stick, ItemLike material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, dagger)
                .define('i', material)
                .define('s', stick)
                .pattern("i")
                .pattern("s")
                .unlockedBy(getHasName(stick), has(stick))
                .unlockedBy(getHasName(material), has(material))
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
    public static void katana(RecipeOutput recipeOutput, ItemLike katana, ItemLike sword, ItemLike material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, katana)
                .define('s', sword)
                .define('i', material)
                .pattern("i")
                .pattern("s")
                .unlockedBy(getHasName(sword), has(sword))
                .unlockedBy(getHasName(material), has(material))
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
    public static void scythe(RecipeOutput recipeOutput, ItemLike scythe, ItemLike sword, ItemLike material) {
        scythe(recipeOutput, scythe, sword, material, Items.STICK);
    }
    public static void scythe(RecipeOutput recipeOutput, ItemLike scythe, ItemLike sword, ItemLike material, ItemLike stick) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, scythe)
                .define('p', sword)
                .define('i', material)
                .define('s', stick)
                .pattern("ip ")
                .pattern("  s")
                .pattern("  s")
                .unlockedBy(getHasName(sword), has(sword))
                .unlockedBy(getHasName(material), has(material))
                .unlockedBy(getHasName(stick), has(stick))
                .save(recipeOutput.withConditions(new ECConfigBooleanCondition("weapon")));
    }
    public static void sickle(RecipeOutput recipeOutput, ItemLike sickle, ItemLike material) {
        sickle(recipeOutput, sickle, material, Items.STICK);
    }
    public static void sickle(RecipeOutput recipeOutput, ItemLike sickle, ItemLike material, ItemLike stick) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, sickle)
                .define('i', material)
                .define('s', stick)
                .pattern("ii")
                .pattern("s ")
                .unlockedBy(getHasName(material), has(material))
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

    public static void fletching(RecipeOutput recipeOutput, ItemLike result, ItemLike addition, ItemLike previosItem, int resultCount) {

        FletchingRecipeBuilder.fletching(Ingredient.of(previosItem), Ingredient.of(addition), RecipeCategory.COMBAT, result.asItem(), resultCount)
                .unlockedBy("has_item", has(previosItem))
                .unlockedBy("has_item", has(addition))
                .save(recipeOutput.withConditions(new ECConfigBooleanCondition("arrow")));
    }

    public static void variableFletching(RecipeOutput recipeOutput, ItemLike result, ItemLike addition, ItemLike previosItem, int maxResultCount) {

        FletchingRecipeBuilder.fletchingVarableResult(Ingredient.of(previosItem), Ingredient.of(addition), RecipeCategory.COMBAT, result.asItem(), maxResultCount)
                .unlockedBy("has_item", has(previosItem))
                .unlockedBy("has_item", has(addition))
                .save(recipeOutput.withConditions(new ECConfigBooleanCondition("arrow")),
                        RecipeBuilder.getDefaultRecipeId(result).withSuffix("_variable_fletching"));
    }
}
