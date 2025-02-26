package com.qzimyion.bucketem.fabric.client;

import com.qzimyion.bucketem.client.BucketEmCommonClient;
import net.fabricmc.api.ClientModInitializer;

public final class ExampleModFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BucketEmCommonClient.init();
    }
}
