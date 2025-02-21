package com.qzimyion.bucketem;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

public class ModTags {
    public static class EntityTypeTagsForMod {

        public static final TagKey<EntityType<?>> MILKABLE_ENTITY = createTag("milkable_entity");

        private static TagKey<EntityType<?>> createTag(String name) {
            return TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation(Bucketem.MOD_ID, name));
        }
    }
}
