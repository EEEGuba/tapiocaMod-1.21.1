package com.eeeguba.tapiocamod.datagen;


import com.eeeguba.tapiocamod.Tapiocaplushie;
import com.eeeguba.tapiocamod.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        List<ItemLike> TUNGSTEN_SMELTABLES = List.of(ModItems.RAW_TUNGSTEN);
//                ModBlocks.TUNGSTEN_ORE, ModBlocks.TUNGSTEN_DEEPSLATE_ORE);

//        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.TUNGSTEN_BLOCK.get())
//                .pattern("BBB")
//                .pattern("BBB")
//                .pattern("BBB")
//                .define('B', ModItems.TUNGSTEN.get())
//                .unlockedBy("has_tungsten", has(ModItems.TUNGSTEN)).save(recipeOutput);

//        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.TUNGSTEN.get(), 9)
//                .requires(ModBlocks.TUNGSTEN_BLOCK)
//                .unlockedBy("has_tungsten_block", has(ModBlocks.TUNGSTEN_BLOCK)).save(recipeOutput);
//
//        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.TUNGSTEN.get(), 18)
//                .requires(ModBlocks.MAGIC_BLOCK)
//                .unlockedBy("has_magic_block", has(ModBlocks.MAGIC_BLOCK))
//                .save(recipeOutput, "tutorialmod:tungsten_from_magic_block");
//
        oreSmelting(recipeOutput, TUNGSTEN_SMELTABLES, RecipeCategory.MISC, ModItems.TUNGSTEN.get(), 0.25f, 200, "tungsten");
        oreBlasting(recipeOutput, TUNGSTEN_SMELTABLES, RecipeCategory.MISC, ModItems.TUNGSTEN.get(), 0.25f, 100, "tungsten");

//        stairBuilder(ModBlocks.TUNGSTEN_STAIRS.get(), Ingredient.of(ModItems.TUNGSTEN)).group("tungsten")
//                .unlockedBy("has_tungsten", has(ModItems.TUNGSTEN)).save(recipeOutput);
//        slab(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.TUNGSTEN_SLAB.get(), ModItems.TUNGSTEN.get());
//
//        buttonBuilder(ModBlocks.TUNGSTEN_BUTTON.get(), Ingredient.of(ModItems.TUNGSTEN.get())).group("tungsten")
//                .unlockedBy("has_tungsten", has(ModItems.TUNGSTEN.get())).save(recipeOutput);
//        pressurePlate(recipeOutput, ModBlocks.TUNGSTEN_PRESSURE_PLATE.get(), ModItems.TUNGSTEN.get());
//
//        fenceBuilder(ModBlocks.TUNGSTEN_FENCE.get(), Ingredient.of(ModItems.TUNGSTEN.get())).group("tungsten")
//                .unlockedBy("has_tungsten", has(ModItems.TUNGSTEN.get())).save(recipeOutput);
//        fenceGateBuilder(ModBlocks.TUNGSTEN_FENCE_GATE.get(), Ingredient.of(ModItems.TUNGSTEN.get())).group("tungsten")
//                .unlockedBy("has_tungsten", has(ModItems.TUNGSTEN.get())).save(recipeOutput);
//        wall(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.TUNGSTEN_WALL.get(), ModItems.TUNGSTEN.get());
//
//        doorBuilder(ModBlocks.TUNGSTEN_DOOR.get(), Ingredient.of(ModItems.TUNGSTEN.get())).group("tungsten")
//                .unlockedBy("has_tungsten", has(ModItems.TUNGSTEN.get())).save(recipeOutput);
//        trapdoorBuilder(ModBlocks.TUNGSTEN_TRAPDOOR.get(), Ingredient.of(ModItems.TUNGSTEN.get())).group("tungsten")
//                .unlockedBy("has_tungsten", has(ModItems.TUNGSTEN.get())).save(recipeOutput);
    }

    protected static void oreSmelting(RecipeOutput recipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult,
                                      float pExperience, int pCookingTIme, String pGroup) {
        oreCooking(recipeOutput, RecipeSerializer.SMELTING_RECIPE, SmeltingRecipe::new, pIngredients, pCategory, pResult,
                pExperience, pCookingTIme, pGroup, "_from_smelting");
    }

    protected static void oreBlasting(RecipeOutput recipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult,
                                      float pExperience, int pCookingTime, String pGroup) {
        oreCooking(recipeOutput, RecipeSerializer.BLASTING_RECIPE, BlastingRecipe::new, pIngredients, pCategory, pResult,
                pExperience, pCookingTime, pGroup, "_from_blasting");
    }

    protected static <T extends AbstractCookingRecipe> void oreCooking(RecipeOutput recipeOutput, RecipeSerializer<T> pCookingSerializer, AbstractCookingRecipe.Factory<T> factory,
                                                                       List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup, String pRecipeName) {
        for(ItemLike itemlike : pIngredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), pCategory, pResult, pExperience, pCookingTime, pCookingSerializer, factory).group(pGroup).unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(recipeOutput, Tapiocaplushie.MODID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike));
        }
    }
}
