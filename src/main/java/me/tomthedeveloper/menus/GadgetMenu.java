package me.tomthedeveloper.menus;

import me.tomthedeveloper.Attacks.Gadgets.*;
import me.tomthedeveloper.EULAGadgets;
import me.tomthedeveloper.Languages;
import me.tomthedeveloper.User;
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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tom on 9/09/2014.
 */
public class GadgetMenu implements Listener {

    private EULAGadgets plugin;

    public GadgetMenu(EULAGadgets plugin){
        this.plugin = plugin;
    }

    private Inventory createMenu(Player player){
        Inventory inventory = player.getServer().createInventory(null, Util.serializeInt(54), Languages.getMessage("GadgetMenuName", "Gadget Menu"));

        List<String> gadgetnames = new ArrayList<String>();
        gadgetnames.add("DiscoArmorGadget");
        gadgetnames.add("BowRiderGadget");
        gadgetnames.add("PlayerBowPopGadget");
        gadgetnames.add("PlayerHandPopGadget");
        gadgetnames.add("PlayerRiderGadget");
        gadgetnames.add("FireWorkRider");
        gadgetnames.add("SmokeBomb");
        gadgetnames.add("FastDiscoArmor");
        gadgetnames.add("TNTBow");
        gadgetnames.add("Puncher");
        gadgetnames.add("PaintGun");
        gadgetnames.add("EggGrenade");
        for(String name:gadgetnames){
            if(plugin.getMenuItems().isEnabled(name)) {
                ItemStack item =plugin.getMenuItems().getItem(name);
                Util.addLore(item, Languages.getMessage("DelayLoreOnGadgets", ChatColor.GRAY + "Delay: " + ChatColor.GREEN + "%DELAY%" + ChatColor.GRAY + " seconds.").replaceAll("%DELAY%", Integer.toString(plugin.getMenuItems().getDelay(name))));
                inventory.addItem(item);
                plugin.getMenuItems().updateItemStackInList(item, name);
            }
        }
        /*
        inventory.addItem(plugin.getMenuItems().getItem("DiscoArmorGadget"));
        inventory.addItem(plugin.getMenuItems().getItem("BowRiderGadget"));
        inventory.addItem(plugin.getMenuItems().getItem("PlayerBowPopGadget"));
        inventory.addItem(plugin.getMenuItems().getItem("PlayerHandPopGadget"));
        inventory.addItem(plugin.getMenuItems().getItem("PlayerRiderGadget"));
        inventory.addItem(plugin.getMenuItems().getItem("FireWorkRider"));
        inventory.addItem(plugin.getMenuItems().getItem("SmokeBomb"));
        inventory.addItem(plugin.getMenuItems().getItem("FastDiscoArmor"));
        inventory.addItem(plugin.getMenuItems().getItem("TNTBow"));
        inventory.addItem(plugin.getMenuItems().getItem("PuncherItem"));
        inventory.addItem(plugin.getMenuItems().getItem("PaintGunItem"));
        inventory.addItem(plugin.getMenuItems().getItem("EggGrenade"));
        */

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
    public void onClick(InventoryClickEvent event){
        if (!event.getInventory().getName().equalsIgnoreCase(Languages.getMessage("GadgetMenuName")))
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
        if(!event.getWhoClicked().hasPermission(plugin.getMenuItems().getPermission(event.getCurrentItem()))){
            ((Player) event.getWhoClicked()).sendMessage(Languages.getMessage("NoPermission", ChatColor.RED + "You don't have permission to do that!"));
            return;
        }

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
        User user = UserManager.getUser(((Player ) event.getWhoClicked()).getUniqueId());
      //  if(user.getAttack() != null)
        //    user.unRegisterAttacks();
        if (plugin.getMenuItems().getItem("BowRiderGadget").getType() == event.getCurrentItem().getType()
                && plugin.getMenuItems().getItem("BowRiderGadget").getItemMeta().getDisplayName().equalsIgnoreCase(event.getCurrentItem().getItemMeta().getDisplayName())) {
            user.unRegisterAttacks();
            ArrowRider arrowRider = new ArrowRider((Player)event.getWhoClicked());
            user.setAttack(arrowRider);
            plugin.getServer().getPluginManager().registerEvents(arrowRider, plugin);
            user.toPlayer().closeInventory();
            ((Player) event.getWhoClicked()).sendMessage(Languages.getMessage("GadgetActivatedMessage", ChatColor.GREEN + "Gadget activated!"));
            return;
        }

        if (plugin.getMenuItems().getItem("DiscoArmorGadget").getType() == event.getCurrentItem().getType()
                && plugin.getMenuItems().getItem("DiscoArmorGadget").getItemMeta().getDisplayName().equalsIgnoreCase(event.getCurrentItem().getItemMeta().getDisplayName())) {
           user.unRegisterAttacks();
            DiscoArmor discoArmor = new DiscoArmor((Player)event.getWhoClicked());
            user.setAttack(discoArmor);
            user.toPlayer().closeInventory();
            ((Player) event.getWhoClicked()).sendMessage(Languages.getMessage("GadgetActivatedMessage", ChatColor.GREEN + "Gadget activated!"));
            return;
        }
        if (plugin.getMenuItems().getItem("PlayerRiderGadget").getType() == event.getCurrentItem().getType()
                && plugin.getMenuItems().getItem("PlayerRiderGadget").getItemMeta().getDisplayName().equalsIgnoreCase(event.getCurrentItem().getItemMeta().getDisplayName())) {
            user.unRegisterAttacks();
            PlayerRider discoArmor = new PlayerRider((Player)event.getWhoClicked());

            user.setAttack(discoArmor);
            user.toPlayer().closeInventory();
            plugin.getServer().getPluginManager().registerEvents(discoArmor, plugin);
            ((Player) event.getWhoClicked()).sendMessage(Languages.getMessage("GadgetActivatedMessage", ChatColor.GREEN + "Gadget activated!"));
            return;
        }
        if (plugin.getMenuItems().getItem("PlayerHandPopGadget").getType() == event.getCurrentItem().getType()
                && plugin.getMenuItems().getItem("PlayerHandPopGadget").getItemMeta().getDisplayName().equalsIgnoreCase(event.getCurrentItem().getItemMeta().getDisplayName())) {
            user.unRegisterAttacks();
            HandPopPlayers discoArmor = new HandPopPlayers((Player)event.getWhoClicked());
            user.setAttack(discoArmor);
            user.toPlayer().closeInventory();
            plugin.getServer().getPluginManager().registerEvents(discoArmor, plugin);
            ((Player) event.getWhoClicked()).sendMessage(Languages.getMessage("GadgetActivatedMessage", ChatColor.GREEN + "Gadget activated!"));
            return;
        }
        if (plugin.getMenuItems().getItem("PlayerBowPopGadget").getType() == event.getCurrentItem().getType()
                && plugin.getMenuItems().getItem("PlayerBowPopGadget").getItemMeta().getDisplayName().equalsIgnoreCase(event.getCurrentItem().getItemMeta().getDisplayName())) {
            user.unRegisterAttacks();
            BowPopPlayers discoArmor = new BowPopPlayers((Player)event.getWhoClicked());
            user.setAttack(discoArmor);
            user.toPlayer().closeInventory();
            plugin.getServer().getPluginManager().registerEvents(discoArmor, plugin);
            ((Player) event.getWhoClicked()).sendMessage(Languages.getMessage("GadgetActivatedMessage", ChatColor.GREEN + "Gadget activated!"));
            return;
        }
        if (plugin.getMenuItems().getItem("FireWorkRider").getType() == event.getCurrentItem().getType()
                && plugin.getMenuItems().getItem("FireWorkRider").getItemMeta().getDisplayName().equalsIgnoreCase(event.getCurrentItem().getItemMeta().getDisplayName())) {
            FireWorkRide gadget = new FireWorkRide((Player)event.getWhoClicked());
            user.unRegisterAttacks();
            user.setAttack(gadget);
            user.toPlayer().closeInventory();
            plugin.getServer().getPluginManager().registerEvents(gadget, plugin);
            ((Player) event.getWhoClicked()).sendMessage(Languages.getMessage("GadgetActivatedMessage", ChatColor.GREEN + "Gadget activated!"));
            return;
        }
        if (plugin.getMenuItems().getItem("SmokeBomb").getType() == event.getCurrentItem().getType()
                && plugin.getMenuItems().getItem("SmokeBomb").getItemMeta().getDisplayName().equalsIgnoreCase(event.getCurrentItem().getItemMeta().getDisplayName())) {

            user.unRegisterAttacks();
            SmokeBomb gadget = new SmokeBomb((Player)event.getWhoClicked());
            user.setAttack(gadget);
            user.toPlayer().closeInventory();
            plugin.getServer().getPluginManager().registerEvents(gadget, plugin);
            ((Player) event.getWhoClicked()).sendMessage(Languages.getMessage("GadgetActivatedMessage", ChatColor.GREEN + "Gadget activated!"));
            return;
        }
        if (plugin.getMenuItems().getItem("FastDiscoArmor").getType() == event.getCurrentItem().getType()
                && plugin.getMenuItems().getItem("FastDiscoArmor").getItemMeta().getDisplayName().equalsIgnoreCase(event.getCurrentItem().getItemMeta().getDisplayName())) {
            user.unRegisterAttacks();
            FastDiscoArmor gadget = new FastDiscoArmor((Player)event.getWhoClicked());

            user.setAttack(gadget);
            user.toPlayer().closeInventory();
            ((Player) event.getWhoClicked()).sendMessage(Languages.getMessage("GadgetActivatedMessage", ChatColor.GREEN + "Gadget activated!"));
            return;
        }
        if (plugin.getMenuItems().getItem("TNTBow").getType() == event.getCurrentItem().getType()
                && plugin.getMenuItems().getItem("TNTBow").getItemMeta().getDisplayName().equalsIgnoreCase(event.getCurrentItem().getItemMeta().getDisplayName())) {

            user.unRegisterAttacks();
            TNTBow gadget = new TNTBow((Player)event.getWhoClicked());
            user.setAttack(gadget);
            user.toPlayer().closeInventory();
            plugin.getServer().getPluginManager().registerEvents(gadget, plugin);
            ((Player) event.getWhoClicked()).sendMessage(Languages.getMessage("GadgetActivatedMessage", ChatColor.GREEN + "Gadget activated!"));
            return;
        }
        if (plugin.getMenuItems().getItem("Puncher").getType() == event.getCurrentItem().getType()
                && plugin.getMenuItems().getItem("Puncher").getItemMeta().getDisplayName().equalsIgnoreCase(event.getCurrentItem().getItemMeta().getDisplayName())) {

            user.unRegisterAttacks();
            Puncher gadget = new Puncher((Player)event.getWhoClicked());
            user.setAttack(gadget);
            user.toPlayer().closeInventory();
            plugin.getServer().getPluginManager().registerEvents(gadget, plugin);
            ((Player) event.getWhoClicked()).sendMessage(Languages.getMessage("GadgetActivatedMessage", ChatColor.GREEN + "Gadget activated!"));
            return;
        }
        if (plugin.getMenuItems().getItem("PaintGun").getType() == event.getCurrentItem().getType()
                && plugin.getMenuItems().getItem("PaintGun").getItemMeta().getDisplayName().equalsIgnoreCase(event.getCurrentItem().getItemMeta().getDisplayName())) {

            user.unRegisterAttacks();
            PaintGun gadget = new PaintGun((Player)event.getWhoClicked());
            user.setAttack(gadget);
            user.toPlayer().closeInventory();
            plugin.getServer().getPluginManager().registerEvents(gadget, plugin);
            ((Player) event.getWhoClicked()).sendMessage(Languages.getMessage("GadgetActivatedMessage", ChatColor.GREEN + "Gadget activated!"));
            return;
        }
        if (plugin.getMenuItems().getItem("EggGrenade").getType() == event.getCurrentItem().getType()
                && plugin.getMenuItems().getItem("EggGrenade").getItemMeta().getDisplayName().equalsIgnoreCase(event.getCurrentItem().getItemMeta().getDisplayName())) {

            user.unRegisterAttacks();
            EggGrenade gadget = new EggGrenade((Player)event.getWhoClicked());
            user.setAttack(gadget);
            user.toPlayer().closeInventory();
            plugin.getServer().getPluginManager().registerEvents(gadget, plugin);
            ((Player) event.getWhoClicked()).sendMessage(Languages.getMessage("GadgetActivatedMessage", ChatColor.GREEN + "Gadget activated!"));
            return;
        }

    }

}
