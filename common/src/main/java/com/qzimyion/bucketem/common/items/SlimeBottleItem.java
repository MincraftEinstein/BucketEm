package com.qzimyion.bucketem.common.items;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.phys.Vec3;

public class SlimeBottleItem extends SlimeLikeBottleItem {

    public SlimeBottleItem(Properties properties) {
        super(EntityType.SLIME, Items.GLASS_BOTTLE, SoundEvents.BOTTLE_FILL_DRAGONBREATH, properties);
    }

    @Override
    public void inventoryTick(ItemStack itemStack, Level level, Entity entity, int i, boolean bl) {
        if (level instanceof ServerLevel serverLevel) {
            boolean chunkFinder = itemStack.getOrCreateTag().getBoolean("SlimeChunk");
            Vec3 pos = entity.position();
            int x = (int) Math.floor(pos.x);
            int z = (int) Math.floor(pos.z);

            if (isSlimeChunk(serverLevel, x, z)) {
                if (chunkFinder != isSlimeChunk(serverLevel, x, z)) {
                    itemStack.getOrCreateTag().putBoolean("SlimeChunk", isSlimeChunk(serverLevel, x, z));
                }
            }

            if (!isSlimeChunk(serverLevel, x, z)) {
                itemStack.getOrCreateTag().putBoolean("SlimeChunk", false);
            }
        }
    }

    // TODO
    public static boolean isSlimeChunk(ServerLevel level, int x, int z) {
        ChunkPos chunkpos = new ChunkPos(new BlockPos(x, 0, z));
        return WorldgenRandom.seedSlimeChunk(chunkpos.x, chunkpos.z, level.getSeed(), 987234911L).nextInt(10) == 0;
    }
}
