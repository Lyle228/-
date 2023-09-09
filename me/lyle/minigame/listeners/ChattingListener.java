package me.lyle.minigame.listeners;

import me.lyle.minigame.Minigame;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChattingListener implements Listener {
    private final Minigame plugin;

    public ChattingListener(Minigame plugin) {
        this.plugin = plugin;
    }
    String firstTopic = null;
    @EventHandler
    public void OnPlayerChatting(AsyncPlayerChatEvent event){
        Player player = event.getPlayer();
        if(plugin.gameSettings.moderator == player.getUniqueId() && plugin.gameSettings.gamemode == 1 && plugin.gameSettings.gamestep == 1){
            event.setCancelled(true);

            TextComponent message = new TextComponent();
            TextComponent messageYes = new TextComponent();
            TextComponent messageNo = new TextComponent();
            message.setText(ChatColor.WHITE + "주제 : " + event.getMessage() + " ");
            message.setFont("minecraft:uniform");

            messageYes.setText(ChatColor.GREEN + "[확정] ");
            messageYes.setFont("minecraft:uniform");

            messageNo.setText(ChatColor.RED + "[취소]");
            messageNo.setFont("minecraft:uniform");


            messageYes.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("주제를 확정합니다")));
            messageYes.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/주제 확정 " + event.getMessage()));


            messageNo.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("주제를 다시 정합니다.")));
            messageNo.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/주제 취소"));
            Player moderator =  Bukkit.getServer().getPlayer(plugin.gameSettings.moderator);
            if(moderator!=null) {
                moderator.spigot().sendMessage(message, messageYes, messageNo);
            }
        }

        if(plugin.gameSettings.moderator == player.getUniqueId() && plugin.gameSettings.gamemode == 2 && plugin.gameSettings.gamestep == 1){
            event.setCancelled(true);
            player.sendMessage("");
            player.sendMessage("");
            player.sendMessage("");
            player.sendMessage("");
            player.sendMessage("");
            player.sendMessage("");
            player.sendMessage("");
            player.sendMessage("");
            player.sendMessage(ChatColor.WHITE + "첫번째 입장은 " + ChatColor.GREEN + event.getMessage() + ChatColor.WHITE  + "입니다");
            firstTopic = event.getMessage();
            plugin.gameSettings.gamestep = 2;
            player.sendMessage(ChatColor.WHITE + "두번째 입장을 입력해주세요.");
        }
        else if (plugin.gameSettings.moderator == player.getUniqueId() && plugin.gameSettings.gamemode == 2 && plugin.gameSettings.gamestep == 2){
            event.setCancelled(true);
            player.sendMessage("");
            player.sendMessage("");
            player.sendMessage("");
            player.sendMessage("");
            player.sendMessage("");
            player.sendMessage("");
            player.sendMessage("");
            player.sendMessage("");
            player.sendMessage(ChatColor.GREEN + "첫번째 입장 : " + firstTopic);
            player.sendMessage(ChatColor.RED + "두번째 입장 : " + event.getMessage());

            TextComponent message = new TextComponent();
            TextComponent messageYes = new TextComponent();
            TextComponent messageNo = new TextComponent();
            message.setText(ChatColor.WHITE + "이대로 확정하시겠습니까? ");
            message.setFont("minecraft:uniform");

            messageYes.setText(ChatColor.GREEN + "[확정] ");
            messageYes.setFont("minecraft:uniform");

            messageNo.setText(ChatColor.RED + "[취소]");
            messageNo.setFont("minecraft:uniform");

            messageYes.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("주제를 확정합니다")));
            messageYes.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/주제 확정2 " + firstTopic + "//" + event.getMessage()));

            messageNo.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("주제를 다시 정합니다.")));
            messageNo.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/주제 취소2"));
            player.spigot().sendMessage(message, messageYes, messageNo);
        }
    }

}
