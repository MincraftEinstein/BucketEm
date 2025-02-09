package com.qzimyion.bucketem.core.registry.items;

import com.qzimyion.bucketem.common.items.frogs.DryFrogBuckets;
import com.qzimyion.bucketem.common.items.EntityBottleItem;
import com.qzimyion.bucketem.common.items.frogs.FrogBuckets;
import com.qzimyion.bucketem.common.items.SlimeBottle;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.FrogVariant;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.MobBucketItem;
import net.minecraft.world.level.material.Fluids;

import static com.qzimyion.bucketem.BucketEmCommon.MOD_ID;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(MOD_ID, Registries.ITEM);

    //==Buckets==//
    public static final RegistrySupplier<Item> STRIDER_BUCKET = ITEMS.register("strider_bucket",
            ()-> new MobBucketItem(EntityType.STRIDER, Fluids.LAVA, SoundEvents.BUCKET_FILL_LAVA, new Item.Properties().stacksTo(1)));
    public static final RegistrySupplier<Item> SQUID_BUCKET = ITEMS.register("squid_bucket",
            ()-> new MobBucketItem(EntityType.SQUID, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().stacksTo(1)));
    public static final RegistrySupplier<Item> GLOW_SQUID_BUCKET = ITEMS.register("glow_squid_bucket",
            ()-> new MobBucketItem(EntityType.GLOW_SQUID, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().stacksTo(1)));
    public static final RegistrySupplier<Item> TEMPERATE_FROG_BUCKET = ITEMS.register("temperate_frog_bucket",
            ()-> new FrogBuckets(FrogVariant.TEMPERATE, Fluids.WATER, new Item.Properties().stacksTo(1)));
    public static final RegistrySupplier<Item> TROPICAL_FROG_BUCKET = ITEMS.register("tropical_frog_bucket",
            ()-> new FrogBuckets(FrogVariant.WARM, Fluids.WATER, new Item.Properties().stacksTo(1)));
    public static final RegistrySupplier<Item> TUNDRA_FROG_BUCKET = ITEMS.register("tundra_frog_bucket",
            ()-> new FrogBuckets(FrogVariant.COLD, Fluids.WATER, new Item.Properties().stacksTo(1)));
    public static final RegistrySupplier<Item> DRY_TEMPERATE_FROG_BUCKET = ITEMS.register("dry_temperate_frog_bucket",
            ()-> new DryFrogBuckets(FrogVariant.TEMPERATE, Fluids.WATER, new Item.Properties().stacksTo(1)));
    public static final RegistrySupplier<Item> DRY_TROPICAL_FROG_BUCKET = ITEMS.register("dry_tropical_frog_bucket",
            ()-> new DryFrogBuckets(FrogVariant.WARM, Fluids.WATER, new Item.Properties().stacksTo(1)));
    public static final RegistrySupplier<Item> DRY_TUNDRA_FROG_BUCKET = ITEMS.register("dry_tundra_frog_bucket",
            ()-> new DryFrogBuckets(FrogVariant.COLD, Fluids.WATER, new Item.Properties().stacksTo(1)));
    public static final RegistrySupplier<Item> TURTLE_BUCKET = ITEMS.register("turtle_bucket",
            ()-> new MobBucketItem(EntityType.TURTLE, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().stacksTo(1)));

    //==Books==//
    public static final RegistrySupplier<Item>  ALLAY_POSSESSED_BOOK = ITEMS.register("allay_possessed_book",
            ()-> new EntityBottleItem(EntityType.ALLAY, SoundEvents.ENCHANTMENT_TABLE_USE, new Item.Properties().stacksTo(1)));
    public static final RegistrySupplier<Item>  VEX_POSSESSED_BOOK = ITEMS.register("vex_possessed_book",
            ()-> new EntityBottleItem(EntityType.VEX, SoundEvents.ENCHANTMENT_TABLE_USE, new Item.Properties().stacksTo(1)));

    //==Bottles==//
    public static final RegistrySupplier<Item> BEE_BOTTLE = ITEMS.register("bee_bottle",
            ()-> new EntityBottleItem(EntityType.BEE, SoundEvents.BOTTLE_FILL_DRAGONBREATH, new Item.Properties().stacksTo(1)));
    public static final RegistrySupplier<Item> SILVERFISH_BOTTLE = ITEMS.register("silverfish_bottle",
            ()-> new EntityBottleItem(EntityType.SILVERFISH, SoundEvents.BOTTLE_FILL_DRAGONBREATH, new Item.Properties().stacksTo(1)));
    public static final RegistrySupplier<Item> ENDERMITE_BOTTLE = ITEMS.register("endermite_bottle",
            ()-> new EntityBottleItem(EntityType.ENDERMITE, SoundEvents.BOTTLE_FILL_DRAGONBREATH, new Item.Properties().stacksTo(1)));
    public static final RegistrySupplier<Item> SLIME_BOTTLE = ITEMS.register("slime_bottle", ()-> new SlimeBottle(new Item.Properties().stacksTo(1)));
    public static final RegistrySupplier<Item> MAGMA_CUBE_BOTTLE = ITEMS.register("magma_bottle", ()-> new SlimeBottle(new Item.Properties().stacksTo(1)));

    public static void registerItems(){
        ITEMS.register();
    }
}
