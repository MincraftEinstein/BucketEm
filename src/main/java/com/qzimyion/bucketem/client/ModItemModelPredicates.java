package com.qzimyion.bucketem.client;

import com.qzimyion.bucketem.mixin.ItemMixins.EntityBucketItemAccessor;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Bucketable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.AxolotlEntity;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.entity.passive.TropicalFishEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.EntityBucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;

import static com.qzimyion.bucketem.items.ModItems.*;

@SuppressWarnings("deprecation")
public class ModItemModelPredicates {
    private static final MinecraftClient MINECRAFT = MinecraftClient.getInstance();


    public static void registerModelPredicates(){
        FabricModelPredicateProviderRegistry.register(TURTLE_BUCKET, new Identifier("age"), (stack, clientWorld, livingEntity, i) -> {
            float age = 1;
            if (stack.getNbt() != null && stack.getNbt().contains("Age") && stack.getNbt().getInt("Age") < 0)
                age = 0;
            return age;
        });

        FabricModelPredicateProviderRegistry.register(SLIME_BOTTLE,new Identifier("slime_chunk"), (stack, clientWorld, livingEntity, i) -> {
            if (livingEntity instanceof PlayerEntity) {
                NbtCompound nbt = stack.getOrCreateNbt();
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

        FabricModelPredicateProviderRegistry.register(Items.AXOLOTL_BUCKET, new Identifier("age"), (stack, clientWorld, livingEntity, i) -> {
            float age = 1;
            if (stack.getNbt() != null && stack.getNbt().contains("Age") && stack.getNbt().getInt("Age") < 0)
                age = 0;
            return age;
        });
        FabricModelPredicateProviderRegistry.register(Items.AXOLOTL_BUCKET, new Identifier("variant"), (stack, world, holder, seed) -> {
            Item item = stack.getItem();
            if (!(item instanceof EntityBucketItem)) {
                return 0f;
            }
            Entity entity = ((EntityBucketItemAccessor) item).getEntityType().create(MINECRAFT.world);
            if (!(entity instanceof AxolotlEntity) || entity == null) {
                return 0f;
            }
            ((Bucketable) entity).copyDataFromNbt(stack.getOrCreateNbt());
            return ((AxolotlEntity) entity).getVariant().ordinal() / 10f;
        });

        //==Bee stuff==//
        //Age
        FabricModelPredicateProviderRegistry.register(BEE_BOTTLE, new Identifier("age"), (stack, clientWorld, livingEntity, i) -> {
            float age = 1;
            if (stack.getNbt() != null && stack.getNbt().contains("Age") && stack.getNbt().getInt("Age") < 0)
                age = 0;
            return age;
        });
        //Nectar
        FabricModelPredicateProviderRegistry.register(BEE_BOTTLE, new Identifier("nectar"), (stack, clientWorld, livingEntity, i) -> {
            if (livingEntity instanceof BeeEntity) {
                NbtCompound nbt = stack.getOrCreateNbt();
                boolean nectar = nbt.contains("HasNectar") && nbt.getBoolean("HasNectar");
                if (nectar){
                    return 1;
                } else {
                    return 0;
                }
            }
            else {
                return 1;
            }
        });
        //Sting
        FabricModelPredicateProviderRegistry.register(BEE_BOTTLE, new Identifier("sting"), (stack, clientWorld, livingEntity, i) -> {
            if (livingEntity instanceof BeeEntity) {
                NbtCompound nbt = stack.getOrCreateNbt();
                boolean nectar = nbt.contains("HasStung") && nbt.getBoolean("HasStung");
                if (nectar){
                    return 1;
                } else {
                    return 0;
                }
            }
            else {
                return 1;
            }
        });
        //Anger
        FabricModelPredicateProviderRegistry.register(BEE_BOTTLE, new Identifier("anger"), (stack, world, holder, seed) -> {
            float anger = 1;
            if (stack.getNbt() != null && stack.getNbt().contains("Anger") && stack.getNbt().getInt("Anger") < 0)
                anger = 0;
            return anger;
        });

        //Tropical fish
        FabricModelPredicateProviderRegistry.register(Items.TROPICAL_FISH_BUCKET, new Identifier("variant"), (stack, clientWorld, livingEntity, id) -> {
            Item item = stack.getItem();
            if (!(item instanceof EntityBucketItem)) {
                return 0f;
            }
            Entity entity = ((EntityBucketItemAccessor) item).getEntityType().create(MINECRAFT.world);
            if (!(entity instanceof TropicalFishEntity) || entity == null) {
                return 0f;
            }
            ((Bucketable) entity).copyDataFromNbt(stack.getOrCreateNbt());
            return ((TropicalFishEntity) entity).getVariant().ordinal() / 10f;
        });
        FabricModelPredicateProviderRegistry.register(Items.TROPICAL_FISH_BUCKET, new Identifier("size"), (stack, clientWorld, livingEntity, i) -> {
            float size = 0;
            if (livingEntity instanceof TropicalFishEntity tropicalFish && tropicalFish.getVariant().getSize().ordinal() == 1){
                size = 1;
            }
            return size;
        });


        //Golden Buckets
        FabricModelPredicateProviderRegistry.register(GOLDEN_WATER_BUCKET, new Identifier("level"), (stack, level, entity, hash) -> stack.getOrCreateNbt().getInt("FluidLevel") / 10f);
        FabricModelPredicateProviderRegistry.register(GOLDEN_LAVA_BUCKET, new Identifier("level"), (stack, level, entity, hash) -> stack.getOrCreateNbt().getInt("FluidLevel") / 10f);
        FabricModelPredicateProviderRegistry.register(GOLDEN_MILK_BUCKET, new Identifier("level"), (stack, level, entity, hash) -> stack.getOrCreateNbt().getInt("FluidLevel") / 10f);
        FabricModelPredicateProviderRegistry.register(GOLDEN_POWDER_SNOW_BUCKET, new Identifier("level"), (stack, level, entity, hash) -> stack.getOrCreateNbt().getInt("FluidLevel") / 10f);
    }
}
