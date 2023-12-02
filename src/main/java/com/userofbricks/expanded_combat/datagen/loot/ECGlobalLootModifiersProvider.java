package com.userofbricks.expanded_combat.datagen.loot;

import com.tterrag.registrate.util.entry.RegistryEntry;
import com.userofbricks.expanded_combat.init.ECItems;
import com.userofbricks.expanded_combat.loot.AddItemFromGauntletModifier;
import com.userofbricks.expanded_combat.loot.AddItemModifier;
import com.userofbricks.expanded_combat.loot.AddItemWithoutGauntletModifier;
import com.userofbricks.expanded_combat.plugins.CustomWeaponsPlugin;
import net.minecraft.advancements.critereon.*;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.predicates.*;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.common.loot.LootTableIdCondition;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.userofbricks.expanded_combat.ExpandedCombat.MODID;
import static net.minecraft.world.level.storage.loot.LootContext.EntityTarget.KILLER;

public class ECGlobalLootModifiersProvider extends GlobalLootModifierProvider {
    public ECGlobalLootModifiersProvider(PackOutput output) {
        super(output, MODID);
    }

    @Override
    protected void start() {
        generateSoulLootModifiers();
        generateCustomWeaponLootModifiers();
    }

    private void generateCustomWeaponLootModifiers() {
        for (Map.Entry<String, RegistryEntry<? extends Item>> entry : CustomWeaponsPlugin.VOID_MATERIAL.getWeapons().entrySet()) {
            add("end_city_treasure_void_" + entry.getKey().toLowerCase().replace(" ", "_"), new AddItemModifier(new LootItemCondition[]{
                    new LootTableIdCondition.Builder(new ResourceLocation("chests/" + "end_city_treasure")).build(),
                    LootItemRandomChanceCondition.randomChance(0.05f).build()
            }, entry.getValue().get()));
        }

        for (String chestLoot : Arrays.asList("shipwreck_treasure", "underwater_ruin_big", "woodland_mansion", "buried_treasure")) {
            for (Map.Entry<String, RegistryEntry<? extends Item>> entry : CustomWeaponsPlugin.COLD_MATERIAL.getWeapons().entrySet()) {
                add(chestLoot + "_cold_" + entry.getKey().toLowerCase().replace(" ", "_"), new AddItemModifier(new LootItemCondition[]{
                        new LootTableIdCondition.Builder(new ResourceLocation("chests/" + chestLoot)).build(),
                        LootItemRandomChanceCondition.randomChance(0.05f).build()
                }, entry.getValue().get()));
            }
        }

        for (String chestLoot : Arrays.asList("desert_pyramid", "bastion_treasure", "woodland_mansion", "buried_treasure")) {
            for (Map.Entry<String, RegistryEntry<? extends Item>> entry : CustomWeaponsPlugin.HEAT_MATERIAL.getWeapons().entrySet()) {
                add(chestLoot + "_heat_" + entry.getKey().toLowerCase().replace(" ", "_"), new AddItemModifier(new LootItemCondition[]{
                        new LootTableIdCondition.Builder(new ResourceLocation("chests/" + chestLoot)).build(),
                        LootItemRandomChanceCondition.randomChance(0.05f).build()
                }, entry.getValue().get()));
            }
        }

        for (String chestLoot : Arrays.asList("ancient_city", "bastion_treasure", "woodland_mansion")) {
            for (Map.Entry<String, RegistryEntry<? extends Item>> entry : CustomWeaponsPlugin.SOUL_MATERIAL.getWeapons().entrySet()) {
                add(chestLoot + "_soul_" + entry.getKey().toLowerCase().replace(" ", "_"), new AddItemModifier(new LootItemCondition[]{
                        new LootTableIdCondition.Builder(new ResourceLocation("chests/" + chestLoot)).build(),
                        LootItemRandomChanceCondition.randomChance(0.05f).build()
                }, entry.getValue().get()));
            }
        }

        add("bastion_treasure" + "_fighters_gauntlet", new AddItemModifier(new LootItemCondition[]{
                new LootTableIdCondition.Builder(new ResourceLocation("chests/" + "bastion_treasure")).build(),
                LootItemRandomChanceCondition.randomChance(0.05f).build()
        }, CustomWeaponsPlugin.FIGHTER.getGauntletEntry().get()));
        add("woodland_mansion" + "_gauntlet", new AddItemModifier(new LootItemCondition[]{
                new LootTableIdCondition.Builder(new ResourceLocation("chests/" + "woodland_mansion")).build(),
                LootItemRandomChanceCondition.randomChance(0.05f).build()
        }, CustomWeaponsPlugin.GAUNTLET.getGauntletEntry().get()));
        add("pillager_outpost" + "_soul_gauntlet", new AddItemModifier(new LootItemCondition[]{
                new LootTableIdCondition.Builder(new ResourceLocation("chests/" + "pillager_outpost")).build(),
                LootItemRandomChanceCondition.randomChance(0.05f).build()
        }, CustomWeaponsPlugin.MAULERS.getGauntletEntry().get()));
        add("ancient_city" + "_soul_gauntlet", new AddItemModifier(new LootItemCondition[]{
                new LootTableIdCondition.Builder(new ResourceLocation("chests/" + "ancient_city")).build(),
                LootItemRandomChanceCondition.randomChance(0.05f).build()
        }, CustomWeaponsPlugin.SOUL_MATERIAL.getGauntletEntry().get()));
    }

