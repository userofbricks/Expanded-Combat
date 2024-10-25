package com.userofbricks.expanded_combat.compatability.jei.recipes;

import com.userofbricks.expanded_combat.api.material.Material;
import com.userofbricks.expanded_combat.api.material.PlacementInShield;
import com.userofbricks.expanded_combat.init.ECItems;
import com.userofbricks.expanded_combat.init.PluginInit;
import com.userofbricks.expanded_combat.item.ECShieldItem;
import net.minecraft.core.Holder;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.stream.StreamSupport;

import static com.userofbricks.expanded_combat.ExpandedCombat.modLoc;

public class ECShieldDecorationRecipeMaker {
    public static List<RecipeHolder<CraftingRecipe>> createRecipes() {
        Iterable<Holder<Item>> banners = BuiltInRegistries.ITEM.getTagOrEmpty(ItemTags.BANNERS);

        Set<DyeColor> colors = EnumSet.noneOf(DyeColor.class);

        List<RecipeHolder<CraftingRecipe>> craftingRecipes = new ArrayList<>();
        for (Material shieldMaterial : PluginInit.materials.values().stream()
                        .filter(materialReference -> materialReference.defense().placementInShield() != PlacementInShield.NONE).toList()) {

            List<RecipeHolder<CraftingRecipe>> craftingRecipesForShield = StreamSupport.stream(banners.spliterator(), false)
                    .filter(Holder::isBound)
                    .map(Holder::value)
                    .filter(BannerItem.class::isInstance)
                    .map(BannerItem.class::cast)
                    .filter(item -> colors.add(item.getColor()))
                    .map((banner) -> createRecipe(banner, shieldMaterial))
                    .toList();
            craftingRecipes.addAll(craftingRecipesForShield);
        }
        return craftingRecipes;
    }

    private static RecipeHolder<CraftingRecipe> createRecipe(BannerItem banner, Material shieldMaterial) {
        ItemStack shieldStack = ECShieldItem.makeShieldBeMaterial(new ItemStack(shieldMaterial.defense().fireResistant() ? ECItems.SHIELD_FIRE_RESISTANT.get() : ECItems.SHIELD.get()), shieldMaterial);
        NonNullList<Ingredient> inputs = NonNullList.of(
                Ingredient.EMPTY,
                Ingredient.of(shieldStack),
                Ingredient.of(banner)
        );

        ItemStack output = createOutput(banner, shieldStack.copy());

        ResourceLocation id = modLoc("jei.ec_shield.decoration." + output.getDescriptionId() + "_" + shieldMaterial.id().toString().replace(':', '_'));
        CraftingRecipe recipe = new ShapelessRecipe("jei.ec_shield.decoration", CraftingBookCategory.MISC, output, inputs);
        return new RecipeHolder<>(id, recipe);
    }

    private static ItemStack createOutput(BannerItem banner, ItemStack output) {
        DyeColor color = banner.getColor();
        CompoundTag tag = new CompoundTag();
        tag.putInt("Base", color.getId());
        BlockItem.setBlockEntityData(output, BlockEntityType.BANNER, tag);
        return output;
    }
}