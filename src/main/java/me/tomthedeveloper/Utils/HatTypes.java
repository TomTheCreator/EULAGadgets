package me.tomthedeveloper.Utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Tom on 15/09/2014.
 */
public enum  HatTypes {


    HAT_ONE (Material.OBSIDIAN),
    HAT_TWO (Material.WOOL),
    HAT_THREE(Material.REDSTONE_BLOCK),
    HAT_FOUR(Material.LAPIS_BLOCK),
    HAT_FIVE(Material.DIAMOND_BLOCK),
    HAT_SIX(Material.IRON_BLOCK),
    HAT_SEVEN(Material.GOLD_BLOCK),
    HAT_EIGHT (Material.LOG),
    HAT_NINE(Material.NETHER_BRICK),
    HAT_TEN(Material.IRON_ORE),
    HAT_ELEVEN(Material.BEACON),
    HAT_TWELVE(Material.CACTUS),
    HAT_THIRTEEN(Material.COAL_BLOCK),
    HAT_FOURTEEN(Material.COAL_ORE),
    HAT_FIFTEEN(Material.DIAMOND_ORE),
    HAT_SIXTEEN(Material.CLAY),
    HAT_SEVENTEEN(Material.DISPENSER),
    HAT_EIGHTEEN(Material.CHEST),
    HAT_NINETEEN(Material.ENDER_CHEST),
    HAT_TWENTY(Material.GRASS),
    HAT_TWENTY_ONE(Material.WORKBENCH),
    HAT_TWENTY_TWO(Material.FURNACE),
    HAT_TWENTY_THREE(Material.BREWING_STAND),
    HAT_TWENTY_FOUR(Material.TNT),
    HAT_TWENTY_FIVE(Material.ENCHANTMENT_TABLE),
    HAT_TWENTY_SIX(Material.ENDER_PORTAL_FRAME),
    HAT_TWENTY_SEVEN(Material.ENDER_STONE),
    HAT_TWENTY_EIGHT(Material.EMERALD_BLOCK),
    HAT_TWENTY_NINE(Material.EMERALD_ORE),
    HAT_THIRTY(Material.PUMPKIN),
    HAT_THIRTY_ONE(Material.MELON_BLOCK),
    HAT_THIRTY_TWO(Material.WEB),
    HAT_THIRTY_THREE(Material.WOOD),
    HAT_THIRTY_FOUR(Material.HOPPER),
    HAT_THIRTY_FIVE(Material.DRAGON_EGG),
    HAT_THIRTY_SIX(Material.PISTON_BASE),
    HAT_THIRTY_SEVEN(Material.ICE);




    private  Material material;


    HatTypes(Material material){
        this.material = material;
    }

    public  ItemStack getItemStack(){
        return new ItemStack(material);

    }

    public Material getMaterial(){
        return material;
    }

    public String getName(){
        return material.toString().toLowerCase().replace("_", " ");
    }
}
