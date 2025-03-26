package com.qzimyion.bucketem.common.items.Frogs;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.FrogVariant;
import net.minecraft.world.entity.animal.frog.Frog;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class ColdDryFrogBuckets extends BucketItem {
    public ColdDryFrogBuckets(Fluid fluid, Properties properties) {
        super(fluid, properties);
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        level.playSound(context.getPlayer(), context.getClickedPos(), SoundEvents.BUCKET_FILL_TADPOLE, SoundSource.BLOCKS, 1, 1);
        if (level.isClientSide()){
            return InteractionResult.SUCCESS;
        } else {
            ItemStack itemStack = context.getItemInHand();
            BlockPos blockPos = context.getClickedPos();
            Direction direction = context.getClickedFace();
            BlockState blockState = level.getBlockState(blockPos);

            BlockPos pos;
            if (blockState.getCollisionShape(level, blockPos).isEmpty()){
                pos = blockPos;
            } else {
                pos = blockPos.relative(direction);
            }
            if (!Objects.requireNonNull(context.getPlayer()).getAbilities().instabuild) {
                context.getPlayer().setItemInHand(context.getHand(), new ItemStack(Items.BUCKET));
            }
            Holder<FrogVariant> variantHolder = level.registryAccess().registryOrThrow(Registries.FROG_VARIANT).getHolder(FrogVariant.COLD).get();
            Frog entity = EntityType.FROG.spawn((ServerLevel) level, itemStack, null, pos, MobSpawnType.BUCKET, true, false);
            if (entity != null) {
                entity.setPersistenceRequired();
                entity.setVariant(variantHolder);
            }
        }
        return InteractionResult.CONSUME;
    }
}
