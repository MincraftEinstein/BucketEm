package com.qzimyion.bucketem.items.NewItems.GoldBuckets;

import com.qzimyion.bucketem.items.ModItems;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PowderSnowBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.FluidModificationItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class PowderSnowGoldenBucket extends BlockItem implements FluidModificationItem {
    private final SoundEvent placeSound;

    public PowderSnowGoldenBucket(Block block, SoundEvent placeSound, Settings settings) {
        super(block, settings);
        this.placeSound = placeSound;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        ItemStack stack = context.getStack();
        PlayerEntity player = context.getPlayer();
        Hand hand = context.getHand();
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        BlockState state = world.getBlockState(pos);
        int fluidLevel = stack.getOrCreateNbt().getInt("FluidLevel");
        if (state.getBlock() instanceof PowderSnowBlock powderSnowBlock && player != null && !player.isSneaking() && fluidLevel < 2) {
            powderSnowBlock.tryDrainFluid(world, pos, state);
            player.playSound(SoundEvents.ITEM_BUCKET_FILL_POWDER_SNOW, 1.0F, 1.0F);
            stack.getOrCreateNbt().putInt("FluidLevel", fluidLevel + 1);
            if (!world.isClient) {
                Criteria.FILLED_BUCKET.trigger((ServerPlayerEntity) player, stack);
            }
            return ActionResult.success(world.isClient());
        }
        ActionResult result = super.useOnBlock(new ItemUsageContext(world, player, hand, stack.copy(), new BlockHitResult(context.getHitPos(), context.getSide(), pos, context.hitsInsideBlock())));
        if (result.isAccepted() && player != null && !player.isCreative()) {
            if (fluidLevel > 0) {
                stack.getOrCreateNbt().putInt("FluidLevel", fluidLevel - 1);
            } else {
                stack = GoldenBucketItem.getEmptyBucket();
            }
            player.setStackInHand(hand, stack);
        }
        return result;
    }

    @Override
    public String getTranslationKey() {
        return this.getOrCreateTranslationKey();
    }

    @Override
    public boolean placeFluid(@Nullable PlayerEntity player, World world, BlockPos pos, @Nullable BlockHitResult hitResult) {
        if (world.isInBuildLimit(pos) && world.isAir(pos)) {
            if (!world.isClient) {
                world.setBlockState(pos, this.getBlock().getDefaultState(), 3);
            }

            world.playSound(player, pos, this.placeSound, SoundCategory.BLOCKS, 1.0F, 1.0F);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public ItemStack getRecipeRemainder(ItemStack stack) {
        int level = stack.getOrCreateNbt().getInt("FluidLevel");
        if (level > 0) {
            ItemStack newStack = new ItemStack(ModItems.GOLDEN_POWDER_SNOW_BUCKET);
            newStack.getOrCreateNbt().putInt("FluidLevel", level - 1);
            return newStack;
        }
        return GoldenBucketItem.getEmptyBucket();
    }
}
