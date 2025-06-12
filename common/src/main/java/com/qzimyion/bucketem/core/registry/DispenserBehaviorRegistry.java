package com.qzimyion.bucketem.core.registry;

import com.qzimyion.bucketem.common.dispenser.behaviors.ContainedEntityItemBehavior;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.world.level.block.DispenserBlock;

import static com.qzimyion.bucketem.core.registry.ModItems.*;

public class DispenserBehaviorRegistry {

    public static void registerDispenserBehavior() {
        //Buckets
        DispenserBlock.registerBehavior(STRIDER_BUCKET.get(), new DefaultDispenseItemBehavior());
        DispenserBlock.registerBehavior(SQUID_BUCKET.get(), new DefaultDispenseItemBehavior());
        DispenserBlock.registerBehavior(GLOW_SQUID_BUCKET.get(), new DefaultDispenseItemBehavior());
        DispenserBlock.registerBehavior(TEMPERATE_FROG_BUCKET.get(), new DefaultDispenseItemBehavior());
        DispenserBlock.registerBehavior(TROPICAL_FROG_BUCKET.get(), new DefaultDispenseItemBehavior());
        DispenserBlock.registerBehavior(TUNDRA_FROG_BUCKET.get(), new DefaultDispenseItemBehavior());
        DispenserBlock.registerBehavior(TURTLE_BUCKET.get(), new DefaultDispenseItemBehavior());

        //Books
        DispenserBlock.registerBehavior(ALLAY_POSSESSED_BOOK.get(), new ContainedEntityItemBehavior());
        DispenserBlock.registerBehavior(VEX_POSSESSED_BOOK.get(), new ContainedEntityItemBehavior());

        //Bottles
        DispenserBlock.registerBehavior(BEE_BOTTLE.get(), new ContainedEntityItemBehavior());
        DispenserBlock.registerBehavior(SILVERFISH_BOTTLE.get(), new ContainedEntityItemBehavior());
        DispenserBlock.registerBehavior(ENDERMITE_BOTTLE.get(), new ContainedEntityItemBehavior());
        DispenserBlock.registerBehavior(SLIME_BOTTLE.get(), new ContainedEntityItemBehavior());
        DispenserBlock.registerBehavior(MAGMA_CUBE_BOTTLE.get(), new ContainedEntityItemBehavior());
    }
}
