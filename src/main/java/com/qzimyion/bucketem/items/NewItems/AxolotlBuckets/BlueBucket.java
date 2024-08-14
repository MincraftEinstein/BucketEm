package com.qzimyion.bucketem.items.NewItems.AxolotlBuckets;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.passive.AxolotlEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

public class BlueBucket extends GoldBucket{

    public BlueBucket(Fluid fluid, Settings settings) {
        super(fluid, settings);
    }

    @Override
    public void onEmptied(@Nullable PlayerEntity player, World world, ItemStack stack, BlockPos pos) {
        if (world instanceof ServerWorld){
            this.spawnEntity((ServerWorld) world, stack, pos);
            world.emitGameEvent(player, GameEvent.ENTITY_PLACE, pos);
        }
    }

    @Override
    public void spawnEntity(ServerWorld world, ItemStack stack, BlockPos pos) {
        AxolotlEntity entity = EntityType.AXOLOTL.spawnFromItemStack(world, stack, null, pos, SpawnReason.BUCKET, true, false);
        if (entity != null) {
            entity.setPersistent();
            entity.setVariant(AxolotlEntity.Variant.BLUE);
        }
        NbtComponent.set(DataComponentTypes.BUCKET_ENTITY_DATA, stack, nbt ->{
            int age = nbt.contains("Age") ? nbt.getInt("Age") : 0;
            assert entity != null;
            entity.setBreedingAge(age);
        });
    }
}
