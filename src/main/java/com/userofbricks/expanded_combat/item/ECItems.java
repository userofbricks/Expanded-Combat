package com.userofbricks.expanded_combat.item;

import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.util.entry.RegistryEntry;
import com.userofbricks.expanded_combat.ExpandedCombat;
import com.userofbricks.expanded_combat.item.materials.GauntletMaterial;
import com.userofbricks.expanded_combat.item.materials.MaterialInit;
import com.userofbricks.expanded_combat.item.recipes.ShieldSmithingRecipeBuilder;
import com.userofbricks.expanded_combat.util.IngredientUtil;
import com.userofbricks.expanded_combat.util.ModIDs;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.Tags;
import net.minecraftforge.fml.ModList;
import twilightforest.init.TFItems;

import java.util.ArrayList;

import static com.userofbricks.expanded_combat.ExpandedCombat.*;

public class ECItems
{
    public static ArrayList<RegistryEntry<? extends Item>> ITEMS = new ArrayList<>();

    public static final RegistryEntry<ECShieldItem> SHIELD_TIER_1 = registerShield("shield_1", false);
    public static final RegistryEntry<ECShieldItem> SHIELD_TIER_2 = registerShield("shield_2", false);
    public static final RegistryEntry<ECShieldItem> SHIELD_TIER_3 = registerShield("shield_3", true);
    public static final RegistryEntry<ECShieldItem> SHIELD_TIER_4 = registerShield("shield_4", true);

    public static void loadClass() {
        for (GauntletMaterial gm : MaterialInit.gauntletMaterials) { gm.registerElements(); }
        REGISTRATE.get().addDataGenerator(ProviderType.RECIPE, recipeProvider -> new ShieldSmithingRecipeBuilder(RecipeCategory.COMBAT)
                .unlocks("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(IngredientUtil.toItemLikeArray(Ingredient.of(ECItemTags.SHIELDS))))
                .save(recipeProvider, new ResourceLocation(MODID, "shield_smithing")));
    }

    private static RegistryEntry<ECShieldItem> registerShield(String name, boolean fireresistant) {
        RegistryEntry<ECShieldItem> shieldRegistryEntry = REGISTRATE.get().item(name, (properties -> new ECShieldItem(fireresistant, properties)))
                .lang("Shield")
                .tag(ECItemTags.SHIELDS)
                .tag(Tags.Items.TOOLS_SHIELDS)
                .model((ctx, prov) -> prov.withExistingParent(ctx.getName(), new ResourceLocation(MODID, "item/bases/shield"))
                        .override().predicate(new ResourceLocation("blocking"), 1.0f).model(prov.withExistingParent(ctx.getName()+"_blocking", new ResourceLocation(MODID, "item/bases/shield_blocking"))))
                .register();
        ITEMS.add(shieldRegistryEntry);
        return shieldRegistryEntry;
    }
}
