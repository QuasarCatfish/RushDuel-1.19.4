package net.quas.rushduel.compat;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IModIngredientRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.ISubtypeRegistration;
import net.minecraft.resources.ResourceLocation;
import net.quas.rushduel.RushDuelMod;
import net.quas.rushduel.item.ModItems;
import org.jetbrains.annotations.NotNull;

@JeiPlugin
public class JEIRushDuelModPlugin implements IModPlugin {

	@Override
	public @NotNull ResourceLocation getPluginUid() {
		return new ResourceLocation(RushDuelMod.MOD_ID, "jei_plugin");
	}

	@Override
	public void registerIngredients(@NotNull IModIngredientRegistration registration) {
		IModPlugin.super.registerIngredients(registration);
	}

	@Override
	public void registerItemSubtypes(@NotNull ISubtypeRegistration registration) {
		registration.useNbtForSubtypes(
				ModItems.CARD.get(),
				ModItems.CARD_PACK.get(),
				ModItems.STARTER_DECK.get()
		);
	}
}
