package net.quas.rushduel.event;

import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.quas.rushduel.RushDuelMod;
import net.quas.rushduel.resources.DataManager;

@Mod.EventBusSubscriber(modid = RushDuelMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeEvents {

	@SubscribeEvent
	public static void addReloadListeners(AddReloadListenerEvent event) {
		RushDuelMod.LOGGER.info("Adding reload listeners.");
		event.addListener(DataManager.CARDS);
		event.addListener(DataManager.CARD_PACKS);
		event.addListener(DataManager.STARTER_DECKS);
	}
}
