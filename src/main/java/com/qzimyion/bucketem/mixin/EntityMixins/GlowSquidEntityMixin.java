package com.qzimyion.bucketem.mixin.EntityMixins;

import com.qzimyion.bucketem.items.ModItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.GlowSquid;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SuppressWarnings("deprecation")
@Debug(export = true)
@Mixin(GlowSquid.class)
public abstract class GlowSquidEntityMixin extends WaterAnimal implements Bucketable {

    @Unique
    private static final EntityDataAccessor<Boolean> FROM_BUCKET = SynchedEntityData.defineId(GlowSquidEntityMixin.class, EntityDataSerializers.BOOLEAN);

    protected GlowSquidEntityMixin(EntityType<? extends WaterAnimal> entityType, Level world) {
        super(entityType, world);
    }

    @Inject(at = @At("HEAD"), method = "defineSynchedData")
    public void initDataTracker(CallbackInfo ci){
        this.entityData.define(FROM_BUCKET, false);
    }

    @Inject(at = @At("HEAD"), method = "addAdditionalSaveData")
    public void writeCustomDataToNbt(CompoundTag nbt, CallbackInfo ci){
        super.addAdditionalSaveData(nbt);
        nbt.putBoolean("FromBucket", this.fromBucket());
    }

    @Inject(at = @At("HEAD"), method = "readAdditionalSaveData")
    public void readCustomDataFromNbt(CompoundTag nbt, CallbackInfo ci){
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

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        return Bucketable.bucketMobPickup(player, hand, this).orElse(super.mobInteract(player, hand));
    }

    @Override
    public void saveToBucketTag(ItemStack stack) {
        Bucketable.saveDefaultDataToBucketTag(this, stack);
    }

    @Override
    public void loadFromBucketTag(CompoundTag nbt) {
        Bucketable.loadDefaultDataFromBucketTag(this, nbt);
    }

    @Override
    public ItemStack getBucketItemStack() {
        return new ItemStack(ModItems.GLOW_SQUID_BUCKET);
    }

    @Override
    public SoundEvent getPickupSound() {
        return SoundEvents.BUCKET_FILL_FISH;
    }

}
