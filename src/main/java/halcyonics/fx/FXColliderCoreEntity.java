package halcyonics.fx;//

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class FXColliderCoreEntity extends EntityFX {

    ResourceLocation event0 = new ResourceLocation("halcyonics", "textures/entity/reactorEvent0.png");
    ResourceLocation event1 = new ResourceLocation("halcyonics", "textures/entity/reactorEvent1.png");
    ResourceLocation event2 = new ResourceLocation("halcyonics", "textures/entity/reactorEvent2.png");
    ResourceLocation event3 = new ResourceLocation("halcyonics", "textures/entity/reactorEvent3.png");
    ResourceLocation event4 = new ResourceLocation("halcyonics", "textures/entity/reactorEvent4.png");
    ResourceLocation event5 = new ResourceLocation("halcyonics", "textures/entity/reactorEvent5.png");
    ResourceLocation usable = null;

    int rotation = 0;
    float sx = 0.5F;
    float sy = 0.5F;
    float sz = 0.5F;
    float rotationX = 0.0F;
    float rotationZ = 0.0F;
    float rotationY = 0.0F;

    public FXColliderCoreEntity(World world, double d, double d1, double d2, float scale) {
        super(world, d, d1, d2, 0.0D, 0.0D, 0.0D);
        this.particleGravity = 0.0F;
        this.motionX = this.motionY = this.motionZ = 0.0D;
        this.particleMaxAge = 12 + this.rand.nextInt(5);
        this.noClip = false;
        this.setSize(scale, scale);
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.noClip = true;
        this.particleScale = scale;
        this.rotation = this.rand.nextInt(360);
        this.sx = MathHelper.clamp_float(0.0F - 0.6F + this.rand.nextFloat() * 0.2F, -0.4F, 0.4F);
        this.sy = MathHelper.clamp_float(0.0F - 0.6F + this.rand.nextFloat() * 0.2F, -0.4F, 0.4F);
        this.sz = MathHelper.clamp_float(0.0F - 0.6F + this.rand.nextFloat() * 0.2F, -0.4F, 0.4F);
        this.particleMaxAge = 5;

        int ran = rand.nextInt(6);
        switch (ran) {
            case 0: {
                usable = event0;
                break;
            }
            case 1: {
                usable = event1;
                break;
            }
            case 2: {
                usable = event2;
                break;
            }
            case 3: {
                usable = event3;
                break;
            }
            case 4: {
                usable = event4;
                break;
            }
            case 5: {
                usable = event5;
                break;
            }
        }
    }

    public void renderParticle(WorldRenderer wr, Entity p_180434_2_, float f, float f1, float f2, float f3, float f4, float f5) {
        Tessellator.getInstance().draw();
        GL11.glPushMatrix();
        Minecraft.getMinecraft().renderEngine.bindTexture(usable);
        GL11.glDepthMask(false);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);

        float var13 = (float) (this.prevPosX + (this.posX - this.prevPosX) * (double) f - interpPosX);
        float var14 = (float) (this.prevPosY + (this.posY - this.prevPosY) * (double) f - interpPosY);
        float var15 = (float) (this.prevPosZ + (this.posZ - this.prevPosZ) * (double) f - interpPosZ);
        GL11.glTranslated(var13 + this.sx + 0.9D, var14 + this.sy + 0.9D, var15 + this.sz + 0.9D);

        float var12 = this.particleScale;
        float var16 = 1.0F;
        wr.begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
        short i = 240;
        int j = i;
        int k = i;

        GL11.glRotatef(90.0F, rotationX, rotationY, rotationZ);
        GL11.glRotatef((float) this.rotation, 0.0F, 0.0F, 1.0F);
        wr.pos(-0.5D * (double) var12, 0.5D * (double) var12, 0.0D).tex(0.0D, 1.0D).color(this.particleRed * var16, this.particleGreen * var16, this.particleBlue * var16, this.particleAlpha * 2.0F).lightmap(j, k).endVertex();
        wr.pos(0.5D * (double) var12, 0.5D * (double) var12, 0.0D).tex(1.0D, 1.0D).color(this.particleRed * var16, this.particleGreen * var16, this.particleBlue * var16, this.particleAlpha * 2.0F).lightmap(j, k).endVertex();
        wr.pos(0.5D * (double) var12, -0.5D * (double) var12, 0.0D).tex(1.0D, 0.0D).color(this.particleRed * var16, this.particleGreen * var16, this.particleBlue * var16, this.particleAlpha * 2.0F).lightmap(j, k).endVertex();
        wr.pos(-0.5D * (double) var12, -0.5D * (double) var12, 0.0D).tex(0.0D, 0.0D).color(this.particleRed * var16, this.particleGreen * var16, this.particleBlue * var16, this.particleAlpha * 2.0F).lightmap(j, k).endVertex();

        GL11.glRotatef(90.0F, rotationX, rotationY, rotationZ);
        GL11.glRotatef((float) this.rotation, 1.0F, 0.0F, 1.0F);
        wr.pos(-0.5D * (double) var12, 0.5D * (double) var12, 0.0D).tex(0.0D, 1.0D).color(this.particleRed * var16, this.particleGreen * var16, this.particleBlue * var16, this.particleAlpha * 2.0F).lightmap(j, k).endVertex();
        wr.pos(0.5D * (double) var12, 0.5D * (double) var12, 0.0D).tex(1.0D, 1.0D).color(this.particleRed * var16, this.particleGreen * var16, this.particleBlue * var16, this.particleAlpha * 2.0F).lightmap(j, k).endVertex();
        wr.pos(0.5D * (double) var12, -0.5D * (double) var12, 0.0D).tex(1.0D, 0.0D).color(this.particleRed * var16, this.particleGreen * var16, this.particleBlue * var16, this.particleAlpha * 2.0F).lightmap(j, k).endVertex();
        wr.pos(-0.5D * (double) var12, -0.5D * (double) var12, 0.0D).tex(0.0D, 0.0D).color(this.particleRed * var16, this.particleGreen * var16, this.particleBlue * var16, this.particleAlpha * 2.0F).lightmap(j, k).endVertex();

        GL11.glRotatef(90.0F, rotationX, rotationY, rotationZ);
        GL11.glRotatef((float) this.rotation, 1.0F, 1.0F, 1.0F);
        wr.pos(-0.5D * (double) var12, 0.5D * (double) var12, 0.0D).tex(0.0D, 1.0D).color(this.particleRed * var16, this.particleGreen * var16, this.particleBlue * var16, this.particleAlpha * 2.0F).lightmap(j, k).endVertex();
        wr.pos(0.5D * (double) var12, 0.5D * (double) var12, 0.0D).tex(1.0D, 1.0D).color(this.particleRed * var16, this.particleGreen * var16, this.particleBlue * var16, this.particleAlpha * 2.0F).lightmap(j, k).endVertex();
        wr.pos(0.5D * (double) var12, -0.5D * (double) var12, 0.0D).tex(1.0D, 0.0D).color(this.particleRed * var16, this.particleGreen * var16, this.particleBlue * var16, this.particleAlpha * 2.0F).lightmap(j, k).endVertex();
        wr.pos(-0.5D * (double) var12, -0.5D * (double) var12, 0.0D).tex(0.0D, 0.0D).color(this.particleRed * var16, this.particleGreen * var16, this.particleBlue * var16, this.particleAlpha * 2.0F).lightmap(j, k).endVertex();


        Tessellator.getInstance().draw();
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDepthMask(true);
        GL11.glPopMatrix();
        wr.begin(7, DefaultVertexFormats.POSITION_NORMAL);
    }

    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.particleScale = particleScale + 0.5F;
        float threshold = (float) this.particleMaxAge / 5.0F;
        if ((float) this.particleAge <= threshold) {
            this.particleAlpha = (float) this.particleAge / threshold;
        } else {
            this.particleAlpha = (float) (this.particleMaxAge - this.particleAge) / (float) this.particleMaxAge;
        }

        if (this.particleAge++ >= this.particleMaxAge) {
            this.setDead();
        }
        this.rotationX = rotationX + 0.1F;
        this.rotationZ = rotationZ + 0.1F;
        this.rotationY = rotationZ + 0.1F;
    }

    @Override
    public int getFXLayer() {
        return 2;
    }

    @Override
    public int getBrightnessForRender(float partialTicks) {
        return 15;
    }

    @Override
    public float getBrightness(float partialTicks) {
        return 15;
    }
}
