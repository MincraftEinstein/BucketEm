package com.qzimyion.bucketem.compact;

import dev.architectury.platform.Platform;

public class IsModLoadedForCrossContent {

    public static boolean isWWLoaded(){
        return Platform.isModLoaded("wilderwild");
    }
}
