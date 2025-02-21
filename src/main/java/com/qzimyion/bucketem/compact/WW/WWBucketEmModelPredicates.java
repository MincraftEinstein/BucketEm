package com.qzimyion.bucketem.compact.WW;

import com.qzimyion.bucketem.mixin.ItemMixins.EntityBucketItemAccessor;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.frozenblock.wilderwild.entity.Jellyfish;
import net.frozenblock.wilderwild.entity.variant.JellyfishVariant;
import net.frozenblock.wilderwild.registry.WWItems;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.MobBucketItem;

@SuppressWarnings("deprecation")
public class WWBucketEmModelPredicates {

    public static void register(){
        FabricModelPredicateProviderRegistry.register(WWItems.JELLYFISH_BUCKET, new ResourceLocation("age"), ((stack, world, livingEntity, seed) -> {
            Item item = stack.getItem();
            if (!(item instanceof MobBucketItem)) {
                return 0f;
            }
            assert Minecraft.getInstance().level != null;
            Entity entity = ((EntityBucketItemAccessor) item).type().create(Minecraft.getInstance().level);
            if (!(entity instanceof Jellyfish)) {
                return 0f;
            }
            ((Bucketable) entity).loadFromBucketTag(stack.getOrCreateTag());
            return ((Jellyfish) entity).getAge() > 0 ? 1 : 0;
        }));
        FabricModelPredicateProviderRegistry.register(WWItems.JELLYFISH_BUCKET, new ResourceLocation("variant"), (stack, world, holder, seed) -> {
            Item item = stack.getItem();
            if (!(item instanceof MobBucketItem)) {
                return 0f;
            }
            Entity entity = ((EntityBucketItemAccessor) item).type().create(Minecraft.getInstance().level);
            if (!(entity instanceof Jellyfish) || entity == null) {
                return 0f;
            }
            ((Bucketable) entity).loadFromBucketTag(stack.getOrCreateTag());
            ResourceLocation texture = ((Jellyfish) entity).getVariant().texture();
            return texture == JellyfishVariant.PINK.texture() ? 0 : (float) (texture == JellyfishVariant.BLUE.texture() ? 0.1 : texture == JellyfishVariant.LIME.texture() ? 0.2 : texture == JellyfishVariant.RED.texture() ? 0.3 : texture == JellyfishVariant.YELLOW.texture() ? 0.4 : texture == JellyfishVariant.PEARLESCENT_BLUE.texture() ? 0.5 : 0.6);
        });
    }
}
