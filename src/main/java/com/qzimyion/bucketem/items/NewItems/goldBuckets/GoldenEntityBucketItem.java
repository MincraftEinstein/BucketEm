package com.qzimyion.bucketem.items.NewItems.goldBuckets;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.*;
import net.minecraft.entity.passive.TropicalFishEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class GoldenEntityBucketItem extends BucketItem {
    private final EntityType<?> entityType;
    private final SoundEvent emptyingSound;
    public static final String NBT_TAG = "EntityAmount";

    public GoldenEntityBucketItem(Fluid fluid, EntityType<?> type, SoundEvent emptyingSound, Settings settings) {
        super(fluid, settings);
        this.entityType = type;
        this.emptyingSound = emptyingSound;
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {


        return super.useOnEntity(stack, user, entity, hand);
    }

    @Override
    public String getTranslationKey() {
        return this.getOrCreateTranslationKey();
    }

    public void onEmptied(@Nullable PlayerEntity player, World world, ItemStack stack, BlockPos pos) {
        var fluidLevel = stack.getOrCreateNbt().getInt("FluidLevel");
        var entityAmount = stack.getOrCreateNbt().getInt("EntityAmount");
        if (world instanceof ServerWorld && fluidLevel > 0 && entityAmount > 0) {
            this.spawnEntity((ServerWorld)world, stack, pos);
            world.emitGameEvent(player, GameEvent.ENTITY_PLACE, pos);
            stack.getOrCreateNbt().putInt("FluidLevel", fluidLevel - 1);
            stack.getOrCreateNbt().putInt("EntityAmount", entityAmount - 1);
            assert player != null;
        }
    }

    private void spawnEntity(ServerWorld world, ItemStack stack, BlockPos pos) {
        var fluidLevel = stack.getOrCreateNbt().getInt("FluidLevel");
        var entityAmount = stack.getOrCreateNbt().getInt("EntityAmount");
        Entity entity = this.entityType.spawnFromItemStack(world, stack, null, pos, SpawnReason.BUCKET, true, false);
        if (entity instanceof Bucketable bucketable && fluidLevel > 0 && entityAmount > 0) {
            bucketable.copyDataFromNbt(stack.getOrCreateNbt());
            bucketable.setFromBucket(true);
            stack.getOrCreateNbt().putInt("FluidLevel", fluidLevel - 1);
            stack.getOrCreateNbt().putInt("EntityAmount", entityAmount - 1);
        }

    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (this.entityType == EntityType.TROPICAL_FISH) {
            NbtCompound nbtCompound = stack.getNbt();
            if (nbtCompound != null && nbtCompound.contains("GoldenBucketVariantTag", 3)) {
                int i = nbtCompound.getInt("GoldenBucketVariantTag");
                Formatting[] formattings = new Formatting[]{Formatting.ITALIC, Formatting.GRAY};
                String string = "color.minecraft." + TropicalFishEntity.getBaseDyeColor(i);
                String string2 = "color.minecraft." + TropicalFishEntity.getPatternDyeColor(i);

                for (int j = 0; j < TropicalFishEntity.COMMON_VARIANTS.size(); ++j) {
                    if (i == TropicalFishEntity.COMMON_VARIANTS.get(j).getId()) {
                        tooltip.add(Text.translatable(TropicalFishEntity.getToolTipForVariant(j)).formatted(formattings));
                        return;
                    }
                }
                tooltip.add(TropicalFishEntity.getVariety(i).getText().copyContentOnly().formatted(formattings));
                MutableText mutableText = Text.translatable(string);
                if (!string.equals(string2)) {
                    mutableText.append(", ").append(Text.translatable(string2));
                }

                mutableText.formatted(formattings);
                tooltip.add(mutableText);
            }
        }
    }
}
