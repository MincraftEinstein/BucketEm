package com.qzimyion.bucketem.compact.WW;

import com.qzimyion.bucketem.items.ModItems;
import net.frozenblock.wilderwild.entity.variant.JellyfishVariant;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;

public class WWBucketEmItems {

    public static final Item BLUE_JELLYFISH_BUCKET = ModItems.registerItem("blue_jellyfish_bucket", new BEJellyFishVariantBucket(Fluids.WATER, JellyfishVariant.BLUE,  new Item.Settings().maxCount(1)));
    public static final Item LIME_JELLYFISH_BUCKET = ModItems.registerItem("lime_jellyfish_bucket", new BEJellyFishVariantBucket(Fluids.WATER, JellyfishVariant.LIME,  new Item.Settings().maxCount(1)));


    public static void regWWBucketEmItems(){

    }
}
