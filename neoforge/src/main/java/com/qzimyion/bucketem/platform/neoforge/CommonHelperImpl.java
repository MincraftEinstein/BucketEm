package com.qzimyion.bucketem.platform.neoforge;

import com.google.common.collect.Lists;
import com.qzimyion.bucketem.neoforge.BucketemNeoForge;
import com.qzimyion.bucketem.platform.CommonHelper;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class CommonHelperImpl {

    public static void addItemsToTabsRegistration(Consumer<CommonHelper.ItemToTabEvent> eventListener) {
        Consumer<BuildCreativeModeTabContentsEvent> eventConsumer = event -> {
            CommonHelper.ItemToTabEvent itemToTabEvent = new ItemToTabEventImpl(event);
            eventListener.accept(itemToTabEvent);
        };
        PlatformHelperImpl.getCurrentBus().addListener(EventPriority.LOW, eventConsumer);
    }

    private record ItemToTabEventImpl(BuildCreativeModeTabContentsEvent event) implements CommonHelper.ItemToTabEvent {
        @Override
        public void addItems(ResourceKey<CreativeModeTab> tab, @Nullable Predicate<ItemStack> target, boolean after, List<ItemStack> items) {
            if (event.getTabKey() != tab) return;
            if (target != null) {
                if (after) {
                    ItemStack last = findLast(event, target);
                    if (!last.isEmpty()) {
                        for (int j = items.size(); j > 0; j--) {
                            event.insertAfter(last, items.get(j - 1), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                        }
                        return;
                    } else {
                        throw new RuntimeException("Failed to find target item before for items: " + items);
                    }
                } else {
                    ItemStack first = findFirst(event, target);
                    if (!first.isEmpty()) {
                        for (var s : items) {
                            event.insertBefore(first, s, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                        }
                        return;
                    } else {
                        throw new RuntimeException("Failed to find target item after for items: " + items);
                    }
                }
            }
            event.acceptAll(items);
        }

        private ItemStack findFirst(BuildCreativeModeTabContentsEvent event, Predicate<ItemStack> target) {
            for (var s : event.getParentEntries()) {
                if (target.test(s)) {
                    return s;
                }
            }
            return ItemStack.EMPTY;
        }

        private ItemStack findLast(BuildCreativeModeTabContentsEvent event, Predicate<ItemStack> target) {
            boolean foundOne = false;
            ItemStack previous = ItemStack.EMPTY;
            for (var s : event.getParentEntries()) {
                if (target.test(s)) {
                    foundOne = true;
                    previous = s;
                } else {
                    if (foundOne) return previous;
                }
            }
            return previous;
        }
    }
}
