package halcyonics.blocks;

import halcyonics.multiblock.AbstractMultiBlockTileEntityContainer;
import halcyonics.tileEntity.MultiBlockEnergyOutputTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Created by Niel on 3/21/2016.
 */
public class ColliderBlockEnergyPort extends AbstractMultiBlockTileEntityContainer {
    public ColliderBlockEnergyPort(String unlocalisedName) {
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
        return new MultiBlockEnergyOutputTileEntity();
    }
}
