package me.lyle.minigame.listeners;

import me.lyle.minigame.Minigame;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerDeathListener implements Listener {
    private final Minigame plugin;

    public PlayerDeathListener(Minigame plugin) {
        this.plugin = plugin;
    }
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        if(plugin.gameSettings.gamemode == 5) {
            Entity deathWho = event.getEntity();
            if (deathWho instanceof Player p) {
                int tryWho = plugin.gameSettings.glassTurn % plugin.gameSettings.gamePlayers.size();
                if (p.getUniqueId() == plugin.gameSettings.gamePlayers.get(tryWho)) {
                    plugin.gameSettings.glassTurn++;
                    tryWho = plugin.gameSettings.glassTurn % plugin.gameSettings.gamePlayers.size();
                    for (Player player : plugin.getServer().getOnlinePlayers()) {
                        player.sendMessage(ChatColor.RED + player.getDisplayName() + "님은 실패했습니다..");
                        if (player.getUniqueId().equals(plugin.gameSettings.gamePlayers.get(tryWho))) {
                            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                                @Override
                                public void run() {
                                    player.sendMessage(ChatColor.YELLOW + "");
                                    player.sendMessage(ChatColor.YELLOW + "");
                                    player.sendMessage(ChatColor.YELLOW + "");
                                    player.sendMessage(ChatColor.YELLOW + "");
                                    player.sendMessage(ChatColor.RED + "" + Bukkit.getPlayer(plugin.gameSettings.gamePlayers.get(0)).getDisplayName() + ChatColor.WHITE + "님의 "
                                            + ChatColor.RED + (plugin.gameSettings.glassTurn / plugin.gameSettings.gamePlayers.size() + 1) + ChatColor.WHITE + "번째 도전");
                                    if (player.getUniqueId().equals(plugin.gameSettings.gamePlayers.get(0))) {
                                        player.teleport(player.getWorld().getBlockAt(-559, 221, -63).getLocation());
                                    }
                                }
                            }, 40L);
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event){
        Player player = event.getPlayer();
        World world = event.getPlayer().getWorld();
        if(plugin.gameSettings.gamemode == 1){
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                @Override
                public void run() {
                    player.teleport(world.getBlockAt(-610,98,30).getLocation());
                }
            }, 1L);
        }
        else if(plugin.gameSettings.gamemode == 2){
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                @Override
                public void run() {
                    player.teleport(world.getBlockAt(-641,84,33).getLocation());
                }
            }, 1L);
        }
        else if(plugin.gameSettings.gamemode == 3){
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                @Override
                public void run() {
                    player.teleport(world.getBlockAt(-761,63,120).getLocation());
                }
            }, 1L);
        }
        else if(plugin.gameSettings.gamemode == 4){
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                @Override
                public void run() {
                    player.teleport(world.getBlockAt(-600,63,15).getLocation());
                }
            }, 1L);
        }
        else if(plugin.gameSettings.gamemode == 5){
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                @Override
                public void run() {
                    player.teleport(world.getBlockAt(-527,228,-50).getLocation());
                }
            }, 1L);
        }
    }

}
