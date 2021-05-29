package com.userofbricks.expandedcombat.item.recipes;

import com.google.common.collect.Maps;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.tags.ITag;
import net.minecraft.tags.TagCollectionManager;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Map;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class VanillaRecipies {
    public static class ECShapedRecipe extends net.minecraft.item.crafting.ShapedRecipe {

        public ECShapedRecipe(ResourceLocation id, String group, int width, int height, NonNullList<Ingredient> ingredients, ItemStack result) {
            super(id, group, width, height, ingredients, result);
        }

        public static class ECSerializer extends Serializer {
            @Override
            public ShapedRecipe fromJson(ResourceLocation p_199425_1_, JsonObject p_199425_2_) {
                return super.fromJson(p_199425_1_, p_199425_2_);
            }
        }
    }

    public static boolean hasReqs(JsonObject p_199803_0_) {
        if (p_199803_0_.has("item") && p_199803_0_.has("tag")) {
            throw new JsonParseException("A Requirement entry is either a tag or an item, not both");
        } else if (p_199803_0_.has("item")) {
            ResourceLocation resourcelocation1 = new ResourceLocation(JSONUtils.getAsString(p_199803_0_, "item"));
            return ForgeRegistries.ITEMS.containsKey(resourcelocation1);
        } else if (p_199803_0_.has("tag")) {
            ResourceLocation resourcelocation = new ResourceLocation(JSONUtils.getAsString(p_199803_0_, "tag"));
            ITag<Item> itag = TagCollectionManager.getInstance().getItems().getTag(resourcelocation);
            return itag != null;
        } else {
            throw new JsonParseException("A Requirement entry needs either a tag or an item");
        }
    }
    private void Thing () {
        JsonObject p_192408_0_ = new JsonObject();
        for (Map.Entry<String, JsonElement> entry : p_192408_0_.entrySet()) {
            if (entry.getKey().length() != 1) {
                throw new JsonSyntaxException("Invalid key entry: '" + (String) entry.getKey() + "' is an invalid symbol (must be 1 character only).");
            }

            if (" ".equals(entry.getKey())) {
                throw new JsonSyntaxException("Invalid key entry: ' ' is a reserved symbol.");
            }
            Map<String, Ingredient> map = Maps.newHashMap();
            map.put(entry.getKey(), Ingredient.fromJson(entry.getValue()));
        }
    }
}
