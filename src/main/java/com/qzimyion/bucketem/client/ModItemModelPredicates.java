package com.qzimyion.bucketem.client;

import com.qzimyion.bucketem.mixin.EntityBucketItemAccessor;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Bucketable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.AxolotlEntity;
import net.minecraft.entity.passive.FrogEntity;
import net.minecraft.entity.passive.FrogVariant;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.EntityBucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;

import static com.qzimyion.bucketem.items.ModItems.*;

public class ModItemModelPredicates {
    private static final MinecraftClient MINECRAFT = MinecraftClient.getInstance();

    @SuppressWarnings("deprecation")
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

        //Experimental
        FabricModelPredicateProviderRegistry.register(FROG_BUCKET, new Identifier("variant"), (stack, world, holder, seed) -> {
            Item item = stack.getItem();
            if (!(item instanceof EntityBucketItem)) {
                return 0;
            }
            Entity entity = ((EntityBucketItemAccessor) item).getEntityType().create(MINECRAFT.world);
            if (!(entity instanceof FrogEntity) || entity == null) {
                return 0;
            }
            if (item == FROG_BUCKET && ((FrogEntity) entity).getVariant()==FrogVariant.COLD){
                return 1;
            }
            if (item == FROG_BUCKET && ((FrogEntity) entity).getVariant()== FrogVariant.WARM){
                return 2;
            }
            return 0;
        });

        FabricModelPredicateProviderRegistry.register(GOLDEN_WATER_BUCKET, new Identifier("level"), (stack, level, entity, hash) -> (float) stack.getOrCreateNbt().getInt("FluidLevel") / 10);
        FabricModelPredicateProviderRegistry.register(GOLDEN_LAVA_BUCKET, new Identifier("level"), (stack, level, entity, hash) -> (float) stack.getOrCreateNbt().getInt("FluidLevel") / 10);
        FabricModelPredicateProviderRegistry.register(GOLDEN_MILK_BUCKET, new Identifier("level"), (stack, level, entity, hash) -> (float) stack.getOrCreateNbt().getInt("FluidLevel") / 10);
        FabricModelPredicateProviderRegistry.register(GOLDEN_POWDER_SNOW_BUCKET, new Identifier("level"), (stack, level, entity, hash) -> (float) stack.getOrCreateNbt().getInt("FluidLevel") / 10);

    }
}
