package com.userofbricks.expanded_combat.item.recipes;

import com.userofbricks.expanded_combat.ExpandedCombat;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ECRecipeSerializerInit {
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, ExpandedCombat.MODID);
    //public static final RecipeType<IFletchingRecipe> FLETCHING_TYPE = register(IFletchingRecipe.FLETCHING_RECIPE_ID.toString());
    public static final RegistryObject<RecipeType<ShieldSmithingRecipie>> SHIELD_TYPE = RECIPE_TYPES.register(ShieldSmithingRecipie.SHIELD_RECIPE_ID.getPath(), () -> ECRecipeSerializerInit.register(ShieldSmithingRecipie.SHIELD_RECIPE_ID.toString()));

    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, ExpandedCombat.MODID);
    //public static final RegistryObject<RecipeSerializer<?>> EC_TIPPED_ARROW_SERIALIZER = RECIPE_SERIALIZERS.register("crafting_ec_tipped_arrow", () -> new SimpleRecipeSerializer<>(ECTippedArrowRecipe::new));
    //public static final RegistryObject<RecipeSerializer<?>> EC_POTION_WEAPON_SERIALIZER = RECIPE_SERIALIZERS.register("crafting_ec_potion_weapon", () -> new SimpleRecipeSerializer<>(ECScytheRecipe::new));
    //public static final RegistryObject<RecipeSerializer<?>> EC_FLETCHING_SERIALIZER = RECIPE_SERIALIZERS.register("ec_fletching", FletchingRecipe.Serializer::new);
    //public static final RegistryObject<RecipeSerializer<?>> EC_SINGLE_FLETCHING_SERIALIZER = RECIPE_SERIALIZERS.register("ec_fletching_single", SingleFletchingRecipe.Serializer::new);
    //public static final RegistryObject<RecipeSerializer<?>> EC_SPECIAL_FLETCHING_SERIALIZER = RECIPE_SERIALIZERS.register("crafting_ec_tipped_fletching", () -> new SpecialFletchingRecipe.SpecialFletchingRecipeSerializer<>(TippedArrowFletchingRecipe::new));
    public static final RegistryObject<RecipeSerializer<?>> EC_SHIELD_SERIALIZER = RECIPE_SERIALIZERS.register("smithing_shields", ShieldSmithingRecipie.Serializer::new);
    //public static final RegistryObject<RecipeSerializer<?>> EC_UPGRADING_SHIELD_SERIALIZER = RECIPE_SERIALIZERS.register("upgrading_shields", UpgradingShieldSmithingRecipie.Serializer::new);


    private static <T extends Recipe<?>> RecipeType<T> register(final String recourceLocation) {
        return new RecipeType<T>() {
            public String toString() {
                return recourceLocation;
            }
        };
    }
}