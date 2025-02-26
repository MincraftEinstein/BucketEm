package com.qzimyion.bucketem.fabric.client;

import com.qzimyion.bucketem.client.BucketEmCommonClient;
import net.fabricmc.api.ClientModInitializer;

public final class BucketemFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BucketEmCommonClient.init();
    }
}
