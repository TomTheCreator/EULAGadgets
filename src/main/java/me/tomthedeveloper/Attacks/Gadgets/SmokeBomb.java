package me.tomthedeveloper.Attacks.Gadgets;

import me.tomthedeveloper.Attacks.PlayerAttack;
import me.tomthedeveloper.Languages;
import me.tomthedeveloper.Utils.ParticleEffect;
import me.tomthedeveloper.Utils.Util;
import me.tomthedeveloper.managers.UserManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * Created by Tom on 9/09/2014.
 */
public class SmokeBomb extends PlayerAttack implements Listener {

    private Item item = null;
   private int i = 0;

    public SmokeBomb( Player player) {
        super(1, player);
        plugin.getAttackManager().registerAttack(this);
        UserManager.getUser(player.getUniqueId()).setAttack(this);
        player.updateInventory();

        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

            @Override
            public void run() {

                    getAttacker().getInventory().addItem(Util.setItemNameAndLore(new ItemStack(Material.GLASS_BOTTLE), Languages.getMessage("SmokeBombName", "Smoke Bomb"), new String[]{}));
                    getAttacker().updateInventory();

            }
        },2L);
    }

    @Override
    public void run() {

        if(item == null)
            return;
        i++;
        if(i > 50 &&i <80){
            ParticleEffect.EXPLOSION_LARGE.display( 2, 2, 2, 1, 25,item.getLocation(),100);
        }
        if (i >80){
            UserManager.getUser(getAttacker().getUniqueId()).setCooldown("SmokeBomb",plugin.getMenuItems().getDelay("SmokeBomb"));
            this.onUnRegister();
            UserManager.getUser(getAttacker().getUniqueId()).unRegisterAttacks();
            plugin.getAttackManager().unregisterAttack(this);


        }

    }

    @Override
    public void onUnRegister() {
        if(item != null)
            item.remove();
        for(ItemStack itemStack:getAttacker().getInventory().getContents()){
            if(itemStack == null)
                continue;
            if(itemStack.getType() == Material.GLASS_BOTTLE)
                getAttacker().getInventory().remove(itemStack);
        }
        HandlerList.unregisterAll(this);

    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent event){
        if(!event.hasItem())
            return;
        if(!event.getItem().hasItemMeta())
            return;
        if(!event.getItem().getItemMeta().hasDisplayName())
            return;
        if(!event.getItem().getItemMeta().getDisplayName().equalsIgnoreCase(Languages.getMessage("SmokeBombName", "Smoke Bomb")))
            return;
        if(!(UserManager.getUser(getAttacker().getUniqueId()).getAttack() instanceof SmokeBomb))
            return;
        if(UserManager.getUser(getAttacker().getUniqueId()).getCooldown("SmokeBomb")!=0){
            getAttacker().sendMessage(Languages.getMessage("GadgetStillOnCooldown").replaceAll("%DELAY%", Integer.toString(UserManager.getUser(getAttacker().getUniqueId()).getCooldown("SmokeBomb"))));
            event.setCancelled(true);
            return;
        }

        getAttacker().getInventory().remove(Util.setItemNameAndLore(new ItemStack(Material.GLASS_BOTTLE), Languages.getMessage("SmokeBombName", "Smoke Bomb"), new String[]{}));
        for(ItemStack itemStack:getAttacker().getInventory().getContents()){
            if(itemStack == null)
                continue;
            if(itemStack.getType() == Material.GLASS_BOTTLE)
                getAttacker().getInventory().remove(itemStack);
        }
         item = (Item) getAttacker().getWorld().dropItem(getAttacker().getEyeLocation(), new ItemStack(Material.GLASS_BOTTLE));

        item.setVelocity(getAttacker().getLocation().getDirection().multiply(1));
        event.setCancelled(true);
        event.getHandlers().unregister(this);



    }

    @EventHandler
    public void onPickup(PlayerPickupItemEvent event){
        if(!event.getPlayer().getWorld().getName().equals(getAttacker().getWorld().getName()))
            return;
        if(event.getItem().getItemStack().getType() != Material.GLASS_BOTTLE)
            return;
        event.setCancelled(true);
    }
}
