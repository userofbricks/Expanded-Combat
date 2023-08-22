package com.userofbricks.expanded_combat.item.recipes;

import com.userofbricks.expanded_combat.ExpandedCombat;
import com.userofbricks.expanded_combat.item.recipes.conditions.ECConfigBooleanCondition;
import com.userofbricks.expanded_combat.item.recipes.conditions.ECConfigBowRecipeTypeCondition;
import com.userofbricks.expanded_combat.item.recipes.conditions.ECMaterialBooleanCondition;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import net.minecraftforge.registries.RegistryObject;

public class ECRecipeSerializerInit {
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, ExpandedCombat.MODID);
    public static final RegistryObject<RecipeType<IFletchingRecipe>> FLETCHING_TYPE = RECIPE_TYPES.register(IFletchingRecipe.FLETCHING_RECIPE_ID.getPath() , () -> ECRecipeSerializerInit.register(IFletchingRecipe.FLETCHING_RECIPE_ID.toString()));
    public static final RegistryObject<RecipeType<IShieldSmithingRecipe>> SHIELD_TYPE = RECIPE_TYPES.register(ShieldSmithingRecipie.SHIELD_RECIPE_ID.getPath(), () -> ECRecipeSerializerInit.register(ShieldSmithingRecipie.SHIELD_RECIPE_ID.toString()));

    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, ExpandedCombat.MODID);
    public static final RegistryObject<RecipeSerializer<?>> EC_TIPPED_ARROW_SERIALIZER = RECIPE_SERIALIZERS.register("crafting_ec_tipped_arrow", () -> new SimpleCraftingRecipeSerializer<>(ECTippedArrowRecipe::new));
    public static final RegistryObject<RecipeSerializer<?>> EC_POTION_WEAPON_SERIALIZER = RECIPE_SERIALIZERS.register("crafting_ec_potion_weapon", () -> new SimpleCraftingRecipeSerializer<>(PotionDippedWeaponRecipe::new));
    public static final RegistryObject<RecipeSerializer<?>> EC_FLETCHING_SERIALIZER = RECIPE_SERIALIZERS.register("ec_fletching", FletchingRecipe.Serializer::new);
    public static final RegistryObject<RecipeSerializer<?>> EC_TIPPED_ARROW_FLETCHING_SERIALIZER = RECIPE_SERIALIZERS.register("crafting_ec_tipped_fletching", () -> new SpecialFletchingRecipe.SpecialFletchingRecipeSerializer<>(TippedArrowFletchingRecipe::new));

    public static final RegistryObject<RecipeSerializer<?>> EC_STANDARD_SHIELD_SERIALIZER = RECIPE_SERIALIZERS.register("standard_shield", StanderStyleShieldSmithingRecipe.Serializer::new);
    public static final RegistryObject<RecipeSerializer<?>> EC_SHIELD_SERIALIZER = RECIPE_SERIALIZERS.register("smithing_shields", ShieldSmithingRecipie.Serializer::new);
    public static final RegistryObject<RecipeSerializer<?>> EC_UPGRADING_SHIELD_SERIALIZER = RECIPE_SERIALIZERS.register("upgrading_shields", ShieldUpgradeRecipe.Serializer::new);
    public static final RegistryObject<RecipeSerializer<?>> EC_SMITHING_UPGRADING_SHIELD_SERIALIZER = RECIPE_SERIALIZERS.register("shield_smithing_upgrade", ShieldSmithingUpgradeRecipe.Serializer::new);

    public static final RegistryObject<RecipeSerializer<?>> EC_SHIELD_DECORATION = RECIPE_SERIALIZERS.register("ec_shield_decoration", () -> new SimpleCraftingRecipeSerializer<>(ECShieldDecorationRecipe::new));



    private static <T extends Recipe<?>> RecipeType<T> register(final String recourceLocation) {
        return new RecipeType<>() {
            public String toString() {
                return recourceLocation;
            }
        };
    }

    public static void registerConditions(RegisterEvent event) {
        if (event.getRegistryKey().equals(ForgeRegistries.Keys.RECIPE_SERIALIZERS)) {
            CraftingHelper.register(ECConfigBooleanCondition.Serializer.INSTANCE);
            CraftingHelper.register(ECConfigBowRecipeTypeCondition.Serializer.INSTANCE);
            CraftingHelper.register(ECMaterialBooleanCondition.Serializer.INSTANCE);
        }
    }
}
