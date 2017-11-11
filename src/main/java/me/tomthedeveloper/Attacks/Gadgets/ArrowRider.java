package me.tomthedeveloper.Attacks.Gadgets;

import me.tomthedeveloper.Attacks.Attack;
import me.tomthedeveloper.Attacks.PlayerAttack;
import me.tomthedeveloper.Languages;
import me.tomthedeveloper.Utils.WeaponHelper;
import me.tomthedeveloper.managers.UserManager;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.inventory.ItemStack;

import javax.swing.text.html.parser.Entity;

/**
 * Created by Tom on 9/09/2014.
 */
public class ArrowRider extends PlayerAttack implements Listener {




    public ArrowRider( Player player) {
        super(20, player);
        plugin.getAttackManager().registerAttack(this);
        UserManager.getUser(player.getUniqueId()).setAttack(this);

    }

    @EventHandler
    public void onShoot(ProjectileLaunchEvent event){
        if(!(event.getEntity() instanceof Arrow))
            return;
        if(!(event.getEntity().getShooter() instanceof Player))
            return;
        if(!(UserManager.getUser(getAttacker().getUniqueId()).getAttack() instanceof ArrowRider))
            return;
        Player player = (Player) event.getEntity().getShooter();
        if(!player.getItemInHand().hasItemMeta())
            return;
        if(UserManager.getUser(getAttacker().getUniqueId()).getCooldown("ArrowRider")!=0){
            getAttacker().sendMessage(Languages.getMessage("GadgetStillOnCooldown").replaceAll("%DELAY%", Integer.toString(UserManager.getUser(getAttacker().getUniqueId()).getCooldown("ArrowRider"))));
            event.setCancelled(true);
            return;
        }
        event.getEntity().setPassenger(player);
        player.setGameMode(GameMode.CREATIVE);
        UserManager.getUser(getAttacker().getUniqueId()).setCooldown("ArrowRider",plugin.getMenuItems().getDelay("BowRiderGadget"));
    }

   @EventHandler
    public void onLand(ProjectileHitEvent event){
        if(!(event.getEntity() instanceof Arrow))
            return;

        if(!(event.getEntity().getShooter() instanceof Player))
            return;
       if(!(UserManager.getUser(getAttacker().getUniqueId()).getAttack() instanceof ArrowRider))
           return;
       ((Player)event.getEntity().getShooter()).setGameMode(GameMode.SURVIVAL);
       if(event.getEntity().getPassenger() == null)
           return;
       // if(event.getEntity().getLocation().getBlock().getType() == Material.AIR)
         //   return;

        teleportToGoodLocation((Player) event.getEntity().getPassenger(), event.getEntity().getLocation());
       ((Player)event.getEntity().getShooter()).setGameMode(GameMode.SURVIVAL);
    }

    @EventHandler
    public void onClick(InventoryClickEvent event){
        if(!(UserManager.getUser(getAttacker().getUniqueId()).getAttack() instanceof ArrowRider))
            return;
        event.setCancelled(true);
    }

    @EventHandler
    public void toggleFlyEvent(PlayerToggleFlightEvent event){
        if(!(UserManager.getUser(getAttacker().getUniqueId()).getAttack() instanceof ArrowRider))
            return;
        event.setCancelled(true);
    }

    @EventHandler
    public void onDismount(VehicleExitEvent event){

        if(event.getExited() instanceof Player){
            ((Player) event.getExited()).setGameMode(GameMode.SURVIVAL);
        }
    }


    public void teleportToGoodLocation(Player entity, Location location){
        if((location.getBlock().isLiquid() || location.getBlock().getType() == Material.AIR)){
            entity.teleport(location.add(0,1,0));
        }else{
            teleportToGoodLocation(entity, location.add(0, 1, 0));
        }


    }


    private  int i = 0;
    @Override
    public void run() {
        if(i == 1){
            getAttacker().getInventory().addItem(WeaponHelper.getEnchantedBow(Enchantment.ARROW_INFINITE, 1));
            getAttacker().getInventory().addItem(new ItemStack(Material.ARROW));
            getAttacker().updateInventory();
        }
        i++;
    }

    @Override
    public void onUnRegister() {
        getAttacker().getInventory().remove(WeaponHelper.getEnchantedBow(Enchantment.ARROW_INFINITE, 1));
        for(ItemStack itemStack:getAttacker().getInventory().getContents()){
            if(itemStack == null)
                continue;
            if(itemStack.getType() == Material.BOW)
                getAttacker().getInventory().remove(itemStack);
            if(itemStack.getType() == Material.ARROW)
                getAttacker().getInventory().remove(itemStack);
        }
    }
}
