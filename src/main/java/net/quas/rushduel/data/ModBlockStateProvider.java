package net.quas.rushduel.data;

import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import net.quas.rushduel.RushDuelMod;
import net.quas.rushduel.block.ModBlocks;

public class ModBlockStateProvider extends BlockStateProvider {

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
