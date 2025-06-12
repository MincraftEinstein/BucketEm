package com.qzimyion.bucketem.common.items;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class SlimeLikeBottleItem extends ContainedEntityItem {

    public SlimeLikeBottleItem(EntityType<?> type, Item containerItem, SoundEvent soundEvent, Properties properties) {
        super(type, containerItem, soundEvent, properties);
    }

    @Override
    protected void loadEntity(Entity entity, ItemStack stack) {
        super.loadEntity(entity, stack);
        if (entity instanceof Slime slime) {
            slime.setSize(1, false);
        }
    }
}
