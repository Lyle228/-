package me.lyle.minigame.utills;

import dev.lone.itemsadder.api.CustomStack;
import me.lyle.minigame.Minigame;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.UUID;

public class CoinShop {
    private final Minigame plugin;

    public CoinShop(Minigame plugin) {
        this.plugin = plugin;
    }

    public void OpenCoinShop(Player player){
        Inventory inventory = Bukkit.createInventory(player, 36, ChatColor.YELLOW + "코인 샵");

        ItemStack item = new ItemStack(Material.COOKIE, 1);
        ItemMeta itemMeta = item.getItemMeta();
        if(itemMeta != null) {
            itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            itemMeta.setDisplayName(ChatColor.WHITE+"쿠키 코인");
            ArrayList<String> lore = new ArrayList<>();
            if(plugin.gameSettings.coinChange1 == 0)
            {
                lore.add(ChatColor.WHITE + "" + plugin.gameSettings.coin1 + "원" + "(-)");
            }
            else if(plugin.gameSettings.coinChange1 > 0){
                lore.add(ChatColor.GREEN + "" + plugin.gameSettings.coin1 + "원" + "(+" + plugin.gameSettings.coinChange1 + "%)");
            }
            else{
                lore.add(ChatColor.RED + "" + plugin.gameSettings.coin1 + "원" + "(" + plugin.gameSettings.coinChange1 + "%)");
            }
            lore.add(ChatColor.GREEN + "상한 : 20%");
            lore.add(ChatColor.RED + "하한 : 20%");
            itemMeta.setLore(lore);
            item.setItemMeta(itemMeta);
            inventory.setItem(0, item);
        }


        item = new ItemStack(Material.MELON, 1);
        itemMeta = item.getItemMeta();
        if(itemMeta != null) {
            itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            itemMeta.setDisplayName(ChatColor.WHITE+"수박 코인");
            ArrayList<String> lore = new ArrayList<>();
            if(plugin.gameSettings.coinChange2 == 0)
            {
                lore.add(ChatColor.WHITE + "" + plugin.gameSettings.coin2 + "원" + "(-)");
            }
            else if(plugin.gameSettings.coinChange2 > 0){
                lore.add(ChatColor.GREEN + "" + plugin.gameSettings.coin2 + "원" + "(+" + plugin.gameSettings.coinChange2 + "%)");
            }
            else{
                lore.add(ChatColor.RED + "" + plugin.gameSettings.coin2 + "원" + "(" + plugin.gameSettings.coinChange2 + "%)");
            }
            lore.add(ChatColor.GREEN + "상한 : 30%");
            lore.add(ChatColor.RED + "하한 : 30%");
            itemMeta.setLore(lore);
            item.setItemMeta(itemMeta);
            inventory.setItem(2, item);
        }

        item = new ItemStack(Material.GOLDEN_CARROT, 1);
        itemMeta = item.getItemMeta();
        if(itemMeta != null) {
            itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            itemMeta.setDisplayName(ChatColor.WHITE+"당근 코인");
            ArrayList<String> lore = new ArrayList<>();
            if(plugin.gameSettings.coinChange3 == 0)
            {
                lore.add(ChatColor.WHITE + "" + plugin.gameSettings.coin3 + "원" + "(-)");
            }
            else if(plugin.gameSettings.coinChange3 > 0){
                lore.add(ChatColor.GREEN + "" + plugin.gameSettings.coin3 + "원" + "(+" + plugin.gameSettings.coinChange3 + "%)");
            }
            else{
                lore.add(ChatColor.RED + "" + plugin.gameSettings.coin3 + "원" + "(" + plugin.gameSettings.coinChange3 + "%)");
            }
            lore.add(ChatColor.GREEN + "상한 : 50%");
            lore.add(ChatColor.RED + "하한 : 50%");
            itemMeta.setLore(lore);
            item.setItemMeta(itemMeta);
            inventory.setItem(4, item);
        }


        item = new ItemStack(Material.GOLDEN_APPLE, 1);
        itemMeta = item.getItemMeta();
        if(itemMeta != null) {
            itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            itemMeta.setDisplayName(ChatColor.WHITE+"사과 코인");
            ArrayList<String> lore = new ArrayList<>();
            if(plugin.gameSettings.coinChange4 == 0)
            {
                lore.add(ChatColor.WHITE + "" + plugin.gameSettings.coin4 + "원" + "(-)");
            }
            else if(plugin.gameSettings.coinChange4 > 0){
                lore.add(ChatColor.GREEN + "" + plugin.gameSettings.coin4 + "원" + "(+" + plugin.gameSettings.coinChange4 + "%)");
            }
            else{
                lore.add(ChatColor.RED + "" + plugin.gameSettings.coin4 + "원" + "(" + plugin.gameSettings.coinChange4 + "%)");
            }
            lore.add(ChatColor.GREEN + "상한 : 60%");
            lore.add(ChatColor.RED + "하한 : 60%");
            itemMeta.setLore(lore);
            item.setItemMeta(itemMeta);
            inventory.setItem(6,item);
        }

        item = new ItemStack(Material.BEETROOT, 1);
        itemMeta = item.getItemMeta();
        if(itemMeta != null) {
            itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            itemMeta.setDisplayName(ChatColor.WHITE+"비트 코인");
            ArrayList<String> lore = new ArrayList<>();
            if(plugin.gameSettings.coinChange5 == 0)
            {
                lore.add(ChatColor.WHITE + "" + plugin.gameSettings.coin5 + "원" + "(-)");
            }
            else if(plugin.gameSettings.coinChange5 > 0){
                lore.add(ChatColor.GREEN + "" + plugin.gameSettings.coin5 + "원" + "(+" + plugin.gameSettings.coinChange5 + "%)");
            }
            else{
                lore.add(ChatColor.RED + "" + plugin.gameSettings.coin5 + "원" + "(" + plugin.gameSettings.coinChange5 + "%)");
            }
            lore.add(ChatColor.GREEN + "상한 : 100%");
            lore.add(ChatColor.RED + "하한 : 100%");
            itemMeta.setLore(lore);
            item.setItemMeta(itemMeta);
            inventory.setItem(8,item);
        }

        CustomStack stack = CustomStack.getInstance("_iainternal:icon_search");
        item = stack.getItemStack();
        itemMeta = stack.getItemStack().getItemMeta();
        if(stack != null){
            stack.setDisplayName("정보");
            itemMeta = stack.getItemStack().getItemMeta();
            ArrayList<String> lore = new ArrayList<>();
            int minute = plugin.gameSettings.refreshCoinTime / 60;
            int second = plugin.gameSettings.refreshCoinTime % 60;
            lore.add(ChatColor.YELLOW + "갱신 : " + minute + "분 " + second + "초");
            itemMeta.setLore(lore);
            item.setItemMeta(itemMeta);
            inventory.setItem(31,item);
        }

        player.openInventory(inventory);
    }
}
