package net.quas.rushduel.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.quas.rushduel.RushDuelMod;

import java.util.concurrent.CompletableFuture;

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

}
