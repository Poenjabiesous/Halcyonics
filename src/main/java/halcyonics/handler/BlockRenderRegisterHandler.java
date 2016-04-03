package halcyonics.handler;


import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import halcyonics.enums.MultiBlockEnum;
import halcyonics.reference.ModInfo;
import halcyonics.reference.Names;

/**
 * Created by Niel on 3/28/2016.
 */
public class BlockRenderRegisterHandler {

    public static void init() {

        ModelBakery.registerItemVariants(Item.getItemFromBlock(BlocksHandler.colliderBlock), new ResourceLocation(ModInfo.ID + ":" + Names.unlocalisedColliderBlock + "_" + MultiBlockEnum.EnumType.UNFORMED));
        reg(BlocksHandler.colliderBlock, 0, Names.unlocalisedColliderBlock + "_" + MultiBlockEnum.EnumType.UNFORMED);

        ModelBakery.registerItemVariants(Item.getItemFromBlock(BlocksHandler.colliderBlockGlass), new ResourceLocation(ModInfo.ID + ":" + Names.unlocalisedColliderBlockGlass + "_" + MultiBlockEnum.EnumType.UNFORMED));
        reg(BlocksHandler.colliderBlockGlass, 0, Names.unlocalisedColliderBlockGlass + "_" + MultiBlockEnum.EnumType.UNFORMED);

        ModelBakery.registerItemVariants(Item.getItemFromBlock(BlocksHandler.colliderBlockController), new ResourceLocation(ModInfo.ID + ":" + Names.unlocalisedColliderBlockController + "_" + MultiBlockEnum.EnumType.UNFORMED));
        reg(BlocksHandler.colliderBlockController, 0, Names.unlocalisedColliderBlockController + "_" + MultiBlockEnum.EnumType.UNFORMED);

        ModelBakery.registerItemVariants(Item.getItemFromBlock(BlocksHandler.colliderBlockEnergyPort), new ResourceLocation(ModInfo.ID + ":" + Names.unlocalisedColliderBlockEnergyPort + "_" + MultiBlockEnum.EnumType.UNFORMED));
        reg(BlocksHandler.colliderBlockEnergyPort, 0, Names.unlocalisedColliderBlockEnergyPort + "_" + MultiBlockEnum.EnumType.UNFORMED);

        ModelBakery.registerItemVariants(Item.getItemFromBlock(BlocksHandler.colliderBlockColliderPort), new ResourceLocation(ModInfo.ID + ":" + Names.unlocalisedColliderBlockColliderPort + "_" + MultiBlockEnum.EnumType.UNFORMED));
        reg(BlocksHandler.colliderBlockColliderPort, 0, Names.unlocalisedColliderBlockColliderPort + "_" + MultiBlockEnum.EnumType.UNFORMED);

        ModelBakery.registerItemVariants(Item.getItemFromBlock(BlocksHandler.colliderBlockColliderPort), new ResourceLocation(ModInfo.ID + ":" + Names.unlocalisedColliderBlockColliderPort + "_" + MultiBlockEnum.EnumType.UNFORMED));
        reg(BlocksHandler.colliderBlockColliderPort, 0, Names.unlocalisedColliderBlockColliderPort + "_" + MultiBlockEnum.EnumType.UNFORMED);

        ModelBakery.registerItemVariants(Item.getItemFromBlock(BlocksHandler.colliderBlockRedstonePort), new ResourceLocation(ModInfo.ID + ":" + Names.unlocalisedColliderBlockRedstonePort + "_" + MultiBlockEnum.EnumType.UNFORMED));
        reg(BlocksHandler.colliderBlockRedstonePort, 0, Names.unlocalisedColliderBlockRedstonePort + "_" + MultiBlockEnum.EnumType.UNFORMED);
    }

    public static void reg(Block block, int meta, String file) {
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
                .register(Item.getItemFromBlock(block), meta, new ModelResourceLocation(ModInfo.ID + ":" + file, "inventory"));
    }
}
