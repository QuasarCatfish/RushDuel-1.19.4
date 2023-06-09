package net.quas.rushduel.item;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.quas.rushduel.RushDuelMod;

public class ModItems {

	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, RushDuelMod.MOD_ID);

	public static final RegistryObject<Item> VELGEARIUM_INGOT = ITEMS.register("velgearium_ingot", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> RAW_VELGEARIUM = ITEMS.register("raw_velgearium", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> CARD = ITEMS.register("card", () -> new CardItem(new Item.Properties()));
	public static final RegistryObject<Item> CARD_PACK = ITEMS.register("card_pack", () -> new CardPackItem(new Item.Properties()));
	public static final RegistryObject<Item> STARTER_DECK = ITEMS.register("starter_deck", () -> new StarterDeckItem(new Item.Properties()));
	public static final RegistryObject<Item> DUEL_DISK_SEVENS = ITEMS.register("duel_disk_sevens", () -> new DuelDiskItem(new Item.Properties()));

	public static void register(IEventBus eventBus) {
		ITEMS.register(eventBus);
	}
}
