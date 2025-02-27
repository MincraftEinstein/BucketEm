package com.qzimyion.bucketem.forge;

import com.qzimyion.bucketem.client.BucketEmCommonClient;
import com.qzimyion.bucketem.forge.mixin.EntityBucketItemAccessor;
import com.qzimyion.bucketem.core.registry.DispenserBehaviorRegistry;
import com.qzimyion.bucketem.core.registry.ModEvents;
import com.qzimyion.bucketem.platform.ClientHelper;
import com.qzimyion.bucketem.platform.PlatformHelper;
import dev.architectury.platform.forge.EventBuses;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.MobBucketItem;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import com.qzimyion.bucketem.BucketEmCommon;

import static net.minecraft.world.item.Items.AXOLOTL_BUCKET;

@SuppressWarnings("removal")
@Mod(BucketEmCommon.MOD_ID)
@Mod.EventBusSubscriber(modid = BucketEmCommon.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class BucketemForge {
    public BucketemForge() {
        EventBuses.registerModEventBus(BucketEmCommon.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        BucketEmCommon.init();
        if (PlatformHelper.getPhysicalSide().isClient()){
            BucketEmCommonClient.init();
            ClientHelper.addModelPredicatesRegistration(BucketemForge::axolotlVariantEvent);
        }
    }

    public static void axolotlVariantEvent(ClientHelper.ModelPredicates event) {
        event.register(AXOLOTL_BUCKET, new ResourceLocation("variant"), (itemStack, world, holder, seed) -> {
            Item item = itemStack.getItem();
            if (!(item instanceof MobBucketItem)) {
                return 0f;
            }
            assert Minecraft.getInstance().level != null;
            Entity entity = ((EntityBucketItemAccessor) item).type().get().create(Minecraft.getInstance().level);
            if (!(entity instanceof Axolotl)) {
                return 0f;
            }
            ((Bucketable) entity).loadFromBucketTag(itemStack.getOrCreateTag());
            return ((Axolotl) entity).getVariant().ordinal() / 10f;
        });
    }

    @SubscribeEvent
    public static void dispenserReg(FMLCommonSetupEvent event) {
        event.enqueueWork(DispenserBehaviorRegistry::registerDispenserBehavior);
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
