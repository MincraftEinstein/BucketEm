package com.qzimyion.bucketem.core.mixin.ItemMixins;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MilkBucketItem;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MilkBucketItem.class)
public class MilkBucketItemMixin {

    @Inject(at = @At("TAIL"), method = "finishUsingItem", cancellable = true)
    public void finishUsingItem(ItemStack itemStack, Level level, LivingEntity livingEntity, CallbackInfoReturnable<ItemStack> cir){
        //Honey bottle code
        if (itemStack.isEmpty()) {
            cir.setReturnValue(new ItemStack(Items.BUCKET));
        } else {
            if (livingEntity instanceof Player player && !player.getAbilities().instabuild) {
                ItemStack itemStack2 = new ItemStack(Items.BUCKET);
                if (!player.getInventory().add(itemStack2)) {
                    player.drop(itemStack2, false);
                }
            }
            cir.setReturnValue(itemStack);
        }
    }
}
