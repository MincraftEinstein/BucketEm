package com.qzimyion.bucketem.platform.forge;

import com.qzimyion.bucketem.platform.PlatformHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.loading.FMLEnvironment;

public class PlatformHelperImpl {

    public static PlatformHelper.Side getPhysicalSide(){
        return FMLEnvironment.dist == Dist.CLIENT ? PlatformHelper.Side.CLIENT : PlatformHelper.Side.SERVER;
    }
}
