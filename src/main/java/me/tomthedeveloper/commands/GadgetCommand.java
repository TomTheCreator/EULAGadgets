package me.tomthedeveloper.commands;

import me.tomthedeveloper.EULAGadgets;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Tom on 10/09/2014.
 */
public class GadgetCommand implements CommandExecutor {

    private EULAGadgets plugin;

    public GadgetCommand(EULAGadgets plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (command.getLabel().equalsIgnoreCase("gadgetmenu") && player.hasPermission("EULAGadgets.ALL"))
                ((Player) commandSender).openInventory(plugin.getStartMenu().getInventory());
            return true;
        }
        return true;
    }
}
