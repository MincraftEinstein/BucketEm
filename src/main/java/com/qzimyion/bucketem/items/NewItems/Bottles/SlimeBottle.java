package com.qzimyion.bucketem.items.NewItems.Bottles;

import com.qzimyion.bucketem.items.ModDataComponents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.ChunkRandom;
import net.minecraft.world.World;

public class SlimeBottle extends EntityBottle{
    protected boolean enableSlimeChunkExcitement = true;

    public SlimeBottle(EntityType<?> getType, Settings settings) {
        super(getType, settings);
    }

    public static boolean isSlimeChunk(ServerWorld world, int x, int z) {
        ChunkPos chunkpos = new ChunkPos(new BlockPos(x, 0, z));
        return ChunkRandom.getSlimeRandom(chunkpos.x, chunkpos.z, world.getSeed(), 987234911L).nextInt(10) == 0;
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if(world instanceof ServerWorld serverWorld) {
            boolean chunkFinder = Boolean.TRUE.equals(stack.get(ModDataComponents.SLIME_CHUNK_F));
            Vec3d pos = entity.getPos();
            int x = (int) Math.floor(pos.x);
            int z = (int) Math.floor(pos.z);
            if (isSlimeChunk(serverWorld, x, z)){
                if (enableSlimeChunkExcitement){
                    if (chunkFinder != isSlimeChunk(serverWorld, x, z)){
                        stack.set(ModDataComponents.SLIME_CHUNK_F, isSlimeChunk(serverWorld, x, z));
                    }
                }
            }
            if (!isSlimeChunk(serverWorld, x, z)){
                stack.set(ModDataComponents.SLIME_CHUNK_F, false);
            }
        }
    }
}
