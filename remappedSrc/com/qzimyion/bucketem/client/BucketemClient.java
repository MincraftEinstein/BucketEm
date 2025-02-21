package com.qzimyion.bucketem.client;

import com.qzimyion.bucketem.compact.BucketemCompactReg;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.world.item.Items;

public class BucketemClient implements ClientModInitializer {

    public void onInitializeClient(){
        ModItemModelPredicates.registerModelPredicates();
        BucketemCompactReg.initializeClientCompacts();
        ColorProviderRegistry.ITEM.register(new TropicalFishBucketItemColour(), Items.TROPICAL_FISH_BUCKET);
    }
}
