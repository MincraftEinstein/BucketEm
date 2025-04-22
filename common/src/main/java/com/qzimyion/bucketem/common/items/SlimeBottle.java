package com.qzimyion.bucketem.common.items;

import com.qzimyion.bucketem.core.registry.ModDataComponents;
import dev.architectury.injectables.annotations.PlatformOnly;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.phys.Vec3;

public class SlimeBottle extends EntityBottleItem {
    protected boolean enableSlimeChunkExcitement = true;

    public SlimeBottle(EntityType<?> entityType, Item storeageItem, SoundEvent soundEvent, Properties properties) {
        super(entityType, storeageItem, soundEvent, properties);
    }

    @PlatformOnly("fabric")
    public boolean allowComponentsUpdateAnimation(Player player, InteractionHand hand, ItemStack oldStack, ItemStack newStack) {
        return false;
    }

    //TODO: Figure out what's the method called in neoforge
    @PlatformOnly("neoforge")
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return false;
    }

    public static boolean isSlimeChunk(ServerLevel level, int x, int z) {
        ChunkPos chunkpos = new ChunkPos(new BlockPos(x, 0, z));
        return WorldgenRandom.seedSlimeChunk(chunkpos.x, chunkpos.z, level.getSeed(), 987234911L).nextInt(10) == 0;
    }

    @Override
    public void inventoryTick(ItemStack itemStack, Level level, Entity entity, int i, boolean bl) {
        if(level instanceof ServerLevel serverLevel) {
            boolean chunkFinder = Boolean.TRUE.equals(itemStack.get(ModDataComponents.SLIME_CHUNK_COMPONENT.get()));
            Vec3 pos = entity.position();
            int x = (int) Math.floor(pos.x);
            int z = (int) Math.floor(pos.z);
            if (isSlimeChunk(serverLevel, x, z)){
                if (enableSlimeChunkExcitement){
                    if (chunkFinder != isSlimeChunk(serverLevel, x, z)){
                        itemStack.set(ModDataComponents.SLIME_CHUNK_COMPONENT.get(), isSlimeChunk(serverLevel, x, z));
                    }
                }
            }
            if (!isSlimeChunk(serverLevel, x, z)){
                itemStack.set(ModDataComponents.SLIME_CHUNK_COMPONENT.get(), false);
            }
        }
    }
}
