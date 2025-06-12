package com.qzimyion.bucketem.core.mixin.entities;

import com.qzimyion.bucketem.util.BuckEmBucketable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.GlowSquid;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.animal.Squid;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.entity.monster.Silverfish;
import net.minecraft.world.entity.monster.Strider;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@SuppressWarnings("deprecation")
@Mixin({Strider.class, GlowSquid.class, Squid.class, Silverfish.class, Turtle.class})
public abstract class MultiEntityMixin implements BuckEmBucketable {

    @Unique
    private boolean bucketem$fromBottle;

    @Unique
    private final Mob bucketem$me = (Mob) (Object) this;

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
    }

    @Override
    public void loadFromBucketTag(CompoundTag tag) {
        Bucketable.loadDefaultDataFromBucketTag(bucketem$me, tag);
    }
}
