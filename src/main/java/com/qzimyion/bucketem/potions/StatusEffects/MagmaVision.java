package com.qzimyion.bucketem.potions.StatusEffects;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class MagmaVision extends StatusEffect {

    public MagmaVision(){
        super(StatusEffectCategory.BENEFICIAL, 0x661348);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }
}
