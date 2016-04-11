package halcyonics.tileEntity;


import cofh.api.energy.EnergyStorage;
import halcyonics.enums.MultiBlockEnum;
import halcyonics.fx.FXAuraBeamEntity;
import halcyonics.fx.FXColliderBeamOriginEntity;
import halcyonics.fx.FXColliderCoreEntity;
import halcyonics.handler.ConfigHandler;
import halcyonics.multiblock.AbstractMultiBlock;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ITickable;
import net.minecraft.world.World;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aura.AuraHelper;

/**
 * Created by Niel on 4/2/2016.
 */
public class ColliderBlockColliderPortTileEntity extends SlaveMultiBlockTileEntity implements ITickable {

    private float storedvis = 0;
    private boolean isOperating;

    public ColliderBlockColliderPortTileEntity() {
        this.isOperating = false;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.storedvis = compound.getFloat("storedvis");
        this.isOperating = compound.getBoolean("isOperating");
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setFloat("storedvis", this.storedvis);
        compound.setBoolean("isOperating", isOperating);
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

        if (master == null || !master.isActive()) {
            return;
        }

        if (worldObj.isRemote) {
            if (master.isActive() && isOperating) {
                BlockPos middle = getMaster().getReactorMiddle();
                FXColliderCoreEntity event = new FXColliderCoreEntity(worldObj, middle.getX(), middle.getY(), middle.getZ(), getMaster().getCurrentColliders() * (0.001F * getMaster().getCurrentColliders()));
                Minecraft.getMinecraft().effectRenderer.addEffect(event);

                if (worldObj.getWorldTime() % (worldObj.rand.nextInt(9) + 1) == 0) {
                    FXAuraBeamEntity beam = new FXAuraBeamEntity(worldObj, getPos().getX() + 0.5D, getPos().getY() + 0.5D, getPos().getZ() + 0.5D, middle.getX() + 0.5D, middle.getY() + 0.5D, middle.getZ() + 0.5D);
                    Minecraft.getMinecraft().effectRenderer.addEffect(beam);

                    FXColliderBeamOriginEntity origin = new FXColliderBeamOriginEntity(worldObj, getPos().getX(), getPos().getY(), getPos().getZ());
                    Minecraft.getMinecraft().effectRenderer.addEffect(origin);
                }
            }
            return;
        }


        if (!worldObj.isRemote && worldObj.getWorldTime() % 10 == 0) {
            worldObj.markBlockForUpdate(getPos());
        }

        if (this.storedvis <= 0) {
            int amountAir = AuraHelper.getAura(worldObj, getPos(), Aspect.AIR);
            int amountWater = AuraHelper.getAura(worldObj, getPos(), Aspect.WATER);
            int amountFire = AuraHelper.getAura(worldObj, getPos(), Aspect.FIRE);
            int amountChaos = AuraHelper.getAura(worldObj, getPos(), Aspect.ENTROPY);
            int amountOrder = AuraHelper.getAura(worldObj, getPos(), Aspect.ORDER);
            int amountEarth = AuraHelper.getAura(worldObj, getPos(), Aspect.EARTH);

            if (amountAir >= 1
                    && amountWater >= 1
                    && amountFire >= 1
                    && amountChaos >= 1
                    && amountOrder >= 1
                    && amountEarth >= 1) {

                AuraHelper.drainAura(worldObj, getPos(), Aspect.AIR, 1);
                AuraHelper.drainAura(worldObj, getPos(), Aspect.WATER, 1);
                AuraHelper.drainAura(worldObj, getPos(), Aspect.FIRE, 1);
                AuraHelper.drainAura(worldObj, getPos(), Aspect.ENTROPY, 1);
                AuraHelper.drainAura(worldObj, getPos(), Aspect.ORDER, 1);
                AuraHelper.drainAura(worldObj, getPos(), Aspect.EARTH, 1);
                this.storedvis = this.storedvis + 1.0F;
            } else {
                this.isOperating = false;
            }

        } else {
            this.storedvis = this.storedvis - 0.005F;
            getMaster().receiveEnergy(ConfigHandler.getAuraicColliderRFPerOperation(), false);
            this.isOperating = true;
        }
    }
}
