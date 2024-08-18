package com.userofbricks.expanded_combat.compatability.jei.recipes;

import com.userofbricks.expanded_combat.data.material.Material;
import com.userofbricks.expanded_combat.data.material.PlacementInShield;
import com.userofbricks.expanded_combat.data_components.ShieldMaterials;
import com.userofbricks.expanded_combat.init.*;
import com.userofbricks.expanded_combat.item.recipes.IShieldSmithingRecipe;
import com.userofbricks.expanded_combat.item.recipes.StanderStyleShieldSmithingRecipe;
import net.minecraft.client.Minecraft;
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
            Holder<Material> materialReference = itemReference.getData(DataMaps.SHIELD_INGREDIENT_MAP);
            if (materialReference != null) {
                if (materialIngredientMap.containsKey(materialReference.value())) {
                    List<ItemStack> list = new ArrayList<>(Arrays.stream(materialIngredientMap.get(materialReference.value()).getItems()).toList());
                    list.add(new ItemStack(itemReference));
                    materialIngredientMap.put(materialReference.value(), Ingredient.of(list.stream()));
                } else {
                    materialIngredientMap.put(materialReference.value(), Ingredient.of(itemReference.value()));
                }
            }
            ShieldMaterials materials = itemReference.getData(DataMaps.SHIELD_MATERIALS);
            if (materials != null) {
                bases.add(new ItemStack(itemReference));
                if (materials.MMaterial() == Materials.DIAMOND.get()
                        || materials.DLMaterial() == Materials.DIAMOND.get()
                        || materials.DRMaterial() == Materials.DIAMOND.get()
                        || materials.ULMaterial() == Materials.DIAMOND.get()
                        || materials.URMaterial() == Materials.DIAMOND.get()) {
                    netherite_bases.add(new ItemStack(itemReference));
                }
            }
        }

        List<Material> materials = Registries.MATERIAL_REGISTRY.stream()
                .filter(materialReference -> materialReference.defense().placementInShield() != PlacementInShield.NONE).toList();


        for (Material material : materials) {
            ItemStack shield = new ItemStack(material.defense().fireResistant() ? ECItems.SHIELD_FIRE_RESISTANT.get() : ECItems.SHIELD.get());

            shield.set(ItemDataComponents.SHIELD_MATERIALS, new ShieldMaterials(material, material, material, material,
                    material.defense().placementInShield() == PlacementInShield.NOT_TRIM ? Materials.IRON.get(): material, 0));
            bases.add(shield);
        }


        for (Material ul : materials) {
            if (ul.isSingleAddition()) continue;
        for (Material ur : materials) {
            if (ur.isSingleAddition()) continue;
        for (Material dl : materials) {
            if (dl.isSingleAddition()) continue;
        for (Material dr : materials) {
            if (dr.isSingleAddition()) continue;
        for (Material m : materials) {
            if (m.isSingleAddition()) continue;

            ItemStack resultShield = new ItemStack((
                    ul.defense().fireResistant()
                    || ur.defense().fireResistant()
                    || dl.defense().fireResistant()
                    || dr.defense().fireResistant()
                    || m.defense().fireResistant()
                    ) ? ECItems.SHIELD_FIRE_RESISTANT.get() : ECItems.SHIELD.get());

            ShieldMaterials resultMaterials = new ShieldMaterials(ul, ur, dl, dr,
                    m.defense().placementInShield() == PlacementInShield.NOT_TRIM ? Materials.IRON.get(): m, 0);
            resultShield.set(ItemDataComponents.SHIELD_MATERIALS, resultMaterials);

            Ingredient basesIngrediant = Ingredient.of(bases.stream());
            Ingredient ul_ad = materialIngredientMap.get(ul);
            Ingredient ur_ad = materialIngredientMap.get(ur);
            Ingredient dl_ad = materialIngredientMap.get(dl);
            Ingredient dr_ad = materialIngredientMap.get(dr);
            Ingredient m_ad = materialIngredientMap.get(m.defense().placementInShield() == PlacementInShield.NOT_TRIM ? Materials.IRON: m);


            ResourceLocation id = modLoc("jei.shield_smithing." + Objects.requireNonNull(Registries.MATERIAL_REGISTRY.getKey(resultMaterials.ULMaterial())).toString().replace(':', '_')
                    + "." + Objects.requireNonNull(Registries.MATERIAL_REGISTRY.getKey(resultMaterials.URMaterial())).toString().replace(':', '_')
                    + "." + Objects.requireNonNull(Registries.MATERIAL_REGISTRY.getKey(resultMaterials.DLMaterial())).toString().replace(':', '_')
                    + "." + Objects.requireNonNull(Registries.MATERIAL_REGISTRY.getKey(resultMaterials.DRMaterial())).toString().replace(':', '_')
                    + "." + Objects.requireNonNull(Registries.MATERIAL_REGISTRY.getKey(resultMaterials.MMaterial())).toString().replace(':', '_')
            );
            recipes.add(new RecipeHolder<>(id, new StanderStyleShieldSmithingRecipe(basesIngrediant, ur_ad, ul_ad, m_ad, dr_ad, dl_ad, resultShield)));


            if (m == Materials.DIAMOND.get()
                    || dl == Materials.DIAMOND.get()
                    || dr == Materials.DIAMOND.get()
                    || ul == Materials.DIAMOND.get()
                    || ur == Materials.DIAMOND.get()) {
                netherite_bases.add(resultShield);
            }
        }
        }
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
                    materials1.MMaterial() == Materials.DIAMOND.get() ? Materials.NETHERITE.get() : materials1.ULMaterial(),
                    materials1.DLMaterial() == Materials.DIAMOND.get() ? Materials.NETHERITE.get() : materials1.DLMaterial(),
                    materials1.DRMaterial() == Materials.DIAMOND.get() ? Materials.NETHERITE.get() : materials1.DRMaterial(),
                    materials1.ULMaterial() == Materials.DIAMOND.get() ? Materials.NETHERITE.get() : materials1.ULMaterial(),
                    materials1.URMaterial() == Materials.DIAMOND.get() ? Materials.NETHERITE.get() : materials1.URMaterial(),
                    0
            );
            output.set(ItemDataComponents.SHIELD_MATERIALS, resultMaterials);
            ResourceLocation id = modLoc("jei.shield_smithing." + Objects.requireNonNull(Registries.MATERIAL_REGISTRY.getKey(resultMaterials.ULMaterial())).toString().replace(':', '_')
                    + "." + Objects.requireNonNull(Registries.MATERIAL_REGISTRY.getKey(resultMaterials.URMaterial())).toString().replace(':', '_')
                    + "." + Objects.requireNonNull(Registries.MATERIAL_REGISTRY.getKey(resultMaterials.DLMaterial())).toString().replace(':', '_')
                    + "." + Objects.requireNonNull(Registries.MATERIAL_REGISTRY.getKey(resultMaterials.DRMaterial())).toString().replace(':', '_')
                    + "." + Objects.requireNonNull(Registries.MATERIAL_REGISTRY.getKey(resultMaterials.MMaterial())).toString().replace(':', '_')
            );
            recipes.add(new RecipeHolder<>(id, new StanderStyleShieldSmithingRecipe(Ingredient.of(netherite_bases.stream()), Ingredient.EMPTY, Ingredient.EMPTY, Ingredient.of(Items.NETHERITE_INGOT), Ingredient.EMPTY, Ingredient.EMPTY, output)));
        }

        return recipes;
    }
}
