package com.qzimyion.bucketem.core.mixin.ItemMixins;

import com.mojang.serialization.MapCodec;
import com.qzimyion.bucketem.BucketEmCommon;
import com.qzimyion.bucketem.client.TropicalFishBucketTintSource;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.color.item.ItemTintSources;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemTintSources.class)
public class ItemTintSourcesMixin {

    @Shadow @Final
    private static ExtraCodecs.LateBoundIdMapper<ResourceLocation, MapCodec<? extends ItemTintSource>> ID_MAPPER;

    @Inject(method="bootstrap", at = @At("HEAD"))
    private static void extraBootstrap (CallbackInfo info) {
        ID_MAPPER.put(ResourceLocation.tryBuild(BucketEmCommon.MOD_ID, "dynamic_tinting"), TropicalFishBucketTintSource.CODEC);
    }
}
