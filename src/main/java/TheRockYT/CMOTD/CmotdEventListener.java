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
        boolean maintenance = CMOTD.getConfig().getBool("maintenance");
        boolean normal = CMOTD.getConfig().getBool("motd.normal.enabled");
        if(!normal && !maintenance){
            return;
        }
        ServerPing ping = event.getResponse();
        ServerPing.Players players = ping.getPlayers();
        String pS = "";
        if(maintenance){
            ping.setVersion(new ServerPing.Protocol(CMOTD.replacePlaceholder(CMOTD.getConfig().getString("motd.maintenance.protocol")),ping.getVersion().getProtocol()-1));
            ping.setDescription(CMOTD.replacePlaceholder(CMOTD.getConfig().getString("motd.maintenance.line1")) + "\n" + CMOTD.replacePlaceholder(CMOTD.getConfig().get("motd.maintenance.line2")));
            pS = CMOTD.replacePlaceholder(CMOTD.getConfig().get("motd.maintenance.players"));
        }else{
            ping.setDescription(CMOTD.replacePlaceholder(CMOTD.getConfig().getString("motd.normal.line1")) + "\n" + CMOTD.replacePlaceholder(CMOTD.getConfig().get("motd.normal.line2")));
            pS = CMOTD.replacePlaceholder(CMOTD.getConfig().get("motd.normal.players"));
        }
        String[] stringSplit = pS.split("\n");
        ServerPing.PlayerInfo[] playerInfos = new ServerPing.PlayerInfo[stringSplit.length];
        for(int i = 0; i < playerInfos.length; i++){
            playerInfos[i] = new ServerPing.PlayerInfo(stringSplit[i], UUID.randomUUID());
        }
        players.setSample(playerInfos);
    }
    @EventHandler
    public void onLogin(PostLoginEvent event) {
        if(CMOTD.getConfig().getBool("maintenance") && !event.getPlayer().hasPermission(CMOTD.getConfig().getString("permission.maintenance.join"))){

            event.getPlayer().disconnect(CMOTD.replacePlaceholder(CMOTD.getConfig().get("kick.maintenance")));
        }else{
            if(event.getPlayer().hasPermission(CMOTD.getConfig().getString("permission.updates"))){
                if(CMOTD.getUpdater().getState() != CMOTDUpdater.CheckState.CHECKING && CMOTD.getUpdater().getState() != CMOTDUpdater.CheckState.LATEST_DEVELOPMENT && CMOTD.getUpdater().getState() != CMOTDUpdater.CheckState.LATEST){

                    event.getPlayer().sendMessage(CMOTD.replacePlaceholder(CMOTD.getConfig().get("messages.update."+CMOTD.getUpdater().getState().toString().toLowerCase())));
                }
            }
        }
    }
}
