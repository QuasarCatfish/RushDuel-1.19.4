package net.quas.rushduel.item;

import com.mojang.datafixers.util.Pair;
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
import net.quas.rushduel.RushDuelMod;
import net.quas.rushduel.item.ModItemTags;
import net.quas.rushduel.item.ModItems;
import net.quas.rushduel.yugioh.data.CardRarity;
import net.quas.rushduel.yugioh.data.CardPack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.TreeSet;

public class CardPackItem extends Item {

	public CardPackItem(Properties properties) {
		super(properties);
	}

	@Override
	public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);
		player.getCooldowns().addCooldown(this, 20);

		if (stack.hasTag() && !level.isClientSide()) {
			stack.shrink(1);

			assert stack.getTag() != null;
			CardPack pack = CardPack.getCardPack(stack.getTag().getString(ModItemTags.PACK_ID));
			RushDuelMod.LOGGER.debug(pack.toString());

			// Drop cards
			TreeSet<String> unique = new TreeSet<>();
			for (int q = 0; q < pack.getCommonCardsPerPack(); q++) {
				String card = pack.getRandomCommon();
				while (unique.contains(card)) card = pack.getRandomCommon();
				unique.add(card);

				ItemStack cardStack = new ItemStack(ModItems.CARD.get());
				CompoundTag tag = cardStack.getOrCreateTag();
				tag.putString(ModItemTags.CARD_ID, card);
				tag.putString(ModItemTags.CARD_RARITY, CardRarity.COMMON.toString());
				player.drop(cardStack, true);
			}
			for (int q = 0; q < pack.getRareCardsPerPack(); q++) {
				Pair<String, CardRarity> card = pack.getRandomRare();
				while (unique.contains(card.getFirst())) card = pack.getRandomRare();
				unique.add(card.getFirst());

				ItemStack cardStack = new ItemStack(ModItems.CARD.get());
				CompoundTag tag = cardStack.getOrCreateTag();
				tag.putString(ModItemTags.CARD_ID, card.getFirst());
				tag.putString(ModItemTags.CARD_RARITY, card.getSecond().toString());
				player.drop(cardStack, true);
			}
		}

		return super.use(level, player, hand);
	}

	@Override
	public @NotNull Rarity getRarity(@NotNull ItemStack itemStack) {
		return Rarity.RARE;
	}

	@Override
	public @NotNull Component getName(@NotNull ItemStack itemStack) {
		if (itemStack.hasTag()) {
			assert itemStack.getTag() != null;
			String packId = itemStack.getTag().getString(ModItemTags.PACK_ID);
			return Component.translatable("rushduel.card_pack." + packId + ".name");
		}
		return super.getName(itemStack);
	}

	@Override
	public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag isAdvanced) {
		if (itemStack.hasTag()) {
			assert itemStack.getTag() != null;

			CardPack pack = CardPack.getCardPack(itemStack.getTag().getString(ModItemTags.PACK_ID));
			if (pack.getCardsPerPack() > 0) tooltipComponents.add(Component.literal("Contains " + pack.getCardsPerPack() + " Cards."));
		}
		super.appendHoverText(itemStack, level, tooltipComponents, isAdvanced);
	}
}
