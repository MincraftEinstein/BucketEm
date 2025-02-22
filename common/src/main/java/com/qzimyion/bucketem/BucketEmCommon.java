package com.qzimyion.bucketem;

import com.qzimyion.bucketem.core.registry.DispenserBehaviorRegistry;
import com.qzimyion.bucketem.core.registry.ModItems;

public final class BucketEmCommon {
    public static final String MOD_ID = "bucketem";

    public static void init() {
        ModItems.registerItems();
        DispenserBehaviorRegistry.registerDispenserBehavior();
    }
}
