package com.userofbricks.expanded_combat.api.registry.itemGeneration;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.util.nullness.NonNullBiFunction;
import com.tterrag.registrate.util.nullness.NonNullConsumer;
import com.userofbricks.expanded_combat.api.NonNullTriConsumer;
import com.userofbricks.expanded_combat.api.material.Material;
import com.userofbricks.expanded_combat.api.material.MaterialBuilder;
import com.userofbricks.expanded_combat.item.ECItemTags;
import com.userofbricks.expanded_combat.item.recipes.conditions.ECConfigBooleanCondition;
import com.userofbricks.expanded_combat.item.recipes.conditions.ECMaterialBooleanCondition;
import com.userofbricks.expanded_combat.util.IngredientUtil;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.NotCondition;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class CrossBowItemBuilder extends MaterialItemBuilder {
    public final MaterialBuilder materialBuilder;
    public final Material material, craftedFrom;
    public final ItemBuilder<? extends CrossbowItem, Registrate> itemBuilder;
    private String lang = "";
    private NonNullTriConsumer<ItemBuilder<? extends CrossbowItem, Registrate>, Material, Boolean> modelBuilder;
    private NonNullTriConsumer<ItemBuilder<? extends CrossbowItem, Registrate>, Material, @Nullable Material> recipeBuilder;
    private NonNullConsumer<ItemBuilder<? extends CrossbowItem, Registrate>> colorBuilder;

    public CrossBowItemBuilder(MaterialBuilder materialBuilder, Registrate registrate, Material material, Material craftedFrom, NonNullBiFunction<Item.Properties, Material, ? extends CrossbowItem> constructor) {
        ItemBuilder<? extends CrossbowItem, Registrate> itemBuilder = registrate.item(material.getLocationName().getPath() + "_crossbow", (p) -> constructor.apply(p, material));

        itemBuilder.properties(properties -> properties.stacksTo(1));
        itemBuilder.tag(ECItemTags.CROSSBOWS);

        this.material = material;
        this.itemBuilder = itemBuilder;
        this.materialBuilder = materialBuilder;
        this.craftedFrom = craftedFrom;
        lang = material.getName() + " Crossbow";
        modelBuilder = CrossBowItemBuilder::generateModel;
        recipeBuilder = CrossBowItemBuilder::generateRecipes;
        colorBuilder = CrossBowItemBuilder::colors;
    }
    public CrossBowItemBuilder lang(String englishName) {
        lang = englishName;
        return this;
    }
    public CrossBowItemBuilder model(NonNullTriConsumer<ItemBuilder<? extends CrossbowItem, Registrate>, Material, Boolean> modelBuilder) {
        this.modelBuilder = modelBuilder;
        return this;
    }
    public CrossBowItemBuilder recipes(NonNullTriConsumer<ItemBuilder<? extends CrossbowItem, Registrate>, Material, Material> recipeBuilder) {
        this.recipeBuilder = recipeBuilder;
        return this;
    }

    public CrossBowItemBuilder color(NonNullConsumer<ItemBuilder<? extends CrossbowItem, Registrate>> colorBuilder) {
        this.colorBuilder = colorBuilder;
        return this;
    }

    public MaterialBuilder build(boolean dyeable) {
        itemBuilder.lang(lang);
        modelBuilder.apply(itemBuilder, material, dyeable);
        recipeBuilder.apply(itemBuilder, material, craftedFrom);
        if (dyeable) colorBuilder.accept(itemBuilder);

        materialBuilder.crossBow(m -> itemBuilder.register());
        return materialBuilder;
    }

    public static void colors(ItemBuilder<? extends CrossbowItem, Registrate> itemBuilder) {
        itemBuilder.color(() -> () -> (stack, itemLayer) -> (itemLayer == 0) ? ((DyeableLeatherItem)stack.getItem()).getColor(stack) : -1);
    }
    public static void generateModel(ItemBuilder<? extends CrossbowItem, Registrate> itemBuilder, Material material, boolean dyeable) {
        String locationName = material.getLocationName().getPath();
        itemBuilder.model((ctx, prov) -> prov.generated(ctx, new ResourceLocation(material.getLocationName().getNamespace(), "item/crossbow/" + locationName))
                .transforms()
                .transform(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND).rotation(-90, 0, -60).translation(2, 0.1f, -3f).scale(0.9f).end()
                .transform(ItemDisplayContext.THIRD_PERSON_LEFT_HAND).rotation(-90, 0, 30).translation(2, 0.1f, -3f).scale(0.9f).end()
                .transform(ItemDisplayContext.FIRST_PERSON_RIGHT_HAND).rotation(-90, 0, -55).translation(1.13f, 3.2f, 1.13f).scale(0.68f).end()
                .transform(ItemDisplayContext.FIRST_PERSON_LEFT_HAND).rotation(-90, 0, 35).translation(1.13f, 3.2f, 1.13f).scale(0.68f).end()
                .end()
                .override().predicate(new ResourceLocation("pulling"), 1).model(
                        prov.withExistingParent(ctx.getName() + "_pulling_0", new ResourceLocation("item/crossbow")).texture("layer0", new ResourceLocation(material.getLocationName().getNamespace(), "item/crossbow/" + locationName + "_pulling_0"))
                ).end()
                .override().predicate(new ResourceLocation("pulling"), 1).predicate(new ResourceLocation("pull"), 0.58f).model(
                        prov.withExistingParent(ctx.getName() + "_pulling_1", new ResourceLocation("item/crossbow")).texture("layer0", new ResourceLocation(material.getLocationName().getNamespace(), "item/crossbow/" + locationName + "_pulling_1"))
                ).end()
                .override().predicate(new ResourceLocation("pulling"), 1).predicate(new ResourceLocation("pull"), 1f).model(
                        prov.withExistingParent(ctx.getName() + "_pulling_2", new ResourceLocation("item/crossbow")).texture("layer0", new ResourceLocation(material.getLocationName().getNamespace(), "item/crossbow/" + locationName + "_pulling_2"))
                ).end()
                .override().predicate(new ResourceLocation("charged"), 1).model(
                        prov.withExistingParent(ctx.getName() + "_arrow", new ResourceLocation("item/crossbow")).texture("layer0", new ResourceLocation(material.getLocationName().getNamespace(), "item/crossbow/" + locationName + "_arrow"))
                ).end()
                .override().predicate(new ResourceLocation("charged"), 1).predicate(new ResourceLocation("firework"), 1).model(
                        prov.withExistingParent(ctx.getName() + "_firework", new ResourceLocation("item/crossbow")).texture("layer0", new ResourceLocation(material.getLocationName().getNamespace(), "item/crossbow/" + locationName + "_firework"))
                ).end()
        );
    }
    public static void generateRecipes(ItemBuilder<? extends CrossbowItem, Registrate> itemBuilder, Material material, @Nullable Material craftedFrom) {
        String name = material.getName();
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
    }
}
