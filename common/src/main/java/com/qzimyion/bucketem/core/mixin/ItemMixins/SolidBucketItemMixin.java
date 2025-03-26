package com.qzimyion.bucketem.core.mixin.ItemMixins;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(SolidBucketItem.class)
public class SolidBucketItemMixin extends BlockItem {


    public SolidBucketItemMixin(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext useOnContext) {
        Player player = useOnContext.getPlayer();
        InteractionHand hand = useOnContext.getHand();
        InteractionResult interactionResult = super.useOn(useOnContext);
        if (interactionResult.consumesAction() && player != null && !player.isCreative()) {
            if (!player.hasInfiniteMaterials()) {
                ItemStack emptyBucket = new ItemStack(Items.BUCKET);
                if (!player.getInventory().add(emptyBucket)) {
                    player.drop(emptyBucket, false);
                }
            } else {
                player.setItemInHand(hand, new ItemStack(Items.BUCKET));
            }
        }
        return interactionResult;
    }
}
