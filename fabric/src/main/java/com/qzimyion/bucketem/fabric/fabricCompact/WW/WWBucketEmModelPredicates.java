package com.qzimyion.bucketem.fabric.fabricCompact.WW;

import com.qzimyion.bucketem.fabric.mixin.ItemMixins.EntityBucketItemAccessor;
import com.qzimyion.bucketem.platform.ClientHelper;
import net.frozenblock.wilderwild.entity.Jellyfish;
import net.frozenblock.wilderwild.entity.variant.JellyfishVariant;
import net.frozenblock.wilderwild.registry.WWItems;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.MobBucketItem;

public class WWBucketEmModelPredicates {

    public static void register(ClientHelper.ModelPredicates event){
        //==Wilder wild==//
        //Jellyfish variants
        event.register(WWItems.JELLYFISH_BUCKET, new ResourceLocation("variant"), (itemStack, world, holder, seed) -> {
            Item item = itemStack.getItem();
            if (!(item instanceof MobBucketItem)) {
                return 0f;
            }
            assert Minecraft.getInstance().level != null;
            Entity entity = ((EntityBucketItemAccessor) item).type().create(Minecraft.getInstance().level);
            if (!(entity instanceof Jellyfish)) {
                return 0f;
            }
            ((Bucketable) entity).loadFromBucketTag(itemStack.getOrCreateTag());
            ResourceLocation texture = ((Jellyfish) entity).getVariant().texture();
            return texture == JellyfishVariant.PINK.texture() ? 0 : (float) (texture == JellyfishVariant.BLUE.texture() ? 0.1 : texture == JellyfishVariant.LIME.texture() ? 0.2 : texture == JellyfishVariant.RED.texture() ? 0.3 : texture == JellyfishVariant.YELLOW.texture() ? 0.4 : texture == JellyfishVariant.PEARLESCENT_BLUE.texture() ? 0.5 : 0.6);
        });
        //Jellyfish age
        event.register(WWItems.JELLYFISH_BUCKET, new ResourceLocation("age"), (itemStack, clientLevel, livingEntity, i) -> {
            Item item = itemStack.getItem();
            if (!(item instanceof MobBucketItem)) {
                return 0f;
            }
            assert Minecraft.getInstance().level != null;
            Entity entity = ((EntityBucketItemAccessor) item).type().create(Minecraft.getInstance().level);
            if (!(entity instanceof Jellyfish)) {
                return 0f;
            }
            ((Bucketable) entity).loadFromBucketTag(itemStack.getOrCreateTag());
            return ((Jellyfish) entity).getAge() > 0 ? 1 : 0;
        });
    }
}
