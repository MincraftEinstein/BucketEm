package com.qzimyion.bucketem.core.mixin.ItemMixins;

import dev.architectury.injectables.annotations.PlatformOnly;
import net.minecraft.world.InteractionHand;
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

    @PlatformOnly("fabric")
    @Inject(at = @At("TAIL"), method = "getEmptySuccessItem", cancellable = true)
    private static void getEmptySuccessItem(ItemStack itemStack, Player player, CallbackInfoReturnable<ItemStack> cir){
        InteractionHand hand = player.getUsedItemHand();
        ItemStack stack = player.getItemInHand(hand);
        if (!player.hasInfiniteMaterials() && stack.getCount() > 1) {
            stack.shrink(1);
            ItemStack emptyBucket = new ItemStack(Items.BUCKET);
            if (!player.getInventory().add(emptyBucket)) {
                player.drop(emptyBucket, false);
            }
            cir.setReturnValue(stack);
        }
    }
}
