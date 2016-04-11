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

    public static Block extractorBlock;
    public static Block extractorBlockGlass;
    public static Block extractorBlockController;
    public static Block extractorBlockEnergyPort;
    public static Block extractorBlockDisplacementPort;

    public static Block blockRedstonePort;

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


        extractorBlock = new ExtractorBlock(Names.unlocalisedExtractorBlock);
        GameRegistry.registerBlock(extractorBlock, Names.unlocalisedExtractorBlock);

        extractorBlockGlass = new ExtractorBlockGlass(Names.unlocalisedExtractorBlockGlass);
        GameRegistry.registerBlock(extractorBlockGlass, Names.unlocalisedExtractorBlockGlass);

        extractorBlockController = new ExtractorBlockController(Names.unlocalisedExtractorBlockController);
        GameRegistry.registerBlock(extractorBlockController, Names.unlocalisedExtractorBlockController);

        extractorBlockEnergyPort = new ExtractorBlockController(Names.unlocalisedExtractorBlockEnergyPort);
        GameRegistry.registerBlock(extractorBlockEnergyPort, Names.unlocalisedExtractorBlockEnergyPort);

        extractorBlockDisplacementPort = new ExtractorBlockDisplacementPort(Names.unlocalisedExtractorBlockDisplacementPort);
        GameRegistry.registerBlock(extractorBlockDisplacementPort, Names.unlocalisedExtractorBlockDisplacementPort);


        blockRedstonePort = new BlockRedstonePort(Names.unlocalisedblockRedstonePort);
        GameRegistry.registerBlock(blockRedstonePort, ItemBlockRedstonePort.class, Names.unlocalisedblockRedstonePort);

    }
}
