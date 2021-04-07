package com.dochsoft.videoplayerplugin;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VideoPlayCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("종료")) {
                if (args[1].equalsIgnoreCase("전체")) {
                    for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                        String temp = "STOP";
                        onlinePlayer.sendPluginMessage(VideoPlayerPlugin.getInstance(), "VIDEOPLAYER", temp.getBytes());
                    }
                } else {
                    Player player = Bukkit.getPlayer(args[1]);

                    if (player == null) {
                        sender.sendMessage(Reference.prefix_error + "플레이어가 접속중이지 않습니다. 정확한 플레이어 이름인지 다시 한 번 확인해주십시오.");
                    }
                    String temp = "STOP||";
                    player.sendPluginMessage(VideoPlayerPlugin.getInstance(), "VIDEOPLAYER", temp.getBytes());
                }
            } else {
                sendCommandHelpMessage(sender);
            }
        } else if (args.length == 3) {
            if (args[0].equalsIgnoreCase("재생")) {
                if (args[1].equalsIgnoreCase("전체")) {
                    for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                        String temp = "PLAY||" + args[2];
                        onlinePlayer.sendPluginMessage(VideoPlayerPlugin.getInstance(), "VIDEOPLAYER", temp.getBytes());
                    }
                } else {
                    Player player = Bukkit.getPlayer(args[1]);

                    String temp = "PLAY||" + args[2];
                    player.sendPluginMessage(VideoPlayerPlugin.getInstance(), "VIDEOPLAYER", temp.getBytes());
                }
            } else {
                sendCommandHelpMessage(sender);
            }
        } else {
            sendCommandHelpMessage(sender);
        }


        return false;
    }

    private static void sendCommandHelpMessage(CommandSender sender) {
        sender.sendMessage("§6/동영상 재생 §b<전체 / 마인크래프트 닉네임> <동영상이름> §7- 동영상을 재생합니다. \n - 동영상 이름 예시: test.mp4");
        sender.sendMessage("§6/동영상 종료 §b<전체 / 마인크래프트 닉네임> §7- 동영상을 종료합니다.");
    }
}
