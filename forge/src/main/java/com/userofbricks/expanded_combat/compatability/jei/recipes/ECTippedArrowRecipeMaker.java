package com.userofbricks.expanded_combat.compatability.jei.recipes;

import com.userofbricks.expanded_combat.ExpandedCombat;
import com.userofbricks.expanded_combat.item.ECArrowItem;
import mezz.jei.api.helpers.IStackHelper;
import mezz.jei.common.platform.IPlatformIngredientHelper;
import mezz.jei.common.platform.IPlatformRegistry;
import mezz.jei.common.platform.Services;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShapedRecipe;

import java.util.List;

public class ECTippedArrowRecipeMaker {
    public static List<CraftingRecipe> createRecipes(IStackHelper stackHelper, ECArrowItem arrowItem) {
        String group = "jei.tipped.arrow";
        ItemStack arrowStack = new ItemStack(arrowItem);
        Ingredient arrowIngredient = Ingredient.of(arrowStack);

        IPlatformRegistry<Potion> potionRegistry = Services.PLATFORM.getRegistry(Registries.POTION);
        IPlatformIngredientHelper ingredientHelper = Services.PLATFORM.getIngredientHelper();
        return potionRegistry.getValues()
                .<CraftingRecipe>map(potion -> {
                    ItemStack input = PotionUtils.setPotion(new ItemStack(Items.LINGERING_POTION), potion);
                    ItemStack output = PotionUtils.setPotion(new ItemStack(arrowItem.getMaterial().getTippedArrowEntry().get(), 8), potion);

                    Ingredient potionIngredient = ingredientHelper.createNbtIngredient(input, stackHelper);
                    NonNullList<Ingredient> inputs = NonNullList.of(Ingredient.EMPTY,
                            arrowIngredient, arrowIngredient, arrowIngredient,
                            arrowIngredient, potionIngredient, arrowIngredient,
                            arrowIngredient, arrowIngredient, arrowIngredient
                    );
                    ResourceLocation id = new ResourceLocation(ExpandedCombat.MODID, "jei.tipped.arrow." + output.getDescriptionId());
                    return new ShapedRecipe(id, group, CraftingBookCategory.MISC, 3, 3, inputs, output);
                })
                .toList();
    }
}
