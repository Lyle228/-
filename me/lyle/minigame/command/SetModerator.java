package me.lyle.minigame.command;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import me.lyle.minigame.Minigame;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket;
import net.minecraft.network.protocol.game.PacketPlayOutEntityHeadRotation;
import net.minecraft.network.protocol.game.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.WorldServer;
import net.minecraft.server.network.PlayerConnection;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.craftbukkit.v1_19_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

public class SetModerator implements CommandExecutor {
    private final Minigame plugin;

    public SetModerator(Minigame plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String args[]) {
        if(sender instanceof Player p){
            if(args.length == 0) {
                p.sendMessage(ChatColor.YELLOW + "[!] " + ChatColor.WHITE + "/사회자 " + ChatColor.GRAY + "[닉네임]");
                return true;
            }
            String playerName = args[0];
            Player target = Bukkit.getServer().getPlayerExact(playerName);
            if(target == null){
                p.sendMessage(ChatColor.GRAY + "존재하지 않는 플레이어입니다.");
            }else {
                target.playSound(p.getLocation(),"my_sounds:sound_sendmsg",1.0f,1.0f);
                p.sendMessage(ChatColor.GREEN + "[!] " + ChatColor.WHITE + target.getDisplayName() + "님을 사회자로 임명했습니다.");
                target.sendMessage(ChatColor.YELLOW + "[!] " + ChatColor.WHITE + "게임의 사회자로 임명됐습니다.");
                plugin.gameSettings.moderator = target.getUniqueId();


            }
        }
        return true;
    }

}
