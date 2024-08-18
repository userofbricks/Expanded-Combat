package com.userofbricks.expanded_combat.init;

import com.userofbricks.expanded_combat.data.material.Material;
import com.userofbricks.expanded_combat.data.material.PlacementInShield;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderOwner;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;
import java.util.Optional;

import static com.userofbricks.expanded_combat.ExpandedCombat.MODID;
import static com.userofbricks.expanded_combat.ExpandedCombat.modLoc;

public class Materials {
    public static final DeferredRegister<Material> MATERIALS = DeferredRegister.create(Registries.MATERIAL_REGISTRY, MODID);
    
    public static DeferredHolder<Material, Material> VANILLA = MATERIALS.register("vanilla", () -> new Material(
            Material.Durabilities.shieldGauntlet(0, 0),
            new Material.EnchantingRelated(0, 0, 0),
            new Material.Offense(0, 0, 0.05, false, true, 1, 0),
            new Material.Defense(PlacementInShield.NOT_TRIM, false, false, 0, 0, 0, 2.5f, 0.3f),
            Ingredient.of(Items.AIR)
    ));
    public static DeferredHolder<Material, Material> LEATHER = MATERIALS.register("leather", () -> new Material(
                        Material.Durabilities.shieldGauntlet(131, 80),
                        new Material.EnchantingRelated(5, 15, 0),
                        new Material.Offense(1, 0, 0.05, false, true, 1, 2),
                        new Material.Defense(PlacementInShield.NOT_TRIM, false, false, 1, 0, 0, 2.75f, 0.45f),
                        Ingredient.of(Items.LEATHER)));
    public static DeferredHolder<Material, Material> RABBIT_HIDE = MATERIALS.register("rabbit_hide", () -> new Material(
                        Material.Durabilities.shieldGauntlet(110, 75),
                        new Material.EnchantingRelated(5, 15, 0),
                        new Material.Offense(1, 0, 0.05, false, true, 1, 3),
                        new Material.Defense(PlacementInShield.NOT_TRIM, false, false, 1, 0, 0, 2.65f, 0.5f),
                        Ingredient.of(Items.RABBIT_HIDE)));
    public static DeferredHolder<Material, Material> WOOD_PLANK = MATERIALS.register("wood_plank", () -> new Material(
                        new Material.Durabilities(59, 0, 0, 40),
                        new Material.EnchantingRelated(15, 0, 0),
                        new Material.Offense(0, 0, 0.05, false, true, 1, 0),
                        new Material.Defense(PlacementInShield.ALL, false, false, 0, 0, 0, 2.5f, 0.3f),
                        Ingredient.of(ItemTags.PLANKS)));
    public static DeferredHolder<Material, Material> STONE = MATERIALS.register("stone", () -> new Material(
                        new Material.Durabilities(131, 0, 0, 0),
                        new Material.EnchantingRelated(5, 0, 0),
                        new Material.Offense(1, 0, 0.05, false, true, 1, 0),
                        new Material.Defense(PlacementInShield.NONE, false, false, 0, 0, 0, 0f, 0f),
                        Ingredient.of(ItemTags.STONE_TOOL_MATERIALS)));
    public static DeferredHolder<Material, Material> IRON = MATERIALS.register("iron", () -> new Material(
                        new Material.Durabilities(250, 250, 480, 150),
                        new Material.EnchantingRelated(14, 9, 0),
                        new Material.Offense(2, 3, 0.045, false, true, 1.15f, 4),
                        new Material.Defense(PlacementInShield.ALL, false, false, 2, 0, 0, 3f, 0.6f),
                        Ingredient.of(Items.IRON_INGOT)));
    public static DeferredHolder<Material, Material> GOLD = MATERIALS.register("gold", () -> new Material(
                        new Material.Durabilities(32, 91, 395, 40),
                        new Material.EnchantingRelated(22, 25, 2),
                        new Material.Offense(0, 4, 0.07, false, true, 1.1f, 6),
                        new Material.Defense(PlacementInShield.ALL, false, true, 1, 0, 0, 3f, 0.4f),
                        Ingredient.of(Items.GOLD_INGOT)));
    public static DeferredHolder<Material, Material> DIAMOND = MATERIALS.register("diamond", () -> new Material(
                        new Material.Durabilities(1561, 1561, 672, 300),
                        new Material.EnchantingRelated(10, 10, -0.1f),
                        new Material.Offense(3, 3.75f, 0.04, false, true, 1.3f, 8),
                        new Material.Defense(PlacementInShield.ALL, false, false, 3, 2, 0, 5f, 0.75f),
                        Ingredient.of(Items.DIAMOND)));
    public static DeferredHolder<Material, Material> NETHERITE = MATERIALS.register("netherite", () -> new Material(
                        new Material.Durabilities(2031, 2031, 768, 375),
                        new Material.EnchantingRelated(15, 15, 0.2f),
                        new Material.Offense(4, 4.5f, 0.05, false, true, 1.45f, 10),
                        new Material.Defense(PlacementInShield.ALL, true, false, 3, 3, 0.1, 6f, 0.85f),
                        Ingredient.of(Items.NETHERITE_INGOT),
                        true, Optional.of(List.of(DIAMOND.getId())),
                        Ingredient.of(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE)));
    public static DeferredHolder<Material, Material> HEART_STEALER = MATERIALS.register("heart_stealer", () -> new Material(
                        new Material.Durabilities(2031, 0, 0, 0),
                        new Material.EnchantingRelated(15, 15, 0f),
                        new Material.Offense(4.5, 0f, 0.05, false, true, 1, 0),
                        new Material.Defense(PlacementInShield.NONE, true, false, 0, 0, 0, 0f, 0f),
                        Ingredient.of(Items.AIR)));
    public static DeferredHolder<Material, Material> HEAT = MATERIALS.register("heat", () -> new Material(
                        new Material.Durabilities(2031, 0, 0, 0),
                        new Material.EnchantingRelated(15, 0, 0f),
                        new Material.Offense(4, 0f, 0.05, false, true, 1, 0),
                        new Material.Defense(PlacementInShield.NONE, true, false, 0, 0, 0, 0f, 0f),
                        Ingredient.of(Items.AIR)));
    public static DeferredHolder<Material, Material> FROST = MATERIALS.register("frost", () -> new Material(
                        new Material.Durabilities(1561, 0, 0, 0),
                        new Material.EnchantingRelated(10, 0, 0f),
                        new Material.Offense(3, 0f, 0.05, false, true, 1f, 0),
                        new Material.Defense(PlacementInShield.NONE, false, false, 0, 0, 0, 0f, 0f),
                        Ingredient.of(Items.AIR)));
    public static DeferredHolder<Material, Material> VOID_TOUCHED = MATERIALS.register("void_touched", () -> new Material(
                        new Material.Durabilities(2031, 0, 0, 0),
                        new Material.EnchantingRelated(15, 0, 0f),
                        new Material.Offense(4, 0f, 0.05, false, true, 1, 0),
                        new Material.Defense(PlacementInShield.NONE, false, false, 0, 0, 0, 0f, 0f),
                        Ingredient.of(Items.AIR)));
    public static DeferredHolder<Material, Material> SOUL = MATERIALS.register("soul", () -> new Material(
                        new Material.Durabilities(2031, 2031, 0, 0),
                        new Material.EnchantingRelated(15, 15, 0f),
                        new Material.Offense(4, 0f, 0.05, false, true, 1, 0),
                        new Material.Defense(PlacementInShield.NONE, false, false, 3, 3, 0, 0f, 0f),
                        Ingredient.of(ECItems.GOOD_SOUL, ECItems.BAD_SOUL)));
    public static DeferredHolder<Material, Material> FIGHTERS = MATERIALS.register("fighters", () -> new Material(
                        new Material.Durabilities(0, 1561, 0, 0),
                        new Material.EnchantingRelated(10, 10, 0f),
                        new Material.Offense(3.5, 0f, 0.05, false, true, 1f, 0),
                        new Material.Defense(PlacementInShield.NONE, false, false, 3, 3, 0.05, 0f, 0f),
                        Ingredient.of(Items.AIR)));
    public static DeferredHolder<Material, Material> BERSERK = MATERIALS.register("berserk", () -> new Material(
                        new Material.Durabilities(0, 1561, 0, 0),
                        new Material.EnchantingRelated(10, 10, 0f),
                        new Material.Offense(3.5, 0f, 0.05, false, true, 1f, 0),
                        new Material.Defense(PlacementInShield.NONE, false, false, 3, 3, 0.05, 0f, 0f),
                        Ingredient.of(Items.AIR)));
    public static DeferredHolder<Material, Material> BRAWLERS = MATERIALS.register("brawlers", () -> new Material(
                        new Material.Durabilities(0, 2031, 0, 0),
                        new Material.EnchantingRelated(15, 15, 0f),
                        new Material.Offense(4, 0f, 0.05, false, true, 1, 0),
                        new Material.Defense(PlacementInShield.NONE, false, false, 3, 3, 0, 0f, 0f),
                        Ingredient.of(Items.AIR)));
}
