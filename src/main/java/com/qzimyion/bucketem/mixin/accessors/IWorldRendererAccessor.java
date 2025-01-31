package com.qzimyion.bucketem.mixin.accessors;

import net.minecraft.client.render.BuiltChunkStorage;
import net.minecraft.client.render.WorldRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(WorldRenderer.class)
public interface IWorldRendererAccessor {

    @Accessor("chunks")
    BuiltChunkStorage chunks();
}
