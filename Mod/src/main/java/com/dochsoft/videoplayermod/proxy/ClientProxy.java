package com.dochsoft.videoplayermod.proxy;

import com.dochsoft.videoplayermod.VideoPlayerMod;
import com.dochsoft.videoplayermod.gui.GuiOverlay;
import com.dochsoft.videoplayermod.gui.PlayVideo;
import io.netty.buffer.ByteBufUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLEventChannel;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.internal.FMLProxyPacket;

import java.nio.charset.Charset;

public class ClientProxy extends CommonProxy {
    private static Minecraft minecraft = Minecraft.getMinecraft();
    public static final FMLEventChannel channel = NetworkRegistry.INSTANCE.newEventDrivenChannel("VIDEOPLAYER");

    public static String videoName = null;
    public static Boolean isPlaying = false;

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
        channel.register(this);
        MinecraftForge.EVENT_BUS.register(new GuiOverlay());

        MinecraftForge.EVENT_BUS.register(new VideoPlayerMod());
    }

    @Override
    public void postinit(FMLPostInitializationEvent event) {
    }

    @Override
    public void registerItemRenderer(Item item, int meta, String id) {
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), id));
    }

    @SubscribeEvent
    public void onClientCustomPacket(FMLNetworkEvent.ClientCustomPacketEvent event) {
        FMLProxyPacket packet = event.getPacket();

        if (packet.channel().equalsIgnoreCase("VIDEOPLAYER")) {
            String packetData = new String(ByteBufUtil.getBytes(packet.payload()), Charset.forName("UTF-8"));
            String[] list = packetData.split("\\|\\|");
            if (packetData.startsWith("PLAY")) {
                System.out.println("Playing");
                videoName = list[1];
                PlayVideo.stopVideo();
                PlayVideo.playVideo();
            } else if (packetData.startsWith("STOP")) {
                PlayVideo.stopVideo();
            }
        }
    }

}
