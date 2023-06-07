package net.quas.rushduel.event;

import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.quas.rushduel.RushDuelMod;
import net.quas.rushduel.item.ModItemTags;
import net.quas.rushduel.item.ModItems;
import net.quas.rushduel.yugioh.data.Card;
import net.quas.rushduel.yugioh.data.CardType;

@Mod.EventBusSubscriber(modid = RushDuelMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEvents {

	@SubscribeEvent
	public static void onClientSetup(FMLClientSetupEvent event) {
		event.enqueueWork(() -> {
			ItemProperties.register(ModItems.CARD.get(),
					new ResourceLocation(RushDuelMod.MOD_ID, "normal_monster"),
					(stack, level, living, id) -> Card.getCard(stack.getOrCreateTag().getString(ModItemTags.CARD_ID)).getCardType() == CardType.NORMAL ? 1 : 0
			);
			ItemProperties.register(ModItems.CARD.get(),
					new ResourceLocation(RushDuelMod.MOD_ID, "effect_monster"),
					(stack, level, living, id) -> Card.getCard(stack.getOrCreateTag().getString(ModItemTags.CARD_ID)).getCardType() == CardType.EFFECT ? 1 : 0
			);
			ItemProperties.register(ModItems.CARD.get(),
					new ResourceLocation(RushDuelMod.MOD_ID, "normal_spell"),
					(stack, level, living, id) -> Card.getCard(stack.getOrCreateTag().getString(ModItemTags.CARD_ID)).getCardType() == CardType.SPELL ? 1 : 0
			);
			ItemProperties.register(ModItems.CARD.get(),
					new ResourceLocation(RushDuelMod.MOD_ID, "normal_trap"),
					(stack, level, living, id) -> Card.getCard(stack.getOrCreateTag().getString(ModItemTags.CARD_ID)).getCardType() == CardType.TRAP ? 1 : 0
			);
			ItemProperties.register(ModItems.CARD.get(),
					new ResourceLocation(RushDuelMod.MOD_ID, "maximum_monster"),
					(stack, level, living, id) -> Card.getCard(stack.getOrCreateTag().getString(ModItemTags.CARD_ID)).getCardType() == CardType.MAXIMUM ? 1 : 0
			);
			ItemProperties.register(ModItems.CARD.get(),
					new ResourceLocation(RushDuelMod.MOD_ID, "fusion_monster"),
					(stack, level, living, id) -> Card.getCard(stack.getOrCreateTag().getString(ModItemTags.CARD_ID)).getCardType() == CardType.FUSION ? 1 : 0
			);
		});
	}
}
