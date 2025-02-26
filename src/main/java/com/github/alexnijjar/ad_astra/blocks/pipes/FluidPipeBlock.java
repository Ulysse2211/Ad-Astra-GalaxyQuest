package com.github.alexnijjar.ad_astra.blocks.pipes;

import java.util.HashMap;
import java.util.Map;

import com.github.alexnijjar.ad_astra.registry.ModSoundEvents;

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateManager.Builder;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.World;

public class FluidPipeBlock extends AbstractPipeBlock {

    public static final EnumProperty<PipeState> UP = EnumProperty.of("up", PipeState.class);
    public static final EnumProperty<PipeState> DOWN = EnumProperty.of("down", PipeState.class);
    public static final EnumProperty<PipeState> NORTH = EnumProperty.of("north", PipeState.class);
    public static final EnumProperty<PipeState> EAST = EnumProperty.of("east", PipeState.class);
    public static final EnumProperty<PipeState> SOUTH = EnumProperty.of("south", PipeState.class);
    public static final EnumProperty<PipeState> WEST = EnumProperty.of("west", PipeState.class);

    public static final Map<Direction, EnumProperty<PipeState>> DIRECTIONS = Util.make(new HashMap<>(), map -> {
        map.put(Direction.UP, UP);
        map.put(Direction.DOWN, DOWN);
        map.put(Direction.NORTH, NORTH);
        map.put(Direction.EAST, EAST);
        map.put(Direction.SOUTH, SOUTH);
        map.put(Direction.WEST, WEST);
    });

    public FluidPipeBlock(long transferRate, int decay, double size, Settings settings) {
        super(transferRate, decay, size, settings);
        this.setDefaultState(getDefaultState().with(UP, PipeState.NONE).with(DOWN, PipeState.NONE).with(NORTH, PipeState.NONE).with(EAST, PipeState.NONE).with(SOUTH, PipeState.NONE).with(WEST, PipeState.NONE).with(WATERLOGGED, false));
    }

    @Override
    protected void appendProperties(Builder<Block, BlockState> builder) {
        builder.add(UP, DOWN, NORTH, EAST, SOUTH, WEST, WATERLOGGED);
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new FluidPipeBlockEntity(pos, state);
    }

    @Override
    public void updateShape(World world, BlockPos pos, BlockState state, Direction direction) {
        if (!world.isClient) {
            BlockPos offset = pos.offset(direction);
            boolean connect = world.getBlockState(offset).getBlock() instanceof FluidPipeBlock || FluidStorage.SIDED.find(world, offset, direction) != null;

            if (connect) {
                if (world.getBlockState(pos).get(DIRECTIONS.get(direction)).equals(PipeState.NONE)) {
                    world.setBlockState(pos, world.getBlockState(pos).with(DIRECTIONS.get(direction), PipeState.NORMAL), Block.NOTIFY_ALL);
                }
                if (world.getBlockState(offset).getBlock().equals(this)) {
                    world.setBlockState(offset, world.getBlockState(offset).with(DIRECTIONS.get(direction.getOpposite()), PipeState.NORMAL), Block.NOTIFY_ALL);
                }
            } else {
                if (world.getBlockState(pos).get(DIRECTIONS.get(direction)).equals(PipeState.NORMAL)) {
                    world.setBlockState(pos, world.getBlockState(pos).with(DIRECTIONS.get(direction), PipeState.NONE), Block.NOTIFY_ALL);
                }
            }
        }
    }

    @Override
    public VoxelShape updateOutlineShape(BlockState state) {
        size = 0.185;
        VoxelShape shape = VoxelShapes.cuboid(size, size, size, 1 - size, 1 - size, 1 - size);

        if (state.get(UP) != PipeState.NONE) {
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(size, size, size, 1 - size, 1, 1 - size));
        }
        if (state.get(DOWN) != PipeState.NONE) {
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(size, 0, size, 1 - size, 1 - size, 1 - size));
        }
        if (state.get(NORTH) != PipeState.NONE) {
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(size, size, 0, 1 - size, 1 - size, 1 - size));
        }
        if (state.get(EAST) != PipeState.NONE) {
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(size, size, size, 1, 1 - size, 1 - size));
        }
        if (state.get(SOUTH) != PipeState.NONE) {
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(size, size, size, 1 - size, 1 - size, 1));
        }
        if (state.get(WEST) != PipeState.NONE) {
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0, size, size, 1 - size, 1 - size, 1 - size));
        }

        return shape;
    }

    @Override
    public void handleWrench(World world, BlockPos pos, BlockState state, Direction side, PlayerEntity user, Vec3d hitPos) {
        if (!world.isClient) {
            EnumProperty<PipeState> property = DIRECTIONS.get(getDirectionByVec(hitPos, pos).orElse(user.isSneaking() ? side.getOpposite() : side));
            world.setBlockState(pos, togglePipeState(state, property, user), Block.NOTIFY_ALL, 0);
            world.playSound(null, pos, ModSoundEvents.WRENCH, SoundCategory.BLOCKS, 1, 1);
        }
    }

    public BlockState togglePipeState(BlockState state, EnumProperty<PipeState> property, PlayerEntity user) {
        state = state.cycle(property);
        switch (state.get(property)) {
            case NORMAL -> user.sendMessage(new TranslatableText("info.ad_astra.normal"), true);
            case INSERT -> user.sendMessage(new TranslatableText("info.ad_astra.insert"), true);
            case EXTRACT -> user.sendMessage(new TranslatableText("info.ad_astra.extract"), true);
            case NONE -> user.sendMessage(new TranslatableText("info.ad_astra.none"), true);
        }
        return state;
    }
}