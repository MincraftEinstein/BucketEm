package com.qzimyion.bucketem.mixin.ItemMixins;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SuspiciousStewItem;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.Objects;
import java.util.function.Consumer;

@Mixin(SuspiciousStewItem.class)
public abstract class SussyStewItemMixin extends Item {

    @Shadow
    private static void forEachEffect(ItemStack stew, Consumer<StatusEffectInstance> effectConsumer) {
    }

    public SussyStewItemMixin(Settings settings) {
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
        Objects.requireNonNull(user);
        forEachEffect(stack, user::addStatusEffect);
        return stack.isEmpty() ? getEmptyBowl() : stack;
    }
}
