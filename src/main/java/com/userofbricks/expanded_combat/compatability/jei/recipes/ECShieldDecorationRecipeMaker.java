package com.userofbricks.expanded_combat.compatability.jei.recipes;

import com.userofbricks.expanded_combat.item.ECItems;
import com.userofbricks.expanded_combat.item.ECShieldItem;
import com.userofbricks.expanded_combat.item.materials.Material;
import com.userofbricks.expanded_combat.item.materials.MaterialInit;
import mezz.jei.api.constants.ModIds;
import mezz.jei.api.helpers.IStackHelper;
import net.minecraft.core.Holder;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.stream.StreamSupport;

public class ECShieldDecorationRecipeMaker {
    public static List<CraftingRecipe> createRecipes(IStackHelper stackHelper) {
        Iterable<Holder<Item>> banners = BuiltInRegistries.ITEM.getTagOrEmpty(ItemTags.BANNERS);

        Set<DyeColor> colors = EnumSet.noneOf(DyeColor.class);

        List<CraftingRecipe> craftingRecipes = new ArrayList<>();
        for (Material shieldMaterial : MaterialInit.shieldMaterials) {

            List<CraftingRecipe> craftingRecipesForShield = StreamSupport.stream(banners.spliterator(), false)
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

    private static CraftingRecipe createRecipe(BannerItem banner, Material shieldMaterial) {
        ItemStack shieldStack = ECShieldItem.makeShieldBeMaterial(new ItemStack(shieldMaterial.getConfig().fireResistant ? ECItems.SHIELD_TIER_3.get() : ECItems.SHIELD_TIER_1.get()), shieldMaterial);
        NonNullList<Ingredient> inputs = NonNullList.of(
                Ingredient.EMPTY,
                Ingredient.of(shieldStack),
                Ingredient.of(banner)
        );

        ItemStack output = createOutput(banner, shieldStack.copy());

        ResourceLocation id = new ResourceLocation(ModIds.MINECRAFT_ID, "jei.ec_shield.decoration." + output.getDescriptionId() + "_" + shieldMaterial.getLocationName());
        return new ShapelessRecipe(id, "jei.ec_shield.decoration", CraftingBookCategory.MISC, output, inputs);
    }

    private static ItemStack createOutput(BannerItem banner, ItemStack output) {
        DyeColor color = banner.getColor();
        CompoundTag tag = new CompoundTag();
        tag.putInt("Base", color.getId());
        BlockItem.setBlockEntityData(output, BlockEntityType.BANNER, tag);
        return output;
    }
}