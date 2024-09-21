package com.qzimyion.bucketem.compact.spelunkery;

import com.ordana.spelunkery.reg.ModFluids;
import com.qzimyion.bucketem.Bucketem;
import com.qzimyion.bucketem.items.NewItems.goldBuckets.GoldenBucketItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class SpelunkeryBucketemItems {

    public static final Item GOLDEN_PORTAL_FLUID_BUCKET = registerItem("golden_portal_fluid_bucket", new GoldenBucketItem(ModFluids.PORTAL_FLUID.get(), new Item.Settings().maxCount(1)));
    public static final Item GOLDEN_SPRING_WATER_BUCKET = registerItem("golden_spring_water_bucket", new GoldenBucketItem(ModFluids.SPRING_WATER.get(), new Item.Settings().maxCount(1)));

    private static Item registerItem(String name, Item item)
    {
        return Registry.register(Registries.ITEM, new Identifier(Bucketem.MOD_ID, name), item);
    }

    public static void registerItems(){

        Bucketem.LOGGER.info("Registering Spelunkery Mod Items");
    }
}
