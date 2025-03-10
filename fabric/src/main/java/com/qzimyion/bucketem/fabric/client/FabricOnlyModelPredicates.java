package com.qzimyion.bucketem.fabric.client;

import com.qzimyion.bucketem.fabric.mixin.EntityBucketItemAccessor;
import com.qzimyion.bucketem.platform.ClientHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.MobBucketItem;

import static net.minecraft.world.item.Items.AXOLOTL_BUCKET;

public class FabricOnlyModelPredicates {

    public static void register(ClientHelper.ModelPredicates event){
        event.register(AXOLOTL_BUCKET, new ResourceLocation("variant"), (itemStack, world, holder, seed) -> {
            Item item = itemStack.getItem();
            if (!(item instanceof MobBucketItem)) {
                return 0f;
            }
            assert Minecraft.getInstance().level != null;
            Entity entity = ((EntityBucketItemAccessor) item).type().create(Minecraft.getInstance().level);
            if (!(entity instanceof Axolotl)) {
                return 0f;
            }
            ((Bucketable) entity).loadFromBucketTag(itemStack.getOrCreateTag());
            return ((Axolotl) entity).getVariant().ordinal() / 10f;
        });
    }
}
