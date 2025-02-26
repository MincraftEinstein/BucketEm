package com.qzimyion.bucketem.platform;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.world.item.ItemStack;

public class CommonHelper {

    @ExpectPlatform
    public static ItemStack getCraftingRemainingItem(ItemStack stack) {
        throw new AssertionError();
    }
}
