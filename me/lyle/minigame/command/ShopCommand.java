package me.lyle.minigame.command;

import dev.lone.itemsadder.api.CustomBlock;
import dev.lone.itemsadder.api.CustomStack;
import dev.lone.itemsadder.api.ItemsAdder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class ShopCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player p) {
            Inventory inventory = Bukkit.createInventory(p, 9, ChatColor.WHITE + "상점");
            ItemStack item = new ItemStack(Material.DIAMOND_SWORD, 1);
            ItemMeta itemMeta = item.getItemMeta();
            if(itemMeta != null) {
                itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                itemMeta.setDisplayName(ChatColor.WHITE+"무기 구매");
                item.setItemMeta(itemMeta);
                inventory.setItem(0, item);
            }

            item = new ItemStack(Material.DIAMOND_CHESTPLATE, 1);
            itemMeta = item.getItemMeta();
            if(itemMeta != null) {
                itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                itemMeta.setDisplayName(ChatColor.WHITE+"방어구 구매");
                item.setItemMeta(itemMeta);
                inventory.setItem(1, item);
            }

            item = new ItemStack(Material.POTION, 1);
            itemMeta = item.getItemMeta();
            if(itemMeta != null) {
                itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                itemMeta.removeItemFlags();
                itemMeta.setDisplayName(ChatColor.WHITE+"물약 구매");
                item.setItemMeta(itemMeta);
                inventory.setItem(2, item);
            }
            item = new ItemStack(Material.OAK_SIGN, 1);
            itemMeta = item.getItemMeta();
            if(itemMeta != null) {
                itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                itemMeta.setDisplayName(ChatColor.WHITE+"특별 아이템 구매");
                item.setItemMeta(itemMeta);
                inventory.setItem(3, item);
            }
            p.openInventory(inventory);
            CustomStack stack = CustomStack.getInstance("_iainternal:icon_cancel");
            if(stack != null){
                stack.setDisplayName("취소");
                inventory.setItem(8,stack.getItemStack());
            }

        }
        return true;
    }
}
