package me.lyle.minigame.listeners;

import com.gikk.twirk.Twirk;
import me.lyle.minigame.Minigame;
import me.lyle.minigame.settings.GameSettings;
import me.lyle.minigame.twitch.twitchBot;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket;
import net.minecraft.network.protocol.game.PacketPlayOutEntity;
import net.minecraft.network.protocol.game.PacketPlayOutEntityHeadRotation;
import net.minecraft.network.protocol.game.PacketPlayOutLookAt;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.network.PlayerConnection;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.UUID;

import static me.lyle.minigame.command.StartGame.startGame;
import static me.lyle.minigame.utills.GiveMoney.GiveMoneyPlayer;

public class PlayerMoveListener implements Listener {
    private final Minigame plugin;

    public PlayerMoveListener(Minigame plugin) {
        this.plugin = plugin;
    }
    @EventHandler
    public void OnPlayerMove(PlayerMoveEvent event){
        Player player = event.getPlayer();
        plugin.gameSettings.npcList.stream()
                .forEach(npc ->{
                    Location location = npc.getBukkitEntity().getLocation();
                    location.setDirection(player.getLocation().subtract(location).toVector());
                    float yaw = location.getYaw();
                    float pitch = location.getPitch();
                    PlayerConnection ps = ((CraftPlayer)player).getHandle().b;
                    ps.a(new PacketPlayOutEntityHeadRotation(npc, (byte) ((yaw%360)*256/360) ));
                    ps.a(new PacketPlayOutEntity.PacketPlayOutEntityLook(npc.getBukkitEntity().getEntityId(), (byte) ((yaw%360)*256/360), (byte) ((pitch%360)*256/360), true));
                });
        if(plugin.gameSettings.gamemode == 3){
            Block playerOnBlock = player.getLocation().getBlock().getRelative(BlockFace.DOWN);
            if(playerOnBlock.getType().equals(Material.REDSTONE_BLOCK) && player.getLocation().distance(new Location(player.getWorld(), -691, 63,118))< 5){
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.sendMessage(ChatColor.YELLOW + "");
                    p.sendMessage(ChatColor.YELLOW + "");
                    p.sendMessage(ChatColor.YELLOW + "");
                    p.sendMessage(ChatColor.YELLOW + "");
                    p.sendMessage(ChatColor.YELLOW + "");
                    p.sendMessage(ChatColor.YELLOW + "");
                    p.sendMessage(ChatColor.YELLOW + "");
                    p.sendMessage(ChatColor.YELLOW + "");
                    p.sendMessage(ChatColor.WHITE + "축하합니다! 우승자는 "+ ChatColor.RED + player.getDisplayName() + ChatColor.WHITE + "님 입니다!");
                    p.playSound(player.getLocation(), "my_sounds:sound_success", 1.0f, 1.0f);
                }
                int reward = plugin.gameSettings.reward3;
                player.sendMessage(ChatColor.GREEN + "게임에서 승리했습니다! [+" + reward+"원]");
                plugin.gameSettings.gamemode = 0;
                GiveMoneyPlayer(player.getUniqueId(), reward, plugin);
                Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                    @Override
                    public void run() {
                        startGame(plugin);
                    }
                }, 100L);
            }
        }
        if(plugin.gameSettings.gamemode == 4){
            Block playerOnBlock = player.getLocation().getBlock().getRelative(BlockFace.DOWN);
            if(playerOnBlock.getType().equals(Material.REDSTONE_BLOCK) && player.getLocation().distance(new Location(player.getWorld(), -607, 106,-10))< 5){
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.sendMessage(ChatColor.YELLOW + "");
                    p.sendMessage(ChatColor.YELLOW + "");
                    p.sendMessage(ChatColor.YELLOW + "");
                    p.sendMessage(ChatColor.YELLOW + "");
                    p.sendMessage(ChatColor.YELLOW + "");
                    p.sendMessage(ChatColor.YELLOW + "");
                    p.sendMessage(ChatColor.YELLOW + "");
                    p.sendMessage(ChatColor.YELLOW + "");
                    p.sendMessage(ChatColor.WHITE + "축하합니다! 우승자는 "+ ChatColor.RED + player.getDisplayName() + ChatColor.WHITE + "님 입니다!");
                    p.playSound(player.getLocation(), "my_sounds:sound_success", 1.0f, 1.0f);
                }
                int reward = plugin.gameSettings.reward4;
                player.sendMessage(ChatColor.GREEN + "게임에서 승리했습니다! [+" + reward+"원]");
                plugin.gameSettings.gamemode = 0;
                GiveMoneyPlayer(player.getUniqueId(), reward, plugin);
                Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                    @Override
                    public void run() {
                        startGame(plugin);
                    }
                }, 100L);
            }
        }
        if(plugin.gameSettings.gamemode == 5) {
            Block playerOnBlock = player.getLocation().getBlock().getRelative(BlockFace.DOWN);
            if(playerOnBlock.getType().equals(Material.REDSTONE_BLOCK) && player.getLocation().distance(new Location(player.getWorld(), -504, 221,-63))< 5){
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.sendMessage(ChatColor.YELLOW + "");
                    p.sendMessage(ChatColor.YELLOW + "");
                    p.sendMessage(ChatColor.YELLOW + "");
                    p.sendMessage(ChatColor.YELLOW + "");
                    p.sendMessage(ChatColor.YELLOW + "");
                    p.sendMessage(ChatColor.YELLOW + "");
                    p.sendMessage(ChatColor.YELLOW + "");
                    p.sendMessage(ChatColor.YELLOW + "");
                    p.sendMessage(ChatColor.WHITE + "축하합니다! 우승자는 "+ ChatColor.RED + player.getDisplayName() + ChatColor.WHITE + "님 입니다!");
                    p.playSound(player.getLocation(), "my_sounds:sound_success", 1.0f, 1.0f);
                }
                int reward = plugin.gameSettings.reward5;
                player.sendMessage(ChatColor.GREEN + "게임에서 승리했습니다! [+" + reward+"원]");
                plugin.gameSettings.gamemode = 0;
                GiveMoneyPlayer(player.getUniqueId(), reward, plugin);
                Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                    @Override
                    public void run() {
                        startGame(plugin);
                    }
                }, 100L);
            }
            if (playerOnBlock.getType().equals(Material.BLACK_STAINED_GLASS)) {
                int where = (playerOnBlock.getX() + 553) / 3;
                //player.sendMessage("Z: " + playerOnBlock.getZ() + "X: " + playerOnBlock.getX() + "where : " + where + "list : " + plugin.gameSettings.randomGlassList.get(where));
                if(plugin.gameSettings.randomGlassList.get(where)){
                    if(playerOnBlock.getZ() == -65){
                        playerOnBlock.setType(Material.AIR);
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            p.playSound(p.getLocation(), Sound.BLOCK_GLASS_BREAK, 1.0f, 1.0f);
                        }

                        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                            @Override
                            public void run() {
                                playerOnBlock.setType(Material.BLACK_STAINED_GLASS);
                            }
                        }, 100L);
                    }
                }
                else{
                    if(playerOnBlock.getZ() == -63){
                        playerOnBlock.setType(Material.AIR);
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            p.playSound(p.getLocation(), Sound.BLOCK_GLASS_BREAK, 1.0f, 1.0f);
                        }

                        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                            @Override
                            public void run() {
                                playerOnBlock.setType(Material.BLACK_STAINED_GLASS);
                            }
                        }, 100L);
                    }
                }
            }
        }
    }

}
