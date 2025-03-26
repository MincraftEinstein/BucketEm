package com.qzimyion.bucketem.platform.fabric;

import com.qzimyion.bucketem.platform.CommonHelper;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.world.item.CreativeModeTab;

import java.util.function.Consumer;

public class CommonHelperImpl {

    public static void addItemsToTabsRegistration(Consumer<CommonHelper.ItemToTabEvent> eventListener) {
        CommonHelper.ItemToTabEvent event = (tab, target, after, items) ->
                ItemGroupEvents.modifyEntriesEvent(tab).register(entries -> {
                    if (target == null) {
                        entries.acceptAll(items);
                    } else {
                        if (after) {
                            entries.addAfter(target, items, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                        } else {
                            entries.addBefore(target, items, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                        }
                    }
                });
        eventListener.accept(event);
    }
}
