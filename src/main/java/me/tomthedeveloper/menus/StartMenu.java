package me.tomthedeveloper.menus;

import me.tomthedeveloper.EULAGadgets;
import me.tomthedeveloper.Languages;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tom on 9/09/2014.
 */
public class StartMenu implements Listener{


    private Inventory inventory;
    private EULAGadgets plugin;


    public StartMenu(EULAGadgets plugin){
        this.plugin = plugin;
        inventory = Bukkit.getServer().createInventory(null,9, Languages.getMessage("StartMenuName", "Gadget Menu"));
        List<String> names = new ArrayList<String>();
        if(plugin.getMenuItems().isEnabled("ParticleTrailItem"))
          inventory.setItem(1, plugin.getMenuItems().getItem("ParticleTrailItem"));
        if(plugin.getMenuItems().isEnabled("ParticleCircleItem"))
             inventory.setItem(2, plugin.getMenuItems().getItem("ParticleCircleItem"));
        if(plugin.getMenuItems().isEnabled("ParticleVortexItem"))
            inventory.setItem(3,plugin.getMenuItems().getItem("ParticleVortexItem"));
        if(plugin.getMenuItems().isEnabled("FireWorkItem"))
            inventory.setItem(4, plugin.getMenuItems().getItem("FireWorkItem"));
        if(plugin.getMenuItems().isEnabled("GadgetItem"))
          inventory.setItem(5, plugin.getMenuItems().getItem("GadgetItem"));
        if(plugin.getMenuItems().isEnabled("StartMenuHatsItem"))
            inventory.setItem(6, plugin.getMenuItems().getItem("StartMenuHatsItem"));
        if(plugin.getMenuItems().isEnabled("CloseInventoryItem"))
          inventory.setItem(8, plugin.getMenuItems().getItem("CloseInventoryItem"));
    }

    public Inventory getInventory(){
        return inventory;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        if(!event.getInventory().getName().equalsIgnoreCase(Languages.getMessage("StartMenuName", ChatColor.GOLD + "Gadget Menu")))
            return;
        event.setCancelled(true);
        if(event.getCurrentItem() == null)
            return;
        if(!event.getCurrentItem().hasItemMeta())
            return;
        if (!event.getCurrentItem().getItemMeta().hasDisplayName())
            return;
        if(plugin.getMenuItems().getItem("CloseInventoryItem").getType() == event.getCurrentItem().getType()
                && plugin.getMenuItems().getItem("CloseInventoryItem").getItemMeta().getDisplayName().equalsIgnoreCase(event.getCurrentItem().getItemMeta().getDisplayName())){
            event.getWhoClicked().closeInventory();
            return;
        }
        if(!event.getWhoClicked().hasPermission(plugin.getMenuItems().getPermission(event.getCurrentItem()))){
            ((Player) event.getWhoClicked()).sendMessage(Languages.getMessage("NoPermission", ChatColor.RED + "You don't have permission to do that!"));
            return;
        }


        if(plugin.getMenuItems().getItem("ParticleTrailItem").getType() == event.getCurrentItem().getType() &&
                plugin.getMenuItems().getItem("ParticleTrailItem").getItemMeta().getDisplayName().equalsIgnoreCase(event.getCurrentItem().getItemMeta().getDisplayName())){
            plugin.getParticleTrailMenu().openMenu((Player) event.getWhoClicked());
            return;
        }
        if(plugin.getMenuItems().getItem("ParticleCircleItem").getType() == event.getCurrentItem().getType() &&
                plugin.getMenuItems().getItem("ParticleCircleItem").getItemMeta().getDisplayName().equalsIgnoreCase(event.getCurrentItem().getItemMeta().getDisplayName())){
            plugin.getParticleCircleMenu().openMenu((Player) event.getWhoClicked());
            return;
        }
        if(plugin.getMenuItems().getItem("FireWorkItem").getType() == event.getCurrentItem().getType() &&
                plugin.getMenuItems().getItem("FireWorkItem").getItemMeta().getDisplayName().equalsIgnoreCase(event.getCurrentItem().getItemMeta().getDisplayName())){
            plugin.getFireWorkMenu().openMenu((Player) event.getWhoClicked());
        }
        if(plugin.getMenuItems().getItem("GadgetItem").getType() == event.getCurrentItem().getType() &&
                plugin.getMenuItems().getItem("GadgetItem").getItemMeta().getDisplayName().equalsIgnoreCase(event.getCurrentItem().getItemMeta().getDisplayName())){
            plugin.getGadgetMenu().openMenu((Player) event.getWhoClicked());
        }
        if(plugin.getMenuItems().getItem("StartMenuHatsItem").getType() == event.getCurrentItem().getType() &&
                plugin.getMenuItems().getItem("StartMenuHatsItem").getItemMeta().getDisplayName().equalsIgnoreCase(event.getCurrentItem().getItemMeta().getDisplayName())){
            plugin.getHatMenu().openMenu((Player) event.getWhoClicked());
        }
        if(plugin.getMenuItems().getItem("ParticleVortexItem").getType() == event.getCurrentItem().getType() &&
                plugin.getMenuItems().getItem("ParticleVortexItem").getItemMeta().getDisplayName().equalsIgnoreCase(event.getCurrentItem().getItemMeta().getDisplayName())){
            plugin.getParticleVortexMenu().openMenu((Player) event.getWhoClicked());
            return;
        }

    }
}
