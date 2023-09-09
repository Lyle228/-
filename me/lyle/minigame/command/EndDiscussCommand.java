package me.lyle.minigame.command;

import com.gikk.twirk.Twirk;
import me.lyle.minigame.Minigame;
import me.lyle.minigame.twitch.twitchBot;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

import static me.lyle.minigame.command.StartGame.startGame;
import static me.lyle.minigame.utills.GiveMoney.GiveMoneyPlayer;

public class EndDiscussCommand implements CommandExecutor {
    private final Minigame plugin;

    public EndDiscussCommand(Minigame plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String args[]) {
        if(sender instanceof Player p){
            if(plugin.gameSettings.moderator.equals(p.getUniqueId()) && plugin.gameSettings.gamemode == 2 && plugin.gameSettings.gamestep == 4){
                plugin.gameSettings.gamestep = 5;

                Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                    @Override
                    public void run() {
                        for (me.lyle.minigame.twitch.twitchBot twitchBot : plugin.getTwitchBotArrayList()) {

                            Twirk twirk = twitchBot.getTwirk();

                            StringBuilder twtMSG = new StringBuilder();
                            String msg = "주제 : " + plugin.gameSettings.firstTopic;
                            twtMSG.append(msg);

                            msg = "[1]" + " " + ChatColor.WHITE + plugin.gameSettings.firstTopic + "\n";
                            twtMSG.append(msg);

                            msg = "[2]" + " " + ChatColor.WHITE + plugin.gameSettings.secondTopic + "\n";
                            twtMSG.append(msg);

                            msg = "채팅창에 '!투표 번호'로 더 합리적인 의견에 투표해주세요 [ex) !투표 1]";
                            twtMSG.append(msg);


                            twirk.channelMessage(twtMSG.toString());
                        }
                    }
                }, 100L);

                Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                    @Override
                    public void run() {
                        for (me.lyle.minigame.twitch.twitchBot twitchBot : plugin.getTwitchBotArrayList()) {
                            StringBuilder twtMSG = new StringBuilder();
                            Twirk twirk = twitchBot.getTwirk();
                            String msg = "시청자 여러분 이제 곧 투표가 종료됩니다." + "\n";
                            twtMSG.append(msg);

                            msg = "[1]" + " " + ChatColor.WHITE + plugin.gameSettings.firstTopic + "\n";
                            twtMSG.append(msg);

                            msg = "[2]" + " " + ChatColor.WHITE + plugin.gameSettings.secondTopic + "\n";
                            twtMSG.append(msg);

                            msg = "채팅창에 '!투표 번호'로 더 합리적인 의견에 투표해주세요 [ex) !투표 1]";
                            twtMSG.append(msg);

                            twirk.channelMessage(twtMSG.toString());
                        }
                    }
                }, 500L);


                Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                    @Override
                    public void run() {
                        for (me.lyle.minigame.twitch.twitchBot twitchBot : plugin.getTwitchBotArrayList()) {
                            StringBuilder twtMSG = new StringBuilder();
                            Twirk twirk = twitchBot.getTwirk();
                            String msg = "투표가 종료됐습니다, 감사합니다." + "\n";
                            twtMSG.append(msg);

                            twirk.channelMessage(twtMSG.toString());
                        }
                    }
                }, 710L);


                Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                    @Override
                    public void run() {
                        startGame(plugin);
                    }
                }, 1000L);


                for (Player player : plugin.getServer().getOnlinePlayers()) {
                    if(player != null) {
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
                        player.sendMessage(ChatColor.YELLOW + "");
                        player.sendMessage(ChatColor.YELLOW + "");
                        player.sendMessage(ChatColor.YELLOW + "토론을 종료하겠습니다.");
                        plugin.gameSettings.firstTopicVote = 0;
                        plugin.gameSettings.secondTopicVote = 0;
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
                                player.sendMessage(ChatColor.YELLOW + "주제 : " + plugin.gameSettings.firstTopic);
                                player.sendMessage(ChatColor.YELLOW + "");


                                player.sendMessage(ChatColor.GREEN + "[1]" + " " + ChatColor.WHITE + plugin.gameSettings.firstTopic);
                                player.sendMessage(ChatColor.RED + "[2]" + " " + ChatColor.WHITE + plugin.gameSettings.secondTopic);
                                player.sendMessage(ChatColor.YELLOW + "");
                                player.sendMessage(ChatColor.YELLOW + "채팅창에 '!투표 번호'로 더 합리적인 의견에 투표해주세요 [ex) !투표 1]");
                            }
                        }, 100L);

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
                                player.sendMessage(ChatColor.RED+ "시청자 여러분, 곧 투표가 종료됩니다.");
                                player.sendMessage(ChatColor.YELLOW + "");
                                player.sendMessage(ChatColor.YELLOW + "주제 : " + plugin.gameSettings.firstTopic);
                                player.sendMessage(ChatColor.YELLOW + "");


                                player.sendMessage(ChatColor.GREEN + "[1]" + " " + ChatColor.WHITE + plugin.gameSettings.firstTopic);
                                player.sendMessage(ChatColor.RED + "[2]" + " " + ChatColor.WHITE + plugin.gameSettings.secondTopic);
                                player.sendMessage(ChatColor.YELLOW + "");
                                player.sendMessage(ChatColor.YELLOW + "채팅창에 '!투표 번호'로 더 합리적인 의견에 투표해주세요 [ex) !투표 1]");
                            }
                        }, 500L);


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
                                player.sendMessage(ChatColor.RED+ "투표가 종료됐습니다, 감사합니다.");
                            }
                        }, 710L);

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
                                player.sendMessage(ChatColor.RED+ "투표 결과를 발표하겠습니다..");
                            }
                        }, 770L);

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
                                player.sendMessage(ChatColor.WHITE+ "투표 결과");
                                player.sendMessage(ChatColor.YELLOW + "");
                                player.sendMessage(ChatColor.YELLOW + "");
                                player.sendMessage(ChatColor.GREEN + "[1] " +plugin.gameSettings.firstTopic + "[" + plugin.gameSettings.firstTopicVote  + "표" +"]");
                                player.sendMessage(ChatColor.RED + "[2] "  +plugin.gameSettings.secondTopic + "[" + plugin.gameSettings.secondTopicVote  + "표" +"]");
                                player.sendMessage(ChatColor.YELLOW + "");
                                if(plugin.gameSettings.firstTopicVote >= plugin.gameSettings.secondTopicVote){
                                    int vote = plugin.gameSettings.firstTopicVote - plugin.gameSettings.secondTopicVote;
                                    player.sendMessage(ChatColor.WHITE + "결과는 " + ChatColor.GREEN + ""  + vote + ChatColor.WHITE +"표 차이로 "  +ChatColor.GREEN +"'" +plugin.gameSettings.firstTopic +"'" + ChatColor.WHITE + "의 승리입니다!" );
                                }
                                else{
                                    int vote = plugin.gameSettings.secondTopicVote - plugin.gameSettings.firstTopicVote;
                                    player.sendMessage(ChatColor.WHITE + "결과는 " + ChatColor.RED + ""  + vote + ChatColor.WHITE +"표 차이로 "  +ChatColor.RED + "'" +plugin.gameSettings.secondTopic +"'" + ChatColor.WHITE + "의 승리입니다!" );
                                }
                            }
                        }, 820L);

                        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                            @Override
                            public void run() {
                                if(plugin.gameSettings.firstTopicVote >= plugin.gameSettings.secondTopicVote) {
                                    int reward = plugin.gameSettings.reward2/plugin.gameSettings.firstTopicPlayers.size();
                                    if(plugin.gameSettings.firstTopicPlayers.contains(player.getUniqueId())) {
                                        player.playSound(player.getLocation(), "my_sounds:sound_success", 1.0f, 1.0f);
                                        player.sendMessage(ChatColor.YELLOW + "");
                                        player.sendMessage(ChatColor.YELLOW + "");
                                        player.sendMessage(ChatColor.YELLOW + "");
                                        player.sendMessage(ChatColor.YELLOW + "");
                                        player.sendMessage(ChatColor.YELLOW + "");
                                        player.sendMessage(ChatColor.YELLOW + "");
                                        player.sendMessage(ChatColor.YELLOW + "");
                                        player.sendMessage(ChatColor.YELLOW + "");
                                        player.sendMessage(ChatColor.GREEN + "게임에서 승리했습니다! [+" + reward+"원]");
                                        GiveMoneyPlayer(player.getUniqueId(), reward, plugin);
                                    }
                                }
                                else {
                                    int reward = plugin.gameSettings.reward2/plugin.gameSettings.secondTopicPlayers.size();
                                    if(plugin.gameSettings.secondTopicPlayers.contains(player.getUniqueId())) {
                                        player.playSound(player.getLocation(), "my_sounds:sound_success", 1.0f, 1.0f);
                                        player.sendMessage(ChatColor.YELLOW + "");
                                        player.sendMessage(ChatColor.YELLOW + "");
                                        player.sendMessage(ChatColor.YELLOW + "");
                                        player.sendMessage(ChatColor.YELLOW + "");
                                        player.sendMessage(ChatColor.YELLOW + "");
                                        player.sendMessage(ChatColor.YELLOW + "");
                                        player.sendMessage(ChatColor.YELLOW + "");
                                        player.sendMessage(ChatColor.YELLOW + "");
                                        player.sendMessage(ChatColor.GREEN + "게임에서 승리했습니다! [+" + reward+"원]");
                                        GiveMoneyPlayer(player.getUniqueId(), reward, plugin);
                                    }
                                }
                            }
                        }, 880L);
                    }
                }

            }
        }
        return true;
    }

}
