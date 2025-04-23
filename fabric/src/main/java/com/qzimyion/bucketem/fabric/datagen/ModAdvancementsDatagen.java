package com.qzimyion.bucketem.fabric.datagen;

import com.qzimyion.bucketem.core.registry.ModItems;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancements.*;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class ModAdvancementsDatagen implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {

    }
    @SuppressWarnings("unused")
    public static class AdvancementsProvider extends FabricAdvancementProvider {
        public AdvancementsProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
            super(output, registryLookup);
        }

        public static ResourceLocation vanillaId(String path) {
            return ResourceLocation.withDefaultNamespace(path);
        }

        @Override
        public void generateAdvancement(HolderLookup.Provider provider, Consumer<AdvancementHolder> consumer) {
            HolderGetter<Item> items = provider.lookupOrThrow(Registries.ITEM);
            AdvancementHolder striderRoot = Advancement.Builder.advancement().build(vanillaId("husbandry/tactical_fishing"));
            AdvancementHolder bottlingRoot = Advancement.Builder.advancement().build(vanillaId("adventure/root"));
            //==Husbandry==//
            //Strider Bucketing
            Advancement.Builder.advancement()
                    .parent(striderRoot)
                    .display(
                            ModItems.STRIDER_BUCKET.get(),
                            Component.translatable("bucketem.advancements.husbandry.strider_bucketing.title"),
                            Component.translatable("bucketem.advancements.husbandry.strider_bucketing.description"),
                            null,
                            AdvancementType.TASK,
                            true,
                            true,
                            false
                    )
                    .addCriterion("strider_bucket", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(ModItems.STRIDER_BUCKET.get())))
                    .save(consumer, "husbandry/strider_bucketing");
            //==Adventure==//
            //Bottling
            Advancement.Builder.advancement()
                            .parent(bottlingRoot)
                            .requirements(AdvancementRequirements.Strategy.OR)
                            .display(
                                    ModItems.SILVERFISH_BOTTLE.get(),
                                    Component.translatable("bucketem.advancements.adventure.bottling.title"),
                                    Component.translatable("bucketem.advancements.adventure.bottling.description"),
                                    null,
                                    AdvancementType.TASK,
                                    true,
                                    true,
                                    false
                            )
                    .addCriterion("got_silverfish_bottle", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(ModItems.SILVERFISH_BOTTLE.get())))
                    .addCriterion("got_endermite_bottle", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(ModItems.ENDERMITE_BOTTLE.get())))
                    .addCriterion("got_slime_bottle", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(ModItems.SLIME_BOTTLE.get())))
                    .addCriterion("got_magma_cube_bottle", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(ModItems.MAGMA_CUBE_BOTTLE.get())))
                    .addCriterion("got_bee_bottle", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(ModItems.BEE_BOTTLE.get())))
                    .save(consumer, "adventure/bottling");
            //Slime Ranching
            Advancement.Builder.advancement()
                    .parent(Advancement.Builder.advancement().build(vanillaId("adventure/bottling")))
                    .display(
                            ModItems.SLIME_BOTTLE.get(),
                            Component.translatable("bucketem.advancements.adventure.slime_ranching.title"),
                            Component.translatable("bucketem.advancements.adventure.slime_ranching.description"),
                            null,
                            AdvancementType.TASK,
                            true,
                            true,
                            false
                    )
                    .addCriterion("got_slime_bottle", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(ModItems.SLIME_BOTTLE.get())))
                    .addCriterion("got_magma_cube_bottle", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(ModItems.MAGMA_CUBE_BOTTLE.get())))
                    .save(consumer, "adventure/slime_ranching");
            //Entomology
            Advancement.Builder.advancement()
                    .parent(Advancement.Builder.advancement().build(vanillaId("adventure/slime_ranching")))
                    .display(
                            ModItems.BEE_BOTTLE.get(),
                            Component.translatable("bucketem.advancements.adventure.entomology.title"),
                            Component.translatable("bucketem.advancements.adventure.entomology.description"),
                            null,
                            AdvancementType.TASK,
                            true,
                            true,
                            false
                    )
                    .addCriterion("got_silverfish_bottle", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(ModItems.SILVERFISH_BOTTLE.get())))
                    .addCriterion("got_endermite_bottle", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(ModItems.ENDERMITE_BOTTLE.get())))
                    .addCriterion("got_slime_bottle", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(ModItems.SLIME_BOTTLE.get())))
                    .addCriterion("got_magma_cube_bottle", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(ModItems.MAGMA_CUBE_BOTTLE.get())))
                    .addCriterion("got_bee_bottle", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(ModItems.BEE_BOTTLE.get())))
                    .rewards(AdvancementRewards.Builder.experience(50))
                    .save(consumer, "adventure/entomology");
            //Curse of imprisonment
            Advancement.Builder.advancement()
                    .parent(bottlingRoot)
                    .requirements(AdvancementRequirements.Strategy.OR)
                    .display(
                            ModItems.ALLAY_POSSESSED_BOOK.get(),
                            Component.translatable("bucketem.advancements.adventure.curse_of_imprisonment.title"),
                            Component.translatable("bucketem.advancements.adventure.curse_of_imprisonment.description"),
                            null,
                            AdvancementType.GOAL,
                            true,
                            true,
                            false
                    )
                    .addCriterion("got_allay_possessed_book", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(ModItems.ALLAY_POSSESSED_BOOK.get())))
                    .addCriterion("got_vex_possessed_book", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(ModItems.VEX_POSSESSED_BOOK.get())))
                    .save(consumer, "adventure/curse_of_imprisonment");
            //XP IS FUEL
//            Advancement.Builder.advancement()
//                    .parent(Advancement.Builder.advancement().build(vanillaId("adventure/curse_of_imprisonment")))
//                    .display(
//                            Blocks.GRINDSTONE,
//                            Component.translatable("bucketem.advancements.adventure.xp_is_fuel.title"),
//                            Component.translatable("bucketem.advancements.adventure.xp_is_fuel.description"),
//                            null,
//                            AdvancementType.TASK,
//                            true,
//                            true,
//                            false
//                    )
//                    .addCriterion("disenchanted", )
//                    .save(consumer, "adventure/xp_is_fuel");
        }
    }
}
