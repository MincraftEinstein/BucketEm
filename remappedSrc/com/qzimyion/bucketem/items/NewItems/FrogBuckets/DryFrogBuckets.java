package com.qzimyion.bucketem.items.NewItems.FrogBuckets;

import com.qzimyion.bucketem.items.NewItems.Bottles.SlimeBottle;
import java.util.Objects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.FrogVariant;
import net.minecraft.world.entity.animal.frog.Frog;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class DryFrogBuckets extends SlimeBottle {
    private final FrogVariant variant;
    public DryFrogBuckets(FrogVariant variant ,Properties settings) {
        super(settings);
        this.variant = variant;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level world = context.getLevel();
        world.playSound(context.getPlayer(), context.getClickedPos(), SoundEvents.BUCKET_FILL_TADPOLE, SoundSource.BLOCKS, 1, 1);
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
            if (!Objects.requireNonNull(context.getPlayer()).getAbilities().instabuild) {
                context.getPlayer().setItemInHand(context.getHand(), new ItemStack(Items.BUCKET));
            }
            Frog entity = EntityType.FROG.spawn((ServerLevel) world, itemStack, null, blockPos1, MobSpawnType.BUCKET, true, false);
            if (entity != null) {
                entity.setPersistenceRequired();
                entity.setVariant(variant);
            }
        }
        return InteractionResult.CONSUME;
    }
}
