package com.qzimyion.bucketem.core.mixin.ItemMixins;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BucketItem.class)
public class BucketItemMixin {

    @Inject(at = @At("RETURN"), method = "getEmptySuccessItem", cancellable = true)
    private static void getEmptySuccessItem(ItemStack itemStack, Player player, CallbackInfoReturnable<ItemStack> cir){
        if (!player.getAbilities().instabuild) {
            if (itemStack.getCount() > 1) {
                itemStack.shrink(1);
                ItemStack emptyBucket = new ItemStack(Items.BUCKET);
                if (!player.getInventory().add(emptyBucket)) {
                    player.drop(emptyBucket, false);
                }
                cir.setReturnValue(itemStack);
            } else {
                cir.setReturnValue(new ItemStack(Items.BUCKET));
            }
        } else {
            cir.setReturnValue(itemStack.copy());
        }
    }
}
