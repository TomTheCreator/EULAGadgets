package me.tomthedeveloper.Attacks.Gadgets;

import me.tomthedeveloper.Attacks.Attack;
import me.tomthedeveloper.Attacks.PlayerAttack;
import me.tomthedeveloper.Languages;
import me.tomthedeveloper.Utils.ParticleEffect;
import me.tomthedeveloper.Utils.WeaponHelper;
import me.tomthedeveloper.managers.UserManager;
import net.minecraft.server.v1_8_R3.Explosion;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Tom on 10/09/2014.
 */
public class TNTBow extends PlayerAttack implements Listener {

    private int i = 0;


    public TNTBow( Player player) {
        super(1, player);
        plugin.getAttackManager().registerAttack(this);
        UserManager.getUser(player.getUniqueId()).setAttack(this);
    }

    @Override
    public void run() {
        if(i == 2){

            getAttacker().getInventory().addItem(WeaponHelper.getEnchantedBow(Enchantment.ARROW_INFINITE, 1));
            getAttacker().getInventory().addItem(new ItemStack(Material.ARROW));
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

    @EventHandler
    public void onLand(ProjectileHitEvent event){
        if(!(event.getEntity() instanceof Arrow))
            return;


        if(!(event.getEntity().getShooter() instanceof Player))
            return;
        if(!(UserManager.getUser(getAttacker().getUniqueId()).getAttack() instanceof TNTBow))
            return;
        if(UserManager.getUser(getAttacker().getUniqueId()).getCooldown("TNTBow")!=0){
            getAttacker().sendMessage(Languages.getMessage("GadgetStillOnCooldown").replaceAll("%DELAY%", Integer.toString(UserManager.getUser(getAttacker().getUniqueId()).getCooldown("TNTBow"))));

            return;
        }
        UserManager.getUser(getAttacker().getUniqueId()).setCooldown("TNTBow",plugin.getMenuItems().getDelay("TNTBow"));
        event.getEntity().getWorld().createExplosion(event.getEntity().getLocation(), 3F);
        ParticleEffect.LAVA.display(2,2,2,1,25,event.getEntity().getLocation(),100);

        // if(event.getEntity().getLocation().getBlock().getType() == Material.AIR)
        //   return;


    }

    @EventHandler
    public void onExplode(BlockExplodeEvent event){
       event.blockList().clear();
    }
}
