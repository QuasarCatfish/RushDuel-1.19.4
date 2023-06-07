package net.quas.rushduel.data;

import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;
import net.quas.rushduel.RushDuelMod;
import net.quas.rushduel.block.ModBlocks;
import net.quas.rushduel.item.ModItemTags;
import net.quas.rushduel.item.ModItems;
import net.quas.rushduel.world.feature.ModFeatureUtils;
import net.quas.rushduel.world.placement.ModPlacementUtils;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

@Mod.EventBusSubscriber(modid = RushDuelMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {

	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {
		DataGenerator gen = event.getGenerator();
		PackOutput output = event.getGenerator().getPackOutput();
		ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
		CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

		gen.addProvider(true, new ModRecipeProvider(output));
		gen.addProvider(true, ModLootTableProvider.create(output));
		gen.addProvider(true, new ModBlockStateProvider(output, existingFileHelper));
		gen.addProvider(true, new ModItemModelProvider(output, existingFileHelper));
		gen.addProvider(event.includeServer(), new ModWorldGenProvider(output, lookupProvider));
	}

	public static class ModBlockLootTables extends BlockLootSubProvider {

		public ModBlockLootTables() {
			super(Set.of(), FeatureFlags.REGISTRY.allFlags());
		}

		@Override
		protected void generate() {
			add(ModBlocks.VELGEARIUM_ORE.get(), block -> createOreDrop(ModBlocks.VELGEARIUM_ORE.get(), ModItems.RAW_VELGEARIUM.get()));
			add(ModBlocks.DEEPSLATE_VELGEARIUM_ORE.get(), block -> createOreDrop(ModBlocks.DEEPSLATE_VELGEARIUM_ORE.get(), ModItems.RAW_VELGEARIUM.get()));
			dropSelf(ModBlocks.RAW_VELGEARIUM_BLOCK.get());
			dropSelf(ModBlocks.VELGEARIUM_BLOCK.get());
		}

		@Override
		protected Iterable<Block> getKnownBlocks() {
			return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
		}
	}

	public static class ModBlockStateProvider extends BlockStateProvider {

		public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
			super(output, RushDuelMod.MOD_ID, exFileHelper);
		}

		@Override
		protected void registerStatesAndModels() {
			blockWithItem(ModBlocks.VELGEARIUM_ORE);
			blockWithItem(ModBlocks.DEEPSLATE_VELGEARIUM_ORE);
			blockWithItem(ModBlocks.RAW_VELGEARIUM_BLOCK);
			blockWithItem(ModBlocks.VELGEARIUM_BLOCK);
		}

		private void blockWithItem(RegistryObject<Block> block) {
			simpleBlockWithItem(block.get(), cubeAll(block.get()));
		}
	}

	public static class ModItemModelProvider extends ItemModelProvider {

		public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
			super(output, RushDuelMod.MOD_ID, existingFileHelper);
		}

		@Override
		protected void registerModels() {
			simpleItem(ModItems.RAW_VELGEARIUM);
			simpleItem(ModItems.VELGEARIUM_INGOT);
			simpleItem(ModItems.STARTER_DECK);
			simpleItem(ModItems.CARD_PACK);

			simpleName("card_normal");
			simpleName("card_effect");
			simpleName("card_maximum");
			simpleName("card_fusion");
			simpleName("card_spell");
			simpleName("card_trap");
		}

		private ItemModelBuilder simpleName(String name) {
			return withExistingParent(name, new ResourceLocation("item/generated"))
					.texture("layer0", new ResourceLocation(RushDuelMod.MOD_ID, "item/" + name));
		}

		private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
			return withExistingParent(item.getId().getPath(), new ResourceLocation("item/generated"))
					.texture("layer0", new ResourceLocation(RushDuelMod.MOD_ID, "item/" + item.getId().getPath()));
		}

		private ItemModelBuilder handheldItem(RegistryObject<Item> item) {
			return withExistingParent(item.getId().getPath(), new ResourceLocation("item/handheld"))
					.texture("layer0", new ResourceLocation(RushDuelMod.MOD_ID, "item/" + item.getId().getPath()));
		}
	}

	public static class ModLootTableProvider {

		public static LootTableProvider create(PackOutput output) {
			return new LootTableProvider(output, Set.of(), List.of(new LootTableProvider.SubProviderEntry(ModBlockLootTables::new, LootContextParamSets.BLOCK)));
		}
	}

	public static class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {

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
			nineBlockStorageRecipes(consumer, itemRecipeCategory, item, blockRecipeCategory, block, getSimpleRecipeName(block), (String)null, getSimpleRecipeName(item), (String)null);
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
			for(ItemLike input : inputs) {
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

	public static class ModWorldGenProvider extends DatapackBuiltinEntriesProvider {

		public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
				.add(Registries.CONFIGURED_FEATURE, ModFeatureUtils::bootstrap)
				.add(Registries.PLACED_FEATURE, ModPlacementUtils::bootstrap);

		public ModWorldGenProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
			super(output, registries, BUILDER, Set.of(RushDuelMod.MOD_ID));
		}
	}
}
