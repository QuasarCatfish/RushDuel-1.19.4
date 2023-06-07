package net.quas.rushduel.data;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import net.quas.rushduel.RushDuelMod;
import net.quas.rushduel.item.ModItems;

public class ModItemModelProvider extends ItemModelProvider {

	public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
		super(output, RushDuelMod.MOD_ID, existingFileHelper);
	}

	@Override
	protected void registerModels() {
		simpleItem(ModItems.RAW_VELGEARIUM);
		simpleItem(ModItems.VELGEARIUM_INGOT);
		simpleItem(ModItems.STARTER_DECK);
		simpleItem(ModItems.CARD_PACK);

		simpleName("card_normal");
		simpleName("card_effect");
		simpleName("card_maximum");
		simpleName("card_fusion");
		simpleName("card_spell");
		simpleName("card_trap");
	}

	private ItemModelBuilder simpleName(String name) {
		return withExistingParent(name, new ResourceLocation("item/generated"))
				.texture("layer0", new ResourceLocation(RushDuelMod.MOD_ID, "item/" + name));
	}

	private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
		return withExistingParent(item.getId().getPath(), new ResourceLocation("item/generated"))
				.texture("layer0", new ResourceLocation(RushDuelMod.MOD_ID, "item/" + item.getId().getPath()));
	}

	private ItemModelBuilder handheldItem(RegistryObject<Item> item) {
		return withExistingParent(item.getId().getPath(), new ResourceLocation("item/handheld"))
				.texture("layer0", new ResourceLocation(RushDuelMod.MOD_ID, "item/" + item.getId().getPath()));
	}
}
