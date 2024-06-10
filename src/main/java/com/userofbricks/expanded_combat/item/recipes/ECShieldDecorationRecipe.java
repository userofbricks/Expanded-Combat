package com.userofbricks.expanded_combat.item.recipes;

import com.userofbricks.expanded_combat.init.ECRecipeSerializerInit;
import com.userofbricks.expanded_combat.item.ECShieldItem;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.BannerItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BannerPatternLayers;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.jetbrains.annotations.NotNull;

public class ECShieldDecorationRecipe extends CustomRecipe {
    public ECShieldDecorationRecipe(CraftingBookCategory p_251065_) {
        super(p_251065_);
    }

    public boolean matches(CraftingContainer p_44308_, @NotNull Level p_44309_) {
        ItemStack itemstack = ItemStack.EMPTY;
        ItemStack itemstack1 = ItemStack.EMPTY;

        for(int i = 0; i < p_44308_.getContainerSize(); ++i) {
            ItemStack itemstack2 = p_44308_.getItem(i);
            if (!itemstack2.isEmpty()) {
                if (itemstack2.getItem() instanceof BannerItem) {
                    if (!itemstack1.isEmpty()) {
                        return false;
                    }

                    itemstack1 = itemstack2;
                } else {
                    if (!(itemstack2.getItem() instanceof ECShieldItem)) {
                        return false;
                    }

                    if (!itemstack.isEmpty()) {
                        return false;
                    }

                    BannerPatternLayers bannerpatternlayers = itemstack2.getOrDefault(DataComponents.BANNER_PATTERNS, BannerPatternLayers.EMPTY);
                    if (!bannerpatternlayers.layers().isEmpty()) {
                        return false;
                    }

                    itemstack = itemstack2;
                }
            }
        }

        return !itemstack.isEmpty() && !itemstack1.isEmpty();
    }

    public @NotNull ItemStack assemble(CraftingContainer p_44306_, @NotNull HolderLookup.Provider p_267112_) {
        ItemStack itemstack = ItemStack.EMPTY;
        ItemStack itemstack1 = ItemStack.EMPTY;

        for(int i = 0; i < p_44306_.getContainerSize(); ++i) {
            ItemStack itemstack2 = p_44306_.getItem(i);
            if (!itemstack2.isEmpty()) {
                if (itemstack2.getItem() instanceof BannerItem) {
                    itemstack = itemstack2;
                } else if (itemstack2.getItem() instanceof ECShieldItem) {
                    itemstack1 = itemstack2.copy();
                }
            }
        }

        if (!itemstack1.isEmpty()) {
            itemstack1.set(DataComponents.BANNER_PATTERNS, itemstack.get(DataComponents.BANNER_PATTERNS));
            itemstack1.set(DataComponents.BASE_COLOR, ((BannerItem)itemstack.getItem()).getColor());
        }
        return itemstack1;
    }

    public boolean canCraftInDimensions(int p_44298_, int p_44299_) {
        return p_44298_ * p_44299_ >= 2;
    }

    public @NotNull RecipeSerializer<?> getSerializer() {
        return ECRecipeSerializerInit.EC_SHIELD_DECORATION.get();
    }
}