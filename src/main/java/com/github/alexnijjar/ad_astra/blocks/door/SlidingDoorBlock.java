package com.github.alexnijjar.ad_astra.blocks.door;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager.Builder;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.explosion.Explosion;

@SuppressWarnings("deprecation")
public class SlidingDoorBlock extends BlockWithEntity {

    public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;
    public static final BooleanProperty OPEN = Properties.OPEN;
    public static final BooleanProperty POWERED = Properties.POWERED;
    public static final EnumProperty<LocationState> LOCATION = EnumProperty.of("location", LocationState.class);

    protected static final VoxelShape X_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 16.0, 3.0);
    protected static final VoxelShape Z_SHAPE = Block.createCuboidShape(13.0, 0.0, 0.0, 16.0, 16.0, 16.0);

    protected static final VoxelShape GIANT_X_SHAPE = Block.createCuboidShape(-16, -16, 0.0, 32.0, 32.0, 3.0);
    protected static final VoxelShape GIANT_Z_SHAPE = Block.createCuboidShape(0.0, -16, -16, 3.0, 32.0, 32.0);

    public SlidingDoorBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(FACING, Direction.NORTH).with(OPEN, false).with(POWERED, false).with(LOCATION, LocationState.BOTTOM));
    }

    @Override
    protected void appendProperties(Builder<Block, BlockState> builder) {
        builder.add(FACING, OPEN, POWERED, LOCATION);
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        if (!world.isClient) {
            Direction direction = state.get(FACING);
            Direction offset = direction.rotateYClockwise();

            // Bottom
            world.setBlockState(pos, getDefaultState().with(LOCATION, LocationState.BOTTOM).with(FACING, direction), 3);
            // Bottom Left
            world.setBlockState(pos.offset(offset), getDefaultState().with(LOCATION, LocationState.BOTTOM_LEFT).with(FACING, direction), 3);
            // Bottom Right
            world.setBlockState(pos.offset(offset.getOpposite()), getDefaultState().with(LOCATION, LocationState.BOTTOM_RIGHT).with(FACING, direction), 3);
            // Center
            world.setBlockState(pos.up(), getDefaultState().with(LOCATION, LocationState.CENTER).with(FACING, direction), 3);
            // Left
            world.setBlockState(pos.up().offset(offset), getDefaultState().with(LOCATION, LocationState.LEFT).with(FACING, direction), 3);
            // Right
            world.setBlockState(pos.up().offset(offset.getOpposite()), getDefaultState().with(LOCATION, LocationState.RIGHT).with(FACING, direction), 3);
            // Top
            world.setBlockState(pos.up().up(), getDefaultState().with(LOCATION, LocationState.TOP).with(FACING, direction), 3);
            // Top Left
            world.setBlockState(pos.up().up().offset(offset), getDefaultState().with(LOCATION, LocationState.TOP_LEFT).with(FACING, direction), 3);
            // Top Right
            world.setBlockState(pos.up().up().offset(offset.getOpposite()), getDefaultState().with(LOCATION, LocationState.TOP_RIGHT).with(FACING, direction), 3);
        }
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        super.onBreak(world, pos, state, player);
        breakDoor(world, world.getBlockState(pos), pos, !player.isCreative());
    }

    @Override
    public void onDestroyedByExplosion(World world, BlockPos pos, Explosion explosion) {
        for (Direction direction : Direction.values()) {
            BlockPos offset = pos.offset(direction);
            BlockState state = world.getBlockState(offset);
            if (state.getBlock().equals(this)) {
                breakDoor(world, state, offset, true);
                break;
            }
        }
        super.onDestroyedByExplosion(world, pos, explosion);
    }

    public void breakDoor(World world, BlockState state, BlockPos pos, boolean drop) {
        if (!world.isClient && state.getBlock().equals(this)) {
            BlockPos mainPos = this.getMainPos(state, pos);
            if (world.getBlockState(mainPos).getBlock().equals(this)) {
                Direction direction = state.get(FACING).rotateYCounterclockwise();

                // Bottom
                world.breakBlock(mainPos, drop);
                // Bottom Left
                world.breakBlock(mainPos.offset(direction), false);
                // Bottom Right
                world.breakBlock(mainPos.offset(direction.getOpposite()), false);
                // Center
                world.breakBlock(mainPos.up(), false);
                // Left
                world.breakBlock(mainPos.up().offset(direction), false);
                // Right
                world.breakBlock(mainPos.up().offset(direction.getOpposite()), false);
                // Top
                world.breakBlock(mainPos.up().up(), false);
                // Top Left
                world.breakBlock(mainPos.up().up().offset(direction), false);
                // Top Right
                world.breakBlock(mainPos.up().up().offset(direction.getOpposite()), false);
            }
        }
    }

    @Override
    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return switch (type) {
            case LAND -> state.get(OPEN);
            case WATER -> false;
            case AIR -> state.get(OPEN);
        };
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient) {
            BlockPos main = this.getMainPos(state, pos);
            if (world.getBlockEntity(this.getMainPos(state, pos)) instanceof SlidingDoorBlockEntity entity) {
                if (entity.getSlideTicks() > 0 && entity.getSlideTicks() < 100) {
                    return ActionResult.PASS;
                } else {
                    world.setBlockState(main, world.getBlockState(main).cycle(OPEN), 10);
                }
            }

        }
        return ActionResult.success(world.isClient);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        LocationState location = state.get(LOCATION);
        return switch (location) {
            case TOP -> getFacingOutlineShape(state, -1);
            case CENTER -> getFacingOutlineShape(state, 0);
            case BOTTOM -> getFacingOutlineShape(state, 1);
            default -> switch (state.get(FACING)) {
                case NORTH -> X_SHAPE.offset(0, 0, 0.42);
                case EAST -> Z_SHAPE.offset(0.38, 0, 0);
                case SOUTH -> X_SHAPE.offset(0, 0, 0.38);
                case WEST -> Z_SHAPE.offset(-0.44, 0, 0);
                default -> VoxelShapes.empty();
            };
        };
    }

    private VoxelShape getFacingOutlineShape(BlockState state, double offset) {
        return switch (state.get(FACING)) {
            case NORTH -> GIANT_X_SHAPE.offset(0, offset, 0.42);
            case EAST -> GIANT_Z_SHAPE.offset(0.38, offset, 0);
            case SOUTH -> GIANT_X_SHAPE.offset(0, offset, 0.38);
            case WEST -> GIANT_Z_SHAPE.offset(-0.44, offset, 0);
            default -> VoxelShapes.empty();
        };
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockPos mainPos = this.getMainPos(state, pos);
        Direction direction = state.get(FACING).rotateYClockwise();

        // Bottom
        return world.getBlockState(mainPos).isAir() &&
        // Bottom Left
                world.getBlockState(mainPos.offset(direction)).isAir() &&
                // Bottom Right
                world.getBlockState(mainPos.offset(direction.getOpposite())).isAir() &&
                // Center
                world.getBlockState(mainPos.up()).isAir() &&
                // Left
                world.getBlockState(mainPos.up().offset(direction)).isAir() &&
                // Right
                world.getBlockState(mainPos.up().offset(direction.getOpposite())).isAir() &&
                // Top
                world.getBlockState(mainPos.up().up()).isAir() &&
                // Top Left
                world.getBlockState(mainPos.up().up().offset(direction)).isAir() &&
                // Top Right
                world.getBlockState(mainPos.up().up().offset(direction.getOpposite())).isAir();

    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(POWERED, ctx.getWorld().isReceivingRedstonePower(ctx.getBlockPos())).with(FACING, ctx.getPlayerFacing().getOpposite());
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        super.neighborUpdate(state, world, pos, block, fromPos, notify);

        if (!world.isClient) {
            world.setBlockState(pos, state.with(POWERED, world.isReceivingRedstonePower(pos)));
        }
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        BlockState main = world.getBlockState(this.getMainPos(state, pos));
        if (main.contains(OPEN) && (!main.get(OPEN) && !main.get(POWERED))) {
            Direction direction = state.get(FACING);
            return switch (direction) {
            case NORTH -> X_SHAPE.offset(0, 0, 0.42);
            case EAST -> Z_SHAPE.offset(0.38, 0, 0);
            case SOUTH -> X_SHAPE.offset(0, 0, 0.38);
            case WEST -> Z_SHAPE.offset(-0.44, 0, 0);
            default -> VoxelShapes.empty();
            };
        } else {
            return VoxelShapes.empty();
        }
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        if (state.get(LOCATION).equals(LocationState.BOTTOM)) {
            return new SlidingDoorBlockEntity(pos, state);
        }
        return null;
    }

    @Override
    public PistonBehavior getPistonBehavior(BlockState state) {
        return PistonBehavior.BLOCK;
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return (entityWorld, pos, entityState, blockEntity) -> {
            if (blockEntity instanceof SlidingDoorBlockEntity door) {
                door.tick();
            }
        };
    }

    public BlockPos getMainPos(BlockState state, BlockPos from) {
		BlockPos target = from;
        Direction facing = state.get(FACING).rotateYCounterclockwise();
        if (state.get(LOCATION).equals(LocationState.TOP_LEFT)) {
            target = from.down().down().offset(facing);
        } else if (state.get(LOCATION).equals(LocationState.TOP)) {
            target = from.down().down();
        } else if (state.get(LOCATION).equals(LocationState.TOP_RIGHT)) {
            target = from.down().down().offset(facing.getOpposite());
        } else if (state.get(LOCATION).equals(LocationState.RIGHT)) {
            target = from.down().offset(facing.getOpposite());
        } else if (state.get(LOCATION).equals(LocationState.CENTER)) {
            target = from.down();
        } else if (state.get(LOCATION).equals(LocationState.LEFT)) {
            target = from.down().offset(facing);
        } else if (state.get(LOCATION).equals(LocationState.BOTTOM_LEFT)) {
            target = from.offset(facing);
        } else if (state.get(LOCATION).equals(LocationState.BOTTOM_RIGHT)) {
            target = from.offset(facing.getOpposite());
        }
		return target;
	}
}
