package com.qzimyion.bucketem;

import com.qzimyion.bucketem.items.ModItems;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.minecraft.util.Identifier;

@SuppressWarnings("deprecation")
public class BucketemClient implements ClientModInitializer {

//    public static void modelPredicates(){
//        FabricModelPredicateProviderRegistry.register(ModItems.TURTLE_BUCKET, Identifier.ofVanilla("age"), (itemStack, clientWorld, livingEntity, i) -> {
//            float age = 1;
//            if (itemStack.getComponents() != null && itemStack.getTag().contains("Age") && itemStack.getTag().getInt("Age") < 0)
//                age = 0;
//            return age;
//        });
//    }


    public void onInitializeClient(){
//        modelPredicates();
    }
}
