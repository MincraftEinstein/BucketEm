package com.qzimyion.bucketem.items;

import com.qzimyion.bucketem.Bucketem;
import com.qzimyion.bucketem.items.NewItems.Bottles.EntityBottle;
import com.qzimyion.bucketem.items.NewItems.Bottles.MagmaCubeBottle;
import com.qzimyion.bucketem.items.NewItems.Bottles.SlimeBottle;
import com.qzimyion.bucketem.items.NewItems.EntityBook;
import com.qzimyion.bucketem.items.NewItems.FrogBuckets.DryFrogBuckets;
import com.qzimyion.bucketem.items.NewItems.FrogBuckets.FrogBuckets;
import com.qzimyion.bucketem.items.NewItems.GoldBuckets.GoldenBucketItem;
//import com.qzimyion.bucketem.items.NewItems.GoldBuckets.GoldenEntityBucketItem;
import com.qzimyion.bucketem.items.NewItems.GoldBuckets.GoldenMilkBucket;
import com.qzimyion.bucketem.items.NewItems.GoldBuckets.PowderSnowGoldenBucket;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.FrogVariant;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.MobBucketItem;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluids;

import static net.minecraft.world.item.Items.GLASS_BOTTLE;

public class ModItems {

    //Buckets
    public static final Item STRIDER_BUCKET = registerItem("strider_bucket", new MobBucketItem(EntityType.STRIDER, Fluids.LAVA, SoundEvents.BUCKET_EMPTY_LAVA, new Item.Properties().stacksTo(1)));
    public static final Item SQUID_BUCKET = registerItem("squid_bucket", new MobBucketItem(EntityType.SQUID, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, new FabricItemSettings().stacksTo(1)));
    public static final Item GLOW_SQUID_BUCKET = registerItem("glow_squid_bucket", new MobBucketItem(EntityType.GLOW_SQUID, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, new FabricItemSettings().stacksTo(1)));
    public static final Item TEMPERATE_FROG_BUCKET = registerItem("temperate_frog_bucket", new FrogBuckets(FrogVariant.TEMPERATE ,Fluids.WATER ,new FabricItemSettings().stacksTo(1)));
    public static final Item TROPICAL_FROG_BUCKET = registerItem("tropical_frog_bucket", new FrogBuckets(FrogVariant.WARM ,Fluids.WATER, new FabricItemSettings().stacksTo(1)));
    public static final Item TUNDRA_FROG_BUCKET = registerItem("tundra_frog_bucket", new FrogBuckets(FrogVariant.COLD, Fluids.WATER ,new FabricItemSettings().stacksTo(1)));
    public static final Item TURTLE_BUCKET = registerItem("turtle_bucket", new MobBucketItem(EntityType.TURTLE, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, new FabricItemSettings().stacksTo(1)));
    public static final Item DRY_TEMPERATE_FROG_BUCKET = registerItem("dry_temperate_frog_bucket", new DryFrogBuckets(FrogVariant.TEMPERATE ,new FabricItemSettings().stacksTo(1)));
    public static final Item DRY_TROPICAL_FROG_BUCKET = registerItem("dry_tropical_frog_bucket", new DryFrogBuckets(FrogVariant.WARM ,new FabricItemSettings().stacksTo(1)));
    public static final Item DRY_TUNDRA_FROG_BUCKET = registerItem("dry_tundra_frog_bucket", new DryFrogBuckets(FrogVariant.COLD ,new FabricItemSettings().stacksTo(1)));
    public static final Item GOLDEN_BUCKET = registerItem("golden_bucket", new GoldenBucketItem(Fluids.EMPTY, new Item.Properties().stacksTo(16)));
    public static final Item GOLDEN_WATER_BUCKET = registerItem("golden_water_bucket", new GoldenBucketItem(Fluids.WATER, new Item.Properties().stacksTo(1)));
    public static final Item GOLDEN_LAVA_BUCKET = registerItem("golden_lava_bucket", new GoldenBucketItem(Fluids.LAVA, new Item.Properties().stacksTo(1)));
    public static final Item GOLDEN_MILK_BUCKET = registerItem("golden_milk_bucket", new GoldenMilkBucket(new Item.Properties().stacksTo(1)));
    public static final Item GOLDEN_POWDER_SNOW_BUCKET = registerItem("golden_powder_snow_bucket", new PowderSnowGoldenBucket(Blocks.POWDER_SNOW, SoundEvents.BUCKET_EMPTY_POWDER_SNOW, new Item.Properties().stacksTo(1)));

    //Books
    public static final Item ALLAY_POSSESSED_BOOK = registerItem("allay_possessed_book", new EntityBook(EntityType.ALLAY ,new FabricItemSettings().stacksTo(1).rarity(Rarity.UNCOMMON)));
    public static final Item VEX_POSSESSED_BOOK = registerItem("vex_possessed_book", new EntityBook(EntityType.VEX, new FabricItemSettings().stacksTo(1).rarity(Rarity.UNCOMMON)));

    //Bottles
    public static final Item BEE_BOTTLE = registerItem("bee_bottle", new EntityBottle(EntityType.BEE ,new FabricItemSettings().stacksTo(1)));
    public static final Item SILVERFISH_BOTTLE = registerItem("silverfish_bottle", new EntityBottle(EntityType.SILVERFISH, new FabricItemSettings().stacksTo(1)));
    public static final Item ENDERMITE_BOTTLE = registerItem("endermite_bottle", new EntityBottle(EntityType.ENDERMITE, new FabricItemSettings().stacksTo(1)));
    public static final Item SLIME_BOTTLE = registerItem("slime_bottle", new SlimeBottle(new FabricItemSettings().stacksTo(1).craftRemainder(GLASS_BOTTLE)));
    public static final Item MAGMA_CUBE_BOTTLE = registerItem("magma_bottle", new MagmaCubeBottle(new FabricItemSettings().stacksTo(1).craftRemainder(GLASS_BOTTLE)));

    public static Item registerItem(String name, Item item)
    {
        return Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(Bucketem.MOD_ID, name), item);
    }

    public static void registerItems(){

        Bucketem.LOGGER.info("Registering mod Items");
    }
}
