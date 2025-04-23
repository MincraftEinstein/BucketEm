package com.qzimyion.bucketem.fabric.datagen.langDatagen.english;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;

import static com.qzimyion.bucketem.core.registry.ModItems.*;

public class AustralianEnglishDatagen extends FabricLanguageProvider {

    public AustralianEnglishDatagen(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, "en_au", registryLookup);
    }

    @Override
    public void generateTranslations(HolderLookup.Provider registryLookup, TranslationBuilder translationBuilder) {
        //Buckets
        translationBuilder.add(STRIDER_BUCKET.get(), "Bucket of Strider");
        translationBuilder.add(SQUID_BUCKET.get(), "Bucket of Squid");
        translationBuilder.add(GLOW_SQUID_BUCKET.get(), "Bucket of Glow Squid");
        translationBuilder.add(TEMPERATE_FROG_BUCKET.get(), "Bucket of Temperate Frog");
        translationBuilder.add(TROPICAL_FROG_BUCKET.get(), "Bucket of Tropical Frog");
        translationBuilder.add(TUNDRA_FROG_BUCKET.get(), "Bucket of Tundra Frog");
        translationBuilder.add(DRY_TEMPERATE_FROG_BUCKET.get(), "Dry Bucket of Temperate Frog");
        translationBuilder.add(DRY_TROPICAL_FROG_BUCKET.get(), "Dry Bucket of Tropical Frog");
        translationBuilder.add(DRY_TUNDRA_FROG_BUCKET.get(), "Dry Bucket of Tundra Frog");
        translationBuilder.add(TURTLE_BUCKET.get(), "Bucket of Turtle");
        //Books
        translationBuilder.add(ALLAY_POSSESSED_BOOK.get(), "Allay Possessed Book");
        translationBuilder.add(VEX_POSSESSED_BOOK.get(), "Vex Possessed Book");
        //Bottle
        translationBuilder.add(BEE_BOTTLE.get(), "Bee in a Bottle");
        translationBuilder.add(SILVERFISH_BOTTLE.get(), "Silverfish in a Bottle");
        translationBuilder.add(ENDERMITE_BOTTLE.get(), "Endermite in a Bottle");
        translationBuilder.add(SLIME_BOTTLE.get(), "Slime in a Bottle");
        translationBuilder.add(MAGMA_CUBE_BOTTLE.get(), "Magma Cube in a Bottle");
        //Advancements
        translationBuilder.add("bucketem.advancements.husbandry.strider_bucketing.title", "Hardcore Bucketing");
        translationBuilder.add("bucketem.advancements.husbandry.strider_bucketing.description", "Bucket up a Strider using a lava bucket");
        translationBuilder.add("bucketem.advancements.adventure.bottling.title", "Critter Collector");
        translationBuilder.add("bucketem.advancements.adventure.bottling.description", "Bottle up a mob using a glass bottle");
        translationBuilder.add("bucketem.advancements.adventure.slime_ranching.title", "Slime Rancher");
        translationBuilder.add("bucketem.advancements.adventure.slime_ranching.description", "Capture both a slime and a magma cube inside a glass bottle");
        translationBuilder.add("bucketem.advancements.adventure.entomology.title", "Entomology");
        translationBuilder.add("bucketem.advancements.adventure.entomology.description", "Capture all the mobs that can be bottled");
        translationBuilder.add("bucketem.advancements.adventure.curse_of_imprisonment.title", "Curse of Imprisonment");
        translationBuilder.add("bucketem.advancements.adventure.curse_of_imprisonment.description", "Capture an Allay or a Vex using a book");
    }
}
