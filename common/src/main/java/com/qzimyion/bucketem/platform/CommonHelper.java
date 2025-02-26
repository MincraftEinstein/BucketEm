package com.qzimyion.bucketem.platform;

import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.architectury.platform.Platform;
import net.fabricmc.api.EnvType;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class CommonHelper {

    public interface QuadConsumer<K, V, S, T> {
        void accept(K k, V v, S s, T t);
    }

    @ExpectPlatform
    public static void addItemsToTabsRegistration(Consumer<ItemToTabEvent> event) {
        throw new AssertionError();
    }

    public record ItemToTabEvent(
            QuadConsumer<ResourceKey<CreativeModeTab>, @Nullable Predicate<ItemStack>, Boolean, Collection<ItemStack>> action) {


        public void add(ResourceKey<CreativeModeTab> tab, ItemLike... items) {
            addAfter(tab, null, items);
        }

        public void add(ResourceKey<CreativeModeTab> tab, ItemStack... items) {
            addAfter(tab, null, items);
        }

        public void addAfter(ResourceKey<CreativeModeTab> tab, Predicate<ItemStack> target, ItemLike... items) {
            List<ItemStack> stacks = new ArrayList<>();
            for (var i : items) {
                if (i.asItem().getDefaultInstance().isEmpty()) {
                    if (Platform.getEnv()== EnvType.SERVER)
                        throw new IllegalStateException("Attempted to add empty item " + i + " to item tabs");
                } else stacks.add(i.asItem().getDefaultInstance());
            }
            action.accept(tab, target, true, stacks);
        }

        public void addAfter(ResourceKey<CreativeModeTab> tab, Predicate<ItemStack> target, ItemStack... items) {
            action.accept(tab, target, true, java.util.List.of(items));
        }

        public void addBefore(ResourceKey<CreativeModeTab> tab, Predicate<ItemStack> target, ItemLike... items) {
            List<ItemStack> stacks = new ArrayList<>();
            for (var i : items) {
                if (i.asItem().getDefaultInstance().isEmpty()) {
                    if (Platform.getEnv()== EnvType.SERVER)
                        throw new IllegalStateException("Attempted to add empty item " + i + " to item tabs");
                } else stacks.add(i.asItem().getDefaultInstance());
            }
            action.accept(tab, target, false, stacks);
        }

        public void addBefore(ResourceKey<CreativeModeTab> tab, Predicate<ItemStack> target, ItemStack... items) {
            action.accept(tab, target, false, java.util.List.of(items));
        }
    }
}
