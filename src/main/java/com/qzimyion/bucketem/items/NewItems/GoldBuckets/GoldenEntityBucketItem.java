//package com.qzimyion.bucketem.items.NewItems.GoldBuckets;
//
//import com.google.common.collect.Lists;
//import net.minecraft.entity.*;
//import net.minecraft.entity.player.PlayerEntity;
//import net.minecraft.fluid.Fluid;
//import net.minecraft.item.BucketItem;
//import net.minecraft.item.ItemStack;
//import net.minecraft.nbt.NbtCompound;
//import net.minecraft.server.world.ServerWorld;
//import net.minecraft.sound.SoundCategory;
//import net.minecraft.sound.SoundEvent;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.world.World;
//import net.minecraft.world.event.GameEvent;
//import org.jetbrains.annotations.Nullable;
//
//import java.util.List;
//
//public class GoldenEntityBucketItem extends BucketItem {
//    private final List<EntityType<?>> entityType;
//    private final SoundEvent emptyingSound;
//
//    public GoldenEntityBucketItem(Fluid fluid, List<EntityType<?>> type, SoundEvent emptyingSound, Settings settings) {
//        super(fluid, settings);
//        this.entityType = Lists.newArrayList();
//        this.emptyingSound = emptyingSound;
//    }
//
//    public boolean hasNoEntities() {
//        return entityType.isEmpty();
//    }
//
//    public boolean isFullOfEntities() {
//        return entityType.size() == 3;
//    }
//
//
//    public void onEmptied(@Nullable PlayerEntity player, World world, ItemStack stack, BlockPos pos) {
//        if (world instanceof ServerWorld) {
//            this.releaseEntity(world, pos, stack);
//            world.emitGameEvent(player, GameEvent.ENTITY_PLACE, pos);
//        }
//    }
//
//    private boolean releaseEntity(World world, BlockPos pos, ItemStack stack){
//        NbtCompound nbtCompound = new NbtCompound();
//        Entity entity = EntityType.loadEntityWithPassengers(nbtCompound, world, (entityx) -> entityx);
//        if (entity instanceof Bucketable bucketable){
//            world.playSound(null, pos, emptyingSound, SoundCategory.NEUTRAL, 1, 1);
//            //this.entityType.removeLast().spawnFromItemStack((ServerWorld) world, stack, null, pos, SpawnReason.BUCKET, true, false);
//            bucketable.copyDataFromNbt(stack.getOrCreateNbt());
//            bucketable.setFromBucket(true);
//            return world.spawnEntity(entity);
//        } else {
//            return false;
//        }
//    }
//}
