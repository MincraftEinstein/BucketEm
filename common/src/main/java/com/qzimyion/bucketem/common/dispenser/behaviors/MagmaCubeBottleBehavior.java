package com.qzimyion.bucketem.common.dispenser.behaviors;

import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.DispenserBlock;

public class MagmaCubeBottleBehavior extends DefaultDispenseItemBehavior {

    @Override
    protected ItemStack execute(BlockSource pointer, ItemStack stack) {
        Direction direction = pointer.getBlockState().getValue(DispenserBlock.FACING);
        Slime entity = EntityType.MAGMA_CUBE.spawn(pointer.getLevel(), stack, null, pointer.getPos().relative(direction), MobSpawnType.DISPENSER, direction != Direction.UP, false);
        if (entity != null) {
            entity.setPersistenceRequired();
            entity.setSize(1, false);
        }
        return new ItemStack(Items.GLASS_BOTTLE);
    }
}
