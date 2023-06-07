package net.quas.rushduel.block;


import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.quas.rushduel.RushDuelMod;
import net.quas.rushduel.item.ModItems;

import java.util.function.Supplier;

public class ModBlocks {

	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, RushDuelMod.MOD_ID);

	public static final RegistryObject<Block> VELGEARIUM_ORE = registerBlock("velgearium_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.of(Material.STONE).strength(3f, 3f).requiresCorrectToolForDrops()));
	public static final RegistryObject<Block> DEEPSLATE_VELGEARIUM_ORE = registerBlock("deepslate_velgearium_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(VELGEARIUM_ORE.get()).strength(4.5f, 3f).color(MaterialColor.DEEPSLATE).sound(SoundType.DEEPSLATE)));
	public static final RegistryObject<Block> RAW_VELGEARIUM_BLOCK = registerBlock("raw_velgearium_block", () -> new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.RAW_IRON).strength(5f, 6f).requiresCorrectToolForDrops()));
	public static final RegistryObject<Block> VELGEARIUM_BLOCK = registerBlock("velgearium_block", () -> new Block(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.METAL).strength(5f, 6f).requiresCorrectToolForDrops().sound(SoundType.METAL)));

	private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
		RegistryObject<T> ret = BLOCKS.register(name, block);
		registerBlockItem(name, ret);
		return ret;
	}

	private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
		return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
	}

	public static void register(IEventBus eventBus) {
		BLOCKS.register(eventBus);
	}
}
