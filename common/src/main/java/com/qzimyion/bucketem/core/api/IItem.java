package com.qzimyion.bucketem.core.api;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public interface IItem {

    //Ported from fabric
    default boolean allowNbtUpdateAnimation(Player player, InteractionHand hand, ItemStack oldStack, ItemStack newStack) {
        return true;
    }

    default ItemStack getRecipeRemainder(ItemStack stack) {
        return ((Item) this).hasCraftingRemainingItem() ? ((Item) this).getCraftingRemainingItem().getDefaultInstance() : ItemStack.EMPTY;
    }
}
