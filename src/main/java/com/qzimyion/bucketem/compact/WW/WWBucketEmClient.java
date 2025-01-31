package com.qzimyion.bucketem.compact.WW;

import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.frozenblock.wilderwild.registry.WWItems;
import net.minecraft.util.Identifier;

@SuppressWarnings("deprecation")
public class WWBucketEmClient {


    public static void WWBucketemItemPredicateModels(){
        FabricModelPredicateProviderRegistry.register(WWItems.JELLYFISH_BUCKET, new Identifier("age"), (stack, clientWorld, livingEntity, i) -> {
            float age = 1;
            if (stack.getNbt() != null && stack.getNbt().contains("age") && stack.getNbt().getInt("age") < 0)
                age = 0;
            return age;
        });
    }
}
