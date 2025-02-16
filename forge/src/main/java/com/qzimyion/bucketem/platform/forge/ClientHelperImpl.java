package com.qzimyion.bucketem.platform.forge;

import com.qzimyion.bucketem.platform.ClientHelper;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.function.Consumer;

@SuppressWarnings("removal")
public class ClientHelperImpl {

    public static void addItemColorsRegistration(Consumer<ClientHelper.ItemColorEvent> eventListener){
        Consumer<RegisterColorHandlersEvent.Item> eventConsumer = event -> eventListener.accept(event::register);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(eventConsumer);
    }

    public static void addModelPredicatesRegistration(Consumer<ClientHelper.ModelPredicates> eventListener){
        FMLJavaModLoadingContext.get().getModEventBus().addListener(event -> eventListener.accept(ItemProperties::register));
    }
}
