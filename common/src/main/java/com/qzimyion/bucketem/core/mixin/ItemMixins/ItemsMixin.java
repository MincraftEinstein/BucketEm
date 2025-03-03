package com.qzimyion.bucketem.core.mixin.ItemMixins;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Items.class)
public class ItemsMixin {

    @Redirect(method = "<clinit>", at = @At(value = "NEW", target = "Lnet/minecraft/world/item/BucketItem;"))
    private static BucketItem BucketCount(Fluid fluid, Item.Properties properties) {
        if (fluid == Fluids.EMPTY) {
            return new BucketItem(fluid, properties.stacksTo(64));
        }
        return new BucketItem(fluid, properties.stacksTo(16));
    }

    @Redirect(method = "<clinit>", at = @At(value = "NEW", target = "Lnet/minecraft/world/item/SolidBucketItem;"))
    private static SolidBucketItem SolidBucketCount(Block block, SoundEvent soundEvent, Item.Properties properties) {
        return new SolidBucketItem(block, soundEvent, properties.stacksTo(16));
    }

    @Redirect(method = "<clinit>", at = @At(value = "NEW", target = "Lnet/minecraft/world/item/MilkBucketItem;"))
    private static MilkBucketItem MilkBucketCount(Item.Properties properties) {
        return new MilkBucketItem(properties.stacksTo(16));
    }
}
