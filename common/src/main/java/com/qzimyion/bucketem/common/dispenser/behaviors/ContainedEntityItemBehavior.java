package com.qzimyion.bucketem.common.dispenser.behaviors;

import com.qzimyion.bucketem.common.items.ContainedEntityItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.DispenserBlock;

public class ContainedEntityItemBehavior extends DefaultDispenseItemBehavior {

    @Override
    protected ItemStack execute(BlockSource source, ItemStack stack) {
        Direction direction = source.getBlockState().getValue(DispenserBlock.FACING);
        if (stack.getItem() instanceof ContainedEntityItem item) {
            ServerLevel level = source.getLevel();
            BlockPos relative = source.getPos().relative(direction);
            item.spawnEntity(level, stack, level.getBlockState(relative), relative, direction, null);
        }
        return new ItemStack(Items.BOOK);
    }
}
