package com.qzimyion.bucketem.mixin.accessors;

import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.block.FluidRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@SuppressWarnings("UnusedReturnValue")
@Mixin(BlockRenderManager.class)
public interface IFluidRendererAccessor {

    @Accessor("fluidRenderer")
    FluidRenderer fluidRenderer();
}
