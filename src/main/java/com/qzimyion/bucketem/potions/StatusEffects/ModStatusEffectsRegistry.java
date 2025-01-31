package com.qzimyion.bucketem.potions.StatusEffects;

import com.qzimyion.bucketem.Bucketem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModStatusEffectsRegistry {

    public static final MagmaVision MAGMA_VISION = new MagmaVision();
    public static final Echolocation ECHOLOCATION = new Echolocation();

    public static void registerStatusEffects(){
        Registry.register(Registries.STATUS_EFFECT, new Identifier(Bucketem.MOD_ID, "magma_vision"), MAGMA_VISION);
        Registry.register(Registries.STATUS_EFFECT, new Identifier(Bucketem.MOD_ID, "echolocation"), ECHOLOCATION);

        Bucketem.LOGGER.info("Registering mod Status Effects");
    }
}
