package com.qzimyion.bucketem.compact.WW;

import com.qzimyion.bucketem.mixin.ItemMixins.EntityBucketItemAccessor;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.frozenblock.wilderwild.entity.Jellyfish;
import net.frozenblock.wilderwild.entity.variant.JellyfishVariant;
import net.frozenblock.wilderwild.registry.WWItems;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Bucketable;
import net.minecraft.entity.Entity;
import net.minecraft.item.EntityBucketItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

@SuppressWarnings("deprecation")
public class WWBucketEmModelPredicates {

    public static void register(){
        FabricModelPredicateProviderRegistry.register(WWItems.JELLYFISH_BUCKET, new Identifier("age"), ((stack, world, entity, seed) -> {
            if (stack.hasNbt() && stack.getNbt().contains("isBaby")) {
                return stack.getNbt().getBoolean("isBaby") ? 0 : 1;
            }
            return 0;
        }));
        FabricModelPredicateProviderRegistry.register(WWItems.JELLYFISH_BUCKET, new Identifier("variant"), (stack, world, holder, seed) -> {
            Item item = stack.getItem();
            if (!(item instanceof EntityBucketItem)) {
                return 0f;
            }
            Entity entity = ((EntityBucketItemAccessor) item).getEntityType().create(MinecraftClient.getInstance().world);
            if (!(entity instanceof Jellyfish) || entity == null) {
                return 0f;
            }
            ((Bucketable) entity).copyDataFromNbt(stack.getOrCreateNbt());
            Identifier texture = ((Jellyfish) entity).getVariant().texture();
            return texture == JellyfishVariant.PINK.texture() ? 0 : (float) (texture == JellyfishVariant.BLUE.texture() ? 0.1 : texture == JellyfishVariant.LIME.texture() ? 0.2 : texture == JellyfishVariant.RED.texture() ? 0.3 : texture == JellyfishVariant.YELLOW.texture() ? 0.4 : texture == JellyfishVariant.PEARLESCENT_BLUE.texture() ? 0.5 : 0.6);
        });
    }
}
