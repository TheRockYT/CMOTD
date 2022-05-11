package TheRockYT.CMOTD;

import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.ArrayList;
import java.util.UUID;

public class CmotdEventListener implements Listener {
    @EventHandler
    public void onPing(ProxyPingEvent event){
        ServerPing ping = event.getResponse();
        ServerPing.Players players = ping.getPlayers();
        String pS = "";
        if(CMOTD.getConfig().getBool("maintenance")){
            ping.setVersion(new ServerPing.Protocol("",ping.getVersion().getProtocol()-1));
            ping.setDescription(CMOTD.replacePlaceholder(CMOTD.getConfig().getString("motd.maintenance.line1")) + "\n" + CMOTD.replacePlaceholder(CMOTD.getConfig().get("motd.maintenance.line2")));
            pS = CMOTD.replacePlaceholder(CMOTD.getConfig().get("motd.maintenance.players"));
        }else{
            ping.setDescription(CMOTD.replacePlaceholder(CMOTD.getConfig().getString("motd.normal.line1")) + "\n" + CMOTD.replacePlaceholder(CMOTD.getConfig().get("motd.normal.line2")));
            pS = CMOTD.replacePlaceholder(CMOTD.getConfig().get("motd.normal.players"));
        }
        ArrayList<ServerPing.PlayerInfo> playerInfos = new ArrayList<>();
        for(String str : pS.split("\n")){
            playerInfos.add(new ServerPing.PlayerInfo(str, UUID.randomUUID()));
        }
        players.setSample((ServerPing.PlayerInfo[])playerInfos.toArray());
    }
    @EventHandler
    public void onLogin(PostLoginEvent event) {
        if(CMOTD.getConfig().getBool("maintenance") && !event.getPlayer().hasPermission(CMOTD.getConfig().getString("permission.maintenance.join"))){

            event.getPlayer().disconnect(CMOTD.replacePlaceholder(CMOTD.getConfig().get("kick.maintenance")));
        }
    }
}
