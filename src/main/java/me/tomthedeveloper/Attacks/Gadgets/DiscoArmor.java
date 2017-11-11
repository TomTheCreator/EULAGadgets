package me.tomthedeveloper.Attacks.Gadgets;

import me.tomthedeveloper.Attacks.Attack;
import me.tomthedeveloper.Attacks.PlayerAttack;
import me.tomthedeveloper.Utils.ArmorHelper;
import me.tomthedeveloper.managers.UserManager;
import org.bukkit.Color;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

/**
 * Created by Tom on 9/09/2014.
 */
public class DiscoArmor extends PlayerAttack {

    private int i = 0;
    private int R =50, G =150, B = 4;
    private boolean r = false, g = false, b = false;


    public DiscoArmor( Player player) {
        super(1, player);
        plugin.getAttackManager().registerAttack(this);
        UserManager.getUser(player.getUniqueId()).setAttack(this);
    }

    @Override
    public void run() {
        if(i ==1 || i == 2 || i ==3){
            ArmorHelper.setColouredArmor(Color.RED, getAttacker());
        }else{
           for(ItemStack itemStack:getAttacker().getInventory().getArmorContents()){
               if(itemStack == null)
                   return;
               if(itemStack.getItemMeta() instanceof LeatherArmorMeta) {
                   LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) itemStack.getItemMeta();

                   leatherArmorMeta.setColor(Color.fromBGR(B, G, R));
                   itemStack.setItemMeta(leatherArmorMeta);
                   getAttacker().updateInventory();
               }
           }
        }

        if(!b) {
            B++;
        }else{
            B--;
        }
        if(B > 250)
            b = true;
        else if (B<3)
            b = false;


        if(!g) {
            G++;
        }else{
            G--;
        }
        if(G > 250)
            g = true;
        else if (G<3)
            g = false;

        if(!r) {
            R++;
        }else{
            R--;
        }
        if(R > 250)
            r = true;
        else if (R<3)
            r = false;






            i++;
    }

    @Override
    public void onUnRegister() {
        ArmorHelper.clearArmor(getAttacker());
    }


    private void toggleBoolean(boolean b){
        if(b)
            b = false;
        else
            b = true;
    }
}
