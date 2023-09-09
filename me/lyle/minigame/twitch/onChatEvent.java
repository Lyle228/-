package me.lyle.minigame.twitch;

import com.gikk.twirk.events.TwirkListener;
import com.gikk.twirk.types.twitchMessage.TwitchMessage;
import com.gikk.twirk.types.users.TwitchUser;
import me.lyle.minigame.Minigame;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class onChatEvent implements TwirkListener {
    private final Minigame plugin;

    public onChatEvent(Minigame plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onPrivMsg(TwitchUser sender, TwitchMessage message) {
        /*
        for (Player player : Bukkit.getOnlinePlayers()){
            player.sendMessage(ChatColor.LIGHT_PURPLE + "<Twitch> " + ChatColor.WHITE + sender.getDisplayName() + " : " + message.getContent() );
        }
        */

        if(plugin.gameSettings.gamemode == 1 && plugin.gameSettings.gamestep == 2){
            String[] meassages = message.getContent().split(" ");
            if(meassages.length >= 2){
                if(meassages[0].equals("!투표")){
                    String intMeassages = meassages[1].replaceAll("[^0-9]", "");
                    if(!intMeassages.isEmpty()) {
                        int voteNum = Integer.parseInt(intMeassages);
                        if (1 <= voteNum && voteNum <= plugin.gameSettings.gamePlayers.size()) {
                            Minigame.vote.put(plugin.gameSettings.gamePlayers.get(voteNum - 1), Minigame.vote.get(plugin.gameSettings.gamePlayers.get(voteNum - 1)) + 1);
                        }
                    }
                }
            }
        }

        if(plugin.gameSettings.gamemode == 2 && plugin.gameSettings.gamestep == 5){
            String[] meassages = message.getContent().split(" ");
            if(meassages.length >= 2){
                if(meassages[0].equals("!투표")){
                    String intMeassages = meassages[1].replaceAll("[^0-9]", "");
                    if(!intMeassages.isEmpty()) {
                        int voteNum = Integer.parseInt(intMeassages);
                        if (voteNum == 1) {
                            plugin.gameSettings.firstTopicVote ++;
                        }
                        else if(voteNum == 2){
                            plugin.gameSettings.secondTopicVote ++;
                        }
                    }
                }
            }
        }
    }
}
