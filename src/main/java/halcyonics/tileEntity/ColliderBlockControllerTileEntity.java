package halcyonics.tileEntity;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyStorage;
import halcyonics.util.MultiBlockStructureUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.world.World;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aura.AuraHelper;
import halcyonics.dto.MultiBlockStructureDTO;
import halcyonics.handler.ConfigHandler;
import halcyonics.fx.FXAuraBeamEntity;
import halcyonics.fx.FXColliderCoreEntity;

import java.util.Random;

/**
 * Created by Niel on 3/28/2016.
 */
public class ColliderBlockControllerTileEntity extends TileEntity implements ITickable, IEnergyStorage {



    public boolean isActive;

    private int currentPowerGeneration = 0;
    private int currentColliders = 0;
    private EnergyStorage storage;
    private Random random;
    private boolean isStructureSet;
    private MultiBlockStructureDTO reactor;

    public ColliderBlockControllerTileEntity() {

        reactor = new MultiBlockStructureDTO();
        isStructureSet = false;
        random = new Random();
        storage = new EnergyStorage(ConfigHandler.getAuraicColliderMaxStorage(), ConfigHandler.getAuraicColliderMaxStorage(), ConfigHandler.getAuraicColliderMaxStorage());
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.currentPowerGeneration = compound.getInteger("currentPowerGeneration");
        this.currentColliders = compound.getInteger("currentColliders");
        this.isActive = compound.getBoolean("isActive");
        this.storage.setEnergyStored(compound.getInteger("energyStored"));
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger("currentPowerGeneration", this.currentPowerGeneration);
        compound.setInteger("currentColliders", this.currentColliders);
        compound.setInteger("currentEnergyStored", this.getEnergyStored());
        compound.setBoolean("isActive", this.isActive);
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound tag = new NBTTagCompound();
        this.writeToNBT(tag);
        return new S35PacketUpdateTileEntity(getPos(), 0, tag);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        super.onDataPacket(net, pkt);
        this.readFromNBT(pkt.getNbtCompound());
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) {
        return (oldState.getBlock() != newSate.getBlock());
    }

    @Override
    public void update() {

        //Don't calculate structure again.
        if (isStructureSet) {
            reactor = MultiBlockStructureUtil.checkMadeStructure(getWorld(), reactor);
        } else {
            reactor = MultiBlockStructureUtil.checkStructure(getWorld(), getPos(), new BlockPos(3, 3, 3), new BlockPos(ConfigHandler.getAuraicColliderMaxWidth(), ConfigHandler.getAuraicColliderMaxHeight(), ConfigHandler.getAuraicColliderMaxWidth()));
        }

        for (TileEntity tile : reactor.getWorkingStructureBlocks().values()) {
            if (tile instanceof SlaveMultiBlockTileEntity && worldObj.getTileEntity(tile.getPos()) != null) {
                ((SlaveMultiBlockTileEntity) worldObj.getTileEntity(tile.getPos())).setMaster(this);
            }
        }

        //structure checks
        if (!worldObj.isRemote) {
            if (worldObj.getWorldTime() % 10 == 0) {

                //Sync first
                worldObj.markBlockForUpdate(getPos());

                this.currentPowerGeneration = 0;
                this.currentColliders = 0;


                //Deconstruct the reactor if a block is missing.
                if (!reactor.isStructureCorrect() && isStructureSet) {
                    deconstructMultiBlock();
                    return;
                }

                //Only reconstruct if something changed.
                if (reactor.isStructureCorrect() && !isStructureSet) {
                    constructMultiBlock();
                }
            }

            if (!isStructureSet) {
                return;
            }

            //get amount of colliders and check for redstone signal on redstone ports.
            for (TileEntity tile : reactor.getWorkingStructureBlocks().values()) {
                if (tile instanceof ColliderBlockColliderPortTileEntity) {
                    this.currentColliders++;
                }

                if (tile instanceof RedstonePortTileEntity && worldObj.isBlockIndirectlyGettingPowered(tile.getPos()) > 0) {
                    this.isActive = false;
                    return;
                }
            }

            this.isActive = true;
        }
    }

    public void deconstructMultiBlock() {
        if (this.reactor != null) {
            MultiBlockStructureUtil.setReactorStructureInactive(getWorld(), reactor);
            this.reactor.invalidateStructure();
            this.isStructureSet = false;
            this.isActive = false;
        }
    }

    public void constructMultiBlock() {
        if (this.reactor != null) {
            MultiBlockStructureUtil.setReactorStructureActive(getWorld(), reactor, this);
            isStructureSet = true;
        }
    }

    public double getOfferedIC2Energy() {

        if (this.getEnergyStored() >= this.storage.getMaxExtract()) {
            return (this.storage.getMaxExtract() / ConfigHandler.getEuPowerConversionRatio());
        }

        return (this.getEnergyStored() / ConfigHandler.getEuPowerConversionRatio());
    }

    public BlockPos getReactorMiddle() {
        return new BlockPos((reactor.getMinPos().getX() + reactor.getMaxPos().getX()) / 2, (reactor.getMinPos().getY() + reactor.getMaxPos().getY()) / 2, (reactor.getMinPos().getZ() + reactor.getMaxPos().getZ()) / 2);
    }

    public MultiBlockStructureDTO getReactor() {
        return reactor;
    }


    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        return this.storage.receiveEnergy(maxReceive, simulate);
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        return this.storage.extractEnergy(maxExtract, simulate);
    }

    @Override
    public int getEnergyStored() {
        return this.storage.getMaxEnergyStored();
    }

    @Override
    public int getMaxEnergyStored() {
        return this.storage.getEnergyStored();
    }

    public int getCurrentColliders() {
        return currentColliders;
    }

    public boolean isActive() {
        return isActive;
    }
}
