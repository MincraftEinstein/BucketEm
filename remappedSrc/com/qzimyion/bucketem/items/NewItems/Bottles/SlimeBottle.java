package com.qzimyion.bucketem.items.NewItems.Bottles;

import java.util.Objects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.phys.Vec3;

public class SlimeBottle extends Item {
    protected boolean enableSlimeChunkExcitement = true;

    public SlimeBottle(Item.Properties settings) {
        super(settings);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level world = context.getLevel();
        world.playSound(context.getPlayer(), context.getClickedPos(), SoundEvents.BOTTLE_FILL_DRAGONBREATH, SoundSource.BLOCKS, 1, 1);
        if (world.isClientSide){
            return InteractionResult.SUCCESS;
        } else {
            ItemStack itemStack = context.getItemInHand();
            BlockPos blockPos = context.getClickedPos();
            Direction direction = context.getClickedFace();
            BlockState blockState = world.getBlockState(blockPos);

            BlockPos blockPos1;
            if (blockState.getCollisionShape(world, blockPos).isEmpty()){
                blockPos1 = blockPos;
            } else {
                blockPos1 = blockPos.relative(direction);
            }
            CompoundTag nbt = itemStack.getOrCreateTag();
            if (!Objects.requireNonNull(context.getPlayer()).getAbilities().instabuild) {
                context.getPlayer().setItemInHand(context.getHand(), new ItemStack(Items.GLASS_BOTTLE));
            }
            Slime entity = EntityType.SLIME.spawn((ServerLevel) world, itemStack, null, blockPos1, MobSpawnType.BUCKET, true, false);
            if (entity != null) {
                entity.setPersistenceRequired();
                entity.setSize(1, false);
            }
            int size = nbt.contains("Size") ? nbt.getInt("Size") : 1;
            assert entity != null;
            entity.setSize(size, false);
        }
        return InteractionResult.CONSUME;
    }

    @Override
    public boolean allowNbtUpdateAnimation(Player player, InteractionHand hand, ItemStack oldStack, ItemStack newStack) {
        return false;
    }

    public static boolean isSlimeChunk(ServerLevel world, int x, int z) {
        ChunkPos chunkpos = new ChunkPos(new BlockPos(x, 0, z));
        return WorldgenRandom.seedSlimeChunk(chunkpos.x, chunkpos.z, world.getSeed(), 987234911L).nextInt(10) == 0;
    }

    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean selected) {
        if(world instanceof ServerLevel serverWorld) {
            boolean chunkFinder = Boolean.TRUE.equals(stack.getOrCreateTag().getBoolean("SlimeChunk"));
            Vec3 pos = entity.position();
            int x = (int) Math.floor(pos.x);
            int z = (int) Math.floor(pos.z);
            if (isSlimeChunk(serverWorld, x, z)){
                if (enableSlimeChunkExcitement){
                    if (chunkFinder != isSlimeChunk(serverWorld, x, z)){
                        stack.getOrCreateTag().putBoolean("SlimeChunk", isSlimeChunk(serverWorld, x, z));
                    }
                }
            }
            if (!isSlimeChunk(serverWorld, x, z)){
                stack.getOrCreateTag().putBoolean("SlimeChunk", false);
            }
        }
    }
}
