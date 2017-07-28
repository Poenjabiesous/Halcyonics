package halcyonics.tileEntity;


import cofh.api.energy.IEnergyProvider;
import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.event.EnergyTileUnloadEvent;
import ic2.api.energy.tile.*;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.MinecraftForge;
import halcyonics.handler.ConfigHandler;

/**
 * Created by Niel on 4/3/2016.
 */
public class MultiBlockEnergyOutputTileEntity extends SlaveMultiBlockTileEntity implements IEnergyProvider {


    @Override
    public int extractEnergy(EnumFacing from, int maxExtract, boolean simulate) {

        if (master == null) {
            return 0;
        }

        return master.extractEnergy(maxExtract, false);
    }

    @Override
    public int getEnergyStored(EnumFacing from) {

        if (master == null) {
            return 0;
        }

        return master.getEnergyStored();
    }

    @Override
    public int getMaxEnergyStored(EnumFacing from) {

        if (master == null) {
            return 0;
        }

        return master.getMaxEnergyStored();
    }

    @Override
    public boolean canConnectEnergy(EnumFacing from) {
        return master != null;
    }


    @Override
    public void operate() {
    }
}


