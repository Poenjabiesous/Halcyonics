package halcyonics.tileEntity;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyStorage;
import halcyonics.util.MultiBlockStructureUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
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

import java.text.DecimalFormat;
import java.util.Random;

/**
 * Created by Niel on 3/28/2016.
 */
public class ColliderBlockControllerTileEntity extends TileEntity implements ITickable, IEnergyStorage {


    private boolean isReceivingRedstonePower;
    private boolean isActive;
    private int currentColliders = 0;
    private EnergyStorage storage;
    private Random random;
    private boolean isStructureSet;
    private MultiBlockStructureDTO reactor;

    private int amountAer;
    private int amountAqua;
    private int amountTerra;
    private int amountOrdo;
    private int amountIgnis;
    private int amountPerditio;

    private int currentPowerGeneration;
    private double stabilityPercentage;
    private double efficiencyPercentage;

    private int maxAura;

    public ColliderBlockControllerTileEntity() {
        reactor = new MultiBlockStructureDTO();
        isStructureSet = false;
        random = new Random();
        storage = new EnergyStorage(0);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.currentColliders = compound.getInteger("currentColliders");
        this.isReceivingRedstonePower = compound.getBoolean("isReceivingRedstonePower");
        this.isReceivingRedstonePower = compound.getBoolean("isActive");
        this.storage.setEnergyStored(compound.getInteger("energyStored"));

        this.amountAer = compound.getInteger("amountAer");
        this.amountAqua = compound.getInteger("amountAqua");
        this.amountTerra = compound.getInteger("amountTerra");
        this.amountOrdo = compound.getInteger("amountOrdo");
        this.amountIgnis = compound.getInteger("amountIgnis");
        this.amountPerditio = compound.getInteger("amountPerditio");
        this.maxAura = compound.getInteger("maxAura");

        this.currentPowerGeneration = compound.getInteger("currentPowerGeneration");
        this.stabilityPercentage = compound.getDouble("stabilityPercentage");
        this.efficiencyPercentage = compound.getDouble("efficiencyPercentage");
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger("currentColliders", this.currentColliders);
        compound.setInteger("currentEnergyStored", this.getEnergyStored());
        compound.setBoolean("isReceivingRedstonePower", this.isReceivingRedstonePower);
        compound.setBoolean("isActive", this.isActive);

        compound.setInteger("amountAer", amountAer);
        compound.setInteger("amountAqua", amountAqua);
        compound.setInteger("amountTerra", amountTerra);
        compound.setInteger("amountOrdo", amountOrdo);
        compound.setInteger("amountIgnis", amountIgnis);
        compound.setInteger("amountPerditio", amountPerditio);
        compound.setInteger("maxAura", maxAura);

        compound.setInteger("currentPowerGeneration", currentPowerGeneration);
        compound.setDouble("stabilityPercentage", stabilityPercentage);
        compound.setDouble("efficiencyPercentage", efficiencyPercentage);
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
    public void update() {

        structureChecks();

        if (worldObj.isRemote) {
            return;
        }

        if (!isStructureSet) {
            this.isActive = false;
            return;
        }

        //redstone checks
        for (TileEntity tile : reactor.getWorkingStructureBlocks().values()) {
            if (tile instanceof RedstonePortTileEntity) {
                ((SlaveMultiBlockTileEntity) tile).operate();
                if (this.isReceivingRedstonePower()) {
                    this.isActive = false;
                    return;
                }
            }
        }

        this.setIsReceivingRedstonePower(false);

        //Aura checks

        if (AuraHelper.shouldPreserveAura(getWorld(), null, getPos(), Aspect.AIR)) {
            this.isActive = false;
            return;
        }

        if (AuraHelper.shouldPreserveAura(getWorld(), null, getPos(), Aspect.WATER)) {
            this.isActive = false;
            return;
        }

        if (AuraHelper.shouldPreserveAura(getWorld(), null, getPos(), Aspect.FIRE)) {
            this.isActive = false;
            return;
        }

        if (AuraHelper.shouldPreserveAura(getWorld(), null, getPos(), Aspect.ENTROPY)) {
            this.isActive = false;
            return;
        }

        if (AuraHelper.shouldPreserveAura(getWorld(), null, getPos(), Aspect.ORDER)) {
            this.isActive = false;
            return;
        }

        if (AuraHelper.shouldPreserveAura(getWorld(), null, getPos(), Aspect.EARTH)) {
            this.isActive = false;
            return;
        }

        this.isActive = true;
        this.maxAura = AuraHelper.getAuraBase(worldObj, getPos());
        this.amountAer = AuraHelper.getAura(worldObj, getPos(), Aspect.AIR);
        this.amountAqua = AuraHelper.getAura(worldObj, getPos(), Aspect.WATER);
        this.amountIgnis = AuraHelper.getAura(worldObj, getPos(), Aspect.FIRE);
        this.amountPerditio = AuraHelper.getAura(worldObj, getPos(), Aspect.ENTROPY);
        this.amountOrdo = AuraHelper.getAura(worldObj, getPos(), Aspect.ORDER);
        this.amountTerra = AuraHelper.getAura(worldObj, getPos(), Aspect.EARTH);

        this.currentPowerGeneration = 0;
        this.currentColliders = 0;


        for (TileEntity tile : reactor.getWorkingStructureBlocks().values()) {
            if (tile instanceof ColliderBlockColliderPortTileEntity) {
                ((ColliderBlockColliderPortTileEntity) tile).operate();
                currentColliders++;
            }
        }

        int maxVisAllowed = ConfigHandler.getAuraicColliderMaxVisPerAccelerator() * currentColliders;

        //Baselines
        double baselineEnergyGenMultiplier = (Math.min(amountIgnis, maxVisAllowed) * Math.min(amountPerditio, maxVisAllowed)) / (1.0F + (0.1F * currentColliders));
        double baselineStability = (Math.min(amountOrdo, maxVisAllowed) * Math.min(amountTerra, maxVisAllowed)) / (1.0F + (0.1F * currentColliders));
        double baselineEfficiency = (Math.min(amountAqua, maxVisAllowed) * Math.min(amountAer, maxVisAllowed)) / (1.0F + (0.1F * currentColliders));

        //Collider Stability
        stabilityPercentage = (baselineStability / baselineEnergyGenMultiplier) * 100F;
        if (stabilityPercentage > 100F) {
            stabilityPercentage = roundTwoDecimals(100F);
        } else {
            stabilityPercentage = roundTwoDecimals(stabilityPercentage);
        }

        //Collider Efficiency
        efficiencyPercentage = (baselineEfficiency / baselineEnergyGenMultiplier) * 100F;
        if (efficiencyPercentage > 100F) {
            efficiencyPercentage = roundTwoDecimals(100F);
        } else {
            efficiencyPercentage = roundTwoDecimals(efficiencyPercentage);
        }

        currentPowerGeneration = (int) Math.floor(baselineEnergyGenMultiplier * (currentColliders * ConfigHandler.getAuraicColliderRFPerOperation()));

        if (getWorld().getWorldTime() % 20 == 0 && (1 + random.nextInt(120)) > stabilityPercentage) {

            switch (random.nextInt(6)) {
                case (0):
                    AuraHelper.drainAura(getWorld(), getPos(), Aspect.AIR, 1);
                    break;
                case (1):
                    AuraHelper.drainAura(getWorld(), getPos(), Aspect.WATER, 1);
                    break;
                case (2):
                    AuraHelper.drainAura(getWorld(), getPos(), Aspect.FIRE, 1);
                    break;
                case (3):
                    AuraHelper.drainAura(getWorld(), getPos(), Aspect.ORDER, 1);
                    break;
                case (4):
                    AuraHelper.drainAura(getWorld(), getPos(), Aspect.ENTROPY, 1);
                    break;
                case (5):
                    AuraHelper.drainAura(getWorld(), getPos(), Aspect.EARTH, 1);
                    break;
            }
        }
    }


    private void structureChecks() {

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
        }
    }

