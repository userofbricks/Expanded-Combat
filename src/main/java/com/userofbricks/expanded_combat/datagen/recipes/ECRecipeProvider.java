package com.userofbricks.expanded_combat.datagen.recipes;

import com.userofbricks.expanded_combat.data.material.Material;
import com.userofbricks.expanded_combat.data.weapon_type.WeaponType;
import com.userofbricks.expanded_combat.init.Materials;
import com.userofbricks.expanded_combat.init.WeaponTypes;
import com.userofbricks.expanded_combat.item.*;
import com.userofbricks.expanded_combat.item.recipes.*;
import com.userofbricks.expanded_combat.item.recipes.builders.FletchingRecipeBuilder;
import com.userofbricks.expanded_combat.item.recipes.conditions.ECConfigBooleanCondition;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static com.userofbricks.expanded_combat.ExpandedCombat.MODID;
import static com.userofbricks.expanded_combat.ExpandedCombat.modLoc;
import static com.userofbricks.expanded_combat.init.ECItems.*;

@ParametersAreNonnullByDefault
public class ECRecipeProvider extends MaterialRecipeProvider {
    private final Map<Holder.Reference<Material>, ItemLike> materialSwords = new HashMap<>();
    private final Map<Holder.Reference<Material>, ItemLike> materialBlocks = new HashMap<>();
    private final Map<Holder.Reference<WeaponType>, ItemLike> diamondWeapons = new HashMap<>();
    private final Map<Holder.Reference<Material>, Ingredient> materialIngredients = new HashMap<>();
    public ECRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider) {
        super(output, provider);
        materialSwords.put(Materials.WOOD_PLANK, Items.WOODEN_SWORD);
        materialSwords.put(Materials.STONE, Items.STONE_SWORD);
        materialSwords.put(Materials.IRON, Items.IRON_SWORD);
        materialSwords.put(Materials.GOLD, Items.GOLDEN_SWORD);
        materialSwords.put(Materials.DIAMOND, Items.DIAMOND_SWORD);

        materialBlocks.put(Materials.WOOD_PLANK, Items.OAK_PLANKS);
        materialBlocks.put(Materials.STONE, Items.STONE);
        materialBlocks.put(Materials.IRON, Items.IRON_BLOCK);
        materialBlocks.put(Materials.GOLD, Items.GOLD_BLOCK);
        materialBlocks.put(Materials.DIAMOND, Items.DIAMOND_BLOCK);

        for (DeferredItem<? extends ECWeaponItem> weapon : DIAMOND_WEAPONS) {
            diamondWeapons.put(weapon.get().weapon, weapon);
        }

        materialIngredients.put(Materials.LEATHER, Ingredient.of(Items.LEATHER));
        materialIngredients.put(Materials.RABBIT_HIDE, Ingredient.of(Items.RABBIT_HIDE));
        materialIngredients.put(Materials.WOOD_PLANK, Ingredient.of(ItemTags.PLANKS));
        materialIngredients.put(Materials.STONE, Ingredient.of(ItemTags.STONE_TOOL_MATERIALS));
        materialIngredients.put(Materials.IRON, Ingredient.of(Items.IRON_INGOT));
        materialIngredients.put(Materials.GOLD, Ingredient.of(Items.GOLD_INGOT));
        materialIngredients.put(Materials.DIAMOND, Ingredient.of(Items.DIAMOND));
        materialIngredients.put(Materials.NETHERITE, Ingredient.of(Items.NETHERITE_INGOT));
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        for (DeferredHolder<Item, ? extends Item> deferredItem : ITEMS.getEntries()) {
            if (deferredItem.get() instanceof ECWeaponItem weaponItem && !(
                    weaponItem.material == Materials.HEAT || weaponItem.material == Materials.FROST || weaponItem.material == Materials.VOID_TOUCHED || weaponItem.material == Materials.SOUL
                            || weaponItem.material == Materials.HEART_STEALER
            )) {
                buildWeaponRecipe(recipeOutput, weaponItem);
            } else if (deferredItem.get() instanceof ECArrowItem arrowItem) {
                if (arrowItem instanceof ECTippedArrowItem) {
                    tippedArrow(recipeOutput, arrowItem, ((ECTippedArrowItem) arrowItem).getNotTipped());
                } else if (arrowItem.material == Materials.NETHERITE) {
                    variableFletching(recipeOutput, arrowItem, Items.NETHERITE_INGOT, DIAMOND_ARROW, 16);
                } else {
                    fletching(recipeOutput, arrowItem, materialIngredients.get(arrowItem.material), FLETCHED_STICKS, 6);
                }
            } else if (deferredItem.get() instanceof ECBowItem bowItem) {
                if (bowItem.material == Materials.NETHERITE) {
                    SmithingTransformRecipeBuilder.smithing(
                                    Ingredient.of(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE),
                                    Ingredient.of(DIAMOND_BOW),
                                    Ingredient.of(Items.NETHERITE_INGOT),
                                    RecipeCategory.COMBAT,
                                    bowItem
                            )
                            .unlocks(getHasName(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE), has(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE))
                            .unlocks(getHasName(DIAMOND_BOW), has(DIAMOND_BOW))
                            .unlocks(getHasName(Items.NETHERITE_INGOT), has(Items.NETHERITE_INGOT))
                            .save(recipeOutput.withConditions(new ECConfigBooleanCondition("bow")), RecipeBuilder.getDefaultRecipeId(bowItem));
                } else {
                    bow(recipeOutput, bowItem, materialIngredients.get(bowItem.material));
                }
            } else if (deferredItem.get() instanceof ECCrossBowItem bowItem) {
                if (bowItem.material == Materials.NETHERITE) {
                    SmithingTransformRecipeBuilder.smithing(
                                    Ingredient.of(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE),
                                    Ingredient.of(DIAMOND_CROSS_BOW),
                                    Ingredient.of(Items.NETHERITE_INGOT),
                                    RecipeCategory.COMBAT,
                                    bowItem
                            )
                            .unlocks(getHasName(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE), has(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE))
                            .unlocks(getHasName(DIAMOND_CROSS_BOW), has(DIAMOND_CROSS_BOW))
                            .unlocks(getHasName(Items.NETHERITE_INGOT), has(Items.NETHERITE_INGOT))
                            .save(recipeOutput.withConditions(new ECConfigBooleanCondition("crossbow")), RecipeBuilder.getDefaultRecipeId(bowItem));
                } else {
                    crossbow(recipeOutput, bowItem, materialIngredients.get(bowItem.material));
                }
            } else if (deferredItem.get() instanceof GauntletItem gauntletItem && !(
                    deferredItem.get() == SOUL_GAUNTLET.get() || deferredItem.get() == BERSERK_GAUNTLETS.get() || deferredItem.get() == BRAWLERS_GAUNTLETS.get() || deferredItem.get() == FIGHTERS_GAUNTLETS.get()
                    )) {
                if (gauntletItem.material == Materials.NETHERITE) {
                    SmithingTransformRecipeBuilder.smithing(
                                    Ingredient.of(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE),
                                    Ingredient.of(DIAMOND_GAUNTLET),
                                    Ingredient.of(Items.NETHERITE_INGOT),
                                    RecipeCategory.COMBAT,
                                    gauntletItem
                            )
                            .unlocks(getHasName(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE), has(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE))
                            .unlocks(getHasName(DIAMOND_GAUNTLET), has(DIAMOND_GAUNTLET))
                            .unlocks(getHasName(Items.NETHERITE_INGOT), has(Items.NETHERITE_INGOT))
                            .save(recipeOutput.withConditions(new ECConfigBooleanCondition("gauntlet")), RecipeBuilder.getDefaultRecipeId(gauntletItem));
                } else {
                    gauntlet(recipeOutput, gauntletItem, materialIngredients.get(gauntletItem.material));
                }
            } else if (deferredItem.get() instanceof QuiverItem quiverItem) {
                if (quiverItem.material == Materials.NETHERITE) {
                    SmithingTransformRecipeBuilder.smithing(
                                    Ingredient.of(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE),
                                    Ingredient.of(DIAMOND_QUIVER),
                                    Ingredient.of(Items.NETHERITE_INGOT),
                                    RecipeCategory.COMBAT,
                                    quiverItem
                            )
                            .unlocks(getHasName(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE), has(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE))
                            .unlocks(getHasName(DIAMOND_QUIVER), has(DIAMOND_QUIVER))
                            .unlocks(getHasName(Items.NETHERITE_INGOT), has(Items.NETHERITE_INGOT))
                            .save(recipeOutput.withConditions(new ECConfigBooleanCondition("quiver")), RecipeBuilder.getDefaultRecipeId(quiverItem));
                } else {
                    quiver(recipeOutput, quiverItem, materialIngredients.get(quiverItem.material));
                }
            }
        }


        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, LEATHER_STICK, 2)
                .define('l', Items.LEATHER)
                .define('s', Items.STICK)
                .pattern("  s")
                .pattern(" l ")
                .pattern("s  ")
                .unlockedBy(getHasName(Items.LEATHER), has(Items.LEATHER))
                .unlockedBy(getHasName(Items.STICK), has(Items.STICK))
                .save(recipeOutput.withConditions(new ECConfigBooleanCondition("weapon")));
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, GOLD_STICK, 2)
                .define('l', Items.GOLD_INGOT)
                .define('s', Items.STICK)
                .pattern("  s")
                .pattern(" l ")
                .pattern("s  ")
                .unlockedBy(getHasName(Items.GOLD_INGOT), has(Items.GOLD_INGOT))
                .unlockedBy(getHasName(Items.STICK), has(Items.STICK))
                .save(recipeOutput.withConditions(new ECConfigBooleanCondition("weapon")));
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, IRON_STICK, 2)
                .define('l', Items.IRON_INGOT)
                .define('s', Items.STICK)
                .pattern("  s")
                .pattern(" l ")
                .pattern("s  ")
                .unlockedBy(getHasName(Items.IRON_INGOT), has(Items.IRON_INGOT))
                .unlockedBy(getHasName(Items.STICK), has(Items.STICK))
                .save(recipeOutput.withConditions(new ECConfigBooleanCondition("weapon")));
        fletching(recipeOutput.withConditions(new ECConfigBooleanCondition("arrow")), FLETCHED_STICKS, Items.FEATHER, Items.STICK, 1);
        SimpleCookingRecipeBuilder.smoking(Ingredient.of(GAS_BOTTLE), RecipeCategory.BREWING, PURIFIED_GAS_BOTTLE, 2, 200)
                .unlockedBy(getHasName(GAS_BOTTLE), has(GAS_BOTTLE))
                .save(recipeOutput.withConditions(new ECConfigBooleanCondition("weapon")));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, GOOD_SOUL)
                .requires(SOLIDIFIED_PURIFICATION, 2)
                .unlockedBy("has_items", InventoryChangeTrigger.TriggerInstance.hasItems(BAD_SOUL.get(), SOLIDIFIED_PURIFICATION))
                .save(recipeOutput.withConditions(new ECConfigBooleanCondition("weapon")));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ALLAY_ITEM)
                .pattern("pap")
                .pattern("psp")
                .pattern("pap")
                .define('p', SOLIDIFIED_PURIFICATION)
                .define('a', Items.AMETHYST_SHARD)
                .define('s', GOOD_SOUL)
                .unlockedBy("has_items", InventoryChangeTrigger.TriggerInstance.hasItems(GOOD_SOUL, SOLIDIFIED_PURIFICATION, Items.AMETHYST_SHARD))
                .save(recipeOutput.withConditions(new ECConfigBooleanCondition("soul")));


        FletchingRecipeBuilder.fletching(Ingredient.of(FLETCHED_STICKS.get()), Ingredient.of(Items.IRON_NUGGET), RecipeCategory.COMBAT, IRON_ARROW.get(), 1)
                .unlockedBy(getHasName(FLETCHED_STICKS), has(FLETCHED_STICKS))
                .unlockedBy(getHasName(Items.IRON_NUGGET), has(Items.IRON_NUGGET))
                .save(recipeOutput.withConditions(new ECConfigBooleanCondition("arrow")), new ResourceLocation(MODID, "iron_arrow_fletching2"));
        fletching(recipeOutput.withConditions(new ECConfigBooleanCondition("arrow")), Items.ARROW, Items.FLINT, FLETCHED_STICKS, 6);

        recipeOutput.accept(modLoc("tipped_arrow_fletching"), new TippedArrowFletchingRecipe(Ingredient.of(Items.ARROW), new ItemStack(Items.TIPPED_ARROW)), null);

        SpecialRecipeBuilder.special(category -> new ShieldSmithingRecipie())
                .save(recipeOutput, new ResourceLocation(MODID, "shield_smithing"));
        SpecialRecipeBuilder.special(category -> new ShieldUpgradeRecipe())
                    .save(recipeOutput, new ResourceLocation(MODID, "shield_smithing_singleton"));
        SpecialRecipeBuilder.special(category -> new ShieldSmithingUpgradeRecipe())
                    .save(recipeOutput, new ResourceLocation(MODID, "shield_vanilla_smithing_singleton"));
        SpecialRecipeBuilder.special(ECShieldDecorationRecipe::new)
                    .save(recipeOutput, new ResourceLocation(MODID, "ec_shield_decoration"));
        SpecialRecipeBuilder.special(PotionDippedWeaponRecipe::new)
                    .save(recipeOutput, new ResourceLocation(MODID, "weapon_potion_dipping_recipe"));
    }

    private void buildWeaponRecipe(RecipeOutput pRecipeOutput, ECWeaponItem weaponItem) {
        Holder.Reference<WeaponType> weaponType = weaponItem.weapon;
        Ingredient material = materialIngredients.get(weaponItem.material);
        ItemLike sword = materialSwords.get(weaponItem.material);
        ItemLike block = materialBlocks.get(weaponItem.material);

        if (weaponItem.material == Materials.NETHERITE) {
            SmithingTransformRecipeBuilder.smithing(
                    Ingredient.of(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE),
                    Ingredient.of(diamondWeapons.get(weaponType)),
                    Ingredient.of(Items.NETHERITE_INGOT),
                    RecipeCategory.COMBAT,
                    weaponItem
            )
            .unlocks(getHasName(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE), has(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE))
            .unlocks(getHasName(diamondWeapons.get(weaponType)), has(diamondWeapons.get(weaponType)))
            .unlocks(getHasName(Items.NETHERITE_INGOT), has(Items.NETHERITE_INGOT))
            .save(pRecipeOutput.withConditions(new ECConfigBooleanCondition("weapon")), RecipeBuilder.getDefaultRecipeId(weaponItem));
        }
        else if (weaponType == WeaponTypes.FLAIL) flail(pRecipeOutput, weaponItem, block);
        else if (weaponType == WeaponTypes.GREAT_HAMMER) greatHammer(pRecipeOutput, weaponItem, block);
        else if (weaponType == WeaponTypes.MACE) mace(pRecipeOutput, weaponItem, block);
        else if (weaponType == WeaponTypes.BATTLE_STAFF) battleStaff(pRecipeOutput, weaponItem, material);
        else if (weaponType == WeaponTypes.BROAD_SWORD) broadSword(pRecipeOutput, weaponItem, sword, material);
        else if (weaponType == WeaponTypes.CLAYMORE) claymore(pRecipeOutput, weaponItem, sword, material);
        else if (weaponType == WeaponTypes.CUTLASS) cutlass(pRecipeOutput, weaponItem, material);
        else if (weaponType == WeaponTypes.DAGGER) dagger(pRecipeOutput, weaponItem, material);
        else if (weaponType == WeaponTypes.DANCERS_SWORD) dancersSword(pRecipeOutput, weaponItem, sword);
        else if (weaponType == WeaponTypes.GLAIVE) glaive(pRecipeOutput, weaponItem, sword);
        else if (weaponType == WeaponTypes.KATANA) katana(pRecipeOutput, weaponItem, sword, material);
        else if (weaponType == WeaponTypes.SCYTHE) scythe(pRecipeOutput, weaponItem, sword, material);
        else if (weaponType == WeaponTypes.SICKLE) sickle(pRecipeOutput, weaponItem, material);
        else if (weaponType == WeaponTypes.SPEAR) spear(pRecipeOutput, weaponItem, sword);
    }
}
