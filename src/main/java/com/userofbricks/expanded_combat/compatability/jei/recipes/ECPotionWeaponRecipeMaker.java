package com.userofbricks.expanded_combat.compatability.jei.recipes;

import com.userofbricks.expanded_combat.ExpandedCombat;
import com.userofbricks.expanded_combat.item.ECWeaponItem;
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
import net.minecraft.world.item.crafting.ShapelessRecipe;

import java.util.List;

public class ECPotionWeaponRecipeMaker {
    public static List<CraftingRecipe> createRecipes(IStackHelper stackHelper, ECWeaponItem.HasPotion weaponItem) {
        String group = "jei.dipped.weapon";
        ItemStack weaponStack = new ItemStack(weaponItem);
        Ingredient weaponIngredient = Ingredient.of(weaponStack);

        IPlatformRegistry<Potion> potionRegistry = Services.PLATFORM.getRegistry(Registries.POTION);
        IPlatformIngredientHelper ingredientHelper = Services.PLATFORM.getIngredientHelper();
        return potionRegistry.getValues()
                .<CraftingRecipe>map(potion -> {
                    ItemStack input = PotionUtils.setPotion(new ItemStack(Items.LINGERING_POTION), potion);
                    ItemStack output = PotionUtils.setPotion(new ItemStack(weaponItem), potion);

                    Ingredient potionIngredient = ingredientHelper.createNbtIngredient(input, stackHelper);
                    NonNullList<Ingredient> inputs = NonNullList.of(Ingredient.EMPTY,
                            weaponIngredient, potionIngredient
                    );
                    ResourceLocation id = new ResourceLocation(ExpandedCombat.MODID, "jei.dipped.weapon." + potion.getName(output.getItem().getDescriptionId()));
                    return new ShapelessRecipe(id, group, CraftingBookCategory.MISC, output, inputs);
                })
                .toList();
    }
}
