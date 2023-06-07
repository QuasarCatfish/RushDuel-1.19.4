package net.quas.rushduel.item.pack;

import net.minecraft.nbt.CompoundTag;
import net.quas.rushduel.item.ModItemTags;
import net.quas.rushduel.item.card.Card;
import net.quas.rushduel.item.card.CardRarity;
import net.quas.rushduel.resources.DataManager;
import net.quas.rushduel.util.IHasId;

import java.util.Collection;

public class StarterDeck implements IHasId {

	public static class CardRarityHolder {

		private String cardId = "dummy_card";
		private CardRarity rarity = CardRarity.COMMON;
		private int count = 1;

		public String getCardId() {
			return cardId;
		}

		public Card getCard() {
			return Card.getCard(cardId);
		}

		public CardRarity getRarity() {
			return rarity;
		}

		public int getCount() {
			return count;
		}
	}


	private String deckId;
	private CardRarityHolder[] cards = new CardRarityHolder[0];
	private String[] decks = new String[0];
	private String[] packs = new String[0];

	public static final StarterDeck EMPTY_DECK = new StarterDeck();

	@Override
	public String getId() {
		return deckId;
	}

	public CardRarityHolder[] getCards() {
		return cards;
	}

	public int getCardCount() {
		int count = 0;
		for (CardRarityHolder crh : cards) count += crh.getCount();
		return count;
	}

	public String[] getDecks() {
		return decks;
	}

	public String[] getPacks() {
		return packs;
	}

	public static StarterDeck getStarterDeck(String deckId) {
		return DataManager.STARTER_DECKS.getOrDefault(deckId, EMPTY_DECK);
	}

	public static Collection<StarterDeck> getStarterDecks() {
		return DataManager.STARTER_DECKS.values();
	}
}
