package me.lyle.minigame.listeners;

import me.lyle.minigame.Minigame;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.spigotmc.event.entity.EntityDismountEvent;
import org.spigotmc.event.entity.EntityMountEvent;

import java.util.ArrayList;

public class MountListener implements Listener {

    @EventHandler
    public void onPlayerMount(EntityMountEvent event){
        if(event.getEntity() instanceof Player p){
            if(event.getMount().getType().equals(EntityType.ARMOR_STAND)) {
                p.sendMessage(ChatColor.GREEN + "[!] " + ChatColor.WHITE + getSitWhere(p) + "위 자리에 앉았습니다.");
                Minigame.sitChair.put(p.getUniqueId(), getSitWhere(p));
            }
        }
    }

    @EventHandler
    public void onPlayerDisMount(EntityDismountEvent event){
        if(event.getEntity() instanceof Player p){
            if(Minigame.sitChair.containsKey(p.getUniqueId())) {
                p.sendMessage(ChatColor.GREEN + "[!] " + ChatColor.WHITE + getSitWhere(p) + "위 자리에서 내려왔습니다.");
                Minigame.sitChair.put(p.getUniqueId(), 0);
            }
        }
    }

    public int getSitWhere(Player p){
        if(p.getLocation().distance(new Location(p.getWorld(),-600,99,40)) <= 2)
        {
            return 1;
        }
        else if (p.getLocation().distance(new Location(p.getWorld(),-603,99,40)) <= 2) {
            return 2;
        }
        else if (p.getLocation().distance(new Location(p.getWorld(),-606,99,40)) <= 2) {
            return 3;
        }
        else if (p.getLocation().distance(new Location(p.getWorld(),-609,99,40)) <= 2) {
            return 4;
        }
        else if (p.getLocation().distance(new Location(p.getWorld(),-612,99,40)) <= 2) {
            return 5;
        }
        else if (p.getLocation().distance(new Location(p.getWorld(),-615,99,40)) <= 2) {
            return 6;
        }
        else if (p.getLocation().distance(new Location(p.getWorld(),-618,99,40)) <= 2) {
            return 7;
        }
        return 0;
    }

}
