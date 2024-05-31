package com.userofbricks.expanded_combat.init;

import com.mojang.serialization.MapCodec;
import com.userofbricks.expanded_combat.item.recipes.*;
import com.userofbricks.expanded_combat.item.recipes.conditions.ECConfigBooleanCondition;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import static com.userofbricks.expanded_combat.ExpandedCombat.MODID;

public class ECRecipeSerializerInit {
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(BuiltInRegistries.RECIPE_TYPE, MODID);
    public static final DeferredHolder<RecipeType<?>, RecipeType<IFletchingRecipe>> FLETCHING_TYPE = RECIPE_TYPES.register(IFletchingRecipe.FLETCHING_RECIPE_ID.getPath() , () -> ECRecipeSerializerInit.register(IFletchingRecipe.FLETCHING_RECIPE_ID.toString()));
    public static final DeferredHolder<RecipeType<?>, RecipeType<IShieldSmithingRecipe>> SHIELD_TYPE = RECIPE_TYPES.register(ShieldSmithingRecipie.SHIELD_RECIPE_ID.getPath(), () -> ECRecipeSerializerInit.register(ShieldSmithingRecipie.SHIELD_RECIPE_ID.toString()));

    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(BuiltInRegistries.RECIPE_SERIALIZER, MODID);
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<?>> EC_TIPPED_ARROW_SERIALIZER = RECIPE_SERIALIZERS.register("crafting_ec_tipped_arrow", () -> new SimpleCraftingRecipeSerializer<>(ECTippedArrowRecipe::new));
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<?>> EC_POTION_WEAPON_SERIALIZER = RECIPE_SERIALIZERS.register("crafting_ec_potion_weapon", () -> new SimpleCraftingRecipeSerializer<>(PotionDippedWeaponRecipe::new));
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<?>> EC_FLETCHING_SERIALIZER = RECIPE_SERIALIZERS.register("ec_fletching", FletchingRecipe.Serializer::new);
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<?>> EC_TIPPED_ARROW_FLETCHING_SERIALIZER = RECIPE_SERIALIZERS.register("crafting_ec_tipped_fletching", () -> new SpecialFletchingRecipe.SpecialFletchingRecipeSerializer<>(TippedArrowFletchingRecipe::new));

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<?>> EC_STANDARD_SHIELD_SERIALIZER = RECIPE_SERIALIZERS.register("standard_shield", StanderStyleShieldSmithingRecipe.Serializer::new);
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<?>> EC_SHIELD_SERIALIZER = RECIPE_SERIALIZERS.register("smithing_shields", ShieldSmithingRecipie.Serializer::new);
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<?>> EC_UPGRADING_SHIELD_SERIALIZER = RECIPE_SERIALIZERS.register("upgrading_shields", ShieldUpgradeRecipe.Serializer::new);
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<?>> EC_SMITHING_UPGRADING_SHIELD_SERIALIZER = RECIPE_SERIALIZERS.register("shield_smithing_upgrade", ShieldSmithingUpgradeRecipe.Serializer::new);

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<?>> EC_SHIELD_DECORATION = RECIPE_SERIALIZERS.register("ec_shield_decoration", () -> new SimpleCraftingRecipeSerializer<>(ECShieldDecorationRecipe::new));


    //vanilla variants
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<?>> SMITHING_TRANSFORM_WITHOUT_TEMPLATE = RECIPE_SERIALIZERS.register("smithing_transform_without_template", SmithingTransformWithoutTemplateRecipe.Serializer::new);




    public static final DeferredRegister<MapCodec<? extends ICondition>> CONDITION_CODECS = DeferredRegister.create(NeoForgeRegistries.Keys.CONDITION_CODECS, MODID);
    public static final DeferredHolder<MapCodec<? extends ICondition>, MapCodec<ECConfigBooleanCondition>> EC_CONFIG_BOOLEAN_CONDITION = CONDITION_CODECS.register("config_boolean", () -> ECConfigBooleanCondition.CODEC);



    private static <T extends Recipe<?>> RecipeType<T> register(final String recourceLocation) {
        return new RecipeType<>() {
            public String toString() {
                return recourceLocation;
            }
        };
    }
}
