package com.qzimyion.bucketem.mixin.BlockEntityMixins;

import com.qzimyion.bucketem.items.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.ChiseledBookShelfBlock;
import net.minecraft.world.level.block.entity.ChiseledBookShelfBlockEntity;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Debug(export = true)
@Mixin(ChiseledBookShelfBlock.class)
public class ChiseledBookshelfMixin {

    //Please add a tag for Enchanted books Mojang

    @Inject(at = @At("RETURN"), method = "addBook")
    private static void addBook(Level world, BlockPos pos, Player player, ChiseledBookShelfBlockEntity blockEntity, ItemStack stack, int slot, CallbackInfo ci){
        SoundEvent soundEvents = stack.is(ModItems.ALLAY_POSSESSED_BOOK) ? SoundEvents.ALLAY_AMBIENT_WITHOUT_ITEM : SoundEvents.ALLAY_AMBIENT_WITH_ITEM;
        SoundEvent soundEvents1 = stack.is(ModItems.VEX_POSSESSED_BOOK) ? SoundEvents.VEX_AMBIENT : SoundEvents.VEX_HURT;
        world.playSound(null, pos, soundEvents, SoundSource.BLOCKS, 1.0f, 1.0f);
        world.playSound(null, pos, soundEvents1, SoundSource.BLOCKS, 1.0f, 1.0f);
    }
}
