package me.lyle.minigame.utills;

import me.lyle.minigame.Minigame;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.UUID;

public class GiveMoney {
    private final Minigame plugin;

    public GiveMoney(Minigame plugin) {
        this.plugin = plugin;
    }

    public static void GiveMoneyPlayer(UUID uuid, int amount,Minigame plugin){
        if(plugin.moneyHashmap.containsKey(uuid)) {
            Player target = plugin.getServer().getPlayer(uuid);
            plugin.moneyHashmap.put(uuid, plugin.moneyHashmap.get(uuid) + amount);
            plugin.playerManager.updateMoneyHUD(uuid, plugin.moneyHashmap.get(uuid));
            if(target != null) {
                if (plugin.moneyHashmap.get(uuid) >= 1000000 && amount > 0) {
                    plugin.gameSettings.gamemode = 0;
                    plugin.gameSettings.gamestep = 0;
                    for (Player player : plugin.getServer().getOnlinePlayers()) {

                        player.sendMessage(ChatColor.WHITE + "");
                        player.sendMessage(ChatColor.WHITE + "");
                        player.sendMessage(ChatColor.WHITE + "");
                        player.sendMessage(ChatColor.WHITE + "");
                        player.sendMessage(ChatColor.WHITE + "");
                        player.sendMessage(ChatColor.WHITE + "");
                        player.sendMessage(ChatColor.WHITE + "");
                        player.sendMessage(ChatColor.WHITE + "");
                        player.sendMessage(ChatColor.WHITE + "");
                        player.sendMessage(ChatColor.WHITE + "");
                        player.sendMessage(ChatColor.GREEN + "우승자가 탄생했습니다!");
                        player.sendMessage(ChatColor.GREEN + "우승자는 ...");
                        player.playSound(player.getLocation(), "my_sounds:sound_success", 1.0f, 1.0f);
                        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                            @Override
                            public void run() {
                                player.sendMessage(ChatColor.YELLOW + "");
                                player.sendMessage(ChatColor.YELLOW + "");
                                player.sendMessage(ChatColor.YELLOW + "");
                                player.sendMessage(ChatColor.YELLOW + "");
                                player.sendMessage(ChatColor.YELLOW + "");
                                player.sendMessage(ChatColor.YELLOW + "");
                                player.sendMessage(ChatColor.YELLOW + "");
                                player.sendMessage(ChatColor.YELLOW + "");
                                player.sendMessage(ChatColor.WHITE + " 우승자는 " + ChatColor.RED + target.getDisplayName() + ChatColor.WHITE +"님 입니다!");
                                player.playSound(player.getLocation(), "my_sounds:sound_gameend", 1.0f, 1.0f);
                            }
                        }, 100L);
                    }
                }
            }
        }
        else{
            plugin.moneyHashmap.put(uuid, amount);
            plugin.playerManager.updateMoneyHUD(uuid, amount);
        }
    }
}
