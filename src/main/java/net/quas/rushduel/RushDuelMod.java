package net.quas.rushduel;

import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.quas.rushduel.block.ModBlocks;
import net.quas.rushduel.item.ModItemTags;
import net.quas.rushduel.item.ModItems;
import net.quas.rushduel.yugioh.data.Card;
import net.quas.rushduel.yugioh.data.CardType;
import net.quas.rushduel.resources.DataManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(RushDuelMod.MOD_ID)
public class RushDuelMod {

	public static final String MOD_ID = "rushduel";
	public static final RandomSource RANDOM_SOURCE = RandomSource.create();
	public static final Logger LOGGER = LoggerFactory.getLogger("RUSHDUEL");

	public RushDuelMod() {
		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

		// Register modded content
		ModItems.register(modEventBus);
		ModBlocks.register(modEventBus);

		// Other registers
		modEventBus.addListener(this::commonSetup);
		MinecraftForge.EVENT_BUS.register(this);
	}

	private void commonSetup(final FMLCommonSetupEvent event) {
	}

	@SubscribeEvent
	public void onServerStarting(ServerStartingEvent event) {
		// Do nothing
	}
}
