package com.qzimyion.bucketem.mixin.BlockEntityMixins;

import com.qzimyion.bucketem.items.ModItems;
import com.qzimyion.bucketem.items.NewItems.GoldBuckets.GoldenBucketItem;
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
        if (stack.is(ModItems.GOLDEN_BUCKET) || stack.is(ModItems.GOLDEN_WATER_BUCKET) && GoldenBucketItem.canBeFilled(stack)) {
            cir.setReturnValue(true);
        }
    }
}
