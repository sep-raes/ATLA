package net.banaan.atla.datagen;

import net.banaan.atla.Atla;
import net.banaan.atla.block.ModBlocks;
import net.banaan.atla.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.List;
import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    private static final List<ItemLike> ROASTED_BANANA = List.of(ModItems.BANANA.get());

    public ModRecipeProvider(PackOutput output) {
        super(output);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        smelting(consumer, ROASTED_BANANA, RecipeCategory.MISC, ModItems.ROASTED_BANANA.get(), 0.25f, 200, "sapphire");
        smoking(consumer, ROASTED_BANANA, RecipeCategory.MISC, ModItems.ROASTED_BANANA.get(), 0.25f, 100, "sapphire");

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.FRUIT_PIE.get())
                .pattern("#u#")
                .pattern("ABM")
                .pattern("WoW")
                .define('#', Items.SUGAR)
                .define('u', Items.MILK_BUCKET)
                .define('A', Items.MELON_SLICE)
                .define('B', ModItems.BANANA.get())
                .define('M', Items.APPLE)
                .define('W', Items.WHEAT)
                .define('o', Items.EGG)
                .unlockedBy(getHasName(ModItems.BANANA.get()), has(ModItems.BANANA.get()))
                .save(consumer);

        //ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.SAPPHIRE.get(), 9)
        //        .requires(ModBlocks.SAPPHIRE_BLOCK.get())
        //        .unlockedBy(getHasName(ModBlocks.SAPPHIRE_BLOCK.get()), has(ModBlocks.SAPPHIRE_BLOCK.get()))
        //        .save(pWriter);
    }





    protected static void smelting(Consumer<FinishedRecipe> recipe, List<ItemLike> ingredients, RecipeCategory category, ItemLike result, float experience, int cookingTime, String group) {
        cooking(recipe, RecipeSerializer.SMELTING_RECIPE, ingredients, category, result, experience, cookingTime, group, "_from_smelting");
    }

    protected static void blasting(Consumer<FinishedRecipe> recipe, List<ItemLike> ingredients, RecipeCategory category, ItemLike result, float experience, int cookingTime, String group) {
        cooking(recipe, RecipeSerializer.BLASTING_RECIPE, ingredients, category, result, experience, cookingTime, group, "_from_blasting");
    }

    protected static void smoking(Consumer<FinishedRecipe> recipe, List<ItemLike> ingredients, RecipeCategory category, ItemLike result, float experience, int cookingTime, String group) {
        cooking(recipe, RecipeSerializer.SMOKING_RECIPE, ingredients, category, result, experience, cookingTime, group, "_from_smoking");
    }

    protected static void cooking(Consumer<FinishedRecipe> pFinishedRecipeConsumer, RecipeSerializer<? extends AbstractCookingRecipe> pCookingSerializer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup, String pRecipeName) {
        for(ItemLike itemlike : pIngredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), pCategory, pResult,
                            pExperience, pCookingTime, pCookingSerializer)
                    .group(pGroup).unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(pFinishedRecipeConsumer,  Atla.MODID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike));
        }
    }
}