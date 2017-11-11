package me.tomthedeveloper.commands;

import me.tomthedeveloper.EULAGadgets;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Tom on 19/09/2014.
 */
public class FireWorkMenuCommand implements CommandExecutor {


    public EULAGadgets plugin;

    public FireWorkMenuCommand(EULAGadgets plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!(commandSender instanceof Player))
            return true;
        Player player = (Player) commandSender;
        if(command.getLabel().equalsIgnoreCase("fireworks") && player.hasPermission("EULAGadgets.Fireworks"))
            plugin.getFireWorkMenu().openMenu((Player) commandSender);
        return true;
    }
}
