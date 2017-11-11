package me.tomthedeveloper.Attacks.Gadgets;

import me.tomthedeveloper.Attacks.PlayerAttack;
import me.tomthedeveloper.Languages;
import me.tomthedeveloper.Utils.ArmorHelper;
import me.tomthedeveloper.Utils.ParticleEffect;
import me.tomthedeveloper.Utils.WeaponHelper;
import me.tomthedeveloper.managers.UserManager;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Tom on 9/09/2014.
 */
public class BowPopPlayers extends PlayerAttack implements Listener {


    public BowPopPlayers( Player player) {
        super(20, player);
        plugin.getAttackManager().registerAttack(this);
        UserManager.getUser(player.getUniqueId()).setAttack(this);
        player.getInventory().addItem(WeaponHelper.getEnchantedBow(Enchantment.ARROW_INFINITE, 1));
        player.getInventory().addItem(new ItemStack(Material.ARROW));
        player.updateInventory();
    }

    @EventHandler
    public void onShoot(EntityDamageByEntityEvent event){
        if(!(event.getDamager() instanceof Arrow))
            return;
        if(!(event.getEntity() instanceof Player))
            return;
        Arrow arrow = (Arrow) event.getDamager();
        if(!(arrow.getShooter() instanceof Player))
            return;
        if(!(UserManager.getUser(getAttacker().getUniqueId()).getAttack() instanceof BowPopPlayers))
            return;
        Player shooter = (Player) arrow.getShooter();
        if(shooter.getUniqueId() != getAttacker().getUniqueId())
            return;
        if(UserManager.getUser(getAttacker().getUniqueId()).getCooldown("BowPopper")!=0){
            getAttacker().sendMessage(Languages.getMessage("GadgetStillOnCooldown").replaceAll("%DELAY%", Integer.toString(UserManager.getUser(getAttacker().getUniqueId()).getCooldown("ArrowRider"))));
            return;
        }
        UserManager.getUser(getAttacker().getUniqueId()).setCooldown("BowPopper",plugin.getMenuItems().getDelay("PlayerBowPopGadget"));


        Player player = (Player) event.getEntity();
        shooter.hidePlayer((Player) event.getEntity());
        ParticleEffect.HEART.display(1,2,1,1,30,player.getLocation(), shooter);
        ParticleEffect.LAVA.display(1,2,1,1,30,player.getLocation(), shooter);
        ParticleEffect.SMOKE_LARGE.display(1,2,1,1,30,player.getLocation(), shooter);
        shooter.playSound(player.getLocation(), Sound.CHICKEN_EGG_POP, 10, 10);
        event.setCancelled(true);
    }

    @Override
    public void run() {

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
