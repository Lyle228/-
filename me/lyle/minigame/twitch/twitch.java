package me.lyle.minigame.twitch;


import com.gikk.twirk.Twirk;
import com.gikk.twirk.TwirkBuilder;
import com.gikk.twirk.events.TwirkListener;
import com.gikk.twirk.types.twitchMessage.TwitchMessage;
import com.gikk.twirk.types.users.TwitchUser;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.IOException;
/*
public class twitch implements TwirkListener {
    public static void main(String[] args) throws IOException, InterruptedException {
        final Twirk twirk = new TwirkBuilder("rlaalstlr228", "haha", "2y331d799k6ezxpo6nxo22x4f7ul7s").setVerboseMode(true).build();

        twirk.addIrcListener( getOnDisconnectListener(twirk) );

        twirk.connect();

        System.out.println("completecompletecompletecompletecompletecompletecompletecompletecompletecomplete");
    }
    private static TwirkListener getOnDisconnectListener(final Twirk twirk) {

        return new TwirkListener() {
            @Override
            public void onDisconnect() {
                //Twitch might sometimes disconnects us from chat. If so, try to reconnect.
                try {
                    if( !twirk.connect() )
                        //Reconnecting might fail, for some reason. If so, close the connection and release resources.
                        twirk.close();
                }
                catch (IOException e) {
                    //If reconnection threw an IO exception, close the connection and release resources.
                    twirk.close();
                }
                catch (InterruptedException ignored) {  }
            }
        };

    }


}
*/