    public void deconstructMultiBlock() {
        if (this.reactor != null) {
            MultiBlockStructureUtil.setReactorStructureInactive(getWorld(), reactor);
            this.reactor.invalidateStructure();
            this.isStructureSet = false;
            this.isReceivingRedstonePower = false;
        }
    }

    public void constructMultiBlock() {
        if (this.reactor != null) {
            MultiBlockStructureUtil.setReactorStructureActive(getWorld(), reactor, this);
            isStructureSet = true;
        }
    }


    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
        return (oldState.getBlock() != newState.getBlock());
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        return this.storage.receiveEnergy(maxReceive, simulate);
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        return Math.min((int) Math.floor(currentPowerGeneration * (efficiencyPercentage / 100F)), maxExtract);
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

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public boolean isReceivingRedstonePower() {
        return isReceivingRedstonePower;
    }

    public void setIsReceivingRedstonePower(boolean isReceivingRedstonePower) {
        this.isReceivingRedstonePower = isReceivingRedstonePower;
    }

    public BlockPos getReactorMiddle() {
        return new BlockPos((reactor.getMinPos().getX() + reactor.getMaxPos().getX()) / 2, (reactor.getMinPos().getY() + reactor.getMaxPos().getY()) / 2, (reactor.getMinPos().getZ() + reactor.getMaxPos().getZ()) / 2);
    }

    public MultiBlockStructureDTO getReactor() {
        return reactor;
    }

    double roundTwoDecimals(double d) {
        DecimalFormat twoDForm = new DecimalFormat("#.##");
        return Double.valueOf(twoDForm.format(d));
    }

    public EnergyStorage getStorage() {
        return storage;
    }

    public boolean isStructureSet() {
        return isStructureSet;
    }

    public int getAmountAer() {
        return amountAer;
    }

    public int getAmountAqua() {
        return amountAqua;
    }

    public int getAmountTerra() {
        return amountTerra;
    }

    public int getAmountOrdo() {
        return amountOrdo;
    }

    public int getAmountIgnis() {
        return amountIgnis;
    }

    public int getAmountPerditio() {
        return amountPerditio;
    }

    public int getCurrentPowerGeneration() {
        return currentPowerGeneration;
    }

    public double getStabilityPercentage() {
        return stabilityPercentage;
    }

    public double getEfficiencyPercentage() {
        return efficiencyPercentage;
    }

    public int getMaxAura() {
        return maxAura;
    }

    public boolean isUseableByPlayer(EntityPlayer player) {
        return worldObj.getTileEntity(getPos()) == this && player.getDistanceSq(getPos().getX() + 0.5,
                getPos().getY() + 0.5,
                getPos().getZ() + 0.5) < 64;
    }
}
