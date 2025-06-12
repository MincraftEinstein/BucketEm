package com.qzimyion.bucketem;

import com.qzimyion.bucketem.core.registry.ModCreativeTabs;
import com.qzimyion.bucketem.core.registry.ModEvents;
import com.qzimyion.bucketem.core.registry.ModItems;
import com.qzimyion.bucketem.platform.CommonHelper;

public final class BucketEmCommon {

    public static final String MOD_ID = "bucketem";

    public static void init() {
        ModItems.registerItems();
        CommonHelper.addItemsToTabsRegistration(ModCreativeTabs::addItems);
        ModEvents.LootTableEvent();
        ModEvents.entityInteractionEvent();
    }
}
