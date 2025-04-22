package com.qzimyion.bucketem.neoforge;

import com.qzimyion.bucketem.BucketEmCommon;
import com.qzimyion.bucketem.core.registry.DispenserBehaviorRegistry;
import com.qzimyion.bucketem.core.registry.ModEvents;
import com.qzimyion.bucketem.platform.neoforge.PlatformHelperImpl;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.phys.EntityHitResult;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;

import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

@Mod(BucketEmCommon.MOD_ID)
@EventBusSubscriber(modid = BucketEmCommon.MOD_ID)
public final class BucketemNeoForge {

    public BucketemNeoForge(IEventBus bus) {
        PlatformHelperImpl.startRegistering(bus);
        BucketEmCommon.init();
        bus.addListener(this::dispenserReg);
//        if (PlatformHelper.getPhysicalSide().isClient()){
//            BucketEmCommonClient.init();
//        }
    }

    public void dispenserReg(final FMLCommonSetupEvent event) {
        DispenserBehaviorRegistry.registerDispenserBehavior();
    }

    @SubscribeEvent
    public static void onPlayerInteract(PlayerInteractEvent.EntityInteract event){
        InteractionResult result = ModEvents.EntityEvents(event.getEntity(), event.getLevel(), event.getHand(), event.getTarget(), new EntityHitResult(event.getTarget()));
        if (result == InteractionResult.SUCCESS){
            event.setCanceled(true);
            event.setCancellationResult(InteractionResult.SUCCESS);
        }
    }
}
