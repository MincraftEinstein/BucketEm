package com.qzimyion.bucketem.core.mixin.items;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Items.class)
public class ItemsMixin {

    @WrapOperation(method = "<clinit>", at = @At(value = "NEW", target = "(Lnet/minecraft/world/level/material/Fluid;Lnet/minecraft/world/item/Item$Properties;)Lnet/minecraft/world/item/BucketItem;"))
    private static BucketItem modifyBucketStackSize(Fluid fluid, Item.Properties properties, Operation<BucketItem> original) {
        return original.call(fluid, properties.stacksTo(fluid == Fluids.EMPTY ? 64 : 16));
    }

    @WrapOperation(method = "<clinit>", at = @At(value = "NEW", target = "(Lnet/minecraft/world/level/block/Block;Lnet/minecraft/sounds/SoundEvent;Lnet/minecraft/world/item/Item$Properties;)Lnet/minecraft/world/item/SolidBucketItem;"))
    private static SolidBucketItem modifySolidBucketStackSize(Block block, SoundEvent soundEvent, Item.Properties properties, Operation<SolidBucketItem> original) {
        return original.call(block, soundEvent, properties.stacksTo(16));
    }

    @WrapOperation(method = "<clinit>", at = @At(value = "NEW", target = "(Lnet/minecraft/world/item/Item$Properties;)Lnet/minecraft/world/item/MilkBucketItem;"))
    private static MilkBucketItem modifyMilkBucketStackSize(Item.Properties properties, Operation<MilkBucketItem> original) {
        return original.call(properties.stacksTo(16));
    }
}
