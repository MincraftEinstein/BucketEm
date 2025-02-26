package com.qzimyion.bucketem.core.registry;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.animal.FrogVariant;
import net.minecraft.world.entity.animal.allay.Allay;
import net.minecraft.world.entity.animal.frog.Frog;
import net.minecraft.world.entity.animal.goat.Goat;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.level.gameevent.GameEvent;

import static com.qzimyion.bucketem.core.registry.ModItems.*;
import static net.minecraft.world.item.Items.*;


@SuppressWarnings("deprecation")
public class ModEvents {

    public static InteractionResult EntityEvents(Player player, Level level, InteractionHand hand, Entity entity, @Nullable EntityHitResult entityHitResult) {
        ItemStack itemStack = player.getItemInHand(hand);

        //Frog buckets
        if (itemStack.getItem() == WATER_BUCKET && entity.isAlive() && entity instanceof Frog frog){
            player.playSound(SoundEvents.BUCKET_FILL_FISH, 1.0f, 1.0f);
            ItemStack bucket;
            if (frog.getVariant()== FrogVariant.TEMPERATE){
                bucket = new ItemStack(TEMPERATE_FROG_BUCKET.get());
            } else if (frog.getVariant()== FrogVariant.WARM) {
                bucket = new ItemStack(TROPICAL_FROG_BUCKET.get());
            } else if (frog.getVariant()== FrogVariant.COLD) {
                bucket = new ItemStack(TUNDRA_FROG_BUCKET.get());
            } else {
                return InteractionResult.SUCCESS;
            }
            Bucketable.saveDefaultDataToBucketTag(frog, bucket);
            ItemStack itemstack2 = ItemUtils.createFilledResult(itemStack ,player, bucket, false);
            player.setItemInHand(hand, itemstack2);
            entity.discard();
        }
        //Dry variant
        if (itemStack.getItem() == BUCKET && entity.isAlive() && entity instanceof Frog frog){
            player.playSound(SoundEvents.BUCKET_FILL_FISH, 1.0f, 1.0f);
            ItemStack bucket;
            if (frog.getVariant()== FrogVariant.TEMPERATE){
                bucket = new ItemStack(DRY_TEMPERATE_FROG_BUCKET.get());
            } else if (frog.getVariant()== FrogVariant.WARM) {
                bucket = new ItemStack(DRY_TROPICAL_FROG_BUCKET.get());
            } else if (frog.getVariant()== FrogVariant.COLD) {
                bucket = new ItemStack(DRY_TUNDRA_FROG_BUCKET.get());
            } else {
                return InteractionResult.SUCCESS;
            }
            Bucketable.saveDefaultDataToBucketTag(frog, bucket);
            ItemStack itemstack2 = ItemUtils.createFilledResult(itemStack ,player, bucket, false);
            player.setItemInHand(hand, itemstack2);
            entity.discard();
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
                CompoundTag nbt = bottleItem.getOrCreateTag();
                nbt.putBoolean("HasNectar", bee.hasNectar());
                nbt.putBoolean("HasStung", bee.hasStung());
                nbt.putInt("Anger", bee.getRemainingPersistentAngerTime());
                nbt.putInt("Age", bee.getAge());
                nbt.putFloat("Health", bee.getHealth());
                if (bee.getPersistentAngerTarget() != null) nbt.putUUID("AngryAt", bee.getPersistentAngerTarget());

                itemStack.shrink(1);
                player.awardStat(Stats.ITEM_USED.get(itemStack.getItem()));
                entity.discard();
                Bucketable.saveDefaultDataToBucketTag(bee, bottleItem);
                ItemStack itemstack2 = ItemUtils.createFilledResult(itemStack ,player, bottleItem, false);
                player.setItemInHand(hand, itemstack2);
                return InteractionResult.SUCCESS;
            }
        }

        //Milking
        if (entity.getType().is(ModTags.EntityTypeTagsForMod.MILKABLE_ENTITY)) {
            if (entity instanceof LivingEntity livingEntity && !livingEntity.isBaby() && (itemStack.getItem() == GOLDEN_MILK_BUCKET.get() || itemStack.getItem() == GOLDEN_BUCKET.get())) {
                CompoundTag tag = itemStack.getOrCreateTag();
                ItemStack milkBucket = ItemUtils.createFilledResult(itemStack.copy(), player, GOLDEN_MILK_BUCKET.get().getDefaultInstance());
                boolean fullBucket = false;
                if (itemStack.getItem() == GOLDEN_MILK_BUCKET.get()) {
                    fullBucket = tag.getInt("FluidLevel") >= 2;
                    if (!fullBucket && !player.isCreative()) {
                        milkBucket.getOrCreateTag().putInt("FluidLevel", tag.getInt("FluidLevel") + 1);
                    }
                }
                if (!fullBucket) {
                    player.playSound(entity instanceof Goat goat ? goat.isScreamingGoat() ? SoundEvents.GOAT_SCREAMING_MILK : SoundEvents.GOAT_MILK : SoundEvents.COW_MILK, 1.0F, 1.0F);
                    entity.gameEvent(GameEvent.ENTITY_INTERACT);
                    player.setItemInHand(hand, milkBucket);
                    return InteractionResultHolder.success(level.isClientSide()).getResult();
                }
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
