package com.github.alexnijjar.ad_astra.blocks.pipes;

import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.Waterloggable;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

import java.util.Optional;

@SuppressWarnings("deprecation")
public abstract class AbstractPipeBlock extends BlockWithEntity implements Waterloggable, Wrenchable {

    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    protected long transferRate;
    protected int decay;
    protected double size;

    protected AbstractPipeBlock(long transferRate, int decay, double size, Settings settings) {
        super(settings);
        this.transferRate = transferRate;
        this.decay = decay;
        this.size = size;
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return (entityWorld, pos, entityState, blockEntity) -> {
            if (blockEntity instanceof InteractablePipe<?> pipe) {
                pipe.pipeTick();
            }
        };
    }

    public long getTransferRate() {
        return this.transferRate;
    }

    public int getDecay() {
        return this.decay;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
        return this.getDefaultState().with(WATERLOGGED, context.getWorld().getFluidState(context.getBlockPos()).getFluid().equals(Fluids.WATER));
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    // Extend the pipe when something around it is updated.
    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        this.updateShape((World) world, pos, state, direction);

        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    // Extend the pipe when it is first placed.
    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);
        this.updateShape(world, pos, state);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return this.updateOutlineShape(state);
    }

    public void updateShape(World world, BlockPos pos, BlockState state) {
        if (!world.isClient) {
            for (Direction direction : Direction.values()) {
                this.updateShape(world, pos, state, direction);
            }
        }
    }

    public abstract void updateShape(World world, BlockPos pos, BlockState state, Direction direction);

    // Expand the voxel shape to match the model.
    public abstract VoxelShape updateOutlineShape(BlockState state);

    public static Optional<Direction> getDirectionByVec(Vec3d hit, BlockPos pos) {
        var relativePos = hit.add(-pos.getX(), -pos.getY(), -pos.getZ());
        if (relativePos.x < (2f/16f)) return Optional.of(Direction.WEST);
        else if (relativePos.x > (14f/16f)) return Optional.of(Direction.EAST);
        else if (relativePos.z < (2f/16f)) return Optional.of(Direction.NORTH);
        else if (relativePos.z > (14f/16f)) return Optional.of(Direction.SOUTH);
        else if (relativePos.y < (2f/16f)) return Optional.of(Direction.DOWN);
        else if (relativePos.y > (14f/16f)) return Optional.of(Direction.UP);
        return Optional.empty();
    }
}