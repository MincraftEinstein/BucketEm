package com.qzimyion.bucketem;

import com.qzimyion.bucketem.mixin.EntityBucketItemAccessor;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Bucketable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.AxolotlEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.EntityBucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;

import static com.qzimyion.bucketem.items.ModItems.*;

@SuppressWarnings("deprecation")
public class BucketemClient implements ClientModInitializer {
    private static final MinecraftClient MINECRAFT = MinecraftClient.getInstance();


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

        FabricModelPredicateProviderRegistry.register(Items.AXOLOTL_BUCKET, new Identifier("variant"), (itemStack, world, holder, seed) -> {
            Item item = itemStack.getItem();
            if (!(item instanceof EntityBucketItem)) {
                return 0f;
            }
            Entity entity = ((EntityBucketItemAccessor) item).getEntityType().create(MINECRAFT.world);
            if (!(entity instanceof AxolotlEntity) || entity == null) {
                return 0f;
            }
            ((Bucketable) entity).copyDataFromNbt(itemStack.getOrCreateNbt());
            return ((AxolotlEntity) entity).getVariant().ordinal() / 10f;
        });

        FabricModelPredicateProviderRegistry.register(GOLDEN_WATER_BUCKET, new Identifier("level"), (stack, level, entity, hash) -> stack.getOrCreateNbt().getInt("fluidlevel"));
        FabricModelPredicateProviderRegistry.register(GOLDEN_LAVA_BUCKET, new Identifier("level"), (stack, level, entity, hash) -> stack.getOrCreateNbt().getInt("fluidlevel"));
        FabricModelPredicateProviderRegistry.register(GOLDEN_MILK_BUCKET, new Identifier("level"), (stack, level, entity, hash) -> stack.getOrCreateNbt().getInt("fluidlevel"));
        FabricModelPredicateProviderRegistry.register(GOLDEN_POWDER_SNOW_BUCKET, new Identifier("level"), (stack, level, entity, hash) -> stack.getOrCreateNbt().getInt("fluidlevel"));

    }

    public void onInitializeClient(){
        itemPredicates();
    }
}
