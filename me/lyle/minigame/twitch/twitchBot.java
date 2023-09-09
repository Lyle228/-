package me.lyle.minigame.twitch;


import com.gikk.twirk.Twirk;
import com.gikk.twirk.TwirkBuilder;
import me.lyle.minigame.Minigame;


public class twitchBot {
    private Twirk twirk;

    public Twirk getTwirk() {
        return twirk;
    }

    public void setTwirk(Twirk twirk) {
        this.twirk = twirk;
    }

    public boolean reload(Minigame plugin) {
        twirk = new TwirkBuilder("lyle22885", "haha", "oauth:zqyw3hftyyh9epi7cp7ux4km7qtt6k").setVerboseMode(true).build();
        twirk.addIrcListener(new onChatEvent(plugin));
        try{
            twirk.connect();
            return true;
        } catch (Exception e){
            System.out.println("봇 연결 실패");
        return false;
    }

    }
    public void disconnect(){
        if(twirk.isConnected()) {
            twirk.disconnect();
        }
    }

}
