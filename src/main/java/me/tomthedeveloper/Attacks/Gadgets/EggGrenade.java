package me.tomthedeveloper.Attacks.Gadgets;

import me.tomthedeveloper.Attacks.PlayerAttack;
import me.tomthedeveloper.Languages;
import me.tomthedeveloper.Utils.ParticleEffect;
import me.tomthedeveloper.Utils.RollBack;
import me.tomthedeveloper.Utils.Util;
import me.tomthedeveloper.managers.UserManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tom on 15/09/2014.
 */
public class EggGrenade extends PlayerAttack implements Listener{

    private Item item = null;
    private int i = 0;

    public EggGrenade( Player player) {
        super(1, player);
        plugin.getAttackManager().registerAttack(this);

        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

            @Override
            public void run() {
                getAttacker().getInventory().addItem(Util.setItemNameAndLore(new ItemStack(Material.EGG), Languages.getMessage("EggGrenadeName", "Egg Grenade"), new String[]{}));
                getAttacker().updateInventory();


            }
        },2L);
    }

    @Override
    public void run() {
        if(item == null)
            return;
        i++;
        if(i==80){
            ParticleEffect.EXPLOSION_LARGE.display(2,2,2,1,25,item.getLocation(),100);
            ParticleEffect.LAVA.display(2,2,2,1,25,item.getLocation(),100);
            for(Location location:getSphere(item.getLocation(), 5, 5, false, true, 0)){
                if(location.getBlock().getType() != Material.AIR)
                RollBack.paintBlock(location);
            }
        }
        if (i >80){
            UserManager.getUser(getAttacker().getUniqueId()).setCooldown("EggGrenade",plugin.getMenuItems().getDelay("EggGrenade"));
            this.onUnRegister();
            plugin.getAttackManager().unregisterAttack(this);
            UserManager.getUser(getAttacker().getUniqueId()).unRegisterAttacks();
        }

    }

    @Override
    public void onUnRegister() {
        if(item != null)
            item.remove();
        for(Entity entity: getAttacker().getNearbyEntities(100, 100,100)){
            if(entity.getType() == EntityType.DROPPED_ITEM){
                Item item1 = (Item) entity;
                if(item1.getItemStack().getType() == Material.EGG)
                    item1.remove();
            }
        }
        for(ItemStack itemStack:getAttacker().getInventory().getContents()){
            if(itemStack == null)
                continue;
            if(itemStack.getType() == Material.EGG)
                getAttacker().getInventory().remove(itemStack);
        }

    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent event){

        if(!event.hasItem())
            return;
        if(!event.getItem().hasItemMeta())
            return;
        if(!event.getItem().getItemMeta().hasDisplayName())
            return;
        if(!event.getItem().getItemMeta().getDisplayName().equalsIgnoreCase(Languages.getMessage("EggGrenadeName", "Egg grenade")))
            return;
        if(!(UserManager.getUser(getAttacker().getUniqueId()).getAttack() instanceof EggGrenade))
            return;
        getAttacker().getInventory().remove(Util.setItemNameAndLore(new ItemStack(Material.EGG), Languages.getMessage("EggGrenadeName", "Egg grenade"), new String[]{}));
        for(ItemStack itemStack:getAttacker().getInventory().getContents()){
            if(itemStack == null)
                continue;
            if(itemStack.getType() == Material.EGG)
                getAttacker().getInventory().remove(itemStack);
        }
        if(UserManager.getUser(getAttacker().getUniqueId()).getCooldown("EggGrenade")!=0){
            event.setCancelled(true);
            getAttacker().sendMessage(Languages.getMessage("GadgetStillOnCooldown").replaceAll("%DELAY%", Integer.toString(UserManager.getUser(getAttacker().getUniqueId()).getCooldown("EggGrenade"))));
            return;
        }

        item = (Item) getAttacker().getWorld().dropItem(getAttacker().getEyeLocation(), new ItemStack(Material.EGG));

        item.setVelocity(getAttacker().getLocation().getDirection().multiply(1));
        event.setCancelled(true);
        event.getHandlers().unregister(this);



    }

    public List<Location> getSphere(Location loc, int r, int h, boolean hollow, boolean sphere, int plus_y) {
        List<Location> circleblocks = new ArrayList<Location>();
        int cx = loc.getBlockX();
        int cy = loc.getBlockY();
        int cz = loc.getBlockZ();
        for (int x = cx - r; x <= cx + r; x++) {
            for (int z = cz - r; z <= cz + r; z++) {
                for (int y = (sphere ? cy - r : cy); y < (sphere ? cy + r : cy + h); y++) {
                    double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? (cy - y) * (cy - y) : 0);
                    if (dist < r * r && !(hollow && dist < (r - 1) * (r - 1))) {
                        Location l = new Location(loc.getWorld(), x, y + plus_y, z);
                        circleblocks.add(l);
                    }
                }
            }
        }
        return circleblocks;
    }

    @EventHandler
    public void onPickup(PlayerPickupItemEvent event){
        if(!event.getPlayer().getWorld().getName().equals(getAttacker().getWorld().getName()))
            return;
        if(event.getItem().getItemStack().getType() != Material.EGG)
            return;
        event.setCancelled(true);
    }
}
