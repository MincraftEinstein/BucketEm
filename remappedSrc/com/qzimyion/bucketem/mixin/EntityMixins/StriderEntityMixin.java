package com.qzimyion.bucketem.mixin.EntityMixins;

import com.qzimyion.bucketem.items.ModItems;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ItemBasedSteering;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.monster.Strider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@SuppressWarnings("deprecation")
@Debug(export = true)
@Mixin(Strider.class)
public abstract class StriderEntityMixin extends Animal implements Bucketable {

    @Shadow
    public abstract void tick();

    @Shadow @Final
    private ItemBasedSteering saddledComponent;
    @Unique
    private static final EntityDataAccessor<Boolean> FROM_BUCKET = SynchedEntityData.defineId(StriderEntityMixin.class, EntityDataSerializers.BOOLEAN);

    protected StriderEntityMixin(EntityType<? extends Animal> entityType, Level world) {
        super(entityType, world);
    }

    @Inject(at = @At("HEAD"), method = "initDataTracker")
    public void initDataTracker(CallbackInfo ci){
        this.entityData.define(FROM_BUCKET, false);
    }

    @Inject(at = @At("HEAD"), method = "writeCustomDataToNbt")
    public void writeCustomDataToNbt(CompoundTag nbt, CallbackInfo ci){
        super.addAdditionalSaveData(nbt);
        nbt.putBoolean("FromBucket", this.fromBucket());
    }

    @Inject(at = @At("HEAD"), method = "readCustomDataFromNbt")
    public void readCustomDataFromNbt(CompoundTag nbt, CallbackInfo ci) {
        super.readAdditionalSaveData(nbt);
        this.setFromBucket(nbt.getBoolean("FromBucket"));
    }

    @Override
    public boolean fromBucket() {
        return this.entityData.get(FROM_BUCKET);
    }

    @Override
    public boolean requiresCustomPersistence() {
        return super.requiresCustomPersistence() || this.fromBucket();
    }

    @Override
    public boolean removeWhenFarAway(double distanceSquared) {
        return !this.fromBucket() && !this.hasCustomName();
    }

    @Override
    public void setFromBucket(boolean fromBucket) {
        this.entityData.set(FROM_BUCKET, fromBucket);
    }

    @Inject(at = @At("HEAD"), method = "interactMob", cancellable = true)
    public void interactMob(Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir){
        cir.setReturnValue(tryLavaBucket(player, hand, this).orElse(super.mobInteract(player, hand)));
    }

    @Override
    public void saveToBucketTag(ItemStack stack) {
        Bucketable.saveDefaultDataToBucketTag(this, stack);
        CompoundTag nbtCompound = stack.getOrCreateTag();
        nbtCompound.putInt("Age", this.getAge());
        nbtCompound.putBoolean("Saddle", this.saddledComponent.hasSaddle());
    }

    @Override
    public void loadFromBucketTag(CompoundTag nbt) {
        Bucketable.loadDefaultDataFromBucketTag(this, nbt);
        if (nbt.contains("Age")) {
            this.setAge(nbt.getInt("Age"));
        }
        if (nbt.contains("Saddle")){
            this.saddledComponent.setSaddle(nbt.getBoolean("Saddle"));
        }

    }

    @Override
    public ItemStack getBucketItemStack() {
        return new ItemStack(ModItems.STRIDER_BUCKET);
    }

    @Override
    public SoundEvent getPickupSound() {
        return SoundEvents.BUCKET_FILL_LAVA;
    }

    @Inject(at = @At("HEAD"), method = "initialize", cancellable = true)
    public void initialize(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType spawnReason, SpawnGroupData entityData, CompoundTag entityNbt, CallbackInfoReturnable<SpawnGroupData> cir) {
        if (spawnReason == MobSpawnType.BUCKET) {
            cir.setReturnValue(entityData);
        }
    }

    @Unique
    private static <T extends LivingEntity> Optional<InteractionResult> tryLavaBucket(Player player, InteractionHand hand, T entity) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (itemStack.getItem() == Items.LAVA_BUCKET && entity.isAlive()) {
            entity.playSound(((Bucketable) entity).getPickupSound(), 1.0f, 1.0f);
            ItemStack itemStack2 = ((Bucketable) entity).getBucketItemStack();
            ((Bucketable) entity).saveToBucketTag(itemStack2);
            ItemStack itemStack3 = ItemUtils.createFilledResult(itemStack, player, itemStack2, false);
            player.setItemInHand(hand, itemStack3);
            Level world = entity.level();
            if (!world.isClientSide) {
                CriteriaTriggers.FILLED_BUCKET.trigger((ServerPlayer)player, itemStack2);
            }
            entity.discard();
            return Optional.of(InteractionResult.sidedSuccess(world.isClientSide));
        }
        return Optional.empty();
    }
}
