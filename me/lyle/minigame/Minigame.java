package me.lyle.minigame;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.gikk.twirk.Twirk;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import dev.lone.itemsadder.api.CustomStack;
import dev.lone.itemsadder.api.FontImages.PlayerCustomHudWrapper;
import dev.lone.itemsadder.api.FontImages.PlayerHudsHolderWrapper;
import me.lyle.minigame.command.*;
import me.lyle.minigame.huds.PlayerManager;
import me.lyle.minigame.listeners.*;
import me.lyle.minigame.settings.GameSettings;
import me.lyle.minigame.twitch.twitchBot;
import me.lyle.minigame.utills.CoinShop;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket;
import net.minecraft.network.protocol.game.PacketPlayOutEntityDestroy;
import net.minecraft.network.protocol.game.PacketPlayOutEntityHeadRotation;
import net.minecraft.network.protocol.game.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.PlayerInteractManager;
import net.minecraft.server.level.WorldServer;
import net.minecraft.server.network.PlayerConnection;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.craftbukkit.v1_19_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public final class Minigame extends JavaPlugin implements Listener {

    public static Minigame instance;

    public GameSettings gameSettings = new GameSettings();
    public HashMap<UUID, Integer> moneyHashmap = new HashMap<>();
    public PlayerManager playerManager = new PlayerManager(this);

    public static HashMap<UUID, Integer> sitChair = new HashMap<>();

    public static HashMap<UUID,Integer> vote = new HashMap<>();


    private ArrayList<twitchBot> twitchBotArrayList = new ArrayList<>();
    @Override
    public void onEnable() {
        instance = this;

        twitchBotArrayList.add(new twitchBot());

        boolean success = twitchBotArrayList.get(0).reload(instance);
        getCommand("사회자").setExecutor(new SetModerator(this));
        getCommand("참가자").setExecutor(new SetGamePlayer(this));
        getCommand("게임시작").setExecutor(new StartGame(this));
        getCommand("주제").setExecutor(new SelectTopic(this));
        getCommand("성벽").setExecutor(new CreateWall());
        getCommand("돈지급").setExecutor(new GiveMoneyCommand(this));
        getCommand("상점").setExecutor(new ShopCommand());
        getCommand("입장선택").setExecutor(new SetTopicPositionCommand(this));
        getCommand("토론시작").setExecutor(new StartDiscussCommand(this));
        getCommand("토론종료").setExecutor(new EndDiscussCommand(this));
        getServer().getPluginManager().registerEvents(this,this);
        getServer().getPluginManager().registerEvents(new ShopListener(this),this);
        getServer().getPluginManager().registerEvents(new ChattingListener(this),this);
        getServer().getPluginManager().registerEvents(new MountListener(),this);
        getServer().getPluginManager().registerEvents(new PlayerMoveListener(this),this);
        getServer().getPluginManager().registerEvents(new PlayerDeathListener(this),this);
        if(!success){

        }

        String texture = "ewogICJ0aW1lc3RhbXAiIDogMTY4OTA4MTgzMjEzNSwKICAicHJvZmlsZUlkIiA6ICIxMWEyY2MwNDYyOTA0ZmI4YjgwNDQxZTQyZDRiZjZkMSIsCiAgInByb2ZpbGVOYW1lIiA6ICJMeWxlMjI4IiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlL2Q5MDAwZjQ4MDA4OGJkOGZiNDI3OTZhZDIxZWY4ODZlOGExNWZiYTM1ZWQ0YTA4Y2FkMTQ2Yzk4Mzk0ODViMjkiCiAgICB9CiAgfQp9";
        String signature = "EXL6PV1UlwbeCrMxp1oOAZ2KhqsJYvml24HQbKoZbkALXOac033D0/00W9TkuR6CLWP2h9Aaz/FFT1kqQI1x4WcFxspmMr3mPA3DCrORDerxuFa+e5GXIBx4Afkq34sVg5XuhoYtWPHwZoHQLD6y9dfyz8pTHFI4ZBv9tKh1uNv698WtMon1GiBU2q446viKpf8uAsPyXZO/BDMB+Kxh/wukZUl7exRNy2wD/oBzSXvu4CH2IXxHfAXNDqepW10zk1wBWjsECNZOqRa4IS1NWzeOh+woShv6kpj2mH6T2k3lAuwKtjmvc6xJvrionjH2mJGbl6WBYG/KWsUiH4gOXVGt5Cz8BulcVy+Xxhp53/g6QEV+o1VqK3+0NpMgwybxsbFzSs/WrG2eCnU2XzyAsn0MZDqBh7SgSspm2gmlHTq4Mw3InYDBf+rsk5qR3xc8GtaSBKHBwOiHYOC+CsPz56nBuy+S6DZxrSdb2DiawwAKS+CIsTsrRS2JvAiR2MgGi1/X/LVf9TNFj3i7eMPclxbVryq+0ByPWnX/1Hs7d3QZaLYRweAYnc682sIO9H9Z9rdlp+tBj88WTNHPlBzb2AAoAAPRVk1SUGwtQTrS9OAj3KgxYiCKA2FGRAx66xHumI+i8Rvmum+8Ul+UZh0O2707A3H5RejhXpmyQTeLBfE=";
        MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
        WorldServer world = ((CraftWorld)Bukkit.getWorld("world")).getHandle();
        GameProfile gameProfile = new GameProfile(UUID.randomUUID(), "딜러");
        gameProfile.getProperties().put("textures", new Property("textures",texture,signature));
        EntityPlayer npcPlayer = new EntityPlayer(server, world, gameProfile);
        npcPlayer.b(-597.7, 65, -419.5,90,-10);

        this.gameSettings.npcList.add(npcPlayer);

        texture = "ewogICJ0aW1lc3RhbXAiIDogMTY4OTA3ODA4MTc3NiwKICAicHJvZmlsZUlkIiA6ICIwNjlhNzlmNDQ0ZTk0NzI2YTViZWZjYTkwZTM4YWFmNSIsCiAgInByb2ZpbGVOYW1lIiA6ICJOb3RjaCIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS8yOTIwMDlhNDkyNWI1OGYwMmM3N2RhZGMzZWNlZjA3ZWE0Yzc0NzJmNjRlMGZkYzMyY2U1NTIyNDg5MzYyNjgwIgogICAgfQogIH0KfQ==";
        signature = "SPwaNsE5MkI3Su4KfXXjEq+ukm6QpLut8yz5FUECE05rsupWLh3/sSbX15rK2x+ipa2HjLniqvPTx0a6qKE69LLn6kaqoASjFJsfHS6DljtsaAaO5I1mSZvNbjNKqxD1XpSWMEBbjhwV5nO0uK5JblKjyYNJlCV96B46LoUFzyTXgi8gLZ6v1INWPvw1N5FKBOdcMLxwTSE3oNs8QddBRDAcpQJ9S4cS7QM0cSt3QLq2kL2i/to6sVMnk77ct+OHdPSSQVA5waVMNruO1wBn8YYQXc3V2115fLJ48ty4A/EfwBMSuAGWK9FAUABhYPSswEkvjSgocnXHW7TJOc0H+HXft2KNxZMgwjBQcN3qz5GdbMbPcps7fIxUphVSFsihaCCUGe7LsZJ4Kxr6EAnYW41FxjcldK1IfPEp+7Fp1DpY96K91JibUt3zOPitSNpyhtBAafaMTp0bl4Tp3i6E/HHdv/Kv5JzUB4G1PEpH/c2WNoDRKl7MnTua5E60uGsqDDHFQBJL0hMfHY9G9Z2gAkrZlIa/3WEhRtzaJ38IzI8fBpjvSWnB2zbNTSxBDOrO/jykVgsBzirWivre01X5oBaegtQoaR37/2+3UWWnahkspGGsXPXEd0Xp/b4M5ppsFcI1u/FtVp4Z5xtlnAjbfdydMsgLJ14Wq2zakxOM/dQ=";
        server = ((CraftServer) Bukkit.getServer()).getServer();
        world = ((CraftWorld)Bukkit.getWorld("world")).getHandle();
        gameProfile = new GameProfile(UUID.randomUUID(), "강태공");
        gameProfile.getProperties().put("textures", new Property("textures",texture,signature));
        npcPlayer = new EntityPlayer(server, world, gameProfile);
        npcPlayer.b(-597.7, 65, -427.5,90,-10);

        this.gameSettings.npcList.add(npcPlayer);


        ProtocolManager manager = ProtocolLibrary.getProtocolManager();
        manager.addPacketListener(new PacketAdapter(this, PacketType.Play.Client.USE_ENTITY) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                PacketContainer packet = event.getPacket();

                int entityID = packet.getIntegers().read(0);
                gameSettings.npcList.stream()
                        .forEach(npc -> {
                            if(npc.getBukkitEntity().getEntityId() == entityID)
                            {
                                EnumWrappers.Hand hand = packet.getEnumEntityUseActions().read(0).getHand();
                                EnumWrappers.EntityUseAction action = packet.getEnumEntityUseActions().read(0).getAction();

                                if(hand == EnumWrappers.Hand.MAIN_HAND && action == EnumWrappers.EntityUseAction.INTERACT && npc.getBukkitEntity().getDisplayName().equals("딜러")){
                                    Player player = event.getPlayer();

                                    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                                        @Override
                                        public void run() {
                                            CoinShop coinshop = new CoinShop(instance);
                                            coinshop.OpenCoinShop(event.getPlayer());
                                        }
                                    },1);

                                }
                            }
                        });
            }
        });


    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        if(!moneyHashmap.containsKey(player.getUniqueId())){
            moneyHashmap.put(player.getUniqueId(), 0);
        }
        PlayerHudsHolderWrapper holder = new PlayerHudsHolderWrapper(player);
        PlayerCustomHudWrapper moneyHud =
                new PlayerCustomHudWrapper(holder, "moneyhuds:money");
        if(moneyHud.exists()){
            moneyHud.setVisible(true);

            holder.recalculateOffsets();
            holder.sendUpdate();
        }
        for(EntityPlayer npcPlayer : gameSettings.npcList){
            PlayerConnection connection = ((CraftPlayer)player).getHandle().b;
            connection.a(new ClientboundPlayerInfoUpdatePacket(ClientboundPlayerInfoUpdatePacket.a.a, npcPlayer));
            connection.a(new PacketPlayOutNamedEntitySpawn(npcPlayer));
        }
    }
    @Override
    public void onDisable() {
        // Plugin shutdown logic
        twitchBotArrayList.get(0).disconnect();
    }


    public ArrayList<twitchBot> getTwitchBotArrayList() {
        return twitchBotArrayList;
    }

    public void setTwitchBotArrayList(ArrayList<twitchBot> twitchBotArrayList) {
        this.twitchBotArrayList = twitchBotArrayList;
    }
}
