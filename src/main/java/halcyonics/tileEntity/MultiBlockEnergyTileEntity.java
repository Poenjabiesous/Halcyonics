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
public class MultiBlockEnergyTileEntity extends SlaveMultiBlockTileEntity implements IEnergyProvider, IEnergySource, IEnergyTile {


    @Override
    public int extractEnergy(EnumFacing from, int maxExtract, boolean simulate) {

        if (master == null) {
            return 0;
        }

        return master.extractEnergy(from, maxExtract, false);
    }

    @Override
    public int getEnergyStored(EnumFacing from) {

        if (master == null) {
            return 0;
        }

        return master.getEnergyStored(EnumFacing.NORTH);
    }

    @Override
    public int getMaxEnergyStored(EnumFacing from) {

        if (master == null) {
            return 0;
        }

        return master.getMaxEnergyStored(EnumFacing.NORTH);
    }

    @Override
    public boolean canConnectEnergy(EnumFacing from) {
        return master == null ? false : true;
    }

    @Override
    public double getOfferedEnergy() {
        if (master == null) {
            return 0.0D;
        }

        return master.getOfferedIC2Energy();
    }

    @Override
    public void drawEnergy(double amount) {

        master.extractEnergy(EnumFacing.NORTH, (int) (amount * ConfigHandler.getEuPowerConversionRatio()), false);
    }

    @Override
    public int getSourceTier() {
        return 0;
    }

    @Override
    public boolean emitsEnergyTo(IEnergyAcceptor receiver, EnumFacing side) {
        return true;
    }

    @Override
    public void invalidate() {
        super.invalidate();
        if (!worldObj.isRemote && master == null) {
            EnergyTileUnloadEvent event = new EnergyTileUnloadEvent(this);
            MinecraftForge.EVENT_BUS.post(event);
        }
    }

    @Override
    public void validate() {
        super.validate();
        if (!worldObj.isRemote && master != null) {
            EnergyTileLoadEvent event = new EnergyTileLoadEvent(this);
            MinecraftForge.EVENT_BUS.post(event);
        }
    }

    @Override
    public void onChunkUnload() {
        super.onChunkUnload();
        super.invalidate();
        if (!worldObj.isRemote) {
            EnergyTileUnloadEvent event = new EnergyTileUnloadEvent(this);
            MinecraftForge.EVENT_BUS.post(event);
        }
    }
}


