package com.qzimyion.bucketem.items.NewItems.GoldBuckets;

import com.google.common.collect.ImmutableList;
import com.qzimyion.bucketem.ModTags;
import com.qzimyion.bucketem.items.ModItems;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.passive.GoatEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import static com.qzimyion.bucketem.items.ModItems.*;

public class GoldenMilkBucket extends Item {

    public GoldenMilkBucket(Settings settings) {
        super(settings);
    }


//    @Override
//    public ActionResult useOnEntity(ItemStack itemStack, PlayerEntity player, LivingEntity entity, Hand hand) {
//        var world = player.getWorld();
//        if (entity.getType().isIn(ModTags.EntityTypeTags.MILKABLE_ENTITY)) {
//            if (!entity.isBaby() && (itemStack.getItem() == GOLDEN_MILK_BUCKET || itemStack.getItem() == GOLDEN_BUCKET)) {
//                NbtCompound tag = itemStack.getOrCreateNbt();
//                ItemStack milkBucket = ItemUsage.exchangeStack(itemStack.copy(), player, GOLDEN_MILK_BUCKET.getDefaultStack());
//                boolean fullBucket = false;
//                if (itemStack.getItem() == GOLDEN_MILK_BUCKET) {
//                    fullBucket = tag.getInt("FluidLevel") >= 2;
//                    if (!fullBucket && !player.isCreative()) {
//                        milkBucket.getOrCreateNbt().putInt("FluidLevel", tag.getInt("FluidLevel") + 1);
//                    }
//                }
//                if (!fullBucket) {
//                    player.playSound(entity instanceof GoatEntity goat ? goat.isScreaming() ? SoundEvents.ENTITY_GOAT_SCREAMING_MILK : SoundEvents.ENTITY_GOAT_MILK : SoundEvents.ENTITY_COW_MILK, 1.0F, 1.0F);
//                    entity.emitGameEvent(GameEvent.ENTITY_INTERACT);
//                    player.setStackInHand(hand, milkBucket);
//                    return TypedActionResult.success(world.isClient()).getResult();
//                }
//            }
//        }
//        return TypedActionResult.pass(world.isClient()).getResult();
//    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (!world.isClient) {
            ImmutableList<StatusEffectInstance> effects = ImmutableList.copyOf(user.getStatusEffects());
            for (int i = 0; i < effects.size(); ++i) {
                user.removeStatusEffect(effects.get(i).getEffectType());
            }
        }
        int level = stack.getOrCreateNbt().getInt("FluidLevel");
        if (user instanceof ServerPlayerEntity player) {
            Criteria.CONSUME_ITEM.trigger(player, stack);
            player.incrementStat(Stats.USED.getOrCreateStat(this));
        }
        if (user instanceof PlayerEntity && !((PlayerEntity) user).getAbilities().creativeMode) {
            if (level > 0) stack.getOrCreateNbt().putInt("FluidLevel", level -1);
            else stack.decrement(1);
        }
        return stack.isEmpty() ? GoldenBucketItem.getEmptyBucket() : stack;
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 32;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.DRINK;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        return ItemUsage.consumeHeldItem(world, user, hand);
    }

    @Override
    public ItemStack getRecipeRemainder(ItemStack stack) {
        int level = stack.getOrCreateNbt().getInt("FluidLevel");
        if (level > 0){
            ItemStack newStack = new ItemStack(GOLDEN_MILK_BUCKET);
            newStack.getOrCreateNbt().putInt("FluidLevel", level - 1);
            return newStack;
        }
        return GoldenBucketItem.getEmptyBucket();
    }
}
