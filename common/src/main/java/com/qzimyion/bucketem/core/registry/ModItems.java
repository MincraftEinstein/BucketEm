package com.qzimyion.bucketem.core.registry;

import com.qzimyion.bucketem.BucketEmCommon;
import com.qzimyion.bucketem.common.items.Frogs.*;
import com.qzimyion.bucketem.common.items.EntityBottleItem;
import com.qzimyion.bucketem.common.items.SlimeBottle;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MobBucketItem;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.material.Fluids;

import static com.qzimyion.bucketem.BucketEmCommon.MOD_ID;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(MOD_ID, Registries.ITEM);

    static String string = MOD_ID + ":";

    //==Buckets==//
    public static final RegistrySupplier<Item> STRIDER_BUCKET = ITEMS.register("strider_bucket",
            ()-> new MobBucketItem(EntityType.STRIDER, Fluids.LAVA, SoundEvents.BUCKET_FILL_LAVA, new Item.Properties().stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)));

    public static final RegistrySupplier<Item> SQUID_BUCKET = ITEMS.register("squid_bucket",
            ()-> new MobBucketItem(EntityType.SQUID, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().useItemDescriptionPrefix()
                    .setId(ResourceKey.create(Registries.ITEM, ResourceLocation.parse(string + "squid_bucket"))).stacksTo(1)));

    public static final RegistrySupplier<Item> GLOW_SQUID_BUCKET = ITEMS.register("glow_squid_bucket",
            ()-> new MobBucketItem(EntityType.GLOW_SQUID, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().useItemDescriptionPrefix()
                    .setId(ResourceKey.create(Registries.ITEM, ResourceLocation.parse(string + "glow_squid_bucket"))).stacksTo(1)));

    public static final RegistrySupplier<Item> TEMPERATE_FROG_BUCKET = ITEMS.register("temperate_frog_bucket",
            ()-> new TemperateFrogBuckets(Fluids.WATER, new Item.Properties().useItemDescriptionPrefix()
                    .setId(ResourceKey.create(Registries.ITEM, ResourceLocation.parse(string + "temperate_frog_bucket"))).stacksTo(1)));

    public static final RegistrySupplier<Item> TROPICAL_FROG_BUCKET = ITEMS.register("tropical_frog_bucket",
            ()-> new WarmFrogBuckets(Fluids.WATER, new Item.Properties().useItemDescriptionPrefix()
                    .setId(ResourceKey.create(Registries.ITEM, ResourceLocation.parse(string + "tropical_frog_bucket"))).stacksTo(1)));

    public static final RegistrySupplier<Item> TUNDRA_FROG_BUCKET = ITEMS.register("tundra_frog_bucket",
            ()-> new ColdFrogBuckets(Fluids.WATER, new Item.Properties().useItemDescriptionPrefix()
                    .setId(ResourceKey.create(Registries.ITEM, ResourceLocation.parse(string + "tundra_frog_bucket"))).stacksTo(1)));

    public static final RegistrySupplier<Item> DRY_TEMPERATE_FROG_BUCKET = ITEMS.register("dry_temperate_frog_bucket",
            ()-> new TemperateDryFrogBuckets(Fluids.WATER, new Item.Properties().useItemDescriptionPrefix()
                    .setId(ResourceKey.create(Registries.ITEM, ResourceLocation.parse(string + "dry_temperate_frog_bucket"))).stacksTo(1)));

    public static final RegistrySupplier<Item> DRY_TROPICAL_FROG_BUCKET = ITEMS.register("dry_tropical_frog_bucket",
            ()-> new WarmDryFrogBuckets(Fluids.WATER, new Item.Properties().useItemDescriptionPrefix()
                    .setId(ResourceKey.create(Registries.ITEM, ResourceLocation.parse(string + "dry_tropical_frog_bucket"))).stacksTo(1)));

    public static final RegistrySupplier<Item> DRY_TUNDRA_FROG_BUCKET = ITEMS.register("dry_tundra_frog_bucket",
            ()-> new ColdDryFrogBuckets(Fluids.WATER, new Item.Properties().useItemDescriptionPrefix()
                    .setId(ResourceKey.create(Registries.ITEM, ResourceLocation.parse(string + "dry_tundra_frog_bucket"))).stacksTo(1)));

    public static final RegistrySupplier<Item> TURTLE_BUCKET = ITEMS.register("turtle_bucket",
            ()-> new MobBucketItem(EntityType.TURTLE, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().useItemDescriptionPrefix()
                    .setId(ResourceKey.create(Registries.ITEM, ResourceLocation.parse(string + "turtle_bucket"))).stacksTo(1)));

    //==Books==//
    public static final RegistrySupplier<Item>  ALLAY_POSSESSED_BOOK = ITEMS.register("allay_possessed_book",
            ()-> new EntityBottleItem(EntityType.ALLAY, Items.BOOK, SoundEvents.ENCHANTMENT_TABLE_USE, new Item.Properties().useItemDescriptionPrefix()
                    .setId(ResourceKey.create(Registries.ITEM, ResourceLocation.parse(string + "allay_possessed_book"))).stacksTo(1)));

    public static final RegistrySupplier<Item>  VEX_POSSESSED_BOOK = ITEMS.register("vex_possessed_book",
            ()-> new EntityBottleItem(EntityType.VEX, Items.BOOK, SoundEvents.ENCHANTMENT_TABLE_USE, new Item.Properties().useItemDescriptionPrefix()
                    .setId(ResourceKey.create(Registries.ITEM, ResourceLocation.parse(string + "vex_possessed_book"))).stacksTo(1)));

    //==Bottles==//
    public static final RegistrySupplier<Item> BEE_BOTTLE = ITEMS.register("bee_bottle",
            ()-> new EntityBottleItem(EntityType.BEE, Items.GLASS_BOTTLE, SoundEvents.BOTTLE_FILL_DRAGONBREATH, new Item.Properties().useItemDescriptionPrefix()
                    .setId(ResourceKey.create(Registries.ITEM, ResourceLocation.parse(string + "bee_bottle"))).stacksTo(1)));

    public static final RegistrySupplier<Item> SILVERFISH_BOTTLE = ITEMS.register("silverfish_bottle",
            ()-> new EntityBottleItem(EntityType.SILVERFISH, Items.GLASS_BOTTLE, SoundEvents.BOTTLE_FILL_DRAGONBREATH, new Item.Properties().useItemDescriptionPrefix()
                    .setId(ResourceKey.create(Registries.ITEM, ResourceLocation.parse(string + "silverfish_bottle"))).stacksTo(1)));

    public static final RegistrySupplier<Item> ENDERMITE_BOTTLE = ITEMS.register("endermite_bottle",
            ()-> new EntityBottleItem(EntityType.ENDERMITE, Items.GLASS_BOTTLE, SoundEvents.BOTTLE_FILL_DRAGONBREATH, new Item.Properties().useItemDescriptionPrefix()
                    .setId(ResourceKey.create(Registries.ITEM, ResourceLocation.parse(string + "endermite_bottle"))).stacksTo(1)));

    public static final RegistrySupplier<Item> SLIME_BOTTLE = ITEMS.register("slime_bottle",
            ()-> new SlimeBottle(EntityType.SLIME, Items.GLASS_BOTTLE, SoundEvents.BOTTLE_FILL_DRAGONBREATH, new Item.Properties().useItemDescriptionPrefix()
                    .setId(ResourceKey.create(Registries.ITEM, ResourceLocation.parse(string + "slime_bottle"))).stacksTo(1)));

    public static final RegistrySupplier<Item> MAGMA_CUBE_BOTTLE = ITEMS.register("magma_bottle", ()-> new EntityBottleItem(EntityType.MAGMA_CUBE, Items.GLASS_BOTTLE, SoundEvents.BOTTLE_FILL_DRAGONBREATH ,new Item.Properties().useItemDescriptionPrefix()
            .setId(ResourceKey.create(Registries.ITEM, ResourceLocation.parse(string + "magma_bottle"))).stacksTo(1)));

    public static void registerItems(){
        ITEMS.register();
    }
}
