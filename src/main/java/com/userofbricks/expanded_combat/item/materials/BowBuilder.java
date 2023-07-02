package com.userofbricks.expanded_combat.item.materials;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.util.entry.RegistryEntry;
import com.userofbricks.expanded_combat.config.ECConfig;
import com.userofbricks.expanded_combat.item.ECBowItem;
import com.userofbricks.expanded_combat.item.ECItemTags;
import com.userofbricks.expanded_combat.item.recipes.conditions.ECConfigBooleanCondition;
import com.userofbricks.expanded_combat.item.recipes.conditions.ECConfigBowRecipeTypeCondition;
import com.userofbricks.expanded_combat.item.recipes.conditions.ECMaterialBooleanCondition;
import com.userofbricks.expanded_combat.util.IngredientUtil;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.NotCondition;
import net.minecraftforge.common.crafting.conditions.OrCondition;

import java.util.HashMap;
import java.util.Map;

import static com.userofbricks.expanded_combat.ExpandedCombat.MODID;

public class BowBuilder extends MaterialBuilder {
    public static RegistryEntry<ECBowItem> generateBow(Registrate registrate, String locationName, String name, Material material, Material craftedFrom) {
        ItemBuilder<ECBowItem, Registrate> itemBuilder = registrate.item(locationName + "_bow", (p) -> new ECBowItem(material, null, p));
        genModel(itemBuilder, locationName, "");
        itemBuilder.tag(ECItemTags.BOWS);
        itemBuilder.recipe((ctx, prov) -> {

            if (!material.getConfig().crafting.repairItem.isEmpty()) {
                InventoryChangeTrigger.TriggerInstance triggerInstance = getTriggerInstance(material.getConfig().crafting.repairItem);

                ECConfigBooleanCondition enableBows = new ECConfigBooleanCondition("bow");
                ECConfigBooleanCondition enableHalfBows = new ECConfigBooleanCondition("half_bow");
                ECConfigBowRecipeTypeCondition smithingOnly = new ECConfigBowRecipeTypeCondition(ECConfig.BowRecipeType.SMITHING_ONLY);
                ECConfigBowRecipeTypeCondition craftingOnly = new ECConfigBowRecipeTypeCondition(ECConfig.BowRecipeType.CRAFTING_TABLE_ONLY);
                ECConfigBowRecipeTypeCondition craftingAndSmithing = new ECConfigBowRecipeTypeCondition(ECConfig.BowRecipeType.CRAFTING_TABLE_AND_SMITHING);
                OrCondition smithing_or_both = new OrCondition(smithingOnly, craftingAndSmithing);
                OrCondition crafting_or_both = new OrCondition(craftingOnly, craftingAndSmithing);
                ECMaterialBooleanCondition isSingleAddition = new ECMaterialBooleanCondition(name, "config", "crafting", "is_single_addition");

                //Shaped Crafting
                Map<Character, Ingredient> ingredientMap = new HashMap<>();
                ingredientMap.put('i', IngredientUtil.getIngrediantFromItemString(material.getConfig().crafting.repairItem));
                if (material.halfbow) {
                    ingredientMap.put('b', material.getHalfBowEntry() == null ? Ingredient.of(Items.BOW) : Ingredient.of(material.getHalfBowEntry().get()));
                    conditionalShapedRecipe(ctx, prov, new String[]{"i", "b"}, ingredientMap, 1, new ICondition[]{crafting_or_both, enableBows, enableHalfBows}, triggerInstance, "");
                }
                ingredientMap.remove('b');
                ingredientMap.put('b', craftedFrom == null ? Ingredient.of(Items.BOW) : Ingredient.of(craftedFrom.getBowEntry().get()));
                conditionalShapedRecipe(ctx, prov, new String[]{"i", "b", "i"}, ingredientMap, 1, new ICondition[]{crafting_or_both, enableBows}, triggerInstance, "_skip");

                //1.20
                conditionalSmithing120Recipe(ctx, prov, material,
                        material.getHalfBowEntry() == null ? Ingredient.of(Items.BOW) : Ingredient.of(material.getHalfBowEntry().get()),
                        new ICondition[]{smithing_or_both, enableBows, enableHalfBows, new NotCondition(isSingleAddition)}, "");
                conditionalSmithing120Recipe(ctx, prov, material,
                        craftedFrom == null ? Ingredient.of(Items.BOW) : Ingredient.of(craftedFrom.getBowEntry().get()),
                        new ICondition[]{smithing_or_both, enableBows, new OrCondition(new NotCondition(enableHalfBows), isSingleAddition)}, "_singleton");
            }
        });
        return itemBuilder.register();
    }
    public static RegistryEntry<ECBowItem> generateHalfBow(Registrate registrate, String locationName, Material material, Material craftedFrom) {
        ItemBuilder<ECBowItem, Registrate> itemBuilder = registrate.item("half_" + locationName + "_bow", (p) -> new ECBowItem(material, craftedFrom, p));
        genModel(itemBuilder, locationName, "half_");
        itemBuilder.tag(ECItemTags.BOWS);
        itemBuilder.recipe((ctx, prov) -> {

            if (!material.getConfig().crafting.repairItem.isEmpty()) {
                InventoryChangeTrigger.TriggerInstance triggerInstance = getTriggerInstance(material.getConfig().crafting.repairItem);

                ECConfigBooleanCondition enableBows = new ECConfigBooleanCondition("bow");
                ECConfigBooleanCondition enableHalfBows = new ECConfigBooleanCondition("half_bow");
                ECConfigBowRecipeTypeCondition smithingOnly = new ECConfigBowRecipeTypeCondition(ECConfig.BowRecipeType.SMITHING_ONLY);
                ECConfigBowRecipeTypeCondition craftingOnly = new ECConfigBowRecipeTypeCondition(ECConfig.BowRecipeType.CRAFTING_TABLE_ONLY);
                ECConfigBowRecipeTypeCondition craftingAndSmithing = new ECConfigBowRecipeTypeCondition(ECConfig.BowRecipeType.CRAFTING_TABLE_AND_SMITHING);
                OrCondition smithing_or_both = new OrCondition(smithingOnly, craftingAndSmithing);
                OrCondition crafting_or_both = new OrCondition(craftingOnly, craftingAndSmithing);

                //Shaped Crafting
                Map<Character, Ingredient> ingredientMap = new HashMap<>();
                ingredientMap.put('i', IngredientUtil.getIngrediantFromItemString(material.getConfig().crafting.repairItem));
                ingredientMap.put('b', material.getHalfBowEntry() == null ? Ingredient.of(Items.BOW) : Ingredient.of(material.getHalfBowEntry().get()));
                conditionalShapedRecipe(ctx, prov, new String[]{"b", "i"}, ingredientMap, 1, new ICondition[]{crafting_or_both, enableBows, enableHalfBows}, triggerInstance, "");

                //1.20
                conditionalSmithing120Recipe(ctx, prov, material,
                        craftedFrom == null ? Ingredient.of(Items.BOW) : Ingredient.of(craftedFrom.getBowEntry().get()),
                        new ICondition[]{smithing_or_both, enableBows, enableHalfBows}, "");
            }
        });
        return itemBuilder.register();
    }

