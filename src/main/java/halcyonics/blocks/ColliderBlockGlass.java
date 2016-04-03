package halcyonics.blocks;

import halcyonics.multiblock.AbstractMultiBlock;

/**
 * Created by Niel on 3/21/2016.
 */
public class ColliderBlockGlass extends AbstractMultiBlock {
    public ColliderBlockGlass(String unlocalisedName) {
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
}
