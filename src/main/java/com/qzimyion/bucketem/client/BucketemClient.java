package com.qzimyion.bucketem.client;

import com.qzimyion.bucketem.compact.BucketemCompactReg;
import com.qzimyion.bucketem.events.ModClientEvents;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.client.color.item.ItemColorProvider;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;

@SuppressWarnings("unused")
public class BucketemClient implements ClientModInitializer {

    public ItemColorProvider getItemColor(ItemColors itemColors, ItemConvertible itemConvertible) {
        return null;
    }

    public static ItemColors getItemColors() {
        return new ItemColors();
    }

    public void onInitializeClient(){
        ModItemModelPredicates.registerModelPredicates();
        BucketemCompactReg.initializeClientCompacts();
        ItemColorProvider provider = getItemColor(getItemColors(), Items.TROPICAL_FISH_BUCKET);
        ColorProviderRegistry.ITEM.register(new TropicalFishBucketItemColour(provider), Items.TROPICAL_FISH_BUCKET);
        ModClientEvents.register();
    }
}
