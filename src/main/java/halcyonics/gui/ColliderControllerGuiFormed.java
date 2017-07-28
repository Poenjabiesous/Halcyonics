package halcyonics.gui;

import halcyonics.reference.ModInfo;
import halcyonics.tileEntity.ColliderBlockControllerTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import halcyonics.container.*;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;

import java.awt.*;

/**
 * Created by Niel on 1/21/2017.
 */
public class ColliderControllerGuiFormed extends GuiContainer {

    ColliderBlockControllerTileEntity tileEntity;

    public ColliderControllerGuiFormed(ColliderBlockControllerTileEntity tileEntity) {
        super(new ColliderControllerContainer(tileEntity));
        this.tileEntity = tileEntity;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
        ResourceLocation texture = (new ResourceLocation(ModInfo.ID + ":textures/gui/collidercontroller_gui_formed.png"));
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.renderEngine.bindTexture(texture);

        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;

        drawModalRectWithCustomSizedTexture(x, y, 66, 48, 320, 200, 66, 48);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {

        fontRendererObj.drawString("Ignis: ", 9, 9, Aspect.FIRE.getColor());
        fontRendererObj.drawString("Perditio: ", 9, 14, Aspect.ENTROPY.getColor());
        fontRendererObj.drawString("Aqua: ", 9, 19, Aspect.WATER.getColor());
        fontRendererObj.drawString("Aer: ", 9, 24, Aspect.AIR.getColor());
        fontRendererObj.drawString("Ordo: ", 9, 29, Aspect.ORDER.getColor());
        fontRendererObj.drawString("Terra:", 9, 34, Aspect.EARTH.getColor());

        fontRendererObj.drawString("" + tileEntity.getAmountIgnis(), 19, 9, Aspect.MOTION.getColor());
        fontRendererObj.drawString("" + tileEntity.getAmountPerditio(), 19, 14, Aspect.MOTION.getColor());
        fontRendererObj.drawString("" + tileEntity.getAmountAqua(), 19, 19, Aspect.MOTION.getColor());
        fontRendererObj.drawString("" + tileEntity.getAmountAer(), 19, 24, Aspect.MOTION.getColor());
        fontRendererObj.drawString("" + tileEntity.getAmountOrdo(), 19, 29, Aspect.MOTION.getColor());
        fontRendererObj.drawString("" + tileEntity.getAmountTerra(), 19, 34, Aspect.MOTION.getColor());
    }
}
