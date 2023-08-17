package com.userofbricks.expanded_combat.compatability.jei.recipes;

import com.userofbricks.expanded_combat.ExpandedCombat;
import com.userofbricks.expanded_combat.item.materials.Material;
import com.userofbricks.expanded_combat.item.materials.MaterialInit;
import com.userofbricks.expanded_combat.item.recipes.FletchingRecipe;
import com.userofbricks.expanded_combat.item.recipes.IFletchingRecipe;
import mezz.jei.api.helpers.IStackHelper;
import mezz.jei.common.platform.IPlatformIngredientHelper;
import mezz.jei.common.platform.IPlatformRegistry;
import mezz.jei.common.platform.Services;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class ECFletchingTippedArrowRecipeMaker {

    public static List<IFletchingRecipe> createTippedArrowRecipes(IStackHelper stackHelper) {
        List<IFletchingRecipe> recipes = new ArrayList<>();
        for (Material material :
                MaterialInit.arrowMaterials) {
            recipes.addAll(createTippedRecipesFor(stackHelper, material.getArrowEntry().get(), material.getTippedArrowEntry().get()));
        }
        recipes.addAll(createTippedRecipesFor(stackHelper, Items.ARROW, Items.TIPPED_ARROW));
        return recipes;
    }
    public static List<IFletchingRecipe> createTippedRecipesFor(IStackHelper stackHelper, Item arrowItem, Item tippedArrow) {
        ItemStack arrowStack = new ItemStack(arrowItem);
        Ingredient arrowIngredient = Ingredient.of(arrowStack);

        IPlatformRegistry<Potion> potionRegistry = Services.PLATFORM.getRegistry(Registries.POTION);
        IPlatformIngredientHelper ingredientHelper = Services.PLATFORM.getIngredientHelper();
        return potionRegistry.getValues()
                .<IFletchingRecipe>map(potion -> {
                    ItemStack input = PotionUtils.setPotion(new ItemStack(Items.LINGERING_POTION), potion);
                    ItemStack output = PotionUtils.setPotion(new ItemStack(tippedArrow), potion);

                    Ingredient potionIngredient = ingredientHelper.createNbtIngredient(input, stackHelper);

                    ResourceLocation id = new ResourceLocation(ExpandedCombat.MODID, "jei.fletching.tipped.arrow." + output.getDescriptionId());
                    return new FletchingRecipe(id, arrowIngredient, potionIngredient, output, 64);
                })
                .toList();
    }
}
