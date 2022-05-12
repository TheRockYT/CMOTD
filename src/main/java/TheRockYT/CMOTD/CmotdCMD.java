package TheRockYT.CMOTD;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class CmotdCMD extends Command {
    public CmotdCMD(String name) {
        super(name);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(args.length == 0){
            sender.sendMessage(CMOTD.replacePlaceholder(CMOTD.getConfig().get("messages.cmotd.info")));
        }else if(args.length == 1 && args[0].equalsIgnoreCase("reload")){
            String perm = CMOTD.getConfig().getString("permission.reload");
            if(sender.hasPermission(perm)){
                CmotdAPI.broadcast(CMOTD.replacePlaceholder(CMOTD.getConfig().get("messages.cmotd.reload.start")), perm);
                CmotdAPI.reload();
                CmotdAPI.broadcast(CMOTD.replacePlaceholder(CMOTD.getConfig().get("messages.cmotd.reload.end")), perm);
            }else{
                sender.sendMessage(CMOTD.replacePlaceholder(CMOTD.getConfig().get("messages.cmotd.permission")).replace("%permission%", perm));
            }
        }else if(args.length == 1 && (args[0].equalsIgnoreCase("version") || args[0].equalsIgnoreCase("updates"))){
            String perm = CMOTD.getConfig().getString("permission.updates");
            if(sender.hasPermission(perm)){
                sender.sendMessage(CMOTD.replacePlaceholder(CMOTD.getConfig().get("messages.update.checking")));
                CMOTDUpdater.CheckState oldState = CMOTD.getUpdater().getState();
                CMOTD.getUpdater().check();
                if(oldState == CMOTD.getUpdater().getState()){
                    sender.sendMessage(CMOTD.replacePlaceholder(CMOTD.getConfig().get("messages.update."+oldState.toString().toLowerCase())));
                }
            }else{
                sender.sendMessage(CMOTD.replacePlaceholder(CMOTD.getConfig().get("messages.cmotd.permission")).replace("%permission%", perm));
            }
        }else{
            String perm = CMOTD.getConfig().getString("permission.help");
            if(sender.hasPermission(perm)){
                sender.sendMessage(CMOTD.replacePlaceholder(CMOTD.getConfig().get("messages.cmotd.help")));
            }else{
                sender.sendMessage(CMOTD.replacePlaceholder(CMOTD.getConfig().get("messages.cmotd.permission")).replace("%permission%", perm));
            }
        }
    }
}
