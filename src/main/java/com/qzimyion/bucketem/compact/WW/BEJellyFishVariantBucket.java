package com.qzimyion.bucketem.compact.WW;

import net.frozenblock.wilderwild.entity.Jellyfish;
import net.frozenblock.wilderwild.entity.variant.JellyfishVariant;
import net.frozenblock.wilderwild.registry.WWEntityTypes;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.passive.FrogEntity;
import net.minecraft.entity.passive.FrogVariant;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

public class BEJellyFishVariantBucket extends BucketItem {

    final JellyfishVariant variant;
    public BEJellyFishVariantBucket(Fluid fluid, JellyfishVariant variant, Item.Settings settings) {
        super(fluid, settings);
        this.variant = variant;
    }

    @Override
    public void onEmptied(@Nullable PlayerEntity player, World world, ItemStack stack, BlockPos pos) {
        if (world instanceof ServerWorld){
            this.spawnEntity((ServerWorld) world, stack, pos);
            world.emitGameEvent(player, GameEvent.ENTITY_PLACE, pos);
        }
    }

    @Override
    public void playEmptyingSound(@Nullable PlayerEntity player, WorldAccess world, BlockPos pos) {
        world.playSound(player, pos, SoundEvents.ITEM_BUCKET_EMPTY_FISH, SoundCategory.NEUTRAL, 1.0F, 1.0F);
    }

    public void spawnEntity(ServerWorld world, ItemStack stack, BlockPos pos) {
        Jellyfish entity = WWEntityTypes.JELLYFISH.spawnFromItemStack(world, stack, null, pos, SpawnReason.BUCKET, true, false);
        if (entity != null) {
            entity.setPersistent();
            entity.setVariant(this.variant);
        }
    }
}
