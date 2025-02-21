//package com.qzimyion.bucketem.items.NewItems.GoldBuckets;
//
//import net.minecraft.entity.*;
//import net.minecraft.entity.mob.MobEntity;
//import net.minecraft.entity.player.PlayerEntity;
//import net.minecraft.fluid.Fluid;
//import net.minecraft.item.ItemStack;
//import net.minecraft.nbt.NbtCompound;
//import net.minecraft.server.world.ServerWorld;
//import net.minecraft.sound.SoundCategory;
//import net.minecraft.sound.SoundEvent;
//import net.minecraft.text.Text;
//import net.minecraft.util.Hand;
//import net.minecraft.util.TypedActionResult;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.util.math.Box;
//import net.minecraft.world.World;
//import net.minecraft.world.event.GameEvent;
//import org.jetbrains.annotations.Nullable;
//
//import java.util.List;
//
//public class GoldenEntityBucketItem extends GoldenBucketItem {
//    private final EntityType<?> entityType;
//    private final SoundEvent emptyingSound;
//    public static final String NBT_TAG = "EntityAmount";
//
//    public GoldenEntityBucketItem(Fluid fluid, EntityType<?> type, SoundEvent emptyingSound, Settings settings) {
//        super(fluid, settings);
//        this.entityType = type;
//        this.emptyingSound = emptyingSound;
//    }
//
//    public void storeMobType(ItemStack stack) {
//        NbtCompound nbt = stack.getOrCreateNbt();
//        int storedMobs = nbt.getInt(NBT_TAG);
//        if (storedMobs < 3) {
//            nbt.putString("Mob" + storedMobs, EntityType.getId(this.entityType).toString());
//            nbt.putInt(NBT_TAG, storedMobs + 1);
//            stack.setNbt(nbt);
//        }
//    }
//
//    public void getMob(World world, ItemStack stack, int index) {
//        if (index < 0 || index >= 3) return;
//        NbtCompound nbt = stack.getOrCreateNbt();
//        if (nbt.contains("Mob" + index)) {
//            NbtCompound mobData = nbt.getCompound("Mob" + index);
//            BlockPos pos = world.getSpawnPos();
//            world.playSound(null, pos, emptyingSound, SoundCategory.NEUTRAL, 1, 1);
//            entityType.loadEntityWithPassengers(mobData, world, (entity) -> entity);
//        }
//    }
//
//    @Override
//    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
//        ItemStack stack = player.getStackInHand(hand);
//        if (!world.isClient) {
//            Box range = player.getBoundingBox().expand(4.5);
//            List<MobEntity> mobs = world.getEntitiesByClass(MobEntity.class, range, mob -> mob.isAlive() && !mob.getUuid().equals(player.getUuid()));
//            if (!mobs.isEmpty()) {
//                MobEntity mob = mobs.get(0);
//                storeMobType(stack);
//                mob.remove(Entity.RemovalReason.DISCARDED);
//                player.sendMessage(Text.of("Stored a " + mob.getType().getName().getString()), true);
//                return TypedActionResult.success(stack);
//            }
//        }
//        return TypedActionResult.pass(stack);
//    }
//
//
//    @Override
//    public void onEmptied(@Nullable PlayerEntity player, World world, ItemStack stack, BlockPos pos) {
//        int index = stack.getOrCreateNbt().getInt(NBT_TAG);
//        if (world instanceof ServerWorld) {
//            this.getMob(world, stack, index);
//            spawnEntity((ServerWorld) world, stack, pos);
//            world.emitGameEvent(player, GameEvent.ENTITY_PLACE, pos);
//        }
//    }
//
//    private void spawnEntity(ServerWorld world, ItemStack stack, BlockPos pos) {
//        Entity entity = this.entityType.spawnFromItemStack(world, stack, null, pos, SpawnReason.BUCKET, true, false);
//        if (entity instanceof Bucketable bucketable) {
//            NbtCompound nbt = stack.getOrCreateNbt();
//            int storedMobs = nbt.getInt(NBT_TAG);
//            nbt.putInt(NBT_TAG, storedMobs - 1);
//            bucketable.copyDataFromNbt(stack.getOrCreateNbt());
//            bucketable.setFromBucket(true);
//        }
//
//    }
//}
