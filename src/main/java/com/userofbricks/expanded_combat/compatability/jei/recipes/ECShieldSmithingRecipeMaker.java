package com.userofbricks.expanded_combat.compatability.jei.recipes;

import com.userofbricks.expanded_combat.api.material.Material;
import com.userofbricks.expanded_combat.api.material.PlacementInShield;
import com.userofbricks.expanded_combat.data_components.ShieldMaterials;
import com.userofbricks.expanded_combat.init.*;
import com.userofbricks.expanded_combat.item.recipes.IShieldSmithingRecipe;
import com.userofbricks.expanded_combat.item.recipes.StanderStyleShieldSmithingRecipe;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.*;

import static com.userofbricks.expanded_combat.ExpandedCombat.modLoc;

public class ECShieldSmithingRecipeMaker {
    public static List<RecipeHolder<IShieldSmithingRecipe>> createShieldSmithingRecipes() {
        List<RecipeHolder<IShieldSmithingRecipe>> recipes = new ArrayList<>();
        List<ItemStack> bases = new ArrayList<>();
        List<ItemStack> netherite_bases = new ArrayList<>();
        bases.add(new ItemStack(Items.SHIELD));

        Map<Material, Ingredient> materialIngredientMap = new HashMap<>();
        for (Holder<Item> itemReference : BuiltInRegistries.ITEM.holders().toList()) {

            //create a map of items that can be used to put materials on shields
            Material possibleMaterial = itemReference.getData(DataMaps.SHIELD_INGREDIENT_MAP);
            if (possibleMaterial == null) continue;
            Material materialReference = PluginInit.materials.get(possibleMaterial.id());
            if (materialIngredientMap.containsKey(materialReference)) {
                List<ItemStack> list = new ArrayList<>(Arrays.stream(materialIngredientMap.get(materialReference).getItems()).toList());
                list.add(new ItemStack(itemReference));
                materialIngredientMap.put(materialReference, Ingredient.of(list.stream()));
            } else {
                materialIngredientMap.put(materialReference, Ingredient.of(itemReference.value()));
            }

            //create list of shield bases
            ShieldMaterials materials = itemReference.getData(DataMaps.SHIELD_MATERIALS);
            if (materials != null) {
                bases.add(new ItemStack(itemReference));
                if (materials.MMaterial() == ECBasePlugin.DIAMOND
                        || materials.DLMaterial() == ECBasePlugin.DIAMOND
                        || materials.DRMaterial() == ECBasePlugin.DIAMOND
                        || materials.ULMaterial() == ECBasePlugin.DIAMOND
                        || materials.URMaterial() == ECBasePlugin.DIAMOND) {
                    netherite_bases.add(new ItemStack(itemReference));
                }
            }
        }

        List<Material> materials = PluginInit.materials.values().stream()
                .filter(materialReference -> materialReference.defense().placementInShield() != PlacementInShield.NONE).toList();


        for (Material material : materials) {
            ItemStack shield = new ItemStack(material.defense().fireResistant() ? ECItems.SHIELD_FIRE_RESISTANT.get() : ECItems.SHIELD.get());

            shield.set(ItemDataComponents.SHIELD_MATERIALS, new ShieldMaterials(material, material, material, material,
                    material.defense().placementInShield() == PlacementInShield.NOT_TRIM ? ECBasePlugin.IRON : material, 0));
            bases.add(shield);
        }


        for (Material ul_m_dr : materials) {
            if (ul_m_dr.crafting().isSingleAddition()) continue;
        for (Material ur_dl : materials) {
            if (ur_dl.crafting().isSingleAddition()) continue;

            ItemStack resultShield = new ItemStack((
                    ul_m_dr.defense().fireResistant()
                    || ur_dl.defense().fireResistant()
                    ) ? ECItems.SHIELD_FIRE_RESISTANT.get() : ECItems.SHIELD.get());

            ShieldMaterials resultMaterials = new ShieldMaterials(ul_m_dr, ur_dl, ur_dl, ul_m_dr,
                    ul_m_dr.defense().placementInShield() == PlacementInShield.NOT_TRIM ? ECBasePlugin.IRON: ul_m_dr, 0);
            resultShield.set(ItemDataComponents.SHIELD_MATERIALS, resultMaterials);

            Ingredient basesIngrediant = Ingredient.of(bases.stream());
            Ingredient ul_dr_ad = materialIngredientMap.get(ul_m_dr);
            Ingredient ur_dl_ad = materialIngredientMap.get(ur_dl);
            Ingredient m_ad = materialIngredientMap.get(ul_m_dr.defense().placementInShield() == PlacementInShield.NOT_TRIM ? ECBasePlugin.IRON: ul_m_dr);


            ResourceLocation id = modLoc("jei.shield_smithing." + resultMaterials.ULMaterial().id().toString().replace(':', '_')
                    + "." + resultMaterials.URMaterial().id().toString().replace(':', '_')
                    + "." + resultMaterials.DLMaterial().id().toString().replace(':', '_')
                    + "." + resultMaterials.DRMaterial().id().toString().replace(':', '_')
                    + "." + resultMaterials.MMaterial().id().toString().replace(':', '_')
            );
            recipes.add(new RecipeHolder<>(id, new StanderStyleShieldSmithingRecipe(basesIngrediant, ur_dl_ad, ul_dr_ad, m_ad, ul_dr_ad, ur_dl_ad, resultShield)));


            if (ul_m_dr == ECBasePlugin.DIAMOND || ur_dl == ECBasePlugin.DIAMOND) {
                netherite_bases.add(resultShield);
            }
        }
        }

        for (ItemStack base : netherite_bases) {
            ItemStack output = base.copy();
            ShieldMaterials materials1 = base.get(ItemDataComponents.SHIELD_MATERIALS);
            if (materials1 == null) {
                materials1 = base.getItemHolder().getData(DataMaps.SHIELD_MATERIALS);
            }
            if (materials1 == null) continue;
            ShieldMaterials resultMaterials = new ShieldMaterials(
                    materials1.MMaterial() == ECBasePlugin.DIAMOND ? ECBasePlugin.NETHERITE : materials1.ULMaterial(),
                    materials1.DLMaterial() == ECBasePlugin.DIAMOND ? ECBasePlugin.NETHERITE : materials1.DLMaterial(),
                    materials1.DRMaterial() == ECBasePlugin.DIAMOND ? ECBasePlugin.NETHERITE : materials1.DRMaterial(),
                    materials1.ULMaterial() == ECBasePlugin.DIAMOND ? ECBasePlugin.NETHERITE : materials1.ULMaterial(),
                    materials1.URMaterial() == ECBasePlugin.DIAMOND ? ECBasePlugin.NETHERITE : materials1.URMaterial(),
                    0
            );
            output.set(ItemDataComponents.SHIELD_MATERIALS, resultMaterials);
            ResourceLocation id = modLoc("jei.shield_smithing." + resultMaterials.ULMaterial().id().toString().replace(':', '_')
                    + "." + resultMaterials.URMaterial().id().toString().replace(':', '_')
                    + "." + resultMaterials.DLMaterial().id().toString().replace(':', '_')
                    + "." + resultMaterials.DRMaterial().id().toString().replace(':', '_')
                    + "." + resultMaterials.MMaterial().id().toString().replace(':', '_')
            );
            recipes.add(new RecipeHolder<>(id, new StanderStyleShieldSmithingRecipe(Ingredient.of(netherite_bases.stream()), Ingredient.EMPTY, Ingredient.EMPTY, Ingredient.of(Items.NETHERITE_INGOT), Ingredient.EMPTY, Ingredient.EMPTY, output)));
        }

        return recipes;
    }
}
