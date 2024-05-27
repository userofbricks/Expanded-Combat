package com.userofbricks.expanded_combat.api.registry.itemGeneration;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateItemModelProvider;
import com.tterrag.registrate.util.entry.RegistryEntry;
import com.userofbricks.expanded_combat.api.QuadConsumer;
import com.userofbricks.expanded_combat.api.TriConsumer;
import com.userofbricks.expanded_combat.api.TriFunction;
import com.userofbricks.expanded_combat.api.material.Material;
import com.userofbricks.expanded_combat.api.material.MaterialBuilder;
import com.userofbricks.expanded_combat.init.ECItemTags;
import com.userofbricks.expanded_combat.api.material.WeaponMaterial;
import com.userofbricks.expanded_combat.plugins.VanillaECPlugin;
import com.userofbricks.expanded_combat.item.recipes.builders.RecipeIngredientMapBuilder;
import com.userofbricks.expanded_combat.item.recipes.conditions.ECConfigBooleanCondition;
import com.userofbricks.expanded_combat.util.IngredientUtil;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.loaders.SeparateTransformsModelBuilder;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.NotCondition;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.Map;

public class WeaponItemBuilder extends MaterialItemBuilder {
    public final WeaponMaterial weapon;
    public final Material material, craftedFrom;
    public final ItemBuilder<? extends Item, Registrate> itemBuilder;
    public final MaterialBuilder materialBuilder;
    private String lang;
    private QuadConsumer<DataGenContext<Item, ? extends Item>, RegistrateItemModelProvider, Material, WeaponMaterial> modelBuilder;
    private QuadConsumer<ItemBuilder<? extends Item, Registrate>, WeaponMaterial, Material, @Nullable Material> recipeBuilder;
    private TriConsumer<ItemBuilder<? extends Item, Registrate>, WeaponMaterial, Material> colorBuilder;

    public WeaponItemBuilder(MaterialBuilder materialBuilder, Registrate registrate, WeaponMaterial weapon, Material material, Material craftedFrom, TriFunction<Material, WeaponMaterial, Item.Properties, ? extends Item> constructor, boolean shaped) {
        String locationName = material.getLocationName().getPath() + "_" + weapon.getLocationName();
        ItemBuilder<? extends Item, Registrate> itemBuilder = registrate.item(locationName, (p) -> constructor.apply(material, weapon, p));

        if (weapon.potionDippable()) itemBuilder.tag(ECItemTags.POTION_WEAPONS);
        this.weapon = weapon;
        this.material = material;
        this.itemBuilder = itemBuilder;
        this.materialBuilder = materialBuilder;
        this.craftedFrom = craftedFrom;
        lang = material.getName() + " " + weapon.name();
        modelBuilder = WeaponItemBuilder::generateModel;
        recipeBuilder = shaped ? WeaponItemBuilder::generateShapedRecipes : WeaponItemBuilder::generateSmithingRecipes;
        colorBuilder = (i, w, m) -> WeaponItemBuilder.weaponColors(i, w);
    }
    public WeaponItemBuilder lang(String englishName) {
        lang = englishName;
        return this;
    }
    public WeaponItemBuilder model(QuadConsumer<DataGenContext<Item, ? extends Item>, RegistrateItemModelProvider, Material, WeaponMaterial> modelBuilder) {
        this.modelBuilder = modelBuilder;
        return this;
    }
    public WeaponItemBuilder recipes(QuadConsumer<ItemBuilder<? extends Item, Registrate>, WeaponMaterial, Material, Material> recipeBuilder) {
        this.recipeBuilder = recipeBuilder;
        return this;
    }
    public WeaponItemBuilder colors(TriConsumer<ItemBuilder<? extends Item, Registrate>, WeaponMaterial, Material> colorBuilder) {
        this.colorBuilder = colorBuilder;
        return this;
    }

