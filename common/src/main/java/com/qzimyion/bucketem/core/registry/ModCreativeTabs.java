package com.qzimyion.bucketem.core.registry;

import com.qzimyion.bucketem.platform.CommonHelper;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.*;
import net.minecraft.world.level.ItemLike;

import java.util.Arrays;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class ModCreativeTabs {

    //Code taken from here:https://github.com/AstralOrdana/Immersive-Weathering/blob/1.20.0-multiloader/common/src/main/java/com/ordana/immersive_weathering/reg/ModCreativeTab.java


    public static void addItems(CommonHelper.ItemToTabEvent event){
        after(event, Items.LAVA_BUCKET, CreativeModeTabs.TOOLS_AND_UTILITIES, ModItems.STRIDER_BUCKET);
        after(event, Items.TADPOLE_BUCKET, CreativeModeTabs.TOOLS_AND_UTILITIES, ModItems.DRY_TEMPERATE_FROG_BUCKET, ModItems.DRY_TROPICAL_FROG_BUCKET, ModItems.DRY_TUNDRA_FROG_BUCKET, ModItems.TEMPERATE_FROG_BUCKET, ModItems.TROPICAL_FROG_BUCKET, ModItems.TUNDRA_FROG_BUCKET, ModItems.GLOW_SQUID_BUCKET, ModItems.SQUID_BUCKET, ModItems.STRIDER_BUCKET);
        after(event, Items.MILK_BUCKET, CreativeModeTabs.TOOLS_AND_UTILITIES, ModItems.BEE_BOTTLE, ModItems.SILVERFISH_BOTTLE, ModItems.ENDERMITE_BOTTLE, ModItems.SLIME_BOTTLE, ModItems.MAGMA_CUBE_BOTTLE, ModItems.ALLAY_POSSESSED_BOOK, ModItems.VEX_POSSESSED_BOOK);
    }

    private static void after(CommonHelper.ItemToTabEvent event, Item target, ResourceKey<CreativeModeTab> tab, Supplier<?>... items) {
        after(event, i -> i.is(target), tab, items);
    }

    private static void after(CommonHelper.ItemToTabEvent event, Predicate<ItemStack> targetPred, ResourceKey<CreativeModeTab> tab, Supplier<?>... items) {
        ItemLike[] entries = Arrays.stream(items).map((supplier -> (ItemLike) (supplier.get()))).toArray(ItemLike[]::new);
        event.addAfter(tab, targetPred, entries);
    }

}
