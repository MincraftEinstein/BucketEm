package com.qzimyion.bucketem.client;

import net.minecraft.client.color.item.ItemColor;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.FastColor;
import net.minecraft.world.entity.animal.TropicalFish;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;

public class TropicalFishBucketItemColour implements ItemColor {
    @Override
    public int getColor(ItemStack itemStack, int i) {
        if (i == 0) return 0xFFFFFF;
        CompoundTag tag = itemStack.getTag();
        float[] colorComponents = (i == 1 ? DyeColor.ORANGE : DyeColor.WHITE).getTextureDiffuseColors();

        if (tag != null && tag.contains(TropicalFish.BUCKET_VARIANT_TAG)) {
            int variant = tag.getInt(TropicalFish.BUCKET_VARIANT_TAG);
            colorComponents = (i == 1 ? TropicalFish.getBaseColor(variant) : TropicalFish.getPatternColor(variant)).getTextureDiffuseColors();
        }
        return FastColor.ARGB32.color(255, (int) (colorComponents[0] * 255), (int) (colorComponents[1] * 255), (int) (colorComponents[2] * 255));
    }
}
