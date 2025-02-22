package com.qzimyion.bucketem.fabric;

import com.qzimyion.bucketem.core.registry.ModEvents;
import net.fabricmc.api.ModInitializer;

import com.qzimyion.bucketem.BucketEmCommon;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.Nullable;

public final class ExampleModFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        BucketEmCommon.init();
        UseEntityCallback.EVENT.register(ModEvents::event);
    }
}
