package com.qzimyion.bucketem.common.dispenser.behaviors;

import com.qzimyion.bucketem.common.items.EntityBottleItem;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.DispenserBlock;

public class BookBehavior extends DefaultDispenseItemBehavior {

    @Override
    protected ItemStack execute(BlockSource pointer, ItemStack stack) {
        Direction direction = pointer.getBlockState().getValue(DispenserBlock.FACING);
        EntityType<?> entitytype = ((EntityBottleItem) stack.getItem()).getType(stack.getTag());
        entitytype.spawn(pointer.getLevel(), stack, null, pointer.getPos().relative(direction), MobSpawnType.DISPENSER, direction != Direction.UP, false);
        return new ItemStack(Items.BOOK);
    }
}
