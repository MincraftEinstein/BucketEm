package com.qzimyion.bucketem.platform.fabric;

import com.qzimyion.bucketem.platform.ClientHelper;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;

import java.util.function.Consumer;

@SuppressWarnings("deprecation")
public class ClientHelperImpl {

    public static void addItemColorsRegistration(Consumer<ClientHelper.ItemColorEvent> eventListener){
        eventListener.accept(ColorProviderRegistry.ITEM::register);
    }

    public static void addModelPredicatesRegistration(Consumer<ClientHelper.ModelPredicates> eventListener){
       eventListener.accept(FabricModelPredicateProviderRegistry::register);
    }
}
