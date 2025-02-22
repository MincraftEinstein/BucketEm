package com.qzimyion.bucketem.fabric.datagen.langDatagen.english;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;

import static com.qzimyion.bucketem.core.registry.ModItems.*;

public class NewZealandEnglishDatagen extends FabricLanguageProvider {
    public NewZealandEnglishDatagen(FabricDataOutput dataOutput) {
        super(dataOutput, "en_nz");
    }

    @Override
    public void generateTranslations(TranslationBuilder translationBuilder) {
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
        translationBuilder.add(GOLDEN_BUCKET.get(), "Golden Bucket");
        translationBuilder.add(GOLDEN_WATER_BUCKET.get(), "Golden Water Bucket");
        translationBuilder.add(GOLDEN_LAVA_BUCKET.get(), "Golden Lava Bucket");
        translationBuilder.add(GOLDEN_POWDER_SNOW_BUCKET.get(), "Golden Powder Snow Bucket");
        translationBuilder.add(GOLDEN_MILK_BUCKET.get(), "Golden Milk Bucket");
    }
}
