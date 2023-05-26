package com.userofbricks.expanded_combat.item;

import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.util.entry.RegistryEntry;
import com.userofbricks.expanded_combat.ExpandedCombat;
import com.userofbricks.expanded_combat.item.curios.ArrowCurio;
import com.userofbricks.expanded_combat.item.materials.*;
import com.userofbricks.expanded_combat.item.recipes.conditions.ECConfigBooleanCondition;
import com.userofbricks.expanded_combat.item.recipes.ECRecipeSerializerInit;
import com.userofbricks.expanded_combat.item.recipes.builders.HardCodedRecipeBuilder;
import com.userofbricks.expanded_combat.item.recipes.builders.RecipeIngredientMapBuilder;
import com.userofbricks.expanded_combat.util.IngredientUtil;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.Direction;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.registries.ForgeRegistries;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.type.capability.ICurio;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Objects;

import static com.userofbricks.expanded_combat.ExpandedCombat.MODID;
import static com.userofbricks.expanded_combat.ExpandedCombat.REGISTRATE;

public class ECItems
{
    public static ArrayList<RegistryEntry<? extends Item>> ITEMS = new ArrayList<>();

    public static final RegistryEntry<Item> LEATHER_STICK = ExpandedCombat.REGISTRATE.get().item("leather_stick", Item::new).recipe((ctx, prov) -> MaterialBuilder
            .conditionalShapedRecipe(ctx, prov, new String[]{"  s", " l ", "s  "}, new RecipeIngredientMapBuilder().put('s', Items.STICK).put('l', Items.LEATHER).build(), 4,
            new ICondition[]{new ECConfigBooleanCondition("weapon")}, InventoryChangeTrigger.TriggerInstance.hasItems(Items.LEATHER, Items.STICK), "")).register();

    public static final RegistryEntry<Item> GOLD_STICK = ExpandedCombat.REGISTRATE.get().item("gold_stick", Item::new).recipe((ctx, prov) -> MaterialBuilder
            .conditionalShapedRecipe(ctx, prov, new String[]{"  s", " i ", "s  "}, new RecipeIngredientMapBuilder().put('i', Items.IRON_INGOT).put('s', Items.STICK).build(), 4,
            new ICondition[]{new ECConfigBooleanCondition("weapon")}, InventoryChangeTrigger.TriggerInstance.hasItems(Items.IRON_INGOT, Items.STICK), "")).register();

    public static final RegistryEntry<Item> IRON_STICK = ExpandedCombat.REGISTRATE.get().item("iron_stick", Item::new).recipe((ctx, prov) -> MaterialBuilder
            .conditionalShapedRecipe(ctx, prov, new String[]{"  s", " i ", "s  "}, new RecipeIngredientMapBuilder().put('i', Items.GOLD_INGOT).put('s', Items.STICK).build(), 4,
            new ICondition[]{new ECConfigBooleanCondition("weapon")}, InventoryChangeTrigger.TriggerInstance.hasItems(Items.GOLD_INGOT, Items.STICK), "")).register();

    public static final RegistryEntry<ECShieldItem> SHIELD_TIER_1 = registerShield("shield_1", false);
    public static final RegistryEntry<ECShieldItem> SHIELD_TIER_2 = registerShield("shield_2", false);
    public static final RegistryEntry<ECShieldItem> SHIELD_TIER_3 = registerShield("shield_3", true);
    public static final RegistryEntry<ECShieldItem> SHIELD_TIER_4 = registerShield("shield_4", true);

    public static void loadClass() {
        ITEMS.add(LEATHER_STICK);
        ITEMS.add(GOLD_STICK);
        ITEMS.add(IRON_STICK);
        for (Material material : MaterialInit.materials) material.registerElements();

        REGISTRATE.get().addDataGenerator(ProviderType.RECIPE, recipeProvider -> {
            new HardCodedRecipeBuilder(RecipeCategory.COMBAT, ECRecipeSerializerInit.EC_SHIELD_SERIALIZER.get())
                    .unlocks("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(IngredientUtil.toItemLikeArray(Ingredient.of(ECItemTags.SHIELDS))))
                    .save(recipeProvider, new ResourceLocation(MODID, "shield_smithing"));
            new HardCodedRecipeBuilder(RecipeCategory.COMBAT, ECRecipeSerializerInit.EC_UPGRADING_SHIELD_SERIALIZER.get())
                    .unlocks("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(IngredientUtil.toItemLikeArray(Ingredient.of(ECItemTags.SHIELDS))))
                    .save(recipeProvider, new ResourceLocation(MODID, "shield_smithing_singleton"));
            new HardCodedRecipeBuilder(RecipeCategory.COMBAT, ECRecipeSerializerInit.LEGACY_EC_SMITHING_UPGRADING_SHIELD_SERIALIZER.get())
                    .unlocks("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(IngredientUtil.toItemLikeArray(Ingredient.of(ECItemTags.SHIELDS))))
                    .save(recipeProvider, new ResourceLocation(MODID, "legacy_shield_vanilla_smithing_singleton"));
            new HardCodedRecipeBuilder(RecipeCategory.COMBAT, ECRecipeSerializerInit.EC_SMITHING_UPGRADING_SHIELD_SERIALIZER.get())
                    .unlocks("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(IngredientUtil.toItemLikeArray(Ingredient.of(ECItemTags.SHIELDS))))
                    .save(recipeProvider, new ResourceLocation(MODID, "shield_vanilla_smithing_singleton"));
            new HardCodedRecipeBuilder(RecipeCategory.COMBAT, ECRecipeSerializerInit.EC_TIPPED_ARROW_SERIALIZER.get())
                    .unlocks("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(IngredientUtil.toItemLikeArray(Ingredient.of(ECItemTags.ARROWS))))
                    .save(recipeProvider, new ResourceLocation(MODID, "ec_tipped_arrow_recipe"));
            new HardCodedRecipeBuilder(RecipeCategory.COMBAT, ECRecipeSerializerInit.EC_POTION_WEAPON_SERIALIZER.get())
                    .unlocks("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(IngredientUtil.toItemLikeArray(Ingredient.of(ECItemTags.POTION_WEAPONS))))
                    .save(recipeProvider, new ResourceLocation(MODID, "weapon_potion_dipping_recipe"));
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

    public static void attachCaps(AttachCapabilitiesEvent<ItemStack> e) {
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
}
