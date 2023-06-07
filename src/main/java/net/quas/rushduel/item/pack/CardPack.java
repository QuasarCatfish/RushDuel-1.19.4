package net.quas.rushduel.item.pack;

import com.mojang.datafixers.util.Pair;
import net.quas.rushduel.RushDuelMod;
import net.quas.rushduel.item.card.CardRarity;
import net.quas.rushduel.resources.DataManager;
import net.quas.rushduel.util.IHasId;

import java.util.*;

public class CardPack implements IHasId {

	private String packId;
	private int commonCardsPerPack;
	private int rareCardsPerPack;
	private HashMap<CardRarity, String[]> cards = new HashMap<>();

	public static final CardPack EMPTY_PACK = new CardPack();

	@Override
	public String getId() {
		return packId;
	}

	public String getRandomCommon() {
		String[] options = cards.get(CardRarity.COMMON);
		if (options == null || options.length == 0) return null;

		int index = RushDuelMod.RANDOM_SOURCE.nextInt(options.length);
		return options[index];
	}

	public Pair<String, CardRarity> getRandomRare() {
		CardRarity rarity = CardRarity.COMMON;
		List<CardRarity> rarities = cards.keySet().stream().toList();
		while (rarity == CardRarity.COMMON || cards.get(rarity).length == 0) rarity = rarities.get(RushDuelMod.RANDOM_SOURCE.nextInt(rarities.size()));

		String[] options = cards.get(rarity);
		int index = RushDuelMod.RANDOM_SOURCE.nextInt(options.length);
		return Pair.of(options[index], rarity);
	}

	public int getCardsPerPack() {
		return commonCardsPerPack + rareCardsPerPack;
	}

	public int getCommonCardsPerPack() {
		return commonCardsPerPack;
	}

	public int getRareCardsPerPack() {
		return rareCardsPerPack;
	}

	public int getCardsInSet() {
		TreeSet<String> unique = new TreeSet<>();
		cards.values().forEach(arr -> unique.addAll(List.of(arr)));
		return unique.size();
	}

	public static CardPack getCardPack(String packId) {
		return DataManager.CARD_PACKS.getOrDefault(packId, EMPTY_PACK);
	}

	public static Collection<CardPack> getCardPacks() {
		return DataManager.CARD_PACKS.values();
	}

	@Override
	public String toString() {
		StringJoiner sj = new StringJoiner(",\n");
		sj.setEmptyValue("No cards found");
		cards.forEach((rarity, list) -> sj.add(rarity.toString() + "=" + Arrays.toString(list)));
		return String.format("CardPack[packId=\"%s\", cards={\n%s\n}]", packId, sj.toString());
	}
}
