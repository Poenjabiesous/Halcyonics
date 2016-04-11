package halcyonics.blocks;

import halcyonics.multiblock.AbstractMultiBlockNeighborAware;
import net.minecraft.util.EnumWorldBlockLayer;

/**
 * Created by Niel on 3/21/2016.
 */
public class ExtractorBlock extends AbstractMultiBlockNeighborAware {
    public ExtractorBlock(String unlocalisedName) {
        super(unlocalisedName);
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
