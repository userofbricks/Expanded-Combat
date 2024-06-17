package com.userofbricks.expanded_combat.compatability.jei.recipes;

import com.userofbricks.expanded_combat.data.material.Material;
import com.userofbricks.expanded_combat.data.material.PlacementInShield;
import com.userofbricks.expanded_combat.init.ECItems;
import com.userofbricks.expanded_combat.init.Registries;
import com.userofbricks.expanded_combat.item.ECShieldItem;
import com.userofbricks.expanded_combat.init.PluginInit;
import mezz.jei.api.constants.ModIds;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Holder;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.*;
import java.util.stream.StreamSupport;

public class ECShieldDecorationRecipeMaker {
    public static List<RecipeHolder<CraftingRecipe>> createRecipes() {
        Iterable<Holder<Item>> banners = BuiltInRegistries.ITEM.getTagOrEmpty(ItemTags.BANNERS);

        Set<DyeColor> colors = EnumSet.noneOf(DyeColor.class);

        List<RecipeHolder<CraftingRecipe>> craftingRecipes = new ArrayList<>();
        for (Holder.Reference<Material> shieldMaterial : Objects.requireNonNull(Minecraft.getInstance().getConnection())
                        .registryAccess().registryOrThrow(Registries.MATERIAL_REGISTRY_KEY).holders()
                        .filter(materialReference -> materialReference.value().defense().placementInShield() != PlacementInShield.NONE).toList()) {

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

    private static RecipeHolder<CraftingRecipe> createRecipe(BannerItem banner, Holder.Reference<Material> shieldMaterial) {
        ItemStack shieldStack = ECShieldItem.makeShieldBeMaterial(new ItemStack(shieldMaterial.value().defense().fireResistant() ? ECItems.SHIELD_FIRE_RESISTANT.get() : ECItems.SHIELD.get()), shieldMaterial);
        NonNullList<Ingredient> inputs = NonNullList.of(
                Ingredient.EMPTY,
                Ingredient.of(shieldStack),
                Ingredient.of(banner)
        );

        ItemStack output = createOutput(banner, shieldStack.copy());

        ResourceLocation id = new ResourceLocation(ModIds.MINECRAFT_ID, "jei.ec_shield.decoration." + output.getDescriptionId() + "_" + shieldMaterial.getRegisteredName().replace(':', '_'));
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