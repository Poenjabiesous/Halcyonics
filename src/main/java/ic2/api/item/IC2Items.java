package ic2.api.item;

import net.minecraft.item.ItemStack;

import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;


/**
 * Provides access to IC2 blocks and items.
 *
 * Some items can be acquired through the ore dictionary which is the
 * recommended way. The items are initialized while IC2 is being loaded - try to
 * use ModsLoaded() or load your mod after IC2. Some blocks/items can be
 * disabled by a config setting, so it's recommended to check if they're null
 * first.
 *
 * Getting the associated Block/Item for an ItemStack x: BlocksHandler:
 * ((ItemBlock)x.getItem).getBlock() Items: x.getItem()
 * Alternatively, you can direwctly call IC2Items.instance.getItem(name) or IC2Items.instance.getBlock(name)
 *
 * It is recommended, that you keep a reference to the Items you get here.
 *
 * @author Aroma1997
 */
public final class IC2Items {

	/*
	 * To find out the name and variant of an Item/ItemStack
	 * have that Item in your hand in-game and type "/ic2 itemNameWithVariant"
	 */

	/**
	 * Get an ItemStack for a specific item name.
	 *
	 * @param name
	 *            item name
	 * @param variant
	 *            the variant/subtype for the Item.
	 * @return The item or null if the item does not exist or an error occurred
	 */
	public static ItemStack getItem(String name, String variant) {
		if (instance == null)  {
			return null;
		}
		return instance.getItemStack(name, variant);
	}

	/**
	 * Get an ItemStack for a specific item name.
	 *
	 * @param name
	 *            item name
	 * @return The item or null if the item does not exist or an error occurred
	 */
	public static ItemStack getItem(String name) {
		return getItem(name, null);
	}


	private static IItemAPI instance;

	/**
	 * Sets the internal IItemAPI instance.
	 * ONLY IC2 CAN DO THIS!!!!!!!
	 */
	public static void setInstance(IItemAPI api) {
		ModContainer mc = Loader.instance().activeModContainer();
		if (mc == null || !"IC2".equals(mc.getModId())) {
			throw new IllegalAccessError();
		}
		instance = api;
	}

}
