package com.qzimyion.bucketem.forge;

import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import com.qzimyion.bucketem.BucketEmCommon;

@Mod(BucketEmCommon.MOD_ID)
public final class ExampleModForge {
    public ExampleModForge() {
        // Submit our event bus to let Architectury API register our content on the right time.
        EventBuses.registerModEventBus(BucketEmCommon.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());

        // Run our common setup.
        BucketEmCommon.init();
    }
}
