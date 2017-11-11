package me.tomthedeveloper.menus;

import me.tomthedeveloper.Languages;
import me.tomthedeveloper.Utils.*;
import me.tomthedeveloper.managers.ConfigurationManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.util.*;

/**
 * Created by Tom on 9/09/2014.
 */
public class MenuItems {

    private  FileConfiguration config;

    private  HashMap<String, Material> materials = new HashMap<String, Material>();
    private  HashMap<String, String> names = new HashMap<String, String>();
    private  HashMap<String, String[]> lores = new HashMap<String, String[]>();
    private HashMap<String, String> permissions = new HashMap<String, String>();
    private HashMap<ItemStack, String> itemnames = new HashMap<ItemStack, String>();
    private HashMap<String, Boolean> enabled = new HashMap<String, Boolean>();
    private HashMap<String, Integer> delays = new HashMap<String, Integer>();

    public  void setup(){
        config = ConfigurationManager.getConfig("menu");
        loadItems();
    }

    public  void loadItems() {
        if (!config.contains("items")) {
            setToConfig("ParticleTrailItem", ChatColor.GOLD + "Particle Trails", new String[]{ChatColor.GRAY + "Click for awesome", ChatColor.GRAY + "particle trails!"}, Material.YELLOW_FLOWER);
            setToConfig("ParticleCircleItem", ChatColor.GOLD + "Paritcle Circles!", new String[]{ChatColor.GRAY + "Click for awesome", ChatColor.GRAY + "particle circles above", ChatColor.GRAY + "your head!"}, Material.RED_ROSE);
            setToConfig("FireWorkItem", ChatColor.GOLD + "Launch Firework!", new String[]{ChatColor.GRAY + "Click for beautifull", ChatColor.GRAY + "firework!"}, Material.FIREWORK);
            setToConfig("CloseInventoryItem", ChatColor.RED + "Close Inventory", new String[]{}, Material.REDSTONE_BLOCK);
            setToConfig("BackItem", ChatColor.RED + "Go back", new String[]{"", ""}, Material.ARROW);
            setToConfig("RemoveGadgetItem", ChatColor.GOLD + "A gadget is activated!", new String[]{
                    ChatColor.GRAY + "Click this to", ChatColor.GRAY + "remove the gadget/effect"
            }, Material.WATER_BUCKET);
            setToConfig("NoActiveGadgetItem", ChatColor.RED + "No activa gadget!", new String[]{
                    ChatColor.GRAY + "There is no effect or gadget", ChatColor.GRAY + "activated!"
            }, Material.BUCKET);
            setToConfig("GadgetItem", ChatColor.GOLD + "Awesome Gadgets", new String[]{"" +
                    ChatColor.GRAY + "Click for gadgets!"}, Material.BLAZE_POWDER);
            setToConfig("BowRiderGadget", ChatColor.GOLD + "Ride your arrows!", new String[]{
                    ChatColor.GRAY + "Shoot while hopping",
                    ChatColor.GRAY + "on your arrow and ride",
                    ChatColor.GRAY + "it!"
            }, Material.BOW);
            setToConfig("DiscoArmorGadget", ChatColor.GOLD + "DiscoArmor", new String[]{
                    ChatColor.GRAY + "The color from your armor",
                    ChatColor.GRAY + "changes to every color!"
            }, Material.LEATHER_CHESTPLATE);
            setToConfig("PlayerRiderGadget", ChatColor.GOLD + "Cowboy", new String[]{
                    ChatColor.GRAY + "Ride players",
                    ChatColor.GRAY + "like a cowboy",
                    ChatColor.GREEN + "Yiiiihaaa!"
            }, Material.LEASH);
            setToConfig("PlayerHandPopGadget", ChatColor.GOLD + "Hand Popper", new String[]{
                    ChatColor.GRAY + "Pop those stupid",
                    ChatColor.GRAY + "away!"
            }, Material.GOLD_AXE);
            setToConfig("PlayerBowPopGadget", ChatColor.GOLD + "Bow Popper", new String[]{
                    ChatColor.GRAY + "Pop those stupid",
                    ChatColor.GRAY + "away with your bow!"
            }, Material.CLAY);
            setToConfig("RandomFireWork", ChatColor.GOLD + "Random Firework", new String[]{
                    ChatColor.GRAY + "Spawn a random Firework!"
            }, Material.FIREWORK);
            setToConfig("FireWorkRider", ChatColor.GOLD + "Firework Rider", new String[]{
                    ChatColor.GRAY + "Ride your firework!",
                    ChatColor.GRAY + "Fly to the moon"
            }, Material.FIREWORK);
            setToConfig("SmokeBomb", ChatColor.GOLD + "Smoke Bomb", new String[]{
                    ChatColor.GRAY + "Throw one and hide!"
            }, Material.SNOW_BALL);
            setToConfig("FastDiscoArmor", ChatColor.GOLD + "Dance with your armor!", new String[]{"" +
                    ChatColor.GRAY + "You're armor changes", "fast from color"}, Material.GOLD_CHESTPLATE);
            setToConfig("TNTBow", ChatColor.GOLD + "TNT Bow", new String[]{
                    ChatColor.GRAY + "Blow up your enemies!"
            }, Material.TNT);
            setToConfig("Puncher", ChatColor.GOLD + "Punch your enimies!",
                    new String[]{ChatColor.GRAY + "Yes, punch them in the face!"}, Material.DIAMOND_SPADE);
            setToConfig("PaintGun", ChatColor.GOLD + "Paint Gun", new String[]{
                    ChatColor.GRAY + "Pant the lobby"
            }, Material.DIAMOND_HOE);
        }
        if (!config.contains("items.ParticleTrail")) {
            for (ParticleEffect particleEffect : ParticleEffect.values()) {
                setToConfig("ParticleTrail." + particleEffect.toString().toLowerCase(), ChatColor.GOLD + particleEffect.toString().toLowerCase().replaceAll("_", " "), new String[]{}, Material.PAPER);
                setToConfig("ParticleCircle." + particleEffect.toString().toLowerCase(), ChatColor.GOLD + particleEffect.toString().toLowerCase().replaceAll("_", " "), new String[]{}, Material.PAPER);
            }

            for (FireworkTypes fireworks : FireworkTypes.values()) {
                setToConfig("FireworkTypes." + fireworks.getName(), ChatColor.GOLD + fireworks.getName().replaceAll("_", " "),
                        new String[]{ChatColor.GRAY + "Spawn a firework with:", ChatColor.GRAY + "Color: " + fireworks.getColorName(),
                                ChatColor.GRAY + "Fade: " + fireworks.getFadeName(),
                                ChatColor.GRAY + "Type: " + ChatColor.DARK_GREEN + fireworks.getType().toString().toLowerCase().replaceAll("_", " ")}, Material.FIREWORK);
            }
          /*  setToConfig("ParticleTrail.HappyVillager", ChatColor.GOLD + "Happy Villager", new String[]{""}, Material.GREEN_RECORD);
            setToConfig("ParticleTrail.Lava", ChatColor.GOLD + "Lava", new String[]{""}, Material.LAVA_BUCKET);
            setToConfig("ParticleTrail.Fire", ChatColor.GOLD + "Fire", new String[]{""}, Material.BLAZE_POWDER);*/


        }
        if (!config.contains("items.ParticleVortex")) {
            for (ParticleEffect particleEffect : ParticleEffect.values()) {
                setToConfig("ParticleVortex." + particleEffect.toString().toLowerCase(), ChatColor.GOLD + particleEffect.toString().toLowerCase().replaceAll("_", " "), new String[]{}, Material.PAPER);
            }
        }
        if(!config.contains("EggGrenade")){
            setToConfig("EggGrenade", ChatColor.GOLD + "Egg grenade",
                    new String[]{"A grenade that colors", "the lobby for you!"}, Material.EGG);
        }

            if(!config.contains("ParticleVortexItem"))
                setToConfig("ParticleVortexItem", ChatColor.GOLD + "Particle Vortex!", new String[]{ChatColor.GRAY + "Click for awesome", ChatColor.GRAY + "particle vortex around", ChatColor.GRAY + "your body!"}, Material.ICE);

            if(!config.contains("Hats")){
            for(HatTypes hatTypes:HatTypes.values()){
                setToConfig("Hats." + hatTypes.toString(), ChatColor.GOLD + hatTypes.getName() + " hat",
                        new String[]{ChatColor.GRAY + "An awesome hat!"}, hatTypes.getMaterial());
            }
            setToConfig("StartMenuHatsItem", ChatColor.GOLD + "Hats Menu", new String[]{
                    ChatColor.GRAY + "Awesome hats"
            }, Material.LAPIS_BLOCK);
        }
        for(ParticleEffect particleEffect:ParticleEffect.values()){
            loadFromConfig("ParticleTrail." + particleEffect.toString().toLowerCase());
            loadFromConfig("ParticleCircle." + particleEffect.toString().toLowerCase());
            loadFromConfig("ParticleVortex." + particleEffect.toString().toLowerCase());
        }
        for(FireworkTypes fireworkTypes: FireworkTypes.values()){
            loadFromConfig("FireworkTypes." + fireworkTypes.getName());
        }
        for(HatTypes hatTypes:HatTypes.values()){
            loadFromConfig("Hats." + hatTypes.toString());
        }
        loadFromConfig("ParticleTrailItem");
        loadFromConfig("ParticleCircleItem");
        loadFromConfig("ParticleVortexItem");
        loadFromConfig("FireWorkItem");
        loadFromConfig("CloseInventoryItem");
        loadFromConfig("GadgetItem");
        loadFromConfig("BackItem");
        loadFromConfig("RemoveGadgetItem");
        loadFromConfig("NoActiveGadgetItem");
        loadFromConfig("BowRiderGadget");
        loadFromConfig("DiscoArmorGadget");
        loadFromConfig("PlayerBowPopGadget");
        loadFromConfig("PlayerHandPopGadget");
        loadFromConfig("PlayerRiderGadget");
        loadFromConfig("RandomFireWork");
        loadFromConfig("FireWorkRider");
        loadFromConfig("SmokeBomb");
        loadFromConfig("FastDiscoArmor");
        loadFromConfig("TNTBow");
        loadFromConfig("EggGrenade");
        loadFromConfig("Puncher");
        loadFromConfig("PaintGun");
        loadFromConfig("StartMenuHatsItem");

    }

