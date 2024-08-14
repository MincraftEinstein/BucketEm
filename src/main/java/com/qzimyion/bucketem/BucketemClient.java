package com.qzimyion.bucketem;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.minecraft.entity.passive.AxolotlEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;

import static com.qzimyion.bucketem.items.ModItems.*;

@SuppressWarnings("deprecation")
public class BucketemClient implements ClientModInitializer {

    public static void itemPredicates(){

        FabricModelPredicateProviderRegistry.register(TURTLE_BUCKET, new Identifier("age"), (itemStack, clientWorld, livingEntity, i) -> {
            float age = 1;
            if (itemStack.getNbt() != null && itemStack.getNbt().contains("Age") && itemStack.getNbt().getInt("Age") < 0)
                age = 0;
            return age;
        });

        FabricModelPredicateProviderRegistry.register(SLIME_BOTTLE,new Identifier("slime_chunk"), (itemStack, clientWorld, livingEntity, i) -> {
            if (livingEntity instanceof PlayerEntity) {
                NbtCompound nbt = itemStack.getOrCreateNbt();
                boolean chunk = nbt.contains("SlimeChunk") && nbt.getBoolean("SlimeChunk");
                if (chunk){
                    return 1;
                } else {
                    return 0;
                }
            }
            else {
                return 1;
            }
        });
    }

    public void onInitializeClient(){
        itemPredicates();
    }
}
