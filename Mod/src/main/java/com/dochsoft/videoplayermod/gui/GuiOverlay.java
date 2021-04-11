package com.dochsoft.videoplayermod.gui;

import com.dochsoft.videoplayermod.proxy.ClientProxy;
import com.dochsoft.videoplayermod.util.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.nio.ByteBuffer;

public class GuiOverlay extends Gui {
    public static Minecraft minecraft = Minecraft.getMinecraft();
    private static BufferedImage image = null;
    private static DynamicTexture dynamicTexture = null;
    private static ResourceLocation location = null;
    private static InGameTextureHandler inGameTextureHandler = null;

    private static boolean firstLoop = true;

    @SubscribeEvent
    public void onRenderGameOverlay(RenderGameOverlayEvent event) {
        if (event.isCanceled() || event.getType() != RenderGameOverlayEvent.ElementType.ALL) {
            return;
        }
        /*getPlayerHeadImage();
        drawOverlay();
        if (firstLoop) {
            location = new InGameTextureHandler(minecraft.getTextureManager(), new DynamicTexture(image)).getTextureLocation();
            firstLoop = false;
        }
        drawPlayerHead(location); */

        if (ClientProxy.videoName != null && ClientProxy.isPlaying) {
            PlayVideo.window.setSize(minecraft.displayWidth, minecraft.displayHeight);
            drawVideoFrame();
            //drawTimer();
            //PlayVideo.playVideo();
        }

    }

    public void getPlayerHeadImage() {
        if (image == null) {
            try{
                URL url = new URL("https://minotar.net/helm/"+ minecraft.player.getName() + "/16.png");
                image = ImageIO.read(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void drawVideoFrame() {
        int width = PlayVideo.window.getWidth();
        int height = PlayVideo.window.getHeight();
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = image.createGraphics();
        PlayVideo.window.printAll(graphics2D);
        graphics2D.dispose();
            /*try {
                OutputStream out = new FileOutputStream("pic.png");
                ImageIO.write(image, "PNG", out);
            } catch (IOException e) {} */
        if (inGameTextureHandler == null) {
            dynamicTexture = new DynamicTexture(image);
            inGameTextureHandler = new InGameTextureHandler(minecraft.getTextureManager(), dynamicTexture);
            location = inGameTextureHandler.getTextureLocation();
        } else {
            inGameTextureHandler.updateTextureData(image);
        }

        ScaledResolution scaled = new ScaledResolution(minecraft);
        int width2 = scaled.getScaledWidth();
        int height2 = scaled.getScaledHeight();
        int x1 = width2 / 100;
        int y1 = width2 / 100;

        GL11.glPushMatrix();
        minecraft.getTextureManager().bindTexture(location);
        GL11.glScalef(0.6f, 0.6f, 0.6f);
        drawModalRectWithCustomSizedTexture((x1 - 25), (y1 - 40), 0.0F, 0.0F, width, height, width, height);
        GL11.glPopMatrix();

        /*int[] pixels = new int[image.getWidth() * image.getHeight()];
        image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());
        ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * 3);

        for(int y = 0; y < image.getHeight(); y++){
            for(int x = 0; x < image.getWidth(); x++){
                int pixel = pixels[y * image.getWidth() + x];
                buffer.put((byte) ((pixel >> 16) & 0xFF));     // Red component
                buffer.put((byte) ((pixel >> 8) & 0xFF));      // Green component
                buffer.put((byte) (pixel & 0xFF));             // Blue component
            }
        }

        buffer.flip();
        int textureID = GL11.glGenTextures();

        GL11.glPushMatrix();
        GlStateManager.enableAlpha();
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width, height, 0, GL11.GL_RGB, GL11.GL_UNSIGNED_BYTE, buffer);
        minecraft.getTextureManager().
        GL11.glPopMatrix();*/
    }

    public void drawPlayerHead(ResourceLocation resourceLocation) {
        ScaledResolution scaled = new ScaledResolution(minecraft);
        int width = scaled.getScaledWidth();
        int height = scaled.getScaledHeight();
        int x1 = width / 100;
        int y1 = width / 100;

        GL11.glPushMatrix();
        minecraft.getTextureManager().bindTexture(resourceLocation);
        GL11.glScalef(0.46f, 0.46f, 0.46f);
        drawModalRectWithCustomSizedTexture((x1 + 20), (y1 + 40), 0.0F, 0.0F, 32, 32, 32.0F, 32.0F);
        GL11.glPopMatrix();
    }

    public void drawOverlay() {
        ScaledResolution scaled = new ScaledResolution(minecraft);
        int width = scaled.getScaledWidth();
        int height = scaled.getScaledHeight();
        int x1 = width / 100;
        int y1 = width / 100;
        int x2 = width / 100 + 80;
        int y2 = width / 100 + 22;

        float xx = (float)width / 1280.0F;
        float yy = (float)height / 720.0F;

        GL11.glPushMatrix();
        //GlStateManager.enableBlend();
        GlStateManager.enableAlpha();
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glScalef(0.5F, 0.5F, 0.5F);
        minecraft.getTextureManager().bindTexture(new ResourceLocation(Reference.MODID, "textures/gui/segment_gameoverlay.png"));
        drawModalRectWithCustomSizedTexture(x1 + 2, y1 + 2, 0.0F, 0.0F, 1280, 720, 1280.0F, 720.0F);
       // GlStateManager.disableBlend();
        GlStateManager.disableAlpha();
        GL11.glPopMatrix();

        GL11.glPushMatrix();
        GL11.glScalef(1.2f, 1.2f, 1.2f);
        // (width / 2) + (width / 8)
        minecraft.fontRenderer.drawString(TextFormatting.GOLD + ClientProxy.videoName + TextFormatting.WHITE, (x1 + 27), y1 + 15.2F, 16777215, false);
        GL11.glPopMatrix();
    }

    public void drawTimer() {
        ScaledResolution scaled = new ScaledResolution(minecraft);
        int width = scaled.getScaledWidth();
        int height = scaled.getScaledHeight();
        int x1 = width / 100;
        int y1 = width / 100;
        float xx = (float)width / 1280.0F;
        float yy = (float)height / 720.0F;

        int textXLoc;
        float textYLoc;
        String backgroundImgPath;

        if (width < 800) {//1280 = 640, 1920 = 960
            textXLoc = (width / 2) + (width / 10) + 24;
            textYLoc = 8.1F;
            backgroundImgPath = "textures/gui/timer_1280.png";
        } else {
            textXLoc = (width / 2) + (width / 9) + 34;
            textYLoc = 9.2F;
            backgroundImgPath = "textures/gui/timer_1920.png";
        }

        GL11.glPushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.enableAlpha();
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glScaled((double) xx, (double) yy, 1.0D);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        //GL11.glScalef(0.5F, 0.5F, 0.5F);
        minecraft.getTextureManager().bindTexture(new ResourceLocation(Reference.MODID, backgroundImgPath));
        drawModalRectWithCustomSizedTexture(x1 + 2, y1 + 2, 0.0F, 0.0F, 1280, 720, 1280.0F, 720.0F);
        GlStateManager.disableBlend();
        GlStateManager.disableAlpha();
        GL11.glPopMatrix();

        GL11.glPushMatrix();
        GL11.glScalef(1.4f, 1.4f, 1.4f);
        minecraft.fontRenderer.drawString(ClientProxy.videoName, textXLoc, y1 + textYLoc, 16777215, false);
        GL11.glPopMatrix();
    }
}
