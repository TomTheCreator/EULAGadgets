package me.tomthedeveloper.menus;

import me.tomthedeveloper.Attacks.Gadgets.Hats;
import me.tomthedeveloper.Attacks.Gadgets.ParticleCircle;
import me.tomthedeveloper.EULAGadgets;
import me.tomthedeveloper.Languages;
import me.tomthedeveloper.User;
import me.tomthedeveloper.Utils.FireworkTypes;
import me.tomthedeveloper.Utils.HatTypes;
import me.tomthedeveloper.Utils.ParticleEffect;
import me.tomthedeveloper.Utils.Util;
import me.tomthedeveloper.managers.UserManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

/**
 * Created by Tom on 15/09/2014.
 */
public class HatMenu implements Listener {



    private EULAGadgets plugin;

    public HatMenu(EULAGadgets plugin){
        this.plugin = plugin;
    }

    private Inventory createMenu(Player player){
        Inventory inventory = player.getServer().createInventory(null, Util.serializeInt(54), Languages.getMessage("HatMenuName", "Hat Menu"));

        for(HatTypes hatTypes:HatTypes.values()){
            if(plugin.getMenuItems().isEnabled("Hats." + hatTypes.toString()))
                inventory.addItem(plugin.getMenuItems().getItem("Hats." + hatTypes.toString()));
        }


        if(plugin.getMenuItems().isEnabled("CloseInventoryItem"))
            inventory.setItem(53, plugin.getMenuItems().getItem("CloseInventoryItem"));
        if(plugin.getMenuItems().isEnabled("BackItem"))
            inventory.setItem(45, plugin.getMenuItems().getItem("BackItem"));
        if(UserManager.getUser(player.getUniqueId()).getAttack() != null) {
            if (plugin.getMenuItems().isEnabled("RemoveGadgetItem"))
                inventory.setItem(49, plugin.getMenuItems().getItem("RemoveGadgetItem"));
        }else {
            if (plugin.getMenuItems().isEnabled("NoActiveGadgetItem"))
                inventory.setItem(49, plugin.getMenuItems().getItem("NoActiveGadgetItem"));
        }
        return inventory;
    }

    public void openMenu(Player player){
        player.openInventory(createMenu(player));
    }


    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (!event.getInventory().getName().equalsIgnoreCase(Languages.getMessage("HatMenuName")))
            return;

        event.setCancelled(true);
        if (event.getCurrentItem() == null)
            return;
        if (!event.getCurrentItem().hasItemMeta())
            return;
        if (!event.getCurrentItem().getItemMeta().hasDisplayName())
            return;
        //if(!plugin.getMenuItems().getItemName(event.getCurrentItem()).contains("Circle"))
        //   return;

        if (plugin.getMenuItems().getItem("CloseInventoryItem").getType() == event.getCurrentItem().getType()
                && plugin.getMenuItems().getItem("CloseInventoryItem").getItemMeta().getDisplayName().equalsIgnoreCase(event.getCurrentItem().getItemMeta().getDisplayName())) {
            event.getWhoClicked().closeInventory();
            return;
        }
        if (plugin.getMenuItems().getItem("BackItem").getType() == event.getCurrentItem().getType()
                && plugin.getMenuItems().getItem("BackItem").getItemMeta().getDisplayName().equalsIgnoreCase(event.getCurrentItem().getItemMeta().getDisplayName())) {
            ((Player) event.getWhoClicked()).openInventory(plugin.getStartMenu().getInventory());
            return;
        }
        if (plugin.getMenuItems().getItem("RemoveGadgetItem").getType() == event.getCurrentItem().getType()
                && plugin.getMenuItems().getItem("RemoveGadgetItem").getItemMeta().getDisplayName().equalsIgnoreCase(event.getCurrentItem().getItemMeta().getDisplayName())) {
            User user = UserManager.getUser(event.getWhoClicked().getUniqueId());
            user.unRegisterAttacks();
            event.getInventory().setItem(49, plugin.getMenuItems().getItem("NoActiveGadgetItem"));
            user.toPlayer().updateInventory();
            user.toPlayer().sendMessage(Languages.getMessage("GadgetRemovedMessage", ChatColor.GREEN + "Gadget removed!"));
            return;
        }
        if (plugin.getMenuItems().getItem("NoActiveGadgetItem").getType() == event.getCurrentItem().getType()
                && plugin.getMenuItems().getItem("NoActiveGadgetItem").getItemMeta().getDisplayName().equalsIgnoreCase(event.getCurrentItem().getItemMeta().getDisplayName())) {
            return;
        }
        if (!event.getWhoClicked().hasPermission(plugin.getMenuItems().getPermission(event.getCurrentItem()))) {
            ((Player) event.getWhoClicked()).sendMessage(Languages.getMessage("NoPermission", ChatColor.RED + "You don't have permission to do that!"));
            return;
        }
        User user = UserManager.getUser(event.getWhoClicked().getUniqueId());
        if(user.getAttack() != null)
            user.unRegisterAttacks();
        String itemname = plugin.getMenuItems().getItemName(event.getCurrentItem());

        Hats particleTrail =  new Hats((Player) event.getWhoClicked(), event.getCurrentItem());
        user.setAttack(particleTrail);
        ((Player) event.getWhoClicked()).sendMessage(Languages.getMessage("HatsActivatedMessage", ChatColor.GREEN + "Hat activated!"));
        plugin.getServer().getPluginManager().registerEvents(particleTrail, plugin);
        event.getWhoClicked().closeInventory();
    }
}
