package com.userofbricks.expanded_combat.block;

import com.userofbricks.expanded_combat.init.ECItems;
import com.userofbricks.expanded_combat.network.ECVariables;
import com.userofbricks.expanded_combat.plugins.CustomWeaponsPlugin;
import com.userofbricks.expanded_combat.plugins.VanillaECPlugin;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Collections;
import java.util.List;

import static com.userofbricks.expanded_combat.ExpandedCombat.modLoc;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class WeaponDisplayBlock extends HorizontalDirectionalBlock {
    public static final EnumProperty<WeaponDisplayPart> PART = EnumProperty.create("part", WeaponDisplayPart.class);
    public WeaponDisplayBlock(Properties p_54120_) {
        super(p_54120_);
        this.registerDefaultState(this.stateDefinition.any().setValue(PART, WeaponDisplayPart.RIGHT));
    }
    @SuppressWarnings("deprecation")
    public BlockState updateShape(BlockState thisState, Direction directionOfUpdate, BlockState updatedBlock, LevelAccessor levelAccessor, BlockPos thisPos, BlockPos updatedPos) {
        if (directionOfUpdate == getNeighbourDirection(thisState.getValue(PART), thisState.getValue(FACING))) {
            return updatedBlock.is(this) && updatedBlock.getValue(PART) != thisState.getValue(PART) ? thisState : Blocks.AIR.defaultBlockState();
        } else {
            return super.updateShape(thisState, directionOfUpdate, updatedBlock, levelAccessor, thisPos, updatedPos);
        }
    }
    private static Direction getNeighbourDirection(WeaponDisplayPart p_49534_, Direction p_49535_) {
        return p_49534_ == WeaponDisplayPart.RIGHT ? p_49535_.getClockWise() : p_49535_.getCounterClockWise();
    }
    public void playerWillDestroy(Level p_49505_, BlockPos p_49506_, BlockState p_49507_, Player p_49508_) {
        if (!p_49505_.isClientSide && p_49508_.isCreative()) {
            WeaponDisplayPart bedpart = p_49507_.getValue(PART);
            if (bedpart == WeaponDisplayPart.RIGHT) {
                BlockPos blockpos = p_49506_.relative(getNeighbourDirection(bedpart, p_49507_.getValue(FACING).getClockWise()));
                BlockState blockstate = p_49505_.getBlockState(blockpos);
                if (blockstate.is(this) && blockstate.getValue(PART) == WeaponDisplayPart.LEFT) {
                    p_49505_.setBlock(blockpos, Blocks.AIR.defaultBlockState(), 35);
                    p_49505_.levelEvent(p_49508_, 2001, blockpos, Block.getId(blockstate));
                }
            }
        }

        super.playerWillDestroy(p_49505_, p_49506_, p_49507_, p_49508_);
    }
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext p_49479_) {
        Direction direction = p_49479_.getHorizontalDirection();
        BlockPos blockpos = p_49479_.getClickedPos();
        BlockPos blockpos1 = blockpos.relative(direction.getClockWise());
        Level level = p_49479_.getLevel();
        return level.getBlockState(blockpos1).canBeReplaced(p_49479_) && level.getWorldBorder().isWithinBounds(blockpos1) ? this.defaultBlockState().setValue(FACING, direction) : null;
    }
    @SuppressWarnings("deprecation")
    public VoxelShape getShape(BlockState p_49547_, BlockGetter p_49548_, BlockPos p_49549_, CollisionContext p_49550_) {
        return switch (p_49547_.getValue(PART)) {
            default -> switch (p_49547_.getValue(FACING)) {
                case NORTH -> northRight();
                case SOUTH -> southRight();
                case WEST -> westRight();
                default -> eastRight();
            };
            case LEFT -> switch (p_49547_.getValue(FACING)) {
                case NORTH -> northLeft();
                case SOUTH -> southLeft();
                case WEST -> westLeft();
                default -> eastLeft();
            };
        };
    }
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_49532_) {
        p_49532_.add(FACING, PART);
    }
    public void setPlacedBy(Level p_49499_, BlockPos p_49500_, BlockState p_49501_, @Nullable LivingEntity p_49502_, ItemStack p_49503_) {
        super.setPlacedBy(p_49499_, p_49500_, p_49501_, p_49502_, p_49503_);
        if (!p_49499_.isClientSide) {
            BlockPos blockpos = p_49500_.relative(p_49501_.getValue(FACING).getClockWise());
            p_49499_.setBlock(blockpos, p_49501_.setValue(PART, WeaponDisplayPart.LEFT), 3);
            p_49499_.blockUpdated(p_49500_, Blocks.AIR);
            p_49501_.updateNeighbourShapes(p_49499_, p_49500_, 3);
        }
    }
    @SuppressWarnings("deprecation")
    public long getSeed(BlockState p_49522_, BlockPos p_49523_) {
        BlockPos blockpos = p_49523_.relative(p_49522_.getValue(FACING).getCounterClockWise(), p_49522_.getValue(PART) == WeaponDisplayPart.LEFT ? 0 : 1);
        return Mth.getSeed(blockpos.getX(), p_49523_.getY(), blockpos.getZ());
    }
    @SuppressWarnings("deprecation")
    public boolean isPathfindable(BlockState p_49510_, BlockGetter p_49511_, BlockPos p_49512_, PathComputationType p_49513_) {
        return false;
    }

    @SuppressWarnings("deprecation")
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
        ResourceLocation resourcelocation = this.getLootTable();
        if (resourcelocation == BuiltInLootTables.EMPTY) {
            return Collections.emptyList();
        } else {
            LootParams lootparams = builder.withParameter(LootContextParams.BLOCK_STATE, state).create(LootContextParamSets.BLOCK);
            ServerLevel serverlevel = lootparams.getLevel();
            LootTable loottable = serverlevel.getServer().getLootData().getLootTable(resourcelocation);

            if (ECVariables.WorldVariables.getHeartStealerCount(builder.getLevel()) != 0 && serverlevel.random.nextInt(10) != 0) {
                return loottable.getRandomItems(lootparams);
            } else {
                ECVariables.WorldVariables.increaseHeartStealerCount(builder.getLevel());
                return List.of(new ItemStack(CustomWeaponsPlugin.HEART_STEALER.getWeaponEntry(VanillaECPlugin.CLAYMORE.name()).get()));
            }
        }
    }

    public VoxelShape westRight(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.or(shape, Shapes.box(0.4375, 0.25, 0, 0.5625, 0.4375, 0.9375));
        shape = Shapes.or(shape, Shapes.box(0.4375, 0, 0.25, 0.5625, 0.5, 0.375));
        shape = Shapes.or(shape, Shapes.box(0.4375, 0, 0, 0.5625, 0.125, 0.25));
        shape = Shapes.or(shape, Shapes.box(0.4375, 0.0625, 0.375, 0.5625, 0.625, 0.5));

        return shape;
    }
    public VoxelShape westLeft(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.or(shape, Shapes.box(0.4375, 0, 0.375, 0.5625, 0.5, 0.5));
        shape = Shapes.or(shape, Shapes.box(0.4375, 0, 0.5, 0.5625, 0.125, 1));
        shape = Shapes.or(shape, Shapes.box(0.4375, 0.25, 0.125, 0.5625, 0.4375, 1));

        return shape;
    }
    public VoxelShape southRight(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.or(shape, Shapes.box(0, 0.25, 0.4375, 0.9375, 0.4375, 0.5625));
        shape = Shapes.or(shape, Shapes.box(0.25, 0, 0.4375, 0.375, 0.5, 0.5625));
        shape = Shapes.or(shape, Shapes.box(0, 0, 0.4375, 0.25, 0.125, 0.5625));
        shape = Shapes.or(shape, Shapes.box(0.375, 0.0625, 0.4375, 0.5, 0.625, 0.5625));

        return shape;
    }
    public VoxelShape southLeft(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.or(shape, Shapes.box(0.375, 0, 0.4375, 0.5, 0.5, 0.5625));
        shape = Shapes.or(shape, Shapes.box(0.5, 0, 0.4375, 1, 0.125, 0.5625));
        shape = Shapes.or(shape, Shapes.box(0.125, 0.25, 0.4375, 1, 0.4375, 0.5625));

        return shape;
    }
    public VoxelShape northRight(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.or(shape, Shapes.box(0.0625, 0.25, 0.4375, 1, 0.4375, 0.5625));
        shape = Shapes.or(shape, Shapes.box(0.625, 0, 0.4375, 0.75, 0.5, 0.5625));
        shape = Shapes.or(shape, Shapes.box(0.75, 0, 0.4375, 1, 0.125, 0.5625));
        shape = Shapes.or(shape, Shapes.box(0.5, 0.0625, 0.4375, 0.625, 0.625, 0.5625));

        return shape;
    }public VoxelShape northLeft(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.or(shape, Shapes.box(0.5, 0, 0.4375, 0.625, 0.5, 0.5625));
        shape = Shapes.or(shape, Shapes.box(0, 0, 0.4375, 0.5, 0.125, 0.5625));
        shape = Shapes.or(shape, Shapes.box(0, 0.25, 0.4375, 0.875, 0.4375, 0.5625));

        return shape;
    }
    public VoxelShape eastRight(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.or(shape, Shapes.box(0.4375, 0.25, 0.0625, 0.5625, 0.4375, 1));
        shape = Shapes.or(shape, Shapes.box(0.4375, 0, 0.625, 0.5625, 0.5, 0.75));
        shape = Shapes.or(shape, Shapes.box(0.4375, 0, 0.75, 0.5625, 0.125, 1));
        shape = Shapes.or(shape, Shapes.box(0.4375, 0.0625, 0.5, 0.5625, 0.625, 0.625));

        return shape;
    }
    public VoxelShape eastLeft(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.or(shape, Shapes.box(0.4375, 0, 0.5, 0.5625, 0.5, 0.625));
        shape = Shapes.or(shape, Shapes.box(0.4375, 0, 0, 0.5625, 0.125, 0.5));
        shape = Shapes.or(shape, Shapes.box(0.4375, 0.25, 0, 0.5625, 0.4375, 0.875));

        return shape;
    }
}
