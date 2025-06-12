package com.qzimyion.bucketem.util;

import com.qzimyion.bucketem.core.registry.ModItems;
import net.minecraft.Util;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import static com.qzimyion.bucketem.util.BuckEmBucketable.EntityBucketData.of;

public interface BuckEmBucketable extends Bucketable {

    String FROM_BOTTLE = "bucketem$FromBottle";
    String FROM_BOTTLE_LEGACY = "FromBottle";

    Map<EntityType<?>, EntityBucketData> MAP = Util.make(new HashMap<>(), map -> {
        map.put(EntityType.BEE, of(Items.GLASS_BOTTLE, ModItems.BEE_BOTTLE, SoundEvents.BOTTLE_FILL_DRAGONBREATH));
        map.put(EntityType.ENDERMITE, of(Items.GLASS_BOTTLE, ModItems.ENDERMITE_BOTTLE, SoundEvents.BOTTLE_FILL_DRAGONBREATH));
        map.put(EntityType.SILVERFISH, of(Items.GLASS_BOTTLE, ModItems.SILVERFISH_BOTTLE, SoundEvents.BOTTLE_FILL_DRAGONBREATH));
        map.put(EntityType.SLIME, of(Items.GLASS_BOTTLE, ModItems.SLIME_BOTTLE, SoundEvents.BOTTLE_FILL_DRAGONBREATH));
        map.put(EntityType.MAGMA_CUBE, of(Items.GLASS_BOTTLE, ModItems.MAGMA_CUBE_BOTTLE, SoundEvents.BOTTLE_FILL_DRAGONBREATH));
        map.put(EntityType.STRIDER, of(Items.LAVA_BUCKET, ModItems.STRIDER_BUCKET, SoundEvents.BUCKET_FILL_LAVA));
        map.put(EntityType.SQUID, of(Items.WATER_BUCKET, ModItems.SQUID_BUCKET, SoundEvents.BUCKET_FILL_FISH));
        map.put(EntityType.GLOW_SQUID, of(Items.WATER_BUCKET, ModItems.GLOW_SQUID_BUCKET, SoundEvents.BUCKET_FILL_FISH));
        map.put(EntityType.TURTLE, of(Items.WATER_BUCKET, ModItems.TURTLE_BUCKET, SoundEvents.BUCKET_FILL_TADPOLE));
        map.put(EntityType.VEX, of(Items.BOOK, ModItems.VEX_POSSESSED_BOOK, SoundEvents.ENCHANTMENT_TABLE_USE));
        map.put(EntityType.ALLAY, of(Items.BOOK, ModItems.ALLAY_POSSESSED_BOOK, SoundEvents.ENCHANTMENT_TABLE_USE));
    });

    // doesn't need to be implemented, will shadow Entity.getType()
    EntityType<?> getType();

    @Override
    default ItemStack getBucketItemStack() {
        return MAP.get(getType()).bucketedMobItem().get().getDefaultInstance();
    }

    @Override
    default SoundEvent getPickupSound() {
        return MAP.get(getType()).pickupSound();
    }

    record EntityBucketData(Item bucketItem, Supplier<Item> bucketedMobItem, SoundEvent pickupSound) {

        public static EntityBucketData of(Item bucketItem, Supplier<Item> bucketedMobItem, SoundEvent pickupSound) {
            return new EntityBucketData(bucketItem, bucketedMobItem, pickupSound);
        }
    }
}
