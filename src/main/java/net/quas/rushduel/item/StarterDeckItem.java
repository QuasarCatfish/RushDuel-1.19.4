package net.quas.rushduel.item;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.quas.rushduel.yugioh.data.StarterDeck;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class StarterDeckItem extends Item {

	public StarterDeckItem(Properties properties) {
		super(properties);
	}

	@Override
	public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);
		player.getCooldowns().addCooldown(this, 40);

		if (stack.hasTag() && !level.isClientSide()) {
			stack.shrink(1);

			assert stack.getTag() != null;
			StarterDeck deck = StarterDeck.getStarterDeck(stack.getTag().getString(ModItemTags.DECK_ID));

			// Drop cards
			for (StarterDeck.CardRarityHolder crh : deck.getCards()) {
				ItemStack cardStack = new ItemStack(ModItems.CARD.get(), crh.getCount());
				CompoundTag tag = cardStack.getOrCreateTag();
				tag.putString(ModItemTags.CARD_ID, crh.getCardId());
				tag.putString(ModItemTags.CARD_RARITY, crh.getRarity().toString());
				player.drop(cardStack, true);
			}

			// Drop decks
			for (String d : deck.getDecks()) {
				ItemStack deckStack = new ItemStack(ModItems.STARTER_DECK.get());
				CompoundTag tag = deckStack.getOrCreateTag();
				tag.putString(ModItemTags.DECK_ID, d);
				player.drop(deckStack, true);
			}

			// Drop packs
			// TODO
		}

		return super.use(level, player, hand);
	}

	@Override
	public @NotNull Rarity getRarity(@NotNull ItemStack itemStack) {
		return Rarity.EPIC;
	}

	@Override
	public @NotNull Component getName(@NotNull ItemStack itemStack) {
		if (itemStack.hasTag()) {
			assert itemStack.getTag() != null;
			String deckId = itemStack.getTag().getString(ModItemTags.DECK_ID);
			return Component.translatable("rushduel.starterdeck." + deckId + ".name");
		}
		return super.getName(itemStack);
	}

	@Override
	public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag isAdvanced) {
		if (itemStack.hasTag()) {
			assert itemStack.getTag() != null;

			StarterDeck deck = StarterDeck.getStarterDeck(itemStack.getTag().getString(ModItemTags.DECK_ID));
			if (deck.getCardCount() > 0) tooltipComponents.add(Component.literal("Contains " + deck.getCardCount() + " Cards."));
			if (deck.getDecks().length > 0) tooltipComponents.add(Component.literal("Contains " + deck.getDecks().length + " Starter Decks."));
		}
		super.appendHoverText(itemStack, level, tooltipComponents, isAdvanced);
	}
}
