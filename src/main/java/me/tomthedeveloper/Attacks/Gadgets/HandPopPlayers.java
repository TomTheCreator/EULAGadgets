package me.tomthedeveloper.Attacks.Gadgets;

import me.tomthedeveloper.Attacks.PlayerAttack;
import me.tomthedeveloper.Languages;
import me.tomthedeveloper.Utils.ParticleEffect;
import me.tomthedeveloper.managers.UserManager;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

/**
 * Created by Tom on 9/09/2014.
 */
public class HandPopPlayers extends PlayerAttack implements Listener {


    public HandPopPlayers( Player player) {
        super(20, player);
        plugin.getAttackManager().registerAttack(this);
        UserManager.getUser(player.getUniqueId()).setAttack(this);
    }

    @EventHandler
    public void EntityInteractEvent(PlayerInteractEntityEvent event){
        if(!(event.getRightClicked() instanceof Player))
            return;
        if(event.getPlayer().getUniqueId() != getAttacker().getUniqueId())
            return;
        if(!(UserManager.getUser(getAttacker().getUniqueId()).getAttack() instanceof HandPopPlayers))
            return;
        if(UserManager.getUser(getAttacker().getUniqueId()).getCooldown("HandPopPlayers")!=0){
            getAttacker().sendMessage(Languages.getMessage("GadgetStillOnCooldown").replaceAll("%DELAY%", Integer.toString(UserManager.getUser(getAttacker().getUniqueId()).getCooldown("HandPopPlayers"))));
            event.setCancelled(true);
            return;
        }
        UserManager.getUser(getAttacker().getUniqueId()).setCooldown("HandPopPlayers",plugin.getMenuItems().getDelay("PlayerHandPopGadget"));
        event.getPlayer().hidePlayer((Player) event.getRightClicked());
        ParticleEffect.HEART.display(1,2,1,1,30,event.getRightClicked().getLocation(), event.getPlayer());
        ParticleEffect.LAVA.display(1,2,1,1,30,event.getRightClicked().getLocation(), event.getPlayer());
        ParticleEffect.SMOKE_LARGE.display(1,2,1,1,30,event.getRightClicked().getLocation(), event.getPlayer());
        event.getPlayer().playSound(event.getRightClicked().getLocation(), Sound.CHICKEN_EGG_POP, 10, 10);

    }

    @Override
    public void run() {

    }

    @Override
    public void onUnRegister() {

    }
}
