package com.qzimyion.bucketem.fabric;

import com.qzimyion.bucketem.core.registry.ModEvents;
import net.fabricmc.api.ModInitializer;

import com.qzimyion.bucketem.BucketEmCommon;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;

public final class ExampleModFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        BucketEmCommon.init();
        UseEntityCallback.EVENT.register(ModEvents::EntityEvents);
    }
}
