package com.qzimyion.bucketem.common.items.GoldBuckets;

//import com.ordana.spelunkery.reg.ModFluids;
import com.qzimyion.bucketem.core.registry.ModItems;
import dev.architectury.hooks.item.ItemStackHooks;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DispensibleContainerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class GoldenBucketItem extends Item implements DispensibleContainerItem {
    public final Fluid fluid;
    public static final String NBT_TAG = "FluidLevel";

    public GoldenBucketItem(Fluid fluid, Properties settings) {
        super(settings);
        this.fluid = fluid;
    }

    public static ItemStack resetFluidLevel(ItemStack stack) {
        stack.getOrCreateTag().putInt(NBT_TAG, 0);
        return stack;
    }

    public static void setFluidLevel(ItemStack stack, int level) {
        stack.getOrCreateTag().putInt(NBT_TAG, level);
    }

    public static void decreaseFluidLevel(ItemStack stack) {
        int level = stack.getOrCreateTag().getInt(NBT_TAG);
        if (level > 0) stack.getOrCreateTag().putInt(NBT_TAG, level - 1);
    }

    public static ItemStack increaseFluidLevel(ItemStack stack) {
        int level = stack.getOrCreateTag().getInt(NBT_TAG);
        if (level < 3) stack.getOrCreateTag().putInt(NBT_TAG, level + 1);
        return stack;
    }

    public static boolean canBeFilled(ItemStack stack) {
        return stack.getOrCreateTag().getInt(NBT_TAG) < 2;
    }

    @Override
    public @Nullable Item getCraftingRemainingItem() {
        return ItemStackHooks.getCraftingRemainingItem(getRecipeRemainder(new ItemStack(this))).getItem();
    }

    public ItemStack getRecipeRemainder(ItemStack stack) {
        int level = stack.getOrCreateTag().getInt(NBT_TAG);
        ItemStack newStack = getFilledBucket(this.getFluid());
        if (level > 0 && newStack != null) {
            setFluidLevel(newStack, level - 1);
            return newStack;
        }
        return getEmptyBucket();
    }

    @Override
    public @NotNull ItemStack getDefaultInstance() {
        return resetFluidLevel(new ItemStack(this));
    }

    @Override
    public void onCraftedBy(ItemStack stack, Level world, Player player) {
        resetFluidLevel(stack);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        CompoundTag tag = stack.getOrCreateTag();
        int bucketLevel = tag.getInt(NBT_TAG);


        BlockHitResult result = getPlayerPOVHitResult(world, player, (this.getFluid() == Fluids.EMPTY || bucketLevel < 2) && !(player.isShiftKeyDown() && this.getFluid() != Fluids.EMPTY) ? ClipContext.Fluid.SOURCE_ONLY : ClipContext.Fluid.NONE);
        if (result.getType() == HitResult.Type.MISS) {
            return InteractionResultHolder.pass(stack);
        } else if (result.getType() != HitResult.Type.BLOCK) {
            return InteractionResultHolder.pass(stack);
        } else {
            BlockPos pos = result.getBlockPos();
            Direction direction = result.getDirection();
            BlockPos sourcePos = pos.relative(direction);
            if (world.mayInteract(player, pos) && player.mayUseItemAt(sourcePos, direction, stack)) {
                BlockState sourceState = world.getBlockState(pos);
                if (this.getFluid() == Fluids.EMPTY || (bucketLevel < 2 && sourceState.getFluidState().getType() == this.getFluid())) {
                    // Picking up raw fluids & powder snow
                    if (sourceState.getBlock() instanceof BucketPickup bucketPickup) {
                        bucketPickup.pickupBlock(world, pos, sourceState);
                        Fluid fluid = sourceState.getFluidState().is(Fluids.WATER) ? Fluids.WATER : sourceState.getFluidState().is(Fluids.LAVA) ? Fluids.LAVA : Fluids.EMPTY;
                        ItemStack newBucket = ItemStack.EMPTY;

//                        //Spelunkery
//                        if (IsModLoaded.isSpelunkeryModLoaded()){
//                            fluid = sourceState.getFluidState().isOf(ModFluids.PORTAL_FLUID.get()) ? ModFluids.PORTAL_FLUID.get() : sourceState.getFluidState().isOf(ModFluids.SPRING_WATER.get()) ? ModFluids.SPRING_WATER.get() : Fluids.EMPTY;
//                        }

                        if (fluid != Fluids.EMPTY && getFilledBucket(sourceState) != null) {
                            newBucket = ItemUtils.createFilledResult(stack, player, getFilledBucket(sourceState));
                            if (this.getFluid() != Fluids.EMPTY)
                                setFluidLevel(newBucket, bucketLevel + 1);
                        }

                        if (sourceState.is(Blocks.POWDER_SNOW)) {
                            newBucket = ItemUtils.createFilledResult(stack, player, new ItemStack(ModItems.GOLDEN_POWDER_SNOW_BUCKET.get()));
                        }

                        if (!newBucket.isEmpty()) {
                            player.awardStat(Stats.ITEM_USED.get(this));
                            bucketPickup.getPickupSound().ifPresent((soundEvent) -> player.playSound(soundEvent, 1.0F, 1.0F));
                            world.gameEvent(player, GameEvent.FLUID_PICKUP, pos);
                            if (!world.isClientSide) {
                                CriteriaTriggers.FILLED_BUCKET.trigger((ServerPlayer) player, newBucket);
                            }

                            return InteractionResultHolder.sidedSuccess(newBucket, world.isClientSide());
                        }
                    }

                    return InteractionResultHolder.fail(stack);
                } else {
                    result = getPlayerPOVHitResult(world, player, ClipContext.Fluid.NONE);
                    if (result.getType() == HitResult.Type.MISS) {
                        return InteractionResultHolder.pass(stack);
                    } else if (result.getType() != HitResult.Type.BLOCK) {
                        return InteractionResultHolder.pass(stack);
                    }

                    // Placing contents
                    pos = result.getBlockPos();
                    direction = result.getDirection();
                    sourcePos = pos.relative(direction);
                    BlockState state = world.getBlockState(pos);
                    BlockPos newPos = canBlockContainFluid(world, pos, state, player) || (this.getFluid() != Fluids.EMPTY && bucketLevel < 2) ? pos : sourcePos;
                    if (this.emptyContents(player, world, newPos, result)) {
                        player.awardStat(Stats.ITEM_USED.get(this));
                        return InteractionResultHolder.sidedSuccess(getEmptySuccessItem(stack, player), world.isClientSide());
                    } else {
                        return InteractionResultHolder.fail(stack);
                    }
                }
            } else {
                return InteractionResultHolder.fail(stack);
            }
        }
    }

    public static ItemStack getEmptySuccessItem(ItemStack stack, Player player) {
        int level = stack.getOrCreateTag().getInt(NBT_TAG);
        ItemStack returnStack = level > 0 ? stack : getEmptyBucket();
        if (player == null || !player.getAbilities().instabuild)
            decreaseFluidLevel(returnStack);
        return player == null || !player.getAbilities().instabuild ? returnStack : stack;
    }

    protected void playEmptySound(Player player, Level world, BlockPos pos) {
        SoundEvent soundevent;
        soundevent = this.getFluid().is(FluidTags.LAVA) ? SoundEvents.BUCKET_EMPTY_LAVA : SoundEvents.BUCKET_EMPTY;
        world.playSound(player, pos, soundevent, SoundSource.BLOCKS, 1.0F, 1.0F);
        world.gameEvent(player, GameEvent.FLUID_PLACE, pos);
    }

    public Fluid getFluid() {
        return fluid;
    }

    private boolean canBlockContainFluid(Level worldIn, BlockPos posIn, BlockState blockstate, Player player) {
        return blockstate.getBlock() instanceof LiquidBlockContainer && ((LiquidBlockContainer) blockstate.getBlock()).canPlaceLiquid(worldIn, posIn, blockstate, this.getFluid());
    }


    public static ItemStack getFilledBucket(Fluid fluid) {
        if (fluid == Fluids.WATER) return new ItemStack(ModItems.GOLDEN_WATER_BUCKET.get());
        if (fluid == Fluids.LAVA) return new ItemStack(ModItems.GOLDEN_LAVA_BUCKET.get());
//        if (IsModLoaded.isSpelunkeryModLoaded()){
//            if (fluid == ModFluids.PORTAL_FLUID) return new ItemStack(SpelunkeryBucketemItems.GOLDEN_PORTAL_FLUID_BUCKET);
//            if (fluid == ModFluids.SPRING_WATER) return new ItemStack(SpelunkeryBucketemItems.GOLDEN_SPRING_WATER_BUCKET);
//        }
        return null;
    }

    public static ItemStack getFilledBucket(BlockState state) {
        if (state.getFluidState().is(Fluids.WATER)) return new ItemStack(ModItems.GOLDEN_WATER_BUCKET.get());
        if (state.getFluidState().is(Fluids.LAVA)) return new ItemStack(ModItems.GOLDEN_LAVA_BUCKET.get());
        if (state.is(Blocks.POWDER_SNOW)) return new ItemStack(ModItems.GOLDEN_POWDER_SNOW_BUCKET.get());
//        if (IsModLoaded.isSpelunkeryModLoaded()){
//            if (state.getFluidState().isOf(ModFluids.PORTAL_FLUID.get())) return new ItemStack(SpelunkeryBucketemItems.GOLDEN_PORTAL_FLUID_BUCKET);
//            if (state.getFluidState().isOf(ModFluids.SPRING_WATER.get())) return new ItemStack(SpelunkeryBucketemItems.GOLDEN_SPRING_WATER_BUCKET);
//        }
        return null;
    }

    public static ItemStack getEmptyBucket() {
        return resetFluidLevel(new ItemStack(ModItems.GOLDEN_BUCKET.get()));
    }

    @Override
    public boolean emptyContents(@Nullable Player player, Level world, BlockPos pos, @Nullable BlockHitResult hitResult) {
        if (!(this.getFluid() instanceof FlowingFluid)) {
            return false;
        } else {
            BlockState state = world.getBlockState(pos);
            Block block = state.getBlock();
            boolean replaceable = state.canBeReplaced(this.getFluid());
            if (!(state.isAir() || replaceable || block instanceof LiquidBlockContainer && ((LiquidBlockContainer) block).canPlaceLiquid(world, pos, state, this.getFluid()))) {
                return hitResult != null && this.emptyContents(player, world, hitResult.getBlockPos().relative(hitResult.getDirection()), null);
            } else if (world.dimensionType().ultraWarm() && this.getFluid().is(FluidTags.WATER)) {
                int i = pos.getX();
                int j = pos.getY();
                int k = pos.getZ();
                world.playSound(player, pos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 0.5F, 2.6F + (world.random.nextFloat() - world.random.nextFloat()) * 0.8F);
                for (int l = 0; l < 8; ++l) {
                    world.addParticle(ParticleTypes.LARGE_SMOKE, (double) i + Math.random(), (double) j + Math.random(), (double) k + Math.random(), 0.0D, 0.0D, 0.0D);
                }
                return true;
                //Spelunkery
            }
//            else if (IsModLoaded.isSpelunkeryModLoaded() && world.getDimension().ultrawarm() && this.getFluid().getDefaultState().isOf(ModFluids.SPRING_WATER.get())) {
//                int i = pos.getX();
//                int j = pos.getY();
//                int k = pos.getZ();
//                world.playSound(player, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.5F, 2.6F + (world.random.nextFloat() - world.random.nextFloat()) * 0.8F);
//                for (int l = 0; l < 8; ++l) {
//                    world.addParticle(ParticleTypes.LARGE_SMOKE, (double) i + Math.random(), (double) j + Math.random(), (double) k + Math.random(), 0.0D, 0.0D, 0.0D);
//                }
//                return true;
            //}
            else if (block instanceof LiquidBlockContainer && ((LiquidBlockContainer) block).canPlaceLiquid(world, pos, state, getFluid())) {
                ((LiquidBlockContainer) block).canPlaceLiquid(world, pos, state, ((FlowingFluid) this.getFluid()).getSource(false).getType());
                this.playEmptySound(player, world, pos);
                return true;
            } else {
                if (!world.isClientSide() && replaceable) {
                    world.destroyBlock(pos, true);
                }
                if (!world.setBlock(pos, this.getFluid().defaultFluidState().createLegacyBlock(), 11) && !state.getFluidState().isSource()) {
                    return false;
                } else {
                    this.playEmptySound(player, world, pos);
                    return true;
                }
            }
        }
    }
}