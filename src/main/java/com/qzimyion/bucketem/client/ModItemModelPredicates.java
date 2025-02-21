package com.qzimyion.bucketem.client;

import com.qzimyion.bucketem.mixin.ItemMixins.EntityBucketItemAccessor;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MobBucketItem;

import static com.qzimyion.bucketem.items.ModItems.*;

@SuppressWarnings("deprecation")
public class ModItemModelPredicates {
    private static final Minecraft MINECRAFT = Minecraft.getInstance();


    public static void registerModelPredicates(){
        FabricModelPredicateProviderRegistry.register(TURTLE_BUCKET, new ResourceLocation("age"), (stack, clientWorld, livingEntity, i) -> {
            float age = 1;
            if (stack.getTag() != null && stack.getTag().contains("Age") && stack.getTag().getInt("Age") < 0)
                age = 0;
            return age;
        });

        FabricModelPredicateProviderRegistry.register(SLIME_BOTTLE,new ResourceLocation("slime_chunk"), (stack, clientWorld, livingEntity, i) -> {
            if (livingEntity instanceof Player) {
                CompoundTag nbt = stack.getOrCreateTag();
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

        //==Axolotls==//
        FabricModelPredicateProviderRegistry.register(Items.AXOLOTL_BUCKET, new ResourceLocation("age"), (stack, clientWorld, livingEntity, i) -> {
            float age = 1;
            if (stack.getTag() != null && stack.getTag().contains("Age") && stack.getTag().getInt("Age") < 0)
                age = 0;
            return age;
        });
        FabricModelPredicateProviderRegistry.register(Items.AXOLOTL_BUCKET, new ResourceLocation("variant"),
                (stack, world, holder, seed) -> {
            Item item = stack.getItem();
            if (!(item instanceof MobBucketItem)) {
                return 0f;
            }
            Entity entity = ((EntityBucketItemAccessor) item).type().create(MINECRAFT.level);
            if (!(entity instanceof Axolotl) || entity == null) {
                return 0f;
            }
            ((Bucketable) entity).loadFromBucketTag(stack.getOrCreateTag());
            return ((Axolotl) entity).getVariant().ordinal() / 10f;
        });

        //==Bee stuff==//
        //Age
        FabricModelPredicateProviderRegistry.register(BEE_BOTTLE, new ResourceLocation("age"), (stack, clientWorld, livingEntity, i) -> {
            float age = 1;
            if (stack.getTag() != null && stack.getTag().contains("Age") && stack.getTag().getInt("Age") < 0)
                age = 0;
            return age;
        });
        //Nectar
        FabricModelPredicateProviderRegistry.register(BEE_BOTTLE, new ResourceLocation("nectar"), (stack, clientWorld, livingEntity, i) -> {
            if (livingEntity instanceof Bee) {
                CompoundTag nbt = stack.getOrCreateTag();
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
        FabricModelPredicateProviderRegistry.register(BEE_BOTTLE, new ResourceLocation("sting"), (stack, clientWorld, livingEntity, i) -> {
            if (livingEntity instanceof Bee) {
                CompoundTag nbt = stack.getOrCreateTag();
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
        FabricModelPredicateProviderRegistry.register(BEE_BOTTLE, new ResourceLocation("anger"), (stack, world, holder, seed) -> {
            float anger = 1;
            if (stack.getTag() != null && stack.getTag().contains("Anger") && stack.getTag().getInt("Anger") < 0)
                anger = 0;
            return anger;
        });


        //Golden Buckets
        FabricModelPredicateProviderRegistry.register(GOLDEN_WATER_BUCKET, new ResourceLocation("level"), (stack, level, entity, hash) -> stack.getOrCreateTag().getInt("FluidLevel") / 10f);
        FabricModelPredicateProviderRegistry.register(GOLDEN_LAVA_BUCKET, new ResourceLocation("level"), (stack, level, entity, hash) -> stack.getOrCreateTag().getInt("FluidLevel") / 10f);
        FabricModelPredicateProviderRegistry.register(GOLDEN_MILK_BUCKET, new ResourceLocation("level"), (stack, level, entity, hash) -> stack.getOrCreateTag().getInt("FluidLevel") / 10f);
        FabricModelPredicateProviderRegistry.register(GOLDEN_POWDER_SNOW_BUCKET, new ResourceLocation("level"), (stack, level, entity, hash) -> stack.getOrCreateTag().getInt("FluidLevel") / 10f);
    }
}
