package TheRockYT.CMOTD;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class CmotdAPI {
    public static void setMaintenance(boolean value){
        Config cfg = CMOTD.getConfig();
        cfg.set("maintenance", value);
        cfg.save();
        String perm = CMOTD.getConfig().getString("permission.maintenance.join");
        if(value){
            for(ProxiedPlayer pl : ProxyServer.getInstance().getPlayers()){
                if(!pl.hasPermission(perm)){
                    pl.disconnect(CMOTD.replacePlaceholder(CMOTD.getConfig().get("kick.maintenance")));
                }
            }
        }

    }
    public static void reload(){
        CMOTD.reload();
    }
}
