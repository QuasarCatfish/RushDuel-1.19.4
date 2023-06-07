package net.quas.rushduel.world.placement;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.quas.rushduel.RushDuelMod;

import java.util.List;

public class ModPlacementUtils {

	public static void bootstrap(BootstapContext<PlacedFeature> context) {
		ModOrePlacements.bootstrap(context);
	}

	public static ResourceKey<PlacedFeature> createKey(String name) {
		return ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(RushDuelMod.MOD_ID, name));
	}

	public static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> holder, List<PlacementModifier> modifiers) {
		context.register(key, new PlacedFeature(holder, List.copyOf(modifiers)));
	}

	public static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> holder, PlacementModifier... modifiers) {
		register(context, key, holder, List.of(modifiers));
	}
}
