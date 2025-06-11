package com.qzimyion.bucketem.core.mixin.block_entities;

import com.qzimyion.bucketem.core.registry.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.ChiseledBookShelfBlock;
import net.minecraft.world.level.block.entity.ChiseledBookShelfBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChiseledBookShelfBlock.class)
public class ChiseledBookshelfMixin {

    @Inject(at = @At("RETURN"), method = "addBook")
    private static void addBook(Level level, BlockPos pos, Player player, ChiseledBookShelfBlockEntity blockEntity, ItemStack stack, int slot, CallbackInfo ci) {
        if (!level.isClientSide) {
            SoundEvent soundEvent = stack.is(ModItems.ALLAY_POSSESSED_BOOK.get()) ? SoundEvents.ALLAY_AMBIENT_WITH_ITEM : stack.is(ModItems.VEX_POSSESSED_BOOK.get()) ? SoundEvents.VEX_AMBIENT : null;
            if (soundEvent != null) {
                level.playSound(null, pos, soundEvent, SoundSource.BLOCKS, 0.5F, 1);
            }
        }
    }
}
