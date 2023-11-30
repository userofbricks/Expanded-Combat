package com.userofbricks.expanded_combat.api.registry.itemGeneration;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.util.entry.RegistryEntry;
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
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.NotCondition;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.userofbricks.expanded_combat.ExpandedCombat.MODID;

public class QuiverItemBuilder extends MaterialItemBuilder {

    public final MaterialBuilder materialBuilder;
    public final Material material, craftedFrom;
    public final ItemBuilder<? extends Item, Registrate> itemBuilder;
    private String lang = "";
    private NonNullTriConsumer<ItemBuilder<? extends Item, Registrate>, Material, Boolean> modelBuilder;
    private NonNullTriConsumer<ItemBuilder<? extends Item, Registrate>, Material, @Nullable Material> recipeBuilder;
    private NonNullConsumer<ItemBuilder<? extends Item, Registrate>> colorBuilder;

    public QuiverItemBuilder(MaterialBuilder materialBuilder, Registrate registrate, Material material, Material craftedFrom, NonNullBiFunction<Item.Properties, Material, ? extends Item> constructor) {
        ItemBuilder<? extends Item, Registrate> itemBuilder = registrate.item(material.getLocationName().getPath() + "_quiver", (p) -> constructor.apply(p, material));

        itemBuilder.tag(ECItemTags.QUIVERS);

        this.material = material;
        this.itemBuilder = itemBuilder;
        this.materialBuilder = materialBuilder;
        this.craftedFrom = craftedFrom;
        lang = material.getName() + " Gauntlet";
        modelBuilder = QuiverItemBuilder::generateModel;
        recipeBuilder = QuiverItemBuilder::generateRecipes;
        colorBuilder = QuiverItemBuilder::colors;
    }
    public QuiverItemBuilder lang(String englishName) {
        lang = englishName;
        return this;
    }
    public QuiverItemBuilder model(NonNullTriConsumer<ItemBuilder<? extends Item, Registrate>, Material, Boolean> modelBuilder) {
        this.modelBuilder = modelBuilder;
        return this;
    }
    public QuiverItemBuilder recipes(NonNullTriConsumer<ItemBuilder<? extends Item, Registrate>, Material, Material> recipeBuilder) {
        this.recipeBuilder = recipeBuilder;
        return this;
    }

    public QuiverItemBuilder color(NonNullConsumer<ItemBuilder<? extends Item, Registrate>> colorBuilder) {
        this.colorBuilder = colorBuilder;
        return this;
    }

    public MaterialBuilder build(boolean dyeable) {
        itemBuilder.lang(lang);
        modelBuilder.apply(itemBuilder, material, dyeable);
        recipeBuilder.apply(itemBuilder, material, craftedFrom);
        if (dyeable) colorBuilder.accept(itemBuilder);

        materialBuilder.quiver(m -> itemBuilder.register());
        return materialBuilder;
    }

    public static void colors(ItemBuilder<? extends Item, Registrate> itemBuilder) {
        itemBuilder.color(() -> () -> (stack, itemLayer) -> (itemLayer == 0) ? ((DyeableLeatherItem)stack.getItem()).getColor(stack) : -1);
    }
    public static void generateModel(ItemBuilder<? extends Item, Registrate> itemBuilder, Material material, boolean dyeable) {
        String locationName = material.getLocationName().getPath();
        itemBuilder.model((ctx, prov) -> {
            ResourceLocation main_texture = new ResourceLocation(material.getLocationName().getNamespace(), "item/quiver/" + locationName);
            ResourceLocation overlay_texture = new ResourceLocation(material.getLocationName().getNamespace(), "item/quiver/" + locationName + "_overlay");
            if (!dyeable) prov.generated(ctx, main_texture);
            else prov.generated(ctx, main_texture, overlay_texture);
        });
    }
    public static void generateRecipes(ItemBuilder<? extends Item, Registrate> itemBuilder, Material material, @Nullable Material craftedFrom) {
        String name = material.getName();
        itemBuilder.recipe((ctx, prov) -> {
            if (!material.getConfig().crafting.repairItem.isEmpty()) {
                InventoryChangeTrigger.TriggerInstance triggerInstance = getTriggerInstance(material.getConfig().crafting.repairItem);
                ECConfigBooleanCondition enableGauntlets = new ECConfigBooleanCondition("arrow");
                ECMaterialBooleanCondition isSingleAddition = new ECMaterialBooleanCondition(name, "config", "crafting", "is_single_addition");

                Map<Character, Ingredient> recipe = new HashMap<>();
                recipe.put('i', IngredientUtil.getIngrediantFromItemString(material.getConfig().crafting.repairItem));
                recipe.put('l', IngredientUtil.getTagedIngredientOrEmpty("forge", "leather"));
                recipe.put('s', IngredientUtil.getTagedIngredientOrEmpty("forge", "string"));
                conditionalShapedRecipe(ctx, prov, new String[]{"sl ","l l", "il "}, recipe, 1, new ICondition[]{enableGauntlets, new NotCondition(isSingleAddition)}, triggerInstance, "");

                if (craftedFrom != null) {
                    if (material.getConfig().crafting.smithingTemplate != null && !Objects.equals(material.getConfig().crafting.smithingTemplate, Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(Items.AIR)).toString())) {
                        conditionalSmithing120Recipe(ctx, prov,
                                Ingredient.of(ForgeRegistries.ITEMS.getValue(new ResourceLocation(material.getConfig().crafting.smithingTemplate))),
                                IngredientUtil.getIngrediantFromItemString(material.getConfig().crafting.repairItem),
                                Ingredient.of(craftedFrom.getQuiverEntry().get()),
                                new ICondition[]{enableGauntlets, isSingleAddition}, triggerInstance, "");
                    } else {
                        conditionalSmithingWithoutTemplateRecipe(ctx, prov,
                                IngredientUtil.getIngrediantFromItemString(material.getConfig().crafting.repairItem),
                                Ingredient.of(craftedFrom.getQuiverEntry().get()),
                                new ICondition[]{enableGauntlets, isSingleAddition}, triggerInstance, "");
                    }
                }

            }
        });
    }
}
