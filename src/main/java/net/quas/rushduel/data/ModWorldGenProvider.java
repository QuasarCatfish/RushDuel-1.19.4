package net.quas.rushduel.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.quas.rushduel.RushDuelMod;
import net.quas.rushduel.world.feature.ModFeatureUtils;
import net.quas.rushduel.world.placement.ModPlacementUtils;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ModWorldGenProvider extends DatapackBuiltinEntriesProvider {

	public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
			.add(Registries.CONFIGURED_FEATURE, ModFeatureUtils::bootstrap)
			.add(Registries.PLACED_FEATURE, ModPlacementUtils::bootstrap);

	public ModWorldGenProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
		super(output, registries, BUILDER, Set.of(RushDuelMod.MOD_ID));
	}
}
