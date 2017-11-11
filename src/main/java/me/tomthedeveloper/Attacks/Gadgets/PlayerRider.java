package me.tomthedeveloper.Attacks.Gadgets;

import me.tomthedeveloper.Attacks.PlayerAttack;
import me.tomthedeveloper.Languages;
import me.tomthedeveloper.Utils.Util;
import me.tomthedeveloper.managers.UserManager;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;


/**
 * Created by Tom on 9/09/2014.
 */
public class PlayerRider extends PlayerAttack implements Listener {


    public PlayerRider( Player player) {
        super(20, player);
        plugin.getAttackManager().registerAttack(this);
        UserManager.getUser(player.getUniqueId()).setAttack(this);
        i = 0;
    }

    @EventHandler
    public void onRide(PlayerInteractEntityEvent event){
        if(event.getPlayer().getItemInHand() == null)
            return;
        if(!event.getPlayer().getItemInHand().hasItemMeta())
            return;
        if(event.getPlayer().getUniqueId() != getAttacker().getUniqueId())
             return;
        if(!(UserManager.getUser(getAttacker().getUniqueId()).getAttack() instanceof PlayerRider))
            return;
        if(UserManager.getUser(getAttacker().getUniqueId()).getCooldown("PlayerRiderGadget")!=0){
            getAttacker().sendMessage(Languages.getMessage("GadgetStillOnCooldown").replaceAll("%DELAY%", Integer.toString(UserManager.getUser(getAttacker().getUniqueId()).getCooldown("PlayerRiderGadget"))));
            event.setCancelled(true);
            return;
        }
        UserManager.getUser(getAttacker().getUniqueId()).setCooldown("PlayerRiderGadget",plugin.getMenuItems().getDelay("PlayerRiderGadget"));
        if(event.getPlayer().getItemInHand().getType() == Material.SADDLE){
            if(event.getPlayer().getPassenger() == null) {
                setPassenger(event.getRightClicked(), event.getPlayer());
                return;
            }
            if(event.getPlayer().getPassenger() != null){
                return;
            }
            if(event.getPlayer().getPassenger().equals(event.getRightClicked())|| event.getRightClicked().getPassenger().equals(event.getPlayer())){
                return;
            }
        }else if(event.getPlayer().getItemInHand().getType() == Material.MINECART){
            if(event.getRightClicked().getPassenger() != null)
                return;
            if( event.getRightClicked().getPassenger() == null && !event.getPlayer().getPassenger().equals(event.getRightClicked())) {
                setPassenger(event.getPlayer(), event.getRightClicked());
                return;
            }


        }
    }

    @EventHandler
    public void onThrow(PlayerInteractEvent event){
        if(event.getAction() != Action.RIGHT_CLICK_AIR || event.getAction() != Action.RIGHT_CLICK_BLOCK)
            return;
        if(event.getPlayer().getPassenger() == null)
            return;
        event.getPlayer().getPassenger().leaveVehicle();
        event.getPlayer().getPassenger().setVelocity(event.getPlayer().getLocation().getDirection().multiply(3));
    }

    @EventHandler
    public void onThrow(PlayerInteractEntityEvent event){
        if(event.getRightClicked().getType() != EntityType.PLAYER)
            return;
        if(event.getPlayer().getPassenger() == null)
            return;
        event.getPlayer().getPassenger().setVelocity(event.getPlayer().getLocation().getDirection().multiply(3));
    }


    public void setPassenger(Entity base, Entity passegner){
        if(base.getPassenger() == null)
            base.setPassenger(passegner);
        else{
            setPassenger(base.getPassenger(), passegner);
        }
    }

    private int i;

    @Override
    public void run() {
        if(i == 1){
            getAttacker().getInventory().addItem(Util.setItemNameAndLore(new ItemStack(Material.SADDLE), Languages.getMessage("SaddleItemForPlayerRiding", "Ride players"), new String[]{}));
            getAttacker().getInventory().addItem(Util.setItemNameAndLore(new ItemStack(Material.MINECART), Languages.getMessage("RailItemForPlayerRiding", "Put players on your head"), new String[]{}));
            getAttacker().updateInventory();
            System.out.print("Give saddle and such");
            i = 3;
        }else{

        }
    i++;
    }

    @Override
    public void onUnRegister() {
        for(ItemStack itemStack:getAttacker().getInventory().getArmorContents()){
            if(itemStack == null)
                continue;
                if(itemStack.getType() == Material.SADDLE)
                    getAttacker().getInventory().remove(itemStack);
                if(itemStack.getType() == Material.MINECART)
                    getAttacker().getInventory().remove(itemStack);
        }
        getAttacker().updateInventory();
    }
}
