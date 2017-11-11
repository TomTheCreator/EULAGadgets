package me.tomthedeveloper.Attacks.Gadgets;

import me.tomthedeveloper.Attacks.PlayerAttack;
import me.tomthedeveloper.managers.UserManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Tom on 15/09/2014.
 */
public class Hats extends PlayerAttack implements Listener{

    private ItemStack itemStack;


    public Hats(Player player, ItemStack itemStack) {
        super(1, player);
        this.itemStack = itemStack;
        plugin.getAttackManager().registerAttack(this);
        UserManager.getUser(player.getUniqueId()).setAttack(this);
    }

    @Override
    public void run() {
        getAttacker().getInventory().setHelmet(itemStack);
    }

    @Override
    public void onUnRegister() {
        getAttacker().getInventory().setHelmet(new ItemStack(Material.AIR));
    }


    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        if(event.getRawSlot() != 5)
            return;
        Player player = (Player) event.getWhoClicked();
        if(UserManager.getUser(player.getUniqueId()).getAttack() instanceof Hats) {

            event.setCancelled(true);
        }

    }
}
