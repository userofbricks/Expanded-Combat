package com.userofbricks.expanded_combat.item.materials;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateItemModelProvider;
import com.tterrag.registrate.util.entry.RegistryEntry;
import com.userofbricks.expanded_combat.item.ECHammerWeaponItem;
import com.userofbricks.expanded_combat.item.ECItemTags;
import com.userofbricks.expanded_combat.item.ECKatanaItem;
import com.userofbricks.expanded_combat.item.ECWeaponItem;
import com.userofbricks.expanded_combat.item.materials.plugins.VanillaECPlugin;
import com.userofbricks.expanded_combat.item.recipes.builders.RecipeIngredientMapBuilder;
import com.userofbricks.expanded_combat.item.recipes.conditions.ECConfigBooleanCondition;
import com.userofbricks.expanded_combat.item.recipes.conditions.ECMaterialBooleanCondition;
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

import java.util.Map;

public class WeaponBuilder extends MaterialBuilder{
    public static RegistryEntry<ECWeaponItem> generateWeapon(Registrate registrate, String name, WeaponMaterial weapon, Material material, Material craftedFrom) {
        String locationName = material.getLocationName() + "_" + weapon.getLocationName();
        ItemBuilder<ECWeaponItem, Registrate> itemBuilder = registrate.item(locationName, (p) -> new ECWeaponItem(material, weapon, p));
        if (weapon.dyeable() && weapon.potionDippable()) itemBuilder = registrate.item(locationName, (p) -> new ECWeaponItem.HasPotionAndIsDyeable(material, weapon, p));
        else if (weapon.dyeable()) itemBuilder = registrate.item(locationName, (p) -> new ECWeaponItem.Dyeable(material, weapon, p));
        else if (weapon.potionDippable()) itemBuilder = registrate.item(locationName, (p) -> new ECWeaponItem.HasPotion(material, weapon, p));
        if (weapon == VanillaECPlugin.KATANA) itemBuilder = registrate.item(locationName, p -> new ECKatanaItem(material, weapon, p));
        if (weapon == VanillaECPlugin.GREAT_HAMMER) itemBuilder = registrate.item(locationName, p -> new ECHammerWeaponItem(material, weapon, p));


        if (weapon.potionDippable()) itemBuilder.tag(ECItemTags.POTION_WEAPONS);

        itemBuilder.lang(name + " " + weapon.name());

        //MODEL
        itemBuilder.model((ctx, prov) -> generateModel(ctx, prov, weapon, material));
        itemBuilder.recipe((ctx, prov) -> {
            if (!material.getConfig().crafting.repairItem.isEmpty()) {
                InventoryChangeTrigger.TriggerInstance triggerInstance = getTriggerInstance(material.getConfig().crafting.repairItem);
                ECConfigBooleanCondition enableArrows = new ECConfigBooleanCondition("weapon");
                ECMaterialBooleanCondition isSingleAddition = new ECMaterialBooleanCondition(name, "config", "crafting", "is_single_addition");

                Map<Character, Ingredient> ingredientMap = new RecipeIngredientMapBuilder().put('i', material.getConfig().crafting.repairItem).build();
                if (weapon.recipeIngredients() != null) {
                    if (!weapon.recipeContains("i")) ingredientMap.remove('i');
                    ingredientMap.putAll(weapon.recipeIngredients().get().build());
                    if(ingredientMap.get('p') == null && weapon.recipeContains("p")) {
                        Ingredient prev = weapon.craftedFrom() == null ? IngredientUtil.getTagedIngredientOrEmpty("forge", "tools/swords/" + material.getLocationName()) : Ingredient.of(material.getWeaponEntry(weapon.craftedFrom().name()).get());
                        ingredientMap.put('p', prev);
                    }
                    if(ingredientMap.get('b') == null && weapon.recipeContains("b")) ingredientMap.put('b', IngredientUtil.getTagedIngredientOrEmpty("forge", "storage_blocks/" + material.getLocationName()));

                    conditionalShapedRecipe(ctx, prov, weapon.recipe(), ingredientMap, 1, new ICondition[]{enableArrows, new NotCondition(isSingleAddition)}, triggerInstance, "");
                }
                if (craftedFrom != null){
                    conditionalSmithing120Recipe(ctx, prov, material, Ingredient.of(craftedFrom.getWeaponEntry(weapon.name()).get()), new ICondition[]{enableArrows, isSingleAddition}, "");
                }
            }
        });
        if (weapon.dyeable()) {
            itemBuilder.color(() -> () -> (ItemColor) (stack, itemLayer) -> (itemLayer > 0) ? -1 : ((DyeableLeatherItem)stack.getItem()).getColor(stack));
        } else if (weapon.potionDippable()) {
            itemBuilder.color(() -> () -> (ItemColor) (stack, itemLayer) -> (itemLayer > 0) ? -1 : PotionUtils.getColor(stack));
        }

        return itemBuilder.register();
    }

