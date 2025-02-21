package com.qzimyion.bucketem.items.NewItems.GoldBuckets;

import com.google.common.collect.ImmutableList;
import com.qzimyion.bucketem.ModTags;
import com.qzimyion.bucketem.items.ModItems;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

import static com.qzimyion.bucketem.items.ModItems.*;

public class GoldenMilkBucket extends Item {

    public GoldenMilkBucket(Properties settings) {
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
    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity user) {
        if (!world.isClientSide) {
            ImmutableList<MobEffectInstance> effects = ImmutableList.copyOf(user.getActiveEffects());
            for (int i = 0; i < effects.size(); ++i) {
                user.removeEffect(effects.get(i).getEffect());
            }
        }
        int level = stack.getOrCreateTag().getInt("FluidLevel");
        if (user instanceof ServerPlayer player) {
            CriteriaTriggers.CONSUME_ITEM.trigger(player, stack);
            player.awardStat(Stats.ITEM_USED.get(this));
        }
        if (user instanceof Player && !((Player) user).getAbilities().instabuild) {
            if (level > 0) stack.getOrCreateTag().putInt("FluidLevel", level -1);
            else stack.shrink(1);
        }
        return stack.isEmpty() ? GoldenBucketItem.getEmptyBucket() : stack;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 32;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.DRINK;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        return ItemUtils.startUsingInstantly(world, user, hand);
    }

    @Override
    public ItemStack getRecipeRemainder(ItemStack stack) {
        int level = stack.getOrCreateTag().getInt("FluidLevel");
        if (level > 0){
            ItemStack newStack = new ItemStack(GOLDEN_MILK_BUCKET);
            newStack.getOrCreateTag().putInt("FluidLevel", level - 1);
            return newStack;
        }
        return GoldenBucketItem.getEmptyBucket();
    }
}
