package com.qzimyion.bucketem.mixin.ItemMixins;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(StewItem.class)
public class StewItemMixin extends Item {

    public StewItemMixin(Settings settings) {
        super(settings);
    }

    @Unique
    private static ItemStack resetStewLevel(ItemStack stack) {
        stack.getOrCreateNbt().putInt("StewLevel", 0);
        return stack;
    }

    @Unique
    private static ItemStack getEmptyBowl() {
        return resetStewLevel(new ItemStack(Items.BOWL));
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        int level = stack.getOrCreateNbt().getInt("StewLevel");
        if (user instanceof ServerPlayerEntity player) {
            Criteria.CONSUME_ITEM.trigger(player, stack);
            player.incrementStat(Stats.USED.getOrCreateStat(this));
        }
        if (user instanceof PlayerEntity && !((PlayerEntity) user).getAbilities().creativeMode) {
            if (level > 0) stack.getOrCreateNbt().putInt("StewLevel", level -1);
            else stack.decrement(1);
        }
        return stack.isEmpty() ? getEmptyBowl() : stack;
    }
}
