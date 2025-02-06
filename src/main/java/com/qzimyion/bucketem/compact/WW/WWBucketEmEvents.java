package com.qzimyion.bucketem.compact.WW;

import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.frozenblock.wilderwild.entity.Jellyfish;
import net.frozenblock.wilderwild.entity.variant.JellyfishVariant;
import net.frozenblock.wilderwild.registry.WWItems;
import net.minecraft.entity.Bucketable;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;

import static net.minecraft.item.Items.WATER_BUCKET;

@SuppressWarnings("deprecation")
public class WWBucketEmEvents {

    public static void registerWWBucketEMEvents(){
        UseEntityCallback.EVENT.register(((player, world, hand, entity, hitResult) -> {
            ItemStack itemStack = player.getStackInHand(hand);
            //Jellyfish
            if (itemStack.getItem() == WATER_BUCKET && entity.isAlive() && entity instanceof Jellyfish jellyfish){
                player.playSound(SoundEvents.ITEM_BUCKET_FILL_FISH, 1.0f, 1.0f);
                ItemStack bucket = WWItems.JELLYFISH_BUCKET.getDefaultStack();
                if (bucket != null){
                    ItemStack bucketItem = new ItemStack(bucket.getItem());
                    NbtCompound nbt = bucketItem.getOrCreateNbt();
                    nbt.putInt("age", jellyfish.getAge());

                    if (jellyfish.getVariant()== JellyfishVariant.BLUE){
                        bucket = new ItemStack(WWBucketEmItems.BLUE_JELLYFISH_BUCKET);
                    } else if (jellyfish.getVariant()== JellyfishVariant.LIME) {
                        bucket = new ItemStack(WWBucketEmItems.LIME_JELLYFISH_BUCKET);
                    } else if (jellyfish.getVariant()== JellyfishVariant.RED) {
                        bucket = new ItemStack(WWBucketEmItems.RED_JELLYFISH_BUCKET);
                    } else if (jellyfish.getVariant()== JellyfishVariant.YELLOW) {
                        bucket = new ItemStack(WWBucketEmItems.YELLOW_JELLYFISH_BUCKET);
                    } else if (jellyfish.getVariant()== JellyfishVariant.PEARLESCENT_BLUE) {
                        bucket = new ItemStack(WWBucketEmItems.PEARLESCENT_BLUE_JELLYFISH_BUCKET);
                    } else if (jellyfish.getVariant()== JellyfishVariant.PEARLESCENT_PURPLE) {
                        bucket = new ItemStack(WWBucketEmItems.PEARLESCENT_PURPLE_JELLYFISH_BUCKET);
                    } else if (jellyfish.getVariant()== JellyfishVariant.PINK) {
                        bucket = new ItemStack(WWItems.JELLYFISH_BUCKET);
                    } else {
                        return ActionResult.SUCCESS;
                    }
                    Bucketable.copyDataToStack(jellyfish, bucket);
                    ItemStack exchangeStack = ItemUsage.exchangeStack(itemStack ,player, bucket, false);
                    player.setStackInHand(hand, exchangeStack);
                    entity.discard();
                }
            }
            return ActionResult.PASS;
        }));
    }
}
