package me.tomthedeveloper.Attacks.Gadgets;

import me.tomthedeveloper.managers.UserManager;
import org.bukkit.Material;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * Created by Tom on 9/09/2014.
 */
public class Parachute implements Listener {



   /* @EventHandler
    public void onParachut(PlayerInteractEvent event){
        if(!event.hasItem())
            return;
        if(!event.getItem().hasItemMeta())
            return;
        if(event.getItem().getType()  != Material.SLIME_BALL)
            return;
        if(UserManager.getUser(event.getPlayer().getUniqueId()).getInt("parachute") == 0) {
            UserManager.getUser(event.getPlayer().getUniqueId()).setInt("parachute", 1);
            Chicken chicken = (Chicken) event.getPlayer().getWorld().spawnEntity(event.getPlayer().getLocation().add(0, 2, 0), EntityType.CHICKEN);
            chicken.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1));
            chicken.setPassenger(event.getPlayer());
            for(int i = 0; i<7; i++) {
                Chicken chick = (Chicken) event.getPlayer().getWorld().spawnEntity(event.getPlayer().getLocation().add(0, 2, 0), EntityType.CHICKEN);

                chicken.setLeashHolder(event.getPlayer());
            }
            return;
        }
        for(Entity entity:event.getPlayer().getWorld().getEntities()){
            UserManager.getUser(event.getPlayer().getUniqueId()).setInt("parachute", 0);
            if(entity.getType() != EntityType.CHICKEN)
                return;
            Chicken chicken = (Chicken) entity;
            if(chicken.getLeashHolder().getUniqueId() == event.getPlayer().getUniqueId())
                chicken.remove();
        }

        */

    //}
}
