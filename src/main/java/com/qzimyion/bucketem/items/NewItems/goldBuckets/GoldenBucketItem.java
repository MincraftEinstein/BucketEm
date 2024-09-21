package com.qzimyion.bucketem.items.NewItems.goldBuckets;

import com.ordana.spelunkery.reg.ModFluids;
import com.qzimyion.bucketem.compact.IsModLoaded;
import com.qzimyion.bucketem.compact.spelunkery.SpelunkeryBucketemItems;
import com.qzimyion.bucketem.items.ModItems;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.FluidModificationItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class GoldenBucketItem extends Item implements FluidModificationItem {
    public final Fluid fluid;
    public static final String NBT_TAG = "FluidLevel";

    public GoldenBucketItem(Fluid fluid, Settings settings) {
        super(settings);
        this.fluid = fluid;
    }

    public static ItemStack resetFluidLevel(ItemStack stack) {
        stack.getOrCreateNbt().putInt(NBT_TAG, 0);
        return stack;
    }

    public static void setFluidLevel(ItemStack stack, int level) {
        stack.getOrCreateNbt().putInt(NBT_TAG, level);
    }

    public static void decreaseFluidLevel(ItemStack stack) {
        int level = stack.getOrCreateNbt().getInt(NBT_TAG);
        if (level > 0) stack.getOrCreateNbt().putInt(NBT_TAG, level - 1);
    }

    public static ItemStack increaseFluidLevel(ItemStack stack) {
        int level = stack.getOrCreateNbt().getInt(NBT_TAG);
        if (level < 3) stack.getOrCreateNbt().putInt(NBT_TAG, level + 1);
        return stack;
    }

    public static boolean canBeFilled(ItemStack stack) {
        return stack.getOrCreateNbt().getInt(NBT_TAG) < 2;
    }

    @Override
    public ItemStack getDefaultStack() {
        return resetFluidLevel(new ItemStack(this));
    }

    @Override
    public void onCraft(ItemStack stack, World world, PlayerEntity player) {
        resetFluidLevel(stack);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);
        NbtCompound tag = stack.getOrCreateNbt();
        int bucketLevel = tag.getInt(NBT_TAG);


        BlockHitResult result = raycast(world, player, (this.getFluid() == Fluids.EMPTY || bucketLevel < 2) && !(player.isSneaking() && this.getFluid() != Fluids.EMPTY) ? RaycastContext.FluidHandling.SOURCE_ONLY : RaycastContext.FluidHandling.NONE);
        if (result.getType() == HitResult.Type.MISS) {
            return TypedActionResult.pass(stack);
        } else if (result.getType() != HitResult.Type.BLOCK) {
            return TypedActionResult.pass(stack);
        } else {
            BlockPos pos = result.getBlockPos();
            Direction direction = result.getSide();
            BlockPos sourcePos = pos.offset(direction);
            if (world.canPlayerModifyAt(player, pos) && player.canPlaceOn(sourcePos, direction, stack)) {
                BlockState sourceState = world.getBlockState(pos);
                if (this.getFluid() == Fluids.EMPTY || (bucketLevel < 2 && sourceState.getFluidState().getFluid() == this.getFluid())) {
                    // Picking up raw fluids & powder snow
                    if (sourceState.getBlock() instanceof FluidDrainable bucketPickup) {
                        bucketPickup.tryDrainFluid(world, pos, sourceState);
                        Fluid fluid = sourceState.getFluidState().isOf(Fluids.WATER) ? Fluids.WATER : sourceState.getFluidState().isOf(Fluids.LAVA) ? Fluids.LAVA : Fluids.EMPTY;
                        ItemStack newBucket = ItemStack.EMPTY;

                        //Spelunkery
                        if (IsModLoaded.isSpelunkeryModLoaded()){
                            fluid = sourceState.getFluidState().isOf(ModFluids.PORTAL_FLUID.get()) ? ModFluids.PORTAL_FLUID.get() : sourceState.getFluidState().isOf(ModFluids.SPRING_WATER.get()) ? ModFluids.SPRING_WATER.get() : Fluids.EMPTY;
                        }

                        if (fluid != Fluids.EMPTY && getFilledBucket(sourceState) != null) {
                            newBucket = ItemUsage.exchangeStack(stack, player, getFilledBucket(sourceState));
                            if (this.getFluid() != Fluids.EMPTY)
                                setFluidLevel(newBucket, bucketLevel + 1);
                        }

                        if (sourceState.isOf(Blocks.POWDER_SNOW)) {
                            newBucket = ItemUsage.exchangeStack(stack, player, new ItemStack(ModItems.GOLDEN_POWDER_SNOW_BUCKET));
                        }

                        if (!newBucket.isEmpty()) {
                            player.incrementStat(Stats.USED.getOrCreateStat(this));
                            bucketPickup.getBucketFillSound().ifPresent((soundEvent) -> player.playSound(soundEvent, 1.0F, 1.0F));
                            world.emitGameEvent(player, GameEvent.FLUID_PICKUP, pos);
                            if (!world.isClient) {
                                Criteria.FILLED_BUCKET.trigger((ServerPlayerEntity) player, newBucket);
                            }

                            return TypedActionResult.success(newBucket, world.isClient());
                        }
                    }

                    return TypedActionResult.fail(stack);
                } else {
                    result = raycast(world, player, RaycastContext.FluidHandling.NONE);
                    if (result.getType() == HitResult.Type.MISS) {
                        return TypedActionResult.pass(stack);
                    } else if (result.getType() != HitResult.Type.BLOCK) {
                        return TypedActionResult.pass(stack);
                    }

                    // Placing contents
                    pos = result.getBlockPos();
                    direction = result.getSide();
                    sourcePos = pos.offset(direction);
                    BlockState state = world.getBlockState(pos);
                    BlockPos newPos = canBlockContainFluid(world, pos, state, player) || (this.getFluid() != Fluids.EMPTY && bucketLevel < 2) ? pos : sourcePos;
                    if (this.placeFluid(player, world, newPos, result)) {
                        player.incrementStat(Stats.USED.getOrCreateStat(this));
                        return TypedActionResult.success(getEmptySuccessItem(stack, player), world.isClient());
                    } else {
                        return TypedActionResult.fail(stack);
                    }
                }
            } else {
                return TypedActionResult.fail(stack);
            }
        }
    }

    public static ItemStack getEmptySuccessItem(ItemStack stack, PlayerEntity player) {
        int level = stack.getOrCreateNbt().getInt(NBT_TAG);
        ItemStack returnStack = level > 0 ? stack : getEmptyBucket();
        if (player == null || !player.getAbilities().creativeMode)
            decreaseFluidLevel(returnStack);
        return player == null || !player.getAbilities().creativeMode ? returnStack : stack;
    }

    protected void playEmptySound(PlayerEntity player, World world, BlockPos pos) {
        SoundEvent soundevent;
        soundevent = this.getFluid().isIn(FluidTags.LAVA) ? SoundEvents.ITEM_BUCKET_EMPTY_LAVA : SoundEvents.ITEM_BUCKET_EMPTY;
        world.playSound(player, pos, soundevent, SoundCategory.BLOCKS, 1.0F, 1.0F);
        world.emitGameEvent(player, GameEvent.FLUID_PLACE, pos);
    }

    public Fluid getFluid() {
        return fluid;
    }

    private boolean canBlockContainFluid(World worldIn, BlockPos posIn, BlockState blockstate, PlayerEntity player) {
        return blockstate.getBlock() instanceof FluidFillable && ((FluidFillable) blockstate.getBlock()).canFillWithFluid(worldIn, posIn, blockstate, this.getFluid());
    }


    public static ItemStack getFilledBucket(Fluid fluid) {
        if (fluid == Fluids.WATER) return new ItemStack(ModItems.GOLDEN_WATER_BUCKET);
        if (fluid == Fluids.LAVA) return new ItemStack(ModItems.GOLDEN_LAVA_BUCKET);
        if (IsModLoaded.isSpelunkeryModLoaded()){
            if (fluid == ModFluids.PORTAL_FLUID) return new ItemStack(SpelunkeryBucketemItems.GOLDEN_PORTAL_FLUID_BUCKET);
            if (fluid == ModFluids.SPRING_WATER) return new ItemStack(SpelunkeryBucketemItems.GOLDEN_SPRING_WATER_BUCKET);
        }
        return null;
    }

    public static ItemStack getFilledBucket(BlockState state) {
        if (state.getFluidState().isOf(Fluids.WATER)) return new ItemStack(ModItems.GOLDEN_WATER_BUCKET);
        if (state.getFluidState().isOf(Fluids.LAVA)) return new ItemStack(ModItems.GOLDEN_LAVA_BUCKET);
        if (state.isOf(Blocks.POWDER_SNOW)) return new ItemStack(ModItems.GOLDEN_POWDER_SNOW_BUCKET);
        if (IsModLoaded.isSpelunkeryModLoaded()){
            if (state.getFluidState().isOf(ModFluids.PORTAL_FLUID.get())) return new ItemStack(SpelunkeryBucketemItems.GOLDEN_PORTAL_FLUID_BUCKET);
            if (state.getFluidState().isOf(ModFluids.SPRING_WATER.get())) return new ItemStack(SpelunkeryBucketemItems.GOLDEN_SPRING_WATER_BUCKET);
        }
        return null;
    }

    @Override
    public ItemStack getRecipeRemainder(ItemStack stack) {
        int level = stack.getOrCreateNbt().getInt(NBT_TAG);
        ItemStack newStack = getFilledBucket(this.getFluid());
        if (level > 0 && newStack != null) {
            setFluidLevel(newStack, level - 1);
            return newStack;
        }
        return getEmptyBucket();
    }

    public static ItemStack getEmptyBucket() {
        return resetFluidLevel(new ItemStack(ModItems.GOLDEN_BUCKET));
    }

    @Override
    public boolean placeFluid(@Nullable PlayerEntity player, World world, BlockPos pos, @Nullable BlockHitResult hitResult) {
        if (!(this.getFluid() instanceof FlowableFluid)) {
            return false;
        } else {
            BlockState state = world.getBlockState(pos);
            Block block = state.getBlock();
            boolean replaceable = state.canBucketPlace(this.getFluid());
            if (!(state.isAir() || replaceable || block instanceof FluidFillable && ((FluidFillable) block).canFillWithFluid(world, pos, state, this.getFluid()))) {
                return hitResult != null && this.placeFluid(player, world, hitResult.getBlockPos().offset(hitResult.getSide()), null);
            } else if (world.getDimension().ultrawarm() && this.getFluid().isIn(FluidTags.WATER) || this.getFluid().getDefaultState().isOf(ModFluids.SPRING_WATER.get())) {
                int i = pos.getX();
                int j = pos.getY();
                int k = pos.getZ();
                world.playSound(player, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.5F, 2.6F + (world.random.nextFloat() - world.random.nextFloat()) * 0.8F);
                for (int l = 0; l < 8; ++l) {
                    world.addParticle(ParticleTypes.LARGE_SMOKE, (double) i + Math.random(), (double) j + Math.random(), (double) k + Math.random(), 0.0D, 0.0D, 0.0D);
                }
                return true;
                //Spelunkery
            } else if (IsModLoaded.isSpelunkeryModLoaded() && world.getDimension().ultrawarm() && this.getFluid().getDefaultState().isOf(ModFluids.SPRING_WATER.get())) {
                int i = pos.getX();
                int j = pos.getY();
                int k = pos.getZ();
                world.playSound(player, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.5F, 2.6F + (world.random.nextFloat() - world.random.nextFloat()) * 0.8F);
                for (int l = 0; l < 8; ++l) {
                    world.addParticle(ParticleTypes.LARGE_SMOKE, (double) i + Math.random(), (double) j + Math.random(), (double) k + Math.random(), 0.0D, 0.0D, 0.0D);
                }
                return true;
            }
            else if (block instanceof FluidFillable && ((FluidFillable) block).canFillWithFluid(world, pos, state, getFluid())) {
                ((FluidFillable) block).canFillWithFluid(world, pos, state, ((FlowableFluid) this.getFluid()).getStill(false).getFluid());
                this.playEmptySound(player, world, pos);
                return true;
            } else {
                if (!world.isClient() && replaceable) {
                    world.breakBlock(pos, true);
                }
                if (!world.setBlockState(pos, this.getFluid().getDefaultState().getBlockState(), 11) && !state.getFluidState().isStill()) {
                    return false;
                } else {
                    this.playEmptySound(player, world, pos);
                    return true;
                }
            }
        }
    }
}