package com.qzimyion.bucketem.common.items.GoldBuckets;

import com.google.common.collect.ImmutableList;
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
import org.jetbrains.annotations.NotNull;

public class GoldenMilkBucket extends Item {

    public GoldenMilkBucket(Properties settings) {
        super(settings);
    }

    @Override
    public @NotNull ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity user) {
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

    

//    @Override
//    public ItemStack getRecipeRemainder(ItemStack stack) {
//        int level = stack.getOrCreateTag().getInt("FluidLevel");
//        if (level > 0){
//            ItemStack newStack = new ItemStack(GOLDEN_MILK_BUCKET);
//            newStack.getOrCreateTag().putInt("FluidLevel", level - 1);
//            return newStack;
//        }
//        return GoldenBucketItem.getEmptyBucket();
//    }
}
