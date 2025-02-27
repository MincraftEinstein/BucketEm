package com.qzimyion.bucketem.forge.mixin;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.MobBucketItem;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.function.Supplier;

@Debug(export = true)
@Mixin(MobBucketItem.class)
public interface EntityBucketItemAccessor {

    @Accessor("entityTypeSupplier")
    Supplier<? extends EntityType<?>> type();
}
