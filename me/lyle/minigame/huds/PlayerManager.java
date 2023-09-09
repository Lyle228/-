package me.lyle.minigame.huds;

import dev.lone.itemsadder.api.FontImages.FontImageWrapper;
import me.lyle.minigame.Minigame;
import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.UUID;

public class PlayerManager {
    private final Minigame plugin;



    public HashMap<UUID, PlayerDataHolder> playerData = new HashMap<>();
    public PlayerManager(Minigame plugin) {
        this.plugin = plugin;
    }

    public void updateMoneyHUD(UUID uuid, int amount){

        PlayerDataHolder dataHolder = playerData.get(uuid);

        if(dataHolder == null){
            dataHolder = new PlayerDataHolder(Bukkit.getPlayer(uuid));
        }

        playerData.put(uuid, dataHolder);
        dataHolder.hudMoney.setVisible(true);
        dataHolder.hudMoney.clearFontImagesAndRefresh();
        String moneyAmount = String.valueOf(amount);
        dataHolder.hudMoney.addFontImage(new FontImageWrapper("moneyhuds:money_icon"));
        for(int i = 0; i< moneyAmount.length(); i++){
            String fontName = "moneyhuds:money_digit_"+ moneyAmount.charAt(i);
            dataHolder.hudMoney.addFontImage(new FontImageWrapper(fontName));
        }

        

    }

}
