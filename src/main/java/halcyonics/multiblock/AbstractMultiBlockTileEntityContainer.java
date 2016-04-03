package halcyonics.multiblock;


import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

/**
 * Created by Niel on 3/28/2016.
 */
public abstract class AbstractMultiBlockTileEntityContainer extends AbstractMultiBlockNeighborAwareAirAlign implements ITileEntityProvider {

    public AbstractMultiBlockTileEntityContainer(String unlocalizedName) {
        super(unlocalizedName);
        this.isBlockContainer = true;
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        super.breakBlock(world, pos, state);
        world.removeTileEntity(pos);
    }

    @Override
    public boolean onBlockEventReceived(World worldIn, BlockPos pos, IBlockState state, int eventID, int eventParam) {
        super.onBlockEventReceived(worldIn, pos, state, eventID, eventParam);
        TileEntity tileentity = worldIn.getTileEntity(pos);
        return tileentity == null ? false : tileentity.receiveClientEvent(eventID, eventParam);
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }
}
