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
                sender.sendMessage(CMOTD.replacePlaceholder(CMOTD.getConfig().get("messages.cmotd.reload.start")));
                CmotdAPI.reload();
                sender.sendMessage(CMOTD.replacePlaceholder(CMOTD.getConfig().get("messages.cmotd.reload.end")));
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
