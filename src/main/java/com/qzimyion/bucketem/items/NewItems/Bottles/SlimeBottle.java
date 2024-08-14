package com.qzimyion.bucketem.items.NewItems.Bottles;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.ChunkRandom;
import net.minecraft.world.World;

import java.util.Objects;

public class SlimeBottle extends Item {
    protected boolean enableSlimeChunkExcitement = true;

    public SlimeBottle(Item.Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        world.playSound(context.getPlayer(), context.getBlockPos(), SoundEvents.ITEM_BOTTLE_FILL_DRAGONBREATH, SoundCategory.BLOCKS, 1, 1);
        if (world.isClient){
            return ActionResult.SUCCESS;
        } else {
            ItemStack itemStack = context.getStack();
            BlockPos blockPos = context.getBlockPos();
            Direction direction = context.getSide();
            BlockState blockState = world.getBlockState(blockPos);

            BlockPos blockPos1;
            if (blockState.getCollisionShape(world, blockPos).isEmpty()){
                blockPos1 = blockPos;
            } else {
                blockPos1 = blockPos.offset(direction);
            }
            NbtCompound nbt = itemStack.getOrCreateNbt();
            if (!Objects.requireNonNull(context.getPlayer()).getAbilities().creativeMode) {
                context.getPlayer().setStackInHand(context.getHand(), new ItemStack(Items.GLASS_BOTTLE));
            }
            SlimeEntity entity = EntityType.SLIME.spawnFromItemStack((ServerWorld) world, itemStack, null, blockPos1, SpawnReason.BUCKET, true, false);
            if (entity != null) {
                entity.setPersistent();
                entity.setSize(1, false);
            }
            int size = nbt.contains("Size") ? nbt.getInt("Size") : 1;
            assert entity != null;
            entity.setSize(size, false);
        }
        return ActionResult.CONSUME;
    }

    @Override
    public boolean allowNbtUpdateAnimation(PlayerEntity player, Hand hand, ItemStack oldStack, ItemStack newStack) {
        return false;
    }

    public static boolean isSlimeChunk(ServerWorld world, int x, int z) {
        ChunkPos chunkpos = new ChunkPos(new BlockPos(x, 0, z));
        return ChunkRandom.getSlimeRandom(chunkpos.x, chunkpos.z, world.getSeed(), 987234911L).nextInt(10) == 0;
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if(world instanceof ServerWorld serverWorld) {
            boolean chunkFinder = Boolean.TRUE.equals(stack.getOrCreateNbt().getBoolean("SlimeChunk"));
            Vec3d pos = entity.getPos();
            int x = (int) Math.floor(pos.x);
            int z = (int) Math.floor(pos.z);
            if (isSlimeChunk(serverWorld, x, z)){
                if (enableSlimeChunkExcitement){
                    if (chunkFinder != isSlimeChunk(serverWorld, x, z)){
                        stack.getOrCreateNbt().putBoolean("SlimeChunk", isSlimeChunk(serverWorld, x, z));
                    }
                }
            }
            if (!isSlimeChunk(serverWorld, x, z)){
                stack.getOrCreateNbt().putBoolean("SlimeChunk", false);
            }
        }
    }
}
