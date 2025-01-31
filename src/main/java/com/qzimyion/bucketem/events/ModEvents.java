package com.qzimyion.bucketem.events;

import com.qzimyion.bucketem.Bucketem;
import com.qzimyion.bucketem.ModTags;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.entity.Bucketable;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.entity.passive.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.event.GameEvent;

import static com.qzimyion.bucketem.items.ModItems.*;
import static net.minecraft.item.Items.*;

@SuppressWarnings("deprecation")
public class ModEvents {

    public static void registerEvents() {

        UseEntityCallback.EVENT.register(((player, world, hand, entity, hitResult) -> {
            ItemStack itemStack = player.getStackInHand(hand);

            //Frog buckets
            if (itemStack.getItem() == WATER_BUCKET && entity.isAlive() && entity instanceof FrogEntity frog){
                player.playSound(SoundEvents.ITEM_BUCKET_FILL_FISH, 1.0f, 1.0f);
                ItemStack bucket;
                if (frog.getVariant()== FrogVariant.TEMPERATE){
                    bucket = new ItemStack(TEMPERATE_FROG_BUCKET);
                } else if (frog.getVariant()== FrogVariant.WARM) {
                    bucket = new ItemStack(TROPICAL_FROG_BUCKET);
                } else if (frog.getVariant()== FrogVariant.COLD) {
                    bucket = new ItemStack(TUNDRA_FROG_BUCKET);
                } else {
                    return ActionResult.SUCCESS;
                }
                Bucketable.copyDataToStack(frog, bucket);
                ItemStack itemstack2 = ItemUsage.exchangeStack(itemStack ,player, bucket, false);
                player.setStackInHand(hand, itemstack2);

                entity.discard();
            }
            //Dry variant
            if (itemStack.getItem() == BUCKET && entity.isAlive() && entity instanceof FrogEntity frog){
                player.playSound(SoundEvents.ITEM_BUCKET_FILL_FISH, 1.0f, 1.0f);
                ItemStack bucket;
                if (frog.getVariant()== FrogVariant.TEMPERATE){
                    bucket = new ItemStack(DRY_TEMPERATE_FROG_BUCKET);
                } else if (frog.getVariant()== FrogVariant.WARM) {
                    bucket = new ItemStack(DRY_TROPICAL_FROG_BUCKET);
                } else if (frog.getVariant()== FrogVariant.COLD) {
                    bucket = new ItemStack(DRY_TUNDRA_FROG_BUCKET);
                } else {
                    return ActionResult.SUCCESS;
                }
                Bucketable.copyDataToStack(frog, bucket);
                ItemStack itemstack2 = ItemUsage.exchangeStack(itemStack ,player, bucket, false);
                player.setStackInHand(hand, itemstack2);
                entity.discard();
            }
            //Slimes
            if (itemStack.getItem() == GLASS_BOTTLE && entity.isAlive() && entity instanceof SlimeEntity slime) {
                ItemStack bottle = new ItemStack(SLIME_BOTTLE);
                if (!player.isCreative()) itemStack.decrement(1);
                int i = MathHelper.clamp(slime.getSize(), 2, 127);
                if (slime.getType() == EntityType.SLIME && slime.getSize() == 1) {
                    player.playSound(SoundEvents.ITEM_BOTTLE_FILL_DRAGONBREATH, 1.0f, 1.0f);
                    Bucketable.copyDataToStack(slime, bottle);
                    ItemStack itemstack2 = ItemUsage.exchangeStack(itemStack, player, bottle, false);
                    player.setStackInHand(hand, itemstack2);
                    entity.discard();
                } else {
                    if (slime.getType() == EntityType.SLIME && (slime.getSize() == i)){
                        return ActionResult.FAIL;
                    }
                }
                if (slime.getType() == EntityType.MAGMA_CUBE && slime.getSize() == 1) {
                    player.playSound(SoundEvents.ITEM_BOTTLE_FILL_DRAGONBREATH, 1.0f, 1.0f);
                    bottle = new ItemStack(MAGMA_CUBE_BOTTLE);
                    Bucketable.copyDataToStack(slime, bottle);
                    ItemStack itemstack2 = ItemUsage.exchangeStack(itemStack, player, bottle, false);
                    player.setStackInHand(hand, itemstack2);
                    entity.discard();
                } else {
                    if (slime.getType() == EntityType.MAGMA_CUBE && (slime.getSize() == i)){
                        return ActionResult.FAIL;
                    }
                }
                return ActionResult.SUCCESS;
            }
            //Bee
            if (itemStack.getItem() == GLASS_BOTTLE && entity.isAlive() && entity instanceof BeeEntity bee){
                player.playSound(SoundEvents.ITEM_BOTTLE_FILL_DRAGONBREATH, 1.0f, 1.0f);
                Item bottle = BEE_BOTTLE;
                if (bottle != null)
                {
                    ItemStack bottleItem = new ItemStack(bottle);
                    NbtCompound nbt = bottleItem.getOrCreateNbt();
                    nbt.putBoolean("HasNectar", bee.hasNectar());
                    nbt.putBoolean("HasStung", bee.hasStung());
                    nbt.putInt("Anger", bee.getAngerTime());
                    nbt.putInt("Age", bee.getBreedingAge());
                    nbt.putFloat("Health", bee.getHealth());
                    if (bee.getAngryAt() != null) nbt.putUuid("AngryAt", bee.getAngryAt());

                    itemStack.decrement(1);
                    player.incrementStat(Stats.USED.getOrCreateStat(itemStack.getItem()));
                    entity.discard();
                    Bucketable.copyDataToStack(bee, bottleItem);
                    ItemStack itemstack2 = ItemUsage.exchangeStack(itemStack ,player, bottleItem, false);
                    player.setStackInHand(hand, itemstack2);
                    return ActionResult.SUCCESS;
                }
            }

            //Milking
            if (entity.getType().isIn(ModTags.EntityTypeTagsForMod.MILKABLE_ENTITY)) {
                if (entity instanceof LivingEntity livingEntity && !livingEntity.isBaby() && (itemStack.getItem() == GOLDEN_MILK_BUCKET || itemStack.getItem() == GOLDEN_BUCKET)) {
                    NbtCompound tag = itemStack.getOrCreateNbt();
                    ItemStack milkBucket = ItemUsage.exchangeStack(itemStack.copy(), player, GOLDEN_MILK_BUCKET.getDefaultStack());
                    boolean fullBucket = false;
                    if (itemStack.getItem() == GOLDEN_MILK_BUCKET) {
                        fullBucket = tag.getInt("FluidLevel") >= 2;
                        if (!fullBucket && !player.isCreative()) {
                            milkBucket.getOrCreateNbt().putInt("FluidLevel", tag.getInt("FluidLevel") + 1);
                        }
                    }
                    if (!fullBucket) {
                        player.playSound(entity instanceof GoatEntity goat ? goat.isScreaming() ? SoundEvents.ENTITY_GOAT_SCREAMING_MILK : SoundEvents.ENTITY_GOAT_MILK : SoundEvents.ENTITY_COW_MILK, 1.0F, 1.0F);
                        entity.emitGameEvent(GameEvent.ENTITY_INTERACT);
                        player.setStackInHand(hand, milkBucket);
                        return TypedActionResult.success(world.isClient()).getResult();
                    }
                }
            }

            //Allay
            if (itemStack.getItem() == BOOK && player.isSneaking() && entity.isAlive() && entity instanceof AllayEntity allay){
                player.playSound(SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, 1.0f, 1.0f);
                ItemStack newStack = ALLAY_POSSESSED_BOOK.getDefaultStack();
                Bucketable.copyDataToStack(allay, newStack);
                ItemStack itemstack2 = ItemUsage.exchangeStack(itemStack ,player, newStack, false);
                player.setStackInHand(hand, itemstack2);
                entity.discard();
                return ActionResult.SUCCESS;

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

            return ActionResult.PASS;
        }));

        Bucketem.LOGGER.info("Registering mod Events");
    }
}
