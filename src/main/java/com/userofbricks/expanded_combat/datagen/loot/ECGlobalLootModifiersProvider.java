package com.userofbricks.expanded_combat.datagen.loot;

import com.userofbricks.expanded_combat.init.ECItems;
import com.userofbricks.expanded_combat.loot.AddItemFromGauntletModifier;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.common.loot.LootTableIdCondition;

import java.util.Arrays;
import java.util.List;

import static com.userofbricks.expanded_combat.ExpandedCombat.MODID;

public class ECGlobalLootModifiersProvider extends GlobalLootModifierProvider {
    public ECGlobalLootModifiersProvider(PackOutput output) {
        super(output, MODID);
    }

    @Override
    protected void start() {
        for (String mob : Arrays.asList("blaze", "cave_spider", "creeper", "ender_dragon", "endermite", "evoker", "guardian", "hoglin", "illusioner", "magma_cube", "piglin", "piglin_brute", "pillager", "ravager", "silverfish", "slime", "spider", "vindicator", "witch", "wither_skeleton")) {
            add("bad_soul_from_mob", new AddItemFromGauntletModifier(new LootItemCondition[]{
                    new LootTableIdCondition.Builder(new ResourceLocation("entities/" + mob)).build(),
                    LootItemRandomChanceCondition.randomChance(0.01f).build()
            }, ECItems.BAD_SOUL.get(), ECItems.SOUL_FIST_GAUNTLETS.get()));
        }
        for (String mob : Arrays.asList("drowned", "enderman", "ghast", "giant", "husk", "phantom", "shulker", "skeleton", "skeleton_horse", "stray", "vex", "zoglin", "zombie", "zombie_horse", "zombie_villager", "zombified_piglin")) {
            add("good_soul_from_mob", new AddItemFromGauntletModifier(new LootItemCondition[]{
                    new LootTableIdCondition.Builder(new ResourceLocation("entities/" + mob)).build(),
                    LootItemRandomChanceCondition.randomChance(0.01f).build()
            }, ECItems.GOOD_SOUL.get(), ECItems.SOUL_FIST_GAUNTLETS.get()));
        }


        add("bad_soul_from_wither", new AddItemFromGauntletModifier(new LootItemCondition[]{
                new LootTableIdCondition.Builder(new ResourceLocation("entities/wither")).build(),
                LootItemRandomChanceCondition.randomChance(0.1f).build()
        }, ECItems.BAD_SOUL.get(), ECItems.SOUL_FIST_GAUNTLETS.get()));
        add("good_soul_from_wither", new AddItemFromGauntletModifier(new LootItemCondition[]{
                new LootTableIdCondition.Builder(new ResourceLocation("entities/wither")).build(),
                LootItemRandomChanceCondition.randomChance(0.1f).build()
        }, ECItems.GOOD_SOUL.get(), ECItems.SOUL_FIST_GAUNTLETS.get()));


        add("bad_soul_from_warden", new AddItemFromGauntletModifier(new LootItemCondition[]{
                new LootTableIdCondition.Builder(new ResourceLocation("entities/warden")).build(),
                LootItemRandomChanceCondition.randomChance(0.1f).build()
        }, ECItems.BAD_SOUL.get(), ECItems.SOUL_FIST_GAUNTLETS.get()));
        add("good_soul_from_warden", new AddItemFromGauntletModifier(new LootItemCondition[]{
                new LootTableIdCondition.Builder(new ResourceLocation("entities/warden")).build(),
                LootItemRandomChanceCondition.randomChance(0.1f).build()
        }, ECItems.GOOD_SOUL.get(), ECItems.SOUL_FIST_GAUNTLETS.get()));


        add("bad_soul_from_elder_guardian", new AddItemFromGauntletModifier(new LootItemCondition[]{
                new LootTableIdCondition.Builder(new ResourceLocation("entities/elder_guardian")).build(),
                LootItemRandomChanceCondition.randomChance(0.1f).build()
        }, ECItems.BAD_SOUL.get(), ECItems.SOUL_FIST_GAUNTLETS.get()));
    }
}
