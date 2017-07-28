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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aura.AuraHelper;

/**
 * Created by Niel on 4/2/2016.
 */
public class ColliderBlockColliderPortTileEntity extends SlaveMultiBlockTileEntity implements ITickable {


    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
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
    public void operate() {

    }

    @SideOnly(Side.CLIENT)
    @Override
    public void update() {
        if (getMaster() != null && getMaster().isActive()) {
            BlockPos middle = getMaster().getReactorMiddle();
            FXColliderCoreEntity event = new FXColliderCoreEntity(worldObj, middle.getX(), middle.getY(), middle.getZ(), getMaster().getCurrentColliders() * (0.005F * getMaster().getCurrentColliders()));
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
}
