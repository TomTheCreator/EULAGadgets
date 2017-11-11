package me.tomthedeveloper.Attacks;


import me.tomthedeveloper.EULAGadgets;
import me.tomthedeveloper.User;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by Tom on 30/07/2014.
 */
public class AttackListener extends BukkitRunnable {

    public static EULAGadgets plugin;


    public AttackListener(){

    }

    public void start(){
        this.runTaskTimer(plugin, 20L, 1L);
    }

    public void stop(){
        Bukkit.getServer().getScheduler().cancelTask(this.getTaskId());

    }





    @Override
    public void run() {
        User.handleCooldowns();
       for(Attack attack: plugin.getAttackManager().getAttacks()){
           if(attack == null)
               continue;
           attack.setCounter(attack.getCounter()+1);
           if(attack.getCounter() == attack.getTicks()){
               attack.run();
               attack.setCounter(0);
           }

       }


        plugin.getAttackManager().unregisterAttacksForReal();

    }

}
