package com.userofbricks.expanded_combat.compatability.jei.recipes;

import com.userofbricks.expanded_combat.ExpandedCombat;
import com.userofbricks.expanded_combat.item.ECTippedArrowItem;
import com.userofbricks.expanded_combat.item.recipes.FletchingRecipe;
import com.userofbricks.expanded_combat.item.recipes.IFletchingRecipe;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.crafting.*;
import net.neoforged.neoforge.common.crafting.DataComponentIngredient;

import java.util.ArrayList;
import java.util.List;

public class ECFletchingTippedArrowRecipeMaker {

    public static List<RecipeHolder<IFletchingRecipe>> createTippedArrowRecipes() {
        List<RecipeHolder<IFletchingRecipe>> recipes = new ArrayList<>();
        for (ECTippedArrowItem item :
                BuiltInRegistries.ITEM.stream().filter(item -> item instanceof ECTippedArrowItem).map(item -> (ECTippedArrowItem)item).toList()) {
            recipes.addAll(createTippedRecipesFor(item.getNotTipped(), item));
        }
        recipes.addAll(createTippedRecipesFor(Items.ARROW, Items.TIPPED_ARROW));
        return recipes;
    }
    public static List<RecipeHolder<IFletchingRecipe>> createTippedRecipesFor(Item arrowItem, Item tippedArrow) {
        ItemStack arrowStack = new ItemStack(arrowItem);
        Ingredient arrowIngredient = Ingredient.of(arrowStack);

        return BuiltInRegistries.POTION.holders()
                .map(potion -> {
                    ItemStack input = PotionContents.createItemStack(Items.LINGERING_POTION, potion);
                    ItemStack output = PotionContents.createItemStack(tippedArrow, potion);

                    Ingredient potionIngredient = DataComponentIngredient.of(false, input);

                    ResourceLocation id = new ResourceLocation(ExpandedCombat.MODID, "jei.fletching.tipped.arrow." + output.getDescriptionId() + "." + potion.getRegisteredName().replace(':', '.'));
                    IFletchingRecipe recipe = new FletchingRecipe(arrowIngredient, potionIngredient, output, 64);
                    return new RecipeHolder<>(id, recipe);
                })
                .toList();
    }
}