    public  ItemStack getItem(String name){
        ItemStack itemStack = new ItemStack(materials.get(name));

        Util.setItemNameAndLore(itemStack, names.get(name), lores.get(name));

        itemnames.put(itemStack, name);
       /* if(itemStack.getType() == Material.BOW){
            ItemStack itemStack1 = WeaponHelper.getEnchanted(itemStack, new Enchantment[]{Enchantment.ARROW_INFINITE} , new int[]{1});
            return itemStack1;
        } */
        return itemStack;


    }

    public void updateItemStackInList(ItemStack itemStack, String itemname){
        itemnames.put(itemStack, itemname);
    }

    public int getDelay(String itemname){
        return delays.get(itemname);
    }

    public boolean isEnabled(String itemname){
        return enabled.get(itemname);
    }

    public boolean isEnabled(ItemStack itemStack){
        return  enabled.get(getItemName(itemStack));
    }

    public  void setToConfig(String itemname, String name, String[] lore, Material material){
        if(config.contains("items." + itemname)){
            if(!config.contains("items." + itemname + ".enabled"))
                config.set("items." + itemname + ".enabled", true);
            return;
        }

        config.set("items." + itemname + ".name", name);
        config.set("items." + itemname + ".material", material.toString()/*.replaceAll("!! org.bukkit.material ", "")*/);
        config.set("items." + itemname + ".lore", lore);
        config.set("items." + itemname + ".permission", "EULAGadgets.VIP");
        config.set("items." + itemname + ".enabled", true);
        if(!(itemname.contains("ParticleTrail") || itemname.contains("ParticleCircle") || itemname.contains("HAT") || itemname.contains("Item"))) {
            config.set("items." + itemname + ".delay", 5);
        }
        try {
            config.save(ConfigurationManager.getFile("menu"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Added " + itemname + " to EULAGadgets!");
    }

    public  void setToConfig(String itemname, String name, String[] lore, Material material, int delay){
        if(config.contains("items." + itemname)){
            if(!config.contains("items." + itemname + ".enabled"))
                config.set("items." + itemname + ".enabled", true);
            return;
        }

        config.set("items." + itemname + ".name", name);
        config.set("items." + itemname + ".material", material.toString()/*.replaceAll("!! org.bukkit.material ", "")*/);
        config.set("items." + itemname + ".lore", lore);
        config.set("items." + itemname + ".permission", "EULAGadgets.VIP");
        config.set("items." + itemname + ".enabled", true);
        if(!(itemname.contains("ParticleTrail") || itemname.contains("ParticleCircle") || itemname.contains("HAT") || itemname.contains("Item"))) {
            config.set("items." + itemname + ".delay", delay);

        }
        try {
            config.save(ConfigurationManager.getFile("menu"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Added " + itemname + " to EULAGadgets!");
    }

    public void loadFromConfig(String itemname){
        names.put(itemname, config.getString("items." + itemname + ".name"));
        List<String> strings =  config.getStringList("items." + itemname + ".lore");

        lores.put(itemname, strings.toArray(new String[strings.size()]));
        materials.put(itemname, Material.matchMaterial(config.getString("items." + itemname + ".material")));

        permissions.put(itemname, config.getString("items." + itemname + ".permission"));
        enabled.put(itemname, config.getBoolean("items." + itemname + ".enabled"));
        if(!(itemname.contains("ParticleTrail") || itemname.contains("ParticleCircle") || itemname.contains("HAT") || itemname.contains("Item"))) {
            if (config.contains("items." + itemname + ".delay")) {
                delays.put(itemname, config.getInt("items." + itemname + ".delay"));
            } else {
                config.set("items." + itemname + ".delay", 5);

                try {
                    config.save(ConfigurationManager.getFile("menu"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                delays.put(itemname, 5);
            }
        }

    }

    public String getPermission(String itemname){
       return permissions.get(itemname);
    }

    public String getPermission(ItemStack itemStack){
        return getPermission(getItemName(itemStack));
    }

    public List<ItemStack> getItems() {
        List<ItemStack> items = new ArrayList<ItemStack>();

        Iterator it = names.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry) it.next();
            items.add(getItem((String) pairs.getKey()));
        }
        return items;
    }

    public String getItemName(ItemStack itemStack){
        return itemnames.get(itemStack);
    }
}
