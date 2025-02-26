package com.qzimyion.bucketem.platform.forge;

import com.google.common.collect.Lists;
import com.qzimyion.bucketem.platform.CommonHelper;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.ArrayList;
import java.util.function.Consumer;

@SuppressWarnings("removal")
public class CommonHelperImpl {

    public static void addItemsToTabsRegistration(Consumer<CommonHelper.ItemToTabEvent> eventListener) {

        Consumer<BuildCreativeModeTabContentsEvent> eventConsumer = event -> {
            CommonHelper.ItemToTabEvent itemToTabEvent = new CommonHelper.ItemToTabEvent((tab, target, after, items) -> {
                if (tab != event.getTabKey()) return;

                if (target == null) {
                    event.acceptAll(items);
                } else {

                    var entries = event.getEntries();
                    ItemStack lastValid = null;


                    for (var e : entries) {
                        ItemStack item = e.getKey();

                        if (!item.isItemEnabled(event.getFlags())) continue;

                        boolean isValid = target.test(item);
                        if (after && lastValid != null && !isValid) {
                            var rev = Lists.reverse(new ArrayList<>(items));
                            for (var ni : rev) {
                                entries.putAfter(lastValid, ni, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                            }
                            return;
                        }

                        if (isValid) {
                            lastValid = item;
                        }

                        if (!after && isValid) {
                            items.forEach(ni -> entries.putBefore(item, ni, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS));
                            return;
                        }
                    }
                    //add at the end if it fails
                    for (var ni : items) {
                        entries.put(ni, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                    }
                }
            });
            eventListener.accept(itemToTabEvent);
        };
        FMLJavaModLoadingContext.get().getModEventBus().addListener(EventPriority.LOW, eventConsumer);
    }


}
