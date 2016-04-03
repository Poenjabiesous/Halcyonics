package halcyonics.fx;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.FMLClientHandler;
import org.lwjgl.opengl.GL11;

public class FXAuraBeamEntity extends EntityFX {
    public int particle = 16;
    private double offset = 0.0D;
    private double toX = 0.0D;
    private double toY = 0.0D;
    private double toZ = 0.0D;
    private double ptX = 0.0D;
    private double ptY = 0.0D;
    private double ptZ = 0.0D;
    private float length = 0.0F;
    private float rotYaw = 0.0F;
    private float rotPitch = 0.0F;
    private float prevYaw = 0.0F;
    private float prevPitch = 0.0F;
    private Entity targetEntity = null;
    private int type = 0;
    private float endMod = 1.0F;
    private boolean reverse = false;
    private boolean pulse = true;
    private int rotationspeed = 5;
    private float prevSize = 0.0F;
    public int impact;

    ResourceLocation beam = new ResourceLocation("halcyonics", "textures/entity/beam.png");
    public FXAuraBeamEntity(World par1World, double fromX, double fromY, double fromZ, double toX, double toY, double toZ) {
        super(par1World, fromX, fromY, fromZ, 0.0D, 0.0D, 0.0D);
        this.setSize(1.00F, 1.00F);
        this.noClip = true;
        this.motionX = 0.0D;
        this.motionY = 0.0D;
        this.motionZ = 0.0D;
        this.toX = toX;
        this.toY = toY;
        this.toZ = toZ;
        this.prevYaw = this.rotYaw;
        this.prevPitch = this.rotPitch;
        this.particleMaxAge = 5;
        this.particleScale = 1.0F;

        Entity renderentity = FMLClientHandler.instance().getClient().getRenderViewEntity();
        byte visibleDistance = 42;

        if (renderentity != null && renderentity.getDistance(this.posX, this.posY, this.posZ) > (double) visibleDistance) {
            this.particleMaxAge = 0;
        }
    }

    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY + this.offset;
        this.prevPosZ = this.posZ;
        this.ptX = this.toX;
        this.ptY = this.toY;
        this.ptZ = this.toZ;
        this.prevYaw = this.rotYaw;
        this.prevPitch = this.rotPitch;
        float xd = (float) (this.posX - this.toX);
        float yd = (float) (this.posY - this.toY);
        float zd = (float) (this.posZ - this.toZ);
        this.length = MathHelper.sqrt_float(xd * xd + yd * yd + zd * zd);
        double var7 = (double) MathHelper.sqrt_double((double) (xd * xd + zd * zd));
        this.rotYaw = (float) (Math.atan2((double) xd, (double) zd) * 180.0D / 3.141592653589793D);
        this.rotPitch = (float) (Math.atan2((double) yd, var7) * 180.0D / 3.141592653589793D);
        this.prevYaw = this.rotYaw;
        this.prevPitch = this.rotPitch;
        if (this.impact > 0) {
            --this.impact;
        }

