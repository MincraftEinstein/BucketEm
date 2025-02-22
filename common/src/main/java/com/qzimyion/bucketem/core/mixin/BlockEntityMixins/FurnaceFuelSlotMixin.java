package com.qzimyion.bucketem.core.mixin.BlockEntityMixins;

import com.qzimyion.bucketem.common.items.GoldBuckets.GoldenBucketItem;
import com.qzimyion.bucketem.core.registry.ModItems;
import net.minecraft.world.inventory.FurnaceFuelSlot;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FurnaceFuelSlot.class)
public class FurnaceFuelSlotMixin {

    @Inject(at = @At("RETURN"), method = "isBucket", cancellable = true)
    private static void isBucket(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if (stack.is(ModItems.GOLDEN_BUCKET.get()) || stack.is(ModItems.GOLDEN_WATER_BUCKET.get()) && GoldenBucketItem.canBeFilled(stack)) {
            cir.setReturnValue(true);
        }
    }
}
