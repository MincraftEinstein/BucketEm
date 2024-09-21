package com.qzimyion.bucketem.client;

import com.qzimyion.bucketem.compact.BucketemCompactReg;
import net.fabricmc.api.ClientModInitializer;

public class BucketemClient implements ClientModInitializer {

    public void onInitializeClient(){
        ModItemModelPredicates.registerModelPredicates();
        BucketemCompactReg.initializeClientCompacts();
    }
}
