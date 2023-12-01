package com.userofbricks.expanded_combat.init;

import com.tterrag.registrate.util.entry.RegistryEntry;
import com.userofbricks.expanded_combat.block.AbstractGasBlock;
import com.userofbricks.expanded_combat.block.PurifiedGasBlock;
import com.userofbricks.expanded_combat.block.WeaponDisplayBlock;
import com.userofbricks.expanded_combat.block.WeaponDisplayPart;
import com.userofbricks.expanded_combat.plugins.CustomWeaponsPlugin;
import com.userofbricks.expanded_combat.plugins.VanillaECPlugin;
import net.minecraft.advancements.critereon.EnchantmentPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.EmptyLootItem;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import javax.annotation.ParametersAreNonnullByDefault;

import static com.userofbricks.expanded_combat.ExpandedCombat.REGISTRATE;

@ParametersAreNonnullByDefault
public class ECBlocks {
    public static final RegistryEntry<PurifiedGasBlock> PURIFIED_GAS_BLOCK = REGISTRATE.get().block("purified_gas", PurifiedGasBlock::new)
            .blockstate((ctx, prov) -> {})
            .register();
    public static final RegistryEntry<? extends AbstractGasBlock> GAS_BLOCK = REGISTRATE.get().block("gas", properties -> new AbstractGasBlock(properties, () -> ParticleTypes.MYCELIUM) {
        @Override
        public boolean blockCatalistItemConversions(BlockState thisState, BlockState catalistState, ServerLevel world, BlockPos thisPos, BlockPos catalistPos, RandomSource random) {
            return false;
        }
    })
            .blockstate((ctx, prov) -> {})
            .register();
    public static final RegistryEntry<WeaponDisplayBlock> HEART_STEALER_DISPLAY = REGISTRATE.get().block("heartstealer_display", WeaponDisplayBlock::new)
            .simpleItem()
            .properties(properties -> properties.strength(20.0F, 2000.0F))
            .blockstate((ctx, prov) -> prov.horizontalBlock(ctx.get(), state -> state.getValue(WeaponDisplayBlock.PART) == WeaponDisplayPart.RIGHT ? prov.models().getExistingFile(prov.modLoc("block/heartstealer")) : prov.models().getExistingFile(prov.modLoc("block/nothing"))))
            .loot((lootTables, block) -> {
                LootItemCondition.Builder HAS_SILK_TOUCH = MatchTool.toolMatches(ItemPredicate.Builder.item().hasEnchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.Ints.atLeast(1))));
                LootItemCondition.Builder HAS_NO_SILK_TOUCH = HAS_SILK_TOUCH.invert();

                lootTables.add(block,
                    LootTable.lootTable()
                            .withPool(LootPool.lootPool().when(HAS_SILK_TOUCH).setRolls(ConstantValue.exactly(1f)).add(LootItem.lootTableItem(block)))
                            .withPool(LootPool.lootPool().when(HAS_NO_SILK_TOUCH).setRolls(ConstantValue.exactly(1f))
                                    .add(LootItem.lootTableItem(CustomWeaponsPlugin.SOUL_MATERIAL.getWeaponEntry(VanillaECPlugin.KATANA.name()).get()).setWeight(2))
                                    .add(LootItem.lootTableItem(VanillaECPlugin.NETHERITE.getWeaponEntry(VanillaECPlugin.KATANA.name()).get()).setWeight(2))
                                    .add(LootItem.lootTableItem(CustomWeaponsPlugin.SOUL_MATERIAL.getWeaponEntry(VanillaECPlugin.DAGGER.name()).get()).setWeight(2))
                                    .add(LootItem.lootTableItem(VanillaECPlugin.NETHERITE.getWeaponEntry(VanillaECPlugin.DAGGER.name()).get()).setWeight(2))
                                    .add(LootItem.lootTableItem(VanillaECPlugin.NETHERITE.getWeaponEntry(VanillaECPlugin.CLAYMORE.name()).get()).setWeight(3))
                                    .add(EmptyLootItem.emptyItem().setWeight(9))
                            ));
            })
            .register();

    public static void register() {

    }
}
