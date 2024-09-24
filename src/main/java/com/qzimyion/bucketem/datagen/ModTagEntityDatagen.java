package com.qzimyion.bucketem.datagen;

import com.qzimyion.bucketem.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ModTagEntityDatagen extends FabricTagProvider.EntityTypeTagProvider {

    public ModTagEntityDatagen(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    public void configure(RegistryWrapper.WrapperLookup arg) {
        getOrCreateTagBuilder(ModTags.EntityTypeTags.MILKABLE_ENTITY)
                .add(EntityType.COW)
                .add(EntityType.GOAT)
        ;
    }
}
