package com.userofbricks.expanded_combat.datagen.loot;

import com.userofbricks.expanded_combat.init.LootTableKeys;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import java.util.function.BiConsumer;

import static com.userofbricks.expanded_combat.init.ECItems.*;

public record ECChestLoot(HolderLookup.Provider registries) implements LootTableSubProvider {
    @Override
    public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> biConsumer) {
        HolderLookup.RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
        biConsumer.accept(LootTableKeys.BASTION_TREASURE, LootTable.lootTable()
                .withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1))
                                .add(LootItem.lootTableItem(HEAT_KATANA).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(HEAT_GLAIVE).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(HEAT_MACE).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(HEAT_SCYTHE).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(SOUL_KATANA).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(SOUL_DAGGER).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(SOUL_SCYTHE).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(SOUL_GAUNTLET).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(FIGHTERS_GAUNTLETS).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
                ));
        biConsumer.accept(LootTableKeys.END_CITY_TREASURE, LootTable.lootTable()
                .withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1))
                                .add(LootItem.lootTableItem(VOID_TOUCHED_CLAYMORE).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(VOID_TOUCHED_CUTLASS).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(VOID_TOUCHED_DAGGER).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(VOID_TOUCHED_GREAT_HAMMER).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(SOUL_KATANA).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(SOUL_DAGGER).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(SOUL_SCYTHE).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(SOUL_GAUNTLET).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
                ));
        biConsumer.accept(LootTableKeys.SHIPWRECK_TREASURE, LootTable.lootTable()
                .withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1))
                                .add(LootItem.lootTableItem(FROST_CLAYMORE).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(FROST_DAGGER).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(FROST_SCYTHE).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
                ));
        biConsumer.accept(LootTableKeys.UNDERWATER_RUIN_BIG, LootTable.lootTable()
                .withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1))
                                .add(LootItem.lootTableItem(FROST_CLAYMORE).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(FROST_DAGGER).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(FROST_SCYTHE).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
                ));
        biConsumer.accept(LootTableKeys.WOODLAND_MANSION, LootTable.lootTable()
                .withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1))
                                .add(LootItem.lootTableItem(FROST_CLAYMORE).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(FROST_DAGGER).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(FROST_SCYTHE).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(HEAT_KATANA).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(HEAT_GLAIVE).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(HEAT_MACE).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(HEAT_SCYTHE).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(SOUL_KATANA).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(SOUL_DAGGER).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(SOUL_SCYTHE).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(SOUL_GAUNTLET).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(BRAWLERS_GAUNTLETS).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
                ));
        biConsumer.accept(LootTableKeys.BURIED_TREASURE, LootTable.lootTable()
                .withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1))
                                .add(LootItem.lootTableItem(FROST_CLAYMORE).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(FROST_DAGGER).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(FROST_SCYTHE).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(HEAT_KATANA).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(HEAT_GLAIVE).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(HEAT_MACE).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(HEAT_SCYTHE).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
                ));
        biConsumer.accept(LootTableKeys.DESERT_PYRAMID, LootTable.lootTable()
                .withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1))
                                .add(LootItem.lootTableItem(HEAT_KATANA).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(HEAT_GLAIVE).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(HEAT_MACE).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(HEAT_SCYTHE).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
                ));
        biConsumer.accept(LootTableKeys.PILLAGER_OUTPOST, LootTable.lootTable()
                .withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1))
                                .add(LootItem.lootTableItem(BERSERK_GAUNTLETS).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
                ));
    }
}
