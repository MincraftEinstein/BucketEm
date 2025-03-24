package com.qzimyion.bucketem.client;

import net.minecraft.client.color.item.ItemColor;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.util.FastColor;
import net.minecraft.world.entity.animal.TropicalFish;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;

import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicReference;

public class TropicalFishBucketItemColour implements ItemColor {

    @Override
    public int getColor(ItemStack itemStack, int i) {
        if (i == 0) return -1;
        int baseColor = DyeColor.ORANGE.getTextureDiffuseColor();
        int basePatternColor = DyeColor.WHITE.getTextureDiffuseColor();
        AtomicIntegerArray colorComponents = new AtomicIntegerArray(new int[]{
                FastColor.ARGB32.red(i == 1 ? baseColor : basePatternColor),
                FastColor.ARGB32.green(i == 1 ? baseColor : basePatternColor),
                FastColor.ARGB32.blue(i == 1 ? baseColor : basePatternColor)
        });
        CustomData.update(DataComponents.BUCKET_ENTITY_DATA, itemStack, compoundTag -> {
            if (compoundTag != null && compoundTag.contains(TropicalFish.BUCKET_VARIANT_TAG)) {
                int variant = compoundTag.getInt(TropicalFish.BUCKET_VARIANT_TAG);
                int color = (i == 1 ? TropicalFish.getBaseColor(variant) : TropicalFish.getPatternColor(variant)).getTextureDiffuseColor();
                int red = FastColor.ARGB32.red(color);
                int green = FastColor.ARGB32.green(color);
                int blue = FastColor.ARGB32.blue(color);
                colorComponents.set(0, red);
                colorComponents.set(1, green);
                colorComponents.set(2, blue);
            }
        });
        return FastColor.ARGB32.color(255, colorComponents.get(0), colorComponents.get(1), colorComponents.get(2));
    }
}
