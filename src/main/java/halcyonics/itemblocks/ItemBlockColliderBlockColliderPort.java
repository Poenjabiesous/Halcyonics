package halcyonics.itemblocks;

import halcyonics.handler.ConfigHandler;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

import java.util.List;

public class ItemBlockColliderBlockColliderPort extends ItemBlock {

    public ItemBlockColliderBlockColliderPort(Block p_i45328_1_) {
        super(p_i45328_1_);
    }

    @Override
    public void addInformation(ItemStack p_77624_1_, EntityPlayer p_77624_2_, List p_77624_3_, boolean p_77624_4_) {
        p_77624_3_.add("");
        p_77624_3_.add(EnumChatFormatting.GOLD + "--" + StatCollector.translateToLocal("block.info") + "--");
        p_77624_3_.add("");
        p_77624_3_.add(StatCollector.translateToLocal("colliderblockcolliderport.info.1"));
        p_77624_3_.add(StatCollector.translateToLocal("colliderblockcolliderport.info.2"));
        p_77624_3_.add("");
        p_77624_3_.add(EnumChatFormatting.LIGHT_PURPLE + StatCollector.translateToLocal("block.usable.frame") + EnumChatFormatting.WHITE +" "+ StatCollector.translateToLocal("block.usable.no"));
        p_77624_3_.add(EnumChatFormatting.LIGHT_PURPLE + StatCollector.translateToLocal("block.usable.panel") + EnumChatFormatting.WHITE +" "+ StatCollector.translateToLocal("block.usable.yes"));
        p_77624_3_.add("");
        p_77624_3_.add(EnumChatFormatting.BLUE + StatCollector.translateToLocal("structure.minsize") + EnumChatFormatting.WHITE + ": 3x3x3");
        p_77624_3_.add(EnumChatFormatting.BLUE + StatCollector.translateToLocal("structure.maxsize") + EnumChatFormatting.WHITE + ": " + ConfigHandler.getAuraicColliderMaxWidth() + "x" + ConfigHandler.getAuraicColliderMaxHeight() + "x" + ConfigHandler.getAuraicColliderMaxWidth());
    }
}
