package com.qzimyion.bucketem.client;

import com.qzimyion.bucketem.core.mixin.ItemMixins.EntityBucketItemAccessor;
import com.qzimyion.bucketem.core.registry.ModDataComponents;
import com.qzimyion.bucketem.platform.ClientHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MobBucketItem;
import net.minecraft.world.item.component.CustomData;

import java.util.concurrent.atomic.AtomicBoolean;

import static com.qzimyion.bucketem.core.registry.ModItems.*;
import static net.minecraft.world.item.Items.*;

public class ModItemModelPredicates {
    private static final Minecraft MINECRAFT = Minecraft.getInstance();
    private static Axolotl getAxolotlRef(ItemStack itemStack, ClientLevel level, LivingEntity holder, int seed) {
        Item item = itemStack.getItem();
        if (!(item instanceof MobBucketItem)) {
            return null;
        }
        Entity entity = ((EntityBucketItemAccessor) item).type().create(MINECRAFT.level);
        if (!(entity instanceof Axolotl axolotl)) {
            return null;
        }
        CustomData data = itemStack.getOrDefault(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY);
        axolotl.loadFromBucketTag(data.copyTag());
        return axolotl;
    }

    public static void registerModelProperties(ClientHelper.ModelPredicates event){
        //==Turtle==//
        event.register(TURTLE_BUCKET.get(), ResourceLocation.parse("age"), (itemStack, clientLevel, livingEntity, i) -> {
            AtomicBoolean ageBL = new AtomicBoolean(false);
            CustomData.update(DataComponents.BUCKET_ENTITY_DATA, itemStack, nbt -> {
                nbt.get("Age");
                ageBL.set(nbt.getInt("Age") < 0);
            });
            float age = 1;
            if (itemStack.has(DataComponents.BUCKET_ENTITY_DATA) && ageBL.get())
                age = 0;
            return age;
        });

        //==Slime==//
        event.register(SLIME_BOTTLE.get(), ResourceLocation.parse("slime_chunk"), (itemStack, clientLevel, livingEntity, i) -> {
            if (livingEntity instanceof Player) {
                boolean inSlimeChunk = Boolean.TRUE.equals(itemStack.get(ModDataComponents.SLIME_CHUNK_COMPONENT.get()));
                return inSlimeChunk ? 1 : 0;
            } else {
                return 0;
            }
        });
        //==Axolotls==//
        event.register(AXOLOTL_BUCKET, ResourceLocation.parse("age"), (itemStack, clientLevel, livingEntity, i) -> {
            AtomicBoolean ageBL = new AtomicBoolean(false);
            CustomData.update(DataComponents.BUCKET_ENTITY_DATA, itemStack, nbt -> {
                nbt.get("Age");
                ageBL.set(nbt.getInt("Age") < 0);
            });
            float age = 1;
            if (itemStack.has(DataComponents.BUCKET_ENTITY_DATA) && ageBL.get())
                age = 0;
            return age;
        });
        event.register(AXOLOTL_BUCKET, ResourceLocation.parse("variant"), (itemStack, world, holder, seed) -> {
            Axolotl axolotl = getAxolotlRef(itemStack, world, holder, seed);
            if (axolotl == null) {
                return 0f;
            }
            return axolotl.getVariant().ordinal() / 10f;
        });
        /*
        //==Bee stuff==//
        //Age
        event.register(BEE_BOTTLE.get(), ResourceLocation.parse("age"), (itemStack, clientLevel, livingEntity, i) -> {
            float age = 1;
            if (itemStack.getTag() != null && itemStack.getTag().contains("Age") && itemStack.getTag().getInt("Age") < 0)
                age = 0;
            return age;
        });
        //Nectar
        event.register(BEE_BOTTLE.get(), ResourceLocation.parse("nectar"), (itemStack, clientLevel, livingEntity, i) -> {
            if (livingEntity instanceof Bee) {
                CompoundTag nbt = itemStack.getOrCreateTag();
                boolean nectar = nbt.contains("HasNectar") && nbt.getBoolean("HasNectar");
                return nectar ? 1 : 0;
            } else {
                return 1;
            }
        });
        //Sting
        event.register(BEE_BOTTLE.get(), ResourceLocation.parse("sting"), (itemStack, clientLevel, livingEntity, i) -> {
            if (livingEntity instanceof Bee) {
                CompoundTag nbt = itemStack.getOrCreateTag();
                boolean nectar = nbt.contains("HasStung") && nbt.getBoolean("HasStung");
                return nectar ? 1 : 0;
            } else {
                return 1;
            }
        });
        //Anger
        event.register(BEE_BOTTLE.get(), ResourceLocation.parse("anger"), (itemStack, world, holder, seed) -> {
            float anger = 1;
            if (itemStack.getTag() != null && itemStack.getTag().contains("Anger") && itemStack.getTag().getInt("Anger") < 0)
                anger = 0;
            return anger;
        });
*/
    }
}
