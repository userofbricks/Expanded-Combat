package com.userofbricks.expanded_combat.init;

import com.userofbricks.expanded_combat.block.AbstractGasBlock;
import com.userofbricks.expanded_combat.block.PurifiedGasBlock;
import com.userofbricks.expanded_combat.block.WeaponDisplayBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import javax.annotation.ParametersAreNonnullByDefault;

import static com.userofbricks.expanded_combat.ExpandedCombat.MODID;

@ParametersAreNonnullByDefault
public class ECBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MODID);
    public static final DeferredBlock<PurifiedGasBlock> PURIFIED_GAS_BLOCK = BLOCKS.registerBlock("purified_gas", PurifiedGasBlock::new, BlockBehaviour.Properties.of());
    public static final DeferredBlock<? extends AbstractGasBlock> GAS_BLOCK = BLOCKS.registerBlock("gas", properties -> new AbstractGasBlock(properties, () -> ParticleTypes.MYCELIUM) {
        @Override
        public boolean blockCatalistItemConversions(BlockState thisState, BlockState catalistState, ServerLevel world, BlockPos thisPos, BlockPos catalistPos, RandomSource random) {
            return false;
        }
    }, BlockBehaviour.Properties.of());
    public static final DeferredBlock<WeaponDisplayBlock> HEART_STEALER_DISPLAY = BLOCKS.registerBlock("heartstealer_display", WeaponDisplayBlock::new,
            BlockBehaviour.Properties.of().strength(20f, 2000f));
    public static final DeferredItem<BlockItem> HEART_STEALER_DISPLAY_ITEM = ECItems.ITEMS.registerSimpleBlockItem("heartstealer_display", HEART_STEALER_DISPLAY);

    public static void registerDataGen() {
        /*TODO:
            Heartstealer datagen

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
            });
         */
    }

    public static void register() {

    }
}
