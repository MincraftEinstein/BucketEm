package com.qzimyion.bucketem.client;

import com.qzimyion.bucketem.platform.ClientHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.player.Player;

import static com.qzimyion.bucketem.core.registry.ModItems.*;
import static net.minecraft.world.item.Items.*;

public class ModItemModelPredicates {

    public static void registerModelProperties(ClientHelper.ModelPredicates event){
        //==Axolotls==//
        event.register(AXOLOTL_BUCKET, new ResourceLocation("age"), ((itemStack, clientLevel, livingEntity, i) -> {
            float age = 1;
            if (itemStack.getTag() != null && itemStack.getTag().contains("Age") && itemStack.getTag().getInt("Age") < 0)
                age = 0;
            return age;
        }));
        //==Turtle==//
        event.register(TURTLE_BUCKET.get(), new ResourceLocation("age"), (itemStack, clientLevel, livingEntity, i) -> {
            float age = 1;
            if (itemStack.getTag() != null && itemStack.getTag().contains("Age") && itemStack.getTag().getInt("Age") < 0)
                age = 0;
            return age;
        });

        //==Slime==//
        event.register(SLIME_BOTTLE.get(), new ResourceLocation("slime_chunk"), (itemStack, clientLevel, livingEntity, i) -> {
            if (livingEntity instanceof Player) {
                CompoundTag nbt = itemStack.getOrCreateTag();
                boolean chunk = nbt.contains("SlimeChunk") && nbt.getBoolean("SlimeChunk");
                return chunk ? 1 : 0;
            } else {
                return 1;
            }
        });

        //==Axolotls==//
        event.register(AXOLOTL_BUCKET, new ResourceLocation("age"), (itemStack, clientLevel, livingEntity, i) -> {
            float age = 1;
            if (itemStack.getTag() != null && itemStack.getTag().contains("Age") && itemStack.getTag().getInt("Age") < 0)
                age = 0;
            return age;
        });

        //==Bee stuff==//
        //Age
        event.register(BEE_BOTTLE.get(), new ResourceLocation("age"), (itemStack, clientLevel, livingEntity, i) -> {
            float age = 1;
            if (itemStack.getTag() != null && itemStack.getTag().contains("Age") && itemStack.getTag().getInt("Age") < 0)
                age = 0;
            return age;
        });
        //Nectar
        event.register(BEE_BOTTLE.get(), new ResourceLocation("nectar"), (itemStack, clientLevel, livingEntity, i) -> {
            if (livingEntity instanceof Bee) {
                CompoundTag nbt = itemStack.getOrCreateTag();
                boolean nectar = nbt.contains("HasNectar") && nbt.getBoolean("HasNectar");
                return nectar ? 1 : 0;
            } else {
                return 1;
            }
        });
        //Sting
        event.register(BEE_BOTTLE.get(), new ResourceLocation("sting"), (itemStack, clientLevel, livingEntity, i) -> {
            if (livingEntity instanceof Bee) {
                CompoundTag nbt = itemStack.getOrCreateTag();
                boolean nectar = nbt.contains("HasStung") && nbt.getBoolean("HasStung");
                return nectar ? 1 : 0;
            } else {
                return 1;
            }
        });
        //Anger
        event.register(BEE_BOTTLE.get(), new ResourceLocation("anger"), (itemStack, world, holder, seed) -> {
            float anger = 1;
            if (itemStack.getTag() != null && itemStack.getTag().contains("Anger") && itemStack.getTag().getInt("Anger") < 0)
                anger = 0;
            return anger;
        });

        //==Golden Buckets==//
        event.register(GOLDEN_WATER_BUCKET.get(), new ResourceLocation("level"), (itemStack, level, entity, hash) -> itemStack.getOrCreateTag().getInt("FluidLevel") / 10f);
        event.register(GOLDEN_LAVA_BUCKET.get(), new ResourceLocation("level"), (itemStack, level, entity, hash) -> itemStack.getOrCreateTag().getInt("FluidLevel") / 10f);
        event.register(GOLDEN_MILK_BUCKET.get(), new ResourceLocation("level"), (itemStack, level, entity, hash) -> itemStack.getOrCreateTag().getInt("FluidLevel") / 10f);
        event.register(GOLDEN_POWDER_SNOW_BUCKET.get(), new ResourceLocation("level"), (itemStack, level, entity, hash) -> itemStack.getOrCreateTag().getInt("FluidLevel") / 10f);
    }
}
