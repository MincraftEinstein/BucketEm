package com.qzimyion.bucketem.fabric.datagen.langDatagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;

import static com.qzimyion.bucketem.core.registry.ModItems.*;

public class HindiDatagen extends FabricLanguageProvider {


    public HindiDatagen(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, "hi_in", registryLookup);
    }

    @Override
    public void generateTranslations(HolderLookup.Provider registryLookup, TranslationBuilder translationBuilder) {
        //==Buckets==//
        translationBuilder.add(STRIDER_BUCKET.get(), "स्ट्राइडर की बाल्टी");
        translationBuilder.add(SQUID_BUCKET.get(), "स्क्वीड की बाल्टी");
        translationBuilder.add(TEMPERATE_FROG_BUCKET.get(), "शीतोष्ण मेंढक की बाल्टी");
        translationBuilder.add(TROPICAL_FROG_BUCKET.get(), "उष्णकटिबंधीय मेंढक की बाल्टी");
        translationBuilder.add(TUNDRA_FROG_BUCKET.get(), "हिमाच्छन्न मेंढक की बाल्टी");
        translationBuilder.add(DRY_TEMPERATE_FROG_BUCKET.get(), "शीतोष्ण मेंढक की सूखी बाल्टी");
        translationBuilder.add(DRY_TROPICAL_FROG_BUCKET.get(), "उष्णकटिबंधीय मेंढक की सूखी बाल्टी");
        translationBuilder.add(DRY_TUNDRA_FROG_BUCKET.get(), "िमाच्छन्न मेंढक की सूखी बाल्टी");
        translationBuilder.add(TURTLE_BUCKET.get(), "कछुए की बाल्टी");
        translationBuilder.add(GLOW_SQUID_BUCKET.get(), "ग्लो स्क्विड की बाल्टी");

        //==Books==//
        translationBuilder.add(ALLAY_POSSESSED_BOOK.get(), "अलाय की किताब");
        translationBuilder.add(VEX_POSSESSED_BOOK.get(), "वेक्स की किताब");

        //Bottles==//
        translationBuilder.add(BEE_BOTTLE.get(), "बोतल में मधुमक्खी");
        translationBuilder.add(SILVERFISH_BOTTLE.get(), "बोतल में रजत मीन");
        translationBuilder.add(ENDERMITE_BOTTLE.get(), "बोतल में एंडर्माइट");
        translationBuilder.add(SLIME_BOTTLE.get(), "बोतल में स्लाइम");
        translationBuilder.add(MAGMA_CUBE_BOTTLE.get(), "बोतल में मैग्मा घन");
    }
}
