package com.userofbricks.expanded_combat.init;

import com.userofbricks.expanded_combat.data.material.Material;
import com.userofbricks.expanded_combat.data.material.PlacementInShield;
import com.userofbricks.expanded_combat.data.weapon_type.WeaponType;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;
import java.util.Optional;

import static com.userofbricks.expanded_combat.ExpandedCombat.modLoc;

public class Materials {
    public static Holder.Reference<Material> VANILLA;
    public static final ResourceKey<Material> VANILLA_KEY = createMaterialKey(modLoc("vanilla"));
    public static Holder.Reference<Material> LEATHER;
    public static final ResourceKey<Material> LEATHER_KEY = createMaterialKey(modLoc("leather"));
    public static Holder.Reference<Material> RABBIT_LEATHER;
    public static final ResourceKey<Material> RABBIT_LEATHER_KEY = createMaterialKey(modLoc("rabbit_hide"));
    public static Holder.Reference<Material> WOOD_PLANK;
    public static final ResourceKey<Material> WOOD_PLANK_KEY = createMaterialKey(modLoc("wood_plank"));
    public static Holder.Reference<Material> STONE;
    public static final ResourceKey<Material> STONE_KEY = createMaterialKey(modLoc("stone"));
    public static Holder.Reference<Material> IRON;
    public static final ResourceKey<Material> IRON_KEY = createMaterialKey(modLoc("iron"));
    public static Holder.Reference<Material> GOLD;
    public static final ResourceKey<Material> GOLD_KEY = createMaterialKey(modLoc("gold"));
    public static Holder.Reference<Material> DIAMOND;
    public static final ResourceKey<Material> DIAMOND_KEY = createMaterialKey(modLoc("diamond"));
    public static Holder.Reference<Material> NETHERITE;
    public static final ResourceKey<Material> NETHERITE_KEY = createMaterialKey(modLoc("netherite"));
    public static Holder.Reference<Material> HEART_STEALER;
    public static final ResourceKey<Material> HEART_STEALER_KEY = createMaterialKey(modLoc("heart_stealer"));
    public static Holder.Reference<Material> HEAT;
    public static final ResourceKey<Material> HEAT_KEY = createMaterialKey(modLoc("heat"));
    public static Holder.Reference<Material> FROST;
    public static final ResourceKey<Material> FROST_KEY = createMaterialKey(modLoc("frost"));
    public static Holder.Reference<Material> VOID_TOUCHED;
    public static final ResourceKey<Material> VOID_TOUCHED_KEY = createMaterialKey(modLoc("void_touched"));
    public static Holder.Reference<Material> SOUL;
    public static final ResourceKey<Material> SOUL_KEY = createMaterialKey(modLoc("soul"));
    public static Holder.Reference<Material> FIGHTERS;
    public static final ResourceKey<Material> FIGHTERS_KEY = createMaterialKey(modLoc("fighters"));
    public static Holder.Reference<Material> BERSERK;
    public static final ResourceKey<Material> BERSERK_KEY = createMaterialKey(modLoc("berserk"));
    public static Holder.Reference<Material> BRAWLERS;
    public static final ResourceKey<Material> BRAWLERS_KEY = createMaterialKey(modLoc("brawlers"));


