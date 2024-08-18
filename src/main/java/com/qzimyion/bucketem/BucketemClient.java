package com.qzimyion.bucketem;

import com.qzimyion.bucketem.items.ModDataComponents;
import com.qzimyion.bucketem.mixin.EntityMixins.EntityBucketItemAccessor;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.AxolotlEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.EntityBucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

import java.util.concurrent.atomic.AtomicBoolean;

import static com.qzimyion.bucketem.items.ModItems.*;
import static net.minecraft.util.profiling.jfr.JfrProfiler.MINECRAFT;

@SuppressWarnings("deprecation")
public class BucketemClient implements ClientModInitializer {
    private static final MinecraftClient MINECRAFT = MinecraftClient.getInstance();


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

        //==Axolotls==//
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
        FabricModelPredicateProviderRegistry.register(Items.AXOLOTL_BUCKET, Identifier.ofVanilla("variant"),
                (itemStack, world, holder, seed) -> {
                    AxolotlEntity axolotl = getAxolotlRef(itemStack, world, holder, seed);
                    if (axolotl == null) {
                        return 0f;
                    }
                    return axolotl.getVariant().ordinal() / 10f;
                }
        );

        //==Golden Buckets==//
        FabricModelPredicateProviderRegistry.register(GOLDEN_WATER_BUCKET, Identifier.ofVanilla("level"), (stack, level, entity, hash) -> stack.get(ModDataComponents.FLUID_LEVEL));
        FabricModelPredicateProviderRegistry.register(GOLDEN_LAVA_BUCKET, Identifier.ofVanilla("level"), (stack, level, entity, hash) -> stack.get(ModDataComponents.FLUID_LEVEL));
        FabricModelPredicateProviderRegistry.register(GOLDEN_MILK_BUCKET, Identifier.ofVanilla("level"), (stack, level, entity, hash) -> stack.get(ModDataComponents.FLUID_LEVEL));
        FabricModelPredicateProviderRegistry.register(GOLDEN_POWDER_SNOW_BUCKET, Identifier.ofVanilla("level"), (stack, level, entity, hash) -> stack.get(ModDataComponents.FLUID_LEVEL));
    }

    private static AxolotlEntity getAxolotlRef(ItemStack itemStack, ClientWorld world, LivingEntity holder, int seed) {
        Item item = itemStack.getItem();
        if (!(item instanceof EntityBucketItem)) {
            return null;
        }
        Entity entity = ((EntityBucketItemAccessor) item).getEntityType().create(MINECRAFT.world);
        if (!(entity instanceof AxolotlEntity axolotl)) {
            return null;
        }
        NbtComponent component = itemStack.getOrDefault(DataComponentTypes.BUCKET_ENTITY_DATA, NbtComponent.DEFAULT);
        axolotl.copyDataFromNbt(component.copyNbt());
        return axolotl;
    }

    public void onInitializeClient(){
        modelPredicates();
    }
}
