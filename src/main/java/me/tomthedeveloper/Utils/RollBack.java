package me.tomthedeveloper.Utils;

import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Created by Tom on 10/09/2014.
 */
public class RollBack extends BukkitRunnable {

    private  static HashMap<Location, Material> blocks = new HashMap<Location, Material>();
    private static HashMap<Location, Byte> data = new HashMap<Location, Byte>();
    private static HashMap<Location, Integer> counters = new HashMap<Location, Integer>();
    private static Random random = new Random();
    private static List<Material> unpaintable = new ArrayList<Material>();

    public static  void loadupUnPaintableList(){
        unpaintable.add(Material.SIGN);
        unpaintable.add(Material.SIGN_POST);
        unpaintable.add(Material.WALL_SIGN);
        unpaintable.add(Material.SKULL);
        unpaintable.add(Material.SKULL_ITEM);
        unpaintable.add(Material.LAVA);
        unpaintable.add(Material.LADDER);
        unpaintable.add(Material.WATER);
    }


    @Override
    public void run() {
        List<Location> list = new ArrayList<Location>();
        for(Location location: blocks.keySet()){
            counters.put(location,counters.get(location) + 1);
            if(counters.get(location) >= 120){
                list.add(location);
                unPaintBlock(location);
                return;
            }
        }
       // for(Location location:list){
         //   unPaintBlock(location);
        //}
    }

    public static void paintBlock(Location location){

        if(unpaintable.contains(location.getBlock().getType()))
            return;
        if(blocks.containsKey(location))
            return;
        blocks.put(location, location.getBlock().getType());
        data.put(location, location.getBlock().getData());
        counters.put(location, 0);
        location.getBlock().setType(Material.STAINED_CLAY);

        location.getBlock().setData((byte) random.nextInt(15));
    }

    public static void unPaintBlock(Location location){
        location.getBlock().setType(blocks.get(location));
        location.getBlock().setData(data.get(location));
        blocks.remove(location);
        data.remove(location);
    }

    public static HashMap<Location, Material> getBlocks(){
        return blocks;
    }
}
