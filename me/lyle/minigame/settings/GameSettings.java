package me.lyle.minigame.settings;

import me.lyle.minigame.Minigame;
import me.lyle.minigame.utills.CoinShop;
import net.minecraft.server.level.EntityPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import javax.swing.text.html.parser.Entity;
import java.util.ArrayList;
import java.util.UUID;

public class GameSettings {
    public int gamemode = 0;
    public int gamestep = 0;
    public  int gameround = 0;
    public UUID moderator = null;
    public ArrayList<UUID> gamePlayers = new ArrayList<>();

    public int reward1 = 300000;
    public int reward2 = 500000;

    public int reward3 = 100000;

    public int reward4 = 200000;
    public int reward5 = 150000;
    public String firstTopic = null;
    public String secondTopic = null;
    public ArrayList<UUID> selectTopicPlayers = new ArrayList<>();
    public ArrayList<UUID> firstTopicPlayers = new ArrayList<>();
    public ArrayList<UUID> secondTopicPlayers = new ArrayList<>();

    public int firstTopicVote = 0;
    public int secondTopicVote = 0;

    public ArrayList<Boolean> randomGlassList = new ArrayList<>();
    public int glassTurn = 0;

    public ArrayList<Integer> banGameList = new ArrayList<>();
    public ArrayList<EntityPlayer> npcList = new ArrayList<>();

    public int refreshCoinTime = 20;

    public int coin1 = 1000;
    public double coinChange1 = 0;
    public int coin2 = 1000;
    public double coinChange2 = 0;
    public int coin3 = 1000;
    public double coinChange3 = 0;
    public int coin4 = 1000;
    public double coinChange4 = 0;
    public int coin5 = 1000;
    public double coinChange5 = 0;

    public void CoinRefresh(Minigame plugin, boolean isStart){
        int a = 0;
        if(isStart) {
            a = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
                        @Override
                        public void run() {
                            for (Player player : plugin.getServer().getOnlinePlayers()) {
                                if (ChatColor.stripColor(player.getOpenInventory().getTitle()).equalsIgnoreCase("코인 샵")) {
                                    CoinShop coinshop = new CoinShop(plugin);
                                    coinshop.OpenCoinShop(player);
                                }
                                ;
                            }
                            if (refreshCoinTime > 0) {
                                refreshCoinTime--;
                            } else {
                                for (Player player : plugin.getServer().getOnlinePlayers()) {
                                    player.sendMessage(ChatColor.YELLOW + "[코인 샵] 코인 가격이 변동됐습니다.");
                                }

                                double value = Math.random();
                                if(value < 0.5) {
                                    value = ((Math.random()*20) - 10)/100;
                                    coinChange1 = (Math.round(value*1000)/10.0);
                                    coin1 = coin1 + (int)(coin1* value);
                                }
                                else if(value < 0.8){
                                    value = ((Math.random()*30) - 15)/100;
                                    coinChange1 = (Math.round(value*1000)/10.0);;
                                    coin1 = coin1 + (int)(coin1* value);
                                }
                                else {
                                    value = ((Math.random()*40) - 20)/100;
                                    coinChange1 = (Math.round(value*1000)/10.0);;
                                    coin1 = coin1 + (int)(coin1* value);
                                }

                                value = Math.random();
                                if(value < 0.5) {
                                    value = ((Math.random()*20) - 10)/100;
                                    coinChange2 = (Math.round(value*1000)/10.0);;
                                    coin2 = coin2 + (int)(coin2* value);
                                }
                                else if(value < 0.8){
                                    value = ((Math.random()*40) - 20)/100;
                                    coinChange2 = (Math.round(value*1000)/10.0);;
                                    coin2 = coin2 + (int)(coin2* value);
                                }
                                else {
                                    value = ((Math.random()*60) - 30)/100;
                                    coinChange2 = (Math.round(value*1000)/10.0);;
                                    coin2 = coin2 + (int)(coin2* value);
                                }

                                value = Math.random();
                                if(value < 0.5) {
                                    value = ((Math.random()*30) - 15)/100;
                                    coinChange3 = (Math.round(value*1000)/10.0);;
                                    coin3 = coin3 + (int)(coin3* value);
                                }
                                else if(value < 0.8){
                                    value = ((Math.random()*60) - 30)/100;
                                    coinChange3 = (Math.round(value*1000)/10.0);;
                                    coin3 = coin3 + (int)(coin3* value);
                                }
                                else {
                                    value = ((Math.random()*100) - 50)/100;
                                    coinChange3 = (Math.round(value*1000)/10.0);;
                                    coin3 = coin3 + (int)(coin3* value);
                                }

                                value = Math.random();
                                if(value < 0.5) {
                                    value = ((Math.random()*34) - 17)/100;
                                    coinChange4 = (Math.round(value*1000)/10.0);;
                                    coin4 = coin4 + (int)(coin4* value);
                                }
                                else if(value < 0.65){
                                    value = ((Math.random()*60) - 30)/100;
                                    coinChange4 = (Math.round(value*1000)/10.0);;
                                    coin4 = coin4 + (int)(coin4* value);
                                }
                                else if(value < 0.85){
                                    value = ((Math.random()*80) - 40)/100;
                                    coinChange4 = (Math.round(value*1000)/10.0);;
                                    coin4 = coin4 + (int)(coin4* value);
                                }
                                else {
                                    value = ((Math.random()*120) - 60)/100;
                                    coinChange4 = (Math.round(value*1000)/10.0);;
                                    coin4 = coin4 + (int)(coin4* value);
                                }

                                value = Math.random();
                                if(value < 0.5) {
                                    value = ((Math.random()*40) - 20)/100;
                                    coinChange5 = (Math.round(value*1000)/10.0);;
                                    coin5 = coin5 + (int)(coin5* value);
                                }
                                else if(value < 0.65){
                                    value = ((Math.random()*70) - 35)/100;
                                    coinChange5 = (Math.round(value*1000)/10.0);;
                                    coin5 = coin5 + (int)(coin5* value);
                                }
                                else if(value < 0.85){
                                    value = ((Math.random()*120) - 60)/100;
                                    coinChange5 = (Math.round(value*1000)/10.0);;
                                    coin5 = coin5 + (int)(coin5* value);
                                }
                                else {
                                    value = ((Math.random()*200) - 100)/100;
                                    coinChange5 = (Math.round(value*1000)/10.0);;
                                    coin5 = coin5 + (int)(coin5* value);
                                }
                                refreshCoinTime = 20;
                            }
                        }
                    }, 0, 20
            );
        }
        else{
            Bukkit.getScheduler().cancelTask(a);
        }
    }
    public void stopCoinRefresh(){
        //Bukkit.getScheduler().cancelTask(a);
    }


}
