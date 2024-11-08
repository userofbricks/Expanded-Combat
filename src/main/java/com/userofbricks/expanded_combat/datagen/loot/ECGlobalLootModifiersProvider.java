package com.userofbricks.expanded_combat.datagen.loot;

import com.userofbricks.expanded_combat.init.ECItems;
import com.userofbricks.expanded_combat.item.recipes.conditions.ECConfigBooleanCondition;
import com.userofbricks.expanded_combat.loot.AddItemFromGauntletModifier;
import com.userofbricks.expanded_combat.loot.AddItemModifier;
import com.userofbricks.expanded_combat.loot.AddItemWithoutGauntletModifier;
import net.minecraft.advancements.critereon.EntityEquipmentPredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.predicates.*;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;
import net.neoforged.neoforge.common.loot.LootTableIdCondition;
import net.neoforged.neoforge.registries.DeferredItem;

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
        generateCustomWeaponLootModifiers();
    }

    private void generateCustomWeaponLootModifiers() {
        for (DeferredItem<?> entry : Arrays.asList(VOID_TOUCHED_CLAYMORE, VOID_TOUCHED_CUTLASS, VOID_TOUCHED_DAGGER, VOID_TOUCHED_GREAT_HAMMER)) {
            add("end_city_treasure_void_" + entry.getId().getPath(), new AddItemModifier(new LootItemCondition[]{
                    new LootTableIdCondition.Builder(ResourceLocation.parse("chests/" + "end_city_treasure")).build(),
                    LootItemRandomChanceCondition.randomChance(0.05f).build()
            }, entry.get()), new ECConfigBooleanCondition("weapons"));
        }

        for (String chestLoot : Arrays.asList("shipwreck_treasure", "underwater_ruin_big", "woodland_mansion", "buried_treasure")) {
            for (DeferredItem<?> entry : Arrays.asList(FROST_CLAYMORE, FROST_DAGGER, FROST_SCYTHE)) {
                add(chestLoot + "_cold_" + entry.getId().getPath(), new AddItemModifier(new LootItemCondition[]{
                        new LootTableIdCondition.Builder(ResourceLocation.parse("chests/" + chestLoot)).build(),
                        LootItemRandomChanceCondition.randomChance(0.05f).build()
                }, entry.get()), new ECConfigBooleanCondition("weapons"));
            }
        }

        for (String chestLoot : Arrays.asList("desert_pyramid", "bastion_treasure", "woodland_mansion", "buried_treasure")) {
            for (DeferredItem<?> entry : Arrays.asList(HEAT_KATANA, HEAT_GLAIVE, HEAT_MACE, HEAT_SCYTHE)) {
                add(chestLoot + "_heat_" + entry.getId().getPath(), new AddItemModifier(new LootItemCondition[]{
                        new LootTableIdCondition.Builder(ResourceLocation.parse("chests/" + chestLoot)).build(),
                        LootItemRandomChanceCondition.randomChance(0.05f).build()
                }, entry.get()), new ECConfigBooleanCondition("weapons"));
            }
        }

        for (String chestLoot : Arrays.asList("ancient_city", "bastion_treasure", "woodland_mansion")) {
            for (DeferredItem<?> entry : Arrays.asList(SOUL_KATANA, SOUL_DAGGER, SOUL_SCYTHE, SOUL_GAUNTLET)) {
                add(chestLoot + "_soul_" + entry.getId().getPath(), new AddItemModifier(new LootItemCondition[]{
                        new LootTableIdCondition.Builder(ResourceLocation.parse("chests/" + chestLoot)).build(),
                        LootItemRandomChanceCondition.randomChance(0.05f).build()
                }, entry.get()), new ECConfigBooleanCondition("weapons"));
            }
        }

        add("bastion_treasure" + "_fighters_gauntlet", new AddItemModifier(new LootItemCondition[]{
                new LootTableIdCondition.Builder(ResourceLocation.parse("chests/" + "bastion_treasure")).build(),
                LootItemRandomChanceCondition.randomChance(0.05f).build()
        }, FIGHTERS_GAUNTLETS.get()), new ECConfigBooleanCondition("weapons"));
        add("woodland_mansion" + "_brawlers_gauntlet", new AddItemModifier(new LootItemCondition[]{
                new LootTableIdCondition.Builder(ResourceLocation.parse("chests/" + "woodland_mansion")).build(),
                LootItemRandomChanceCondition.randomChance(0.05f).build()
        }, BRAWLERS_GAUNTLETS.get()), new ECConfigBooleanCondition("weapons"));
        add("pillager_outpost" + "_berserk_gauntlet", new AddItemModifier(new LootItemCondition[]{
                new LootTableIdCondition.Builder(ResourceLocation.parse("chests/" + "pillager_outpost")).build(),
                LootItemRandomChanceCondition.randomChance(0.05f).build()
        }, BERSERK_GAUNTLETS.get()), new ECConfigBooleanCondition("weapons"));
    }

    private void generateSoulLootModifiers() {
        AllOfCondition.Builder soulWeaponChance = AllOfCondition.allOf(
                LootItemEntityPropertyCondition.hasProperties(ATTACKER,
                        new EntityPredicate.Builder().equipment(
                                new EntityEquipmentPredicate.Builder().mainhand(
                                        ItemPredicate.Builder.item().of(registries.lookupOrThrow(Registries.ITEM), SOUL_KATANA, SOUL_DAGGER, SOUL_SCYTHE)
                                ).build()
                        )
                ), LootItemRandomChanceCondition.randomChance(0.2f));


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
