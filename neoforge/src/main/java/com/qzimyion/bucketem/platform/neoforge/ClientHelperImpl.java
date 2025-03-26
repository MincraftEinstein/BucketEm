package com.qzimyion.bucketem.platform.neoforge;

import com.qzimyion.bucketem.neoforge.BucketemNeoForge;
import com.qzimyion.bucketem.platform.ClientHelper;
import net.minecraft.client.renderer.item.ItemProperties;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

import java.lang.ref.WeakReference;
import java.util.function.Consumer;

public class ClientHelperImpl {

    public static void addItemColorsRegistration(Consumer<ClientHelper.ItemColorEvent> eventListener){
        Consumer<RegisterColorHandlersEvent.Item> eventConsumer = event -> eventListener.accept(event::register);
        IEventBus bus = PlatformHelperImpl.currentBus.get();
        assert bus != null;
        bus.addListener(eventConsumer);
    }

    public static void addModelPredicatesRegistration(Consumer<ClientHelper.ModelPredicates> eventListener) {
        IEventBus bus = PlatformHelperImpl.currentBus.get();
        assert bus != null;
        bus.addListener((FMLClientSetupEvent event) -> eventListener.accept(ItemProperties::register));
    }
}
