package me.lyle.minigame.command;

import me.lyle.minigame.Minigame;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class StartDiscussCommand implements CommandExecutor {
    private final Minigame plugin;

    public StartDiscussCommand(Minigame plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String args[]) {
        if(sender instanceof Player p){
            if(plugin.gameSettings.moderator.equals(p.getUniqueId()) && plugin.gameSettings.gamemode == 2 && plugin.gameSettings.gamestep == 3){
                plugin.gameSettings.gamestep = 4;
                for (UUID playerUUID : plugin.gameSettings.firstTopicPlayers) {
                    Player player = Bukkit.getPlayer(playerUUID);
                    if(player != null) {
                        player.sendMessage(ChatColor.YELLOW + "");
                        player.sendMessage(ChatColor.YELLOW + "");
                        player.sendMessage(ChatColor.YELLOW + "");
                        player.sendMessage(ChatColor.YELLOW + "");
                        player.sendMessage(ChatColor.YELLOW + "");
                        player.sendMessage(ChatColor.YELLOW + "");
                        player.sendMessage(ChatColor.YELLOW + "");
                        player.sendMessage(ChatColor.YELLOW + "");
                        player.sendMessage(ChatColor.YELLOW + "");
                        player.sendMessage(ChatColor.YELLOW + "");
                        player.sendMessage(ChatColor.YELLOW + "");
                        player.sendMessage(ChatColor.YELLOW + "");
                        player.sendMessage(ChatColor.YELLOW + "토론을 시작하겠습니다, 모두 자리에 위치해주세요.");
                        player.teleport(player.getWorld().getBlockAt(-633,84,23).getLocation());
                    }
                }
                for (UUID playerUUID : plugin.gameSettings.secondTopicPlayers) {
                    Player player = Bukkit.getPlayer(playerUUID);
                    if(player != null) {
                        player.sendMessage(ChatColor.YELLOW + "");
                        player.sendMessage(ChatColor.YELLOW + "");
                        player.sendMessage(ChatColor.YELLOW + "");
                        player.sendMessage(ChatColor.YELLOW + "");
                        player.sendMessage(ChatColor.YELLOW + "");
                        player.sendMessage(ChatColor.YELLOW + "");
                        player.sendMessage(ChatColor.YELLOW + "");
                        player.sendMessage(ChatColor.YELLOW + "");
                        player.sendMessage(ChatColor.YELLOW + "");
                        player.sendMessage(ChatColor.YELLOW + "");
                        player.sendMessage(ChatColor.YELLOW + "");
                        player.sendMessage(ChatColor.YELLOW + "");
                        player.sendMessage(ChatColor.YELLOW + "토론을 시작하겠습니다, 모두 자리에 위치해주세요.");
                        player.teleport(player.getWorld().getBlockAt(-633, 84, 42).getLocation());
                    }
                }
                Player moderator = Bukkit.getPlayer(plugin.gameSettings.moderator);
                if(moderator != null) {
                    moderator.sendMessage(ChatColor.RED + "토론이 끝나면 [/토론종료]를 입력해주세요.");
                    moderator.teleport(moderator.getWorld().getBlockAt(-625, 86, 33).getLocation());
                }
            }
        }
        return true;
    }

}
