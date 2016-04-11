package halcyonics.handler;


import halcyonics.tileEntity.ColliderBlockColliderPortTileEntity;
import halcyonics.tileEntity.MultiBlockEnergyOutputTileEntity;
import halcyonics.tileEntity.ColliderBlockControllerTileEntity;
import halcyonics.tileEntity.RedstonePortTileEntity;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class TileEntityHandler {
    public static void init() {

        GameRegistry.registerTileEntity(ColliderBlockControllerTileEntity.class, "reactorBlockTileEntity");
        GameRegistry.registerTileEntity(ColliderBlockColliderPortTileEntity.class, "reactorBlockColliderPortTileEntity");
        GameRegistry.registerTileEntity(MultiBlockEnergyOutputTileEntity.class, "multiBlockEnergyTileEntity");
        GameRegistry.registerTileEntity(RedstonePortTileEntity.class, "redstonePortTileEntity");
    }
}
