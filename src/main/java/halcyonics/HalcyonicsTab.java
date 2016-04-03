package halcyonics;

import halcyonics.handler.BlocksHandler;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class HalcyonicsTab extends CreativeTabs {

    public HalcyonicsTab(String label) {
        super(label);
    }

    @Override
    public ItemStack getIconItemStack() {
        return new ItemStack(BlocksHandler.colliderBlock);
    }

    @Override
    public Item getTabIconItem() {
        return new ItemStack(BlocksHandler.colliderBlock).getItem();
    }
}
