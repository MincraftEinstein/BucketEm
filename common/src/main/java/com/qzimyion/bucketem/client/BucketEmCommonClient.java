package com.qzimyion.bucketem.client;

import com.qzimyion.bucketem.platform.ClientHelper;
import net.minecraft.world.item.Items;

public class BucketEmCommonClient {

    public static void init(){
       ClientHelper.addItemColorsRegistration(BucketEmCommonClient::registerItemColors);
       ClientHelper.addModelPredicatesRegisteration(ModItemModelPredicates::registerModelProperties);
    }

    public static void registerItemColors(ClientHelper.ItemColorEvent event) {
        event.register(new TropicalFishBucketItemColour(), Items.TROPICAL_FISH);
    }
}
