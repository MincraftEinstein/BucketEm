package com.qzimyion.bucketem.datagen;

import com.qzimyion.bucketem.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.entity.EntityType;
import java.util.concurrent.CompletableFuture;

public class ModTagEntityDatagen extends FabricTagProvider.EntityTypeTagProvider {

    public ModTagEntityDatagen(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    public void addTags(HolderLookup.Provider arg) {
        tag(ModTags.EntityTypeTagsForMod.MILKABLE_ENTITY)
                .add(EntityType.COW)
                .add(EntityType.GOAT)
        ;
    }
}
