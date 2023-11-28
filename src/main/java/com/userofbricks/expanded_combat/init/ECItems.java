package com.userofbricks.expanded_combat.init;

import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.util.DataIngredient;
import com.tterrag.registrate.util.entry.RegistryEntry;
import com.userofbricks.expanded_combat.api.registry.itemGeneration.GauntletItemBuilder;
import com.userofbricks.expanded_combat.item.*;
import com.userofbricks.expanded_combat.api.curios.ArrowCurio;
import com.userofbricks.expanded_combat.api.material.Material;
import com.userofbricks.expanded_combat.api.registry.itemGeneration.MaterialItemBuilder;
import com.userofbricks.expanded_combat.plugins.VanillaECPlugin;
import com.userofbricks.expanded_combat.item.recipes.builders.FletchingRecipeBuilder;
import com.userofbricks.expanded_combat.item.recipes.builders.HardCodedRecipeBuilder;
import com.userofbricks.expanded_combat.item.recipes.builders.RecipeIngredientMapBuilder;
import com.userofbricks.expanded_combat.item.recipes.conditions.ECConfigBooleanCondition;
import com.userofbricks.expanded_combat.util.IngredientUtil;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagFile;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.type.capability.ICurio;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

import static com.userofbricks.expanded_combat.ExpandedCombat.*;
import static com.userofbricks.expanded_combat.api.registry.itemGeneration.WeaponItemBuilder.getItemBaseModel;

public class ECItems
{
    public static ArrayList<RegistryEntry<? extends Item>> ITEMS = new ArrayList<>();

    public static final RegistryEntry<Item> LEATHER_STICK = REGISTRATE.get().item("leather_stick", Item::new).recipe((ctx, prov) -> MaterialItemBuilder
            .conditionalShapedRecipe(ctx, prov, new String[]{"  s", " l ", "s  "}, new RecipeIngredientMapBuilder().put('s', Items.STICK).put('l', Items.LEATHER).build(), 4,
            new ICondition[]{new ECConfigBooleanCondition("weapon")}, InventoryChangeTrigger.TriggerInstance.hasItems(Items.LEATHER, Items.STICK), "")).register();

    public static final RegistryEntry<Item> GOLD_STICK = REGISTRATE.get().item("gold_stick", Item::new).recipe((ctx, prov) -> MaterialItemBuilder
            .conditionalShapedRecipe(ctx, prov, new String[]{"  s", " i ", "s  "}, new RecipeIngredientMapBuilder().put('i', Items.GOLD_INGOT).put('s', Items.STICK).build(), 4,
            new ICondition[]{new ECConfigBooleanCondition("weapon")}, InventoryChangeTrigger.TriggerInstance.hasItems(Items.GOLD_INGOT, Items.STICK), "")).register();

    public static final RegistryEntry<Item> IRON_STICK = REGISTRATE.get().item("iron_stick", Item::new).recipe((ctx, prov) -> MaterialItemBuilder
            .conditionalShapedRecipe(ctx, prov, new String[]{"  s", " i ", "s  "}, new RecipeIngredientMapBuilder().put('i', Items.IRON_INGOT).put('s', Items.STICK).build(), 4,
            new ICondition[]{new ECConfigBooleanCondition("weapon")}, InventoryChangeTrigger.TriggerInstance.hasItems(Items.IRON_INGOT, Items.STICK), "")).register();

    public static final RegistryEntry<Item> FLETCHED_STICKS = REGISTRATE.get().item("fletched_sticks", Item::new).recipe((ctx, prov) -> {
        MaterialItemBuilder.conditionalFletchingRecipe(ctx, prov, Ingredient.of(Items.FEATHER), Ingredient.of(Items.STICK), new ICondition[]{new ECConfigBooleanCondition("arrow")},
                InventoryChangeTrigger.TriggerInstance.hasItems(Items.FEATHER, Items.STICK), "", 1);
    }).register();
    public static final RegistryEntry<PurifiedGasBottle> GAS_BOTTLE = REGISTRATE.get().item("gas_bottle", properties -> new PurifiedGasBottle(properties, ECBlocks.GAS_BLOCK::get)).register();

    public static final RegistryEntry<PurifiedGasBottle> PURIFIED_GAS_BOTTLE = REGISTRATE.get().item("purified_gas_bottle", properties -> new PurifiedGasBottle(properties, ECBlocks.PURIFIED_GAS_BLOCK::get))
            .recipe((ctx, prov) -> prov.smoking(DataIngredient.items(GAS_BOTTLE.get()), RecipeCategory.BREWING, ctx, 2, 200))
            .register();

