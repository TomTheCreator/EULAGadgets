package me.tomthedeveloper.Attacks.Gadgets;

import me.tomthedeveloper.Attacks.PlayerAttack;
import me.tomthedeveloper.Languages;
import me.tomthedeveloper.Utils.Util;
import me.tomthedeveloper.managers.UserManager;
import org.bukkit.Material;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Tom on 9/09/2014.
 */
public class FireWorkRide extends PlayerAttack implements Listener {


    public FireWorkRide( Player player) {
        super(2000, player);
        player.getInventory().addItem(plugin.getMenuItems().getItem("FireWorkRider"));
        player.updateInventory();
    }

    @EventHandler
     public void onRIde(PlayerInteractEvent event){
        if(!event.hasItem())
            return;
        if(event.getItem().getType() != Material.FIREWORK)
            return;
        if(event.getPlayer().getUniqueId() != getAttacker().getUniqueId())
            return;
        if(!event.getItem().hasItemMeta())
            return;
        if(!(UserManager.getUser(getAttacker().getUniqueId()).getAttack() instanceof FireWorkRide))
            return;
        event.setCancelled(true);
        if(UserManager.getUser(getAttacker().getUniqueId()).getCooldown("FireworkRider")!=0){
            getAttacker().sendMessage(Languages.getMessage("GadgetStillOnCooldown").replaceAll("%DELAY%", Integer.toString(UserManager.getUser(getAttacker().getUniqueId()).getCooldown("FireworkRider"))));
            return;
        }
        UserManager.getUser(getAttacker().getUniqueId()).setCooldown("FireworkRider",plugin.getMenuItems().getDelay("FireWorkRider"));
        Firework firework = Util.spawnRandomFirework(event.getPlayer().getLocation());
        firework.setPassenger(event.getPlayer());

    }

    @EventHandler
    public void onFall(EntityDamageByBlockEvent event){
        if(!(UserManager.getUser(getAttacker().getUniqueId()).getAttack() instanceof FireWorkRide))
            return;
        event.setCancelled(true);
    }

    @Override
    public void run() {

    }

    @Override
    public void onUnRegister() {
        getAttacker().getInventory().remove(plugin.getMenuItems().getItem("FireWorkRider"));
        getAttacker().updateInventory();

        for(ItemStack itemStack:getAttacker().getInventory().getContents()){
            if(itemStack == null)
                continue;

            if(itemStack.getType() == Material.FIREWORK)
                getAttacker().getInventory().remove(itemStack);
        }
        getAttacker().updateInventory();
    }
}
