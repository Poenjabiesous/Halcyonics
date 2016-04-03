package halcyonics.blocks;

import halcyonics.multiblock.AbstractMultiBlockNeighborAware;
import net.minecraft.util.EnumWorldBlockLayer;

/**
 * Created by Niel on 3/21/2016.
 */
public class ColliderBlock extends AbstractMultiBlockNeighborAware {
    public ColliderBlock(String unlocalisedName) {
        super(unlocalisedName);
    }

    @Override
    public boolean canRenderInLayer(EnumWorldBlockLayer layer) {
        if (layer == EnumWorldBlockLayer.SOLID) {
            return true;
        }

        return false;
    }


    @Override
    public boolean isValidForFrame() {
        return true;
    }

    @Override
    public boolean isValidForPanel() {
        return true;
    }

    @Override
    public boolean isValidForContent() {
        return false;
    }
}
