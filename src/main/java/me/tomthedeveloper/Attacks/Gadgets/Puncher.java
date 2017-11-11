package me.tomthedeveloper.Attacks.Gadgets;

import me.tomthedeveloper.Attacks.PlayerAttack;
import me.tomthedeveloper.Languages;
import me.tomthedeveloper.Utils.Util;
import me.tomthedeveloper.Utils.WeaponHelper;
import me.tomthedeveloper.managers.UserManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Tom on 10/09/2014.
 */
public class Puncher extends PlayerAttack implements Listener {


    public Puncher( Player player) {
        super(2, player);
        plugin.getAttackManager().registerAttack(this);
        UserManager.getUser(player.getUniqueId()).setAttack(this);
        ItemStack itemStack = new ItemStack(Material.DIAMOND_SPADE);
        itemStack = WeaponHelper.getEnchanted(itemStack, new Enchantment[]{Enchantment.DURABILITY, Enchantment.KNOCKBACK}, new int[]{10,5});
        Util.setItemNameAndLore(itemStack, Languages.getMessage("PuncherItem", ChatColor.GREEN + "Puncher"), new String[]{});
        player.getInventory().addItem(itemStack);
    }

    @Override
    public void run() {

    }

    @Override
    public void onUnRegister() {
        for(ItemStack itemStack:getAttacker().getInventory().getContents()){
            if(itemStack == null)
                continue;
            if(itemStack.getType() == Material.DIAMOND_SPADE)
                getAttacker().getInventory().remove(itemStack);
        }
    }


    @EventHandler
    public void onHit(PlayerInteractEntityEvent event){
        if(!(UserManager.getUser(getAttacker().getUniqueId()).getAttack() instanceof Puncher))
            return;
        if(event.getPlayer().getItemInHand() == null)
            return;
        if(event.getPlayer().getItemInHand().getType() != Material.DIAMOND_SPADE)
            return;
        if(UserManager.getUser(getAttacker().getUniqueId()).getCooldown("Puncher")!=0){
            getAttacker().sendMessage(Languages.getMessage("GadgetStillOnCooldown").replaceAll("%DELAY%", Integer.toString(UserManager.getUser(getAttacker().getUniqueId()).getCooldown("Puncher"))));
            event.setCancelled(true);
            return;
        }
        UserManager.getUser(getAttacker().getUniqueId()).setCooldown("Puncher",plugin.getMenuItems().getDelay("Puncher"));

        event.getRightClicked().setVelocity(event.getPlayer().getLocation().getDirection().multiply(3F));
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event){
        if(!(event.getDamager() instanceof Player))
            return;
        Player player = (Player) event.getDamager();
        if(!(UserManager.getUser(getAttacker().getUniqueId()).getAttack() instanceof Puncher))
            return;
        if(player.getItemInHand() == null)
            return;
        if(player.getItemInHand().getType() != Material.DIAMOND_SPADE)
            return;
        event.setCancelled(true);
        event.getEntity().setVelocity(player.getLocation().getDirection().multiply(3F));
    }
}
