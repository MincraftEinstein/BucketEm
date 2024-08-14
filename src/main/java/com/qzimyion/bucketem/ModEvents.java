package com.qzimyion.bucketem;

import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.Bucketable;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.entity.passive.*;
import net.minecraft.item.*;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.MathHelper;

import static com.qzimyion.bucketem.items.ModItems.*;
import static net.minecraft.item.Items.*;

@SuppressWarnings("deprecation, OptionalGetWithoutIsPresent")
public class ModEvents {

    public static void registerEvents() {

        UseEntityCallback.EVENT.register(((player, world, hand, entity, hitResult) -> {
            ItemStack heldItem = player.getStackInHand(hand);

            //Frog buckets
            if (heldItem.getItem() == WATER_BUCKET && entity.isAlive() && entity instanceof FrogEntity frog){
                player.playSound(SoundEvents.ITEM_BUCKET_FILL_FISH, 1.0f, 1.0f);
                ItemStack bucket;
                RegistryEntry<FrogVariant> temperateVariant = world.getRegistryManager().get(RegistryKeys.FROG_VARIANT).getEntry(FrogVariant.TEMPERATE).get();
                RegistryEntry<FrogVariant> tropicalVariant = world.getRegistryManager().get(RegistryKeys.FROG_VARIANT).getEntry(FrogVariant.WARM).get();
                RegistryEntry<FrogVariant> tundraVariant = world.getRegistryManager().get(RegistryKeys.FROG_VARIANT).getEntry(FrogVariant.COLD).get();
                if (frog.getVariant()==temperateVariant){
                    bucket = new ItemStack(TEMPERATE_FROG_BUCKET);
                    Bucketable.copyDataToStack(frog, bucket);
                    ItemStack itemstack = ItemUsage.exchangeStack(heldItem ,player, bucket, false);
                    player.setStackInHand(hand, itemstack);
                    entity.discard();
                }
                if (frog.getVariant()==tundraVariant){
                    bucket = new ItemStack(TUNDRA_FROG_BUCKET);
                    Bucketable.copyDataToStack(frog, bucket);
                    ItemStack itemstack = ItemUsage.exchangeStack(heldItem ,player, bucket, false);
                    player.setStackInHand(hand, itemstack);
                    entity.discard();
                }
                if (frog.getVariant()==tropicalVariant){
                    bucket = new ItemStack(TROPICAL_FROG_BUCKET);
                    Bucketable.copyDataToStack(frog, bucket);
                    ItemStack itemstack = ItemUsage.exchangeStack(heldItem ,player, bucket, false);
                    player.setStackInHand(hand, itemstack);
                    entity.discard();
                }
            }

            //Axolotl Buckets
            if (heldItem.getItem() == WATER_BUCKET && entity.isAlive() && entity instanceof AxolotlEntity axolotlEntity){
                player.playSound(SoundEvents.ITEM_BUCKET_FILL_FISH, 1.0f, 1.0f);
                ItemStack bucket;
                if (axolotlEntity.getVariant()==AxolotlEntity.Variant.WILD){
                    bucket = new ItemStack(BROWN_AXOLOTL_BUCKET);
                    NbtComponent.set(DataComponentTypes.BUCKET_ENTITY_DATA, bucket, nbt -> nbt.putInt("Age", axolotlEntity.getBreedingAge()));
                    Bucketable.copyDataToStack(axolotlEntity, bucket);
                    ItemStack itemstack = ItemUsage.exchangeStack(heldItem ,player, bucket, false);
                    player.setStackInHand(hand, itemstack);
                    entity.discard();
                }
                if (axolotlEntity.getVariant()==AxolotlEntity.Variant.CYAN){
                    bucket = new ItemStack(CYAN_AXOLOTL_BUCKET);
                    NbtComponent.set(DataComponentTypes.BUCKET_ENTITY_DATA, bucket, nbt -> nbt.putInt("Age", axolotlEntity.getBreedingAge()));
                    Bucketable.copyDataToStack(axolotlEntity, bucket);
                    ItemStack itemstack = ItemUsage.exchangeStack(heldItem ,player, bucket, false);
                    player.setStackInHand(hand, itemstack);
                    entity.discard();
                }
                if (axolotlEntity.getVariant()==AxolotlEntity.Variant.BLUE){
                    bucket = new ItemStack(BLUE_AXOLOTL_BUCKET);
                    NbtComponent.set(DataComponentTypes.BUCKET_ENTITY_DATA, bucket, nbt -> nbt.putInt("Age", axolotlEntity.getBreedingAge()));
                    Bucketable.copyDataToStack(axolotlEntity, bucket);
                    ItemStack itemstack = ItemUsage.exchangeStack(heldItem ,player, bucket, false);
                    player.setStackInHand(hand, itemstack);
                    entity.discard();
                }
                if (axolotlEntity.getVariant()==AxolotlEntity.Variant.GOLD){
                    bucket = new ItemStack(GOLD_AXOLOTL_BUCKET);
                    NbtComponent.set(DataComponentTypes.BUCKET_ENTITY_DATA, bucket, nbt -> nbt.putInt("Age", axolotlEntity.getBreedingAge()));
                    Bucketable.copyDataToStack(axolotlEntity, bucket);
                    ItemStack itemstack = ItemUsage.exchangeStack(heldItem ,player, bucket, false);
                    player.setStackInHand(hand, itemstack);
                    entity.discard();
                }
            }

            //Dry variant
            if (heldItem.getItem() == BUCKET && entity.isAlive() && entity instanceof FrogEntity frog){
                if (!player.isCreative()) heldItem.decrement(1);
                player.playSound(SoundEvents.ITEM_BUCKET_FILL_FISH, 1.0f, 1.0f);
                ItemStack bucket;
                RegistryEntry<FrogVariant> temperateVariant = world.getRegistryManager().get(RegistryKeys.FROG_VARIANT).getEntry(FrogVariant.TEMPERATE).get();
                RegistryEntry<FrogVariant> tropicalVariant = world.getRegistryManager().get(RegistryKeys.FROG_VARIANT).getEntry(FrogVariant.WARM).get();
                RegistryEntry<FrogVariant> tundraVariant = world.getRegistryManager().get(RegistryKeys.FROG_VARIANT).getEntry(FrogVariant.COLD).get();
                if (frog.getVariant()==temperateVariant){
                    bucket = new ItemStack(DRY_TEMPERATE_FROG_BUCKET);
                    Bucketable.copyDataToStack(frog, bucket);
                    ItemStack itemstack = ItemUsage.exchangeStack(heldItem ,player, bucket, false);
                    player.setStackInHand(hand, itemstack);
                    entity.discard();
                }
                if (frog.getVariant()==tundraVariant){
                    bucket = new ItemStack(DRY_TUNDRA_FROG_BUCKET);
                    Bucketable.copyDataToStack(frog, bucket);
                    ItemStack itemstack = ItemUsage.exchangeStack(heldItem ,player, bucket, false);
                    player.setStackInHand(hand, itemstack);
                    entity.discard();
                }
                if (frog.getVariant()==tropicalVariant){
                    bucket = new ItemStack(DRY_TROPICAL_FROG_BUCKET);
                    Bucketable.copyDataToStack(frog, bucket);
                    ItemStack itemstack = ItemUsage.exchangeStack(heldItem ,player, bucket, false);
                    player.setStackInHand(hand, itemstack);
                    entity.discard();
                }
            }

            //Slimes
            if (heldItem.getItem() == GLASS_BOTTLE && entity.isAlive() && entity instanceof SlimeEntity slime) {
                ItemStack bottle = new ItemStack(SLIME_BOTTLE);
                if (!player.isCreative()) heldItem.decrement(1);
                int i = MathHelper.clamp(slime.getSize(), 2, 127);
                if (slime.getType() == EntityType.SLIME && slime.getSize() == 1) {
                    player.playSound(SoundEvents.ITEM_BOTTLE_FILL_DRAGONBREATH, 1.0f, 1.0f);
                    Bucketable.copyDataToStack(slime, bottle);
                    ItemStack itemstack2 = ItemUsage.exchangeStack(heldItem, player, bottle, false);
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
                    ItemStack itemstack2 = ItemUsage.exchangeStack(heldItem, player, bottle, false);
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
            if (heldItem.getItem() == GLASS_BOTTLE && entity.isAlive() && entity instanceof BeeEntity bee){
                player.playSound(SoundEvents.ITEM_BOTTLE_FILL_DRAGONBREATH, 1.0f, 1.0f);
                if (!player.isCreative()) heldItem.decrement(1);
                ItemStack bottleItem = new ItemStack(BEE_BOTTLE);
                NbtComponent.set(DataComponentTypes.BUCKET_ENTITY_DATA, bottleItem, nbt ->
                {
                    nbt.putBoolean("HasNectar", bee.hasNectar());
                    nbt.putBoolean("HasStung", bee.hasStung());
                    nbt.putInt("Anger", bee.getAngerTime());
                    nbt.putInt("Age", bee.getBreedingAge());
                    nbt.putFloat("Health", bee.getHealth());
                    nbt.putUuid("AngryAt", bee.getAngryAt());
                });
                heldItem.decrement(1);
                player.incrementStat(Stats.USED.getOrCreateStat(heldItem.getItem()));
                entity.discard();
                Bucketable.copyDataToStack(bee, bottleItem);
                ItemStack itemstack2 = ItemUsage.exchangeStack(heldItem ,player, bottleItem, false);
                player.setStackInHand(hand, itemstack2);
                return ActionResult.SUCCESS;
            }

            //Allay
            //RegistryEntry<Enchantment> cursed = world.getRegistryManager().get(RegistryKeys.ENCHANTMENT).getEntry(Enchantments.BINDING_CURSE).get();
            if (heldItem.getItem() == BOOK && player.isSneaking() && entity.isAlive() && entity instanceof AllayEntity allay){
                player.playSound(SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, 1.0f, 1.0f);
                if (!player.isCreative()) heldItem.decrement(1);
                ItemStack newStack = ALLAY_POSSESSED_BOOK.getDefaultStack();
                NbtComponent.set(DataComponentTypes.BUCKET_ENTITY_DATA, newStack, nbt ->
                {
                    allay.writeInventory(nbt, allay.getRegistryManager());
                    allay.readInventory(nbt, allay.getRegistryManager());
                });
                Bucketable.copyDataToStack(allay, newStack);
                ItemStack itemstack2 = ItemUsage.exchangeStack(heldItem ,player, newStack, false);
                player.setStackInHand(hand, itemstack2);
                entity.discard();
                return ActionResult.SUCCESS;
            }
            return ActionResult.PASS;
        }));

        Bucketem.LOGGER.info("Registering mod Events");
    }
}
