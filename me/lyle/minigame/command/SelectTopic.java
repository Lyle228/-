package me.lyle.minigame.command;

import com.gikk.twirk.Twirk;
import me.lyle.minigame.Minigame;
import me.lyle.minigame.twitch.twitchBot;
import me.lyle.minigame.utills.GiveMoney;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;

import static me.lyle.minigame.command.StartGame.startGame;
import static me.lyle.minigame.utills.GiveMoney.GiveMoneyPlayer;

public class SelectTopic implements CommandExecutor {
    private final Minigame plugin;

    public SelectTopic(Minigame plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String args[]) {
        if(args.length >= 1) {
            if(args[0].equals("확정")) {
                if (sender instanceof Player p) {
                    if (plugin.gameSettings.gamemode == 1 && plugin.gameSettings.gamestep == 1 && p.getUniqueId().equals(plugin.gameSettings.moderator)) {
                        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                            @Override
                        public void run(){
                                plugin.gameSettings.gamestep = 2;
                                Minigame.vote.clear();
                                for(UUID playerUUID: plugin.gameSettings.gamePlayers){
                                    Minigame.vote.put(playerUUID,0);
                                }

                                Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                                    @Override
                                    public void run(){
                                        StringBuilder builder = new StringBuilder();

                                        for(int i = 1; i < args.length; i++){
                                            builder.append(args[i]);
                                            builder.append(" ");
                                        }

                                        String topic = builder.toString();
                                        topic = topic.stripTrailing();
                                        for(twitchBot twitchBot : plugin.getTwitchBotArrayList()){

                                            Twirk twirk = twitchBot.getTwirk();
                                            int index = 1;
                                            StringBuilder twtMSG = new StringBuilder();
                                            String msg = "";
                                            msg = "주제 : " + topic + "\n";
                                            twtMSG.append(msg);
                                            for(UUID playerUUID: plugin.gameSettings.gamePlayers) {
                                                Player gamePlayer = Bukkit.getPlayer(playerUUID);
                                                if (gamePlayer != null) {
                                                    msg = "[" + index + "]" + " "  + gamePlayer.getDisplayName() + "\n";
                                                    twtMSG.append(msg);
                                                    index ++;
                                                }
                                            }
                                            msg = "곧 투표가 종료됩니다! [/투표 번호]" + "\n";
                                            twtMSG.append(msg);
                                            twirk.channelMessage(twtMSG.toString());
                                        }
                                        for (Player player : Bukkit.getOnlinePlayers()) {
                                            player.sendMessage(ChatColor.YELLOW + "곧 투표가 종료됩니다!");
                                            player.playSound(player.getLocation(),"my_sounds:sound_yes",1.0f,1.0f);
                                        }
                                    }
                                }, 500L);
                                Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                                    @Override
                                    public void run(){

                                        ArrayList<UUID> endlist = new ArrayList<>();
                                        for(int i = 0; i < Minigame.vote.size(); i ++){
                                            UUID maxUUID = null;
                                            int voted = -1;
                                            for(int j = 0; j < Minigame.vote.size(); j ++){
                                                if(voted < Minigame.vote.get(plugin.gameSettings.gamePlayers.get(j)) && !endlist.contains(plugin.gameSettings.gamePlayers.get(j))){
                                                    voted = Minigame.vote.get(plugin.gameSettings.gamePlayers.get(j));
                                                    maxUUID = plugin.gameSettings.gamePlayers.get(j);
                                                }
                                            }
                                            endlist.add(maxUUID);
                                        }


                                        for(twitchBot twitchBot : plugin.getTwitchBotArrayList()) {
                                            Twirk twirk = twitchBot.getTwirk();
                                            twirk.channelMessage("감사합니다, 투표가 종료됐습니다.");
                                        }
                                        for (Player player : Bukkit.getOnlinePlayers()) {
                                            player.sendMessage(ChatColor.YELLOW + "투표가 종료됐습니다, 이제 순위를 예상하여 자리에 앉아주세요.");
                                            player.sendMessage(ChatColor.YELLOW + "7분 뒤에 결과가 발표됩니다.");
                                            player.playSound(player.getLocation(),"my_sounds:sound_yes",1.0f,1.0f);
                                        }

                                        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                                            @Override
                                            public void run() {
                                                for (Player player : Bukkit.getOnlinePlayers()) {
                                                    player.sendMessage(ChatColor.YELLOW + "투표 결과");
                                                    player.sendMessage(ChatColor.YELLOW + "");
                                                    for (int i = 0; i < Minigame.vote.size(); i++) {
                                                        player.sendMessage(ChatColor.GREEN + "[" + (i + 1) + "위] " + Bukkit.getPlayer(endlist.get(i)).getDisplayName() + "(" + Minigame.vote.get(endlist.get(i)) + "표" + ")");
                                                    }
                                                }
                                                for (int i = 0; i < Minigame.vote.size(); i++) {
                                                    Player player = Bukkit.getPlayer(endlist.get(i));
                                                    if (player != null) {
                                                        if (Minigame.sitChair.containsKey(endlist.get(i))) {
                                                            if (Minigame.sitChair.get(endlist.get(i)) == i + 1) {
                                                                player.playSound(player.getLocation(), "my_sounds:sound_success", 1.0f, 1.0f);
                                                                player.sendMessage(ChatColor.GREEN + "[!] " + ChatColor.WHITE + "정답! 순위를 맞췄습니다.");
                                                            } else {
                                                                player.playSound(player.getLocation(), "my_sounds:sound_fail", 1.0f, 1.0f);
                                                                player.sendMessage(ChatColor.RED + "[!] " + ChatColor.WHITE + "오답! 순위를 틀렸습니다.");
                                                                try {
                                                                    endlist.remove(i);
                                                                } catch (Exception exception) {
                                                                    player.sendMessage("" + exception);
                                                                }
                                                                World world = player.getLocation().getWorld();
                                                                Location location = player.getLocation();
                                                                if (world != null) {
                                                                    world.getBlockAt(location.getBlockX() + 1, location.getBlockY() - 1, location.getBlockZ() - 1).setType(Material.AIR);
                                                                    world.getBlockAt(location.getBlockX() + 1, location.getBlockY() - 1, location.getBlockZ() - 2).setType(Material.AIR);
                                                                    world.getBlockAt(location.getBlockX() + 2, location.getBlockY() - 1, location.getBlockZ() - 2).setType(Material.AIR);
                                                                    world.getBlockAt(location.getBlockX() + 2, location.getBlockY() - 1, location.getBlockZ() - 1).setType(Material.AIR);
                                                                    player.teleport(new Location(world, location.getBlockX() + 1.5, location.getBlockY() + 1.0, location.getBlockZ() - 1.5));
                                                                    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                                                                        @Override
                                                                        public void run() {
                                                                            world.getBlockAt(location.getBlockX() + 1, location.getBlockY() - 1, location.getBlockZ() - 1).setType(Material.SMOOTH_QUARTZ);
                                                                            world.getBlockAt(location.getBlockX() + 1, location.getBlockY() - 1, location.getBlockZ() - 2).setType(Material.SMOOTH_QUARTZ);
                                                                            world.getBlockAt(location.getBlockX() + 2, location.getBlockY() - 1, location.getBlockZ() - 2).setType(Material.SMOOTH_QUARTZ);
                                                                            world.getBlockAt(location.getBlockX() + 2, location.getBlockY() - 1, location.getBlockZ() - 1).setType(Material.SMOOTH_QUARTZ);
                                                                        }
                                                                    }, 40L);
                                                                }
                                                            }
                                                        } else {
                                                            player.sendMessage(ChatColor.RED + "자리에 앉지 않아 실격처리 됐습니다.");
                                                        }
                                                    }

                                                }
                                                int reward = (int) plugin.gameSettings.reward1 / endlist.size();
                                                for (UUID uuid : endlist) {
                                                    Bukkit.getPlayer(uuid).sendMessage(ChatColor.GREEN + "[!] " + ChatColor.WHITE + "상금이 지급됩니다. [+ " + reward + "원]");
                                                    GiveMoneyPlayer(uuid, reward, plugin);
                                                }
                                            }
                                        },200L);

                                        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                                            @Override
                                            public void run() {
                                                startGame(plugin);
                                            }
                                        }, 300L);
                                    }
                                }, 600L);

                        }
                    }, 100L);

                        StringBuilder builder = new StringBuilder();

                        for(int i = 1; i < args.length; i++){
                            builder.append(args[i]);
                            builder.append(" ");
                        }

                        String topic = builder.toString();
                        topic = topic.stripTrailing();

                        String finalTopic1 = topic;
                        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                            @Override
                            public void run() {
                                int index = 1;
                                for(twitchBot twitchBot : plugin.getTwitchBotArrayList()){

                                    Twirk twirk = twitchBot.getTwirk();
                                    twirk.channelMessage("주제 : " + finalTopic1);
                                    StringBuilder twtMSG = new StringBuilder();
                                    for(UUID playerUUID: plugin.gameSettings.gamePlayers) {
                                        Player gamePlayer = Bukkit.getPlayer(playerUUID);
                                        if (gamePlayer != null) {
                                            String msg = "[" + index + "]" + " " + gamePlayer.getDisplayName() + "\n";
                                            twtMSG.append(msg);
                                            index ++;
                                        }
                                    }
                                    twirk.channelMessage(twtMSG.toString());
                                    twirk.channelMessage("채팅창에 '!투표 번호'로 원하시는 멤버에게 투표해주세요 [ex) !투표 1]");
                                }
                            }
                        },100L);
                        for (Player player : Bukkit.getOnlinePlayers()) {
                            player.sendMessage(ChatColor.YELLOW + "주제가 공개됩니다.");

                            player.sendMessage(ChatColor.YELLOW + "주제 : " + topic);
                            String finalTopic2 = topic;
                            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                                @Override
                                public void run() {
                                    player.playSound(player.getLocation(),"my_sounds:sound_yes",1.0f,1.0f);
                                    player.sendMessage(ChatColor.YELLOW + "");
                                    player.sendMessage(ChatColor.YELLOW + "");
                                    player.sendMessage(ChatColor.YELLOW + "");
                                    player.sendMessage(ChatColor.YELLOW + "");
                                    player.sendMessage(ChatColor.YELLOW + "");
                                    player.sendMessage(ChatColor.YELLOW + "");
                                    player.sendMessage(ChatColor.YELLOW + "");
                                    player.sendMessage(ChatColor.YELLOW + "");
                                    player.sendMessage(ChatColor.YELLOW + " 시청자 여러분 30초간 투표를 시작하겠습니다.");
                                    player.sendMessage(ChatColor.YELLOW + "");
                                    player.sendMessage(ChatColor.YELLOW + "주제 : " + finalTopic2);
                                    player.sendMessage(ChatColor.YELLOW + "");



                                    int index = 1;
                                    for(UUID playerUUID: plugin.gameSettings.gamePlayers) {
                                        Player gamePlayer = Bukkit.getPlayer(playerUUID);
                                        if (gamePlayer != null) {
                                            player.sendMessage(ChatColor.AQUA + "[" + index + "]" + " " + ChatColor.WHITE + gamePlayer.getDisplayName());
                                        }
                                        index++;
                                    }
                                    player.sendMessage(ChatColor.YELLOW + "");
                                    player.sendMessage(ChatColor.YELLOW + "채팅창에 '!투표 번호'로 원하시는 멤버에게 투표해주세요 [ex) !투표 1]");
                                }
                            }, 100L);
                        }
                    }
                }
            }
            else if(args[0].equals("취소"))
            {
                sender.sendMessage(ChatColor.RED + "주제를 새롭게 입력해주세요.");
            }
            else if(args[0].equals("확정2"))
            {
                StringBuilder builder = new StringBuilder();

                for(int i = 1; i < args.length; i++){
                    builder.append(args[i]);
                    builder.append(" ");
                }

                String topic = builder.toString();
                topic = topic.stripTrailing();
                String firstTopic = null;
                String secondTopic = null;
                String[] topicSplit = topic.split("//");
                if(topicSplit.length == 2){
                    firstTopic = topicSplit[0];
                    secondTopic = topicSplit[1];
                    plugin.gameSettings.firstTopic= firstTopic;
                    plugin.gameSettings.secondTopic = secondTopic;
                    plugin.gameSettings.selectTopicPlayers.clear();
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        plugin.gameSettings.gamestep = 3;
                        plugin.gameSettings.selectTopicPlayers.add(player.getUniqueId());
                        player.sendMessage(ChatColor.YELLOW + "");
                        player.sendMessage(ChatColor.YELLOW + "");
                        player.sendMessage(ChatColor.YELLOW + "");
                        player.sendMessage(ChatColor.YELLOW + "");
                        player.sendMessage(ChatColor.YELLOW + "");
                        player.sendMessage(ChatColor.YELLOW + "");
                        player.sendMessage(ChatColor.YELLOW + "");
                        player.sendMessage(ChatColor.YELLOW + "");
                        player.sendMessage(ChatColor.YELLOW + "");
                        player.sendMessage(ChatColor.YELLOW + "");
                        player.sendMessage(ChatColor.YELLOW + "주제가 공개됩니다.");
                        player.sendMessage(ChatColor.YELLOW + "");

                        player.sendMessage(ChatColor.GREEN + "첫번째 입장 : " + firstTopic);
                        player.sendMessage(ChatColor.RED + "두번째 입장 : " + secondTopic);
                        TextComponent message = new TextComponent();
                        TextComponent messageYes = new TextComponent();
                        TextComponent messageNo = new TextComponent();
                        String finalFirstTopic = firstTopic;
                        String finalSecondTopic = secondTopic;
                        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                            @Override
                            public void run() {
                                message.setText(ChatColor.WHITE + "입장을 선택해주세요. ");
                                message.setFont("minecraft:uniform");

                                messageYes.setText(ChatColor.GREEN + "[첫번째 입장] ");
                                messageYes.setFont("minecraft:uniform");

                                messageNo.setText(ChatColor.RED + "[두번째 입장]");
                                messageNo.setFont("minecraft:uniform");

                                messageYes.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(finalFirstTopic)));
                                messageYes.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/입장선택 1"));

                                messageNo.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(finalSecondTopic)));
                                messageNo.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/입장선택 2"));

                                player.spigot().sendMessage(message, messageYes, messageNo);
                            }
                        }, 100L);
                    }
                }
            }
            else if(args[0].equals("취소2"))
            {
                plugin.gameSettings.gamestep = 1;
                sender.sendMessage(ChatColor.RED + "첫번째 입장을 새롭게 입력해주세요.");
            }
        }
        return true;
    }

}
