package com.qzimyion.bucketem.fabric;

import com.qzimyion.bucketem.core.registry.DispenserBehaviorRegistry;
import com.qzimyion.bucketem.core.registry.ModCreativeTabs;
import com.qzimyion.bucketem.core.registry.ModEvents;
import com.qzimyion.bucketem.core.registry.ModItems;
import com.qzimyion.bucketem.platform.fabric.CommonHelperImpl;
import dev.architectury.registry.fuel.FuelRegistry;
import net.fabricmc.api.ModInitializer;

import com.qzimyion.bucketem.BucketEmCommon;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Items;

public final class BucketemFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        BucketEmCommon.init();
        UseEntityCallback.EVENT.register(ModEvents::EntityEvents);
        DispenserBehaviorRegistry.registerDispenserBehavior();
        FuelRegistry.register(20000, ModItems.GOLDEN_LAVA_BUCKET.get());
    }
}
