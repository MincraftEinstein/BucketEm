package com.qzimyion.bucketem.common.items;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.monster.MagmaCube;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class EntityBottleItem extends Item {
    private final EntityType<?> getType;
    private final SoundEvent soundEvent;
    private final Item storeageItem;
    private static final MapCodec<EntityType<?>> ENTITY_TYPE_MAP_CODEC = BuiltInRegistries.ENTITY_TYPE.byNameCodec().fieldOf("id");
    public EntityBottleItem(EntityType<?> entityType, Item storeageItem, SoundEvent soundEvent, Properties properties) {
        super(properties);
        this.getType = entityType;
        this.soundEvent = soundEvent;
        this.storeageItem = storeageItem;
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

            EntityType<?> entitytype = this.getType(itemStack);
            if (!Objects.requireNonNull(context.getPlayer()).getAbilities().instabuild){
                context.getPlayer().setItemInHand(context.getHand(), this.storeageItem.getDefaultInstance());
            }
            Entity entity = entitytype.spawn((ServerLevel) world, itemStack, context.getPlayer(), blockPos1, MobSpawnType.BUCKET, true, !Objects.equals(blockPos, blockPos1) && direction == Direction.UP);
            if (entity instanceof Mob){
                ((Mob) entity).setPersistenceRequired();
            }
            CustomData.update(DataComponents.BUCKET_ENTITY_DATA, itemStack, compoundTag ->
            {
                int size = 1;
                if (entity instanceof Bee bee) {
                    int anger = compoundTag.contains("Anger") ? compoundTag.getInt("Anger") : 0;
//                    UUID angryAt = compoundTag.getUUID("AngryAt");;
//                    if (compoundTag.contains("AngryAt")){
//                        angryAt = compoundTag.getUUID("AngryAt");
//                    }
//                    else{
//                        assert bee.getPersistentAngerTarget() != null;
//                        compoundTag.putUUID("AngryAt", bee.getPersistentAngerTarget());
//                    }
                    int age = compoundTag.contains("Age") ? compoundTag.getInt("Age") : 0;
                    float health = compoundTag.contains("Health") ? compoundTag.getFloat("Health") : 10.0F;
                    boolean nectar = compoundTag.contains("HasNectar") && compoundTag.getBoolean("HasNectar");
                    boolean stung = compoundTag.contains("HasStung") && compoundTag.getBoolean("HasStung");
                    bee.setHasNectar(nectar);
                    bee.setHasStung(stung);
                    bee.setAge(age);
                    bee.setRemainingPersistentAngerTime(anger);
                    //bee.setPersistentAngerTarget(angryAt);
                    bee.setHealth(health);
                    bee.setPersistenceRequired();
                }
                if (entity instanceof Slime slimeEntity) {
                    slimeEntity.setSize(size, false);
                }
                if (entity instanceof MagmaCube magmaCubeEntity) {
                    magmaCubeEntity.setSize(size, false);
                }
            });
            return InteractionResult.CONSUME;
        }
    }

    public EntityType<?> getType(ItemStack stack) {
        CustomData nbtComponent = stack.getOrDefault(DataComponents.ENTITY_DATA, CustomData.EMPTY);
        if (!nbtComponent.isEmpty()) {
            return nbtComponent.read(ENTITY_TYPE_MAP_CODEC).result().orElse(this.getType);
        }
        return this.getType;
    }

}
