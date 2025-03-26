package com.qzimyion.bucketem.platform.neoforge;

import com.qzimyion.bucketem.platform.PlatformHelper;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.loading.FMLEnvironment;

import java.lang.ref.WeakReference;

public class PlatformHelperImpl {
    public static WeakReference<IEventBus> currentBus = null;

    public static void startRegistering(IEventBus bus) {
        currentBus = new WeakReference<>(bus);
    }

    public static IEventBus getCurrentBus() {
        if (currentBus == null || currentBus.get() == null)
            throw new IllegalStateException("Bus is null. You must call RegHelper.startRegistering(IEventBus) before registering events");
        return currentBus.get();
    }

    public static PlatformHelper.Side getPhysicalSide(){
        return FMLEnvironment.dist == Dist.CLIENT ? PlatformHelper.Side.CLIENT : PlatformHelper.Side.SERVER;
    }

}
