package com.qzimyion.bucketem.common.items.frogs;

import com.qzimyion.bucketem.common.items.ContainedEntityItem;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.FrogVariant;
import net.minecraft.world.entity.animal.frog.Frog;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class DryFrogBucketItem extends ContainedEntityItem {

    private final FrogVariant variant;

    public DryFrogBucketItem(FrogVariant variant, Properties properties) {
        super(EntityType.FROG, Items.BUCKET, SoundEvents.BUCKET_FILL_FISH, properties);
        this.variant = variant;
    }

    @Override
    protected void loadEntity(Entity entity, ItemStack stack) {
        super.loadEntity(entity, stack);

        if (entity instanceof Frog frog) {
            frog.setVariant(variant);
        }
    }
}