    public static final RegistrySetBuilder registrySetBuilder = new RegistrySetBuilder()
            .add(Registries.MATERIAL_REGISTRY_KEY, bootstrap -> {
                VANILLA = bootstrap.register(VANILLA_KEY, new Material(
                        Material.Durabilities.shieldGauntlet(0, 0),
                        new Material.EnchantingRelated(0, 0, 0),
                        new Material.Offense(0, 0, 0.05, false, true, 1, 0),
                        new Material.Defense(PlacementInShield.NOT_TRIM, false, false, 0, 0, 0, 2.5f, 0.3f),
                        Ingredient.of(Items.AIR)
                ));
                LEATHER = bootstrap.register(LEATHER_KEY, new Material(
                        Material.Durabilities.shieldGauntlet(131, 80),
                        new Material.EnchantingRelated(5, 15, 0),
                        new Material.Offense(1, 0, 0.05, false, true, 1, 2),
                        new Material.Defense(PlacementInShield.NOT_TRIM, false, false, 1, 0, 0, 2.75f, 0.45f),
                        Ingredient.of(Items.LEATHER)
                ));
                RABBIT_LEATHER = bootstrap.register(RABBIT_LEATHER_KEY, new Material(
                        Material.Durabilities.shieldGauntlet(110, 75),
                        new Material.EnchantingRelated(5, 15, 0),
                        new Material.Offense(1, 0, 0.05, false, true, 1, 3),
                        new Material.Defense(PlacementInShield.NOT_TRIM, false, false, 1, 0, 0, 2.65f, 0.5f),
                        Ingredient.of(Items.RABBIT_HIDE)
                ));
                WOOD_PLANK = bootstrap.register(WOOD_PLANK_KEY, new Material(
                        new Material.Durabilities(59, 0, 0, 40),
                        new Material.EnchantingRelated(15, 0, 0),
                        new Material.Offense(0, 0, 0.05, false, true, 1, 0),
                        new Material.Defense(PlacementInShield.ALL, false, false, 0, 0, 0, 2.5f, 0.3f),
                        Ingredient.of(ItemTags.PLANKS)
                ));
                STONE = bootstrap.register(STONE_KEY, new Material(
                        new Material.Durabilities(131, 0, 0, 0),
                        new Material.EnchantingRelated(5, 0, 0),
                        new Material.Offense(1, 0, 0.05, false, true, 1, 0),
                        new Material.Defense(PlacementInShield.NONE, false, false, 0, 0, 0, 0f, 0f),
                        Ingredient.of(ItemTags.STONE_TOOL_MATERIALS)
                ));
                IRON = bootstrap.register(IRON_KEY, new Material(
                        new Material.Durabilities(250, 250, 480, 150),
                        new Material.EnchantingRelated(14, 9, 0),
                        new Material.Offense(2, 3, 0.045, false, true, 1.15f, 4),
                        new Material.Defense(PlacementInShield.ALL, false, false, 2, 0, 0, 3f, 0.6f),
                        Ingredient.of(Items.IRON_INGOT)
                ));
                GOLD = bootstrap.register(GOLD_KEY, new Material(
                        new Material.Durabilities(32, 91, 395, 40),
                        new Material.EnchantingRelated(22, 25, 2),
                        new Material.Offense(0, 4, 0.07, false, true, 1.1f, 6),
                        new Material.Defense(PlacementInShield.ALL, false, true, 1, 0, 0, 3f, 0.4f),
                        Ingredient.of(Items.GOLD_INGOT)
                ));
                DIAMOND = bootstrap.register(DIAMOND_KEY, new Material(
                        new Material.Durabilities(1561, 1561, 672, 300),
                        new Material.EnchantingRelated(10, 10, -0.1f),
                        new Material.Offense(3, 3.75f, 0.04, false, true, 1.3f, 8),
                        new Material.Defense(PlacementInShield.ALL, false, false, 3, 2, 0, 5f, 0.75f),
                        Ingredient.of(Items.DIAMOND)
                ));
                NETHERITE = bootstrap.register(NETHERITE_KEY, new Material(
                        new Material.Durabilities(2031, 2031, 768, 375),
                        new Material.EnchantingRelated(15, 15, 0.2f),
                        new Material.Offense(4, 4.5f, 0.05, false, true, 1.45f, 10),
                        new Material.Defense(PlacementInShield.ALL, true, false, 3, 3, 0.1, 6f, 0.85f),
                        Ingredient.of(Items.NETHERITE_INGOT),
                        true, Optional.of(List.of(DIAMOND.key().location())),
                        Ingredient.of(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE)
                ));
                HEART_STEALER = bootstrap.register(HEART_STEALER_KEY, new Material(
                        new Material.Durabilities(2031, 0, 0, 0),
                        new Material.EnchantingRelated(15, 15, 0f),
                        new Material.Offense(4.5, 0f, 0.05, false, true, 1, 0),
                        new Material.Defense(PlacementInShield.NONE, true, false, 0, 0, 0, 0f, 0f),
                        Ingredient.of(Items.AIR)
                ));
                HEAT = bootstrap.register(HEAT_KEY, new Material(
                        new Material.Durabilities(2031, 0, 0, 0),
                        new Material.EnchantingRelated(15, 0, 0f),
                        new Material.Offense(4, 0f, 0.05, false, true, 1, 0),
                        new Material.Defense(PlacementInShield.NONE, true, false, 0, 0, 0, 0f, 0f),
                        Ingredient.of(Items.AIR)
                ));
                FROST = bootstrap.register(FROST_KEY, new Material(
                        new Material.Durabilities(1561, 0, 0, 0),
                        new Material.EnchantingRelated(10, 0, 0f),
                        new Material.Offense(3, 0f, 0.05, false, true, 1f, 0),
                        new Material.Defense(PlacementInShield.NONE, false, false, 0, 0, 0, 0f, 0f),
                        Ingredient.of(Items.AIR)
                ));
                VOID_TOUCHED = bootstrap.register(VOID_TOUCHED_KEY, new Material(
                        new Material.Durabilities(2031, 0, 0, 0),
                        new Material.EnchantingRelated(15, 0, 0f),
                        new Material.Offense(4, 0f, 0.05, false, true, 1, 0),
                        new Material.Defense(PlacementInShield.NONE, false, false, 0, 0, 0, 0f, 0f),
                        Ingredient.of(Items.AIR)
                ));
                SOUL = bootstrap.register(SOUL_KEY, new Material(
                        new Material.Durabilities(2031, 2031, 0, 0),
                        new Material.EnchantingRelated(15, 15, 0f),
                        new Material.Offense(4, 0f, 0.05, false, true, 1, 0),
                        new Material.Defense(PlacementInShield.NONE, false, false, 3, 3, 0, 0f, 0f),
                        Ingredient.of(ECItems.GOOD_SOUL, ECItems.BAD_SOUL)
                ));
                FIGHTERS = bootstrap.register(FIGHTERS_KEY, new Material(
                        new Material.Durabilities(0, 1561, 0, 0),
                        new Material.EnchantingRelated(10, 10, 0f),
                        new Material.Offense(3.5, 0f, 0.05, false, true, 1f, 0),
                        new Material.Defense(PlacementInShield.NONE, false, false, 3, 3, 0.05, 0f, 0f),
                        Ingredient.of(Items.AIR)
                ));
                BERSERK = bootstrap.register(BERSERK_KEY, new Material(
                        new Material.Durabilities(0, 1561, 0, 0),
                        new Material.EnchantingRelated(10, 10, 0f),
                        new Material.Offense(3.5, 0f, 0.05, false, true, 1f, 0),
                        new Material.Defense(PlacementInShield.NONE, false, false, 3, 3, 0.05, 0f, 0f),
                        Ingredient.of(Items.AIR)
                ));
                BRAWLERS = bootstrap.register(BRAWLERS_KEY, new Material(
                        new Material.Durabilities(0, 2031, 0, 0),
                        new Material.EnchantingRelated(15, 15, 0f),
                        new Material.Offense(4, 0f, 0.05, false, true, 1, 0),
                        new Material.Defense(PlacementInShield.NONE, false, false, 3, 3, 0, 0f, 0f),
                        Ingredient.of(Items.AIR)
                ));
            });

    public static ResourceKey<Material> createMaterialKey(ResourceLocation id) {
        return ResourceKey.create(
                Registries.MATERIAL_REGISTRY_KEY,
                id
        );
    }
}
