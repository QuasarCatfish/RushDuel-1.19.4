package net.quas.rushduel.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.quas.rushduel.item.ModItemTags;
import net.quas.rushduel.yugioh.data.Card;
import net.quas.rushduel.yugioh.data.CardRarity;
import net.quas.rushduel.yugioh.data.CardType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CardItem extends Item {

	public CardItem(Properties properties) {
		super(properties);
	}

	@Override
	public boolean isFoil(@NotNull ItemStack itemStack) {
		if (itemStack.hasTag()) {
			assert itemStack.getTag() != null;
			if (itemStack.getTag().contains(ModItemTags.CARD_RARITY)) {
				CardRarity rarity = CardRarity.valueOf(itemStack.getTag().getString(ModItemTags.CARD_RARITY));
				return rarity.isFoil();
			}
		}
		return super.isFoil(itemStack);
	}

	@Override
	public @NotNull Rarity getRarity(@NotNull ItemStack itemStack) {
		if (itemStack.hasTag()) {
			assert itemStack.getTag() != null;
			if (itemStack.getTag().contains(ModItemTags.CARD_RARITY)) {
				CardRarity rarity = CardRarity.valueOf(itemStack.getTag().getString(ModItemTags.CARD_RARITY));
				return rarity.getRarity();
			}
		}
		return super.getRarity(itemStack);
	}

	@Override
	public @NotNull Component getName(@NotNull ItemStack itemStack) {
		if (itemStack.hasTag()) {
			assert itemStack.getTag() != null;
			String cardId = itemStack.getTag().getString(ModItemTags.CARD_ID);
			return Component.translatable("rushduel.card." + cardId + ".name");
		}
		return super.getName(itemStack);
	}

	@Override
	public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag isAdvanced) {
		if (itemStack.hasTag()) {
			assert itemStack.getTag() != null;
			Card card = Card.getCard(itemStack.getTag().getString(ModItemTags.CARD_ID));

			tooltipComponents.add(Component.translatable("rushduel.cardtype." + card.getCardType().toString().toLowerCase()));

			if (card.isMaximumCenter()) {
				tooltipComponents.add(
						MutableComponent.create(new TranslatableContents("rushduel.keyword.maximum_materials", null, new Object[] {
								Component.translatable("rushduel.card." + card.getMaximumLeft() + ".name"),
								Component.translatable("rushduel.card." + card.getMaximumRight() + ".name")
						}))
				);
			}

			if (card.getCardType() == CardType.FUSION) {
				Object[] components = new Object[card.getFusionMaterials().length];
				for (int q = 0; q < components.length; q++) {
					components[q] = Component.translatable("rushduel.card." + card.getFusionMaterials()[q] + ".name");
				}

				tooltipComponents.add(MutableComponent.create(new TranslatableContents("rushduel.keyword.fusion_materials_" + components.length, null, components)));
			}

			if (card.getCardType().hasEffect()) {
				// [REQUIREMENT] ...
				tooltipComponents.add(
						MutableComponent.create(new TranslatableContents("rushduel.keyword.requirement", null, new Object[] {
								Component.translatable("rushduel.card." + card.getId() + ".requirement")
						}))
				);

				// [EFFECT] ...
				String effectTranslate = switch (card.getEffectType()) {
					case CONTINUOUS -> "continuous_effect";
					case MULTICHOICE -> "multichoice_effect";
					default -> "effect";
				};

				tooltipComponents.add(
						MutableComponent.create(new TranslatableContents("rushduel.keyword." + effectTranslate, null, new Object[] {
								Component.translatable("rushduel.card." + card.getId() + ".effect")
						}))
				);
			}

			if (card.getCardType().hasText()) {
				tooltipComponents.add(Component.translatable("rushduel.card." + card.getId() + ".text"));
			}

			if (card.getCardType().isMonster()) {
				// Maximum ATK
				if (card.isMaximumCenter()) {
					tooltipComponents.add(
							MutableComponent.create(new TranslatableContents("rushduel.keyword.maximum_atk", null, new Object[] {
									Component.literal(Integer.toString(card.getMaximumAtk()))
							})).withStyle(ChatFormatting.GOLD)
					);
				}

				// ATK and DEF
				tooltipComponents.add(
						MutableComponent.create(new TranslatableContents("rushduel.keyword.atk", null, new Object[] {
								Component.literal(Integer.toString(card.getAtk()))
						})).withStyle(ChatFormatting.RED)
								.append(Component.literal("   "))
								.append(MutableComponent.create(new TranslatableContents("rushduel.keyword.def", null, new Object[] {
										Component.literal(Integer.toString(card.getDef()))
								})).withStyle(ChatFormatting.BLUE)
						)
				);
			}
		}

		super.appendHoverText(itemStack, level, tooltipComponents, isAdvanced);
	}
}
