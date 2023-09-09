package me.lyle.minigame.command;

import me.lyle.minigame.Minigame;
import me.lyle.minigame.huds.PlayerDataHolder;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

import static org.bukkit.Sound.ENTITY_PUFFER_FISH_BLOW_UP;

public class StartGame implements CommandExecutor {
    private final Minigame plugin;

    public StartGame(Minigame plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String args[]) {
        if(sender instanceof Player p){
            if(args.length == 0) {
                if(plugin.gameSettings.moderator == null){
                    p.sendMessage(ChatColor.RED + "[!] " + ChatColor.WHITE + "사회자가 임명되지 않았습니다. [/사회자]");
                    return true;
                }
                else if(plugin.gameSettings.gamePlayers.isEmpty()){
                    p.sendMessage(ChatColor.RED + "[!] " + ChatColor.WHITE + "플레이어가 등록되지 않았습니다. [/참가자]");
                    return true;
                }
                p.sendMessage(ChatColor.WHITE + "");
                p.sendMessage(ChatColor.WHITE + "");
                p.sendMessage(ChatColor.WHITE + "");
                p.sendMessage(ChatColor.WHITE + "");
                p.sendMessage(ChatColor.WHITE + "");
                p.sendMessage(ChatColor.WHITE + "");
                p.sendMessage(ChatColor.YELLOW + "[사회자] ");
                Player moderator = Bukkit.getPlayer(plugin.gameSettings.moderator);
                if (moderator != null) {
                    p.sendMessage(ChatColor.WHITE + moderator.getDisplayName());
                }
                p.sendMessage(ChatColor.WHITE + "");
                p.sendMessage(ChatColor.AQUA + "[참가자]");
                for (UUID playerUUID : plugin.gameSettings.gamePlayers) {
                    Player gamePlayer = Bukkit.getPlayer(playerUUID);
                    if (gamePlayer != null) {
                        p.sendMessage(ChatColor.WHITE + gamePlayer.getDisplayName());
                    }
                }
                p.sendMessage(ChatColor.WHITE + "");
                TextComponent message = new TextComponent();
                TextComponent messageYes = new TextComponent();
                TextComponent messageNo = new TextComponent();
                message.setText(ChatColor.WHITE + "게임을 시작하시겠습니까? ");
                message.setFont("minecraft:uniform");

                messageYes.setText(ChatColor.GREEN + "[시작] ");
                messageYes.setFont("minecraft:uniform");

                messageNo.setText(ChatColor.RED + "[취소]");
                messageNo.setFont("minecraft:uniform");


                messageYes.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("게임을 시작합니다.")));
                messageYes.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/게임시작 확정"));


                messageNo.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("게임을 시작하지 않습니다.")));
                messageNo.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/게임시작 취소"));

                p.spigot().sendMessage(message, messageYes,messageNo);

            }
            else {
                if(args[0].equals("확정")){
                    plugin.gameSettings.banGameList.clear();
                    for(Player player : Bukkit.getOnlinePlayers()){

                        player.playSound(player.getLocation(),"my_sounds:sound_yes",1.0f,1.0f);
                        player.sendMessage(ChatColor.YELLOW + "");
                        player.sendMessage(ChatColor.YELLOW + "");
                        player.sendMessage(ChatColor.YELLOW + "");
                        player.sendMessage(ChatColor.YELLOW + "");
                        player.sendMessage(ChatColor.YELLOW + "머니 게임");
                        player.sendMessage(ChatColor.YELLOW + "");
                        player.sendMessage(ChatColor.YELLOW + "");
                        player.sendMessage(ChatColor.YELLOW + "");
                        player.sendMessage(ChatColor.YELLOW + "");
                        player.sendMessage(ChatColor.YELLOW + " 지금부터 머니게임을 시작하겠습니다.");
                        player.sendMessage(ChatColor.YELLOW + " 랜덤으로 여러 게임을 진행하게 되고 우승자에겐 상금이 지급됩니다.");
                        player.sendMessage(ChatColor.YELLOW + " 그 외에도 개인 미션이 존재합니다. 미션을 성공시키면 상금이 지급됩니다.");
                        player.sendMessage(ChatColor.YELLOW + " 그렇게 가장 먼저 100만원을 모은 플레이어가 게임을 우승하게됩니다.");
                        player.sendMessage(ChatColor.YELLOW + "");
                        plugin.playerManager.playerData.put(player.getUniqueId(),new PlayerDataHolder(player));
                        plugin.playerManager.playerData.get(player.getUniqueId()).initHudMoney();
                        plugin.moneyHashmap.put(player.getUniqueId(),0);
                        plugin.gameSettings.gamestep = 0;
                        plugin.gameSettings.gameround = 0;
                    }
                    startGame(plugin);

                }
                else if(args[0].equals("취소")){
                    p.sendMessage(ChatColor.RED + "게임을 취소했습니다.");
                }
            }
        }
        return true;
    }

   public static void startGame(Minigame plugin) {
       plugin.gameSettings.gameround++;
       plugin.gameSettings.gamestep = 0;
       int rand = 0;
       if (plugin.gameSettings.gameround % 4 != 0) {
           do {
               if (plugin.gameSettings.gameround > 3) {
                   rand = (int) ((Math.random() * 10000) % 6);
               } else {
                   rand = (int) ((Math.random() * 10000) % 5);
               }
           } while (plugin.gameSettings.banGameList.contains(rand+1));

           if (rand == 0) {
               plugin.gameSettings.gamemode = 1;
               plugin.gameSettings.banGameList.add(1);
           } else if (rand == 1) {
               plugin.gameSettings.gamemode = 2;
               plugin.gameSettings.banGameList.add(2);
           } else if (rand == 2) {
               plugin.gameSettings.gamemode = 3;
               plugin.gameSettings.banGameList.add(3);
           } else if (rand == 3) {
               plugin.gameSettings.gamemode = 4;
               plugin.gameSettings.banGameList.add(4);
           } else if (rand == 4) {
               plugin.gameSettings.gamemode = 5;
               plugin.gameSettings.banGameList.add(5);

               plugin.gameSettings.randomGlassList.clear();
               for (int i = 0; i < 15; i++) {
                   int ra = (int) ((Math.random() * 10000) % 2);
                   if (ra == 0) {
                       plugin.gameSettings.randomGlassList.add(true);
                   } else {
                       plugin.gameSettings.randomGlassList.add(false);
                   }
               }
               plugin.gameSettings.glassTurn = 0;
           }
           for (Player player : Bukkit.getOnlinePlayers()) {

               Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                   @Override
                   public void run() {
                       player.sendMessage(ChatColor.YELLOW + "");
                       player.sendMessage(ChatColor.YELLOW + "");
                       player.sendMessage(ChatColor.YELLOW + "");
                       player.sendMessage(ChatColor.YELLOW + "");
                       player.sendMessage(ChatColor.RED + String.valueOf(plugin.gameSettings.gameround) + "라운드" + " 게임 추첨을 시작합니다... ");
                       player.playSound(player.getLocation(), "my_sounds:sound_yes", 1.0f, 1.0f);
                   }
               }, 200L);

               int finalRand = rand;
               Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                   @Override
                   public void run() {
                       if (finalRand == 0) {
                           World world = player.getWorld();
                           player.teleport(world.getBlockAt(-610, 98, 30).getLocation());
                           player.sendMessage(ChatColor.YELLOW + "");
                           player.sendMessage(ChatColor.YELLOW + "");
                           player.sendMessage(ChatColor.YELLOW + "");
                           player.sendMessage(ChatColor.YELLOW + "");
                           player.sendMessage(ChatColor.YELLOW + "머니게임 - 순위를 맞춰라");
                           player.sendMessage(ChatColor.YELLOW + "");
                           player.sendMessage(ChatColor.YELLOW + "이번 게임은 '순위를 맞춰라!' 입니다.");
                           player.sendMessage(ChatColor.YELLOW + "사회자가 주제를 정하면 트위치 시청자들의 투표를 받게됩니다.");
                           player.sendMessage(ChatColor.YELLOW + "예를 들어, '이 중에서 가장 안씻을거 어울리는 멤버는?' 이라면");
                           player.sendMessage(ChatColor.YELLOW + "시청자들은 가장 안씻을거 같은 멤버에게 투표를 합니다.");
                           player.sendMessage(ChatColor.YELLOW + "약 30초간 투표가 진행된 뒤에 여러분들은 자신이 몇 위일지");
                           player.sendMessage(ChatColor.YELLOW + "투표 결과를 예상하여 해당되는 의자에 앉으시면 됩니다.");
                           player.sendMessage(ChatColor.YELLOW + "정답을 맞추는 분들은 상금 " + plugin.gameSettings.reward3 + "원을 1/N하여 나누어 가지게됩니다.");
                           player.sendMessage(ChatColor.YELLOW + "시청자분들이 투표하는 법은 따로 안내될 예정이니 기다려주세요.");
                           player.playSound(player.getLocation(), "my_sounds:sound_yes", 1.0f, 1.0f);
                           if (player.getUniqueId().equals(plugin.gameSettings.moderator)) {
                               Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                                   @Override
                                   public void run() {
                                       player.sendMessage(ChatColor.YELLOW + "");
                                       player.sendMessage(ChatColor.YELLOW + "");
                                       player.sendMessage(ChatColor.YELLOW + "");
                                       player.sendMessage(ChatColor.YELLOW + "");
                                       player.sendMessage(ChatColor.RED + "사회자님, 채팅창에 주제를 입력해주세요.");
                                       plugin.gameSettings.gamestep = 1;
                                   }
                               }, 100L);
                           }
                       }
                       if (finalRand == 1) {
                           World world = player.getWorld();
                           player.teleport(world.getBlockAt(-641, 84, 33).getLocation());
                           player.sendMessage(ChatColor.YELLOW + "");
                           player.sendMessage(ChatColor.YELLOW + "");
                           player.sendMessage(ChatColor.YELLOW + "");
                           player.sendMessage(ChatColor.YELLOW + "");
                           player.sendMessage(ChatColor.YELLOW + "머니게임 - 100분 토론");
                           player.sendMessage(ChatColor.YELLOW + "");
                           player.sendMessage(ChatColor.YELLOW + "");
                           player.sendMessage(ChatColor.YELLOW + "");
                           player.sendMessage(ChatColor.YELLOW + "이번 게임은 '100분 토론' 입니다.");
                           player.sendMessage(ChatColor.YELLOW + "사회자가 주제를 정하면 여러분은 둘 중 하나의 입장을 선택해야합니다.");
                           player.sendMessage(ChatColor.YELLOW + "입장을 선택한 뒤 준비시간이 주어집니다.");
                           player.sendMessage(ChatColor.YELLOW + "팀원들과 의견을 조율한 뒤 사회자의 진행에 따라 토론을 진행합니다.");
                           player.sendMessage(ChatColor.YELLOW + "토론이 끝나면 시청자들이 더 합리적이였던 의견에 투표합니다.");
                           player.sendMessage(ChatColor.YELLOW + "시청자 투표에서 승리한 팀은 " + plugin.gameSettings.reward2 + "원을 1/N하여 나누어 가지게됩니다.");
                           player.playSound(player.getLocation(), "my_sounds:sound_yes", 1.0f, 1.0f);
                           if (player.getUniqueId().equals(plugin.gameSettings.moderator)) {
                               Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                                   @Override
                                   public void run() {
                                       player.sendMessage(ChatColor.YELLOW + "");
                                       player.sendMessage(ChatColor.YELLOW + "");
                                       player.sendMessage(ChatColor.YELLOW + "");
                                       player.sendMessage(ChatColor.YELLOW + "");
                                       player.sendMessage(ChatColor.RED + "사회자님, 채팅창에 첫번째 입장을 입력해주세요.");
                                       plugin.gameSettings.gamestep = 1;
                                   }
                               }, 100L);
                           }
                       }

                       if (finalRand == 2) {
                           World world = player.getWorld();
                           player.teleport(world.getBlockAt(-761, 63, 120).getLocation());
                           player.sendMessage(ChatColor.YELLOW + "");
                           player.sendMessage(ChatColor.YELLOW + "");
                           player.sendMessage(ChatColor.YELLOW + "");
                           player.sendMessage(ChatColor.YELLOW + "");
                           player.sendMessage(ChatColor.YELLOW + "머니게임 - 미로 ");
                           player.sendMessage(ChatColor.YELLOW + "");
                           player.sendMessage(ChatColor.YELLOW + "");
                           player.sendMessage(ChatColor.YELLOW + "");
                           player.sendMessage(ChatColor.YELLOW + "이번 게임은 미로 탈출 게임입니다.");
                           player.sendMessage(ChatColor.YELLOW + "최초로 탈출하는 플레이어에 " + plugin.gameSettings.reward3 + "원이 지급됩니다.");
                           player.playSound(player.getLocation(), "my_sounds:sound_yes", 1.0f, 1.0f);
                       }
                       if (finalRand == 3) {
                           World world = player.getWorld();
                           player.teleport(world.getBlockAt(-600, 63, 15).getLocation());
                           player.sendMessage(ChatColor.YELLOW + "");
                           player.sendMessage(ChatColor.YELLOW + "");
                           player.sendMessage(ChatColor.YELLOW + "");
                           player.sendMessage(ChatColor.YELLOW + "");
                           player.sendMessage(ChatColor.YELLOW + "머니게임 - 암벽등반 ");
                           player.sendMessage(ChatColor.YELLOW + "");
                           player.sendMessage(ChatColor.YELLOW + "");
                           player.sendMessage(ChatColor.YELLOW + "");
                           player.sendMessage(ChatColor.YELLOW + "이번 게임은 암벽등반 게임입니다.");
                           player.sendMessage(ChatColor.YELLOW + "최초로 탈출하는 플레이어에 " + plugin.gameSettings.reward4 + "원이 지급됩니다.");
                           player.playSound(player.getLocation(), "my_sounds:sound_yes", 1.0f, 1.0f);
                       }
                       if (finalRand == 4) {
                           World world = player.getWorld();
                           player.teleport(world.getBlockAt(-527, 228, -50).getLocation());
                           player.sendMessage(ChatColor.YELLOW + "");
                           player.sendMessage(ChatColor.YELLOW + "");
                           player.sendMessage(ChatColor.YELLOW + "");
                           player.sendMessage(ChatColor.YELLOW + "");
                           player.sendMessage(ChatColor.YELLOW + "머니게임 - 유리 다리를 건너라! ");
                           player.sendMessage(ChatColor.YELLOW + "");
                           player.sendMessage(ChatColor.YELLOW + "");
                           player.sendMessage(ChatColor.YELLOW + "이번 게임은 '유리 다리를 건너라!' 게임입니다.");
                           player.sendMessage(ChatColor.YELLOW + "매번 선택지에서 둘 중 하나는 부숴지는 유리입니다.");
                           player.sendMessage(ChatColor.YELLOW + "최초로 건너는 플레이어에 " + plugin.gameSettings.reward5 + "원이 지급됩니다.");
                           player.playSound(player.getLocation(), "my_sounds:sound_yes", 1.0f, 1.0f);
                           Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                               @Override
                               public void run() {
                                   player.sendMessage(ChatColor.YELLOW + "");
                                   player.sendMessage(ChatColor.YELLOW + "");
                                   player.sendMessage(ChatColor.YELLOW + "");
                                   player.sendMessage(ChatColor.YELLOW + "");
                                   player.sendMessage(ChatColor.RED + "" + Bukkit.getPlayer(plugin.gameSettings.gamePlayers.get(0)).getDisplayName() + ChatColor.WHITE + "님의 "
                                           + ChatColor.RED + (plugin.gameSettings.glassTurn / plugin.gameSettings.gamePlayers.size() + 1) + ChatColor.WHITE + "번째 도전");
                                   if (player.getUniqueId().equals(plugin.gameSettings.gamePlayers.get(0))) {
                                       player.teleport(world.getBlockAt(-559, 221, -63).getLocation());
                                   }
                               }
                           }, 80L);
                       }
                   }
               }, 300L);
           }


       } else {
           plugin.gameSettings.banGameList.clear();
           for (Player player : Bukkit.getOnlinePlayers()) {
               Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                   @Override
                   public void run() {
                       player.sendMessage(ChatColor.RED + String.valueOf(plugin.gameSettings.gameround) + "라운드는 20분간 휴식입니다. ");
                       player.sendMessage(ChatColor.YELLOW + "");
                       player.sendMessage(ChatColor.YELLOW + "");
                       player.sendMessage(ChatColor.YELLOW + "저택에서는 코인을 이용하여 한방 역전을 노릴 수도 있고");
                       player.sendMessage(ChatColor.YELLOW + "낚시를 하며 착실히 돈을 모을 수 있습니다.");
                       player.sendMessage(ChatColor.YELLOW + "단 몇몇 플레이어는 미션을 가지고 있습니다.");
                       player.sendMessage(ChatColor.YELLOW + "그들이 미션을 달성하지 못하도록 방해하세요.");
                       player.playSound(player.getLocation(), "my_sounds:sound_yes", 1.0f, 1.0f);
                       player.teleport(player.getWorld().getBlockAt(-610, 65, -418).getLocation());

                       plugin.gameSettings.CoinRefresh(plugin,true);
                   }
               }, 200L);

           }
       }
   }


}
