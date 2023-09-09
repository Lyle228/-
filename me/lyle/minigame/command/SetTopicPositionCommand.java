package me.lyle.minigame.command;

import me.lyle.minigame.Minigame;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetTopicPositionCommand implements CommandExecutor {
    private final Minigame plugin;

    public SetTopicPositionCommand(Minigame plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String args[]) {
        if(sender instanceof Player p){
            if(args.length == 1 ){
                if(args[0].equals("1")){
                    if(plugin.gameSettings.firstTopicPlayers.contains(p.getUniqueId()) || plugin.gameSettings.secondTopicPlayers.contains(p.getUniqueId()))
                    {
                        p.sendMessage(ChatColor.RED + "[!] " + ChatColor.WHITE + "이미 입장을 선택했습니다.");
                        return true;
                    }
                    plugin.gameSettings.firstTopicPlayers.add(p.getUniqueId());
                    plugin.gameSettings.selectTopicPlayers.remove(p.getUniqueId());
                    p.sendMessage(ChatColor.GREEN +  "'" + plugin.gameSettings.firstTopic + "'" +"를 선택했습니다.");
                    p.playSound(p.getLocation(),"my_sounds:sound_yes",1.0f,1.0f);
                    if(plugin.gameSettings.selectTopicPlayers.isEmpty()){
                        for (Player player : Bukkit.getOnlinePlayers()) {
                            player.sendMessage(ChatColor.RED + "");
                            player.sendMessage(ChatColor.RED + "");
                            player.sendMessage(ChatColor.RED + "");
                            player.sendMessage(ChatColor.RED + "");
                            player.sendMessage(ChatColor.RED + "");
                            player.sendMessage(ChatColor.RED + "");
                            player.sendMessage(ChatColor.RED + "");
                            player.sendMessage(ChatColor.RED + "");
                            player.sendMessage(ChatColor.RED + "");
                            player.sendMessage(ChatColor.YELLOW + "모든 플레이어가 선택을 완료했습니다.");
                            player.sendMessage(ChatColor.YELLOW + "3초 뒤에 선택에 따라 텔레포트 됩니다.");
                            player.playSound(player.getLocation(),"my_sounds:sound_yes",1.0f,1.0f);
                            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                                @Override
                                public void run() {
                                    if(plugin.gameSettings.firstTopicPlayers.contains(player.getUniqueId())) {
                                        player.teleport(player.getWorld().getBlockAt(-653,84,46).getLocation());
                                    }
                                    else if(plugin.gameSettings.secondTopicPlayers.contains(player.getUniqueId())){
                                        player.teleport(player.getWorld().getBlockAt(-589,77,38).getLocation());
                                    }

                                    if(plugin.gameSettings.moderator.equals(player.getUniqueId())) {
                                        player.sendMessage(ChatColor.YELLOW + "");
                                        player.sendMessage(ChatColor.YELLOW + "");
                                        player.sendMessage(ChatColor.YELLOW + "");
                                        player.sendMessage(ChatColor.YELLOW + "");
                                        player.sendMessage(ChatColor.YELLOW + "");
                                        player.sendMessage(ChatColor.YELLOW + "");
                                        player.sendMessage(ChatColor.YELLOW + "플레이어들을 각자 장소에 텔레포트 시켰습니다.");
                                        player.sendMessage(ChatColor.YELLOW + "사회자님은 이동하시면서 의견이 조율됐는지 확인하시고");
                                        player.sendMessage(ChatColor.YELLOW + "준비가 됐으면 [/토론시작]을 타이핑 해주세요.");
                                    }
                                    else {
                                        player.sendMessage(ChatColor.YELLOW + "");
                                        player.sendMessage(ChatColor.YELLOW + "");
                                        player.sendMessage(ChatColor.YELLOW + "");
                                        player.sendMessage(ChatColor.YELLOW + "");
                                        player.sendMessage(ChatColor.YELLOW + "");
                                        player.sendMessage(ChatColor.YELLOW + "");
                                        player.sendMessage(ChatColor.YELLOW + "");
                                        player.sendMessage(ChatColor.YELLOW + "");
                                        player.sendMessage(ChatColor.YELLOW + "텔레포트 됐습니다, 팀원들과 의견을 조율하세요.");
                                    }
                                }
                            }, 100L);
                        }
                    }
                }
                else if(args[0].equals("2")){
                    if(plugin.gameSettings.firstTopicPlayers.contains(p.getUniqueId()) || plugin.gameSettings.secondTopicPlayers.contains(p.getUniqueId()))
                    {
                        p.sendMessage(ChatColor.RED + "[!] " + ChatColor.WHITE + "이미 입장을 선택했습니다.");
                        return true;
                    }
                    plugin.gameSettings.selectTopicPlayers.remove(p.getUniqueId());
                    plugin.gameSettings.secondTopicPlayers.add(p.getUniqueId());
                    p.sendMessage(ChatColor.RED +  "'" + plugin.gameSettings.secondTopic + "'" +"를 선택했습니다.");
                    p.playSound(p.getLocation(),"my_sounds:sound_yes",1.0f,1.0f);
                    if(plugin.gameSettings.selectTopicPlayers.isEmpty()){
                        for (Player player : Bukkit.getOnlinePlayers()) {
                            player.sendMessage(ChatColor.RED + "");
                            player.sendMessage(ChatColor.RED + "");
                            player.sendMessage(ChatColor.RED + "");
                            player.sendMessage(ChatColor.RED + "");
                            player.sendMessage(ChatColor.RED + "");
                            player.sendMessage(ChatColor.RED + "");
                            player.sendMessage(ChatColor.RED + "");
                            player.sendMessage(ChatColor.RED + "");
                            player.sendMessage(ChatColor.RED + "");
                            player.sendMessage(ChatColor.YELLOW + "모든 플레이어가 선택을 완료했습니다.");
                            player.sendMessage(ChatColor.YELLOW + "3초 뒤에 선택에 따라 텔레포트 됩니다.");
                            player.playSound(player.getLocation(),"my_sounds:sound_yes",1.0f,1.0f);
                            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                                @Override
                                public void run() {
                                    if(plugin.gameSettings.firstTopicPlayers.contains(player.getUniqueId())) {
                                        player.teleport(player.getWorld().getBlockAt(-653,84,46).getLocation());
                                    }
                                    else if(plugin.gameSettings.secondTopicPlayers.contains(player.getUniqueId())){
                                        player.teleport(player.getWorld().getBlockAt(-589,77,38).getLocation());
                                    }

                                    if(plugin.gameSettings.moderator.equals(player.getUniqueId())) {
                                        player.sendMessage(ChatColor.YELLOW + "");
                                        player.sendMessage(ChatColor.YELLOW + "");
                                        player.sendMessage(ChatColor.YELLOW + "");
                                        player.sendMessage(ChatColor.YELLOW + "");
                                        player.sendMessage(ChatColor.YELLOW + "");
                                        player.sendMessage(ChatColor.YELLOW + "");
                                        player.sendMessage(ChatColor.YELLOW + "플레이어들을 각자 장소에 텔레포트 시켰습니다.");
                                        player.sendMessage(ChatColor.YELLOW + "사회자님은 이동하시면서 의견이 조율됐는지 확인하시고");
                                        player.sendMessage(ChatColor.YELLOW + "준비가 됐으면 [/토론시작]을 타이핑 해주세요.");
                                    }
                                    else {
                                        player.sendMessage(ChatColor.YELLOW + "");
                                        player.sendMessage(ChatColor.YELLOW + "");
                                        player.sendMessage(ChatColor.YELLOW + "");
                                        player.sendMessage(ChatColor.YELLOW + "");
                                        player.sendMessage(ChatColor.YELLOW + "");
                                        player.sendMessage(ChatColor.YELLOW + "");
                                        player.sendMessage(ChatColor.YELLOW + "");
                                        player.sendMessage(ChatColor.YELLOW + "");
                                        player.sendMessage(ChatColor.YELLOW + "텔레포트 됐습니다, 팀원들과 의견을 조율하세요.");
                                    }
                                }
                            }, 100L);
                        }
                    }
                }
            }
        }
        return true;
    }

}
