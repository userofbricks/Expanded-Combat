package com.userofbricks.expanded_combat.datagen.loot;

import com.userofbricks.expanded_combat.init.ECItems;
import com.userofbricks.expanded_combat.init.LootTableKeys;
import com.userofbricks.expanded_combat.item.recipes.conditions.ECConfigBooleanCondition;
import com.userofbricks.expanded_combat.loot.AddItemFromGauntletModifier;
import com.userofbricks.expanded_combat.loot.AddItemModifier;
import com.userofbricks.expanded_combat.loot.AddItemWithoutGauntletModifier;
import net.minecraft.advancements.critereon.EntityEquipmentPredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.predicates.*;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;
import net.neoforged.neoforge.common.loot.AddTableLootModifier;
import net.neoforged.neoforge.common.loot.LootTableIdCondition;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

import static com.userofbricks.expanded_combat.ExpandedCombat.MODID;
import static com.userofbricks.expanded_combat.init.ECItems.*;
import static net.minecraft.world.level.storage.loot.LootContext.EntityTarget.ATTACKER;

public class ECGlobalLootModifiersProvider extends GlobalLootModifierProvider {
    public ECGlobalLootModifiersProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, MODID);
    }

    @Override
    protected void start() {
        generateSoulLootModifiers();

        add("bastion_treasure", new AddTableLootModifier(new LootItemCondition[]{
                new LootTableIdCondition.Builder(BuiltInLootTables.BASTION_TREASURE.location()).build(),
                LootItemRandomChanceCondition.randomChance(0.1f).build()
        }, LootTableKeys.BASTION_TREASURE), new ECConfigBooleanCondition("weapons"));
        add("end_city_treasure", new AddTableLootModifier(new LootItemCondition[]{
                new LootTableIdCondition.Builder(BuiltInLootTables.END_CITY_TREASURE.location()).build(),
                LootItemRandomChanceCondition.randomChance(0.1f).build()
        }, LootTableKeys.END_CITY_TREASURE), new ECConfigBooleanCondition("weapons"));
        add("shipwreck_treasure", new AddTableLootModifier(new LootItemCondition[]{
                new LootTableIdCondition.Builder(BuiltInLootTables.SHIPWRECK_TREASURE.location()).build(),
                LootItemRandomChanceCondition.randomChance(0.1f).build()
        }, LootTableKeys.SHIPWRECK_TREASURE), new ECConfigBooleanCondition("weapons"));
        add("underwater_ruin_big", new AddTableLootModifier(new LootItemCondition[]{
                new LootTableIdCondition.Builder(BuiltInLootTables.UNDERWATER_RUIN_BIG.location()).build(),
                LootItemRandomChanceCondition.randomChance(0.05f).build()
        }, LootTableKeys.UNDERWATER_RUIN_BIG), new ECConfigBooleanCondition("weapons"));
        add("woodland_mansion", new AddTableLootModifier(new LootItemCondition[]{
                new LootTableIdCondition.Builder(BuiltInLootTables.WOODLAND_MANSION.location()).build(),
                LootItemRandomChanceCondition.randomChance(0.1f).build()
        }, LootTableKeys.WOODLAND_MANSION), new ECConfigBooleanCondition("weapons"));
        add("buried_treasure", new AddTableLootModifier(new LootItemCondition[]{
                new LootTableIdCondition.Builder(BuiltInLootTables.BURIED_TREASURE.location()).build(),
                LootItemRandomChanceCondition.randomChance(0.1f).build()
        }, LootTableKeys.BURIED_TREASURE), new ECConfigBooleanCondition("weapons"));
        add("pillager_outpost", new AddTableLootModifier(new LootItemCondition[]{
                new LootTableIdCondition.Builder(BuiltInLootTables.PILLAGER_OUTPOST.location()).build(),
                LootItemRandomChanceCondition.randomChance(0.05f).build()
        }, LootTableKeys.PILLAGER_OUTPOST), new ECConfigBooleanCondition("weapons"));
    }

    private void generateSoulLootModifiers() {
        AllOfCondition.Builder soulWeaponChance = AllOfCondition.allOf(
                LootItemEntityPropertyCondition.hasProperties(ATTACKER,
                        new EntityPredicate.Builder().equipment(
                                new EntityEquipmentPredicate.Builder().mainhand(
                                        ItemPredicate.Builder.item().of(SOUL_KATANA, SOUL_DAGGER, SOUL_SCYTHE)
                                ).build()
                        )
                ), LootItemRandomChanceCondition.randomChance(0.2f));


        //TODO: update to use the new loot table adder instead.
        for (String mob : Arrays.asList("blaze", "cave_spider", "creeper", "ender_dragon", "endermite", "evoker", "guardian", "hoglin", "illusioner", "magma_cube", "piglin", "piglin_brute", "pillager", "ravager", "silverfish", "slime", "spider", "vindicator", "witch", "wither_skeleton")) {
            add("bad_soul_from_" + mob + "_with_soul_gauntlet", new AddItemFromGauntletModifier(new LootItemCondition[]{
                    new LootTableIdCondition.Builder(ResourceLocation.parse("entities/" + mob)).build(),
                    AnyOfCondition.anyOf(LootItemRandomChanceCondition.randomChance(0.01f), soulWeaponChance).build()
            }, ECItems.BAD_SOUL.get(), SOUL_GAUNTLET.get()), new ECConfigBooleanCondition("soul"));

            add("bad_soul_from_" + mob + "_without_soul_gauntlet", new AddItemWithoutGauntletModifier(new LootItemCondition[]{
                    new LootTableIdCondition.Builder(ResourceLocation.parse("entities/" + mob)).build(),
                    soulWeaponChance.build()
            }, ECItems.BAD_SOUL.get(), SOUL_GAUNTLET.get()), new ECConfigBooleanCondition("soul"));
        }
        for (String mob : Arrays.asList("drowned", "enderman", "ghast", "giant", "husk", "phantom", "shulker", "skeleton", "skeleton_horse", "stray", "vex", "zoglin", "zombie", "zombie_horse", "zombie_villager", "zombified_piglin")) {
            add("good_soul_from_" + mob + "_with_soul_gauntlet", new AddItemFromGauntletModifier(new LootItemCondition[]{
                    new LootTableIdCondition.Builder(ResourceLocation.parse("entities/" + mob)).build(),
                    AnyOfCondition.anyOf(LootItemRandomChanceCondition.randomChance(0.01f), soulWeaponChance).build()
            }, ECItems.GOOD_SOUL.get(), SOUL_GAUNTLET.get()), new ECConfigBooleanCondition("soul"));

            add("good_soul_from_" + mob + "_without_soul_gauntlet", new AddItemWithoutGauntletModifier(new LootItemCondition[]{
                    new LootTableIdCondition.Builder(ResourceLocation.parse("entities/" + mob)).build(),
                    soulWeaponChance.build()
            }, ECItems.GOOD_SOUL.get(), SOUL_GAUNTLET.get()), new ECConfigBooleanCondition("soul"));
        }


        for (String mob : Arrays.asList("wither", "warden", "elder_guardian")) {
            add("bad_soul_from_" + mob + "_with_soul_gauntlet", new AddItemFromGauntletModifier(new LootItemCondition[]{
                    new LootTableIdCondition.Builder(ResourceLocation.parse("entities/" + mob)).build(),
                    AnyOfCondition.anyOf(LootItemRandomChanceCondition.randomChance(0.1f), soulWeaponChance).build()
            }, ECItems.BAD_SOUL.get(), SOUL_GAUNTLET.get()), new ECConfigBooleanCondition("soul"));

            add("bad_soul_from_" + mob + "_without_soul_gauntlet", new AddItemWithoutGauntletModifier(new LootItemCondition[]{
                    new LootTableIdCondition.Builder(ResourceLocation.parse("entities/" + mob)).build(),
                    soulWeaponChance.build()
            }, ECItems.BAD_SOUL.get(), SOUL_GAUNTLET.get()), new ECConfigBooleanCondition("soul"));
        }

        for (String mob : Arrays.asList("wither", "warden")) {
            add("good_soul_from_" + mob + "_with_soul_gauntlet", new AddItemFromGauntletModifier(new LootItemCondition[]{
                    new LootTableIdCondition.Builder(ResourceLocation.parse("entities/" + mob)).build(),
                    AnyOfCondition.anyOf(LootItemRandomChanceCondition.randomChance(0.1f), soulWeaponChance).build()
            }, ECItems.GOOD_SOUL.get(), SOUL_GAUNTLET.get()), new ECConfigBooleanCondition("soul"));

            add("good_soul_from_" + mob + "_without_soul_gauntlet", new AddItemWithoutGauntletModifier(new LootItemCondition[]{
                    new LootTableIdCondition.Builder(ResourceLocation.parse("entities/" + mob)).build(),
                    soulWeaponChance.build()
            }, ECItems.GOOD_SOUL.get(), SOUL_GAUNTLET.get()), new ECConfigBooleanCondition("soul"));
        }
    }
}
