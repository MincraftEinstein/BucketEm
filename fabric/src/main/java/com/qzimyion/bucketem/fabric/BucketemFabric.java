package com.qzimyion.bucketem.fabric;

import com.qzimyion.bucketem.core.registry.DispenserBehaviorRegistry;
import com.qzimyion.bucketem.core.registry.ModEvents;
import com.qzimyion.bucketem.core.registry.ModItems;
import dev.architectury.registry.fuel.FuelRegistry;
import net.fabricmc.api.ModInitializer;

import com.qzimyion.bucketem.BucketEmCommon;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;

public final class BucketemFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        BucketEmCommon.init();
        UseEntityCallback.EVENT.register(ModEvents::EntityEvents);
        DispenserBehaviorRegistry.registerDispenserBehavior();
        FuelRegistry.register(20000, ModItems.GOLDEN_LAVA_BUCKET.get());
    }
}
