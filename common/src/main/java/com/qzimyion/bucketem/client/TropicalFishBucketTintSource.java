package com.qzimyion.bucketem.client;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.component.DataComponents;
import net.minecraft.util.ARGB;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.TropicalFish;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.atomic.AtomicIntegerArray;

public record TropicalFishBucketTintSource(int layer) implements ItemTintSource {
    public static final MapCodec<TropicalFishBucketTintSource> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(Codec.INT.fieldOf("layer").forGetter(TropicalFishBucketTintSource::layer))
                    .apply(instance, TropicalFishBucketTintSource::new));

    @Override
    public int calculate(ItemStack itemStack, @Nullable ClientLevel clientLevel, @Nullable LivingEntity livingEntity) {
        if (layer == 0) return -1;
        int baseColor = DyeColor.ORANGE.getTextureDiffuseColor();
        int basePatternColor = DyeColor.WHITE.getTextureDiffuseColor();
        AtomicIntegerArray colorComponents = new AtomicIntegerArray(new int[]{
                ARGB.red(layer == 1 ? baseColor : basePatternColor),
                ARGB.green(layer == 1 ? baseColor : basePatternColor),
                ARGB.blue(layer == 1 ? baseColor : basePatternColor)
        });
        CustomData.update(DataComponents.BUCKET_ENTITY_DATA, itemStack, compoundTag -> {
            if (compoundTag != null && compoundTag.contains(TropicalFish.BUCKET_VARIANT_TAG)) {
                int variant = compoundTag.getInt(TropicalFish.BUCKET_VARIANT_TAG);
                int color = (layer == 1 ? TropicalFish.getBaseColor(variant) : TropicalFish.getPatternColor(variant)).getTextureDiffuseColor();
                int red = ARGB.red(color);
                int green = ARGB.green(color);
                int blue = ARGB.blue(color);
                colorComponents.set(0, red);
                colorComponents.set(1, green);
                colorComponents.set(2, blue);
            }
        });
        return ARGB.color(255, colorComponents.get(0), colorComponents.get(1), colorComponents.get(2));
    }

    @Override
    public @NotNull MapCodec<? extends ItemTintSource> type() {
        return CODEC;
    }
}
