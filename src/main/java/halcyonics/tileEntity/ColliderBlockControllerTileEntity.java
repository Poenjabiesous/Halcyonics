package halcyonics.tileEntity;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyProvider;
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
public class ColliderBlockControllerTileEntity extends TileEntity implements ITickable, IEnergyProvider {

    MultiBlockStructureDTO reactor;
    boolean isStructureSet;
    Random random;

    private int currentHeat = 0;
    private int currentPowerGeneration = 0;
    private int currentStability = 0;
    private int currentColliders = 0;
    private boolean isActive = true;
    private EnergyStorage storage;

    public ColliderBlockControllerTileEntity() {

        reactor = new MultiBlockStructureDTO();
        isStructureSet = false;
        random = new Random();
        storage = new EnergyStorage(ConfigHandler.getAuraicColliderMaxStorage(), ConfigHandler.getAuraicColliderMaxStorage(), ConfigHandler.getAuraicColliderMaxIO());
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.currentHeat = compound.getInteger("currentHeat");
        this.currentPowerGeneration = compound.getInteger("currentPowerGeneration");
        this.currentStability = compound.getInteger("currentStability");
        this.currentColliders = compound.getInteger("currentColliders");
        this.isActive = compound.getBoolean("isActive");
        this.storage.setEnergyStored(compound.getInteger("energyStored"));
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger("currentHeat", this.currentHeat);
        compound.setInteger("currentPowerGeneration", this.currentPowerGeneration);
        compound.setInteger("currentStability", this.currentStability);
        compound.setInteger("currentColliders", this.currentColliders);
        compound.setInteger("currentEnergyStored", this.getEnergyStored(EnumFacing.NORTH));
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

        if (worldObj.isRemote && reactor.isStructureCorrect() && isActive) {

            int colliderAmount = 0;

            for (TileEntity tile : reactor.getWorkingStructureBlocks().values()) {
                if (tile instanceof ColliderBlockColliderPortTileEntity) {
                    colliderAmount++;
                }
            }

            for (TileEntity tile : reactor.getWorkingStructureBlocks().values()) {
                if (tile instanceof ColliderBlockColliderPortTileEntity) {

                    BlockPos middle = getReactorMiddle();
                    FXAuraBeamEntity beam = new FXAuraBeamEntity(worldObj, tile.getPos().getX() + 0.5D, tile.getPos().getY() + 0.5D, tile.getPos().getZ() + 0.5D, middle.getX() + 0.5D, middle.getY() + 0.5D, middle.getZ() + 0.5D);
                    Minecraft.getMinecraft().effectRenderer.addEffect(beam);
                    FXColliderCoreEntity event = new FXColliderCoreEntity(worldObj, middle.getX(), middle.getY(), middle.getZ(), 0F, 0F, 0F, colliderAmount * (0.001F * colliderAmount));
                    Minecraft.getMinecraft().effectRenderer.addEffect(event);
                }
            }
        }

        //structure checks
        if (!worldObj.isRemote) {
            if (worldObj.getWorldTime() % 10 == 0) {

                //Sync first
                worldObj.markBlockForUpdate(getPos());

                this.currentHeat = 0;
                this.currentPowerGeneration = 0;
                this.currentStability = 0;
                this.currentColliders = 0;


                //Deconstruct the reactor if a block is missing.
                if (!reactor.isStructureCorrect() && isStructureSet) {
                    MultiBlockStructureUtil.setReactorStructureInactive(getWorld(), reactor);
                    reactor.invalidateStructure();
                    isStructureSet = false;
                    return;
                }

                //Only reconstruct if something changed.
                if (reactor.isStructureCorrect() && !isStructureSet) {
                    MultiBlockStructureUtil.setReactorStructureActive(getWorld(), reactor, this);
                    isStructureSet = true;
                    return;
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

            if (this.currentColliders == 0) {
                return;
            }

            this.isActive = true;

            int auraAir = AuraHelper.getAura(worldObj, getPos(), Aspect.AIR);
            int auraWater = AuraHelper.getAura(worldObj, getPos(), Aspect.WATER);
            int auraFire = AuraHelper.getAura(worldObj, getPos(), Aspect.FIRE);
            int auraChaos = AuraHelper.getAura(worldObj, getPos(), Aspect.ENTROPY);
            int auraOrder = AuraHelper.getAura(worldObj, getPos(), Aspect.ORDER);
            int auraEarth = AuraHelper.getAura(worldObj, getPos(), Aspect.EARTH);

            this.currentStability = (auraOrder * (auraEarth == 0 ? 1 : auraEarth)) * ConfigHandler.getStabilityMultiplier();
            int potentialPowerOnEventRaw = auraFire * (auraChaos == 0 ? 1 : auraChaos);
            int potentialCoolingOnEvent = (auraAir * (auraWater == 0 ? 1 : auraWater)) * ConfigHandler.getCoolingMultiplier();
            int heatOnEvent = potentialPowerOnEventRaw - potentialCoolingOnEvent;
            heatOnEvent = (heatOnEvent < 0 ? 0 : heatOnEvent);
            potentialPowerOnEventRaw = potentialPowerOnEventRaw - heatOnEvent;
            int potentialPowerOnEvent = (int) (Math.sqrt(potentialPowerOnEventRaw)) * ConfigHandler.getPowerMultiplier();

            if (random.nextInt(this.currentStability <= 0 ? 1 : this.currentStability) <= this.currentColliders++) {
                AuraHelper.drainAura(worldObj, getPos(), getNextDrainableAspect(), 1);
                return;
            }

            //now fire events
            for (TileEntity tile : reactor.getWorkingStructureBlocks().values()) {
                if (tile instanceof ColliderBlockColliderPortTileEntity) {
                    this.storage.receiveEnergy(potentialPowerOnEvent, false);
                    this.currentPowerGeneration = this.currentPowerGeneration + potentialPowerOnEvent;
                    this.currentHeat = this.currentHeat + heatOnEvent;
                }
            }
        }
    }

    private Aspect getNextDrainableAspect() {
        switch (random.nextInt(6)) {
            case 0: {
                return Aspect.AIR;
            }
            case 1: {
                return Aspect.WATER;
            }
            case 2: {
                return Aspect.ENTROPY;
            }
            case 3: {
                return Aspect.ORDER;
            }
            case 4: {
                return Aspect.FIRE;
            }
            case 5: {
                return Aspect.EARTH;
            }
        }
        return null;
    }

    public double getOfferedIC2Energy() {

        if (this.getEnergyStored(EnumFacing.NORTH) >= this.storage.getMaxExtract()) {
            return (this.storage.getMaxExtract() / ConfigHandler.getEuPowerConversionRatio());
        }

        return (this.getEnergyStored(EnumFacing.NORTH) / ConfigHandler.getEuPowerConversionRatio());
    }

    private BlockPos getReactorMiddle() {
        return new BlockPos((reactor.getMinPos().getX() + reactor.getMaxPos().getX()) / 2, (reactor.getMinPos().getY() + reactor.getMaxPos().getY()) / 2, (reactor.getMinPos().getZ() + reactor.getMaxPos().getZ()) / 2);

    }

    public MultiBlockStructureDTO getReactor() {
        return reactor;
    }

    public int getCurrentHeat() {
        return currentHeat;
    }

    public int getCurrentPowerGeneration() {
        return currentPowerGeneration;
    }

    public int getCurrentStability() {
        return currentStability;
    }

    public int getCurrentColliders() {
        return currentColliders;
    }


    @Override
    public int extractEnergy(EnumFacing from, int maxExtract, boolean simulate) {
        return this.storage.extractEnergy(maxExtract, simulate);
    }

    @Override
    public int getEnergyStored(EnumFacing from) {
        return this.storage.getEnergyStored();
    }

    @Override
    public int getMaxEnergyStored(EnumFacing from) {
        return this.storage.getMaxEnergyStored();
    }

    @Override
    public boolean canConnectEnergy(EnumFacing from) {
        return false;
    }
}
