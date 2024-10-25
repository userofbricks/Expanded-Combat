package com.userofbricks.expanded_combat.compatability.jei.recipes;

import com.userofbricks.expanded_combat.item.PotionWeaponItem;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.crafting.*;
import net.neoforged.neoforge.common.crafting.DataComponentIngredient;

import java.util.List;

import static com.userofbricks.expanded_combat.ExpandedCombat.modLoc;

public class ECPotionWeaponRecipeMaker {
    public static List<RecipeHolder<CraftingRecipe>> createRecipes(PotionWeaponItem weaponItem) {
        ItemStack weaponStack = new ItemStack(weaponItem);
        Ingredient weaponIngredient = Ingredient.of(weaponStack);
        String group = "jei.dipped." + weaponStack.getDescriptionId();

        return BuiltInRegistries.POTION.holders()
                .map(potion -> {
                    ItemStack input = PotionContents.createItemStack(Items.LINGERING_POTION, potion);
                    ItemStack output = PotionContents.createItemStack(weaponItem, potion);

                    Ingredient potionIngredient = DataComponentIngredient.of(false, input);
                    NonNullList<Ingredient> inputs = NonNullList.of(Ingredient.EMPTY,
                            weaponIngredient, potionIngredient
                    );
                    ResourceLocation id = modLoc("jei.dipped.weapon." + output.getItem().getDescriptionId() + "." + potion.getRegisteredName().replace(':', '.'));
                    CraftingRecipe recipe = new ShapelessRecipe(group, CraftingBookCategory.MISC, output, inputs);
                    return new RecipeHolder<>(id, recipe);
                })
                .toList();
    }
}
