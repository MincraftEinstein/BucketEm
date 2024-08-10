package com.qzimyion.bucketem;

import com.qzimyion.bucketem.items.ModItems;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.util.Identifier;

import java.util.concurrent.atomic.AtomicBoolean;

@SuppressWarnings("deprecation")
public class BucketemClient implements ClientModInitializer {

    public static void modelPredicates(){
        FabricModelPredicateProviderRegistry.register(ModItems.TURTLE_BUCKET, Identifier.ofVanilla("age"), (itemStack, clientWorld, livingEntity, i) -> {
            AtomicBoolean ageBL = new AtomicBoolean(false);
            NbtComponent.set(DataComponentTypes.BUCKET_ENTITY_DATA, itemStack, nbt -> {
                nbt.get("Age");
                ageBL.set(nbt.getInt("Age") < 0);
            });
            float age = 1;
            if (itemStack.getComponents() != null && itemStack.contains(DataComponentTypes.BUCKET_ENTITY_DATA) && ageBL.get())
                age = 0;
            return age;
        });
    }


    public void onInitializeClient(){
        modelPredicates();
    }
}
