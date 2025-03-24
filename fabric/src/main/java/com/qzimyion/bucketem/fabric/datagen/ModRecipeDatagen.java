package com.qzimyion.bucketem.fabric.datagen;

import com.qzimyion.bucketem.core.registry.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;

import java.util.concurrent.CompletableFuture;

public class ModRecipeDatagen extends FabricRecipeProvider {


    public ModRecipeDatagen(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void buildRecipes(RecipeOutput recipeOutput) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.SLIME_BALL, 3).requires(ModItems.SLIME_BOTTLE.get()).unlockedBy("has_bottle", RecipeProvider.has(Items.SLIME_BALL)).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.MAGMA_CREAM, 3).requires(ModItems.MAGMA_CUBE_BOTTLE.get()).unlockedBy("has_bottle", RecipeProvider.has(Items.MAGMA_CREAM)).save(recipeOutput);
    }
}
