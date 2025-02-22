package com.qzimyion.bucketem.forge;

import com.qzimyion.bucketem.core.registry.ModEvents;
import dev.architectury.platform.forge.EventBuses;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import com.qzimyion.bucketem.BucketEmCommon;

@Mod(BucketEmCommon.MOD_ID)
public final class ExampleModForge {
    public ExampleModForge() {
        // Submit our event bus to let Architectury API register our content on the right time.
        EventBuses.registerModEventBus(BucketEmCommon.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());

        // Run our common setup.
        BucketEmCommon.init();
    }

    @SubscribeEvent
    public static void onPlayerInteract(PlayerInteractEvent.EntityInteract event){
        EntityHitResult hitResult = new EntityHitResult(event.getTarget());
        var result = ModEvents.event(event.getEntity(), event.getLevel(), event.getHand(), event.getTarget(), hitResult);
        if (result != InteractionResult.PASS) {
            event.setCanceled(true);
            event.setCancellationResult(result);
        }
    }
}
