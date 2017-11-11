package me.tomthedeveloper.Attacks.Gadgets;

import me.tomthedeveloper.Attacks.PlayerAttack;
import me.tomthedeveloper.Languages;
import me.tomthedeveloper.Utils.RollBack;
import me.tomthedeveloper.Utils.Util;
import me.tomthedeveloper.managers.UserManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.BlockIterator;

import java.util.*;

/**
 * Created by Tom on 10/09/2014.
 */
public class PaintGun extends PlayerAttack implements Listener {

    private int h = 0;



    private Random random = new Random();


    public PaintGun( Player player) {
        super(1, player);
        plugin.getAttackManager().registerAttack(this);
        UserManager.getUser(player.getUniqueId()).setAttack(this);


       // unpaintable.add(Material.AIR);
    }

    @Override
    public void run() {
        if(h == 2){
            ItemStack gun = new ItemStack(Material.DIAMOND_HOE);
            Util.setItemNameAndLore(gun, Languages.getMessage("PaintGunName", "Paint Gun"), new String[]{});
            getAttacker().getInventory().addItem(gun);
            h = 3;
        }else{
            h++;
        }


    }

    @Override
    public void onUnRegister() {
        for(ItemStack itemStack:getAttacker().getInventory().getContents()){
            if(itemStack == null)
                continue;
            if(itemStack.getType() == Material.DIAMOND_HOE)
                getAttacker().getInventory().remove(itemStack);
        }
    }

    @EventHandler
    public void onPaint(ProjectileHitEvent event){
        if(event.getEntity().getType() != EntityType.SNOWBALL)
            return;
        if(!(UserManager.getUser(getAttacker().getUniqueId()).getAttack() instanceof PaintGun))
            return;

        BlockIterator bi = new BlockIterator(event.getEntity().getWorld(), event.getEntity().getLocation().toVector(), event.getEntity().getVelocity().normalize(), 0.0D, 4);
        while (bi.hasNext()){
            Block block = bi.next();
            if(block.getType() != Material.AIR){
                if(RollBack.getBlocks().containsKey(block.getLocation()))
                    break;
                RollBack.paintBlock(block.getLocation());
                break;
            }
        }

    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        if(!event.hasItem())
            return;
        if(event.getPlayer().getUniqueId() != getAttacker().getUniqueId())
            return;
        if(event.getItem().getType() != Material.DIAMOND_HOE)
            return;
        if(!(UserManager.getUser(getAttacker().getUniqueId()).getAttack() instanceof PaintGun))
            return;
        event.setCancelled(true);
        if(UserManager.getUser(getAttacker().getUniqueId()).getCooldown("PaintGun")!=0){
            getAttacker().sendMessage(Languages.getMessage("GadgetStillOnCooldown").replaceAll("%DELAY%", Integer.toString(UserManager.getUser(getAttacker().getUniqueId()).getCooldown("PaintGun"))));

            return;
        }
        UserManager.getUser(getAttacker().getUniqueId()).setCooldown("PaintGun",plugin.getMenuItems().getDelay("PaintGun"));
        Snowball snowball = (Snowball) event.getPlayer().getWorld().spawn(event.getPlayer().getEyeLocation(), Snowball.class);
        snowball.setVelocity(event.getPlayer().getLocation().getDirection().multiply(1.5));
        snowball.setShooter(getAttacker());



    }



}
