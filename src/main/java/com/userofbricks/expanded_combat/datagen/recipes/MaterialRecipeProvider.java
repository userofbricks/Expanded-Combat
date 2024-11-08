package com.userofbricks.expanded_combat.datagen.recipes;

import com.google.common.collect.ImmutableMap;
import com.userofbricks.expanded_combat.init.ECItems;
import com.userofbricks.expanded_combat.item.recipes.builders.FletchingRecipeBuilder;
import com.userofbricks.expanded_combat.item.recipes.builders.TippedArrowRecipeBuilder;
import com.userofbricks.expanded_combat.item.recipes.conditions.ECConfigBooleanCondition;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.ItemSubPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentPredicate;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public abstract class MaterialRecipeProvider extends RecipeProvider {
    public MaterialRecipeProvider(HolderLookup.Provider provider, RecipeOutput pRegistries) {
        super(provider, pRegistries);
    }

    public void battleStaff(RecipeOutput recipeOutput, ItemLike staff, Ingredient material) {
        battleStaff(recipeOutput, staff, ECItems.LEATHER_STICK.get(), material);
    }
    public void battleStaff(RecipeOutput recipeOutput, ItemLike staff, ItemLike stick, Ingredient material) {
        shaped(RecipeCategory.COMBAT, staff)
                .define('i', material)
                .define('s', stick)
                .pattern("  i")
                .pattern(" s ")
                .pattern("i  ")
                .unlockedBy(getHasName(stick), has(stick))
                .unlockedBy("has_material", inventoryTrigger(new ItemPredicate(Optional.of(material.getValues()), MinMaxBounds.Ints.ANY, DataComponentPredicate.EMPTY, new HashMap<>())))
                .save(recipeOutput.withConditions(new ECConfigBooleanCondition("weapon")));
    }
    public void broadSword(RecipeOutput recipeOutput, ItemLike broadSword, ItemLike sword, Ingredient material) {
        shaped(RecipeCategory.COMBAT, broadSword)
                .define('i', material)
                .define('s', sword)
                .pattern(" i ")
                .pattern("isi")
                .unlockedBy(getHasName(sword), has(sword))
                .unlockedBy("has_material", inventoryTrigger(new ItemPredicate(Optional.of(material.getValues()), MinMaxBounds.Ints.ANY, DataComponentPredicate.EMPTY, new HashMap<>())))
                .save(recipeOutput.withConditions(new ECConfigBooleanCondition("weapon")));
    }
    public void claymore(RecipeOutput recipeOutput, ItemLike claymore, ItemLike sword, Ingredient material) {
        shaped(RecipeCategory.COMBAT, claymore)
                .define('i', material)
                .define('s', sword)
                .pattern("i")
                .pattern("i")
                .pattern("s")
                .unlockedBy(getHasName(sword), has(sword))
                .unlockedBy("has_material", inventoryTrigger(new ItemPredicate(Optional.of(material.getValues()), MinMaxBounds.Ints.ANY, DataComponentPredicate.EMPTY, new HashMap<>())))
                .save(recipeOutput.withConditions(new ECConfigBooleanCondition("weapon")));
    }
    public void cutlass(RecipeOutput recipeOutput, ItemLike cutlass, Ingredient material) {
        cutlass(recipeOutput, cutlass, ECItems.GOLD_STICK.get(), material);
    }
    public void cutlass(RecipeOutput recipeOutput, ItemLike cutlass, ItemLike stick, Ingredient material) {
        shaped(RecipeCategory.COMBAT, cutlass)
                .define('i', material)
                .define('s', stick)
                .pattern("i")
                .pattern("i")
                .pattern("s")
                .unlockedBy(getHasName(stick), has(stick))
                .unlockedBy("has_material", inventoryTrigger(new ItemPredicate(Optional.of(material.getValues()), MinMaxBounds.Ints.ANY, DataComponentPredicate.EMPTY, new HashMap<>())))
                .save(recipeOutput.withConditions(new ECConfigBooleanCondition("weapon")));
    }
    public void dagger(RecipeOutput recipeOutput, ItemLike dagger, Ingredient material) {
        dagger(recipeOutput, dagger, ECItems.IRON_STICK.get(), material);
    }
    public void dagger(RecipeOutput recipeOutput, ItemLike dagger, ItemLike stick, Ingredient material) {
        shaped(RecipeCategory.COMBAT, dagger)
                .define('i', material)
                .define('s', stick)
                .pattern("i")
                .pattern("s")
                .unlockedBy(getHasName(stick), has(stick))
                .unlockedBy("has_material", inventoryTrigger(new ItemPredicate(Optional.of(material.getValues()), MinMaxBounds.Ints.ANY, DataComponentPredicate.EMPTY, new HashMap<>())))
                .save(recipeOutput.withConditions(new ECConfigBooleanCondition("weapon")));
    }
    public void dancersSword(RecipeOutput recipeOutput, ItemLike dancersSword, ItemLike sword) {
        dancersSword(recipeOutput, dancersSword, sword, ECItems.IRON_STICK.get());
    }
    public void dancersSword(RecipeOutput recipeOutput, ItemLike dancersSword, ItemLike sword, ItemLike stick) {
        shaped(RecipeCategory.COMBAT, dancersSword)
                .define('p', sword)
                .define('s', stick)
                .pattern("  p")
                .pattern(" s ")
                .pattern("p  ")
                .unlockedBy(getHasName(sword), has(sword))
                .unlockedBy(getHasName(stick), has(stick))
                .save(recipeOutput.withConditions(new ECConfigBooleanCondition("weapon")));
    }
    public void flail(RecipeOutput recipeOutput, ItemLike flail, ItemLike block) {
        flail(recipeOutput, flail, block, Items.CHAIN, Items.STICK);
    }
    public void flail(RecipeOutput recipeOutput, ItemLike flail, ItemLike block, ItemLike chain, ItemLike stick) {
        shaped(RecipeCategory.COMBAT, flail)
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
    public void glaive(RecipeOutput recipeOutput, ItemLike glaive, ItemLike sword) {
        glaive(recipeOutput, glaive, sword, Items.STICK);
    }
    public void glaive(RecipeOutput recipeOutput, ItemLike glaive, ItemLike sword, ItemLike stick) {
        shaped(RecipeCategory.COMBAT, glaive)
                .define('p', sword)
                .define('s', stick)
                .pattern("  p")
                .pattern(" s ")
                .pattern("s  ")
                .unlockedBy(getHasName(sword), has(sword))
                .unlockedBy(getHasName(stick), has(stick))
                .save(recipeOutput.withConditions(new ECConfigBooleanCondition("weapon")));
    }
    public void greatHammer(RecipeOutput recipeOutput, ItemLike greatHammer, ItemLike block) {
        greatHammer(recipeOutput, greatHammer, block, Items.STICK);
    }
    public void greatHammer(RecipeOutput recipeOutput, ItemLike greatHammer, ItemLike block, ItemLike stick) {
        shaped(RecipeCategory.COMBAT, greatHammer)
                .define('b', block)
                .define('s', stick)
                .pattern("  b")
                .pattern(" s ")
                .pattern("s  ")
                .unlockedBy(getHasName(block), has(block))
                .unlockedBy(getHasName(stick), has(stick))
                .save(recipeOutput.withConditions(new ECConfigBooleanCondition("weapon")));
    }
    public void katana(RecipeOutput recipeOutput, ItemLike katana, ItemLike sword, Ingredient material) {
        shaped(RecipeCategory.COMBAT, katana)
                .define('s', sword)
                .define('i', material)
                .pattern("i")
                .pattern("s")
                .unlockedBy(getHasName(sword), has(sword))
                .unlockedBy("has_material", inventoryTrigger(new ItemPredicate(Optional.of(material.getValues()), MinMaxBounds.Ints.ANY, DataComponentPredicate.EMPTY, new HashMap<>())))
                .save(recipeOutput.withConditions(new ECConfigBooleanCondition("weapon")));
    }
    public void mace(RecipeOutput recipeOutput, ItemLike mace, ItemLike block) {
        mace(recipeOutput, mace, block, Items.STICK);
    }
    public void mace(RecipeOutput recipeOutput, ItemLike mace, ItemLike block, ItemLike stick) {
        shaped(RecipeCategory.COMBAT, mace)
                .define('b', block)
                .define('s', stick)
                .pattern(" b")
                .pattern("s ")
                .unlockedBy(getHasName(block), has(block))
                .unlockedBy(getHasName(stick), has(stick))
                .save(recipeOutput.withConditions(new ECConfigBooleanCondition("weapon")));
    }
    public void scythe(RecipeOutput recipeOutput, ItemLike scythe, ItemLike sword, Ingredient material) {
        scythe(recipeOutput, scythe, sword, material, Items.STICK);
    }
    public void scythe(RecipeOutput recipeOutput, ItemLike scythe, ItemLike sword, Ingredient material, ItemLike stick) {
        shaped(RecipeCategory.COMBAT, scythe)
                .define('p', sword)
                .define('i', material)
                .define('s', stick)
                .pattern("ip ")
                .pattern("  s")
                .pattern("  s")
                .unlockedBy(getHasName(sword), has(sword))
                .unlockedBy("has_material", inventoryTrigger(new ItemPredicate(Optional.of(material.getValues()), MinMaxBounds.Ints.ANY, DataComponentPredicate.EMPTY, new HashMap<>())))
                .unlockedBy(getHasName(stick), has(stick))
                .save(recipeOutput.withConditions(new ECConfigBooleanCondition("weapon")));
    }
    public void sickle(RecipeOutput recipeOutput, ItemLike sickle, Ingredient material) {
        sickle(recipeOutput, sickle, material, Items.STICK);
    }
    public void sickle(RecipeOutput recipeOutput, ItemLike sickle, Ingredient material, ItemLike stick) {
        shaped(RecipeCategory.COMBAT, sickle)
                .define('i', material)
                .define('s', stick)
                .pattern("ii")
                .pattern("s ")
                .unlockedBy("has_material", inventoryTrigger(new ItemPredicate(Optional.of(material.getValues()), MinMaxBounds.Ints.ANY, DataComponentPredicate.EMPTY, new HashMap<>())))
                .unlockedBy(getHasName(stick), has(stick))
                .save(recipeOutput.withConditions(new ECConfigBooleanCondition("weapon")));
    }
    public void spear(RecipeOutput recipeOutput, ItemLike spear, ItemLike sword) {
        spear(recipeOutput, spear, sword, Items.STICK);
    }
    public void spear(RecipeOutput recipeOutput, ItemLike spear, ItemLike sword, ItemLike stick) {
        shaped(RecipeCategory.COMBAT, spear)
                .define('p', sword)
                .define('s', stick)
                .pattern("p")
                .pattern("s")
                .pattern("s")
                .unlockedBy(getHasName(sword), has(sword))
                .unlockedBy(getHasName(stick), has(stick))
                .save(recipeOutput.withConditions(new ECConfigBooleanCondition("weapon")));
    }


    public void arrow(RecipeOutput recipeOutput, ItemLike arrow, ItemLike tip) {
        arrow(recipeOutput, arrow, 4, tip);
    }
    public void arrow(RecipeOutput recipeOutput, ItemLike arrow, int amount, ItemLike tip) {
        arrow(recipeOutput, arrow, amount, tip, Items.STICK, Items.FEATHER);
    }
    public void arrow(RecipeOutput recipeOutput, ItemLike arrow, ItemLike tip, ItemLike stick, ItemLike feather) {
        arrow(recipeOutput, arrow, 4, tip, stick, feather);
    }
    public void arrow(RecipeOutput recipeOutput, ItemLike arrow, int amount, ItemLike tip, ItemLike stick, ItemLike feather) {
        shaped(RecipeCategory.COMBAT, arrow, amount)
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

    public void tippedArrow(RecipeOutput recipeOutput, ItemLike tipped_arrow, ItemLike arrow) {
        new TippedArrowRecipeBuilder(new ItemStack(tipped_arrow), Ingredient.of(arrow))
                .save(recipeOutput.withConditions(new ECConfigBooleanCondition("arrow")));
    }

    public void fletching(RecipeOutput recipeOutput, ItemLike result, ItemLike addition, ItemLike previosItem, int resultCount) {
        fletching(recipeOutput, result, Ingredient.of(addition), previosItem, resultCount);
    }

    public void fletching(RecipeOutput recipeOutput, ItemLike result, Ingredient addition, ItemLike previosItem, int resultCount) {

        FletchingRecipeBuilder.fletching(Ingredient.of(previosItem), addition, RecipeCategory.COMBAT, result.asItem(), resultCount)
                .unlockedBy("has_item", has(previosItem))
                .unlockedBy("has_material", inventoryTrigger(new ItemPredicate(Optional.of(addition.getValues()), MinMaxBounds.Ints.ANY, DataComponentPredicate.EMPTY, new HashMap<>())))
                .save(recipeOutput.withConditions(new ECConfigBooleanCondition("arrow")));
    }

    public void variableFletching(RecipeOutput recipeOutput, ItemLike result, ItemLike addition, ItemLike previosItem, int maxResultCount) {

        FletchingRecipeBuilder.fletchingVarableResult(Ingredient.of(previosItem), Ingredient.of(addition), RecipeCategory.COMBAT, result.asItem(), maxResultCount)
                .unlockedBy("has_item", has(previosItem))
                .unlockedBy("has_material", has(addition))
                .save(recipeOutput.withConditions(new ECConfigBooleanCondition("arrow")),
                        RecipeBuilder.getDefaultRecipeId(result).withSuffix("_variable_fletching"));
    }

    public void bow(RecipeOutput recipeOutput, ItemLike bow, Ingredient material) {
        bow(recipeOutput, bow, Items.STRING, ECItems.IRON_STICK.get(), material);
    }
    public void bow(RecipeOutput recipeOutput, ItemLike bow, ItemLike string, ItemLike stick, Ingredient material) {
        shaped(RecipeCategory.COMBAT, bow, 1)
                .define('s', string)
                .define('/', stick)
                .define('m', material)
                .pattern(" ms")
                .pattern("/ s")
                .pattern(" ms")
                .unlockedBy(getHasName(string), has(string))
                .unlockedBy(getHasName(stick), has(stick))
                .unlockedBy("has_material", inventoryTrigger(new ItemPredicate(Optional.of(material.getValues()), MinMaxBounds.Ints.ANY, DataComponentPredicate.EMPTY, new HashMap<>())))
                .save(recipeOutput.withConditions(new ECConfigBooleanCondition("bow")));
    }

    public void crossbow(RecipeOutput recipeOutput, ItemLike crossbow, Ingredient material) {
        crossbow(recipeOutput, crossbow, Items.CROSSBOW, material);
    }
    public void crossbow(RecipeOutput recipeOutput, ItemLike crossbow, ItemLike previous, Ingredient material) {
        shaped(RecipeCategory.COMBAT, crossbow, 1)
                .define('p', previous)
                .define('m', material)
                .pattern(" m ")
                .pattern("mpm")
                .unlockedBy(getHasName(previous), has(previous))
                .unlockedBy("has_material", inventoryTrigger(new ItemPredicate(Optional.of(material.getValues()), MinMaxBounds.Ints.ANY, DataComponentPredicate.EMPTY, new HashMap<>())))
                .save(recipeOutput.withConditions(new ECConfigBooleanCondition("crossbow")));
    }
    public void gauntlet(RecipeOutput recipeOutput, ItemLike gauntlet, Ingredient material) {
        shaped(RecipeCategory.COMBAT, gauntlet, 1)
                .define('b', material)
                .pattern("bb")
                .pattern("b ")
                .unlockedBy("has_material", inventoryTrigger(new ItemPredicate(Optional.of(material.getValues()), MinMaxBounds.Ints.ANY, DataComponentPredicate.EMPTY, new HashMap<>())))
                .save(recipeOutput.withConditions(new ECConfigBooleanCondition("gauntlet")));
    }

    public void quiver(RecipeOutput recipeOutput, ItemLike quiver, Ingredient material) {
        quiver(recipeOutput, quiver, Items.LEATHER, Items.STRING, material);
    }
    public void quiver(RecipeOutput recipeOutput, ItemLike quiver, ItemLike leather, ItemLike string, Ingredient material) {
        shaped(RecipeCategory.COMBAT, quiver, 1)
                .define('l', leather)
                .define('s', string)
                .define('m', material)
                .pattern("sl ")
                .pattern("l l")
                .pattern("ml ")
                .unlockedBy(getHasName(leather), has(leather))
                .unlockedBy(getHasName(string), has(string))
                .unlockedBy("has_material", inventoryTrigger(new ItemPredicate(Optional.of(material.getValues()), MinMaxBounds.Ints.ANY, DataComponentPredicate.EMPTY, new HashMap<>())))
                .save(recipeOutput.withConditions(new ECConfigBooleanCondition("quiver")));
    }
}
