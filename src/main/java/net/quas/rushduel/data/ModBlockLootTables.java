package net.quas.rushduel.data;

import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;
import net.quas.rushduel.block.ModBlocks;
import net.quas.rushduel.item.ModItems;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class ModBlockLootTables extends BlockLootSubProvider {

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
	protected @NotNull Iterable<Block> getKnownBlocks() {
		return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
	}
}
