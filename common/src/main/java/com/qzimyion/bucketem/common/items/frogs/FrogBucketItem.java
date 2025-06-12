package com.qzimyion.bucketem.common.items.frogs;

import com.qzimyion.bucketem.util.BuckEmBucketable;
import net.minecraft.core.BlockPos;
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

public class FrogBucketItem extends BucketItem {

    private final FrogVariant variant;

    public FrogBucketItem(FrogVariant variant, Fluid fluid, Properties properties) {
        super(fluid, properties);
        this.variant = variant;
    }

    @Override
    public void checkExtraContent(@Nullable Player player, Level level, ItemStack stack, BlockPos pos) {
        if (level instanceof ServerLevel serverLevel) {
            Frog frog = EntityType.FROG.spawn(serverLevel, stack, null, pos, MobSpawnType.BUCKET, true, false);
            if (frog instanceof BuckEmBucketable bucketable) {
                frog.setVariant(variant);
                bucketable.loadFromBucketTag(stack.getOrCreateTag());
            }
            level.gameEvent(player, GameEvent.ENTITY_PLACE, pos);
        }
    }
}
