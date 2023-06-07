package net.quas.rushduel.item.card;

import net.quas.rushduel.resources.DataManager;
import net.quas.rushduel.util.IHasId;

import java.util.Collection;

public class Card implements IHasId {
	private String cardId;
	private CardType cardType;
	private MonsterAttribute attribute;
	private MonsterRace race;
	private int level;
	private int atk;
	private int def;
	private EffectType effectType;
	private int maximumAtk;
	private MaximumType maximumType;
	private String maximumLeft;
	private String maximumCenter;
	private String maximumRight;
	private String[] fusionMaterials;

	public static final Card DUMMY_CARD = new Card("dummy_card", CardType.UNKNOWN);

	public Card(String cardId, CardType cardType) {
		this.cardId = cardId;
		this.cardType = cardType;
	}

	@Override
	public String getId() {
		return cardId;
	}

	public CardType getCardType() {
		return cardType;
	}

	public MonsterAttribute getAttribute() {
		return attribute;
	}

	public MonsterRace getRace() {
		return race;
	}

	public int getLevel() {
		return level;
	}

	public int getAtk() {
		return atk;
	}

	public int getDef() {
		return def;
	}

	public EffectType getEffectType() {
		if (effectType == null) return EffectType.EFFECT;
		return effectType;
	}

	public boolean isMaximumCenter() {
		return maximumType == MaximumType.CENTER;
	}

	public int getMaximumAtk() {
		return maximumAtk;
	}

	public String getMaximumLeft() {
		return maximumLeft;
	}

	public String getMaximumRight() {
		return maximumRight;
	}

	public String[] getFusionMaterials() {
		return fusionMaterials;
	}

	public static Card getCard(String cardId) {
		return DataManager.CARDS.getOrDefault(cardId, DUMMY_CARD);
	}

	public static Collection<Card> getCards() {
		return DataManager.CARDS.values();
	}

	@Override
	public String toString() {
		return String.format("Card[id=%s, type=%s, attribute=%s, race=%s, level=%d, atk=%d, def=%d]", cardId, cardType, attribute, race, level, atk, def);
	}
}
