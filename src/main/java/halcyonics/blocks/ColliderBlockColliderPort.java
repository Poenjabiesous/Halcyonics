package halcyonics.blocks;

import halcyonics.multiblock.AbstractMultiBlockTileEntityContainer;
import halcyonics.tileEntity.ColliderBlockColliderPortTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by Niel on 3/21/2016.
 */
public class ColliderBlockColliderPort extends AbstractMultiBlockTileEntityContainer {

    public ColliderBlockColliderPort(String unlocalisedName) {
        super(unlocalisedName);
    }

    @Override
    public boolean canRenderInLayer(EnumWorldBlockLayer layer) {
        if (layer == EnumWorldBlockLayer.CUTOUT) {
            return true;
        }
        if (layer == EnumWorldBlockLayer.TRANSLUCENT) {
            return true;
        }
        return false;
    }
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
      return true;
    }


    @Override
    public boolean isValidForFrame() {
        return false;
    }

    @Override
    public boolean isValidForPanel() {
        return true;
    }

    @Override
    public boolean isValidForContent() {
        return false;
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new ColliderBlockColliderPortTileEntity();
    }
}
