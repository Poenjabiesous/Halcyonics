package halcyonics.handler;

import halcyonics.gui.ColliderControllerGuiFormed;
import halcyonics.tileEntity.ColliderBlockControllerTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import halcyonics.container.*;

public class GuiHandler implements IGuiHandler {

    @Override
    public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity tileEntity = world.getTileEntity(new BlockPos(x, y, z));
        return new ColliderControllerContainer((ColliderBlockControllerTileEntity) tileEntity);
    }

    @Override
    public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity tileEntity = world.getTileEntity(new BlockPos(x, y, z));
        return new ColliderControllerGuiFormed((ColliderBlockControllerTileEntity) tileEntity);
    }
}