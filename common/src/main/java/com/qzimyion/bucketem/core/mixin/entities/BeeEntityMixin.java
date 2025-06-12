package com.qzimyion.bucketem.core.mixin.entities;

import com.qzimyion.bucketem.util.BuckEmBucketable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@SuppressWarnings("deprecation")
@Mixin(Bee.class)
public abstract class BeeEntityMixin implements BuckEmBucketable {

    @Unique
    private boolean bucketem$fromBottle;

    @Unique
    private final Bee bucketem$me = (Bee) (Object) this;

    @Override
    public boolean fromBucket() {
        return bucketem$fromBottle;
    }

    @Override
    public void setFromBucket(boolean fromBucket) {
        bucketem$fromBottle = fromBucket;
    }

    @Override
    public void saveToBucketTag(ItemStack stack) {
        Bucketable.saveDefaultDataToBucketTag(bucketem$me, stack);
        CompoundTag tag = stack.getOrCreateTag();
        tag.putBoolean("HasNectar", bucketem$me.hasNectar());
        tag.putBoolean("HasStung", bucketem$me.hasStung());
        tag.putInt("Age", bucketem$me.getAge());
        tag.putFloat("Health", bucketem$me.getHealth());
        bucketem$me.addPersistentAngerSaveData(tag);
    }

    @Override
    public void loadFromBucketTag(CompoundTag tag) {
        Bucketable.loadDefaultDataFromBucketTag(bucketem$me, tag);
        bucketem$me.setHasNectar(tag.getBoolean("HasNectar"));
        bucketem$me.setHasStung(tag.getBoolean("HasStung"));
        bucketem$me.setAge(tag.getInt("Age"));
        bucketem$me.setHealth(tag.getFloat("Health"));
        bucketem$me.readPersistentAngerSaveData(bucketem$me.level(), tag);

        if (tag.contains("Anger", Tag.TAG_INT)) {
            bucketem$me.setRemainingPersistentAngerTime(tag.getInt("Anger"));
        }
    }
}
