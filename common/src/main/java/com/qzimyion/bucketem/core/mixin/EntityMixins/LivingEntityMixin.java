package com.qzimyion.bucketem.core.mixin.EntityMixins;

import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;

@SuppressWarnings("SameParameterValue")
@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

//    @Shadow protected abstract boolean shouldSwimInFluids();
//
//    @Shadow protected abstract float getBaseMovementSpeedMultiplier();
//
//    @Inject(method = "travel", at = @At("HEAD"), cancellable = true)
//    private void modifyLavaMovement(Vec3d movementInput, CallbackInfo ci) {
//        LivingEntity entity = (LivingEntity) (Object) this;
//        World world = entity.getWorld();
//
//        if (entity.isInLava() && entity.hasStatusEffect(ModStatusEffectsRegistry.MAGMA_VISION)) {
//            double ex = entity.getY();
//            FluidState fluidState = world.getFluidState(entity.getBlockPos());
//            if (shouldSwimInFluids() && !entity.canWalkOnFluid(fluidState)) {
//                float f = entity.isSprinting() ? 0.9F : getBaseMovementSpeedMultiplier();
//                float g = 0.02F;
//                float h = (float) EnchantmentHelper.getDepthStrider(entity);
//
//                if (h > 3.0F) {
//                    h = 3.0F;
//                }
//                if (!entity.isOnGround()) {
//                    h *= 0.5F;
//                }
//                if (h > 0.0F) {
//                    f += (0.54600006F - f) * h / 3.0F;
//                    g += (entity.getMovementSpeed() - g) * h / 3.0F;
//                }
//                if (entity.hasStatusEffect(StatusEffects.DOLPHINS_GRACE)) {
//                    f = 0.96F;
//                }
//
//                entity.updateVelocity(g, movementInput);
//                entity.move(MovementType.SELF, entity.getVelocity());
//                Vec3d vec3d = entity.getVelocity();
//                if (entity.horizontalCollision && entity.isClimbing()) {
//                    vec3d = new Vec3d(vec3d.x, 0.2, vec3d.z);
//                }
//                if (movementInput.y <= 0) {
//                    entity.setVelocity(entity.getVelocity().add(0.0, -0.1, 0.0));
//                } else {
//                    entity.setVelocity(entity.getVelocity().add(0.0, 0.02, 0.0));
//                }
//                entity.setVelocity(vec3d.multiply(f, 0.8F, f));
//                Vec3d vec3d2 = entity.applyFluidMovingSpeed(0.08, entity.getVelocity().y <= 0.0, entity.getVelocity());
//                entity.setVelocity(vec3d2);
//
//                if (entity.horizontalCollision && entity.doesNotCollide(vec3d2.x, vec3d2.y + 0.6F - entity.getY() + ex, vec3d2.z)) {
//                    entity.setVelocity(vec3d2.x, 0.3F, vec3d2.z);
//                }
//                ci.cancel();
//            }
//        }
//    }

}
