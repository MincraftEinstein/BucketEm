package com.qzimyion.bucketem.common.items.Frogs;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.FrogVariant;
import net.minecraft.world.entity.animal.frog.Frog;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("OptionalGetWithoutIsPresent")
public class TemperateFrogBuckets extends BucketItem {
    public TemperateFrogBuckets(Fluid fluid, Properties properties) {
        super(fluid, properties);
    }

    @Override
    public void checkExtraContent(@Nullable Player player, Level level, ItemStack itemStack, BlockPos blockPos) {
        if (level instanceof ServerLevel){
            spawnEntity((ServerLevel) level, itemStack, blockPos);
            level.gameEvent(player, GameEvent.ENTITY_PLACE, blockPos);
        }
    }

    public void spawnEntity(ServerLevel level, ItemStack stack, BlockPos pos){
        Holder<FrogVariant> variantHolder = level.registryAccess().registryOrThrow(Registries.FROG_VARIANT).getHolder(FrogVariant.TEMPERATE).get();
        Frog entity = EntityType.FROG.spawn(level, stack, null, pos, MobSpawnType.BUCKET, true, false);
        if (entity != null) {
            entity.setPersistenceRequired();
            entity.setVariant(variantHolder);
        }
    }
}
