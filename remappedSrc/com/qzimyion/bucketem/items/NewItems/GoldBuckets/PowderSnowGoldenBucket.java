package com.qzimyion.bucketem.items.NewItems.GoldBuckets;

import com.qzimyion.bucketem.items.ModItems;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DispensibleContainerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.PowderSnowBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class PowderSnowGoldenBucket extends BlockItem implements DispensibleContainerItem {
    private final SoundEvent placeSound;

    public PowderSnowGoldenBucket(Block block, SoundEvent placeSound, Properties settings) {
        super(block, settings);
        this.placeSound = placeSound;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        ItemStack stack = context.getItemInHand();
        Player player = context.getPlayer();
        InteractionHand hand = context.getHand();
        Level world = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = world.getBlockState(pos);
        int fluidLevel = stack.getOrCreateTag().getInt("FluidLevel");
        if (state.getBlock() instanceof PowderSnowBlock powderSnowBlock && player != null && !player.isShiftKeyDown() && fluidLevel < 2) {
            powderSnowBlock.pickupBlock(world, pos, state);
            player.playSound(SoundEvents.BUCKET_FILL_POWDER_SNOW, 1.0F, 1.0F);
            stack.getOrCreateTag().putInt("FluidLevel", fluidLevel + 1);
            if (!world.isClientSide) {
                CriteriaTriggers.FILLED_BUCKET.trigger((ServerPlayer) player, stack);
            }
            return InteractionResult.sidedSuccess(world.isClientSide());
        }
        InteractionResult result = super.useOn(new UseOnContext(world, player, hand, stack.copy(), new BlockHitResult(context.getClickLocation(), context.getClickedFace(), pos, context.isInside())));
        if (result.consumesAction() && player != null && !player.isCreative()) {
            if (fluidLevel > 0) {
                stack.getOrCreateTag().putInt("FluidLevel", fluidLevel - 1);
            } else {
                stack = GoldenBucketItem.getEmptyBucket();
            }
            player.setItemInHand(hand, stack);
        }
        return result;
    }

    @Override
    public String getDescriptionId() {
        return this.getOrCreateDescriptionId();
    }

    @Override
    public boolean emptyContents(@Nullable Player player, Level world, BlockPos pos, @Nullable BlockHitResult hitResult) {
        if (world.isInWorldBounds(pos) && world.isEmptyBlock(pos)) {
            if (!world.isClientSide) {
                world.setBlock(pos, this.getBlock().defaultBlockState(), 3);
            }

            world.playSound(player, pos, this.placeSound, SoundSource.BLOCKS, 1.0F, 1.0F);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public ItemStack getRecipeRemainder(ItemStack stack) {
        int level = stack.getOrCreateTag().getInt("FluidLevel");
        if (level > 0) {
            ItemStack newStack = new ItemStack(ModItems.GOLDEN_POWDER_SNOW_BUCKET);
            newStack.getOrCreateTag().putInt("FluidLevel", level - 1);
            return newStack;
        }
        return GoldenBucketItem.getEmptyBucket();
    }
}
