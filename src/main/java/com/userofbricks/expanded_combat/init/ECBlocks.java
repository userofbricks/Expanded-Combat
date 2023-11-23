package com.userofbricks.expanded_combat.init;

import com.tterrag.registrate.util.entry.RegistryEntry;
import com.userofbricks.expanded_combat.block.AbstractGasBlock;
import com.userofbricks.expanded_combat.block.PurifiedGasBlock;
import com.userofbricks.expanded_combat.block.WeaponDisplayBlock;
import com.userofbricks.expanded_combat.block.WeaponDisplayPart;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;

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
            .blockstate((ctx, prov) -> {
                prov.horizontalBlock(ctx.get(), state -> state.getValue(WeaponDisplayBlock.PART) == WeaponDisplayPart.RIGHT ? prov.models().getExistingFile(prov.modLoc("block/heartstealer")) : prov.models().getExistingFile(prov.modLoc("block/nothing")));
            })
            .register();

    public static void register() {

    }
}
