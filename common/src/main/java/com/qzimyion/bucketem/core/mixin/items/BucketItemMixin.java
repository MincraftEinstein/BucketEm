package com.qzimyion.bucketem.core.mixin.items;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BucketItem.class)
public class BucketItemMixin {

    @ModifyReturnValue(method = "getEmptySuccessItem", at = @At("RETURN"))
    private static ItemStack getEmptySuccessItem(ItemStack original, ItemStack stack, Player player) {
        if (!player.getAbilities().instabuild) {
            if (stack.getCount() > 1) {
                stack.shrink(1);
                ItemStack emptyBucket = new ItemStack(Items.BUCKET);
                if (!player.getInventory().add(emptyBucket)) {
                    player.drop(emptyBucket, false);
                }
                return stack;
            }
            return new ItemStack(Items.BUCKET);
        }
        return stack;
    }
}
