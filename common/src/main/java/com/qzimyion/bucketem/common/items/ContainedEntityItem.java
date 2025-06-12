package com.qzimyion.bucketem.common.items;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class ContainedEntityItem extends Item {

    private final EntityType<?> type;
    private final Item containerItem;
    private final SoundEvent soundEvent;

    public ContainedEntityItem(EntityType<?> type, Item containerItem, SoundEvent soundEvent, Properties properties) {
        super(properties);
        this.type = type;
        this.containerItem = containerItem;
        this.soundEvent = soundEvent;
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        if (level.isClientSide()) {
            return InteractionResult.SUCCESS;
        }

        Player player = context.getPlayer();
        BlockPos pos = context.getClickedPos();

        if (player != null && !player.getAbilities().instabuild) {
            player.setItemInHand(context.getHand(), containerItem.getDefaultInstance());
            player.awardStat(Stats.ITEM_USED.get(this));
        }

        spawnEntity(level, context.getItemInHand(), level.getBlockState(pos), pos, context.getClickedFace(), player);
        return InteractionResult.CONSUME;
    }

    public void spawnEntity(Level level, ItemStack stack, BlockState state, BlockPos pos, Direction direction, @Nullable Player player) {
        BlockPos spawnPos = state.getCollisionShape(level, pos).isEmpty() ? pos : pos.relative(direction);
        Entity entity = type.spawn((ServerLevel) level, stack, null, spawnPos, MobSpawnType.BUCKET, true, !Objects.equals(pos, spawnPos) && direction == Direction.UP);

        if (entity != null) {
            level.playSound(null, spawnPos, soundEvent, entity.getSoundSource(), 1, 1);
            level.gameEvent(entity, GameEvent.ENTITY_PLACE, spawnPos);
            loadEntity(entity, stack);
        }
    }

    protected void loadEntity(Entity entity, ItemStack stack) {
        if (entity instanceof Bucketable bucketable) {
            bucketable.loadFromBucketTag(stack.getOrCreateTag());
            bucketable.setFromBucket(true);
        }
    }
}
