package halcyonics.handler;


import halcyonics.blocks.*;
import halcyonics.itemblocks.*;
import net.minecraft.block.Block;
import net.minecraftforge.fml.common.registry.GameRegistry;
import halcyonics.reference.Names;

public class BlocksHandler {

    public static Block colliderBlock;
    public static Block colliderBlockGlass;
    public static Block colliderBlockController;
    public static Block colliderBlockEnergyPort;
    public static Block colliderBlockColliderPort;
    public static Block colliderBlockRedstonePort;

    public static void init() {
        colliderBlock = new ColliderBlock(Names.unlocalisedColliderBlock);
        GameRegistry.registerBlock(colliderBlock, ItemBlockColliderBlock.class, Names.unlocalisedColliderBlock);

        colliderBlockGlass = new ColliderBlockGlass(Names.unlocalisedColliderBlockGlass);
        GameRegistry.registerBlock(colliderBlockGlass, ItemBlockColliderBlockGlass.class, Names.unlocalisedColliderBlockGlass);

        colliderBlockController = new ColliderBlockController(Names.unlocalisedColliderBlockController);
        GameRegistry.registerBlock(colliderBlockController, ItemBlockColliderBlockController.class, Names.unlocalisedColliderBlockController);

        colliderBlockEnergyPort = new ColliderBlockEnergyPort(Names.unlocalisedColliderBlockEnergyPort);
        GameRegistry.registerBlock(colliderBlockEnergyPort, ItemBlockColliderBlockEnergyPort.class, Names.unlocalisedColliderBlockEnergyPort);

        colliderBlockColliderPort = new ColliderBlockColliderPort(Names.unlocalisedColliderBlockColliderPort);
        GameRegistry.registerBlock(colliderBlockColliderPort, ItemBlockColliderBlockColliderPort.class, Names.unlocalisedColliderBlockColliderPort);

        colliderBlockRedstonePort = new ColliderBlockRedstonePort(Names.unlocalisedColliderBlockRedstonePort);
        GameRegistry.registerBlock(colliderBlockRedstonePort, ItemBlockColliderBlockRedstonePort.class, Names.unlocalisedColliderBlockRedstonePort);
    }
}