    private void generateSoulLootModifiers() {
        AllOfCondition.Builder soulWeaponChance = AllOfCondition.allOf(
                LootItemEntityPropertyCondition.hasProperties(KILLER, new EntityPredicate.Builder().equipment(new EntityEquipmentPredicate.Builder().mainhand(new ItemPredicate(null,
                        CustomWeaponsPlugin.SOUL_MATERIAL.getWeapons().values().stream().map(RegistryEntry::get).collect(Collectors.toSet()), MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY,
                        EnchantmentPredicate.NONE, EnchantmentPredicate.NONE, null, NbtPredicate.ANY)).build()).build()),
                LootItemRandomChanceCondition.randomChance(0.2f)
        );


        for (String mob : Arrays.asList("blaze", "cave_spider", "creeper", "ender_dragon", "endermite", "evoker", "guardian", "hoglin", "illusioner", "magma_cube", "piglin", "piglin_brute", "pillager", "ravager", "silverfish", "slime", "spider", "vindicator", "witch", "wither_skeleton")) {
            add("bad_soul_from_" + mob + "_with_soul_gauntlet", new AddItemFromGauntletModifier(new LootItemCondition[]{
                    new LootTableIdCondition.Builder(new ResourceLocation("entities/" + mob)).build(),
                    AnyOfCondition.anyOf(LootItemRandomChanceCondition.randomChance(0.01f), soulWeaponChance).build()
            }, ECItems.BAD_SOUL.get(), CustomWeaponsPlugin.SOUL_MATERIAL.getGauntletEntry().get()));

            add("bad_soul_from_" + mob + "_without_soul_gauntlet", new AddItemWithoutGauntletModifier(new LootItemCondition[]{
                    new LootTableIdCondition.Builder(new ResourceLocation("entities/" + mob)).build(),
                    soulWeaponChance.build()
            }, ECItems.BAD_SOUL.get(), CustomWeaponsPlugin.SOUL_MATERIAL.getGauntletEntry().get()));
        }
        for (String mob : Arrays.asList("drowned", "enderman", "ghast", "giant", "husk", "phantom", "shulker", "skeleton", "skeleton_horse", "stray", "vex", "zoglin", "zombie", "zombie_horse", "zombie_villager", "zombified_piglin")) {
            add("good_soul_from_" + mob + "_with_soul_gauntlet", new AddItemFromGauntletModifier(new LootItemCondition[]{
                    new LootTableIdCondition.Builder(new ResourceLocation("entities/" + mob)).build(),
                    AnyOfCondition.anyOf(LootItemRandomChanceCondition.randomChance(0.01f), soulWeaponChance).build()
            }, ECItems.GOOD_SOUL.get(), CustomWeaponsPlugin.SOUL_MATERIAL.getGauntletEntry().get()));

            add("good_soul_from_" + mob + "_without_soul_gauntlet", new AddItemWithoutGauntletModifier(new LootItemCondition[]{
                    new LootTableIdCondition.Builder(new ResourceLocation("entities/" + mob)).build(),
                    soulWeaponChance.build()
            }, ECItems.GOOD_SOUL.get(), CustomWeaponsPlugin.SOUL_MATERIAL.getGauntletEntry().get()));
        }


        for (String mob : Arrays.asList("wither", "warden", "elder_guardian")) {
            add("bad_soul_from_" + mob + "_with_soul_gauntlet", new AddItemFromGauntletModifier(new LootItemCondition[]{
                    new LootTableIdCondition.Builder(new ResourceLocation("entities/" + mob)).build(),
                    AnyOfCondition.anyOf(LootItemRandomChanceCondition.randomChance(0.1f), soulWeaponChance).build()
            }, ECItems.BAD_SOUL.get(), CustomWeaponsPlugin.SOUL_MATERIAL.getGauntletEntry().get()));

            add("bad_soul_from_" + mob + "_without_soul_gauntlet", new AddItemWithoutGauntletModifier(new LootItemCondition[]{
                    new LootTableIdCondition.Builder(new ResourceLocation("entities/" + mob)).build(),
                    soulWeaponChance.build()
            }, ECItems.BAD_SOUL.get(), CustomWeaponsPlugin.SOUL_MATERIAL.getGauntletEntry().get()));
        }

        for (String mob : Arrays.asList("wither", "warden")) {
            add("good_soul_from_" + mob + "_with_soul_gauntlet", new AddItemFromGauntletModifier(new LootItemCondition[]{
                    new LootTableIdCondition.Builder(new ResourceLocation("entities/" + mob)).build(),
                    AnyOfCondition.anyOf(LootItemRandomChanceCondition.randomChance(0.1f), soulWeaponChance).build()
            }, ECItems.GOOD_SOUL.get(), CustomWeaponsPlugin.SOUL_MATERIAL.getGauntletEntry().get()));

            add("good_soul_from_" + mob + "_without_soul_gauntlet", new AddItemWithoutGauntletModifier(new LootItemCondition[]{
                    new LootTableIdCondition.Builder(new ResourceLocation("entities/" + mob)).build(),
                    soulWeaponChance.build()
            }, ECItems.GOOD_SOUL.get(), CustomWeaponsPlugin.SOUL_MATERIAL.getGauntletEntry().get()));
        }
    }
}
