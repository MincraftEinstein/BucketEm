package com.qzimyion.bucketem.fabric;

import com.qzimyion.bucketem.BucketEmCommon;
import com.qzimyion.bucketem.core.registry.DispenserBehaviorRegistry;
import net.fabricmc.api.ModInitializer;

public final class BucketemFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        BucketEmCommon.init();
        DispenserBehaviorRegistry.registerDispenserBehavior();
    }
}