        if (this.particleAge++ >= this.particleMaxAge) {
            this.setDead();
        }

    }


    public void renderParticle(WorldRenderer wr, Entity p_180434_2_, float f, float f1, float f2, float f3, float f4, float f5) {
        Tessellator.getInstance().draw();
        GL11.glPushMatrix();
        float var9 = 1.0F;
        float slide = (float) Minecraft.getMinecraft().thePlayer.ticksExisted;
        float rot = (float) (this.worldObj.provider.getWorldTime() % (long) (360 / this.rotationspeed) * (long) this.rotationspeed) + (float) this.rotationspeed * f;
        float size = 1.0F;
        if (this.pulse) {
            size = Math.min((float) this.particleAge / 4.0F, 1.0F);
            size = (float) ((double) this.prevSize + (double) (size - this.prevSize) * (double) f);
        }

        float op = 0.4F;
        if (this.pulse && this.particleMaxAge - this.particleAge <= 4) {
            op = 0.4F - (float) (4 - (this.particleMaxAge - this.particleAge)) * 0.1F;
        }

        Minecraft.getMinecraft().renderEngine.bindTexture(this.beam);


        GL11.glTexParameterf(3553, 10242, 10497.0F);
        GL11.glTexParameterf(3553, 10243, 10497.0F);
        GL11.glDisable(2884);
        float var11 = slide + f;
        if (this.reverse) {
            var11 *= -1.0F;
        }

        float var12 = -var11 * 0.2F - (float) MathHelper.floor_float(-var11 * 0.1F);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 1);
        GL11.glDepthMask(false);
        float xx = (float) (this.prevPosX + (this.posX - this.prevPosX) * (double) f - interpPosX);
        float yy = (float) (this.prevPosY + (this.posY - this.prevPosY) * (double) f - interpPosY);
        float zz = (float) (this.prevPosZ + (this.posZ - this.prevPosZ) * (double) f - interpPosZ);
        GL11.glTranslated((double) xx, (double) yy, (double) zz);
        float ry = (float) ((double) this.prevYaw + (double) (this.rotYaw - this.prevYaw) * (double) f);
        float rp = (float) ((double) this.prevPitch + (double) (this.rotPitch - this.prevPitch) * (double) f);
        GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
        GL11.glRotatef(180.0F + ry, 0.0F, 0.0F, -1.0F);
        GL11.glRotatef(rp, 1.0F, 0.0F, 0.0F);
        double var44 = -0.15D * (double) size;
        double var17 = 0.15D * (double) size;
        double var44b = -0.15D * (double) size * (double) this.endMod;
        double var17b = 0.15D * (double) size * (double) this.endMod;
        short i = 200;
        int j = i >> 16 & '\uffff';
        int k = i & '\uffff';
        GL11.glRotatef(rot, 0.0F, 1.0F, 0.0F);

        for (int t = 0; t < 3; ++t) {
            double var29 = (double) (this.length * size * var9);
            double var31 = 0.0D;
            double var33 = 1.0D;
            double var35 = (double) (-1.0F + var12 + (float) t / 3.0F);
            double var37 = (double) (this.length * size * var9) + var35;
            GL11.glRotatef(60.0F, 0.0F, 1.0F, 0.0F);
            wr.begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
            wr.pos(var44b, var29, 0.0D).tex(var33, var37).color(this.particleRed, this.particleGreen, this.particleBlue, op).lightmap(j, k).endVertex();
            wr.pos(var44, 0.0D, 0.0D).tex(var33, var35).color(this.particleRed, this.particleGreen, this.particleBlue, op).lightmap(j, k).endVertex();
            wr.pos(var17, 0.0D, 0.0D).tex(var31, var35).color(this.particleRed, this.particleGreen, this.particleBlue, op).lightmap(j, k).endVertex();
            wr.pos(var17b, var29, 0.0D).tex(var31, var37).color(this.particleRed, this.particleGreen, this.particleBlue, op).lightmap(j, k).endVertex();
            Tessellator.getInstance().draw();
        }

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDepthMask(true);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3042);
        GL11.glEnable(2884);
        GL11.glPopMatrix();
        if (this.impact > 0) {
            this.renderImpact(Tessellator.getInstance(), f, f1, f2, f3, f4, f5);
        }

        wr.begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
        this.prevSize = size;
    }

    public void renderImpact(Tessellator tessellator, float f, float f1, float f2, float f3, float f4, float f5) {
        GL11.glPushMatrix();
        GL11.glDepthMask(false);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 1);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.66F);
        int part = this.particleAge % 16;
        float var8 = (float) part / 16.0F;
        float var9 = var8 + 0.0624375F;
        float var10 = 0.3125F;
        float var11 = var10 + 0.0624375F;
        float var12 = this.endMod / 2.0F / (float) (6 - this.impact);
        float var13 = (float) (this.ptX + (this.toX - this.ptX) * (double) f - interpPosX);
        float var14 = (float) (this.ptY + (this.toY - this.ptY) * (double) f - interpPosY);
        float var15 = (float) (this.ptZ + (this.toZ - this.ptZ) * (double) f - interpPosZ);
        short i = 200;
        int j = i >> 16 & '\uffff';
        int k = i & '\uffff';
        tessellator.getWorldRenderer().begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
        tessellator.getWorldRenderer().pos((double) (var13 - f1 * var12 - f4 * var12), (double) (var14 - f2 * var12), (double) (var15 - f3 * var12 - f5 * var12)).tex((double) var9, (double) var11).color(this.particleRed, this.particleGreen, this.particleBlue, 0.66F).lightmap(j, k).endVertex();
        tessellator.getWorldRenderer().pos((double) (var13 - f1 * var12 + f4 * var12), (double) (var14 + f2 * var12), (double) (var15 - f3 * var12 + f5 * var12)).tex((double) var9, (double) var10).color(this.particleRed, this.particleGreen, this.particleBlue, 0.66F).lightmap(j, k).endVertex();
        tessellator.getWorldRenderer().pos((double) (var13 + f1 * var12 + f4 * var12), (double) (var14 + f2 * var12), (double) (var15 + f3 * var12 + f5 * var12)).tex((double) var8, (double) var10).color(this.particleRed, this.particleGreen, this.particleBlue, 0.66F).lightmap(j, k).endVertex();
        tessellator.getWorldRenderer().pos((double) (var13 + f1 * var12 - f4 * var12), (double) (var14 - f2 * var12), (double) (var15 + f3 * var12 - f5 * var12)).tex((double) var8, (double) var11).color(this.particleRed, this.particleGreen, this.particleBlue, 0.66F).lightmap(j, k).endVertex();
        tessellator.draw();
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3042);
        GL11.glDepthMask(true);
        GL11.glPopMatrix();
    }
}
