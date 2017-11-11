package me.tomthedeveloper.menus;

import me.tomthedeveloper.Attacks.Gadgets.ParticleTrail;
import me.tomthedeveloper.Attacks.Gadgets.RandomFireWork;
import me.tomthedeveloper.Attacks.Gadgets.SpawnFireWork;
import me.tomthedeveloper.EULAGadgets;
import me.tomthedeveloper.Languages;
import me.tomthedeveloper.User;
import me.tomthedeveloper.Utils.FireworkTypes;
import me.tomthedeveloper.Utils.ParticleEffect;
import me.tomthedeveloper.Utils.Util;
import me.tomthedeveloper.managers.UserManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Tom on 9/09/2014.
 */
public class FireWorkMenu implements Listener {

    private EULAGadgets plugin;

    public FireWorkMenu(EULAGadgets plugin){
        this.plugin = plugin;
    }

    private Inventory createMenu(Player player){
        Inventory inventory = player.getServer().createInventory(null, Util.serializeInt(54), Languages.getMessage("FireWorkMenuName", "FireWork Menu"));

        inventory.addItem(plugin.getMenuItems().getItem("RandomFireWork"));

        for(FireworkTypes fireworkTypes:FireworkTypes.getValues()){
            if(plugin.getMenuItems().isEnabled("FireworkTypes." + fireworkTypes.getName())) {

                ItemStack item = plugin.getMenuItems().getItem("FireworkTypes." + fireworkTypes.getName());
                Util.addLore(item, Languages.getMessage("DelayLoreOnGadgets", ChatColor.GRAY + "Delay: " + ChatColor.GREEN + "%DELAY%" + ChatColor.GRAY + " seconds.").replaceAll("%DELAY%", Integer.toString(plugin.getMenuItems().getDelay("FireworkTypes." + fireworkTypes.getName()))));
                inventory.addItem(item);
                plugin.getMenuItems().updateItemStackInList(item,"FireworkTypes." + fireworkTypes.getName() );

            }
        }
        if(plugin.getMenuItems().isEnabled("CloseInventoryItem"))
            inventory.setItem(53, plugin.getMenuItems().getItem("CloseInventoryItem"));
        if(plugin.getMenuItems().isEnabled("BackItem"))
            inventory.setItem(45, plugin.getMenuItems().getItem("BackItem"));
        if(UserManager.getUser(player.getUniqueId()).getAttack() != null) {
            if(plugin.getMenuItems().isEnabled("RemoveGadgetItem"))
                 inventory.setItem(49, plugin.getMenuItems().getItem("RemoveGadgetItem"));
        }else {
            if(plugin.getMenuItems().isEnabled("NoActiveGadgetItem"))
                inventory.setItem(49, plugin.getMenuItems().getItem("NoActiveGadgetItem"));
        }
        return inventory;
    }

    public void openMenu(Player player){
        player.openInventory(createMenu(player));
    }


    @EventHandler
    public void onClick(InventoryClickEvent event){
        if (!event.getInventory().getName().equalsIgnoreCase(Languages.getMessage("FireWorkMenuName")))
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
            ((Player)event.getWhoClicked()).openInventory(plugin.getStartMenu().getInventory());
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
        if(!event.getWhoClicked().hasPermission(plugin.getMenuItems().getPermission(event.getCurrentItem()))){
            ((Player) event.getWhoClicked()).sendMessage(Languages.getMessage("NoPermission", ChatColor.RED + "You don't have permission to do that!"));
            return;
        }
        User user = UserManager.getUser(event.getWhoClicked().getUniqueId());
        if (plugin.getMenuItems().getItem("RandomFireWork").getType() == event.getCurrentItem().getType()
                && plugin.getMenuItems().getItem("RandomFireWork").getItemMeta().getDisplayName().equalsIgnoreCase(event.getCurrentItem().getItemMeta().getDisplayName())) {
             RandomFireWork gadget = new RandomFireWork((Player)event.getWhoClicked());
            user.unRegisterAttacks();
            user.setAttack(gadget);
            user.toPlayer().closeInventory();
            plugin.getServer().getPluginManager().registerEvents(gadget, plugin);
            ((Player) event.getWhoClicked()).sendMessage(Languages.getMessage("FireWorkChosenMessage", ChatColor.GREEN + "Firework activated!"));
            return;
        }

        user.unRegisterAttacks();
        String itemname = plugin.getMenuItems().getItemName(event.getCurrentItem());
        FireworkTypes fireworkTypes = FireworkTypes.fromName(itemname);
        SpawnFireWork spawnFirework =  new SpawnFireWork((Player) event.getWhoClicked(), fireworkTypes.getColor(), fireworkTypes.getFade(), fireworkTypes.getType(), itemname );
        user.setAttack(spawnFirework);
        plugin.getServer().getPluginManager().registerEvents(spawnFirework, plugin);
        ((Player) event.getWhoClicked()).sendMessage(Languages.getMessage("FireWorkActivated", ChatColor.GREEN + "Firework Gadget activated!"));
        event.getWhoClicked().closeInventory();
    }
}
