package halcyonics.blocks;

import halcyonics.multiblock.AbstractMultiBlock;
import net.minecraft.util.EnumWorldBlockLayer;

/**
 * Created by Niel on 3/21/2016.
 */
public class ColliderBlockGlass extends AbstractMultiBlock {
    public ColliderBlockGlass(String unlocalisedName) {
        super(unlocalisedName);
    }

    @Override
    public boolean canRenderInLayer(EnumWorldBlockLayer layer) {
        if (layer == EnumWorldBlockLayer.TRANSLUCENT) {
            return true;
        }
        return false;
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
