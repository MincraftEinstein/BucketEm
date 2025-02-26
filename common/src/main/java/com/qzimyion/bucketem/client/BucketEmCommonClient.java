package com.qzimyion.bucketem.client;

import com.qzimyion.bucketem.platform.ClientHelper;
import net.minecraft.world.item.Items;

public class BucketEmCommonClient {

    public static void init(){
       ClientHelper.addItemColorsRegistration(event -> event.register(new TropicalFishBucketItemColour(), Items.TROPICAL_FISH_BUCKET));
       ClientHelper.addModelPredicatesRegistration(ModItemModelPredicates::registerModelProperties);
    }
}
