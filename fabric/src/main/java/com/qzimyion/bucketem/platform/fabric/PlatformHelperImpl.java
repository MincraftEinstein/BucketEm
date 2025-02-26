package com.qzimyion.bucketem.platform.fabric;

import com.qzimyion.bucketem.platform.PlatformHelper;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;

public class PlatformHelperImpl {

    public static PlatformHelper.Side getPhysicalSide() {
        return FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT ? PlatformHelper.Side.CLIENT : PlatformHelper.Side.SERVER;
    }
}
