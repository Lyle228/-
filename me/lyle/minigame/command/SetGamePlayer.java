package me.lyle.minigame.command;

import me.lyle.minigame.Minigame;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetGamePlayer implements CommandExecutor {
    private final Minigame plugin;

    public SetGamePlayer(Minigame plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String args[]) {
        if(sender instanceof Player p){
            if(args.length == 0) {
                p.sendMessage(ChatColor.YELLOW + "[!] " + ChatColor.WHITE + "/참가자 " + ChatColor.GRAY + "[닉네임]");
                return true;
            }
            String playerName = args[0];
            Player target = Bukkit.getServer().getPlayerExact(playerName);
            if(target == null){
                p.sendMessage(ChatColor.GRAY + "존재하지 않는 플레이어입니다.");
            }
            else if(plugin.gameSettings.gamePlayers.contains(target.getUniqueId())){
                p.sendMessage(ChatColor.RED + "[!] " + ChatColor.WHITE + "이미 등록된 참가자입니다.");
            }
            else {
                target.playSound(p.getLocation(),"my_sounds:sound_sendmsg",1.0f,1.0f);
                p.sendMessage(ChatColor.GREEN + "[!] " + ChatColor.WHITE + target.getDisplayName() + "님을 참가자로 등록했습니다.");
                target.sendMessage(ChatColor.YELLOW + "[!] " + ChatColor.WHITE + "게임의 참가자로 등록됐습니다.");
                plugin.gameSettings.gamePlayers.add(target.getUniqueId());
            }
        }
        return true;
    }

}
