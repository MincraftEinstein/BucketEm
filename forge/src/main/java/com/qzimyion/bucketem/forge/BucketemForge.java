package com.qzimyion.bucketem.forge;

import com.qzimyion.bucketem.BucketEmCommon;
import com.qzimyion.bucketem.client.BucketEmCommonClient;
import com.qzimyion.bucketem.core.registry.DispenserBehaviorRegistry;
import com.qzimyion.bucketem.platform.ClientHelper;
import com.qzimyion.bucketem.platform.PlatformHelper;
import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@SuppressWarnings("removal")
@Mod(BucketEmCommon.MOD_ID)
@Mod.EventBusSubscriber(modid = BucketEmCommon.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class BucketemForge {

    public BucketemForge() {
        EventBuses.registerModEventBus(BucketEmCommon.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        BucketEmCommon.init();

        if (PlatformHelper.getPhysicalSide().isClient()) {
            BucketEmCommonClient.init();
            ClientHelper.addModelPredicatesRegistration(ForgeOnlyModelPredicates::register);
        }
    }

    @SubscribeEvent
    public static void dispenserReg(FMLCommonSetupEvent event) {
        event.enqueueWork(DispenserBehaviorRegistry::registerDispenserBehavior);
    }
}
