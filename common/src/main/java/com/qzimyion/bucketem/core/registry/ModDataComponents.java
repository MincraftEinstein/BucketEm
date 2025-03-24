package com.qzimyion.bucketem.core.registry;

import com.mojang.serialization.Codec;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;

import static com.qzimyion.bucketem.BucketEmCommon.MOD_ID;

public class ModDataComponents {

    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENTS = DeferredRegister.create(MOD_ID, Registries.DATA_COMPONENT_TYPE);

    public static final RegistrySupplier<DataComponentType<Boolean>> SLIME_CHUNK_COMPONENT =
            DATA_COMPONENTS.register("slime_chunk_component", () -> new DataComponentType.Builder<Boolean>()
                    .persistent(Codec.BOOL)
                    .networkSynchronized(ByteBufCodecs.BOOL)
                    .build());

    public static void registerDataComponents(){
        DATA_COMPONENTS.register();
    }

}
