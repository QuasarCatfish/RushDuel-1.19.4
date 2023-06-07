package net.quas.rushduel.yugioh.data;

public enum CardType {

	UNKNOWN,
	NORMAL,
	EFFECT,
	SPELL,
	TRAP,
	MAXIMUM,
	FUSION;

	public boolean hasText() {
		return this == NORMAL;
	}

	public boolean hasEffect() {
		return this == EFFECT || this == SPELL || this == TRAP || this == MAXIMUM || this == FUSION;
	}

	public boolean isMonster() {
		return this == NORMAL || this == EFFECT || this == MAXIMUM || this == FUSION;
	}
}
