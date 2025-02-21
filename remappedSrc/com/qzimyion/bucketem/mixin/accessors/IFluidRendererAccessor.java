package com.qzimyion.bucketem.mixin.accessors;

import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.LiquidBlockRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@SuppressWarnings("UnusedReturnValue")
@Mixin(BlockRenderDispatcher.class)
public interface IFluidRendererAccessor {

    @Accessor("fluidRenderer")
    LiquidBlockRenderer fluidRenderer();
}
