package com.qzimyion.bucketem.common.items;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.UUID;

public class EntityBottleItem extends Item {
    private final EntityType<?> getType;
    private final SoundEvent soundEvent;
    public EntityBottleItem(EntityType<?> entityType, SoundEvent soundEvent,Properties properties) {
        super(properties);
        this.getType = entityType;
        this.soundEvent = soundEvent;
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        Level world = context.getLevel();
        world.playSound(context.getPlayer(), context.getClickedPos(), this.soundEvent, SoundSource.BLOCKS, 1, 1);
        if (world.isClientSide()){
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
                context.getPlayer().setItemInHand(context.getHand(), new ItemStack(Items.GLASS_BOTTLE));
            }
            Entity entity = entitytype.spawn((ServerLevel) world, itemStack, context.getPlayer(), blockPos1, MobSpawnType.BUCKET, true, !Objects.equals(blockPos, blockPos1) && direction == Direction.UP);
            if (entity instanceof Mob){
                ((Mob) entity).setPersistenceRequired();
            }
            CompoundTag nbt = itemStack.getOrCreateTag();
            if (entity instanceof Bee bee){
                int anger = nbt.contains("Anger") ? nbt.getInt("Anger") : 0;
                UUID angryAt = nbt.contains("AngryAt") ? nbt.getUUID("AngryAt") : null;
                int age = nbt.contains("Age") ? nbt.getInt("Age") : 0;
                float health = nbt.contains("Health") ? nbt.getFloat("Health") : 10.0F;
                boolean nectar = nbt.contains("HasNectar") && nbt.getBoolean("HasNectar");
                boolean stung = nbt.contains("HasStung") && nbt.getBoolean("HasStung");

                bee.setHasNectar(nectar);
                bee.setHasStung(stung);
                bee.setAge(age);
                bee.setRemainingPersistentAngerTime(anger);
                if (angryAt != null) bee.setPersistentAngerTarget(angryAt);
                bee.setHealth(health);
                bee.setPersistenceRequired();
            }
            return InteractionResult.CONSUME;
        }
    }

    public EntityType<?> getType(CompoundTag tag) {
        if (tag != null && tag.contains("EntityTag", 10)){
            CompoundTag nbtCompound = tag.getCompound("EntityTag");
            if (nbtCompound.contains("id", 8)){
                return EntityType.byString(nbtCompound.getString("id")).orElse(this.getType);
            }
        }
        return this.getType;
    }
}
