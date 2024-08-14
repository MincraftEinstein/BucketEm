package com.qzimyion.bucketem.items.NewItems;

import com.google.common.collect.ImmutableList;
import com.qzimyion.bucketem.items.ModDataComponents;
import com.qzimyion.bucketem.items.ModItems;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

public class MilkGoldenBucket extends Item {

    public MilkGoldenBucket(Settings settings) {
        super(settings);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (!world.isClient) {
            ImmutableList<StatusEffectInstance> effects = ImmutableList.copyOf(user.getStatusEffects());
            for (int i = 0; i < effects.size(); ++i) {
                user.removeStatusEffect(effects.get(i).getEffectType());
            }
        }
        if (stack.contains(ModDataComponents.FLUID_LEVEL)){
            int level = stack.get(ModDataComponents.FLUID_LEVEL);
            if (user instanceof ServerPlayerEntity player) {
                Criteria.CONSUME_ITEM.trigger(player, stack);
                player.incrementStat(Stats.USED.getOrCreateStat(this));
            }
            if (user instanceof PlayerEntity && !((PlayerEntity) user).getAbilities().creativeMode) {
                if (level > 0) stack.set(ModDataComponents.FLUID_LEVEL, level -1);
                else stack.decrement(1);
            }
        }
        return stack.isEmpty() ? GoldenBucketItem.getEmptyBucket() : stack;
    }

    @Override
    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
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
        if (stack.contains(ModDataComponents.FLUID_LEVEL)){
            int level = stack.get(ModDataComponents.FLUID_LEVEL);
            if (level > 0){
                ItemStack newStack = new ItemStack(ModItems.GOLDEN_MILK_BUCKET);
                newStack.set(ModDataComponents.FLUID_LEVEL, level -1);
                return newStack;
            }
        }
        return GoldenBucketItem.getEmptyBucket();
    }
}
