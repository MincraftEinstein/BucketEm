package com.qzimyion.bucketem.core.mixin.items;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MilkBucketItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(MilkBucketItem.class)
public class MilkBucketItemMixin {

    @ModifyReturnValue(method = "finishUsingItem", at = @At("RETURN"))
    private ItemStack finishUsingItem(ItemStack original, @Local(argsOnly = true) ItemStack stack, @Local LivingEntity entity) {
        if (stack.isEmpty()) {
            return new ItemStack(Items.BUCKET);
        }

        if (entity instanceof Player player && !player.getAbilities().instabuild) {
            ItemStack emptyBucket = new ItemStack(Items.BUCKET);
            if (!player.getInventory().add(emptyBucket)) {
                player.drop(emptyBucket, false);
            }
        }
        return stack;
    }
}
