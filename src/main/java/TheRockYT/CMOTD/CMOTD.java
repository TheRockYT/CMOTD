package TheRockYT.CMOTD;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public final class CMOTD extends Plugin {
    private static Config config;
    private static String version;
    private static Plugin plugin;
    private static CMOTDUpdater updater;
    @Override
    public void onEnable() {
        CommandSender cm = ProxyServer.getInstance().getConsole();
        cm.sendMessage("§aLoading §eC§1MOTD §av"+getDescription().getVersion()+" by TheRockYT...");
        config = new Config(new File(getDataFolder(), "config.yml"));
        version = getDescription().getVersion();
        plugin = this;
        reload();
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new MaintenanceCMD("maintenance"));
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new MaintenanceCMD("wartung"));
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new MaintenanceCMD("wartungen"));
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new CmotdCMD("cmotd"));
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new CmotdCMD("motd"));
        getProxy().getPluginManager().registerListener(this, new CmotdEventListener());

        cm.sendMessage("§eC§1MOTD §aloaded.");
        cm.sendMessage("§eThanks for using §eC§1MOTD");
    }
    public static void reload(){
        config.load();
        config.set("info", "CMOTD v"+version+" by TheRockYT.");
        config.add("maintenance", false);

        ArrayList<String> normalPlayers = new ArrayList<>();
        normalPlayers.add("&4--------------------------------");
        normalPlayers.add("&a");
        normalPlayers.add("&eWelcome to our &lMinecraft-Server");
        normalPlayers.add("&a");
        normalPlayers.add("&cJoin now");
        normalPlayers.add("&a");
        normalPlayers.add("&4--------------------------------");

        ArrayList<String> maintenancePlayers = new ArrayList<>();
        maintenancePlayers.add("&4--------------------------------");
        maintenancePlayers.add("&a");
        maintenancePlayers.add("&cMAINTENANCE mode is turned on.");
        maintenancePlayers.add("&a");
        maintenancePlayers.add("&cWe will be back soon.");
        maintenancePlayers.add("&a");
        maintenancePlayers.add("&4--------------------------------");

        config.add("motd.normal.enabled", true);
        config.add("motd.normal.players", normalPlayers);
        config.add("motd.normal.line1", "&eWelcome to our &lMinecraft-Server");
        config.add("motd.normal.line2", "&eC&1MOTD &6by TheRockYT");
        config.add("motd.maintenance.protocol", "&cMAINTENANCE");
        config.add("motd.maintenance.players", maintenancePlayers);
        config.add("motd.maintenance.line1", "&c--- &lMAINTENANCE&c ---");
        config.add("motd.maintenance.line2", "&eC&1MOTD &cby TheRockYT");

        ArrayList<String> maintenanceKick = new ArrayList<>();
        maintenanceKick.add("&4--------------------------------");
        maintenanceKick.add("&a");
        maintenanceKick.add("&cMAINTENANCE mode is turned on.");
        maintenanceKick.add("&a");
        maintenanceKick.add("&cWe will be back soon.");
        maintenanceKick.add("&a");
        maintenanceKick.add("&4--------------------------------");

        config.add("kick.maintenance", maintenanceKick);

        config.add("permission.maintenance.join", "CMOTD.maintenance.join");
        config.add("permission.maintenance.on", "CMOTD.maintenance.on");
        config.add("permission.maintenance.off", "CMOTD.maintenance.off");
        config.add("permission.maintenance.help", "CMOTD.maintenance.help");

        config.add("permission.updates", "CMOTD.updates");
        config.add("permission.help", "CMOTD.help");
        config.add("permission.reload", "CMOTD.reload");

        config.add("messages.maintenance.on", "&eC&1MOTD &6> &aMaintenance was turned on. Only players with the permission \"%permission%\" can join.");
        config.add("messages.maintenance.off", "&eC&1MOTD &6> &cMaintenance was turned off. All players can join.");
        config.add("messages.maintenance.help", "&eC&1MOTD &6> &aUse \"/maintenance <on/off>\".");
        config.add("messages.maintenance.info", "&eC&1MOTD &6> &aPlugin by TheRockYT.");
        config.add("messages.maintenance.permission", "&eC&1MOTD &6> &cYou need the permission \"%permission%\".");

        config.add("messages.cmotd.reload.start", "&eC&1MOTD &6> &aReloading CMOTD...");
        config.add("messages.cmotd.reload.end", "&eC&1MOTD &6> &aCMOTD was successfully reloaded.");

        ArrayList<String> cmotd_help = new ArrayList<>();
        cmotd_help.add("&eC&1MOTD &6> &aUse \"/cmotd reload\".");
        cmotd_help.add("&eC&1MOTD &6> &aUse \"/cmotd version\".");

        config.add("messages.cmotd.help", cmotd_help);
        config.add("messages.cmotd.info", "&eC&1MOTD &6> &aPlugin by TheRockYT.");
        config.add("messages.cmotd.permission", "&eC&1MOTD &6> &cYou need the permission \"%permission%\".");

        ArrayList<String> development_outdated = new ArrayList<>();
        development_outdated.add("&eC&1MOTD &6> &cYou are using an &4outdated &bdevelopment &cversion of &eC&1MOTD&c. &4Your version: v%version%. &aLatest release: v%latest%. &bLatest development: v%development%.");
        development_outdated.add("&eC&1MOTD &6> &aDownload the latest version at https://therockyt.github.io");

        ArrayList<String> outdated = new ArrayList<>();
        outdated.add("&eC&1MOTD &6> &cYou are using an &4outdated &cversion of &eC&1MOTD&c. &4Your version: v%version%. &aLatest release: v%latest%. &bLatest development: v%development%.");
        outdated.add("&eC&1MOTD &6> &aDownload the latest version at https://therockyt.github.io.");

        config.add("messages.update.outdated_development", development_outdated);
        config.add("messages.update.outdated", outdated);
        config.add("messages.update.latest", "&eC&1MOTD &6> &aYou are using the latest release of &eC&1MOTD&a. &aYour version: v%version%. &aLatest release: v%latest%. &bLatest development: v%development%.");
        config.add("messages.update.latest_development", "&eC&1MOTD &6> &aYou are using the latest &bdevelopment &aversion of &eC&1MOTD&a. &bYour version: v%version%. &aLatest release: v%latest%. &bLatest development: v%development%.");
        config.add("messages.update.checking", "&eC&1MOTD &6> &aChecking for updates...");
        config.add("messages.update.check_failed", "&eC&1MOTD &6> &4Update check failed.");
        config.save();
        if(updater != null){
            updater.stop();
        }
        updater = new CMOTDUpdater( version, "https://therockyt.github.io/CMOTD/versions.json", plugin);
        updater.check();
        updater.runUpdater();
    }
    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Config getConfig() {
        return config;
    }

    public static CMOTDUpdater getUpdater() {
        return updater;
    }

    public static String replacePlaceholder(Object object){

        String finalString = null;
        if(object instanceof List){
            for(String str : (List<String>)object){
                if(finalString == null){
                    finalString = str;
                }else{
                    finalString = finalString+"\n"+str;
                }
            }
        }
        if(finalString == null){
            finalString = (String) object;
        }
        finalString = finalString.replace("&", "§");
        finalString = finalString.replace("%version%", version);
        finalString = finalString.replace("%latest%", updater.getLatest());
        finalString = finalString.replace("%development%", updater.getLatestDevelopment());
        return finalString;
    }
}
