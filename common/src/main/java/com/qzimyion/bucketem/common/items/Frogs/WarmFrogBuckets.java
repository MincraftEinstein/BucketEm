package com.qzimyion.bucketem.common.items.Frogs;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.registries.VanillaRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.FrogVariant;
import net.minecraft.world.entity.animal.frog.Frog;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;
import org.jetbrains.annotations.Nullable;


public class WarmFrogBuckets extends TemperateFrogBuckets {
    public WarmFrogBuckets(Fluid fluid, Properties properties) {
        super(fluid, properties);
    }

    @Override
    public void checkExtraContent(@Nullable Player player, Level level, ItemStack itemStack, BlockPos blockPos) {
        if (level instanceof ServerLevel){
            spawnEntity((ServerLevel) level, itemStack, blockPos);
            level.gameEvent(player, GameEvent.ENTITY_PLACE, blockPos);
        }
    }

    @Override
    public void spawnEntity(ServerLevel level, ItemStack stack, BlockPos pos){
        Frog entity = EntityType.FROG.spawn(level, stack, null, pos, EntitySpawnReason.BUCKET, true, false);
        Holder<FrogVariant> variant = VanillaRegistries.createLookup().lookup(Registries.FROG_VARIANT).orElseThrow().getOrThrow(FrogVariant.WARM);
        if (entity != null) {
            entity.setPersistenceRequired();
            entity.setVariant(variant);
        }
    }
}
