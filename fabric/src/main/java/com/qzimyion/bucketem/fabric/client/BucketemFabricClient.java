package com.qzimyion.bucketem.fabric.client;

import com.qzimyion.bucketem.client.BucketEmCommonClient;
import com.qzimyion.bucketem.fabric.fabricCompact.BucketemCompactRegFabric;
import com.qzimyion.bucketem.platform.fabric.ClientHelperImpl;
import net.fabricmc.api.ClientModInitializer;

public final class BucketemFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BucketEmCommonClient.init();
        BucketemCompactRegFabric.initClientContent();
        ClientHelperImpl.addModelPredicatesRegistration(FabricOnlyModelPredicates::register);
    }
}
