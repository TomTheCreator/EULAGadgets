package me.tomthedeveloper;

import me.tomthedeveloper.Attacks.Attack;
import me.tomthedeveloper.Attacks.AttackListener;
import me.tomthedeveloper.Attacks.Gadgets.*;
import me.tomthedeveloper.Utils.RollBack;
import me.tomthedeveloper.Utils.Util;
import me.tomthedeveloper.commands.*;
import me.tomthedeveloper.managers.AttackManager;
import me.tomthedeveloper.managers.ConfigurationManager;
import me.tomthedeveloper.managers.UserManager;
import me.tomthedeveloper.menus.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.List;

/**
 * Created by Tom on 9/09/2014.
 */
public class EULAGadgets extends JavaPlugin implements Listener {


    private AttackManager attackManager;
    private AttackListener attackListener;
    private MenuItems menuItems;
    private FileConfiguration hotbarconfig;
    private ItemStack gadgetmenItemStack = null;
    private int gadgetmenuitemslot = 8;



    private StartMenu startMenu;
    private ParticleTrailMenu particleTrailMenu;
    private ParticleCircleMenu particleCircleMenu;
    private VortexMenu particleVortexMenu;
    private GadgetMenu gadgetMenu;
    private FireWorkMenu fireWorkMenu;
    private HatMenu hatMenu;

    private Languages languages;


    @Override
    public void onDisable() {

    }

    @Override
    public void onEnable() {

        ConfigurationManager.plugin = this;
        Attack.plugin = this;
        User.plugin = this;
        AttackListener.plugin = this;
        languages = new Languages();
        languages.loadMessages();
        attackManager = new AttackManager();
        attackListener = new AttackListener();
        attackListener.start();
        this.getServer().getPluginManager().registerEvents(this, this);

        this.getServer().getPluginManager().registerEvents(new Parachute(), this);
        menuItems = new MenuItems();
        menuItems.setup();
        startMenu = new StartMenu(this);
        particleTrailMenu = new ParticleTrailMenu(this);
        particleCircleMenu = new ParticleCircleMenu(this);
        particleVortexMenu = new VortexMenu(this);
        gadgetMenu = new GadgetMenu(this);
        fireWorkMenu = new FireWorkMenu(this);
        hatMenu = new HatMenu(this);
        this.getServer().getPluginManager().registerEvents(startMenu, this);
        this.getServer().getPluginManager().registerEvents(particleCircleMenu, this);
        this.getServer().getPluginManager().registerEvents(particleTrailMenu, this);
        this.getServer().getPluginManager().registerEvents(particleVortexMenu,this);
        this.getServer().getPluginManager().registerEvents(gadgetMenu, this);
        this.getServer().getPluginManager().registerEvents(fireWorkMenu, this);
        this.getServer().getPluginManager().registerEvents(hatMenu, this);
        RollBack.loadupUnPaintableList();
        RollBack rollBack = new RollBack();
        rollBack.runTaskTimer(this, 1L, 1L);
        this.getCommand("gadgetmenu").setExecutor(new GadgetCommand(this));
        this.getCommand("fireworks").setExecutor(new FireWorkMenuCommand(this));
        this.getCommand("gadgets").setExecutor(new GadgetMenuCommand(this));
        this.getCommand("hats").setExecutor(new HatsMenuCommand(this));
        this.getCommand("particlecircles").setExecutor(new ParticleCirclesMenuCommand(this));
        this.getCommand("particletrails").setExecutor(new ParticleTrailsCommand(this));

        hotbarconfig = ConfigurationManager.getConfig("HotBarItems");
        loadupHotBarConfig();
        Languages.getMessage("GadgetStillOnCooldown", ChatColor.RED + "This gadget is still on cooldown for %DELAY% more seconds!");
    }

    public AttackManager getAttackManager(){
        return attackManager;
    }

    @EventHandler
    public void ontest(PlayerInteractEvent event){
        if(!event.hasItem())
            return;
        if(event.getItem().getType() != gadgetmenItemStack.getType())
            return;
        if(!event.getItem().hasItemMeta())
            return;
        if(!event.getItem().getItemMeta().hasDisplayName())
            return;
        if(!event.getItem().getItemMeta().getDisplayName().equals(gadgetmenItemStack.getItemMeta().getDisplayName()))
            return;
        event.getPlayer().openInventory(startMenu.getInventory());
        event.setCancelled(true);
    }

    public MenuItems getMenuItems(){
        return menuItems;
    }

    public StartMenu getStartMenu(){
        return startMenu;
    }

    public ParticleTrailMenu getParticleTrailMenu(){
        return particleTrailMenu;
    }

    public ParticleCircleMenu getParticleCircleMenu(){
        return particleCircleMenu;
    }

    public VortexMenu getParticleVortexMenu(){ return particleVortexMenu;}

    public FireWorkMenu getFireWorkMenu(){
        return fireWorkMenu;
    }

    public GadgetMenu getGadgetMenu(){
        return gadgetMenu;
    }

    private void loadupHotBarConfig(){
        if(!hotbarconfig.contains("items.OpenGadgetMenuItem")){
            setToConfig(hotbarconfig, "OpenGadgetMenuItem", ChatColor.GOLD + "Gadget Menu", new String[]{
                    ChatColor.GRAY + "Right click to open gadget menu!"
            }, Material.TRAPPED_CHEST, 8);

        }

       String itemname = "OpenGadgetMenuItem";
        List<String> strings =  hotbarconfig.getStringList( "items." + itemname + ".lore");
        ItemStack itemStack = new ItemStack(Material.getMaterial(hotbarconfig.getString("items." +itemname + ".material")));

        gadgetmenItemStack = Util.setItemNameAndLore(itemStack, hotbarconfig.getString("items." +itemname + ".name"),strings.toArray(new String[strings.size()]));
        gadgetmenuitemslot = hotbarconfig.getInt("items." +itemname + ".slot");


    }

    public  void setToConfig(FileConfiguration config, String itemname, String name, String[] lore, Material material, int slot){
        config.set("items." + itemname + ".name", name);
        config.set("items." + itemname + ".material", material.toString()/*.replaceAll("!! org.bukkit.material ", "")*/);
        config.set("items." + itemname + ".lore", lore);

        config.set("items." + itemname + ".slot", slot);
        try {
            config.save(ConfigurationManager.getFile("HotBarItems"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        event.getPlayer().getInventory().setItem(gadgetmenuitemslot, gadgetmenItemStack);

    }

    @EventHandler
    public void onQuit(final PlayerQuitEvent event){
        this.getAttackManager().unregisterAttack(UserManager.getUser(event.getPlayer().getUniqueId()).getAttack());
        UserManager.getUser(event.getPlayer().getUniqueId()).unRegisterAttacks();
        Bukkit.getServer().getScheduler().runTaskLater(this, new Runnable() {
            @Override
            public void run() {
                UserManager.removeUser(event.getPlayer().getUniqueId());
            }
        },1L);

    }

    public HatMenu getHatMenu(){
        return hatMenu;
    }



}
