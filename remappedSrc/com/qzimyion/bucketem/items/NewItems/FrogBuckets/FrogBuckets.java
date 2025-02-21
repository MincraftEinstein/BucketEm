package com.qzimyion.bucketem.items.NewItems.FrogBuckets;

import net.frozenblock.wilderwild.entity.variant.JellyfishVariant;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.FrogVariant;
import net.minecraft.world.entity.animal.frog.Frog;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;
import org.jetbrains.annotations.Nullable;

public class FrogBuckets extends BucketItem {

    final FrogVariant variant;
    public FrogBuckets(FrogVariant variant ,Fluid fluid, Properties settings) {
        super(fluid, settings);
        this.variant = variant;
    }

    @Override
    public void checkExtraContent(@Nullable Player player, Level world, ItemStack stack, BlockPos pos) {
        if (world instanceof ServerLevel){
            this.spawnEntity((ServerLevel) world, stack, pos);
            world.gameEvent(player, GameEvent.ENTITY_PLACE, pos);
        }
    }

    @Override
    public void playEmptySound(@Nullable Player player, LevelAccessor world, BlockPos pos) {
        world.playSound(player, pos, SoundEvents.BUCKET_EMPTY_FISH, SoundSource.NEUTRAL, 1.0F, 1.0F);
    }

    public void spawnEntity(ServerLevel world, ItemStack stack, BlockPos pos) {
        Frog entity = EntityType.FROG.spawn(world, stack, null, pos, MobSpawnType.BUCKET, true, false);
        if (entity != null) {
            entity.setPersistenceRequired();
            entity.setVariant(variant);
        }
    }
}
