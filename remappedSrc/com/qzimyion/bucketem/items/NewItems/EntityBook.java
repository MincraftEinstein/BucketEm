package com.qzimyion.bucketem.items.NewItems;

import java.util.Objects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class EntityBook extends Item {
    private final EntityType<?> getType;

    public EntityBook(EntityType<?> getType, Properties settings) {
        super(settings);
        this.getType = getType;
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level world = context.getLevel();
        world.playSound(context.getPlayer(), context.getClickedPos(), SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.BLOCKS, 1, 1);
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

            EntityType<?> entitytype = this.getType(itemStack.getTag());
            if (!Objects.requireNonNull(context.getPlayer()).getAbilities().instabuild){
                context.getPlayer().setItemInHand(context.getHand(), new ItemStack(Items.BOOK));
            }
            Entity entity = entitytype.spawn((ServerLevel) world, itemStack, context.getPlayer(), blockPos1, MobSpawnType.BUCKET, true, !Objects.equals(blockPos, blockPos1) && direction == Direction.UP);
            if (entity instanceof Mob){
                ((Mob) entity).setPersistenceRequired();
            }
            return InteractionResult.CONSUME;
        }
    }

    public EntityType<?> getType(CompoundTag nbt) {
        if (nbt != null && nbt.contains("EntityTag", 10)){
            CompoundTag nbtCompound = nbt.getCompound("EntityTag");
            if (nbtCompound.contains("id", 8)){
                return EntityType.byString(nbtCompound.getString("id")).orElse(this.getType);
            }
        }
        return this.getType;
    }
}
