package com.qzimyion.bucketem.compact;

import net.fabricmc.loader.api.FabricLoader;

public class IsModLoaded {

    public static boolean isSpelunkeryModLoaded(){
        return FabricLoader.getInstance().isModLoaded("spelunkery");
    }

    public static boolean isWWModLoaded(){
        return FabricLoader.getInstance().isModLoaded("wilderwild");
    }
}
