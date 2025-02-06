package com.qzimyion.bucketem.compact.WW;

import com.qzimyion.bucketem.mixin.ItemMixins.EntityBucketItemAccessor;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.frozenblock.wilderwild.entity.Jellyfish;
import net.frozenblock.wilderwild.registry.WWItems;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Bucketable;
import net.minecraft.entity.Entity;
import net.minecraft.item.EntityBucketItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

@SuppressWarnings("deprecation")
public class WWBucketEmClient {

    public static void WWBucketemItemPredicateModels(){
        FabricModelPredicateProviderRegistry.register(WWItems.JELLYFISH_BUCKET, new Identifier("age"),
                (stack, clientWorld, livingEntity, i) -> {
            float age = 1;
            if (stack.getNbt() != null && stack.getNbt().contains("age") && stack.getNbt().getInt("age") < 0)
                age = 0;
            return age;
        });
        FabricModelPredicateProviderRegistry.register(WWItems.JELLYFISH_BUCKET, new Identifier("variant"),
                (stack, world, holder, seed) -> {
            Item item = stack.getItem();
            if (!(item instanceof EntityBucketItem)) {
                return 0f;
            }
            Entity entity = ((EntityBucketItemAccessor) item).getEntityType().create(MinecraftClient.getInstance().world);
            if (!(entity instanceof Jellyfish) || entity == null) {
                return 0f;
            }
            ((Bucketable) entity).copyDataFromNbt(stack.getOrCreateNbt());
            return ((Jellyfish) entity).getVariant().hashCode() / 10F;
        });
    }
}
