package com.eeeguba.tapiocamod.datagen;


import com.eeeguba.tapiocamod.Tapioca;
import com.eeeguba.tapiocamod.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static com.ibm.icu.lang.UCharacter.GraphemeClusterBreak.T;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
//        List<ItemLike> TUNGSTEN_SMELTABLES = List.of(ModItems.RAW_TUNGSTEN);
//                ModBlocks.TUNGSTEN_ORE, ModBlocks.TUNGSTEN_DEEPSLATE_ORE);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.HEAD_BOW.get())
                .pattern("   ")
                .pattern("WSW")
                .pattern("   ")
                .define('W', Items.CYAN_WOOL)
                .define('S', Items.STRING)
                .unlockedBy("has_wool", has(Items.WHITE_WOOL)).save(recipeOutput);
    }
//        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.TUNGSTEN.get(), 9)
//                .requires(ModBlocks.TUNGSTEN_BLOCK)
//                .unlockedBy("has_tungsten_block", has(ModBlocks.TUNGSTEN_BLOCK)).save(recipeOutput);
//
//        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.TUNGSTEN.get(), 18)
//                .requires(ModBlocks.MAGIC_BLOCK)
//                .unlockedBy("has_magic_block", has(ModBlocks.MAGIC_BLOCK))
//                .save(recipeOutput, "tutorialmod:tungsten_from_magic_block");
//
//        oreSmelting(recipeOutput, TUNGSTEN_SMELTABLES, RecipeCategory.MISC, ModItems.TUNGSTEN.get(), 0.25f, 200, "tungsten");
//        oreBlasting(recipeOutput, TUNGSTEN_SMELTABLES, RecipeCategory.MISC, ModItems.TUNGSTEN.get(), 0.25f, 100, "tungsten");
//
//
//    }
//
//    protected static void oreSmelting(RecipeOutput recipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult,
//                                      float pExperience, int pCookingTIme, String pGroup) {
//        oreCooking(recipeOutput, RecipeSerializer.SMELTING_RECIPE, SmeltingRecipe::new, pIngredients, pCategory, pResult,
//                pExperience, pCookingTIme, pGroup, "_from_smelting");
//    }
//
//    protected static void oreBlasting(RecipeOutput recipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult,
//                                      float pExperience, int pCookingTime, String pGroup) {
//        oreCooking(recipeOutput, RecipeSerializer.BLASTING_RECIPE, BlastingRecipe::new, pIngredients, pCategory, pResult,
//                pExperience, pCookingTime, pGroup, "_from_blasting");
//    }

//    protected static <T extends AbstractCookingRecipe> void oreCooking(RecipeOutput recipeOutput, RecipeSerializer<T> pCookingSerializer, AbstractCookingRecipe.Factory<T> factory,
//                                                                       List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup, String pRecipeName) {
//        for(ItemLike itemlike : pIngredients) {
//            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), pCategory, pResult, pExperience, pCookingTime, pCookingSerializer, factory).group(pGroup).unlockedBy(getHasName(itemlike), has(itemlike))
//                    .save(recipeOutput, Tapioca.MODID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike));
//        }
//    }
}
