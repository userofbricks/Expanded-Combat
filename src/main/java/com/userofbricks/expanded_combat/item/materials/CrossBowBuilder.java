package com.userofbricks.expanded_combat.item.materials;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.util.entry.RegistryEntry;
import com.userofbricks.expanded_combat.item.ECCrossBowItem;
import com.userofbricks.expanded_combat.item.ECItemTags;
import com.userofbricks.expanded_combat.item.recipes.conditions.ECConfigBooleanCondition;
import com.userofbricks.expanded_combat.item.recipes.conditions.ECMaterialBooleanCondition;
import com.userofbricks.expanded_combat.util.IngredientUtil;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.NotCondition;

import java.util.HashMap;
import java.util.Map;

import static com.userofbricks.expanded_combat.ExpandedCombat.MODID;

public class CrossBowBuilder extends MaterialBuilder{
    public static RegistryEntry<ECCrossBowItem> generateCrossBow(Registrate registrate, String locationName, String name, Material material, Material craftedFrom) {
        ItemBuilder<ECCrossBowItem, Registrate> itemBuilder = registrate.item(locationName + "_crossbow", (p) -> new ECCrossBowItem(material, p));
        itemBuilder.model((ctx, prov) -> prov.generated(ctx, new ResourceLocation(MODID, "item/crossbow/" + locationName))
                .transforms()
                .transform(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND).rotation(-90, 0, -60).translation(2, 0.1f, -3f).scale(0.9f).end()
                .transform(ItemDisplayContext.THIRD_PERSON_LEFT_HAND).rotation(-90, 0, 30).translation(2, 0.1f, -3f).scale(0.9f).end()
                .transform(ItemDisplayContext.FIRST_PERSON_RIGHT_HAND).rotation(-90, 0, -55).translation(1.13f, 3.2f, 1.13f).scale(0.68f).end()
                .transform(ItemDisplayContext.FIRST_PERSON_LEFT_HAND).rotation(-90, 0, 35).translation(1.13f, 3.2f, 1.13f).scale(0.68f).end()
                .end()
                .override().predicate(new ResourceLocation("pulling"), 1).model(
                        prov.withExistingParent(ctx.getName() + "_pulling_0", new ResourceLocation("item/crossbow")).texture("layer0", new ResourceLocation(MODID, "item/crossbow/" + locationName + "_pulling_0"))
                ).end()
                .override().predicate(new ResourceLocation("pulling"), 1).predicate(new ResourceLocation("pull"), 0.58f).model(
                        prov.withExistingParent(ctx.getName() + "_pulling_1", new ResourceLocation("item/crossbow")).texture("layer0", new ResourceLocation(MODID, "item/crossbow/" + locationName + "_pulling_1"))
                ).end()
                .override().predicate(new ResourceLocation("pulling"), 1).predicate(new ResourceLocation("pull"), 1f).model(
                        prov.withExistingParent(ctx.getName() + "_pulling_2", new ResourceLocation("item/crossbow")).texture("layer0", new ResourceLocation(MODID, "item/crossbow/" + locationName + "_pulling_2"))
                ).end()
                .override().predicate(new ResourceLocation("charged"), 1).model(
                        prov.withExistingParent(ctx.getName() + "_arrow", new ResourceLocation("item/crossbow")).texture("layer0", new ResourceLocation(MODID, "item/crossbow/" + locationName + "_arrow"))
                ).end()
                .override().predicate(new ResourceLocation("charged"), 1).predicate(new ResourceLocation("firework"), 1).model(
                        prov.withExistingParent(ctx.getName() + "_firework", new ResourceLocation("item/crossbow")).texture("layer0", new ResourceLocation(MODID, "item/crossbow/" + locationName + "_firework"))
                ).end()
        );
        itemBuilder.tag(ECItemTags.CROSSBOWS);
        itemBuilder.recipe((ctx, prov) -> {
            if (!material.getConfig().crafting.repairItem.isEmpty()) {
                InventoryChangeTrigger.TriggerInstance triggerInstance = getTriggerInstance(material.getConfig().crafting.repairItem);
                ECConfigBooleanCondition enableCrossBows = new ECConfigBooleanCondition("crossbow");
                ECMaterialBooleanCondition isSingleAddition = new ECMaterialBooleanCondition(name, "config", "crafting", "is_single_addition");

                //Shaped Crafting
                Map<Character, Ingredient> ingredientMap = new HashMap<>();
                ingredientMap.put('i', IngredientUtil.getIngrediantFromItemString(material.getConfig().crafting.repairItem));
                ingredientMap.put('b', craftedFrom == null ? Ingredient.of(Items.CROSSBOW) : Ingredient.of(craftedFrom.getCrossbowEntry().get()));
                conditionalShapedRecipe(ctx, prov, new String[]{"ibi", " i "}, ingredientMap, 1, new ICondition[]{enableCrossBows, new NotCondition(isSingleAddition)}, triggerInstance, "");

                //1.20
                conditionalSmithing120Recipe(ctx, prov, material,
                        craftedFrom == null ? Ingredient.of(Items.CROSSBOW) : Ingredient.of(craftedFrom.getCrossbowEntry().get()),
                        new ICondition[]{enableCrossBows, isSingleAddition}, "");
            }
        });

        return itemBuilder.register();
    }
}
