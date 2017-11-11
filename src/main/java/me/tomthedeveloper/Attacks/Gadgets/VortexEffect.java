package me.tomthedeveloper.Attacks.Gadgets;

import me.tomthedeveloper.Attacks.PlayerAttack;
import me.tomthedeveloper.Utils.ParticleEffect;
import me.tomthedeveloper.managers.UserManager;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * Created by Tom on 11/07/2015.
 */
public class VortexEffect extends PlayerAttack {

    private int counter = 0;
    private Location start = null;
    private double x;
    private double y;
    private double z;

    private ParticleEffect effect;



    public VortexEffect( Player player, ParticleEffect effect) {

        super(1, player);
        start = getAttacker().getLocation();
        x = start.getX();
        y = start.getY();
        z = start.getZ();
        this.effect = effect;
        plugin.getAttackManager().registerAttack(this);
        UserManager.getUser(player.getUniqueId()).setAttack(this);
        player.updateInventory();

    }


    @Override
    public void run() {
        start = getAttacker().getLocation();
        x = start.getX();
        y = start.getY();
        z = start.getZ();
        effect.display(0,0,0,0,1,calculateLocation(0),100);
        effect.display(0,0,0,0,1,calculateLocation(Math.PI),100);
        if(counter==50)
            counter = 0;
        counter++;
    }

    @Override
    public void onUnRegister() {
    }

    public Location calculateLocation(double angle){
        double cx = x+(counter*0.03)*Math.sin( 2 * Math.PI * (counter/3) / 30 + angle);
        double cy = y+3 - counter * 0.05;
        double cz = z+(counter*0.03)*Math.cos( (2 * Math.PI * (counter)/3 / 30) + angle);
        return new Location(getAttacker().getWorld(),cx,cy,cz);
    }
}
