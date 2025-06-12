package com.qzimyion.bucketem.fabric.mixin;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.qzimyion.bucketem.util.BuckEmBucketable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.level.ServerLevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EntityType.class)
public class FabricEntityTypeMixin {

    @WrapWithCondition(method = "create(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/nbt/CompoundTag;Ljava/util/function/Consumer;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/entity/MobSpawnType;ZZ)Lnet/minecraft/world/entity/Entity;", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Mob;finalizeSpawn(Lnet/minecraft/world/level/ServerLevelAccessor;Lnet/minecraft/world/DifficultyInstance;Lnet/minecraft/world/entity/MobSpawnType;Lnet/minecraft/world/entity/SpawnGroupData;Lnet/minecraft/nbt/CompoundTag;)Lnet/minecraft/world/entity/SpawnGroupData;"))
    private boolean cancelFinalizeSpawnForBucketedMobs(Mob mob, ServerLevelAccessor accessor, DifficultyInstance difficulty, MobSpawnType spawnType, SpawnGroupData groupData, CompoundTag tag) {
        return !(mob instanceof BuckEmBucketable && spawnType == MobSpawnType.BUCKET);
    }
}