    public static final RegistryEntry<SolidPureFoodItem> SOLIDIFIED_PURIFICATION = REGISTRATE.get().item("solidified_purification", SolidPureFoodItem::new)
            .initialProperties(() -> new Item.Properties().food( new FoodProperties.Builder()
                    .alwaysEat().nutrition(0).saturationMod(0)
                    .build()))
            .register();
    public static final RegistryEntry<Item> BAD_SOUL = REGISTRATE.get().item("evil_soul", Item::new).register();
    public static final RegistryEntry<Item> GOOD_SOUL = REGISTRATE.get().item("good_soul", Item::new)
            .recipe((ctx, prov) -> ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ctx.get())
                    .requires(SOLIDIFIED_PURIFICATION.get(), 2)
                    .unlockedBy("has_items", InventoryChangeTrigger.TriggerInstance.hasItems(BAD_SOUL.get(), SOLIDIFIED_PURIFICATION.get()))
                    .save(prov))
            .register();
    public static final RegistryEntry<AllayItem> ALLAY_ITEM = REGISTRATE.get().item("allay", AllayItem::new)
            .model((c, p) -> {})
            .recipe((ctx, prov) -> ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ctx.get())
                    .pattern("pap")
                    .pattern("psp")
                    .pattern("pap")
                    .define('p', SOLIDIFIED_PURIFICATION.get())
                    .define('a', Items.AMETHYST_SHARD)
                    .define('s', GOOD_SOUL.get())
                    .unlockedBy("has_items", InventoryChangeTrigger.TriggerInstance.hasItems(GOOD_SOUL.get(), SOLIDIFIED_PURIFICATION.get(), Items.AMETHYST_SHARD))
                    .save(prov))
            .register();

    public static final RegistryEntry<ECShieldItem> SHIELD_TIER_1 = registerShield("shield_1", false);
    public static final RegistryEntry<ECShieldItem> SHIELD_TIER_2 = registerShield("shield_2", false);
    public static final RegistryEntry<ECShieldItem> SHIELD_TIER_3 = registerShield("shield_3", true);
    public static final RegistryEntry<ECShieldItem> SHIELD_TIER_4 = registerShield("shield_4", true);

    public static final RegistryEntry<HeartStealerItem> HEARTSTEALER = REGISTRATE.get().item("heartstealer", HeartStealerItem::new)
            .model((ctx, prov) -> {
                ItemModelBuilder stage1Builder =  getItemBaseModel(prov, VanillaECPlugin.CLAYMORE, ctx, "", "")
                        .texture("layer0",  new ResourceLocation(ctx.getId().getNamespace(), "item_large/" + ctx.getId().getPath() + "/stage1"));
                ItemModelBuilder stage2Builder =  getItemBaseModel(prov, VanillaECPlugin.CLAYMORE, ctx, "heartstealer/stage2_", "")
                        .texture("layer0",  new ResourceLocation(ctx.getId().getNamespace(), "item_large/" + ctx.getId().getPath() + "/stage2"));
                ItemModelBuilder stage3Builder =  getItemBaseModel(prov, VanillaECPlugin.CLAYMORE, ctx, "heartstealer/stage3_", "")
                        .texture("layer0",  new ResourceLocation(ctx.getId().getNamespace(), "item_large/" + ctx.getId().getPath() + "/stage3"));
                ItemModelBuilder stage4Builder =  getItemBaseModel(prov, VanillaECPlugin.CLAYMORE, ctx, "heartstealer/stage4_", "")
                        .texture("layer0",  new ResourceLocation(ctx.getId().getNamespace(), "item_large/" + ctx.getId().getPath() + "/stage4"));
                ItemModelBuilder stage5Builder =  getItemBaseModel(prov, VanillaECPlugin.CLAYMORE, ctx, "heartstealer/stage5_", "")
                        .texture("layer0",  new ResourceLocation(ctx.getId().getNamespace(), "item_large/" + ctx.getId().getPath() + "/stage5"));

                stage1Builder.override()
                        .predicate(new ResourceLocation("stage"), 0.4f)
                        .model(stage2Builder)
                        .end();
                stage1Builder.override()
                        .predicate(new ResourceLocation("stage"), 0.6f)
                        .model(stage3Builder)
                        .end();
                stage1Builder.override()
                        .predicate(new ResourceLocation("stage"), 0.8f)
                        .model(stage4Builder)
                        .end();
                stage1Builder.override()
                        .predicate(new ResourceLocation("stage"), 1f)
                        .model(stage5Builder)
                        .end();
            })
            .register();
    public static final RegistryEntry<UniqueStandardGaunlet> GAUNTLET = REGISTRATE.get().item("gauntlet", UniqueStandardGaunlet::new)
            .tag(ECItemTags.GAUNTLETS, ItemTags.TRIMMABLE_ARMOR)
            .model((ctx, prov) -> GauntletItemBuilder.generateGauntletModel("gauntlet", VanillaECPlugin.LEATHER, ctx, prov))
            .register();

    public static final RegistryEntry<Mawlers> MAULERS = REGISTRATE.get().item("maulers", Mawlers::new)
            .tag(ECItemTags.GAUNTLETS, ItemTags.TRIMMABLE_ARMOR)
            .model((ctx, prov) -> GauntletItemBuilder.generateGauntletModel("maulers", VanillaECPlugin.LEATHER, ctx, prov))
            .register();

    public static final RegistryEntry<FightersBindings> FIGHTERS_GAUNTLET = REGISTRATE.get().item("fighters_gauntlet", FightersBindings::new)
            .tag(ECItemTags.GAUNTLETS, ItemTags.TRIMMABLE_ARMOR)
            .model((ctx, prov) -> GauntletItemBuilder.generateGauntletModel("fighters_gauntlet", VanillaECPlugin.LEATHER, ctx, prov))
            .register();

    public static final RegistryEntry<SoulFist> SOUL_FIST_GAUNTLETS = REGISTRATE.get().item("soul_fist", SoulFist::new)
            .tag(ECItemTags.GAUNTLETS, ItemTags.TRIMMABLE_ARMOR)
            .model((ctx, prov) -> GauntletItemBuilder.generateGauntletModel("soul_fist", VanillaECPlugin.GOLD, ctx, prov))
            .register();


    //public static final TagKey<EntityType<?>>

    public static void loadClass() {
        ITEMS.add(LEATHER_STICK);
        ITEMS.add(GOLD_STICK);
        ITEMS.add(IRON_STICK);
        ITEMS.add(FLETCHED_STICKS);
        ITEMS.add(PURIFIED_GAS_BOTTLE);
        ITEMS.add(SOLIDIFIED_PURIFICATION);
        ITEMS.add(SHIELD_TIER_1);
        ITEMS.add(SHIELD_TIER_2);
        ITEMS.add(SHIELD_TIER_3);
        ITEMS.add(SHIELD_TIER_4);
        ITEMS.add(HEARTSTEALER);
        ITEMS.add(GAUNTLET);
        ITEMS.add(MAULERS);
        ITEMS.add(FIGHTERS_GAUNTLET);
        ITEMS.add(SOUL_FIST_GAUNTLETS);
        ITEMS.add(ALLAY_ITEM);
        for (Material material : MaterialInit.materials) {
            if (material.getArrowEntry() != null) ITEMS.add(material.getArrowEntry());
            if (material.getTippedArrowEntry() != null) ITEMS.add(material.getTippedArrowEntry());
            if (material.getHalfBowEntry() != null) ITEMS.add(material.getHalfBowEntry());
            if (material.getBowEntry() != null) ITEMS.add(material.getBowEntry());
            if (material.getCrossbowEntry() != null) ITEMS.add(material.getCrossbowEntry());
            if (material.getGauntletEntry() != null) ITEMS.add(material.getGauntletEntry());
            if (material.getQuiverEntry() != null) ITEMS.add(material.getQuiverEntry());
            if (material.getArrowEntry() != null) ITEMS.add(material.getArrowEntry());
            for (Map.Entry<String, RegistryEntry<? extends Item>> entry : material.getWeapons().entrySet()) {
                if (entry.getValue() != null) ITEMS.add(entry.getValue());
            }
        }

        REGISTRATE.get().addDataGenerator(ProviderType.RECIPE, recipeProvider -> {
            new HardCodedRecipeBuilder(RecipeCategory.COMBAT, ECRecipeSerializerInit.EC_SHIELD_SERIALIZER.get())
                    .unlocks("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(IngredientUtil.toItemLikeArray(Ingredient.of(ECItemTags.SHIELDS))))
                    .save(recipeProvider, new ResourceLocation(MODID, "shield_smithing"));
            new HardCodedRecipeBuilder(RecipeCategory.COMBAT, ECRecipeSerializerInit.EC_UPGRADING_SHIELD_SERIALIZER.get())
                    .unlocks("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(IngredientUtil.toItemLikeArray(Ingredient.of(ECItemTags.SHIELDS))))
                    .save(recipeProvider, new ResourceLocation(MODID, "shield_smithing_singleton"));
            new HardCodedRecipeBuilder(RecipeCategory.COMBAT, ECRecipeSerializerInit.EC_SMITHING_UPGRADING_SHIELD_SERIALIZER.get())
                    .unlocks("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(IngredientUtil.toItemLikeArray(Ingredient.of(ECItemTags.SHIELDS))))
                    .save(recipeProvider, new ResourceLocation(MODID, "shield_vanilla_smithing_singleton"));
            new HardCodedRecipeBuilder(RecipeCategory.COMBAT, ECRecipeSerializerInit.EC_SHIELD_DECORATION.get())
                    .unlocks("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(IngredientUtil.toItemLikeArray(Ingredient.of(ItemTags.BANNERS))))
                    .save(recipeProvider, new ResourceLocation(MODID, "ec_shield_decoration"));

            new HardCodedRecipeBuilder(RecipeCategory.COMBAT, ECRecipeSerializerInit.EC_POTION_WEAPON_SERIALIZER.get())
                    .unlocks("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(IngredientUtil.toItemLikeArray(Ingredient.of(ECItemTags.POTION_WEAPONS))))
                    .save(recipeProvider, new ResourceLocation(MODID, "weapon_potion_dipping_recipe"));

            new HardCodedRecipeBuilder(RecipeCategory.COMBAT, ECRecipeSerializerInit.EC_TIPPED_ARROW_SERIALIZER.get())
                    .unlocks("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(IngredientUtil.toItemLikeArray(Ingredient.of(ItemTags.ARROWS))))
                    .save(recipeProvider, new ResourceLocation(MODID, "ec_tipped_arrow_recipe"));
            new HardCodedRecipeBuilder(RecipeCategory.COMBAT, ECRecipeSerializerInit.EC_TIPPED_ARROW_FLETCHING_SERIALIZER.get())
                    .unlocks("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(IngredientUtil.toItemLikeArray(Ingredient.of(ItemTags.ARROWS))))
                    .save(recipeProvider, new ResourceLocation(MODID, "tipped_arrow_fletching_recipe"));

            FletchingRecipeBuilder.fletching(Ingredient.of(FLETCHED_STICKS.get()), Ingredient.of(Items.IRON_NUGGET), RecipeCategory.COMBAT, VanillaECPlugin.IRON.getArrowEntry().get(), 2)
                    .unlocks("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(FLETCHED_STICKS.get()))
                    .save(recipeProvider, new ResourceLocation(MODID, "iron_arrow_fletching2"));
            FletchingRecipeBuilder.fletching(Ingredient.of(FLETCHED_STICKS.get()), Ingredient.of(Items.FLINT), RecipeCategory.COMBAT, Items.ARROW, 6)
                    .unlocks("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(FLETCHED_STICKS.get()))
                    .save(recipeProvider, new ResourceLocation(MODID, "gold_arrow_fletching2"));

            FletchingRecipeBuilder.fletchingVarableResult(Ingredient.of(VanillaECPlugin.DIAMOND.getTippedArrowEntry().get()), Ingredient.of(Items.NETHERITE_INGOT), RecipeCategory.COMBAT, VanillaECPlugin.NETHERITE.getTippedArrowEntry().get(), 32)
                    .unlocks("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(FLETCHED_STICKS.get()))
                    .save(recipeProvider, new ResourceLocation(MODID, "netherite_arrow_fletching2"));

            ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, VanillaECPlugin.IRON.getArrowEntry().get(), 1)
                    .pattern("N")
                    .pattern("S")
                    .pattern("F")
                    .define('N', Items.IRON_NUGGET)
                    .define('S', Items.STICK)
                    .define('F', Items.FEATHER)
                    .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(Items.IRON_NUGGET, Items.STICK, Items.FEATHER))
                    .save(recipeProvider, new ResourceLocation(MODID, "iron_arrow_shaped2"));
        });

        REGISTRATE.get().addDataGenerator(ProviderType.ENTITY_TAGS, tagProvider -> {
            //tagProvider.addTag()
        });
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

    @SubscribeEvent
    public void attachCaps(AttachCapabilitiesEvent<ItemStack> e) {
        ItemStack stack = e.getObject();

        if (Objects.requireNonNull(ForgeRegistries.ITEMS.tags()).getTag(ItemTags.ARROWS).contains(stack.getItem())) {
            ArrowCurio arrowCurio = new ArrowCurio(stack);
            e.addCapability(CuriosCapability.ID_ITEM, new ICapabilityProvider() {
                final LazyOptional<ICurio> curio = LazyOptional.of(() -> arrowCurio);

                @Nonnull
                public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
                    return CuriosCapability.ITEM.orEmpty(cap, this.curio);
                }
            });
        }
    }

    public static TagKey<EntityType<?>> modTag(String name)
    {
        return TagKey.create(Registries.ENTITY_TYPE, modLoc(name));
    }
}
