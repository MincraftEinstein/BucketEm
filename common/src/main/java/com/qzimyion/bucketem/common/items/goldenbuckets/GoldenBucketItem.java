package com.qzimyion.bucketem.common.items.goldenbuckets;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DispensibleContainerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class GoldenBucketItem extends Item implements DispensibleContainerItem {
    public final Fluid fluid;
    public static final String NBT_TAG = "FluidLevel";
    public GoldenBucketItem(Fluid fluid ,Properties properties) {
        super(properties);
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
    public @NotNull ItemStack getDefaultInstance() {
        return resetFluidLevel(new ItemStack(this));
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        return super.use(level, player, interactionHand);
    }

    @Override
    public boolean emptyContents(@Nullable Player player, Level level, BlockPos blockPos, @Nullable BlockHitResult blockHitResult) {
        return false;
    }
}
