package net.quas.rushduel.world.feature;

import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.quas.rushduel.block.ModBlocks;

import java.util.List;

public class ModOreFeatures {

	public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_VELGEARIUM = ModFeatureUtils.createKey("ore_velgearium");

	public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
		RuleTest stoneReplacables = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
		RuleTest deepslateReplacables = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);

		List<OreConfiguration.TargetBlockState> overworldVelgeariumOres = List.of(
				OreConfiguration.target(stoneReplacables, ModBlocks.VELGEARIUM_ORE.get().defaultBlockState()),
				OreConfiguration.target(deepslateReplacables, ModBlocks.DEEPSLATE_VELGEARIUM_ORE.get().defaultBlockState())
		);

		ModFeatureUtils.register(context, ORE_VELGEARIUM, Feature.ORE, new OreConfiguration(overworldVelgeariumOres, 9));
	}
}
