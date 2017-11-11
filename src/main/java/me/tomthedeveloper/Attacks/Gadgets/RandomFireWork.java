package me.tomthedeveloper.Attacks.Gadgets;

import me.tomthedeveloper.Attacks.PlayerAttack;
import me.tomthedeveloper.Languages;
import me.tomthedeveloper.Utils.Util;
import me.tomthedeveloper.managers.UserManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Tom on 9/09/2014.
 */
public class RandomFireWork extends PlayerAttack implements Listener {

    public RandomFireWork( Player player) {
        super(10000, player);
        player.getInventory().addItem(plugin.getMenuItems().getItem("RandomFireWork"));
    }

    @Override
    public void run() {

    }

    @Override
    public void onUnRegister() {
        getAttacker().getInventory().remove(plugin.getMenuItems().getItem("RandomFireWork"));
        getAttacker().getInventory().remove(new ItemStack(Material.FIREWORK));
        getAttacker().updateInventory();
    }

    @EventHandler
    public void onFireWork(PlayerInteractEvent event){
        if(!event.hasItem())
            return;
        if(!event.getItem().hasItemMeta())
            return;
        if(!event.getItem().getItemMeta().hasDisplayName())
            return;
        if(!event.getItem().getItemMeta().getDisplayName().equalsIgnoreCase(plugin.getMenuItems().getItem("RandomFireWork").getItemMeta().getDisplayName()))
            return;
        if(event.getPlayer().getUniqueId() != getAttacker().getUniqueId())
            return;
        event.setCancelled(true);
        if(UserManager.getUser(getAttacker().getUniqueId()).getCooldown("RandomFireWork")!=0){
            getAttacker().sendMessage(Languages.getMessage("GadgetStillOnCooldown").replaceAll("%DELAY%", Integer.toString(UserManager.getUser(getAttacker().getUniqueId()).getCooldown("RandomFireWork"))));
            event.setCancelled(true);
            return;
        }
        UserManager.getUser(getAttacker().getUniqueId()).setCooldown("RandomFireWork",plugin.getMenuItems().getDelay("RandomFireWork"));
        Util.spawnRandomFirework(event.getPlayer().getLocation());
    }
}
