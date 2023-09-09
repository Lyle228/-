package me.lyle.minigame.huds;

import dev.lone.itemsadder.api.FontImages.FontImageWrapper;
import dev.lone.itemsadder.api.FontImages.PlayerCustomHudWrapper;
import dev.lone.itemsadder.api.FontImages.PlayerHudsHolderWrapper;
import org.bukkit.entity.Player;

public class PlayerDataHolder {
    private Player player;
    public PlayerHudsHolderWrapper holder;
    private static final String hudMoneyName = "moneyhuds:money";

    public PlayerCustomHudWrapper hudMoney;

    public PlayerDataHolder(Player player){
        this.player = player;
        holder = new PlayerHudsHolderWrapper(player);
        hudMoney = new PlayerCustomHudWrapper(holder, hudMoneyName);
    }

    public void initHudMoney()
    {
        hudMoney.clearFontImagesAndRefresh();
        hudMoney.addFontImage(new FontImageWrapper("moneyhuds:money_icon"));
        hudMoney.addFontImage(new FontImageWrapper("moneyhuds:money_digit_0"));
    }
}
