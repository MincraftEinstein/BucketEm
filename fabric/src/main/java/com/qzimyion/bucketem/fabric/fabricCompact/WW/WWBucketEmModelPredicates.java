package com.qzimyion.bucketem.fabric.fabricCompact.WW;

//import com.qzimyion.bucketem.core.mixin.ItemMixins.EntityBucketItemAccessor;
//import com.qzimyion.bucketem.platform.ClientHelper;
//import net.frozenblock.wilderwild.entity.Jellyfish;
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.multiplayer.ClientLevel;
//import net.minecraft.core.component.DataComponents;
//import net.minecraft.world.entity.Entity;
//import net.minecraft.world.entity.LivingEntity;
//import net.minecraft.world.item.Item;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.world.item.MobBucketItem;
//import net.minecraft.world.item.component.CustomData;

public class WWBucketEmModelPredicates {

//    private static Jellyfish getJellyfishlRef(ItemStack itemStack, ClientLevel level, LivingEntity holder, int seed) {
//        Item item = itemStack.getItem();
//        if (!(item instanceof MobBucketItem)) {
//            return null;
//        }
//        assert Minecraft.getInstance().level != null;
//        Entity entity = ((EntityBucketItemAccessor) item).type().create(Minecraft.getInstance().level);
//        if (!(entity instanceof Jellyfish jellyfish)) {
//            return null;
//        }
//        CustomData data = itemStack.getOrDefault(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY);
//        jellyfish.loadFromBucketTag(data.copyTag());
//        return jellyfish;
//    }

//    public static void register(ClientHelper.ModelPredicates event){
        //==Wilder wild==//
        //Jellyfish variants
//        event.register(WWItems.JELLYFISH_BUCKET, ResourceLocation.parse("variant"), (itemStack, world, holder, seed) -> {
//            Jellyfish jellyfish = getJellyfishlRef(itemStack, world, holder, seed);
//            if (jellyfish == null) {
//                return 0f;
//            }
//            ResourceKey<JellyfishVariant> texture = jellyfish.;
//            return texture == JellyfishVariants.PINK ? 0f : 1f;
//        });
//        //Jellyfish age
//        event.register(WWItems.JELLYFISH_BUCKET, ResourceLocation.parse("age"), (itemStack, clientLevel, livingEntity, i) -> {
//            AtomicBoolean ageBL = new AtomicBoolean(false);
//            CustomData.update(DataComponents.BUCKET_ENTITY_DATA, itemStack, nbt -> {
//                nbt.get("age");
//                ageBL.set(nbt.getInt("age") < 0);
//            });
//            float age = 1;
//            if (itemStack.has(DataComponents.BUCKET_ENTITY_DATA) && ageBL.get())
//                age = 0;
//            return age;
//        });
//    }
}
