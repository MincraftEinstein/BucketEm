package com.qzimyion.bucketem.mixin.BlockEntityMixins;

import com.qzimyion.bucketem.items.ModItems;
import com.qzimyion.bucketem.items.NewItems.GoldBuckets.GoldenBucketItem;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Recipe;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@SuppressWarnings("DiscouragedShift")
@Mixin(AbstractFurnaceBlockEntity.class)
public class AbstractFurnaceBlockEntityMixin {

    @Shadow
    protected DefaultedList<ItemStack> inventory;

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;decrement(I)V", shift = At.Shift.BEFORE), method = "craftRecipe")
    private static void craftRecipe(DynamicRegistryManager registryManager, Recipe<?> recipe, DefaultedList<ItemStack> slots, int count, CallbackInfoReturnable<Boolean> cir) {
        ItemStack stack = slots.get(1);
        if (slots.get(0).isOf(Blocks.WET_SPONGE.asItem()) && !stack.isEmpty()) {
            if (stack.isOf(ModItems.GOLDEN_BUCKET)) {
                slots.set(1, new ItemStack(ModItems.GOLDEN_WATER_BUCKET));
            } else if (stack.isOf(ModItems.GOLDEN_WATER_BUCKET) && GoldenBucketItem.canBeFilled(stack)) {
                slots.set(1, GoldenBucketItem.increaseFluidLevel(stack));
            }
        }
    }

    @Inject(at = @At("RETURN"), method = "canExtract", cancellable = true)
    private void canTakeItemThroughFace(int index, ItemStack stack, Direction direction, CallbackInfoReturnable<Boolean> cir) {
        if (direction == Direction.DOWN && index == 1 && stack.isOf(ModItems.GOLDEN_WATER_BUCKET) || stack.isOf(ModItems.GOLDEN_BUCKET)) {
            cir.setReturnValue(true);
        }
    }

    @Inject(at = @At("RETURN"), method = "isValid", cancellable = true)
    private void canPlaceItem(int index, ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        ItemStack existingStack = inventory.get(1);
        if (index == 1 && (stack.isOf(ModItems.GOLDEN_BUCKET) && !existingStack.isOf(ModItems.GOLDEN_BUCKET)) || (stack.isOf(ModItems.GOLDEN_WATER_BUCKET) && GoldenBucketItem.canBeFilled(stack))) {
            cir.setReturnValue(true);
        }
    }
}
