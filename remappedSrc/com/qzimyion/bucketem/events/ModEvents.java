package com.qzimyion.bucketem.events;

import com.qzimyion.bucketem.Bucketem;
import com.qzimyion.bucketem.ModTags;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.entity.passive.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResult;
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

import static com.qzimyion.bucketem.items.ModItems.*;
import static net.minecraft.world.item.Items.*;

@SuppressWarnings("deprecation")
public class ModEvents {

    public static void registerEvents() {

        UseEntityCallback.EVENT.register(((player, world, hand, entity, hitResult) -> {
            ItemStack itemStack = player.getItemInHand(hand);

            //Frog buckets
            if (itemStack.getItem() == WATER_BUCKET && entity.isAlive() && entity instanceof Frog frog){
                player.playSound(SoundEvents.BUCKET_FILL_FISH, 1.0f, 1.0f);
                ItemStack bucket;
                if (frog.getVariant()== FrogVariant.TEMPERATE){
                    bucket = new ItemStack(TEMPERATE_FROG_BUCKET);
                } else if (frog.getVariant()== FrogVariant.WARM) {
                    bucket = new ItemStack(TROPICAL_FROG_BUCKET);
                } else if (frog.getVariant()== FrogVariant.COLD) {
                    bucket = new ItemStack(TUNDRA_FROG_BUCKET);
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
                    bucket = new ItemStack(DRY_TEMPERATE_FROG_BUCKET);
                } else if (frog.getVariant()== FrogVariant.WARM) {
                    bucket = new ItemStack(DRY_TROPICAL_FROG_BUCKET);
                } else if (frog.getVariant()== FrogVariant.COLD) {
                    bucket = new ItemStack(DRY_TUNDRA_FROG_BUCKET);
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
                ItemStack bottle = new ItemStack(SLIME_BOTTLE);
                if (!player.isCreative()) itemStack.shrink(1);
                int i = Mth.clamp(slime.getSize(), 2, 127);
                if (slime.getType() == EntityType.SLIME && slime.getSize() == 1) {
                    player.playSound(SoundEvents.BOTTLE_FILL_DRAGONBREATH, 1.0f, 1.0f);
                    Bucketable.saveDefaultDataToBucketTag(slime, bottle);
                    ItemStack itemstack2 = ItemUtils.createFilledResult(itemStack, player, bottle, false);
                    player.setItemInHand(hand, itemstack2);
                    entity.discard();
                } else {
                    if (slime.getType() == EntityType.SLIME && (slime.getSize() == i)){
                        return InteractionResult.FAIL;
                    }
                }
                if (slime.getType() == EntityType.MAGMA_CUBE && slime.getSize() == 1) {
                    player.playSound(SoundEvents.BOTTLE_FILL_DRAGONBREATH, 1.0f, 1.0f);
                    bottle = new ItemStack(MAGMA_CUBE_BOTTLE);
                    Bucketable.saveDefaultDataToBucketTag(slime, bottle);
                    ItemStack itemstack2 = ItemUtils.createFilledResult(itemStack, player, bottle, false);
                    player.setItemInHand(hand, itemstack2);
                    entity.discard();
                } else {
                    if (slime.getType() == EntityType.MAGMA_CUBE && (slime.getSize() == i)){
                        return InteractionResult.FAIL;
                    }
                }
                return InteractionResult.SUCCESS;
            }
            //Bee
            if (itemStack.getItem() == GLASS_BOTTLE && entity.isAlive() && entity instanceof Bee bee){
                player.playSound(SoundEvents.BOTTLE_FILL_DRAGONBREATH, 1.0f, 1.0f);
                Item bottle = BEE_BOTTLE;
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
                if (entity instanceof LivingEntity livingEntity && !livingEntity.isBaby() && (itemStack.getItem() == GOLDEN_MILK_BUCKET || itemStack.getItem() == GOLDEN_BUCKET)) {
                    CompoundTag tag = itemStack.getOrCreateTag();
                    ItemStack milkBucket = ItemUtils.createFilledResult(itemStack.copy(), player, GOLDEN_MILK_BUCKET.getDefaultInstance());
                    boolean fullBucket = false;
                    if (itemStack.getItem() == GOLDEN_MILK_BUCKET) {
                        fullBucket = tag.getInt("FluidLevel") >= 2;
                        if (!fullBucket && !player.isCreative()) {
                            milkBucket.getOrCreateTag().putInt("FluidLevel", tag.getInt("FluidLevel") + 1);
                        }
                    }
                    if (!fullBucket) {
                        player.playSound(entity instanceof Goat goat ? goat.isScreamingGoat() ? SoundEvents.GOAT_SCREAMING_MILK : SoundEvents.GOAT_MILK : SoundEvents.COW_MILK, 1.0F, 1.0F);
                        entity.gameEvent(GameEvent.ENTITY_INTERACT);
                        player.setItemInHand(hand, milkBucket);
                        return InteractionResultHolder.success(world.isClientSide()).getResult();
                    }
                }
            }

            //Allay
            if (itemStack.getItem() == BOOK && player.isShiftKeyDown() && entity.isAlive() && entity instanceof Allay allay){
                player.playSound(SoundEvents.ENCHANTMENT_TABLE_USE, 1.0f, 1.0f);
                ItemStack newStack = ALLAY_POSSESSED_BOOK.getDefaultInstance();
                Bucketable.saveDefaultDataToBucketTag(allay, newStack);
                ItemStack itemstack2 = ItemUtils.createFilledResult(itemStack ,player, newStack, false);
                player.setItemInHand(hand, itemstack2);
                entity.discard();
                return InteractionResult.SUCCESS;

            }

            //Stuff

            //Golden Entity buckets
            //Puffer
//            if (itemStack.getItem() == GOLDEN_WATER_BUCKET && entity.isAlive() && entity instanceof PufferfishEntity pufferfish){
//                if (itemStack.getItem() instanceof GoldenEntityBucketItem goldBItem && goldBItem.hasNoEntities()){
//                    pufferfish.playSound(SoundEvents.ITEM_BUCKET_EMPTY_FISH, 1, 1);
//                    ItemStack itemStack2 = new ItemStack(GOLDEN_PUFFERFISH_BUCKET);
//                    pufferfish.copyDataToStack(itemStack2);
//                    ItemStack itemStack3 = ItemUsage.exchangeStack(itemStack, player, itemStack2, false);
//                    player.setStackInHand(hand, itemStack3);
//                    pufferfish.getWorld();
//                    if (!world.isClient) {
//                        Criteria.FILLED_BUCKET.trigger((ServerPlayerEntity)player, itemStack2);
//                    }
//                    entity.discard();
//                    return ActionResult.SUCCESS;
//                } else {
//                    return ActionResult.valueOf(String.valueOf(Optional.empty()));
//                }
//            }

            return InteractionResult.PASS;
        }));

        Bucketem.LOGGER.info("Registering mod Events");
    }
}
