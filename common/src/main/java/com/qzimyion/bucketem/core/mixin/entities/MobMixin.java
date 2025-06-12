package com.qzimyion.bucketem.core.mixin.entities;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.qzimyion.bucketem.util.BuckEmBucketable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.Animal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mob.class)
public class MobMixin {

    @Unique
    private final Mob bucketem$me = (Mob) (Object) this;

    @ModifyReturnValue(method = "requiresCustomPersistence", at = @At("RETURN"))
    private boolean modifyCustomPersistence(boolean original) {
        return original || (bucketem$me instanceof BuckEmBucketable bucketable && bucketable.fromBucket());
    }

    @ModifyReturnValue(method = "removeWhenFarAway", at = @At("RETURN"))
    private boolean modifyRemoveWhenFarAway(boolean original) {
        return (bucketem$me instanceof BuckEmBucketable bucketable
                && !(bucketem$me instanceof Animal)
                && !bucketable.fromBucket() && !bucketem$me.hasCustomName()
        ) || original;
    }

    @Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
    private void addBucketableData(CompoundTag tag, CallbackInfo ci) {
        if (bucketem$me instanceof BuckEmBucketable bucketable) {
            tag.putBoolean(BuckEmBucketable.FROM_BOTTLE, bucketable.fromBucket());
        }
    }

    @Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
    private void readBucketableData(CompoundTag tag, CallbackInfo ci) {
        if (bucketem$me instanceof BuckEmBucketable bucketable) {
            bucketable.setFromBucket(tag.getBoolean(BuckEmBucketable.FROM_BOTTLE));

            if (tag.contains(BuckEmBucketable.FROM_BOTTLE_LEGACY, Tag.TAG_BYTE)) { // Booleans are stored as bytes
                bucketable.setFromBucket(tag.getBoolean(BuckEmBucketable.FROM_BOTTLE_LEGACY));
            }
        }
    }
}
