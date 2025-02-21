package com.qzimyion.bucketem.datagen;

import com.qzimyion.bucketem.items.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.ItemTags;
import java.util.concurrent.CompletableFuture;

public class ModItemTagsDatagen extends FabricTagProvider.ItemTagProvider{
    public ModItemTagsDatagen(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider arg) {
        getOrCreateTagBuilder(ItemTags.BOOKSHELF_BOOKS).add(ModItems.ALLAY_POSSESSED_BOOK).add(ModItems.VEX_POSSESSED_BOOK);
    }
}
