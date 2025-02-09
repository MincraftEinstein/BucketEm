package com.qzimyion.bucketem.core.mixin.ItemMixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemInHandRenderer.class)
public class HeldItemMixin {

    @Shadow private ItemStack mainHandItem;

    @Shadow private ItemStack offHandItem;

    @Shadow @Final private Minecraft minecraft;

    @Inject(method = "tick", at = @At("HEAD"))
    private void modifyAnimation(CallbackInfo ci){
        assert minecraft.player != null;
        ItemStack newMainStack = minecraft.player.getMainHandItem();
        if (mainHandItem.getItem() == newMainStack.getItem()) {
            if (!((IItem)mainHandItem.getItem()).allowNbtUpdateAnimation(minecraft.player, InteractionHand.MAIN_HAND, mainHandItem, newMainStack)) {
                mainHandItem = newMainStack;
            }
        }
        ItemStack newOffStack = minecraft.player.getOffhandItem();
        if (offHandItem.getItem() == newOffStack.getItem()) {
            if (!((IItem)offHandItem.getItem()).allowNbtUpdateAnimation(minecraft.player, InteractionHand.OFF_HAND, offHandItem, newOffStack)) {
                offHandItem = newOffStack;
            }
        }
    }
}
