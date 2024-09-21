package com.qzimyion.bucketem.dispenser.behaviors;

import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.ItemDispenserBehavior;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.Direction;

public class MagmaCubeBottleBehavior extends ItemDispenserBehavior {

    @Override
    protected ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
        Direction direction = pointer.getBlockState().get(DispenserBlock.FACING);
        SlimeEntity entity = EntityType.MAGMA_CUBE.spawnFromItemStack(pointer.getWorld(), stack, null, pointer.getPos().offset(direction), SpawnReason.DISPENSER, direction != Direction.UP, false);
        if (entity != null) {
            entity.setPersistent();
            entity.setSize(1, false);
        }
        return new ItemStack(Items.GLASS_BOTTLE);
    }
}
