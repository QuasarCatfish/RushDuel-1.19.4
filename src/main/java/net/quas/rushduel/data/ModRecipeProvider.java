package net.quas.rushduel.data;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.quas.rushduel.RushDuelMod;
import net.quas.rushduel.block.ModBlocks;
import net.quas.rushduel.item.ModItems;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {

	public ModRecipeProvider(PackOutput output) {
		super(output);
	}

	@Override
	protected void buildRecipes(@NotNull Consumer<FinishedRecipe> consumer) {
		oreSmelting(consumer, List.of(ModItems.RAW_VELGEARIUM.get()), RecipeCategory.MISC, ModItems.VELGEARIUM_INGOT.get(), 0.7f, 200, "velgearium_ingot");
		oreBlasting(consumer, List.of(ModItems.RAW_VELGEARIUM.get()), RecipeCategory.MISC, ModItems.VELGEARIUM_INGOT.get(), 0.7f, 100, "velgearium_ingot");

		oreSmelting(consumer, List.of(ModBlocks.VELGEARIUM_ORE.get()), RecipeCategory.MISC, ModItems.VELGEARIUM_INGOT.get(), 0.7f, 200, "velgearium_ingot");
		oreBlasting(consumer, List.of(ModBlocks.VELGEARIUM_ORE.get()), RecipeCategory.MISC, ModItems.VELGEARIUM_INGOT.get(), 0.7f, 100, "velgearium_ingot");

		oreSmelting(consumer, List.of(ModBlocks.DEEPSLATE_VELGEARIUM_ORE.get()), RecipeCategory.MISC, ModItems.VELGEARIUM_INGOT.get(), 0.7f, 200, "velgearium_ingot");
		oreBlasting(consumer, List.of(ModBlocks.DEEPSLATE_VELGEARIUM_ORE.get()), RecipeCategory.MISC, ModItems.VELGEARIUM_INGOT.get(), 0.7f, 100, "velgearium_ingot");

		nineBlockStorageRecipes(consumer, RecipeCategory.MISC, ModItems.VELGEARIUM_INGOT.get(), RecipeCategory.BUILDING_BLOCKS, ModBlocks.VELGEARIUM_BLOCK.get());
		nineBlockStorageRecipes(consumer, RecipeCategory.MISC, ModItems.RAW_VELGEARIUM.get(), RecipeCategory.BUILDING_BLOCKS, ModBlocks.RAW_VELGEARIUM_BLOCK.get());

		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.DUEL_DISK_SEVENS.get())
				.define('V', ModItems.VELGEARIUM_INGOT.get())
				.define('R', Items.REDSTONE_BLOCK)
				.pattern("VVV")
				.pattern(" RV")
				.pattern(" V ")
				.unlockedBy(getHasName(ModItems.VELGEARIUM_INGOT.get()), has(ModItems.VELGEARIUM_INGOT.get()))
				.save(consumer, new ResourceLocation(RushDuelMod.MOD_ID, getSimpleRecipeName(ModItems.DUEL_DISK_SEVENS.get())));
	}

	// Crafting Recipes
	protected static void nineBlockStorageRecipes(@NotNull Consumer<FinishedRecipe> consumer, @NotNull RecipeCategory itemRecipeCategory, ItemLike item, @NotNull RecipeCategory blockRecipeCategory, ItemLike block) {
		nineBlockStorageRecipes(consumer, itemRecipeCategory, item, blockRecipeCategory, block, getSimpleRecipeName(block), (String) null, getSimpleRecipeName(item), (String) null);
	}

	protected static void nineBlockStorageRecipes(@NotNull Consumer<FinishedRecipe> consumer, @NotNull RecipeCategory itemRecipeCategory, ItemLike item, @NotNull RecipeCategory blockRecipeCategory, ItemLike block, String blockRecipeName, @Nullable String blockGroup, String itemRecipeName, @Nullable String itemGroup) {
		ShapelessRecipeBuilder.shapeless(itemRecipeCategory, item, 9)
				.requires(block)
				.group(itemGroup)
				.unlockedBy(getHasName(block), has(block))
				.save(consumer, new ResourceLocation(RushDuelMod.MOD_ID, itemRecipeName));
		ShapedRecipeBuilder.shaped(blockRecipeCategory, block)
				.define('#', item)
				.pattern("###")
				.pattern("###")
				.pattern("###")
				.group(blockGroup)
				.unlockedBy(getHasName(item), has(item))
				.save(consumer, new ResourceLocation(RushDuelMod.MOD_ID, blockRecipeName));
	}

	// Smelting Recipes
	protected static void oreCooking(@NotNull Consumer<FinishedRecipe> consumer, @NotNull RecipeSerializer<? extends AbstractCookingRecipe> recipeType, List<ItemLike> inputs, @NotNull RecipeCategory recipeCategory, @NotNull ItemLike output, float xp, int timeTicks, @NotNull String itemGroup, @NotNull String recipeSourceName) {
		for (ItemLike input : inputs) {
			SimpleCookingRecipeBuilder.generic(Ingredient.of(input), recipeCategory, output, xp, timeTicks, recipeType)
					.group(itemGroup)
					.unlockedBy(getHasName(input), has(input))
					.save(consumer, new ResourceLocation(RushDuelMod.MOD_ID, getItemName(output) + recipeSourceName + "_" + getItemName(input)));
		}
	}

	protected static void oreBlasting(@NotNull Consumer<FinishedRecipe> consumer, List<ItemLike> inputs, @NotNull RecipeCategory recipeCategory, @NotNull ItemLike output, float xp, int timeTicks, @NotNull String recipeSourceName) {
		oreCooking(consumer, RecipeSerializer.BLASTING_RECIPE, inputs, recipeCategory, output, xp, timeTicks, recipeSourceName, "_from_blasting");
	}

	protected static void oreSmelting(@NotNull Consumer<FinishedRecipe> p_250654_, List<ItemLike> p_250172_, @NotNull RecipeCategory p_250588_, @NotNull ItemLike p_251868_, float p_250789_, int p_252144_, @NotNull String p_251687_) {
		oreCooking(p_250654_, RecipeSerializer.SMELTING_RECIPE, p_250172_, p_250588_, p_251868_, p_250789_, p_252144_, p_251687_, "_from_smelting");
	}
}
