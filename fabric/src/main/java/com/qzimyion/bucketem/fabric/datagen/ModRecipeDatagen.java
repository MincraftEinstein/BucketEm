package com.qzimyion.bucketem.fabric.datagen;

import com.qzimyion.bucketem.core.registry.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.Items;
import java.util.function.Consumer;

public class ModRecipeDatagen extends FabricRecipeProvider {
    public ModRecipeDatagen(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void buildRecipes(Consumer<FinishedRecipe> exporter) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.SLIME_BALL, 3).requires(ModItems.SLIME_BOTTLE.get()).unlockedBy("has_bottle", RecipeProvider.has(Items.SLIME_BALL)).save(exporter);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.MAGMA_CREAM, 3).requires(ModItems.MAGMA_CUBE_BOTTLE.get()).unlockedBy("has_bottle", RecipeProvider.has(Items.MAGMA_CREAM)).save(exporter);
    }
}
