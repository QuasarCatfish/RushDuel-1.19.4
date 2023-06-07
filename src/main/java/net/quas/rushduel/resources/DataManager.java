package net.quas.rushduel.resources;

import net.quas.rushduel.yugioh.data.Card;
import net.quas.rushduel.yugioh.data.CardPack;
import net.quas.rushduel.yugioh.data.StarterDeck;

public class DataManager {

	public static final SimpleJsonDataManager<Card> CARDS = new SimpleJsonDataManager<>("cards", Card.class);
	public static final SimpleJsonDataManager<CardPack> CARD_PACKS = new SimpleJsonDataManager<>("packs", CardPack.class);
	public static final SimpleJsonDataManager<StarterDeck> STARTER_DECKS = new SimpleJsonDataManager<>("starterdecks", StarterDeck.class);
}
