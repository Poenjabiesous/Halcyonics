package halcyonics.blocks;

import halcyonics.multiblock.AbstractMultiBlockTileEntityContainer;
import halcyonics.tileEntity.ColliderBlockColliderPortTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.World;

/**
 * Created by Niel on 3/21/2016.
 */
public class ExtractorBlockDisplacementPort extends AbstractMultiBlockTileEntityContainer {

    public ExtractorBlockDisplacementPort(String unlocalisedName) {
        super(unlocalisedName);
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
