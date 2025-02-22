package com.qzimyion.bucketem.core.mixin.BlockEntityMixins;


import com.qzimyion.bucketem.common.items.GoldBuckets.GoldenBucketItem;
import com.qzimyion.bucketem.core.registry.ModItems;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@SuppressWarnings("DiscouragedShift")
@Mixin(AbstractFurnaceBlockEntity.class)
public class AbstractFurnaceBlockEntityMixin {

    @Shadow protected NonNullList<ItemStack> items;

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;shrink(I)V", shift = At.Shift.BEFORE), method = "burn")
    private static void burn(RegistryAccess registryManager, Recipe<?> recipe, NonNullList<ItemStack> slots, int count, CallbackInfoReturnable<Boolean> cir) {
        ItemStack stack = slots.get(1);
        if (slots.get(0).is(Blocks.WET_SPONGE.asItem()) && !stack.isEmpty()) {
            if (stack.is(ModItems.GOLDEN_BUCKET.get())) {
                slots.set(1, new ItemStack(ModItems.GOLDEN_WATER_BUCKET.get()));
            } else if (stack.is(ModItems.GOLDEN_WATER_BUCKET.get()) && GoldenBucketItem.canBeFilled(stack)) {
                slots.set(1, GoldenBucketItem.increaseFluidLevel(stack));
            }
        }
    }

    @Inject(at = @At("RETURN"), method = "canTakeItemThroughFace", cancellable = true)
    private void canTakeItemThroughFace(int index, ItemStack stack, Direction direction, CallbackInfoReturnable<Boolean> cir) {
        if (direction == Direction.DOWN && index == 1 && stack.is(ModItems.GOLDEN_WATER_BUCKET.get()) || stack.is(ModItems.GOLDEN_BUCKET.get())) {
            cir.setReturnValue(true);
        }
    }

    @Inject(at = @At("RETURN"), method = "canPlaceItem", cancellable = true)
    private void canPlaceItem(int index, ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        ItemStack existingStack = items.get(1);
        if (index == 1 && (stack.is(ModItems.GOLDEN_BUCKET.get()) && !existingStack.is(ModItems.GOLDEN_BUCKET.get())) || (stack.is(ModItems.GOLDEN_WATER_BUCKET.get()) && GoldenBucketItem.canBeFilled(stack))) {
            cir.setReturnValue(true);
        }
    }
}