    public static void generateModel(DataGenContext<Item, ECWeaponItem> ctx, RegistrateItemModelProvider prov, WeaponMaterial weapon, Material material) {
        ItemModelBuilder mainModelBuilder = generateModel(ctx, prov, weapon, material, "");
        if (weapon == VanillaECPlugin.KATANA) {
            mainModelBuilder.override()
                    .predicate(new ResourceLocation("blocking"), 1f)
                    .predicate(new ResourceLocation("blocked_recently"), 1f)
                    .predicate(new ResourceLocation("block_pos"), 0.1f)
                    .model(generateModel(ctx, prov, weapon, material, "block_1"))
                    .end();
            mainModelBuilder.override()
                    .predicate(new ResourceLocation("blocking"), 1f)
                    .predicate(new ResourceLocation("blocked_recently"), 1f)
                    .predicate(new ResourceLocation("block_pos"), 0.2f)
                    .model(generateModel(ctx, prov, weapon, material, "block_2"))
                    .end();
            mainModelBuilder.override()
                    .predicate(new ResourceLocation("blocking"), 1f)
                    .predicate(new ResourceLocation("blocked_recently"), 1f)
                    .predicate(new ResourceLocation("block_pos"), 0.3f)
                    .model(generateModel(ctx, prov, weapon, material, "block_3"))
                    .end();
            mainModelBuilder.override()
                    .predicate(new ResourceLocation("blocking"), 1f)
                    .predicate(new ResourceLocation("blocked_recently"), 1f)
                    .predicate(new ResourceLocation("block_pos"), 0.4f)
                    .model(generateModel(ctx, prov, weapon, material, "none"))
                    .end();
        }
    }


    public static ItemModelBuilder generateModel(DataGenContext<Item, ECWeaponItem> ctx, RegistrateItemModelProvider prov, WeaponMaterial weapon, Material material, String baseModelSuffix) {

        if (weapon.hasLargeModel() && !weapon.isBlockWeapon()) {
            SeparateTransformsModelBuilder<ItemModelBuilder> modelFileBuilder = prov.getBuilder(!baseModelSuffix.isBlank() ? ("item/base/" + baseModelSuffix + "/" + ctx.getName() + "_" + baseModelSuffix) : ("item/" + ctx.getName())).parent(new ModelFile.UncheckedModelFile("item/handheld")).customLoader(SeparateTransformsModelBuilder::begin);

            modelFileBuilder.base(generateModel(ctx, prov, weapon, material, "item_large/", "base/" + (!baseModelSuffix.isBlank() ? (baseModelSuffix + "/") : ""), !baseModelSuffix.isBlank() ? baseModelSuffix : ""));
            ItemModelBuilder guiModel = generateModel(ctx, prov, weapon, material, "item/", "gui/", "");
            modelFileBuilder.perspective(ItemDisplayContext.GUI, guiModel);
            modelFileBuilder.perspective(ItemDisplayContext.GROUND, guiModel);
            modelFileBuilder.perspective(ItemDisplayContext.FIXED, guiModel);
            return modelFileBuilder.end();
        } else if (!weapon.hasLargeModel() && weapon.isBlockWeapon()) {
            return getItemBaseModel(prov, weapon, ctx, "", "").texture("head", getWeaponTexture(prov, weapon.getLocationName(), material.getLocationName()));
        } else {
            return generateModel(ctx, prov, weapon, material, "item/", "", "");
        }
    }

    public static ItemModelBuilder generateModel(DataGenContext<Item, ECWeaponItem> ctx, RegistrateItemModelProvider prov, WeaponMaterial weapon, Material material, String directory, String returningModelfolder, String parentSuffix) {
        ItemModelBuilder itemModelBuilder = prov.getBuilder("item/" + returningModelfolder + ctx.getName()).parent(new ModelFile.UncheckedModelFile("item/generated"));
        if (weapon.hasCustomTransforms() || (weapon.hasLargeModel() && directory.equals("item_large/"))) {
            itemModelBuilder = getItemBaseModel(prov, weapon, ctx, returningModelfolder, parentSuffix);
        }
        if (!weapon.dyeable() && !weapon.potionDippable()) {
            itemModelBuilder.texture("layer0", new ResourceLocation("expanded_combat", directory + weapon.getLocationName() + "_handle"));
            itemModelBuilder.texture("layer1",  new ResourceLocation("expanded_combat", directory + weapon.getLocationName() + "_" + material.getLocationName()));
        } else {
            itemModelBuilder.texture("layer0", new ResourceLocation("expanded_combat", directory + weapon.getLocationName() + "_dye"));
            itemModelBuilder.texture("layer1", new ResourceLocation("expanded_combat", directory + weapon.getLocationName() + "_handle"));
            itemModelBuilder.texture("layer2",  new ResourceLocation("expanded_combat", directory + weapon.getLocationName() + "_" + material.getLocationName()));
        }

        return itemModelBuilder;
    }

    private static ItemModelBuilder getItemBaseModel(RegistrateItemModelProvider prov, WeaponMaterial weapon, DataGenContext<Item, ? extends Item> ctx, String returningModelfolder, String parentSuffix) {
        return prov.withExistingParent("item/" + returningModelfolder + ctx.getName(), new ResourceLocation("expanded_combat", "item/bases/" + weapon.getLocationName() + (!parentSuffix.isBlank() ? "_" + parentSuffix : "")));
    }

    private static ResourceLocation getWeaponTexture(RegistrateItemModelProvider prov, String weaponLocation, String textureName) {
        return prov.modLoc("item/" + weaponLocation + "/" + textureName);
    }

}
