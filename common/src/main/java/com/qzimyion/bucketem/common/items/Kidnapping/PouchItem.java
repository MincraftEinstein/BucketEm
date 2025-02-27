package com.qzimyion.bucketem.common.items.Kidnapping;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PouchItem extends Item {
    private static final String TAG_ENTITIES = "Entities";
    private static final int MAX_ENTITIES = 3;
    private static final int BAR_COLOR = Mth.color(0.4F, 0.4F, 1.0F);
    private final EntityType<?> type;

    public PouchItem(Properties properties, EntityType<?> type) {
        super(properties);
        this.type = type;
    }

    public static float getFullnessDisplay(ItemStack itemStack) {
        return (float) getStoredEntityCount(itemStack) / MAX_ENTITIES;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        ItemStack itemStack = player.getItemInHand(interactionHand);
        if (releaseEntities(itemStack, player, level)) {
            this.playDropContentsSound(player);
            player.awardStat(Stats.ITEM_USED.get(this));
            return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide());
        } else {
            return InteractionResultHolder.fail(itemStack);
        }
    }

    private static boolean releaseEntities(ItemStack itemStack, Player player, Level level) {
        CompoundTag compoundTag = itemStack.getOrCreateTag();
        if (!compoundTag.contains(TAG_ENTITIES)) {
            return false;
        }

        ListTag listTag = compoundTag.getList(TAG_ENTITIES, 10);
        for (int i = 0; i < listTag.size(); i++) {
            CompoundTag entityData = listTag.getCompound(i);
            Entity entity = EntityType.loadEntityRecursive(entityData, level, e -> {
                e.setPos(player.getX(), player.getY(), player.getZ());
                return e;
            });
            if (entity != null) {
                level.addFreshEntity(entity);
            }
        }

        itemStack.removeTagKey(TAG_ENTITIES);
        return true;
    }

    @Deprecated
    public static boolean storeEntity(ItemStack itemStack, Entity entity) {
        if (entity.getType() != ((PouchItem) itemStack.getItem()).type) {
            return false;
        }

        CompoundTag compoundTag = itemStack.getOrCreateTag();
        ListTag listTag = compoundTag.getList(TAG_ENTITIES, 10);
        if (listTag.size() >= MAX_ENTITIES) {
            return false;
        }

        CompoundTag entityData = new CompoundTag();
        if (entity.save(entityData)) {
            listTag.add(entityData);
            compoundTag.put(TAG_ENTITIES, listTag);
            entity.discard();
            return true;
        }
        return false;
    }

    private static int getStoredEntityCount(ItemStack itemStack) {
        CompoundTag compoundTag = itemStack.getTag();
        return compoundTag != null && compoundTag.contains(TAG_ENTITIES) ? compoundTag.getList(TAG_ENTITIES, 10).size() : 0;
    }

    @Override
    public boolean isBarVisible(ItemStack itemStack) {
        return getStoredEntityCount(itemStack) > 0;
    }

    @Override
    public int getBarWidth(ItemStack itemStack) {
        return Math.min(1 + 12 * getStoredEntityCount(itemStack) / MAX_ENTITIES, 13);
    }

    @Override
    public int getBarColor(ItemStack itemStack) {
        return BAR_COLOR;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, Level level, List<Component> list, TooltipFlag tooltipFlag) {
        list.add(Component.translatable("item.bucketem.pouch_item.fullness", getStoredEntityCount(itemStack), MAX_ENTITIES).withStyle(ChatFormatting.GRAY));
    }

    private void playDropContentsSound(Player entity) {
        entity.playSound(SoundEvents.BUNDLE_DROP_CONTENTS, 0.8F, 0.8F + entity.level().getRandom().nextFloat() * 0.4F);
    }
}
