package TheRockYT.CMOTD;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class MaintenanceCMD extends Command {
    public MaintenanceCMD(String name) {
        super(name);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(args.length == 0){
            sender.sendMessage(CMOTD.replacePlaceholder(CMOTD.getConfig().get("messages.maintenance.info")));
        }else if(args.length == 1 && args[0].equalsIgnoreCase("on")){
            String perm = CMOTD.getConfig().getString("permission.maintenance.on");
            if(sender.hasPermission(perm)){
                CmotdAPI.setMaintenance(true);
                sender.sendMessage(CMOTD.replacePlaceholder(CMOTD.getConfig().get("messages.maintenance.on")));
            }else{
                sender.sendMessage(CMOTD.replacePlaceholder(CMOTD.getConfig().get("messages.maintenance.permission")).replace("%permission%", perm));
            }
        }else if(args.length == 1 && args[0].equalsIgnoreCase("off")){
            String perm = CMOTD.getConfig().getString("permission.maintenance.off");
            if(sender.hasPermission(perm)){
                CmotdAPI.setMaintenance(false);
                sender.sendMessage(CMOTD.replacePlaceholder(CMOTD.getConfig().get("messages.maintenance.off")));
            }else{
                sender.sendMessage(CMOTD.replacePlaceholder(CMOTD.getConfig().get("messages.maintenance.permission")).replace("%permission%", perm));
            }
        }else{
            String perm = CMOTD.getConfig().getString("permission.maintenance.help");
            if(sender.hasPermission(perm)){
                sender.sendMessage(CMOTD.replacePlaceholder(CMOTD.getConfig().get("messages.maintenance.help")));
            }else{
                sender.sendMessage(CMOTD.replacePlaceholder(CMOTD.getConfig().get("messages.maintenance.permission")).replace("%permission%", perm));
            }
        }
    }
}
