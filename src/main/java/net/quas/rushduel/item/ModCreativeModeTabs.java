package net.quas.rushduel.item;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.quas.rushduel.RushDuelMod;
import net.quas.rushduel.block.ModBlocks;
import net.quas.rushduel.item.card.Card;
import net.quas.rushduel.item.card.CardRarity;
import net.quas.rushduel.item.card.CardType;
import net.quas.rushduel.item.pack.CardPack;
import net.quas.rushduel.item.pack.StarterDeck;

@Mod.EventBusSubscriber(modid = RushDuelMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModCreativeModeTabs {

	public static CreativeModeTab RUSH_DUEL;
	public static CreativeModeTab RUSH_DUEL_CARDS;
	public static CreativeModeTab RUSH_DUEL_PACKS;

	@SubscribeEvent
	public static void registerCreativeModeTabs(CreativeModeTabEvent.Register event) {
		RUSH_DUEL = event.registerCreativeModeTab(new ResourceLocation(RushDuelMod.MOD_ID, "rush_duel"),
				builder -> builder.icon(() -> new ItemStack(ModItems.VELGEARIUM_INGOT.get())).title(Component.translatable("creativemodetab.rush_duel"))
						.displayItems((parameters, output) -> {
							// Items
							output.accept(ModItems.RAW_VELGEARIUM.get());
							output.accept(ModItems.VELGEARIUM_INGOT.get());
							output.accept(ModItems.DUEL_DISK_SEVENS.get());

							// Blocks
							output.accept(ModBlocks.VELGEARIUM_ORE.get());
							output.accept(ModBlocks.DEEPSLATE_VELGEARIUM_ORE.get());
							output.accept(ModBlocks.RAW_VELGEARIUM_BLOCK.get());
							output.accept(ModBlocks.VELGEARIUM_BLOCK.get());
						}
				)
		);

		RUSH_DUEL_CARDS = event.registerCreativeModeTab(new ResourceLocation(RushDuelMod.MOD_ID, "rush_duel_cards"),
				builder -> builder.icon(() -> new ItemStack(ModItems.CARD.get())).title(Component.translatable("creativemodetab.rush_duel_cards"))
						.displayItems((parameters, output) -> {
							for (Card card : Card.getCards()) {
								if (card.getCardType() == CardType.UNKNOWN) continue;

								ItemStack itemStack = new ItemStack(ModItems.CARD.get());
								CompoundTag tag = itemStack.getOrCreateTag();
								tag.putString(ModItemTags.CARD_ID, card.getId());
								tag.putString(ModItemTags.CARD_RARITY, CardRarity.COMMON.toString());
								output.accept(itemStack);
							}
						}
				)
		);
		RUSH_DUEL_PACKS = event.registerCreativeModeTab(new ResourceLocation(RushDuelMod.MOD_ID, "rush_duel_packs"),
				builder -> builder.icon(() -> new ItemStack(ModItems.STARTER_DECK.get())).title(Component.translatable("creativemodetab.rush_duel_packs"))
						.displayItems((parameters, output) -> {
							for (StarterDeck deck : StarterDeck.getStarterDecks()) {
								ItemStack itemStack = new ItemStack(ModItems.STARTER_DECK.get());
								CompoundTag tag = itemStack.getOrCreateTag();
								tag.putString(ModItemTags.DECK_ID, deck.getId());
								output.accept(itemStack);
							}
							for (CardPack pack : CardPack.getCardPacks()) {
								ItemStack itemStack = new ItemStack(ModItems.CARD_PACK.get());
								CompoundTag tag = itemStack.getOrCreateTag();
								tag.putString(ModItemTags.PACK_ID, pack.getId());
								output.accept(itemStack);
							}
						}
				)
		);
	}
}
