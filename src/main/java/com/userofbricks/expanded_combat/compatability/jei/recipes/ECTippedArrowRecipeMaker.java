package com.userofbricks.expanded_combat.compatability.jei.recipes;

import com.userofbricks.expanded_combat.ExpandedCombat;
import com.userofbricks.expanded_combat.item.ECTippedArrowItem;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.crafting.*;
import net.neoforged.neoforge.common.crafting.DataComponentIngredient;

import java.util.List;
import java.util.Optional;

public class ECTippedArrowRecipeMaker {
    public static List<RecipeHolder<CraftingRecipe>> createRecipes(ECTippedArrowItem arrowItem) {
        ItemStack arrowStack = new ItemStack(arrowItem.getNotTipped());
        Ingredient arrowIngredient = Ingredient.of(arrowStack);
        String group = "jei.tipped." + arrowStack.getDescriptionId();

        return BuiltInRegistries.POTION.holders()
                .map(potion -> {
                    ItemStack input = PotionContents.createItemStack(Items.LINGERING_POTION, potion);
                    ItemStack output = PotionContents.createItemStack(arrowItem, potion);
                    output.setCount(8);

                    Ingredient potionIngredient = DataComponentIngredient.of(false, input);
                    NonNullList<Ingredient> inputs = NonNullList.of(Ingredient.EMPTY,
                            arrowIngredient, arrowIngredient, arrowIngredient,
                            arrowIngredient, potionIngredient, arrowIngredient,
                            arrowIngredient, arrowIngredient, arrowIngredient
                    );
                    ResourceLocation id = new ResourceLocation(ExpandedCombat.MODID, "jei.tipped.arrow." + output.getDescriptionId() + "." + potion.getRegisteredName().replace(':', '.'));
                    ShapedRecipePattern pattern = new ShapedRecipePattern(3, 3, inputs, Optional.empty());
                    CraftingRecipe recipe = new ShapedRecipe(group, CraftingBookCategory.MISC, pattern, output);
                    return new RecipeHolder<>(id, recipe);
                })
                .toList();
    }
}
