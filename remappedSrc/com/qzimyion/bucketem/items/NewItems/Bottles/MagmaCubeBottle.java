package com.qzimyion.bucketem.items.NewItems.Bottles;

import java.util.Objects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.MagmaCube;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class MagmaCubeBottle extends Item {

    public MagmaCubeBottle(Item.Properties settings) {
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
            MagmaCube entity = EntityType.MAGMA_CUBE.spawn((ServerLevel) world, itemStack, null, blockPos1, MobSpawnType.BUCKET, true, false);
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
}
