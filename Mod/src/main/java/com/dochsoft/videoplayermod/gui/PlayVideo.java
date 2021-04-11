package com.dochsoft.videoplayermod.gui;

import com.dochsoft.videoplayermod.proxy.ClientProxy;
import com.dochsoft.videoplayermod.util.Reference;
import com.dochsoft.videoplayermod.util.VideoUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import org.freedesktop.gstreamer.*;
import org.freedesktop.gstreamer.elements.PlayBin;
import org.freedesktop.gstreamer.event.SeekFlags;
import org.freedesktop.gstreamer.swing.GstVideoComponent;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.EnumSet;
import java.util.concurrent.TimeUnit;

public class PlayVideo {
    private static Pipeline pipeline;
    public static PlayBin playbin;
    public static JFrame window;

    public static void playVideo() {
        VideoUtils.configurePaths();
        Gst.init(Version.BASELINE, "VideoPlayer-Mod");

        /*System.out.println(Reference.ROOTDIRECTORY + "\\" + ClientProxy.videoName);
        System.out.println("C:\\Users\\wona0\\AppData\\Roaming\\.fullmoonlauncher\\instances\\catchOrNot-1.12.2"+  "\\" + ClientProxy.videoName);
        pipeline = (Pipeline) Gst.parseLaunch("C:\\Users\\wona0\\AppData\\Roaming\\.fullmoonlauncher\\instances\\catchOrNot-1.12.2"+  "\\" + ClientProxy.videoName + " ! autovideosink");
        pipeline.play();
        Gst.getExecutor().schedule(Gst::quit, 10, TimeUnit.SECONDS);
        Gst.main(); */

        GstVideoComponent vc = new GstVideoComponent();

        /**
         * Create a PlayBin element and set the AppSink from the Swing
         * component as the video sink.
         */

        playbin = new PlayBin("playbin");
        playbin.setVideoSink(vc.getElement());

        window = new JFrame("Video Player");
        window.add(vc);

        Minecraft minecraft = Minecraft.getMinecraft();

        vc.setPreferredSize(new Dimension(minecraft.displayWidth, minecraft.displayHeight));

        window.pack();
        //window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        window.setVisible(true);

        File file = new File("C:\\Users\\wona0\\AppData\\Roaming\\.fullmoonlauncher\\instances\\catchOrNot-1.12.2"+  "\\" + ClientProxy.videoName);
        playbin.stop();
        playbin.setURI(file.toURI());
        playbin.setVolume(0.3);
        playbin.play();

        playbin.getBus().connect((Bus.EOS) source -> {
            // handle on Swing thread!
            EventQueue.invokeLater(() -> {
                playbin.stop();
                ClientProxy.isPlaying = false;
                PlayVideo.window.setVisible(false);
                PlayVideo.window.dispose();
            });
        });

        ClientProxy.isPlaying = true;
        System.out.println("Play Start!");

    }

    public static void stopVideo() {
        if (playbin != null && window != null) {
            ClientProxy.isPlaying = false;
            PlayVideo.playbin.stop();
            PlayVideo.window.setVisible(false);
            PlayVideo.window.dispose();
        }
    }
}
