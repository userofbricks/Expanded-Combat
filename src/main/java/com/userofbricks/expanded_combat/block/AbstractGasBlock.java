package com.userofbricks.expanded_combat.block;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Arrays;
import java.util.function.Supplier;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@SuppressWarnings("deprecation")
public abstract class AbstractGasBlock extends Block {
    private final Supplier<ParticleOptions> particleSupplier;
    public AbstractGasBlock(Properties properties, Supplier<ParticleOptions> particleSupplier) {
        super(properties.replaceable());
        this.registerDefaultState(this.stateDefinition.any());
        this.particleSupplier = particleSupplier;
    }

    public RenderShape getRenderShape(BlockState p_48758_) {
        return RenderShape.INVISIBLE;
    }
    public VoxelShape getShape(BlockState p_48760_, BlockGetter p_48761_, BlockPos p_48762_, CollisionContext p_48763_) {
        return Shapes.empty();
    }
    public VoxelShape getVisualShape(BlockState p_60479_, BlockGetter p_60480_, BlockPos p_60481_, CollisionContext p_60482_) {
        return Shapes.block();
    }

    public void animateTick(BlockState p_221789_, Level p_221790_, BlockPos p_221791_, RandomSource p_221792_) {
        super.animateTick(p_221789_, p_221790_, p_221791_, p_221792_);
        p_221790_.addParticle(particleSupplier.get(), (double)p_221791_.getX() + p_221792_.nextDouble(), (double)p_221791_.getY() + p_221792_.nextDouble(), (double)p_221791_.getZ() + p_221792_.nextDouble(), 0.0D, 0.0D, 0.0D);
        p_221790_.addParticle(particleSupplier.get(), (double)p_221791_.getX() + p_221792_.nextDouble(), (double)p_221791_.getY() + p_221792_.nextDouble(), (double)p_221791_.getZ() + p_221792_.nextDouble(), 0.0D, 0.0D, 0.0D);
        p_221790_.addParticle(particleSupplier.get(), (double)p_221791_.getX() + p_221792_.nextDouble(), (double)p_221791_.getY() + p_221792_.nextDouble(), (double)p_221791_.getZ() + p_221792_.nextDouble(), 0.0D, 0.0D, 0.0D);
    }

    @Override
    public void onPlace(BlockState blockstate, Level world, BlockPos pos, BlockState oldState, boolean moving) {
        super.onPlace(blockstate, world, pos, oldState, moving);
        world.scheduleTick(pos, this, 5);
    }

    @Override
    public void tick(BlockState blockState, ServerLevel world, BlockPos pos, RandomSource random) {
        super.tick(blockState, world, pos, random);
        BlockPos randPos = pos.relative(Util.getRandom(Arrays.asList(Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST), random));
        if (random.nextInt(3) == 0){
            if (world.getBlockState(pos.above()).isAir() && pos.getY() < (world.getMaxBuildHeight() - 1)) {
                world.setBlockAndUpdate(pos.above(), this.defaultBlockState());
                world.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
                world.scheduleTick(pos, this, 5);
                return;
            } else if (world.getBlockState(randPos).isAir() && pos.getY() < (world.getMaxBuildHeight() - 1) && isAirNear(world, randPos.above())) {
                world.setBlockAndUpdate(randPos, this.defaultBlockState());
                world.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
                world.scheduleTick(pos, this, 5);
                return;
            }
        }
        if (random.nextInt( 500) == 0) {
            BlockPos randPos1 = pos.relative(Util.getRandom(Direction.values(), random));
            if (blockCatalistItemConversions(blockState, world.getBlockState(randPos), world, pos, randPos1, random)) {
                world.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
                return;
            }
        }
        world.scheduleTick(pos, this, 5);
    }

    public abstract boolean blockCatalistItemConversions(BlockState thisState, BlockState catalistState, ServerLevel world, BlockPos thisPos, BlockPos catalistPos, RandomSource random);

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos pos1, boolean b) {
        level.scheduleTick(pos, this, 1);
        super.neighborChanged(state, level, pos, block, pos1, b);
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (level instanceof ServerLevel) {
            BlockPos randPos = pos.relative(Util.getRandom(Arrays.asList(Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST), level.random));
            if (level.getBlockState(randPos).isAir() && pos.getY() < (level.getMaxBuildHeight() - 1) && isAirNear(level, randPos)) {
                level.setBlockAndUpdate(randPos, this.defaultBlockState());
                level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
            }
        }
        super.entityInside(state, level, pos, entity);
    }

    private boolean isAirNear(Level world, BlockPos randPos) {
        for (int x = -3; x < 3; x++) {
            for (int z = -3; z < 3; z++) {
                if (world.getBlockState(randPos.offset(x, 0, z)).isAir()) return true;
            }
        }
        return false;
    }

    public static BlockHitResult getPlayerPOVHitResult(Level level, Player player, ClipContext.Fluid fluid) {
        float f = player.getXRot();
        float f1 = player.getYRot();
        Vec3 vec3 = player.getEyePosition();
        float f2 = Mth.cos(-f1 * ((float)Math.PI / 180F) - (float)Math.PI);
        float f3 = Mth.sin(-f1 * ((float)Math.PI / 180F) - (float)Math.PI);
        float f4 = -Mth.cos(-f * ((float)Math.PI / 180F));
        float f5 = Mth.sin(-f * ((float)Math.PI / 180F));
        float f6 = f3 * f4;
        float f7 = f2 * f4;
        double d0 = player.getBlockReach();
        Vec3 vec31 = vec3.add((double)f6 * d0, (double)f5 * d0, (double)f7 * d0);
        return level.clip(new ClipContext(vec3, vec31, ClipContext.Block.VISUAL, fluid, player));
    }
}
