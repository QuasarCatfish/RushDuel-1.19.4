package net.quas.rushduel.item.card;

import net.minecraft.world.item.Rarity;
import org.jetbrains.annotations.NotNull;

public enum CardRarity {
	COMMON,
	RARE,
	SUPER_RARE,
	SUPER_PARALLEL_RARE,
	ULTRA_RARE,
	RUSH_RARE;

	public boolean isFoil() {
		return this == SUPER_PARALLEL_RARE;
	}

	public @NotNull Rarity getRarity() {
		return switch (this) {
			case COMMON -> Rarity.COMMON;
			case RARE -> Rarity.UNCOMMON;
			case SUPER_RARE, SUPER_PARALLEL_RARE -> Rarity.RARE;
			case ULTRA_RARE, RUSH_RARE -> Rarity.EPIC;
		};
	}
}
