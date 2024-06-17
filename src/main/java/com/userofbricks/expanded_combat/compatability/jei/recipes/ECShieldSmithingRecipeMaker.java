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

        Map<Holder<Material>, Ingredient> materialIngredientMap = new HashMap<>();
        for (Holder<Item> itemReference : BuiltInRegistries.ITEM.holders().toList()) {
            Holder<Material> materialReference = itemReference.getData(DataMaps.SHIELD_INGREDIENT_MAP);
            if (materialReference != null) {
                if (materialIngredientMap.containsKey(materialReference)) {
                    List<ItemStack> list = new ArrayList<>(Arrays.stream(materialIngredientMap.get(materialReference).getItems()).toList());
                    list.add(new ItemStack(itemReference));
                    materialIngredientMap.put(materialReference, Ingredient.of(list.stream()));
                } else {
                    materialIngredientMap.put(materialReference, Ingredient.of(itemReference.value()));
                }
            }
            ShieldMaterials materials = itemReference.getData(DataMaps.SHIELD_MATERIALS);
            if (materials != null) {
                bases.add(new ItemStack(itemReference));
                if (materials.MMaterial() == Materials.DIAMOND
                        || materials.DLMaterial() == Materials.DIAMOND
                        || materials.DRMaterial() == Materials.DIAMOND
                        || materials.ULMaterial() == Materials.DIAMOND
                        || materials.URMaterial() == Materials.DIAMOND) {
                    netherite_bases.add(new ItemStack(itemReference));
                }
            }
        }

        List<Holder.Reference<Material>> materials = Objects.requireNonNull(Minecraft.getInstance().getConnection())
                .registryAccess().registryOrThrow(Registries.MATERIAL_REGISTRY_KEY).holders()
                .filter(materialReference -> materialReference.value().defense().placementInShield() != PlacementInShield.NONE).toList();


        for (Holder.Reference<Material> material : materials) {
            ItemStack shield = new ItemStack(material.value().defense().fireResistant() ? ECItems.SHIELD_FIRE_RESISTANT.get() : ECItems.SHIELD.get());

            shield.set(ItemDataComponents.SHIELD_MATERIALS, new ShieldMaterials(material, material, material, material,
                    material.value().defense().placementInShield() == PlacementInShield.NOT_TRIM ? Materials.IRON: material, 0));
            bases.add(shield);
        }


        for (Holder.Reference<Material> ul : materials) {
            if (ul.value().isSingleAddition()) continue;
        for (Holder.Reference<Material> ur : materials) {
            if (ur.value().isSingleAddition()) continue;
        for (Holder.Reference<Material> dl : materials) {
            if (dl.value().isSingleAddition()) continue;
        for (Holder.Reference<Material> dr : materials) {
            if (dr.value().isSingleAddition()) continue;
        for (Holder.Reference<Material> m : materials) {
            if (m.value().isSingleAddition()) continue;

            ItemStack resultShield = new ItemStack((
                    ul.value().defense().fireResistant()
                    || ur.value().defense().fireResistant()
                    || dl.value().defense().fireResistant()
                    || dr.value().defense().fireResistant()
                    || m.value().defense().fireResistant()
                    ) ? ECItems.SHIELD_FIRE_RESISTANT.get() : ECItems.SHIELD.get());

            ShieldMaterials resultMaterials = new ShieldMaterials(ul, ur, dl, dr,
                    m.value().defense().placementInShield() == PlacementInShield.NOT_TRIM ? Materials.IRON: m, 0);
            resultShield.set(ItemDataComponents.SHIELD_MATERIALS, resultMaterials);

            Ingredient basesIngrediant = Ingredient.of(bases.stream());
            Ingredient ul_ad = materialIngredientMap.get(ul);
            Ingredient ur_ad = materialIngredientMap.get(ur);
            Ingredient dl_ad = materialIngredientMap.get(dl);
            Ingredient dr_ad = materialIngredientMap.get(dr);
            Ingredient m_ad = materialIngredientMap.get(m.value().defense().placementInShield() == PlacementInShield.NOT_TRIM ? Materials.IRON: m);

            ResourceLocation id = modLoc("jei.shield_smithing." + resultMaterials.ULMaterial().getRegisteredName().replace(':', '_')
                    + "." + resultMaterials.URMaterial().getRegisteredName().replace(':', '_')
                    + "." + resultMaterials.DLMaterial().getRegisteredName().replace(':', '_')
                    + "." + resultMaterials.DRMaterial().getRegisteredName().replace(':', '_')
                    + "." + resultMaterials.MMaterial().getRegisteredName().replace(':', '_')
            );
            recipes.add(new RecipeHolder<>(id, new StanderStyleShieldSmithingRecipe(basesIngrediant, ur_ad, ul_ad, m_ad, dr_ad, dl_ad, resultShield)));


            if (m == Materials.DIAMOND
                    || dl == Materials.DIAMOND
                    || dr == Materials.DIAMOND
                    || ul == Materials.DIAMOND
                    || ur == Materials.DIAMOND) {
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
                    materials1.MMaterial() == Materials.DIAMOND ? Materials.NETHERITE : materials1.ULMaterial(),
                    materials1.DLMaterial() == Materials.DIAMOND ? Materials.NETHERITE : materials1.DLMaterial(),
                    materials1.DRMaterial() == Materials.DIAMOND ? Materials.NETHERITE : materials1.DRMaterial(),
                    materials1.ULMaterial() == Materials.DIAMOND ? Materials.NETHERITE : materials1.ULMaterial(),
                    materials1.URMaterial() == Materials.DIAMOND ? Materials.NETHERITE : materials1.URMaterial(),
                    0
            );
            output.set(ItemDataComponents.SHIELD_MATERIALS, resultMaterials);
            ResourceLocation id = modLoc("jei.shield_smithing." + resultMaterials.ULMaterial().getRegisteredName().replace(':', '_')
                    + "." + resultMaterials.URMaterial().getRegisteredName().replace(':', '_')
                    + "." + resultMaterials.DLMaterial().getRegisteredName().replace(':', '_')
                    + "." + resultMaterials.DRMaterial().getRegisteredName().replace(':', '_')
                    + "." + resultMaterials.MMaterial().getRegisteredName().replace(':', '_')
            );
            recipes.add(new RecipeHolder<>(id, new StanderStyleShieldSmithingRecipe(Ingredient.of(netherite_bases.stream()), Ingredient.EMPTY, Ingredient.EMPTY, Ingredient.of(Items.NETHERITE_INGOT), Ingredient.EMPTY, Ingredient.EMPTY, output)));
        }

        return recipes;
    }
}
