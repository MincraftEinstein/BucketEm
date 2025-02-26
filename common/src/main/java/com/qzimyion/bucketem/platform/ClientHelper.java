package com.qzimyion.bucketem.platform;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;

import java.util.function.Consumer;

public class ClientHelper {

    public interface ItemColorEvent {
        void register(ItemColor color, ItemLike... items);
    }

    public interface ModelPredicates {
        void register(Item item, ResourceLocation id, ClampedItemPropertyFunction provider);
    }

    @ExpectPlatform
    public static void addModelPredicatesRegistration(Consumer<ModelPredicates> eventListener){
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void addItemColorsRegistration(Consumer<ItemColorEvent> eventListener) {
        throw new AssertionError();
    }


}
