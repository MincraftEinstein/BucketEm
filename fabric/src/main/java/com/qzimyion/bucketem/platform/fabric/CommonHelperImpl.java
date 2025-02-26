package com.qzimyion.bucketem.platform.fabric;

import com.qzimyion.bucketem.platform.CommonHelper;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.world.item.CreativeModeTab;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;

public class CommonHelperImpl {
    public static final Queue<Runnable> AFTER_SETUP_WORK = new ConcurrentLinkedQueue<>();

    public static void addItemsToTabsRegistration(Consumer<CommonHelper.ItemToTabEvent> eventListener) {
        AFTER_SETUP_WORK.add(() -> {
            CommonHelper.ItemToTabEvent event = new CommonHelper.ItemToTabEvent((tab, target, after, items) -> {
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
            });
            eventListener.accept(event);
        });
    }
}
