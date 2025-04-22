package com.qzimyion.bucketem.fabric.client;

import com.qzimyion.bucketem.fabric.fabricCompact.BucketemCompactRegFabric;
import net.fabricmc.api.ClientModInitializer;

public final class BucketemFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        //BucketEmCommonClient.init();
        BucketemCompactRegFabric.initClientContent();
    }
}