    private static void genModel(ItemBuilder<ECBowItem, Registrate> itemBuilder, String locationName, String prefix) {
        itemBuilder.model((ctx, prov) -> prov.generated(ctx, new ResourceLocation(MODID, "item/bow/" + prefix + locationName))
                .transforms()
                .transform(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND).rotation(-80, 260, -40).translation(-1, -2, 2.5f).scale(0.9f).end()
                .transform(ItemDisplayContext.THIRD_PERSON_LEFT_HAND).rotation(-80, -280, 40).translation(-1, -2, 2.5f).scale(0.9f).end()
                .transform(ItemDisplayContext.FIRST_PERSON_RIGHT_HAND).rotation(0, -90, 25).translation(1.13f, 3.2f, 1.13f).scale(0.68f).end()
                .transform(ItemDisplayContext.FIRST_PERSON_LEFT_HAND).rotation(0, 90, -25).translation(1.13f, 3.2f, 1.13f).scale(0.68f).end()
                .end()
                .override().predicate(new ResourceLocation("pulling"), 1).model(
                        prov.withExistingParent(ctx.getName()+"_pulling_0", new ResourceLocation("item/bow")).texture("layer0", new ResourceLocation(MODID, "item/bow/" + prefix + locationName + "_pulling_0"))
                ).end()
                .override().predicate(new ResourceLocation("pulling"), 1).predicate(new ResourceLocation("pull"), 0.65f).model(
                        prov.withExistingParent(ctx.getName()+"_pulling_1", new ResourceLocation("item/bow")).texture("layer0", new ResourceLocation(MODID, "item/bow/" + prefix + locationName + "_pulling_1"))
                ).end()
                .override().predicate(new ResourceLocation("pulling"), 1).predicate(new ResourceLocation("pull"), 0.9f).model(
                        prov.withExistingParent(ctx.getName()+"_pulling_2", new ResourceLocation("item/bow")).texture("layer0", new ResourceLocation(MODID, "item/bow/" + prefix + locationName + "_pulling_2"))
                ).end());
    }
}
