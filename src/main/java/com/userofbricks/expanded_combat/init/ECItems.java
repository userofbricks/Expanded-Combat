package com.userofbricks.expanded_combat.init;

import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.util.DataIngredient;
import com.tterrag.registrate.util.entry.RegistryEntry;
import com.userofbricks.expanded_combat.api.material.Material;
import com.userofbricks.expanded_combat.item.AllayItem;
import com.userofbricks.expanded_combat.item.ECShieldItem;
import com.userofbricks.expanded_combat.item.PurifiedGasBottle;
import com.userofbricks.expanded_combat.item.SolidPureFoodItem;
import com.userofbricks.expanded_combat.item.recipes.builders.FletchingRecipeBuilder;
import com.userofbricks.expanded_combat.item.recipes.builders.HardCodedRecipeBuilder;
import com.userofbricks.expanded_combat.plugins.VanillaECPlugin;
import com.userofbricks.expanded_combat.util.IngredientUtil;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Map;

import static com.userofbricks.expanded_combat.ExpandedCombat.MODID;
import static com.userofbricks.expanded_combat.ExpandedCombat.modLoc;

public class ECItems
{
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);

    public static final DeferredItem<Item> LEATHER_STICK = ITEMS.registerSimpleItem("leather_stick");
    public static final DeferredItem<Item> GOLD_STICK = ITEMS.registerSimpleItem("gold_stick");
    public static final DeferredItem<Item> IRON_STICK = ITEMS.registerSimpleItem("iron_stick");
    public static final DeferredItem<Item> FLETCHED_STICKS = ITEMS.registerSimpleItem("fletched_sticks");
    public static final DeferredItem<PurifiedGasBottle> GAS_BOTTLE = ITEMS.registerItem("gas_bottle",
            properties -> new PurifiedGasBottle(properties, ECBlocks.GAS_BLOCK::get));
    public static final DeferredItem<PurifiedGasBottle> PURIFIED_GAS_BOTTLE = ITEMS.registerItem("purified_gas_bottle",
            properties -> new PurifiedGasBottle(properties, ECBlocks.PURIFIED_GAS_BLOCK::get));
    public static final DeferredItem<SolidPureFoodItem> SOLIDIFIED_PURIFICATION = ITEMS.registerItem("solidified_purification",
            properties -> new SolidPureFoodItem(properties.food(new FoodProperties.Builder()
                    .alwaysEdible().nutrition(0).saturationModifier(0)
                    .build())));
    public static final DeferredItem<Item> BAD_SOUL = ITEMS.registerSimpleItem("evil_soul");
    public static final DeferredItem<Item> GOOD_SOUL = ITEMS.registerSimpleItem("good_soul");
    public static final DeferredItem<AllayItem> ALLAY_ITEM = ITEMS.registerItem("allay", AllayItem::new);

    public static final DeferredItem<ECShieldItem> SHIELD = ITEMS.registerItem("shield_1", ECShieldItem::new);
    public static final DeferredItem<ECShieldItem> SHIELD_FIRE_RESISTANT = ITEMS.registerItem("shield_1", ECShieldItem::new, new Item.Properties().fireResistant());

    private static DeferredItem<ECShieldItem> registerShield(String name, boolean fireresistant) {
        return ITEMS.item(name, (properties -> new ECShieldItem(properties)))
                .lang("Shield")
                .tag(ECItemTags.SHIELDS)
                .tag(Tags.Items.TOOLS_SHIELDS)
                .model((ctx, prov) -> prov.withExistingParent(ctx.getName(), new ResourceLocation(MODID, "item/bases/shield"))
                        .override().predicate(new ResourceLocation("blocking"), 1.0f).model(prov.withExistingParent(ctx.getName()+"_blocking", new ResourceLocation(MODID, "item/bases/shield_blocking"))))
                .register();
    }
}
