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
	public static final String MOD_NAME = "Rush Duel";

	public static final String RESOURCE_PREFIX = MOD_ID + ":";

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

	@Mod.EventBusSubscriber(modid = RushDuelMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
	public static class ForgeEvents {
		@SubscribeEvent
		public static void addReloadListeners(AddReloadListenerEvent event) {
			RushDuelMod.LOGGER.info("Adding reload listeners.");
			event.addListener(DataManager.CARDS);
			event.addListener(DataManager.CARD_PACKS);
			event.addListener(DataManager.STARTER_DECKS);
		}
	}

	@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
	public static class ClientModEvents {
		@SubscribeEvent
		public static void onClientSetup(FMLClientSetupEvent event) {
			event.enqueueWork(() -> {
				ItemProperties.register(ModItems.CARD.get(),
						new ResourceLocation(MOD_ID, "normal_monster"),
						(stack, level, living, id) -> Card.getCard(stack.getOrCreateTag().getString(ModItemTags.CARD_ID)).getCardType() == CardType.NORMAL ? 1 : 0
				);
				ItemProperties.register(ModItems.CARD.get(),
						new ResourceLocation(MOD_ID, "effect_monster"),
						(stack, level, living, id) -> Card.getCard(stack.getOrCreateTag().getString(ModItemTags.CARD_ID)).getCardType() == CardType.EFFECT ? 1 : 0
				);
				ItemProperties.register(ModItems.CARD.get(),
						new ResourceLocation(MOD_ID, "normal_spell"),
						(stack, level, living, id) -> Card.getCard(stack.getOrCreateTag().getString(ModItemTags.CARD_ID)).getCardType() == CardType.SPELL ? 1 : 0
				);
				ItemProperties.register(ModItems.CARD.get(),
						new ResourceLocation(MOD_ID, "normal_trap"),
						(stack, level, living, id) -> Card.getCard(stack.getOrCreateTag().getString(ModItemTags.CARD_ID)).getCardType() == CardType.TRAP ? 1 : 0
				);
				ItemProperties.register(ModItems.CARD.get(),
						new ResourceLocation(MOD_ID, "maximum_monster"),
						(stack, level, living, id) -> Card.getCard(stack.getOrCreateTag().getString(ModItemTags.CARD_ID)).getCardType() == CardType.MAXIMUM ? 1 : 0
				);
				ItemProperties.register(ModItems.CARD.get(),
						new ResourceLocation(MOD_ID, "fusion_monster"),
						(stack, level, living, id) -> Card.getCard(stack.getOrCreateTag().getString(ModItemTags.CARD_ID)).getCardType() == CardType.FUSION ? 1 : 0
				);
			});
		}
	}
}
