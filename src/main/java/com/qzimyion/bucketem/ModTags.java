package com.qzimyion.bucketem;

import net.minecraft.entity.EntityType;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModTags {
    public static class EntityTypeTagsForMod {
        
        public static final TagKey<EntityType<?>> MILKABLE_ENTITY = createTag("milkable_entity");

        private static TagKey<EntityType<?>> createTag(String name) {
            return TagKey.of(RegistryKeys.ENTITY_TYPE, new Identifier(Bucketem.MOD_ID, name));
        }
    }
}
