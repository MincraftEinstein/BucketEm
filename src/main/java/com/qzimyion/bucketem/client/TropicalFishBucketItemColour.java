package com.qzimyion.bucketem.client;

import net.minecraft.client.color.item.ItemColorProvider;
import net.minecraft.entity.passive.TropicalFishEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.ColorHelper;

public class TropicalFishBucketItemColour implements ItemColorProvider {

    @Override
    public int getColor(ItemStack stack, int tintLayer) {
        if (tintLayer == 0) return 0xFFFFFF;
        NbtCompound nbt = stack.getNbt();
        float[] colorComponents = (tintLayer == 1 ? DyeColor.ORANGE : DyeColor.WHITE).getColorComponents();

        if (nbt != null && nbt.contains(TropicalFishEntity.BUCKET_VARIANT_TAG_KEY)) {
            int variant = nbt.getInt(TropicalFishEntity.BUCKET_VARIANT_TAG_KEY);
            colorComponents = (tintLayer == 1 ? TropicalFishEntity.getBaseDyeColor(variant) : TropicalFishEntity.getPatternDyeColor(variant)).getColorComponents();
        }
        return ColorHelper.Argb.getArgb(255, (int) (colorComponents[0] * 255), (int) (colorComponents[1] * 255), (int) (colorComponents[2] * 255));
    }
}
