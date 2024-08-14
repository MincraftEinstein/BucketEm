package com.qzimyion.bucketem;

import com.qzimyion.bucketem.items.ModDataComponents;
import com.qzimyion.bucketem.items.ModItems;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

import java.util.concurrent.atomic.AtomicBoolean;

import static com.qzimyion.bucketem.items.ModItems.*;

@SuppressWarnings("deprecation")
public class BucketemClient implements ClientModInitializer {

    public static void modelPredicates(){

        //==Turtles==//
        FabricModelPredicateProviderRegistry.register(TURTLE_BUCKET, Identifier.ofVanilla("age"), (itemStack, clientWorld, livingEntity, i) -> {
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

        //==Slimes==//
        FabricModelPredicateProviderRegistry.register(SLIME_BOTTLE, Identifier.ofVanilla("slime_chunk"), (itemStack, clientWorld, livingEntity, i) -> {
            if (livingEntity instanceof PlayerEntity) {
                boolean inSlimeChunk = Boolean.TRUE.equals(itemStack.get(ModDataComponents.SLIME_CHUNK_F));
                return inSlimeChunk ? 1 : 0;
            } else {
                return 0;
            }
        });

        //==Golden Buckets==//
        //Water
//        FabricModelPredicateProviderRegistry.register(GOLDEN_WATER_BUCKET, Identifier.ofVanilla("fluidlevel"), (itemStack, clientWorld, livingEntity, i) -> {
//            if (livingEntity instanceof PlayerEntity) {
//                return 0;
//            }
//        });


        //==Axolotls==//
        //Pink
        FabricModelPredicateProviderRegistry.register(Items.AXOLOTL_BUCKET, Identifier.ofVanilla("age"), (itemStack, clientWorld, livingEntity, i) -> {
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
        //Brown
        FabricModelPredicateProviderRegistry.register(BROWN_AXOLOTL_BUCKET, Identifier.ofVanilla("age"), (itemStack, clientWorld, livingEntity, i) -> {
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
        //Gold
        FabricModelPredicateProviderRegistry.register(GOLD_AXOLOTL_BUCKET, Identifier.ofVanilla("age"), (itemStack, clientWorld, livingEntity, i) -> {
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
        //Cyan
        FabricModelPredicateProviderRegistry.register(CYAN_AXOLOTL_BUCKET, Identifier.ofVanilla("age"), (itemStack, clientWorld, livingEntity, i) -> {
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
        //Blue
        FabricModelPredicateProviderRegistry.register(BLUE_AXOLOTL_BUCKET, Identifier.ofVanilla("age"), (itemStack, clientWorld, livingEntity, i) -> {
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
