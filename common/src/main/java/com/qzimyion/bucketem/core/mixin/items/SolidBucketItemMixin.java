package com.qzimyion.bucketem.core.mixin.items;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SolidBucketItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(SolidBucketItem.class)
public class SolidBucketItemMixin {

    @WrapWithCondition(method = "useOn", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;setItemInHand(Lnet/minecraft/world/InteractionHand;Lnet/minecraft/world/item/ItemStack;)V"))
    private boolean addOrDropBucket(Player player, InteractionHand hand, ItemStack stack) {
        if (!player.getAbilities().instabuild) {
            ItemStack emptyBucket = new ItemStack(Items.BUCKET);
            if (!player.getInventory().add(emptyBucket)) {
                player.drop(emptyBucket, false);
            }
            return false;
        }
        return true;
    }
}
