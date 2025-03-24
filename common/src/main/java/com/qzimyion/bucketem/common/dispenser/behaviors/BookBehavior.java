package com.qzimyion.bucketem.common.dispenser.behaviors;

import com.qzimyion.bucketem.common.items.EntityBottleItem;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.DispenserBlock;
import org.jetbrains.annotations.NotNull;

public class BookBehavior extends DefaultDispenseItemBehavior {

    @Override
    protected @NotNull ItemStack execute(BlockSource pointer, ItemStack stack) {
        Direction direction = pointer.state().getValue(DispenserBlock.FACING);
        EntityType<?> entitytype = ((EntityBottleItem) stack.getItem()).getType(stack);
        entitytype.spawn(pointer.level(), stack, null, pointer.pos().relative(direction), MobSpawnType.DISPENSER, direction != Direction.UP, false);
        return new ItemStack(Items.BOOK);
    }
}
