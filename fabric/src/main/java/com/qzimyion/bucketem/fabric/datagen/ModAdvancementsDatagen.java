package com.qzimyion.bucketem.fabric.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.critereon.ChangeDimensionTrigger;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.KilledTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import static com.qzimyion.bucketem.core.registry.ModItems.*;

public class ModAdvancementsDatagen implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {

    }

    @SuppressWarnings("unused")
    public static class AdvancementsProvider extends FabricAdvancementProvider {
        public AdvancementsProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
            super(output, registryLookup);
        }


//        @Override
//        public void generateAdvancement(Consumer<Advancement> consumer) {
//            Advancement NetherAdvancementEntry = Advancement.Builder.advancement()
//                    .display(Blocks.RED_NETHER_BRICKS,
//                            Component.translatable("advancements.nether.root.title"),
//                            Component.translatable("advancements.nether.root.description"),
//                            new ResourceLocation("textures/gui/advancements/backgrounds/nether.png"),
//                            FrameType.TASK, false, false, false)
//                    .addCriterion("entered_nether", ChangeDimensionTrigger.TriggerInstance.changedDimensionTo(Level.NETHER)).save(consumer, "nether/root");
//
//            Advancement AdventureAdvancementEntry = Advancement.Builder.advancement().display(Items.MAP,
//                            Component.translatable("advancements.adventure.root.title"),
//                            Component.translatable("advancements.adventure.root.description"),
//                            new ResourceLocation("textures/gui/advancements/backgrounds/adventure.png"),
//                            FrameType.TASK, false, false, false)
//                    .requirements(RequirementsStrategy.OR).addCriterion("killed_something",
//                            KilledTrigger.TriggerInstance.playerKilledEntity()).addCriterion("killed_by_something",
//                            KilledTrigger.TriggerInstance.entityKilledPlayer()).save(consumer, "adventure/root");
//
//
//            Advancement rootAdvancement = Advancement.Builder.advancement().parent(NetherAdvancementEntry)
//                    .display(
//                            STRIDER_BUCKET.get(),
//                            Component.translatable("Hardcore Bucketing"),
//                            Component.translatable("Bucket up a strider using a lava bucket"),
//                            null,
//                            FrameType.TASK,
//                            true,
//                            true,
//                            false
//                    )
//                    .addCriterion("got_strider_bucket", InventoryChangeTrigger.TriggerInstance.hasItems(STRIDER_BUCKET.get()))
//                    .save(consumer, "minecraft" + "/strider_bucketing");
//
//            Advancement rootAdvancement1 = Advancement.Builder.advancement().parent(AdventureAdvancementEntry)
//                    .display(
//                            SLIME_BOTTLE.get(),
//                            Component.translatable("Slime Rancher"),
//                            Component.translatable("Bottle up slimes and magma cubes"),
//                            null,
//                            FrameType.TASK,
//                            true,
//                            true,
//                            false
//                    )
//                    .addCriterion("got_slime_bottle", InventoryChangeTrigger.TriggerInstance.hasItems(SLIME_BOTTLE.get()))
//                    .addCriterion("got_magma_cube_bottle", InventoryChangeTrigger.TriggerInstance.hasItems(MAGMA_CUBE_BOTTLE.get()))
//                    .save(consumer, "minecraft" + "/critter_bottling");
//
//            Advancement rootAdvancement2 = Advancement.Builder.advancement().parent(rootAdvancement1)
//                    .display(
//                            BEE_BOTTLE.get(),
//                            Component.translatable("Critter Rancher"),
//                            Component.translatable("Bottle up all the critters out there"),
//                            null,
//                            FrameType.TASK,
//                            true,
//                            true,
//                            false
//                    )
//                    .addCriterion("got_bee_bottle", InventoryChangeTrigger.TriggerInstance.hasItems(BEE_BOTTLE.get()))
//                    .addCriterion("got_silverfish_bottle", InventoryChangeTrigger.TriggerInstance.hasItems(SILVERFISH_BOTTLE.get()))
//                    .addCriterion("got_endermite_bottle", InventoryChangeTrigger.TriggerInstance.hasItems(ENDERMITE_BOTTLE.get()))
//                    .addCriterion("got_slime_bottle", InventoryChangeTrigger.TriggerInstance.hasItems(SLIME_BOTTLE.get()))
//                    .addCriterion("got_magma_cube_bottle", InventoryChangeTrigger.TriggerInstance.hasItems(MAGMA_CUBE_BOTTLE.get()))
//                    .save(consumer, "minecraft" + "/critter_ranching");
//
//            Advancement turtleAdvancement = Advancement.Builder.advancement().parent(AdventureAdvancementEntry)
//                    .display(
//                            TURTLE_BUCKET.get(),
//                            Component.translatable("I like Turtles"),
//                            Component.translatable("Bucket up a Turtle in a water bucket"),
//                            null,
//                            FrameType.TASK,
//                            true,
//                            true,
//                            false
//                    )
//                    .addCriterion("got_turtle_bucket", InventoryChangeTrigger.TriggerInstance.hasItems(TURTLE_BUCKET.get()))
//                    .save(consumer, "minecraft" + "/turtle_bucketing");
//
//        }

        @Override
        public void generateAdvancement(HolderLookup.Provider provider, Consumer<AdvancementHolder> consumer) {

        }
    }
}
