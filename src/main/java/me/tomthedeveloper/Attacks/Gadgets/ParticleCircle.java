package me.tomthedeveloper.Attacks.Gadgets;

import me.tomthedeveloper.Attacks.PlayerAttack;
import me.tomthedeveloper.Utils.ParticleEffect;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * Created by Tom on 9/09/2014.
 */
public class ParticleCircle extends PlayerAttack {

    private double x,y,z, radius, angle;
    private int i;
    private ParticleEffect effect;


    public ParticleCircle( Player player, ParticleEffect effect) {
        super(1, player);
        plugin.getAttackManager().registerAttack(this);
        this.effect = effect;

    }

    @Override
    public void run() {
        radius = 0.7F;
        angle = 2 * Math.PI * i / 30;
        x = Math.cos(angle) * radius;
        z = Math.sin(angle) * radius;
        y = 0.5;


        Location particlelocation = getAttacker().getEyeLocation();
        particlelocation.add(x, y, z);
        effect.display(0,0,0,0,1,particlelocation,100);
        i++;
    }

    @Override
    public void onUnRegister() {

    }
}
