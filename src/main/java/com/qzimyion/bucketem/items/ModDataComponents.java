package com.qzimyion.bucketem.items;

import com.mojang.serialization.Codec;
import com.qzimyion.bucketem.Bucketem;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModDataComponents {

    public static final ComponentType<Boolean> SLIME_CHUNK_F = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of(Bucketem.MOD_ID, "slime_chunk_f"),
            ComponentType.<Boolean>builder().codec(Codec.BOOL).build()
    );

    public static void registerComponents() {
    }
}
