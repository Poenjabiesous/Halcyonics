package halcyonics.tileEntity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

/**
 * Created by Niel on 4/2/2016.
 */
public abstract class SlaveMultiBlockTileEntity extends TileEntity {

    protected ColliderBlockControllerTileEntity master;

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
        return (oldState.getBlock() != newState.getBlock());
    }

    public void setMaster(ColliderBlockControllerTileEntity master) {
        this.master = master;
    }
}
