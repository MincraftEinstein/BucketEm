package com.qzimyion.bucketem.mixin.BlockEntityMixins;

import com.qzimyion.bucketem.items.ModItems;
import com.qzimyion.bucketem.items.NewItems.goldBuckets.GoldenBucketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.FurnaceFuelSlot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FurnaceFuelSlot.class)
public class FurnaceFuelSlotMixin {

    @Inject(at = @At("RETURN"), method = "isBucket", cancellable = true)
    private static void isBucket(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if (stack.isOf(ModItems.GOLDEN_BUCKET) || stack.isOf(ModItems.GOLDEN_WATER_BUCKET) && GoldenBucketItem.canBeFilled(stack)) {
            cir.setReturnValue(true);
        }
    }
}
