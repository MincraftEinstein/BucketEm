package com.qzimyion.bucketem.client;

import com.qzimyion.bucketem.platform.ClientHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;

public class ModItemModelPredicates {

    public static void registerModelProperties(ClientHelper.ModelPredicates event){
        //==Axolotls==//
        event.register(Items.AXOLOTL_BUCKET, new ResourceLocation("age"), ((itemStack, clientLevel, livingEntity, i) -> {
            float age = 1;
            if (itemStack.getTag() != null && itemStack.getTag().contains("Age") && itemStack.getTag().getInt("Age") < 0)
                age = 0;
            return age;
        }));
    }
}
