package me.lyle.minigame.command;

import me.lyle.minigame.Minigame;
import me.lyle.minigame.utills.GiveMoney;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

import static me.lyle.minigame.utills.GiveMoney.GiveMoneyPlayer;

public class GiveMoneyCommand implements CommandExecutor {
    private final Minigame plugin;

    public GiveMoneyCommand(Minigame plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String args[]) {
        if (sender instanceof Player p) {
            if(args.length == 0) {
                p.sendMessage(ChatColor.YELLOW + "[!] " + ChatColor.WHITE + "/돈지급 " + ChatColor.GRAY + "[플레이어] " + "[금액]");
                return true;
            }
            else if(args.length == 1) {
                p.sendMessage(ChatColor.YELLOW + "[!] " + ChatColor.WHITE + "/돈지급 " + "[플레이어] " + ChatColor.GRAY + "[금액]");
                return true;
            }
            String playerName = args[0];
            Player target = Bukkit.getServer().getPlayerExact(playerName);
            if(target == null){
                p.sendMessage(ChatColor.RED + "[!] " + ChatColor.WHITE + "존재하지 않는 플레이어입니다.");
                return true;
            }
            String intAmount = args[1].replaceAll("[^0-9]", "");
            if(intAmount.isEmpty()){
                p.sendMessage(ChatColor.YELLOW + "[!] " + ChatColor.WHITE + "/돈지급 " + "[플레이어] "+ChatColor.GRAY + "[금액]");
                return true;
            }
            p.sendMessage(ChatColor.GREEN + "[!] " + ChatColor.WHITE + target.getDisplayName() +"님에게 " + intAmount + "원을 지급했습니다.");
            target.sendMessage(ChatColor.YELLOW + "[!] " + ChatColor.WHITE + "당신은 "+ intAmount + "원을 지급받았습니다.");
            GiveMoneyPlayer(p.getUniqueId(), Integer.parseInt(intAmount), plugin);
        }
        return true;
    }

}
