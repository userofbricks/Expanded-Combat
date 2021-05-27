package com.userofbricks.expandedcombat.item.recipes;

import net.minecraft.item.crafting.*;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;
import java.util.function.Function;

import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

public class RecipeSerializerInit
{
    public static final IRecipeType<IFletchingRecipe> FLETCHING_TYPE = register(IFletchingRecipe.FLETCHING_RECIPE_ID.toString());

    public static final DeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, "expanded_combat");
    public static final RegistryObject<IRecipeSerializer<?>> EC_TIPPED_ARROW_SERIALIZER = RECIPE_SERIALIZERS.register("crafting_ec_tipped_arrow", () -> new SpecialRecipeSerializer<>(ECTippedArrowRecipe::new));
    public static final RegistryObject<IRecipeSerializer<?>> EC_POTION_WEAPON_SERIALIZER = RECIPE_SERIALIZERS.register("crafting_ec_potion_weapon", () -> new SpecialRecipeSerializer<>(ECScytheRecipe::new));
    public static final RegistryObject<IRecipeSerializer<?>> EC_FLETCHING_SERIALIZER = RECIPE_SERIALIZERS.register("ec_fletching", FletchingRecipe.Serializer::new);
    public static final RegistryObject<IRecipeSerializer<?>> EC_SPECIAL_FLETCHING_SERIALIZER = RECIPE_SERIALIZERS.register("crafting_ec_tipped_fletching", () -> new SpecialFletchingRecipe.SpecialFletchingRecipeSerializer<>(TippedArrowFletchingRecipe::new));


    static <T extends IRecipe<?>> IRecipeType<T> register(final String p_222147_0_) {
        return Registry.register(Registry.RECIPE_TYPE, new ResourceLocation(p_222147_0_), new IRecipeType<T>() {
            public String toString() {
                return p_222147_0_;
            }
        });
    }
}
