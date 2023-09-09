package me.lyle.minigame.command;

import me.lyle.minigame.Minigame;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CreateWall implements CommandExecutor {
    private Location location1 = null;
    private Location location2 = null;

    private Location saveLocation1 = null;
    private Location saveLocation2 = null;
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String args[]) {
        if(sender instanceof Player p){
            if(args.length == 0) {
                if (location1 == null) {
                    p.sendMessage(ChatColor.GREEN + "[!] " + ChatColor.WHITE + "첫번째 좌표를 지정했습니다, 두번째 좌표를 지정해주세요.");
                    location1 = p.getLocation();
                } else {
                    location2 = p.getLocation();
                    int minX = Math.min(location1.getBlockX(),location2.getBlockX());
                    int minY = Math.min(location1.getBlockY(),location2.getBlockY());
                    int minZ = Math.min(location1.getBlockZ(),location2.getBlockZ());

                    int maxX = Math.max(location1.getBlockX(),location2.getBlockX());
                    int maxZ = Math.max(location1.getBlockZ(),location2.getBlockZ());
                    World world = p.getWorld();
                    for(int i = minX; i <= maxX; i++){
                        for(int j = minY - 1; j <= minY + 60 + i % 2; j++){
                            world.getBlockAt(i, j, minZ).setType(Material.STONE_BRICKS);
                            world.getBlockAt(i, j, maxZ).setType(Material.STONE_BRICKS);
                        }
                    }
                    for(int i = minZ; i <= maxZ; i++){
                        for(int j = minY - 1; j <= minY + 60 + i % 2; j++){
                            world.getBlockAt(minX, j, i).setType(Material.STONE_BRICKS);
                            world.getBlockAt(maxX, j, i).setType(Material.STONE_BRICKS);
                        }
                    }

                    p.sendMessage(ChatColor.GREEN + "[!] " + ChatColor.WHITE + "성벽이 생성됐습니다. 취소하려면 [/성벽 취소]");
                    saveLocation1 = location1;
                    saveLocation2 = location2;
                    location1 = null;
                    location2 = null;
                }
            }
            else if(args[0].equals("취소")) {
                if (saveLocation1 != null && saveLocation2 != null) {
                    int minX = Math.min(saveLocation1.getBlockX(), saveLocation2.getBlockX());
                    int minY = Math.min(saveLocation1.getBlockY(), saveLocation2.getBlockY());
                    int minZ = Math.min(saveLocation1.getBlockZ(), saveLocation2.getBlockZ());

                    int maxX = Math.max(saveLocation1.getBlockX(), saveLocation2.getBlockX());
                    int maxZ = Math.max(saveLocation1.getBlockZ(), saveLocation2.getBlockZ());
                    World world = p.getWorld();
                    for (int i = minX; i <= maxX; i++) {
                        for (int j = minY - 1; j <= minY + 60 + i % 2; j++) {
                            world.getBlockAt(i, j, minZ).setType(Material.AIR);
                            world.getBlockAt(i, j, maxZ).setType(Material.AIR);
                        }
                    }
                    for (int i = minZ; i <= maxZ; i++) {
                        for (int j = minY - 1; j <= minY + 60 + i % 2; j++) {
                            world.getBlockAt(minX, j, i).setType(Material.AIR);
                            world.getBlockAt(maxX, j, i).setType(Material.AIR);
                        }
                    }

                    p.sendMessage(ChatColor.RED + "[!] " + ChatColor.WHITE + "생성을 취소했습니다.");
                }
            }
        }
        return true;
    }

}
