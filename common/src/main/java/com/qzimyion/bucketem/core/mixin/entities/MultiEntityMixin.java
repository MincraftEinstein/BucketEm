package com.qzimyion.bucketem.core.mixin.entities;

import com.qzimyion.bucketem.util.BuckEmBucketable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.GlowSquid;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.animal.Squid;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.entity.animal.allay.Allay;
import net.minecraft.world.entity.animal.frog.Frog;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@SuppressWarnings("deprecation")
@Mixin({
        Strider.class,
        GlowSquid.class,
        Squid.class,
        Turtle.class,
        Frog.class,
        Silverfish.class,
        Endermite.class,
        Slime.class,
        MagmaCube.class,
        Allay.class,
        Vex.class,
        Bee.class
})
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

        if (bucketem$me instanceof Bee bee) {
            CompoundTag tag = stack.getOrCreateTag();
            tag.putBoolean("HasNectar", bee.hasNectar());
            tag.putBoolean("HasStung", bee.hasStung());
            tag.putInt("Age", bee.getAge());
            tag.putFloat("Health", bee.getHealth());
            bee.addPersistentAngerSaveData(tag);
        }
    }

    @Override
    public void loadFromBucketTag(CompoundTag tag) {
        Bucketable.loadDefaultDataFromBucketTag(bucketem$me, tag);

        if (bucketem$me instanceof Bee bee) {
            bee.setHasNectar(tag.getBoolean("HasNectar"));
            bee.setHasStung(tag.getBoolean("HasStung"));
            bee.setAge(tag.getInt("Age"));
            bee.setHealth(tag.getFloat("Health"));
            bee.readPersistentAngerSaveData(bee.level(), tag);

            if (tag.contains("Anger", Tag.TAG_INT)) {
                bee.setRemainingPersistentAngerTime(tag.getInt("Anger"));
            }
        }
    }
}
