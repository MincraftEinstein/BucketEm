package com.qzimyion.bucketem;

import com.qzimyion.bucketem.core.registry.items.ModItems;

public final class BucketEmCommon {
    public static final String MOD_ID = "bucketem";

    public static void init() {
        ModItems.registerItems();
    }
}
