package com.userofbricks.expandedcombat.item.recipes;

import net.minecraftforge.registries.ForgeRegistries;
import java.util.function.Function;
import net.minecraft.item.crafting.SpecialRecipeSerializer;
import net.minecraftforge.fml.RegistryObject;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;

public class RecipeSerializerInit
{
    public static final DeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, "expanded_combat");
    public static final RegistryObject<IRecipeSerializer<?>> EC_TIPPED_ARROW_SERIALIZER = RecipeSerializerInit.RECIPE_SERIALIZERS.register("crafting_ec_tipped_arrow", () -> new SpecialRecipeSerializer<>(ECTippedArrowRecipe::new));
    public static final RegistryObject<IRecipeSerializer<?>> EC_POTION_WEAPON_SERIALIZER = RecipeSerializerInit.RECIPE_SERIALIZERS.register("crafting_ec_potion_weapon", () -> new SpecialRecipeSerializer<>(ECScytheRecipe::new));
}
