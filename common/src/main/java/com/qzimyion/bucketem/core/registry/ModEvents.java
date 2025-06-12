package com.qzimyion.bucketem.core.registry;

import com.qzimyion.bucketem.util.BuckEmBucketable;
import dev.architectury.event.CompoundEventResult;
import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.InteractionEvent;
import dev.architectury.event.events.common.LootEvent;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.FrogVariant;
import net.minecraft.world.entity.animal.frog.Frog;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import static com.qzimyion.bucketem.core.registry.ModItems.*;
import static net.minecraft.world.item.Items.*;

public class ModEvents {

    public static void LootTableEvent() {
        InteractionEvent.RIGHT_CLICK_ITEM.register(((player, hand) -> {
            ItemStack itemStack = player.getItemInHand(hand);
            Level level = player.level();
            double x = player.getX() + player.getLookAngle().x * 4.5;
            double y = player.getEyeY() + player.getLookAngle().y * 4.5;
            double z = player.getZ() + player.getLookAngle().z * 4.5;

            BlockPos pos = new BlockPos((int) Math.floor(x), (int) Math.floor(y), (int) Math.floor(z));
            if (player.isInWater() && level.isInWorldBounds(pos) && level.getWorldBorder().isWithinBounds(pos)) {

            }
            return CompoundEventResult.pass();
        }));

        LootEvent.MODIFY_LOOT_TABLE.register(((lootDataManager, id, context, builtin) -> {
            LootPool.Builder pool = LootPool.lootPool();
            //==Bastions==//
            if (BuiltInLootTables.BASTION_BRIDGE.equals(id) || BuiltInLootTables.BASTION_OTHER.equals(id) || BuiltInLootTables.BASTION_HOGLIN_STABLE.equals(id)) {
                pool.add(LootItem.lootTableItem(STRIDER_BUCKET.get()).setWeight(12).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))));
            }
            //==OW==//
            //--Villagers
            if (BuiltInLootTables.VILLAGE_FISHER.equals(id)) {
                pool.add(LootItem.lootTableItem(SQUID_BUCKET.get()).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))));
                pool.add(LootItem.lootTableItem(COD_BUCKET).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))));
                pool.add(LootItem.lootTableItem(SALMON_BUCKET).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))));
                pool.add(LootItem.lootTableItem(PUFFERFISH_BUCKET).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))));
            }
            //--Mineshaft
            if (BuiltInLootTables.ABANDONED_MINESHAFT.equals(id)) {
                pool.add(LootItem.lootTableItem(GLOW_SQUID_BUCKET.get()).setWeight(20).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))));
            }
            context.addPool(pool);
        }));
    }

    public static void entityInteractionEvent() {
        InteractionEvent.INTERACT_ENTITY.register((player, entity, hand) -> {
            EntityType<?> type = entity.getType();

            if (BuckEmBucketable.MAP.containsKey(type)) {
                return handleBucketInteraction(player, entity, hand, BuckEmBucketable.MAP.get(type));
            }
            else if (entity instanceof Frog frog) {
                FrogVariant variant = frog.getVariant();
                if (variant == FrogVariant.TEMPERATE) {
                    return tryHandleOrElse(player, entity, hand,
                            new BuckEmBucketable.EntityBucketData(Items.WATER_BUCKET, ModItems.TEMPERATE_FROG_BUCKET, SoundEvents.BUCKET_FILL_FISH),
                            new BuckEmBucketable.EntityBucketData(Items.BUCKET, ModItems.DRY_TEMPERATE_FROG_BUCKET, SoundEvents.BUCKET_FILL_FISH)
                    );
                }
                else if (variant == FrogVariant.WARM) {
                    return tryHandleOrElse(player, entity, hand,
                            new BuckEmBucketable.EntityBucketData(Items.WATER_BUCKET, ModItems.TROPICAL_FROG_BUCKET, SoundEvents.BUCKET_FILL_FISH),
                            new BuckEmBucketable.EntityBucketData(Items.BUCKET, ModItems.DRY_TROPICAL_FROG_BUCKET, SoundEvents.BUCKET_FILL_FISH)
                    );
                }
                else if (variant == FrogVariant.COLD) {
                    return tryHandleOrElse(player, entity, hand,
                            new BuckEmBucketable.EntityBucketData(Items.WATER_BUCKET, ModItems.TUNDRA_FROG_BUCKET, SoundEvents.BUCKET_FILL_FISH),
                            new BuckEmBucketable.EntityBucketData(Items.BUCKET, ModItems.DRY_TUNDRA_FROG_BUCKET, SoundEvents.BUCKET_FILL_FISH)
                    );
                }
            }
            return EventResult.pass();
        });
    }

    private static EventResult tryHandleOrElse(Player player, Entity entity, InteractionHand hand, BuckEmBucketable.EntityBucketData bucketData, BuckEmBucketable.EntityBucketData otherBucketData) {
        EventResult result = handleBucketInteraction(player, entity, hand, bucketData);
        if (result == EventResult.pass()) {
            return handleBucketInteraction(player, entity, hand, otherBucketData);
        }
        return result;
    }

    private static EventResult handleBucketInteraction(Player player, Entity entity, InteractionHand hand, BuckEmBucketable.EntityBucketData bucketData) {
        if (entity instanceof BuckEmBucketable bucketable) {
            ItemStack stack = player.getItemInHand(hand);

            if (stack.is(bucketData.bucketItem()) && entity.isAlive()) {
                if (entity instanceof Slime slime && slime.getSize() > 1) {
                    return EventResult.pass();
                }

                ItemStack bucketedStack = bucketData.bucketedMobItem().get().getDefaultInstance();
                bucketable.saveToBucketTag(bucketedStack);
                player.setItemInHand(hand, ItemUtils.createFilledResult(stack, player, bucketedStack, false));
                entity.playSound(bucketData.pickupSound());

                if (!entity.level().isClientSide) {
                    CriteriaTriggers.FILLED_BUCKET.trigger((ServerPlayer) player, bucketedStack);
                }

                entity.discard();
                return EventResult.interruptTrue();
            }
        }
        return EventResult.pass();
    }
}
