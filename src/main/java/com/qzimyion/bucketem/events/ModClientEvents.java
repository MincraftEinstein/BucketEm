package com.qzimyion.bucketem.events;

import com.qzimyion.bucketem.mixin.accessors.IFluidRendererAccessor;
import com.qzimyion.bucketem.mixin.accessors.IWorldRendererAccessor;
import com.qzimyion.bucketem.potions.StatusEffects.ModStatusEffectsRegistry;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.fluid.Fluids;

public class ModClientEvents {
    private static void updateAllChunks() {
        if (((IWorldRendererAccessor) MinecraftClient.getInstance().worldRenderer).chunks().chunks != null) {
            int length = ((IWorldRendererAccessor) MinecraftClient.getInstance().worldRenderer).chunks().chunks.length;
            for (int i = 0; i < length; i++) {
                ((IWorldRendererAccessor) MinecraftClient.getInstance().worldRenderer).chunks().chunks[i].needsRebuild = true;
            }
        }
    }
    private static boolean previousMagmaVision = false;

    public static void register(){
        ClientTickEvents.START_CLIENT_TICK.register(minecraftClient -> {
            if (minecraftClient.player == null) return;
            ((IFluidRendererAccessor) minecraftClient.getBlockRenderManager()).fluidRenderer();
            if (minecraftClient.player.hasStatusEffect(ModStatusEffectsRegistry.MAGMA_VISION)){
                if (!previousMagmaVision){
                    BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(), Fluids.FLOWING_LAVA, Fluids.LAVA);
                    updateAllChunks();
                }
            } else {
                if (previousMagmaVision){
                    BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getSolid(), Fluids.FLOWING_LAVA, Fluids.LAVA);
                    updateAllChunks();
                }
            }
            previousMagmaVision = minecraftClient.player.hasStatusEffect(ModStatusEffectsRegistry.MAGMA_VISION);
        });
    }
}
