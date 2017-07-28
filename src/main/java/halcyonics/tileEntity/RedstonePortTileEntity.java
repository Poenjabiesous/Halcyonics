package halcyonics.tileEntity;

/**
 * Created by Niel on 4/3/2016.
 */
public class RedstonePortTileEntity extends SlaveMultiBlockTileEntity {

    @Override
    public void operate() {
        if (worldObj.isBlockIndirectlyGettingPowered(getPos()) > 0) {
            this.getMaster().setIsReceivingRedstonePower(true);
            return;
        }
    }
}