    public MaterialBuilder build() {
        itemBuilder.lang(lang);
        itemBuilder.model((ctx, prov) -> modelBuilder.apply(ctx, prov, material, weapon));
        recipeBuilder.apply(itemBuilder, weapon, material, craftedFrom);
        colorBuilder.apply(itemBuilder, weapon, material);

        materialBuilder.weapon(weapon, (m) -> itemBuilder.register());
        return materialBuilder;
    }



    public static void weaponColors(ItemBuilder<? extends Item, Registrate> itemBuilder, WeaponMaterial weapon) {
        if (weapon.dyeable()) {
            itemBuilder.color(() -> () -> (ItemColor) (stack, itemLayer) -> (itemLayer > 0) ? -1 : ((DyeableLeatherItem)stack.getItem()).getColor(stack));
        } else if (weapon.potionDippable()) {
            itemBuilder.color(() -> () -> (ItemColor) (stack, itemLayer) -> (itemLayer > 0) ? -1 : PotionUtils.getColor(stack));
        }
    }

    public static void generateShapedRecipes(ItemBuilder<? extends Item, Registrate> itemBuilder, WeaponMaterial weapon, Material material, Material craftedFrom) {
        itemBuilder.recipe((ctx, prov) -> {
            Ingredient craftingIngredient = null;
            InventoryChangeTrigger.TriggerInstance triggerInstance = null;
            boolean useCraftingItem = !material.getConfig().crafting.craftingItem.isEmpty();
            if (useCraftingItem) {
                craftingIngredient = Ingredient.of(ForgeRegistries.ITEMS.getValue(new ResourceLocation(material.getConfig().crafting.craftingItem)));
                triggerInstance = getTriggerInstance(Collections.singletonList(material.getConfig().crafting.craftingItem));
            }
            else if (!material.getConfig().crafting.repairItem.isEmpty()) {
                craftingIngredient = IngredientUtil.getIngrediantFromItemString(material.getConfig().crafting.repairItem);
                triggerInstance = getTriggerInstance(material.getConfig().crafting.repairItem);
            }

            if (craftingIngredient != null) {
                ECConfigBooleanCondition enableArrows = new ECConfigBooleanCondition("weapon");

                Map<Character, Ingredient> ingredientMap = new RecipeIngredientMapBuilder().put('i', craftingIngredient).build();
                if (weapon.recipeIngredients() != null) {
                    if (!weapon.recipeContains("i")) ingredientMap.remove('i');
                    ingredientMap.putAll(weapon.recipeIngredients().get().build());
                    if(ingredientMap.get('p') == null && weapon.recipeContains("p")) {
                        Ingredient prev = weapon.craftedFrom() == null ? IngredientUtil.getTagedIngredientOrEmpty("forge", "tools/swords/" + material.getLocationName().getPath()) : Ingredient.of(material.getWeaponEntry(weapon.craftedFrom().name()).get());
                        ingredientMap.put('p', prev);
                    }
                    if(ingredientMap.get('b') == null && weapon.recipeContains("b")) ingredientMap.put('b', IngredientUtil.getTagedIngredientOrEmpty("forge", "storage_blocks/" + material.getLocationName().getPath()));

                    conditionalShapedRecipe(ctx, prov, weapon.recipe(), ingredientMap, 1, new ICondition[]{enableArrows}, triggerInstance, "");
                }
            }
        });
    }

    public static void generateSmithingRecipes(ItemBuilder<? extends Item, Registrate> itemBuilder, WeaponMaterial weapon, Material material, Material craftedFrom) {
        itemBuilder.recipe((ctx, prov) -> {
            ECConfigBooleanCondition enableWeapons = new ECConfigBooleanCondition("weapon");
            if (craftedFrom != null){
                conditionalSmithing120Recipe(ctx, prov, material, Ingredient.of(craftedFrom.getWeaponEntry(weapon.name()).get()), new ICondition[]{enableWeapons}, "");
            }
        });
    }

}
