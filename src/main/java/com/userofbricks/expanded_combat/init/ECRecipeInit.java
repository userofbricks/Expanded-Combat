package com.userofbricks.expanded_combat.init;

import com.mojang.serialization.MapCodec;
import com.userofbricks.expanded_combat.item.recipes.*;
import com.userofbricks.expanded_combat.item.recipes.conditions.ECConfigBooleanCondition;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.*;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import static com.userofbricks.expanded_combat.ExpandedCombat.MODID;

public class ECRecipeInit {
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(BuiltInRegistries.RECIPE_TYPE, MODID);
    public static final DeferredHolder<RecipeType<?>, RecipeType<IFletchingRecipe>> FLETCHING_TYPE = RECIPE_TYPES.register(IFletchingRecipe.FLETCHING_RECIPE_ID.getPath() , () -> ECRecipeInit.register(IFletchingRecipe.FLETCHING_RECIPE_ID.toString()));
    public static final DeferredHolder<RecipeType<?>, RecipeType<IShieldSmithingRecipe>> SHIELD_TYPE = RECIPE_TYPES.register(ShieldSmithingRecipie.SHIELD_RECIPE_ID.getPath(), () -> ECRecipeInit.register(ShieldSmithingRecipie.SHIELD_RECIPE_ID.toString()));

    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(BuiltInRegistries.RECIPE_SERIALIZER, MODID);
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<ECTippedArrowRecipe>> EC_TIPPED_ARROW_SERIALIZER = RECIPE_SERIALIZERS.register("crafting_ec_tipped_arrow", ECTippedArrowRecipe.Serializer::new);
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<PotionDippedWeaponRecipe>> EC_POTION_WEAPON_SERIALIZER = RECIPE_SERIALIZERS.register("crafting_ec_potion_weapon", () -> new SimpleCraftingRecipeSerializer<>(PotionDippedWeaponRecipe::new));
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<? extends IFletchingRecipe>> EC_FLETCHING_SERIALIZER = RECIPE_SERIALIZERS.register("ec_fletching", FletchingRecipe.Serializer::new);
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<? extends SpecialFletchingRecipe>> EC_TIPPED_ARROW_FLETCHING_SERIALIZER = RECIPE_SERIALIZERS.register("crafting_ec_tipped_fletching", TippedArrowFletchingRecipe.Serializer::new);

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<StandardStyleShieldSmithingRecipe>> EC_STANDARD_SHIELD_SERIALIZER = RECIPE_SERIALIZERS.register("standard_shield", StandardStyleShieldSmithingRecipe.Serializer::new);
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<ShieldSmithingRecipie>> EC_SHIELD_SERIALIZER = RECIPE_SERIALIZERS.register("smithing_shields", () -> new NoEncodingRecipeSerializer<>(ShieldSmithingRecipie::new));
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<ShieldUpgradeRecipe>> EC_UPGRADING_SHIELD_SERIALIZER = RECIPE_SERIALIZERS.register("upgrading_shields", () -> new NoEncodingRecipeSerializer<>(ShieldUpgradeRecipe::new));
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<ShieldSmithingUpgradeRecipe>> EC_SMITHING_UPGRADING_SHIELD_SERIALIZER = RECIPE_SERIALIZERS.register("shield_smithing_upgrade", () -> new NoEncodingRecipeSerializer<>(ShieldSmithingUpgradeRecipe::new));

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<ECShieldDecorationRecipe>> EC_SHIELD_DECORATION = RECIPE_SERIALIZERS.register("ec_shield_decoration", () -> new SimpleCraftingRecipeSerializer<>(ECShieldDecorationRecipe::new));




    public static final DeferredRegister<MapCodec<? extends ICondition>> CONDITION_CODECS = DeferredRegister.create(NeoForgeRegistries.Keys.CONDITION_CODECS, MODID);
    public static final DeferredHolder<MapCodec<? extends ICondition>, MapCodec<ECConfigBooleanCondition>> EC_CONFIG_BOOLEAN_CONDITION = CONDITION_CODECS.register("config_boolean", () -> ECConfigBooleanCondition.CODEC);

    public static final DeferredRegister<RecipeBookCategory> RECIPE_BOOK_CATEGORIES = DeferredRegister.create(BuiltInRegistries.RECIPE_BOOK_CATEGORY, MODID);
    public static final DeferredHolder<RecipeBookCategory, RecipeBookCategory> FLETCHING = RECIPE_BOOK_CATEGORIES.register("fletching", RecipeBookCategory::new);
    public static final DeferredHolder<RecipeBookCategory, RecipeBookCategory> SHIELD_SMITHING = RECIPE_BOOK_CATEGORIES.register("shield_smithing", RecipeBookCategory::new);



    private static <T extends Recipe<?>> RecipeType<T> register(final String recourceLocation) {
        return new RecipeType<>() {
            public String toString() {
                return recourceLocation;
            }
        };
    }
}
