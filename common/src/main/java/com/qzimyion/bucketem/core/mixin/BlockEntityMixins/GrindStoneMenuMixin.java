package com.qzimyion.bucketem.core.mixin.BlockEntityMixins;

import net.minecraft.world.inventory.GrindstoneMenu;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GrindstoneMenu.class)
public class GrindStoneMenuMixin {

    @Inject(at = @At("HEAD"), method = "removeNonCursesFrom")
    private void removeNonCursesFrom(ItemStack itemStack, CallbackInfoReturnable<ItemStack> cir){

    }

}
