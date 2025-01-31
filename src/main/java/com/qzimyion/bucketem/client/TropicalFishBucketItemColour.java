package com.qzimyion.bucketem.client;

import net.minecraft.client.color.item.ItemColorProvider;
import net.minecraft.entity.passive.TropicalFishEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.util.DyeColor;

public class TropicalFishBucketItemColour implements ItemColorProvider {
    private final ItemColorProvider itemColorProvider;

    public TropicalFishBucketItemColour(ItemColorProvider parent) {
        this.itemColorProvider = parent;
    }


    @Override
    public int getColor(ItemStack stack, int tintLayer) {
        if (tintLayer == 1 || tintLayer == 2){
            NbtCompound nbtCompound = stack.getNbt();
            if (nbtCompound != null && nbtCompound.contains(TropicalFishEntity.BUCKET_VARIANT_TAG_KEY, NbtElement.INT_TYPE)){
                int variant = nbtCompound.getInt(TropicalFishEntity.BUCKET_VARIANT_TAG_KEY);
                DyeColor color = tintLayer == 1 ? TropicalFishEntity.getBaseDyeColor(variant) : TropicalFishEntity.getPatternDyeColor(variant);
                float[] colors = color.getColorComponents();
                return (int) (colors[0] * 255) << 16 | (int) (colors[1] * 255) << 8 | (int) (colors[2] * 255);
            }
        }
        return itemColorProvider != null ? itemColorProvider.getColor(stack, tintLayer) : -1;
    }
}
