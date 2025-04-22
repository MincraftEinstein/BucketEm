package com.qzimyion.bucketem.core.registry;

import dev.architectury.event.events.common.LootEvent;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.Nullable;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.animal.FrogVariant;
import net.minecraft.world.entity.animal.allay.Allay;
import net.minecraft.world.entity.animal.frog.Frog;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;

import static com.qzimyion.bucketem.core.registry.ModItems.*;
import static net.minecraft.world.item.Items.*;


@SuppressWarnings({"deprecation"})
public class ModEvents {

    public static void LootTableEvent(){

        LootEvent.MODIFY_LOOT_TABLE.register(((lootDataManager, id, context) -> {
            LootPool.Builder pool = LootPool.lootPool();
            //==Bastions==//
            if (BuiltInLootTables.BASTION_BRIDGE.equals(lootDataManager) || BuiltInLootTables.BASTION_OTHER.equals(lootDataManager) || BuiltInLootTables.BASTION_HOGLIN_STABLE.equals(lootDataManager)){
                pool.add(LootItem.lootTableItem(STRIDER_BUCKET.get()).setWeight(12).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))));
            }
            //==OW==//
            //--Villagers
            if (BuiltInLootTables.VILLAGE_FISHER.equals(lootDataManager)){
                pool.add(LootItem.lootTableItem(SQUID_BUCKET.get()).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))));
                pool.add(LootItem.lootTableItem(COD_BUCKET).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))));
                pool.add(LootItem.lootTableItem(SALMON_BUCKET).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))));
                pool.add(LootItem.lootTableItem(PUFFERFISH_BUCKET).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))));
            }
            //--Mineshaft
            if (BuiltInLootTables.ABANDONED_MINESHAFT.equals(lootDataManager)){
                pool.add(LootItem.lootTableItem(GLOW_SQUID_BUCKET.get()).setWeight(20).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))));
            }
            id.addPool(pool);
        }));
    }

    public static InteractionResult EntityEvents(Player player, Level level, InteractionHand hand, Entity entity, @Nullable EntityHitResult entityHitResult) {
        ItemStack itemStack = player.getItemInHand(hand);
        //Frog buckets
        if (itemStack.getItem() == WATER_BUCKET && entity.isAlive() && entity instanceof Frog frog){
            player.playSound(SoundEvents.BUCKET_FILL_TADPOLE, 1.0f, 1.0f);
            Holder<FrogVariant> variantHolder = frog.getVariant();
            ItemStack bucket = variantHolder.is(FrogVariant.TEMPERATE) ? new ItemStack(TEMPERATE_FROG_BUCKET.get()) : variantHolder.is(FrogVariant.WARM) ? new ItemStack(TROPICAL_FROG_BUCKET.get()) : new ItemStack(TUNDRA_FROG_BUCKET.get());
            Bucketable.saveDefaultDataToBucketTag(frog, bucket);
            ItemStack itemstack2 = ItemUtils.createFilledResult(itemStack ,player, bucket, false);
            player.setItemInHand(hand, itemstack2);
            entity.discard();
            return InteractionResult.SUCCESS;
        }
        //Dry variant
        if (itemStack.getItem() == BUCKET && entity.isAlive() && entity instanceof Frog frog){
            player.playSound(SoundEvents.BUCKET_FILL_TADPOLE, 1.0f, 1.0f);
            Holder<FrogVariant> variantHolder = frog.getVariant();
            ItemStack bucket = variantHolder.is(FrogVariant.TEMPERATE) ? new ItemStack(TEMPERATE_FROG_BUCKET.get()) : variantHolder.is(FrogVariant.WARM) ? new ItemStack(TROPICAL_FROG_BUCKET.get()) : new ItemStack(TUNDRA_FROG_BUCKET.get());
            Bucketable.saveDefaultDataToBucketTag(frog, bucket);
            ItemStack itemstack2 = ItemUtils.createFilledResult(itemStack ,player, bucket, false);
            player.setItemInHand(hand, itemstack2);
            entity.discard();
            return InteractionResult.SUCCESS;
        }
        //Slimes
        if (itemStack.getItem() == GLASS_BOTTLE && entity.isAlive() && entity instanceof Slime slime) {
            if (slime.getSize() > 1) {
                return InteractionResult.FAIL;
            }
            ItemStack bottle;
            if (slime.getType() == EntityType.SLIME && slime.getSize() == 1) {
                player.playSound(SoundEvents.BOTTLE_FILL_DRAGONBREATH, 1.0f, 1.0f);
                bottle = new ItemStack(SLIME_BOTTLE.get());
            } else if (slime.getType() == EntityType.MAGMA_CUBE && slime.getSize() == 1) {
                player.playSound(SoundEvents.BOTTLE_FILL_DRAGONBREATH, 1.0f, 1.0f);
                bottle = new ItemStack(MAGMA_CUBE_BOTTLE.get());
            } else {
                return InteractionResult.FAIL;
            }
            if (!player.isCreative()) {
                itemStack.shrink(1);
            }
            Bucketable.saveDefaultDataToBucketTag(slime, bottle);
            if (!player.getInventory().add(bottle)) {
                player.drop(bottle, false);
            }
            entity.discard();
            return InteractionResult.SUCCESS;
        }
        //Bee
        if (itemStack.getItem() == GLASS_BOTTLE && entity.isAlive() && entity instanceof Bee bee){
            player.playSound(SoundEvents.BOTTLE_FILL_DRAGONBREATH, 1.0f, 1.0f);
            Item bottle = BEE_BOTTLE.get();
            if (bottle != null)
            {
                ItemStack bottleItem = new ItemStack(bottle);
                CustomData.update(DataComponents.BUCKET_ENTITY_DATA, itemStack, compoundTag -> {
                    compoundTag.putBoolean("HasNectar", bee.hasNectar());
                    compoundTag.putBoolean("HasStung", bee.hasStung());
                    compoundTag.putInt("Anger", bee.getRemainingPersistentAngerTime());
                    compoundTag.putInt("Age", bee.getAge());
                    compoundTag.putFloat("Health", bee.getHealth());
                    compoundTag.putUUID("AngryAt", bee.getPersistentAngerTarget());
                });
                itemStack.shrink(1);
                player.awardStat(Stats.ITEM_USED.get(itemStack.getItem()));
                entity.discard();
                Bucketable.saveDefaultDataToBucketTag(bee, bottleItem);
                ItemStack itemstack2 = ItemUtils.createFilledResult(itemStack ,player, bottleItem, false);
                player.setItemInHand(hand, itemstack2);
                return InteractionResult.SUCCESS;
            }
        }

        //Allay
        if (itemStack.getItem() == BOOK && player.isShiftKeyDown() && entity.isAlive() && entity instanceof Allay allay){
            player.playSound(SoundEvents.ENCHANTMENT_TABLE_USE, 1.0f, 1.0f);
            ItemStack newStack = ALLAY_POSSESSED_BOOK.get().getDefaultInstance();
            Bucketable.saveDefaultDataToBucketTag(allay, newStack);
            ItemStack itemstack2 = ItemUtils.createFilledResult(itemStack ,player, newStack, false);
            player.setItemInHand(hand, itemstack2);
            entity.discard();
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }
}
