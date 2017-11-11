package me.tomthedeveloper.Attacks.Gadgets;

import me.tomthedeveloper.Attacks.PlayerAttack;
import me.tomthedeveloper.Utils.ParticleEffect;
import me.tomthedeveloper.managers.UserManager;
import org.bukkit.entity.Player;

/**
 * Created by Tom on 9/09/2014.
 */
public class ParticleTrail extends PlayerAttack {

    private ParticleEffect effect;



    public ParticleTrail( Player player, ParticleEffect effect) {
        super(3, player);
        UserManager.getUser(player.getUniqueId()).setAttack(this);
        plugin.getAttackManager().registerAttack(this);
        this.effect = effect;
    }

    @Override
    public void run() {
      effect.display( 1,1,1,1,2,getAttacker().getLocation().add(0,1,0),100);
    }


    @Override
    public void onUnRegister() {

    }
}
