package net.quas.rushduel.world.placement;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;
import net.quas.rushduel.world.feature.ModOreFeatures;

import java.util.List;

public class ModOrePlacements {

	public static final ResourceKey<PlacedFeature> ORE_VELGEARIUM = ModPlacementUtils.createKey("ore_velgearium");

	private static List<PlacementModifier> orePlacement(PlacementModifier p_195347_, PlacementModifier p_195348_) {
		return List.of(p_195347_, InSquarePlacement.spread(), p_195348_, BiomeFilter.biome());
	}

	private static List<PlacementModifier> commonOrePlacement(int veinsPerChunk, PlacementModifier modifier) {
		return orePlacement(CountPlacement.of(veinsPerChunk), modifier);
	}

	private static List<PlacementModifier> rareOrePlacement(int count, PlacementModifier modifier) {
		return orePlacement(RarityFilter.onAverageOnceEvery(count), modifier);
	}

	public static void bootstrap(BootstapContext<PlacedFeature> context) {
		HolderGetter<ConfiguredFeature<?, ?>> holderGetter = context.lookup(Registries.CONFIGURED_FEATURE);

		Holder<ConfiguredFeature<?, ?>> featureVelgearium = holderGetter.getOrThrow(ModOreFeatures.ORE_VELGEARIUM);
		ModPlacementUtils.register(context, ORE_VELGEARIUM, featureVelgearium, commonOrePlacement(20, HeightRangePlacement.triangle(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(64))));
	}
}
