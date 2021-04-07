package com.dochsoft.videoplayerplugin;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class VideoPlayerPlugin extends JavaPlugin {
    private static VideoPlayerPlugin Instance;

    @Override
    public void onEnable() {
        Instance = this;

        getServer().getMessenger().registerOutgoingPluginChannel(this, "VIDEOPLAYER");
        getCommand("동영상").setExecutor(new VideoPlayCommand());

        Bukkit.getConsoleSender().sendMessage(Reference.prefix_normal + Reference.ENABLE_MESSAGE);
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(Reference.prefix_normal + Reference.DISABLE_MESSAGE);
    }

    public static VideoPlayerPlugin getInstance() { return Instance; }
}
