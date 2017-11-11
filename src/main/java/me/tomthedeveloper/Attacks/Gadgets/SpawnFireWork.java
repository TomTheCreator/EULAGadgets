package me.tomthedeveloper.Attacks.Gadgets;

import me.tomthedeveloper.Attacks.PlayerAttack;
import me.tomthedeveloper.Languages;
import me.tomthedeveloper.Utils.Util;
import me.tomthedeveloper.managers.UserManager;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;

/**
 * Created by Tom on 10/09/2014.
 */
public class SpawnFireWork extends PlayerAttack implements Listener{

    private FireworkEffect.Type type;
    private Color color, fade;
    private String itemname;
    private int i = 0;



    public SpawnFireWork( Player player, Color color, Color fade, FireworkEffect.Type type, String itemname) {
        super(1, player);
        plugin.getAttackManager().registerAttack(this);
        UserManager.getUser(player.getUniqueId()).setAttack(this);
        this.type = type;
        this.color = color;
        this.fade = fade;
        this.itemname = itemname;

    }

    @Override
    public void run() {
        if(i==2){
            getAttacker().getInventory().addItem(plugin.getMenuItems().getItem(itemname));
            getAttacker().updateInventory();
        }
        i++;
    }

    @Override
    public void onUnRegister() {
        for(ItemStack itemStack:getAttacker().getInventory().getContents()){
            if(itemStack == null)
                continue;

            if(itemStack.getType() == plugin.getMenuItems().getItem(itemname).getType())
                getAttacker().getInventory().remove(itemStack);
        }
        getAttacker().updateInventory();
    }


    private void spawnFireWork(Location location, Color color, Color fade, FireworkEffect.Type type){
        Firework firework = (Firework) location.getWorld().spawnEntity(location, EntityType.FIREWORK);
        FireworkMeta fireworkMeta = firework.getFireworkMeta();
        fireworkMeta.addEffect(FireworkEffect.builder().flicker(true).withColor(color).withFade(fade).with(type).withTrail().build());
        firework.setFireworkMeta(fireworkMeta);
    }

    @EventHandler
    public void onFireWork(PlayerInteractEvent event) {
        if (!event.hasItem())
            return;
        if (!event.getItem().hasItemMeta())
            return;
        if (!event.getItem().getItemMeta().hasDisplayName())
            return;
        if (!event.getItem().getItemMeta().getDisplayName().equalsIgnoreCase(plugin.getMenuItems().getItem(itemname).getItemMeta().getDisplayName()))
            return;
        if (event.getPlayer().getUniqueId() != getAttacker().getUniqueId())
            return;
        event.setCancelled(true);
        if(UserManager.getUser(getAttacker().getUniqueId()).getCooldown(itemname)!=0){
            getAttacker().sendMessage(Languages.getMessage("GadgetStillOnCooldown").replaceAll("%DELAY%", Integer.toString(UserManager.getUser(getAttacker().getUniqueId()).getCooldown(itemname))));
            event.setCancelled(true);
            return;
        }
        UserManager.getUser(getAttacker().getUniqueId()).setCooldown(itemname,plugin.getMenuItems().getDelay(itemname));
        spawnFireWork(event.getPlayer().getLocation(), color, fade, type);
    }
}
