package me.lyle.minigame.listeners;

import dev.lone.itemsadder.api.CustomStack;
import me.lyle.minigame.Minigame;
import me.lyle.minigame.utills.GiveMoney;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

import static me.lyle.minigame.utills.GiveMoney.GiveMoneyPlayer;

public class ShopListener implements Listener {
    private final Minigame plugin;

    public ShopListener(Minigame plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onMenuClick(InventoryClickEvent e){
        Player player = (Player) e.getWhoClicked();

        if(ChatColor.stripColor(e.getView().getTitle()).equalsIgnoreCase("코인 샵")){
            e.setCancelled(true);
            switch (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName())){
                case "쿠키 코인":
                    int price = plugin.gameSettings.coin1;
                    if(e.isLeftClick() && !e.isShiftClick()) {
                        if(!hasPlayerInventorySpace(1,player)){
                            player.sendMessage(ChatColor.RED + "[!] " + ChatColor.WHITE + "인벤토리를 한 칸 이상 확보해주세요.");
                            return;
                        }
                        if(plugin.moneyHashmap.get(player.getUniqueId()) < price) {
                            player.sendMessage(ChatColor.RED + "[!] " + ChatColor.WHITE + "쿠키 코인을 사기에는 돈이 부족합니다.");
                            return;
                        }
                        ItemStack coin = new ItemStack(Material.COOKIE, 1);
                        ItemMeta coinItemMeta = coin.getItemMeta();

                        ArrayList<String> lore = new ArrayList<>();

                        lore.add(ChatColor.GRAY + "");
                        lore.add(ChatColor.YELLOW + "딜러에게 팔 수 있는 코인입니다.");
                        lore.add(ChatColor.RED + "구매가 : " + price +"원");
                        coinItemMeta.setLore(lore);
                        coin.setItemMeta(coinItemMeta);
                        GiveMoneyPlayer(player.getUniqueId(), -price, plugin);
                        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
                        e.getView().getPlayer().getInventory().addItem(coin);

                        player.sendMessage(ChatColor.GREEN + "[!] " + ChatColor.WHITE + "쿠키 코인을 1개 구매했습니다." );
                    }
                    else if(e.isLeftClick() && e.isShiftClick()){
                        if(plugin.moneyHashmap.get(player.getUniqueId()) < price) {
                            player.sendMessage(ChatColor.RED + "[!] " + ChatColor.WHITE + "쿠키 코인을 사기에는 돈이 부족합니다.");
                            return;
                        }
                        int count = plugin.moneyHashmap.get(player.getUniqueId())/price;
                        if(!hasPlayerInventorySpace((count/64) + 1,player)){
                            player.sendMessage(ChatColor.RED + "[!] " + ChatColor.WHITE + "인벤토리를 "+((count/64) + 1)+"칸 이상 확보해주세요.");
                            return;
                        }
                        ItemStack coin = new ItemStack(Material.COOKIE, count);
                        ItemMeta coinItemMeta = coin.getItemMeta();

                        ArrayList<String> lore = new ArrayList<>();
                        lore.add(ChatColor.GRAY + "");
                        lore.add(ChatColor.YELLOW + "딜러에게 팔 수 있는 코인입니다.");
                        lore.add(ChatColor.RED + "구매가 : " + price +"원");
                        coinItemMeta.setLore(lore);
                        coin.setItemMeta(coinItemMeta);
                        GiveMoneyPlayer(player.getUniqueId(), -(price*count), plugin);
                        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
                        player.sendMessage(ChatColor.GREEN + "[!] " + ChatColor.WHITE + "쿠키 코인을 " +count + "개 구매했습니다." );
                        e.getView().getPlayer().getInventory().addItem(coin);
                    }
                    else if(e.isRightClick() && !e.isShiftClick()){
                        int size = 36;
                        for(int i = 0; i< size; i++){
                            ItemStack it = player.getInventory().getItem(i);
                            if(it != null) {
                                if (it.getType().equals(Material.COOKIE)) {
                                    it.setAmount(it.getAmount()-1);
                                    player.sendMessage(ChatColor.GREEN + "[!] " + ChatColor.WHITE + "쿠키 코인 1개 판매 했습니다. [+" +price +"원]" );
                                    GiveMoneyPlayer(player.getUniqueId(),price,plugin);
                                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
                                    break;
                                }
                            }
                        }
                    }
                    else if(e.isRightClick() && e.isShiftClick()){
                        int size = 36;
                        int count = 0;
                        for(int i = 0; i< size; i++){
                            ItemStack it = player.getInventory().getItem(i);
                            if(it != null) {
                                if (it.getType().equals(Material.COOKIE)) {
                                    count += it.getAmount();
                                    it.setAmount(0);
                                }
                            }
                        }
                        if(count == 0)
                        {
                            player.sendMessage(ChatColor.RED + "[!] " + ChatColor.WHITE + "가진 쿠키 코인이 없습니다.");
                            break;
                        }
                        GiveMoneyPlayer(player.getUniqueId(),price*count,plugin);
                        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
                        player.sendMessage(ChatColor.GREEN + "[!] " + ChatColor.WHITE + "쿠키 코인 "+count+"개 판매 했습니다. [+" +price*count +"원]" );
                    }
                    break;


                case "수박 코인":
                    price = plugin.gameSettings.coin2;
                    if(e.isLeftClick() && !e.isShiftClick()) {
                        if(!hasPlayerInventorySpace(1,player)){
                            player.sendMessage(ChatColor.RED + "[!] " + ChatColor.WHITE + "인벤토리를 한 칸 이상 확보해주세요.");
                            return;
                        }
                        if(plugin.moneyHashmap.get(player.getUniqueId()) < price) {
                            player.sendMessage(ChatColor.RED + "[!] " + ChatColor.WHITE + "수박 코인을 사기에는 돈이 부족합니다.");
                            return;
                        }
                        ItemStack coin = new ItemStack(Material.MELON, 1);
                        ItemMeta coinItemMeta = coin.getItemMeta();

                        ArrayList<String> lore = new ArrayList<>();

                        lore.add(ChatColor.GRAY + "");
                        lore.add(ChatColor.YELLOW + "딜러에게 팔 수 있는 코인입니다.");
                        lore.add(ChatColor.RED + "구매가 : " + price +"원");
                        coinItemMeta.setLore(lore);
                        coin.setItemMeta(coinItemMeta);
                        GiveMoneyPlayer(player.getUniqueId(), -price, plugin);
                        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
                        e.getView().getPlayer().getInventory().addItem(coin);

                        player.sendMessage(ChatColor.GREEN + "[!] " + ChatColor.WHITE + "수박 코인을 1개 구매했습니다." );
                    }
                    else if(e.isLeftClick() && e.isShiftClick()){
                        if(plugin.moneyHashmap.get(player.getUniqueId()) < price) {
                            player.sendMessage(ChatColor.RED + "[!] " + ChatColor.WHITE + "수박 코인을 사기에는 돈이 부족합니다.");
                            return;
                        }
                        int count = plugin.moneyHashmap.get(player.getUniqueId())/price;
                        if(!hasPlayerInventorySpace((count/64) + 1,player)){
                            player.sendMessage(ChatColor.RED + "[!] " + ChatColor.WHITE + "인벤토리를 "+((count/64) + 1)+"칸 이상 확보해주세요.");
                            return;
                        }
                        ItemStack coin = new ItemStack(Material.MELON, count);
                        ItemMeta coinItemMeta = coin.getItemMeta();

                        ArrayList<String> lore = new ArrayList<>();
                        lore.add(ChatColor.GRAY + "");
                        lore.add(ChatColor.YELLOW + "딜러에게 팔 수 있는 코인입니다.");
                        lore.add(ChatColor.RED + "구매가 : " + price +"원");
                        coinItemMeta.setLore(lore);
                        coin.setItemMeta(coinItemMeta);
                        GiveMoneyPlayer(player.getUniqueId(), -(price*count), plugin);
                        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
                        player.sendMessage(ChatColor.GREEN + "[!] " + ChatColor.WHITE + "수박 코인을 " +count + "개 구매했습니다." );
                        e.getView().getPlayer().getInventory().addItem(coin);
                    }
                    else if(e.isRightClick() && !e.isShiftClick()){
                        int size = 36;
                        for(int i = 0; i< size; i++){
                            ItemStack it = player.getInventory().getItem(i);
                            if(it != null) {
                                if (it.getType().equals(Material.MELON)) {
                                    it.setAmount(it.getAmount()-1);
                                    player.sendMessage(ChatColor.GREEN + "[!] " + ChatColor.WHITE + "수박 코인 1개 판매 했습니다. [+" +price +"원]" );
                                    GiveMoneyPlayer(player.getUniqueId(),price,plugin);
                                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
                                    break;
                                }
                            }
                        }
                    }
                    else if(e.isRightClick() && e.isShiftClick()){
                        int size = 36;
                        int count = 0;
                        for(int i = 0; i< size; i++){
                            ItemStack it = player.getInventory().getItem(i);
                            if(it != null) {
                                if (it.getType().equals(Material.MELON)) {
                                    count += it.getAmount();
                                    it.setAmount(0);
                                }
                            }
                        }
                        if(count == 0)
                        {
                            player.sendMessage(ChatColor.RED + "[!] " + ChatColor.WHITE + "가진 수박 코인이 없습니다.");
                            break;
                        }
                        GiveMoneyPlayer(player.getUniqueId(),price*count,plugin);
                        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
                        player.sendMessage(ChatColor.GREEN + "[!] " + ChatColor.WHITE + "수박 코인 "+count+"개 판매 했습니다. [+" +price*count +"원]" );
                    }
                    break;



                case "당근 코인":
                    price = plugin.gameSettings.coin3;
                    if(e.isLeftClick() && !e.isShiftClick()) {
                        if(!hasPlayerInventorySpace(1,player)){
                            player.sendMessage(ChatColor.RED + "[!] " + ChatColor.WHITE + "인벤토리를 한 칸 이상 확보해주세요.");
                            return;
                        }
                        if(plugin.moneyHashmap.get(player.getUniqueId()) < price) {
                            player.sendMessage(ChatColor.RED + "[!] " + ChatColor.WHITE + "당근 코인을 사기에는 돈이 부족합니다.");
                            return;
                        }
                        ItemStack coin = new ItemStack(Material.GOLDEN_CARROT, 1);
                        ItemMeta coinItemMeta = coin.getItemMeta();

                        ArrayList<String> lore = new ArrayList<>();

                        lore.add(ChatColor.GRAY + "");
                        lore.add(ChatColor.YELLOW + "딜러에게 팔 수 있는 코인입니다.");
                        lore.add(ChatColor.RED + "구매가 : " + price +"원");
                        coinItemMeta.setLore(lore);
                        coin.setItemMeta(coinItemMeta);
                        GiveMoneyPlayer(player.getUniqueId(), -price, plugin);
                        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
                        e.getView().getPlayer().getInventory().addItem(coin);

                        player.sendMessage(ChatColor.GREEN + "[!] " + ChatColor.WHITE + "당근 코인을 1개 구매했습니다." );
                    }
                    else if(e.isLeftClick() && e.isShiftClick()){
                        if(plugin.moneyHashmap.get(player.getUniqueId()) < price) {
                            player.sendMessage(ChatColor.RED + "[!] " + ChatColor.WHITE + "당근 코인을 사기에는 돈이 부족합니다.");
                            return;
                        }
                        int count = plugin.moneyHashmap.get(player.getUniqueId())/price;
                        if(!hasPlayerInventorySpace((count/64) + 1,player)){
                            player.sendMessage(ChatColor.RED + "[!] " + ChatColor.WHITE + "인벤토리를 "+((count/64) + 1)+"칸 이상 확보해주세요.");
                            return;
                        }
                        ItemStack coin = new ItemStack(Material.GOLDEN_CARROT, count);
                        ItemMeta coinItemMeta = coin.getItemMeta();

                        ArrayList<String> lore = new ArrayList<>();
                        lore.add(ChatColor.GRAY + "");
                        lore.add(ChatColor.YELLOW + "딜러에게 팔 수 있는 코인입니다.");
                        lore.add(ChatColor.RED + "구매가 : " + price +"원");
                        coinItemMeta.setLore(lore);
                        coin.setItemMeta(coinItemMeta);
                        GiveMoneyPlayer(player.getUniqueId(), -(price*count), plugin);
                        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
                        player.sendMessage(ChatColor.GREEN + "[!] " + ChatColor.WHITE + "당근 코인을 " +count + "개 구매했습니다." );
                        e.getView().getPlayer().getInventory().addItem(coin);
                    }
                    else if(e.isRightClick() && !e.isShiftClick()){
                        int size = 36;
                        for(int i = 0; i< size; i++){
                            ItemStack it = player.getInventory().getItem(i);
                            if(it != null) {
                                if (it.getType().equals(Material.GOLDEN_CARROT)) {
                                    it.setAmount(it.getAmount()-1);
                                    player.sendMessage(ChatColor.GREEN + "[!] " + ChatColor.WHITE + "당근 코인 1개 판매 했습니다. [+" +price +"원]" );
                                    GiveMoneyPlayer(player.getUniqueId(),price,plugin);
                                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
                                    break;
                                }
                            }
                        }
                    }
                    else if(e.isRightClick() && e.isShiftClick()){
                        int size = 36;
                        int count = 0;
                        for(int i = 0; i< size; i++){
                            ItemStack it = player.getInventory().getItem(i);
                            if(it != null) {
                                if (it.getType().equals(Material.GOLDEN_CARROT)) {
                                    count += it.getAmount();
                                    it.setAmount(0);
                                }
                            }
                        }
                        if(count == 0)
                        {
                            player.sendMessage(ChatColor.RED + "[!] " + ChatColor.WHITE + "가진 당근 코인이 없습니다.");
                            break;
                        }
                        GiveMoneyPlayer(player.getUniqueId(),price*count,plugin);
                        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
                        player.sendMessage(ChatColor.GREEN + "[!] " + ChatColor.WHITE + "당근 코인 "+count+"개 판매 했습니다. [+" +price*count +"원]" );
                    }
                    break;


                case "사과 코인":
                    price = plugin.gameSettings.coin4;
                    if(e.isLeftClick() && !e.isShiftClick()) {
                        if(!hasPlayerInventorySpace(1,player)){
                            player.sendMessage(ChatColor.RED + "[!] " + ChatColor.WHITE + "인벤토리를 한 칸 이상 확보해주세요.");
                            return;
                        }
                        if(plugin.moneyHashmap.get(player.getUniqueId()) < price) {
                            player.sendMessage(ChatColor.RED + "[!] " + ChatColor.WHITE + "사과 코인을 사기에는 돈이 부족합니다.");
                            return;
                        }
                        ItemStack coin = new ItemStack(Material.GOLDEN_APPLE, 1);
                        ItemMeta coinItemMeta = coin.getItemMeta();

                        ArrayList<String> lore = new ArrayList<>();

                        lore.add(ChatColor.GRAY + "");
                        lore.add(ChatColor.YELLOW + "딜러에게 팔 수 있는 코인입니다.");
                        lore.add(ChatColor.RED + "구매가 : " + price +"원");
                        coinItemMeta.setLore(lore);
                        coin.setItemMeta(coinItemMeta);
                        GiveMoneyPlayer(player.getUniqueId(), -price, plugin);
                        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
                        e.getView().getPlayer().getInventory().addItem(coin);

                        player.sendMessage(ChatColor.GREEN + "[!] " + ChatColor.WHITE + "사과 코인을 1개 구매했습니다." );
                    }
                    else if(e.isLeftClick() && e.isShiftClick()){
                        if(plugin.moneyHashmap.get(player.getUniqueId()) < price) {
                            player.sendMessage(ChatColor.RED + "[!] " + ChatColor.WHITE + "사과 코인을 사기에는 돈이 부족합니다.");
                            return;
                        }
                        int count = plugin.moneyHashmap.get(player.getUniqueId())/price;
                        if(!hasPlayerInventorySpace((count/64) + 1,player)){
                            player.sendMessage(ChatColor.RED + "[!] " + ChatColor.WHITE + "인벤토리를 "+((count/64) + 1)+"칸 이상 확보해주세요.");
                            return;
                        }
                        ItemStack coin = new ItemStack(Material.GOLDEN_APPLE, count);
                        ItemMeta coinItemMeta = coin.getItemMeta();

                        ArrayList<String> lore = new ArrayList<>();
                        lore.add(ChatColor.GRAY + "");
                        lore.add(ChatColor.YELLOW + "딜러에게 팔 수 있는 코인입니다.");
                        lore.add(ChatColor.RED + "구매가 : " + price +"원");
                        coinItemMeta.setLore(lore);
                        coin.setItemMeta(coinItemMeta);
                        GiveMoneyPlayer(player.getUniqueId(), -(price*count), plugin);
                        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
                        player.sendMessage(ChatColor.GREEN + "[!] " + ChatColor.WHITE + "사과 코인을 " +count + "개 구매했습니다." );
                        e.getView().getPlayer().getInventory().addItem(coin);
                    }
                    else if(e.isRightClick() && !e.isShiftClick()){
                        int size = 36;
                        for(int i = 0; i< size; i++){
                            ItemStack it = player.getInventory().getItem(i);
                            if(it != null) {
                                if (it.getType().equals(Material.GOLDEN_APPLE)) {
                                    it.setAmount(it.getAmount()-1);
                                    player.sendMessage(ChatColor.GREEN + "[!] " + ChatColor.WHITE + "사과 코인 1개 판매 했습니다. [+" +price +"원]" );
                                    GiveMoneyPlayer(player.getUniqueId(),price,plugin);
                                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
                                    break;
                                }
                            }
                        }
                    }
                    else if(e.isRightClick() && e.isShiftClick()){
                        int size = 36;
                        int count = 0;
                        for(int i = 0; i< size; i++){
                            ItemStack it = player.getInventory().getItem(i);
                            if(it != null) {
                                if (it.getType().equals(Material.GOLDEN_APPLE)) {
                                    count += it.getAmount();
                                    it.setAmount(0);
                                }
                            }
                        }
                        if(count == 0)
                        {
                            player.sendMessage(ChatColor.RED + "[!] " + ChatColor.WHITE + "가진 사과 코인이 없습니다.");
                            break;
                        }
                        GiveMoneyPlayer(player.getUniqueId(),price*count,plugin);
                        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
                        player.sendMessage(ChatColor.GREEN + "[!] " + ChatColor.WHITE + "사과 코인 "+count+"개 판매 했습니다. [+" +price*count +"원]" );
                    }
                    break;



                case "비트 코인":
                    price = plugin.gameSettings.coin5;
                    if(e.isLeftClick() && !e.isShiftClick()) {
                        if(!hasPlayerInventorySpace(1,player)){
                            player.sendMessage(ChatColor.RED + "[!] " + ChatColor.WHITE + "인벤토리를 한 칸 이상 확보해주세요.");
                            return;
                        }
                        if(plugin.moneyHashmap.get(player.getUniqueId()) < price) {
                            player.sendMessage(ChatColor.RED + "[!] " + ChatColor.WHITE + "비트 코인을 사기에는 돈이 부족합니다.");
                            return;
                        }
                        ItemStack coin = new ItemStack(Material.BEETROOT, 1);
                        ItemMeta coinItemMeta = coin.getItemMeta();

                        ArrayList<String> lore = new ArrayList<>();

                        lore.add(ChatColor.GRAY + "");
                        lore.add(ChatColor.YELLOW + "딜러에게 팔 수 있는 코인입니다.");
                        lore.add(ChatColor.RED + "구매가 : " + price +"원");
                        coinItemMeta.setLore(lore);
                        coin.setItemMeta(coinItemMeta);
                        GiveMoneyPlayer(player.getUniqueId(), -price, plugin);
                        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
                        e.getView().getPlayer().getInventory().addItem(coin);
                        player.sendMessage(ChatColor.GREEN + "[!] " + ChatColor.WHITE + "비트 코인을 1개 구매했습니다." );
                    }
                    else if(e.isLeftClick() && e.isShiftClick()){
                        if(plugin.moneyHashmap.get(player.getUniqueId()) < price) {
                            player.sendMessage(ChatColor.RED + "[!] " + ChatColor.WHITE + "비트 코인을 사기에는 돈이 부족합니다.");
                            return;
                        }
                        int count = plugin.moneyHashmap.get(player.getUniqueId())/price;
                        if(!hasPlayerInventorySpace((count/64) + 1,player)){
                            player.sendMessage(ChatColor.RED + "[!] " + ChatColor.WHITE + "인벤토리를 "+((count/64) + 1)+"칸 이상 확보해주세요.");
                            return;
                        }
                        ItemStack coin = new ItemStack(Material.BEETROOT, count);
                        ItemMeta coinItemMeta = coin.getItemMeta();

                        ArrayList<String> lore = new ArrayList<>();
                        lore.add(ChatColor.GRAY + "");
                        lore.add(ChatColor.YELLOW + "딜러에게 팔 수 있는 코인입니다.");
                        lore.add(ChatColor.RED + "구매가 : " + price +"원");
                        coinItemMeta.setLore(lore);
                        coin.setItemMeta(coinItemMeta);
                        GiveMoneyPlayer(player.getUniqueId(), -(price*count), plugin);
                        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
                        player.sendMessage(ChatColor.GREEN + "[!] " + ChatColor.WHITE + "비트 코인을 " +count + "개 구매했습니다." );
                        e.getView().getPlayer().getInventory().addItem(coin);
                    }
                    else if(e.isRightClick() && !e.isShiftClick()){
                        int size = 36;
                        for(int i = 0; i< size; i++){
                            ItemStack it = player.getInventory().getItem(i);
                            if(it != null) {
                                if (it.getType().equals(Material.BEETROOT)) {
                                    it.setAmount(it.getAmount()-1);
                                    player.sendMessage(ChatColor.GREEN + "[!] " + ChatColor.WHITE + "비트 코인 1개 판매 했습니다. [+" +price +"원]" );
                                    GiveMoneyPlayer(player.getUniqueId(),price,plugin);
                                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
                                    break;
                                }
                            }
                        }
                    }
                    else if(e.isRightClick() && e.isShiftClick()){
                        int size = 36;
                        int count = 0;
                        for(int i = 0; i< size; i++){
                            ItemStack it = player.getInventory().getItem(i);
                            if(it != null) {
                                if (it.getType().equals(Material.BEETROOT)) {
                                    count += it.getAmount();
                                    it.setAmount(0);
                                }
                            }
                        }
                        if(count == 0)
                        {
                            player.sendMessage(ChatColor.RED + "[!] " + ChatColor.WHITE + "가진 비트 코인이 없습니다.");
                            break;
                        }
                        GiveMoneyPlayer(player.getUniqueId(),price*count,plugin);
                        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
                        player.sendMessage(ChatColor.GREEN + "[!] " + ChatColor.WHITE + "비트 코인 "+count+"개 판매 했습니다. [+" +price*count +"원]" );
                    }
                    break;



            }
        }


        if(ChatColor.stripColor(e.getView().getTitle()).equalsIgnoreCase("상점")){
            if(e.getCurrentItem() == null){
                return;
            }
            switch (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName())){
                case "무기 구매":
                    OpenWeaponShopInventory(player);
                    break;
                case "방어구 구매":
                    OpenArmorShopInventory(player);
                    break;
                case "물약 구매":
                    break;

                case "특별 아이템 구매":
                    break;


                case "취소":
                    player.closeInventory();
                    break;

                case "돌아가기":
                    OpenShopInventory(player);
                    break;

                case "네더라이트 검":
                    if(!hasPlayerInventorySpace(1,player))
                    {
                        player.sendMessage(ChatColor.RED + "[!] " + ChatColor.WHITE + "인벤토리에 공간이 부족합니다.");
                        player.closeInventory();
                        break;
                    }
                    if(!plugin.moneyHashmap.containsKey(player.getUniqueId())){
                        plugin.moneyHashmap.put(player.getUniqueId(),0);
                    }
                    if(plugin.moneyHashmap.get(player.getUniqueId()) < 20000){
                        player.sendMessage(ChatColor.RED + "[!] " + ChatColor.WHITE + "네더라이트 검을 사기에는 돈이 부족합니다.");
                    }
                    else{
                        player.sendMessage(ChatColor.GREEN + "[!] " + ChatColor.WHITE + "네더라이트 검을 구매했습니다." + ChatColor.RED +" [-20000원]");
                        GiveMoneyPlayer(player.getUniqueId(),-20000,plugin);
                        e.getView().getPlayer().getInventory().addItem(RandomSwordEnchant(Material.NETHERITE_SWORD));
                        player.closeInventory();
                    }
                    break;

                case "다이아몬드 검":
                    if(!hasPlayerInventorySpace(1,player))
                    {
                        player.sendMessage(ChatColor.RED + "[!] " + ChatColor.WHITE + "인벤토리에 공간이 부족합니다.");
                        player.closeInventory();
                        break;
                    }
                    if(!plugin.moneyHashmap.containsKey(player.getUniqueId())){
                        plugin.moneyHashmap.put(player.getUniqueId(),0);
                    }
                    if(plugin.moneyHashmap.get(player.getUniqueId()) < 15000){
                        player.sendMessage(ChatColor.RED + "[!] " + ChatColor.WHITE + "다이아몬드 검을 사기에는 돈이 부족합니다.");
                    }
                    else{
                        player.sendMessage(ChatColor.GREEN + "[!] " + ChatColor.WHITE + "다이아몬드 검을 구매했습니다." + ChatColor.RED +" [-15000원]");
                        GiveMoneyPlayer(player.getUniqueId(),-15000,plugin);
                        e.getView().getPlayer().getInventory().addItem(RandomSwordEnchant(Material.DIAMOND_SWORD));
                        player.closeInventory();
                    }
                    break;


                case "철 검":
                    if(!hasPlayerInventorySpace(1,player))
                    {
                        player.sendMessage(ChatColor.RED + "[!] " + ChatColor.WHITE + "인벤토리에 공간이 부족합니다.");
                        player.closeInventory();
                        break;
                    }
                    if(!plugin.moneyHashmap.containsKey(player.getUniqueId())){
                        plugin.moneyHashmap.put(player.getUniqueId(),0);
                    }
                    if(plugin.moneyHashmap.get(player.getUniqueId()) < 8000){
                        player.sendMessage(ChatColor.RED + "[!] " + ChatColor.WHITE + "철 검을 사기에는 돈이 부족합니다.");
                    }
                    else{
                        player.sendMessage(ChatColor.GREEN + "[!] " + ChatColor.WHITE + "철 검을 구매했습니다." + ChatColor.RED +" [-8000원]");
                        GiveMoneyPlayer(player.getUniqueId(),-8000,plugin);
                        e.getView().getPlayer().getInventory().addItem(RandomSwordEnchant(Material.IRON_SWORD));
                        player.closeInventory();
                    }
                    break;

                case "돌 검":
                    if(!hasPlayerInventorySpace(1,player))
                    {
                        player.sendMessage(ChatColor.RED + "[!] " + ChatColor.WHITE + "인벤토리에 공간이 부족합니다.");
                        player.closeInventory();
                        break;
                    }
                    if(!plugin.moneyHashmap.containsKey(player.getUniqueId())){
                        plugin.moneyHashmap.put(player.getUniqueId(),0);
                    }
                    if(plugin.moneyHashmap.get(player.getUniqueId()) < 3000){
                        player.sendMessage(ChatColor.RED + "[!] " + ChatColor.WHITE + "돌 검을 사기에는 돈이 부족합니다.");
                    }
                    else{
                        player.sendMessage(ChatColor.GREEN + "[!] " + ChatColor.WHITE + "돌 검을 구매했습니다." + ChatColor.RED +" [-3000원]");
                        GiveMoneyPlayer(player.getUniqueId(),-3000,plugin);
                        e.getView().getPlayer().getInventory().addItem(RandomSwordEnchant(Material.STONE_SWORD));
                        player.closeInventory();
                    }
                    break;

                case "나무 검":
                    if(!hasPlayerInventorySpace(1,player))
                    {
                        player.sendMessage(ChatColor.RED + "[!] " + ChatColor.WHITE + "인벤토리에 공간이 부족합니다.");
                        player.closeInventory();
                        break;
                    }
                    if(!plugin.moneyHashmap.containsKey(player.getUniqueId())){
                        plugin.moneyHashmap.put(player.getUniqueId(),0);
                    }
                    if(plugin.moneyHashmap.get(player.getUniqueId()) < 1000){
                        player.sendMessage(ChatColor.RED + "[!] " + ChatColor.WHITE + "나무 검을 사기에는 돈이 부족합니다.");
                    }
                    else{
                        player.sendMessage(ChatColor.GREEN + "[!] " + ChatColor.WHITE + "나무 검을 구매했습니다." + ChatColor.RED +" [-1000원]");
                        GiveMoneyPlayer(player.getUniqueId(),-1000,plugin);
                        e.getView().getPlayer().getInventory().addItem(RandomSwordEnchant(Material.WOODEN_SWORD));
                        player.closeInventory();
                    }
                    break;

                case "엘프 활":
                    if(!hasPlayerInventorySpace(1,player))
                    {
                        player.sendMessage(ChatColor.RED + "[!] " + ChatColor.WHITE + "인벤토리에 공간이 부족합니다.");
                        player.closeInventory();
                        break;
                    }
                    if(!plugin.moneyHashmap.containsKey(player.getUniqueId())){
                        plugin.moneyHashmap.put(player.getUniqueId(),0);
                    }
                    if(plugin.moneyHashmap.get(player.getUniqueId()) < 10000){
                        player.sendMessage(ChatColor.RED + "[!] " + ChatColor.WHITE + "활을 사기에는 돈이 부족합니다.");
                    }
                    else{
                        player.sendMessage(ChatColor.GREEN + "[!] " + ChatColor.WHITE + "활을 구매했습니다." + ChatColor.RED +" [-10000원]");
                        GiveMoneyPlayer(player.getUniqueId(),-10000,plugin);
                        e.getView().getPlayer().getInventory().addItem(RandomArrowInchant(Material.BOW));
                        player.closeInventory();
                    }
                    break;

                case "평범한 화살":
                    if(!hasPlayerInventorySpace(1,player))
                    {
                        player.sendMessage(ChatColor.RED + "[!] " + ChatColor.WHITE + "인벤토리에 공간이 부족합니다.");
                        player.closeInventory();
                        break;
                    }
                    if(!plugin.moneyHashmap.containsKey(player.getUniqueId())){
                        plugin.moneyHashmap.put(player.getUniqueId(),0);
                    }
                    if(plugin.moneyHashmap.get(player.getUniqueId()) < 500){
                        player.sendMessage(ChatColor.RED + "[!] " + ChatColor.WHITE + "화살을 사기에는 돈이 부족합니다.");
                    }
                    else{
                        player.sendMessage(ChatColor.GREEN + "[!] " + ChatColor.WHITE + "화살을 구매했습니다." + ChatColor.RED +" [-500원]");
                        GiveMoneyPlayer(player.getUniqueId(),-500,plugin);
                        ItemStack arrow = new ItemStack(Material.ARROW,64);
                        e.getView().getPlayer().getInventory().addItem(arrow);
                        player.closeInventory();
                    }
                    break;

                case "네더라이트 갑옷 세트":
                    if(!hasPlayerInventorySpace(4,player))
                    {
                        player.sendMessage(ChatColor.RED + "[!] " + ChatColor.WHITE + "인벤토리에 공간이 부족합니다.");
                        player.closeInventory();
                        break;
                    }
                    if(!plugin.moneyHashmap.containsKey(player.getUniqueId())){
                        plugin.moneyHashmap.put(player.getUniqueId(),0);
                    }
                    if(plugin.moneyHashmap.get(player.getUniqueId()) < 50000){
                        player.sendMessage(ChatColor.RED + "[!] " + ChatColor.WHITE + "네더라이트 갑옷 세트를 사기에는 돈이 부족합니다.");
                    }
                    else{
                        player.sendMessage(ChatColor.GREEN + "[!] " + ChatColor.WHITE + "네더라이트 갑옷 세트를 구매했습니다." + ChatColor.RED +" [-50000원]");
                        GiveMoneyPlayer(player.getUniqueId(),-50000,plugin);
                        e.getView().getPlayer().getInventory().addItem(RandomHelmetEnchant(Material.NETHERITE_HELMET));
                        e.getView().getPlayer().getInventory().addItem(RandomChestPlateEnchant(Material.NETHERITE_CHESTPLATE));
                        e.getView().getPlayer().getInventory().addItem(RandomLeggingsEnchant(Material.NETHERITE_LEGGINGS));
                        e.getView().getPlayer().getInventory().addItem(RandomBootsEnchant(Material.NETHERITE_BOOTS));
                        player.closeInventory();
                    }
                    break;

                case "다이아몬드 갑옷 세트":
                    if(!hasPlayerInventorySpace(4,player))
                    {
                        player.sendMessage(ChatColor.RED + "[!] " + ChatColor.WHITE + "인벤토리에 공간이 부족합니다.");
                        player.closeInventory();
                        break;
                    }
                    if(!plugin.moneyHashmap.containsKey(player.getUniqueId())){
                        plugin.moneyHashmap.put(player.getUniqueId(),0);
                    }
                    if(plugin.moneyHashmap.get(player.getUniqueId()) < 30000){
                        player.sendMessage(ChatColor.RED + "[!] " + ChatColor.WHITE + "다이아몬드 갑옷 세트를 사기에는 돈이 부족합니다.");
                    }
                    else{
                        player.sendMessage(ChatColor.GREEN + "[!] " + ChatColor.WHITE + "다이아몬드 갑옷 세트를 구매했습니다." + ChatColor.RED +" [-30000원]");
                        GiveMoneyPlayer(player.getUniqueId(),-30000,plugin);
                        e.getView().getPlayer().getInventory().addItem(RandomHelmetEnchant(Material.DIAMOND_HELMET));
                        e.getView().getPlayer().getInventory().addItem(RandomChestPlateEnchant(Material.DIAMOND_CHESTPLATE));
                        e.getView().getPlayer().getInventory().addItem(RandomLeggingsEnchant(Material.DIAMOND_LEGGINGS));
                        e.getView().getPlayer().getInventory().addItem(RandomBootsEnchant(Material.DIAMOND_BOOTS));
                        player.closeInventory();
                    }
                    break;

                case "철 갑옷 세트":
                    if(!hasPlayerInventorySpace(4,player))
                    {
                        player.sendMessage(ChatColor.RED + "[!] " + ChatColor.WHITE + "인벤토리에 공간이 부족합니다.");
                        player.closeInventory();
                        break;
                    }
                    if(!plugin.moneyHashmap.containsKey(player.getUniqueId())){
                        plugin.moneyHashmap.put(player.getUniqueId(),0);
                    }
                    if(plugin.moneyHashmap.get(player.getUniqueId()) < 15000){
                        player.sendMessage(ChatColor.RED + "[!] " + ChatColor.WHITE + "철 갑옷 세트를 사기에는 돈이 부족합니다.");
                    }
                    else{
                        player.sendMessage(ChatColor.GREEN + "[!] " + ChatColor.WHITE + "철 갑옷 세트를 구매했습니다." + ChatColor.RED +" [-15000원]");
                        GiveMoneyPlayer(player.getUniqueId(),-15000,plugin);
                        e.getView().getPlayer().getInventory().addItem(RandomHelmetEnchant(Material.IRON_HELMET));
                        e.getView().getPlayer().getInventory().addItem(RandomChestPlateEnchant(Material.IRON_CHESTPLATE));
                        e.getView().getPlayer().getInventory().addItem(RandomLeggingsEnchant(Material.IRON_LEGGINGS));
                        e.getView().getPlayer().getInventory().addItem(RandomBootsEnchant(Material.IRON_BOOTS));
                        player.closeInventory();
                    }
                    break;

                case "가죽 갑옷 세트":
                    if(!hasPlayerInventorySpace(4,player))
                    {
                        player.sendMessage(ChatColor.RED + "[!] " + ChatColor.WHITE + "인벤토리에 공간이 부족합니다.");
                        player.closeInventory();
                        break;
                    }
                    if(!plugin.moneyHashmap.containsKey(player.getUniqueId())){
                        plugin.moneyHashmap.put(player.getUniqueId(),0);
                    }
                    if(plugin.moneyHashmap.get(player.getUniqueId()) < 8000){
                        player.sendMessage(ChatColor.RED + "[!] " + ChatColor.WHITE + "가죽 갑옷 세트를 사기에는 돈이 부족합니다.");
                    }
                    else{
                        player.sendMessage(ChatColor.GREEN + "[!] " + ChatColor.WHITE + "가죽 갑옷 세트를 구매했습니다." + ChatColor.RED +" [-8000원]");
                        GiveMoneyPlayer(player.getUniqueId(),-8000,plugin);
                        e.getView().getPlayer().getInventory().addItem(RandomHelmetEnchant(Material.LEATHER_HELMET));
                        e.getView().getPlayer().getInventory().addItem(RandomChestPlateEnchant(Material.LEATHER_CHESTPLATE));
                        e.getView().getPlayer().getInventory().addItem(RandomLeggingsEnchant(Material.LEATHER_LEGGINGS));
                        e.getView().getPlayer().getInventory().addItem(RandomBootsEnchant(Material.LEATHER_BOOTS));
                        player.closeInventory();
                    }
                    break;
            }
            e.setCancelled(true);
        }
    }

    public ItemStack RandomArrowInchant(Material material) {
        ItemStack sword = new ItemStack(material, 1);
        ItemMeta itemMeta = sword.getItemMeta();
        ArrayList<Enchantment> enchantments = new ArrayList<>();
        ArrayList<Integer> enchantmentScore = new ArrayList<>();

        enchantments.add(Enchantment.ARROW_FIRE);
        enchantmentScore.add(5);
        enchantments.add(Enchantment.ARROW_INFINITE);
        enchantmentScore.add(5);
        enchantments.add(Enchantment.ARROW_DAMAGE);
        enchantmentScore.add(4);
        enchantments.add(Enchantment.ARROW_KNOCKBACK);
        enchantmentScore.add(2);
        enchantments.add(Enchantment.DURABILITY);
        enchantmentScore.add(1);

        int rand = (int) ((Math.random() * 10000) % enchantments.size())  + 1;
        int score = 0;
        if(itemMeta != null) {
            while (rand != 0) {
                int kindEnchant = (int) ((Math.random() * 10000) % enchantments.size());
                Enchantment enchantment = enchantments.get(kindEnchant);
                int maxLevel = enchantment.getMaxLevel();
                int randLevel = (int) ((Math.random() * 10000) % maxLevel) + 1;
                itemMeta.addEnchant(enchantment, randLevel, true);
                score += enchantmentScore.get(kindEnchant)*randLevel;
                enchantments.remove(kindEnchant);
                enchantmentScore.remove(kindEnchant);
                rand--;
            }

            ArrayList<String> lore = new ArrayList<>();
            if(score >= 25){
                lore.add(ChatColor.GRAY + "");
                lore.add(ChatColor.DARK_PURPLE + "놀랍습니다! 매우 뛰어난 명궁입니다.");
                lore.add(ChatColor.DARK_PURPLE + "이 활에서 발사된 화살은 스치면 치명상입니다.");
                lore.add(ChatColor.DARK_PURPLE + "두꺼운 갑옷도 이 활 앞에선 무용지물입니다.");
            }
            else if(score >= 15){
                lore.add(ChatColor.GRAY + "");
                lore.add(ChatColor.DARK_RED + "멋진 명궁입니다.");
                lore.add(ChatColor.DARK_RED + "이 활로는 무엇이든 맞출 수 있을 것 같습니다.");
            }
            else if(score >= 5){
                lore.add(ChatColor.GRAY + "");
                lore.add(ChatColor.YELLOW + "그냥 평범한 활입니다.");
                lore.add(ChatColor.YELLOW +"너무 평범에서 적을 말도 없네요.");
            }
            else{
                lore.add(ChatColor.GRAY + "");
                lore.add(ChatColor.GRAY + "무언가 잘못되었습니다..");
                lore.add(ChatColor.GRAY +"누가 이딴걸 만든걸까요?");
            }
            itemMeta.setLore(lore);
        }

        sword.setItemMeta(itemMeta);




        return sword;
    }

    public ItemStack RandomBootsEnchant(Material material) {

        ItemStack boots = new ItemStack(material, 1);
        ItemMeta itemMeta = boots.getItemMeta();
        ArrayList<Enchantment> enchantments = new ArrayList<>();
        ArrayList<Integer> enchantmentScore = new ArrayList<>();

        enchantments.add(Enchantment.FROST_WALKER);
        enchantmentScore.add(3);
        enchantments.add(Enchantment.SOUL_SPEED);
        enchantmentScore.add(3);
        enchantments.add(Enchantment.PROTECTION_EXPLOSIONS);
        enchantmentScore.add(1);
        enchantments.add(Enchantment.PROTECTION_FIRE);
        enchantmentScore.add(2);
        enchantments.add(Enchantment.PROTECTION_PROJECTILE);
        enchantmentScore.add(3);
        enchantments.add(Enchantment.PROTECTION_FALL);
        enchantmentScore.add(3);
        enchantments.add(Enchantment.THORNS);
        enchantmentScore.add(5);
        enchantments.add(Enchantment.PROTECTION_ENVIRONMENTAL);
        enchantmentScore.add(7);
        enchantments.add(Enchantment.DURABILITY);
        enchantmentScore.add(1);

        int rand = (int) ((Math.random() * 10000) % enchantments.size() + 1);
        int score = 0;
        if(itemMeta != null) {
            while (rand != 0) {
                int kindEnchant = (int) ((Math.random() * 10000) % enchantments.size());
                Enchantment enchantment = enchantments.get(kindEnchant);
                int maxLevel = enchantment.getMaxLevel();
                int randLevel = (int) ((Math.random() * 10000) % maxLevel) + 1;
                if(!itemMeta.hasConflictingEnchant(enchantment)) {
                    itemMeta.addEnchant(enchantment, randLevel, true);
                    score += enchantmentScore.get(kindEnchant) * randLevel;
                }
                enchantments.remove(kindEnchant);
                enchantmentScore.remove(kindEnchant);
                rand--;
            }

            ArrayList<String> lore = new ArrayList<>();
            if(score >= 30){
                lore.add(ChatColor.GRAY + "");
                lore.add(ChatColor.DARK_PURPLE + "놀랍습니다!");
                lore.add(ChatColor.DARK_PURPLE + "이미 자신의 한계를 깨버린 듯한 무구입니다.");
                lore.add(ChatColor.DARK_PURPLE + "기대했던 것 이상의 성능을 보여줍니다.");
            }
            else if(score >= 20){
                lore.add(ChatColor.GRAY + "");
                lore.add(ChatColor.DARK_RED + "훌륭한 방어구입니다.");
                lore.add(ChatColor.DARK_RED +"어떤 대장간을 가든 명품으로 취급될만 합니다.");
                lore.add(ChatColor.DARK_RED + "괜찮은 성능을 보여줍니다.");
            }
            else if(score >= 7){
                lore.add(ChatColor.GRAY + "");
                lore.add(ChatColor.YELLOW + "그냥 평범한 방어구입니다.");
                lore.add(ChatColor.YELLOW +"동네 대장장이가 만든 방어구군요.");
            }
            else{
                lore.add(ChatColor.GRAY + "");
                lore.add(ChatColor.GRAY + "무언가 잘못되었습니다..");
                lore.add(ChatColor.GRAY +"불량품 아닐까요..?");
            }
            itemMeta.setLore(lore);
        }

        boots.setItemMeta(itemMeta);
        return boots;
    }
    public ItemStack RandomLeggingsEnchant(Material material) {
        ItemStack leggings = new ItemStack(material, 1);
        ItemMeta itemMeta = leggings.getItemMeta();
        ArrayList<Enchantment> enchantments = new ArrayList<>();
        ArrayList<Integer> enchantmentScore = new ArrayList<>();
        enchantments.add(Enchantment.SWIFT_SNEAK);
        enchantmentScore.add(5);
        enchantments.add(Enchantment.PROTECTION_EXPLOSIONS);
        enchantmentScore.add(1);
        enchantments.add(Enchantment.PROTECTION_FIRE);
        enchantmentScore.add(2);
        enchantments.add(Enchantment.PROTECTION_PROJECTILE);
        enchantmentScore.add(3);
        enchantments.add(Enchantment.PROTECTION_FALL);
        enchantmentScore.add(3);
        enchantments.add(Enchantment.THORNS);
        enchantmentScore.add(5);
        enchantments.add(Enchantment.PROTECTION_ENVIRONMENTAL);
        enchantmentScore.add(7);
        enchantments.add(Enchantment.DURABILITY);
        enchantmentScore.add(1);

        int rand = (int) ((Math.random() * 10000) % enchantments.size() + 1);
        int score = 0;
        if(itemMeta != null) {
            while (rand != 0) {
                int kindEnchant = (int) ((Math.random() * 10000) % enchantments.size());
                Enchantment enchantment = enchantments.get(kindEnchant);
                int maxLevel = enchantment.getMaxLevel();
                int randLevel = (int) ((Math.random() * 10000) % maxLevel) + 1;
                if(!itemMeta.hasConflictingEnchant(enchantment)) {
                    itemMeta.addEnchant(enchantment, randLevel, true);
                    score += enchantmentScore.get(kindEnchant) * randLevel;
                }
                enchantments.remove(kindEnchant);
                enchantmentScore.remove(kindEnchant);
                rand--;
            }

            ArrayList<String> lore = new ArrayList<>();
            if(score >= 30){
                lore.add(ChatColor.GRAY + "");
                lore.add(ChatColor.DARK_PURPLE + "놀랍습니다!");
                lore.add(ChatColor.DARK_PURPLE + "이미 자신의 한계를 깨버린 듯한 무구입니다.");
                lore.add(ChatColor.DARK_PURPLE + "기대했던 것 이상의 성능을 보여줍니다.");
            }
            else if(score >= 20){
                lore.add(ChatColor.GRAY + "");
                lore.add(ChatColor.DARK_RED + "훌륭한 방어구입니다.");
                lore.add(ChatColor.DARK_RED +"어떤 대장간을 가든 명품으로 취급될만 합니다.");
                lore.add(ChatColor.DARK_RED + "괜찮은 성능을 보여줍니다.");
            }
            else if(score >= 7){
                lore.add(ChatColor.GRAY + "");
                lore.add(ChatColor.YELLOW + "그냥 평범한 방어구입니다.");
                lore.add(ChatColor.YELLOW +"동네 대장장이가 만든 방어구군요.");
            }
            else{
                lore.add(ChatColor.GRAY + "");
                lore.add(ChatColor.GRAY + "무언가 잘못되었습니다..");
                lore.add(ChatColor.GRAY +"불량품 아닐까요..?");
            }
            itemMeta.setLore(lore);
        }

        leggings.setItemMeta(itemMeta);
        return leggings;
    }
    public ItemStack RandomChestPlateEnchant(Material material) {

        ItemStack chestPlate = new ItemStack(material, 1);
        ItemMeta itemMeta = chestPlate.getItemMeta();
        ArrayList<Enchantment> enchantments = new ArrayList<>();
        ArrayList<Integer> enchantmentScore = new ArrayList<>();

        enchantments.add(Enchantment.PROTECTION_EXPLOSIONS);
        enchantmentScore.add(1);
        enchantments.add(Enchantment.PROTECTION_FIRE);
        enchantmentScore.add(2);
        enchantments.add(Enchantment.PROTECTION_PROJECTILE);
        enchantmentScore.add(3);
        enchantments.add(Enchantment.PROTECTION_FALL);
        enchantmentScore.add(3);
        enchantments.add(Enchantment.THORNS);
        enchantmentScore.add(5);
        enchantments.add(Enchantment.PROTECTION_ENVIRONMENTAL);
        enchantmentScore.add(7);
        enchantments.add(Enchantment.DURABILITY);
        enchantmentScore.add(1);

        int rand = (int) ((Math.random() * 10000) % enchantments.size() + 1);
        int score = 0;
        if(itemMeta != null) {
            while (rand != 0) {
                int kindEnchant = (int) ((Math.random() * 10000) % enchantments.size());
                Enchantment enchantment = enchantments.get(kindEnchant);
                int maxLevel = enchantment.getMaxLevel();
                int randLevel = (int) ((Math.random() * 10000) % maxLevel) + 1;
                if(!itemMeta.hasConflictingEnchant(enchantment)) {
                    itemMeta.addEnchant(enchantment, randLevel, true);
                    score += enchantmentScore.get(kindEnchant) * randLevel;
                }
                enchantments.remove(kindEnchant);
                enchantmentScore.remove(kindEnchant);
                rand--;
            }

            ArrayList<String> lore = new ArrayList<>();
            if(score >= 30){
                lore.add(ChatColor.GRAY + "");
                lore.add(ChatColor.DARK_PURPLE + "놀랍습니다!");
                lore.add(ChatColor.DARK_PURPLE + "이미 자신의 한계를 깨버린 듯한 무구입니다.");
                lore.add(ChatColor.DARK_PURPLE + "기대했던 것 이상의 성능을 보여줍니다.");
            }
            else if(score >= 20){
                lore.add(ChatColor.GRAY + "");
                lore.add(ChatColor.DARK_RED + "훌륭한 방어구입니다.");
                lore.add(ChatColor.DARK_RED +"어떤 대장간을 가든 명품으로 취급될만 합니다.");
                lore.add(ChatColor.DARK_RED + "괜찮은 성능을 보여줍니다.");
            }
            else if(score >= 7){
                lore.add(ChatColor.GRAY + "");
                lore.add(ChatColor.YELLOW + "그냥 평범한 방어구입니다.");
                lore.add(ChatColor.YELLOW +"동네 대장장이가 만든 방어구군요.");
            }
            else{
                lore.add(ChatColor.GRAY + "");
                lore.add(ChatColor.GRAY + "무언가 잘못되었습니다..");
                lore.add(ChatColor.GRAY +"불량품 아닐까요..?");
            }
            itemMeta.setLore(lore);
        }

        chestPlate.setItemMeta(itemMeta);
        return chestPlate;
    }

    public ItemStack RandomHelmetEnchant(Material material) {
        ItemStack helmet = new ItemStack(material, 1);
        ItemMeta itemMeta = helmet.getItemMeta();
        ArrayList<Enchantment> enchantments = new ArrayList<>();
        ArrayList<Integer> enchantmentScore = new ArrayList<>();

        enchantments.add(Enchantment.WATER_WORKER);
        enchantmentScore.add(1);
        enchantments.add(Enchantment.PROTECTION_FIRE);
        enchantmentScore.add(2);
        enchantments.add(Enchantment.PROTECTION_PROJECTILE);
        enchantmentScore.add(3);
        enchantments.add(Enchantment.PROTECTION_FALL);
        enchantmentScore.add(3);
        enchantments.add(Enchantment.THORNS);
        enchantmentScore.add(5);
        enchantments.add(Enchantment.PROTECTION_ENVIRONMENTAL);
        enchantmentScore.add(7);
        enchantments.add(Enchantment.DURABILITY);
        enchantmentScore.add(1);

        int rand = (int) ((Math.random() * 10000) % enchantments.size() + 1);
        int score = 0;
        if(itemMeta != null) {
            while (rand != 0) {
                int kindEnchant = (int) ((Math.random() * 10000) % enchantments.size());
                Enchantment enchantment = enchantments.get(kindEnchant);
                int maxLevel = enchantment.getMaxLevel();
                int randLevel = (int) ((Math.random() * 10000) % maxLevel) + 1;
                if(!itemMeta.hasConflictingEnchant(enchantment)) {
                    itemMeta.addEnchant(enchantment, randLevel, true);
                    score += enchantmentScore.get(kindEnchant) * randLevel;
                }
                enchantments.remove(kindEnchant);
                enchantmentScore.remove(kindEnchant);
                rand--;
            }

            ArrayList<String> lore = new ArrayList<>();
            if(score >= 30){
                lore.add(ChatColor.GRAY + "");
                lore.add(ChatColor.DARK_PURPLE + "놀랍습니다!");
                lore.add(ChatColor.DARK_PURPLE + "이미 자신의 한계를 깨버린 듯한 무구입니다.");
                lore.add(ChatColor.DARK_PURPLE + "기대했던 것 이상의 성능을 보여줍니다.");
            }
            else if(score >= 20){
                lore.add(ChatColor.GRAY + "");
                lore.add(ChatColor.DARK_RED + "훌륭한 방어구입니다.");
                lore.add(ChatColor.DARK_RED +"어떤 대장간을 가든 명품으로 취급될만 합니다.");
                lore.add(ChatColor.DARK_RED + "괜찮은 성능을 보여줍니다.");
            }
            else if(score >= 7){
                lore.add(ChatColor.GRAY + "");
                lore.add(ChatColor.YELLOW + "그냥 평범한 방어구입니다.");
                lore.add(ChatColor.YELLOW +"동네 대장장이가 만든 방어구군요.");
            }
            else{
                lore.add(ChatColor.GRAY + "");
                lore.add(ChatColor.GRAY + "무언가 잘못되었습니다..");
                lore.add(ChatColor.GRAY +"불량품 아닐까요..?");
            }
            itemMeta.setLore(lore);
        }

        helmet.setItemMeta(itemMeta);




        return helmet;
    }
    public ItemStack RandomSwordEnchant(Material material) {
        ItemStack sword = new ItemStack(material, 1);
        ItemMeta swordItemMetaMeta = sword.getItemMeta();
        ArrayList<Enchantment> enchantments = new ArrayList<>();
        ArrayList<Integer> enchantmentScore = new ArrayList<>();

        enchantments.add(Enchantment.DAMAGE_ALL);
        enchantmentScore.add(4);
        enchantments.add(Enchantment.FIRE_ASPECT);
        enchantmentScore.add(6);
        enchantments.add(Enchantment.KNOCKBACK);
        enchantmentScore.add(2);
        enchantments.add(Enchantment.SWEEPING_EDGE);
        enchantmentScore.add(3);
        enchantments.add(Enchantment.DURABILITY);
        enchantmentScore.add(1);

        int rand = (int) ((Math.random() * 10000) % enchantments.size() + 1);
        int score = 0;
        if(swordItemMetaMeta != null) {
            while (rand != 0) {
                int kindEnchant = (int) ((Math.random() * 10000) % enchantments.size());
                Enchantment enchantment = enchantments.get(kindEnchant);
                int maxLevel = enchantment.getMaxLevel();
                int randLevel = (int) ((Math.random() * 10000) % maxLevel) + 1;
                swordItemMetaMeta.addEnchant(enchantment, randLevel, true);
                score += enchantmentScore.get(kindEnchant)*randLevel;
                enchantments.remove(kindEnchant);
                enchantmentScore.remove(kindEnchant);
                rand--;
            }

            ArrayList<String> lore = new ArrayList<>();
            if(material == Material.NETHERITE_SWORD){
                if(score >= 40){
                    lore.add(ChatColor.GRAY + "");
                    lore.add(ChatColor.DARK_PURPLE + "명성에 걸맞은 놀라운 검입니다.");
                    lore.add(ChatColor.DARK_PURPLE + "드래곤마저 양단할 수 있을 것 같은 기분이 듭니다.");
                    lore.add(ChatColor.DARK_PURPLE + "이 검을 가지고 누구에게 패배한다면...");
                }
                else if(score >= 30){
                    lore.add(ChatColor.GRAY + "");
                    lore.add(ChatColor.DARK_RED + "명성 정도는 아니지만 놀라운 검입니다.");
                    lore.add(ChatColor.DARK_RED +"누구와 싸워도 이길 수 있을 것 같은 기분이 듭니다.");
                }
                else if(score >= 20){
                    lore.add(ChatColor.GRAY + "");
                    lore.add(ChatColor.YELLOW + "소문은 항상 과장되는 법입니다.");
                    lore.add(ChatColor.YELLOW +"그냥 동네 대장장이가 만든 검이군요.");
                }
                else{
                    lore.add(ChatColor.GRAY + "");
                    lore.add(ChatColor.GRAY + "무언가 잘못되었습니다..");
                    lore.add(ChatColor.GRAY +"누가 이걸 용사검이라고 부른 것일까요?");
                }
            }
            if(material == Material.DIAMOND_SWORD){
                if(score >= 40){
                    lore.add(ChatColor.GRAY + "");
                    lore.add(ChatColor.DARK_PURPLE + "명성 이상으로 놀라운 검입니다.");
                    lore.add(ChatColor.DARK_PURPLE + "왜 이것이 용사 검이 되지 못한건지 이해할 수 없습니다.");
                    lore.add(ChatColor.DARK_PURPLE + "아무리 두꺼운 갑옷이라도 이 검 앞에선 소용없을 것 같습니다.");
                }
                else if(score >= 30){
                    lore.add(ChatColor.GRAY + "");
                    lore.add(ChatColor.DARK_RED + "명성대로의 명검입니다.");
                    lore.add(ChatColor.DARK_RED +"누구와 싸워도 이길 수 있을 것 같은 기분이 듭니다.");
                }
                else if(score >= 15){
                    lore.add(ChatColor.GRAY + "");
                    lore.add(ChatColor.YELLOW + "소문은 항상 과장되는 법입니다.");
                    lore.add(ChatColor.YELLOW +"그냥 동네 대장장이가 만든 검이군요.");
                }
                else{
                    lore.add(ChatColor.GRAY + "");
                    lore.add(ChatColor.GRAY + "무언가 잘못되었습니다..");
                    lore.add(ChatColor.GRAY +"누가 이걸 명검이라고 평가한 것일까요?");
                }
            }
            if(material == Material.IRON_SWORD){
                if(score >= 40){
                    lore.add(ChatColor.GRAY + "");
                    lore.add(ChatColor.DARK_PURPLE + "땡 잡았다!");
                    lore.add(ChatColor.DARK_PURPLE + "이게 동네 대장장이가 만든 검이라고? 말도 안되는 소리입니다.");
                    lore.add(ChatColor.DARK_PURPLE + "이 검에는 어떤 비밀이 숨겨져 있는 걸까요?");
                }
                else if(score >= 30){
                    lore.add(ChatColor.GRAY + "");
                    lore.add(ChatColor.DARK_RED + "놀랍습니다!");
                    lore.add(ChatColor.DARK_RED +"이런게 동네 대장장이가 만든 검이라니, 말도 안됩니다.");
                    lore.add(ChatColor.DARK_RED + "사연을 가진 검인게 틀림없습니다.");
                }
                else if(score >= 15){
                    lore.add(ChatColor.GRAY + "");
                    lore.add(ChatColor.YELLOW + "오.. 생각보다 괜찮습니다.");
                    lore.add(ChatColor.YELLOW +"별로 기대하지 않았는데 괜찮은 성능을 보여줄 것 같습니다.");
                }
                else if(score >= 5){
                    lore.add(ChatColor.GRAY + "");
                    lore.add(ChatColor.YELLOW + "이게 뭐야..");
                    lore.add(ChatColor.YELLOW +"실망스럽습니다, 생각보다 성능이 너무 좋지 않네요.");
                }
                else{
                    lore.add(ChatColor.GRAY + "");
                    lore.add(ChatColor.GRAY + "무언가 잘못되었습니다..");
                    lore.add(ChatColor.GRAY +"그냥 부엌 칼로 쓸까요..");
                }
            }
            if(material == Material.STONE_SWORD) {
                if (score >= 40) {
                    lore.add(ChatColor.GRAY + "");
                    lore.add(ChatColor.DARK_PURPLE + "땡 잡았다!");
                    lore.add(ChatColor.DARK_PURPLE + "이런 검이 왜 여기에 있는거지? 성능이 너무 좋습니다.");
                    lore.add(ChatColor.DARK_PURPLE + "이 검에는 어떤 비밀이 숨겨져 있는 걸까요?");
                } else if (score >= 30) {
                    lore.add(ChatColor.GRAY + "");
                    lore.add(ChatColor.DARK_RED + "놀랍습니다!");
                    lore.add(ChatColor.DARK_RED + "돌 검이 맞나요? 너무 잘 썰립니다.");
                    lore.add(ChatColor.DARK_RED + "이 검을 들고있으면 부족장정도는 문제 없을듯 하네요.");
                } else if (score >= 20) {
                    lore.add(ChatColor.GRAY + "");
                    lore.add(ChatColor.YELLOW + "오.. 생각보다 괜찮습니다!!");
                    lore.add(ChatColor.YELLOW + "별로 기대하지 않았는데 괜찮은 성능을 보여줄 것 같습니다.");
                } else if (score >= 10) {
                    lore.add(ChatColor.GRAY + "");
                    lore.add(ChatColor.YELLOW + "이게 뭐야..");
                    lore.add(ChatColor.YELLOW + "실망스럽습니다, 혹시 모를 대박을 노려봤지만 예상대로네요.");
                } else {
                    lore.add(ChatColor.GRAY + "");
                    lore.add(ChatColor.GRAY + "무언가 잘못되었습니다..");
                    lore.add(ChatColor.GRAY + "원시인들도 이런 검은 그냥 길바닥에 버릴 것 같네요.");
                }
            }

            if(material == Material.WOODEN_SWORD) {
                if (score >= 40) {
                    lore.add(ChatColor.GRAY + "");
                    lore.add(ChatColor.DARK_PURPLE + "로또 맞았다!");
                    lore.add(ChatColor.DARK_PURPLE + "이런 검이 왜 여기에 있는거지? 성능이 너무 좋습니다.");
                    lore.add(ChatColor.DARK_PURPLE + "정체가 정말 의심스럽습니다. 대체 누가 만든걸까요?");
                } else if (score >= 30) {
                    lore.add(ChatColor.GRAY + "");
                    lore.add(ChatColor.DARK_RED + "놀랍습니다!");
                    lore.add(ChatColor.DARK_RED + "나무 검 치고는 정말 훌륭합니다.");
                    lore.add(ChatColor.DARK_RED + "실력과 운이 동시에 맞아 만들어진 역작입니다.");
                } else if (score >= 15) {
                    lore.add(ChatColor.GRAY + "");
                    lore.add(ChatColor.YELLOW + "오.. 생각보다 괜찮습니다!!");
                    lore.add(ChatColor.YELLOW + "별로 기대하지 않았는데 괜찮은 성능을 보여줄 것 같습니다.");
                } else if (score >= 10) {
                    lore.add(ChatColor.GRAY + "");
                    lore.add(ChatColor.YELLOW + "이게 뭐야..");
                    lore.add(ChatColor.YELLOW + "실망스럽습니다, 혹시 모를 대박을 노려봤지만 예상대로네요.");
                } else {
                    lore.add(ChatColor.GRAY + "");
                    lore.add(ChatColor.GRAY + "무언가 잘못되었습니다..");
                    lore.add(ChatColor.GRAY + "뭐 나무검에 기대하신건 아니죠? 그냥 칼놀이나 하시면 됩니다.");
                }
            }
            swordItemMetaMeta.setLore(lore);
        }

        sword.setItemMeta(swordItemMetaMeta);




        return sword;
    }
    public boolean hasPlayerInventorySpace(int space, Player player){
        int size = 36;
        int extraSpace = 0;
        for(int i = 0; i< size; i++){
            ItemStack it = player.getInventory().getItem(i);
            if(it == null || it.getType().isAir())
            {
                extraSpace ++;
            }
        }

        return extraSpace >= space;
    }
    public void OpenArmorShopInventory(Player player){

        Inventory inventory = Bukkit.createInventory(player, 9, ChatColor.WHITE + "상점");
        ItemStack item = new ItemStack(Material.NETHERITE_CHESTPLATE, 1);
        ItemMeta itemMeta = item.getItemMeta();
        ArrayList<String> lore = new ArrayList<>();

        lore.add(ChatColor.WHITE + "이 방어구는 용사력 122년에 봉인되어");
        lore.add(ChatColor.WHITE + "자격에 걸맞는 영웅을 기다리고 있는 방어구 세트입니다.");
        lore.add(ChatColor.WHITE + "능력 : " + ChatColor.RED + "[알 수 없음]");
        lore.add(ChatColor.WHITE + "가격 : " + ChatColor.YELLOW +"50000원");
        itemMeta.setDisplayName(ChatColor.WHITE+"네더라이트 갑옷 세트");
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);
        inventory.setItem(0, item);


        item = new ItemStack(Material.DIAMOND_CHESTPLATE, 1);
        itemMeta = item.getItemMeta();
        lore = new ArrayList<>();

        lore.add(ChatColor.WHITE + "한 명장의 손에서 탄생한 방어구 세트입니다.");
        lore.add(ChatColor.WHITE + "매우 단단해보입니다, 어떤 검이 이 갑옷을 뚫을 수 있을까요?");
        lore.add(ChatColor.WHITE + "능력 : " + ChatColor.RED + "[알 수 없음]");
        lore.add(ChatColor.WHITE + "가격 : " + ChatColor.YELLOW+"30000원");
        itemMeta.setDisplayName(ChatColor.WHITE+"다이아몬드 갑옷 세트");
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);
        inventory.setItem(1, item);

        item = new ItemStack(Material.IRON_CHESTPLATE, 1);
        itemMeta = item.getItemMeta();
        lore = new ArrayList<>();
        lore.add(ChatColor.WHITE + "동네에서 이름난 대장장이가 만든 보급용 방어구 세트입니다.");
        lore.add(ChatColor.WHITE + "조금 낡은티가 나는 방어구입니다, 어떤 사연이 숨겨져 있을까요?");
        lore.add(ChatColor.WHITE + "능력 : " + ChatColor.RED + "[알 수 없음]");
        lore.add(ChatColor.WHITE + "가격 : " + ChatColor.YELLOW +"15000원");
        itemMeta.setDisplayName(ChatColor.WHITE+"철 갑옷 세트");
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);
        inventory.setItem(2, item);


        item = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
        itemMeta = item.getItemMeta();
        lore = new ArrayList<>();
        lore.add(ChatColor.WHITE + "북부 지방에서 보온용으로 입은 낡은 옷입니다.");
        lore.add(ChatColor.WHITE + "퀴퀴한 냄새가 납니다, 그래도 맨몸보단 낫겠죠..");
        lore.add(ChatColor.WHITE + "능력 : " + ChatColor.RED + "[알 수 없음]");
        lore.add(ChatColor.WHITE + "가격 : " + ChatColor.YELLOW + "8000원");
        itemMeta.setDisplayName(ChatColor.WHITE+"가죽 갑옷 세트");
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);
        inventory.setItem(3, item);



        player.openInventory(inventory);

    }
    public void OpenWeaponShopInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(player, 36, ChatColor.WHITE + "상점");
        ItemStack item = new ItemStack(Material.NETHERITE_SWORD, 1);
        ItemMeta itemMeta = item.getItemMeta();
        ArrayList<String> lore = new ArrayList<>();
        itemMeta.setDisplayName(ChatColor.DARK_PURPLE + "네더라이트 검");
        lore.add(ChatColor.WHITE + "이 검은 용사력 122년에 봉인되어");
        lore.add(ChatColor.WHITE + "자격에 걸맞는 영웅을 기다리고 있는 검입니다.");
        lore.add(ChatColor.WHITE + "능력 : " + ChatColor.RED + "[알 수 없음]");
        lore.add(ChatColor.WHITE + "가격 : " + ChatColor.YELLOW + "20000원");
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);
        inventory.setItem(0, item);


        item = new ItemStack(Material.DIAMOND_SWORD, 1);
        itemMeta = item.getItemMeta();
        lore = new ArrayList<>();

        lore.add(ChatColor.WHITE + "한 명장의 손에서 탄생한 명검입니다.");
        lore.add(ChatColor.WHITE + "번뜩이는 칼날이 검의 날카로움을 보여주는 듯 합니다.");
        lore.add(ChatColor.WHITE + "능력 : " + ChatColor.RED + "[알 수 없음]");
        lore.add(ChatColor.WHITE + "가격 : " + ChatColor.YELLOW + "15000원");
        itemMeta.setDisplayName(ChatColor.GREEN + "다이아몬드 검");
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);
        inventory.setItem(1, item);

        item = new ItemStack(Material.IRON_SWORD, 1);
        itemMeta = item.getItemMeta();
        lore = new ArrayList<>();
        lore.add(ChatColor.WHITE + "동네에서 이름난 대장장이가 만든 보급용 검입니다.");
        lore.add(ChatColor.WHITE + "날이 조금 빠져있고, 보급용으로 만들어진 검에는 이름이 없습니다.");
        lore.add(ChatColor.WHITE + "하지만 모릅니다, 그에게 숨겨진 능력이 있을지도..?");
        lore.add(ChatColor.WHITE + "능력 : " + ChatColor.RED + "[알 수 없음]");
        lore.add(ChatColor.WHITE + "가격 : " + ChatColor.YELLOW + "8000원");
        itemMeta.setDisplayName(ChatColor.WHITE + "철 검");
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);
        inventory.setItem(1, item);


        item = new ItemStack(Material.STONE_SWORD, 1);
        itemMeta = item.getItemMeta();
        lore = new ArrayList<>();
        lore.add(ChatColor.WHITE + "견습 대장장이가 연습용으로 만들어본 검입니다.");
        lore.add(ChatColor.WHITE + "날카롭다는 말보다는 뭉툭하다는 말이 어울립니다.");
        lore.add(ChatColor.WHITE + "벤다는 생각보다는 부수겠다는 생각으로 휘둘러야 할 것 같습니다.");
        lore.add(ChatColor.WHITE + "능력 : " + ChatColor.RED + "[알 수 없음]");
        lore.add(ChatColor.WHITE + "가격 : " + ChatColor.YELLOW + "3000원");
        itemMeta.setDisplayName(ChatColor.GRAY + "돌 검");
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);
        inventory.setItem(2, item);


        item = new ItemStack(Material.WOODEN_SWORD, 1);
        itemMeta = item.getItemMeta();
        lore = new ArrayList<>();
        lore.add(ChatColor.WHITE + "동네에 손재주 좋은 꼬마가 만든 장난감 검입니다.");
        lore.add(ChatColor.WHITE + "그래도 뭐.. 맨 손보단 낫겠죠?");
        lore.add(ChatColor.WHITE + "참고로 이 꼬마는 먼 훗날 용사검을 만듭니다.");
        lore.add(ChatColor.WHITE + "능력 : " + ChatColor.RED + "[알 수 없음]");
        lore.add(ChatColor.WHITE + "가격 : " + ChatColor.YELLOW + "1000원");
        itemMeta.setDisplayName(ChatColor.GOLD + "나무 검");
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);
        inventory.setItem(3, item);


        item = new ItemStack(Material.BOW, 1);
        itemMeta = item.getItemMeta();
        lore = new ArrayList<>();
        lore.add(ChatColor.WHITE + "엘프의 나무가지를 엮어 만든 활입니다.");
        lore.add(ChatColor.WHITE + "어떠한 잠재능력을 가지고 있는지는 아무도 알 수 없습니다.");
        lore.add(ChatColor.WHITE + "엄청난 명궁일 수도 있고, 그냥 조잡한 활일 수도 있죠.");
        lore.add(ChatColor.WHITE + "능력 : " + ChatColor.RED + "[알 수 없음]");
        lore.add(ChatColor.WHITE + "가격 : " + ChatColor.YELLOW + "10000원");
        itemMeta.setDisplayName(ChatColor.GOLD + "엘프 활");
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);
        inventory.setItem(9, item);


        item = new ItemStack(Material.ARROW, 64);
        itemMeta = item.getItemMeta();
        lore = new ArrayList<>();
        lore.add(ChatColor.WHITE + "철을 깎아 만든 평범한 화살입니다.");
        lore.add(ChatColor.WHITE + "아무런 잠재능력이 없는 평범한 화살입니다.");
        lore.add(ChatColor.WHITE + "가격 : " + ChatColor.YELLOW + "500원");
        itemMeta.setDisplayName(ChatColor.GOLD + "평범한 화살");
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);
        inventory.setItem(10, item);
        CustomStack stack = CustomStack.getInstance("_iainternal:icon_back_orange");
        if (stack != null) {
            stack.setDisplayName("돌아가기");
            inventory.setItem(34, stack.getItemStack());
        }
        stack = CustomStack.getInstance("_iainternal:icon_cancel");
        if (stack != null) {
            stack.setDisplayName("취소");
            inventory.setItem(35, stack.getItemStack());
        }

        player.openInventory(inventory);
    }
    public void OpenShopInventory(Player p){
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
}
