package me.tomthedeveloper.Utils;

import org.bukkit.*;
import sun.io.CharToByteASCII;


/**
 * Created by Tom on 10/09/2014.
 */
public enum FireworkTypes {

    RED_GREEN_BALL (Color.RED,  ChatColor.RED + "Red", Color.GREEN, ChatColor.GREEN + "Green", FireworkEffect.Type.BALL,  "Red_Green_Ball"),
    RED_YELLOW_BALL(Color.RED, ChatColor.RED + "Red", Color.YELLOW, ChatColor.YELLOW + "Yellow", FireworkEffect.Type.BALL, "Red_Yellow_Ball"),
    RED_PURPLE_BAL(Color.RED, ChatColor.RED + "Red", Color.PURPLE, ChatColor.DARK_PURPLE + "Purple", FireworkEffect.Type.BALL, "Red_Purple_Ball"),
    GREEN_YELLOW_BALL(Color.GREEN, ChatColor.GREEN + "Green", Color.YELLOW, ChatColor.YELLOW + "Yellow", FireworkEffect.Type.BALL, "Green_Yellow_Ball"),
    GREEN_GREEN_BALL(Color.GREEN, ChatColor.GREEN + "Green", Color.GREEN, ChatColor.GREEN + "Green", FireworkEffect.Type.BALL, "Green_Green_Ball"),
    RED_RED_BALL(Color.RED , ChatColor.RED + "Red", Color.RED, ChatColor.RED + "Red", FireworkEffect.Type.BALL, "Red_Red_Ball"),
    BLACK_GREEN_BALL(Color.BLACK, ChatColor.BLACK + "Black", Color.GREEN, ChatColor.GREEN + "Green", FireworkEffect.Type.BALL, "Black_Green_Ball"),
    AQUA_YELLOW_BALL(Color.AQUA, ChatColor.AQUA + "Aqua", Color.YELLOW, ChatColor.YELLOW + "Yellow", FireworkEffect.Type.BALL, "Aqua_Yellow_Ball"),
   // AQUA_AQUA_BALL(Color.AQUA, ChatColor.AQUA + "Aqua", Color.AQUA , ChatColor.AQUA  +"Aqua", FireworkEffect.Type.BALL, "Aqua_Aqua_Ball"),

    RED_YELLOW_CREEPER(Color.RED, ChatColor.RED + "Red", Color.YELLOW, ChatColor.YELLOW + "Yellow", FireworkEffect.Type.CREEPER, "Red_Yellow_Creeper"),
    BLACK_BLACK_CREEPER(Color.BLACK, ChatColor.BLACK + "Black",Color.BLACK, ChatColor.BLACK + "Black",FireworkEffect.Type.CREEPER, "Black_Black_Creeper"),
    YELLOW_YELLOW_CREEPER(Color.YELLOW, ChatColor.YELLOW + "Yellow",Color.YELLOW, ChatColor.YELLOW + "Yellow",FireworkEffect.Type.CREEPER, "Yellow_Yellow_Creeper"),
    PURPLE_PURPLE_CREEPER(Color.PURPLE, ChatColor.DARK_PURPLE + "Purple",Color.PURPLE, ChatColor.DARK_PURPLE + "Purple",FireworkEffect.Type.CREEPER, "Purple_Purple_Creeper"),
    GREEN_YELLOW_CREEPER (Color.GREEN, ChatColor.GREEN + "Green", Color.YELLOW, ChatColor.YELLOW + "Yellow", FireworkEffect.Type.CREEPER, "Green_Yellow_Creeper"),
    AQUA_AQUA_CREEPER(Color.AQUA, ChatColor.AQUA + "Aqua", Color.AQUA, ChatColor.AQUA + "Aqua", FireworkEffect.Type.CREEPER, "Aqua_Aqua_Creeper"),
    WHITE_WHITE_CREEPER(Color.WHITE, ChatColor.WHITE + "White", Color.WHITE, ChatColor.WHITE + "White", FireworkEffect.Type.CREEPER, "White_White_Creeper"),
    GREEN_WHITE_CREEPER(Color.GREEN, ChatColor.GREEN + "Green", Color.WHITE, ChatColor.WHITE + "White", FireworkEffect.Type.CREEPER, "Green_White_Creeper"),
    GREEN_GRAY_CREEPER(Color.GREEN, ChatColor.GREEN + "Green", Color.GRAY, ChatColor.GRAY + "Gray", FireworkEffect.Type.CREEPER, "Green_Gray_Creeper"),

    BLUE_YELLOW_STAR(Color.BLUE, ChatColor.BLUE + "Blue", Color.YELLOW , ChatColor.YELLOW + "Yellow", FireworkEffect.Type.STAR, "Blue_Yellow_Star"),
    BLUE_RED_STAR(Color.BLUE, ChatColor.BLUE + "Blue", Color.YELLOW, ChatColor.RED +  "Red", FireworkEffect.Type.STAR, "Blue_Red_Star"),
    BLACK_YELLOW_STAR(Color.BLACK, ChatColor.BLACK + "Black", Color.YELLOW, ChatColor.YELLOW + "Yellow", FireworkEffect.Type.STAR, "Black_Yellow_Star"),
    AQUA_GREEN_STAR(Color.AQUA, ChatColor.AQUA + "Aqua", Color.GREEN, ChatColor.GREEN + "Green", FireworkEffect.Type.STAR, "Aqua_Green_Star"),
    AQUA_BLUE_STAR(Color.AQUA, ChatColor.AQUA + "Aqua", Color.BLUE, ChatColor.BLUE + "Bleu", FireworkEffect.Type.STAR, "Aqua_Blue_Star"),
    YELLOW_YELLOW_STAR(Color.YELLOW, ChatColor.YELLOW + "Yellow", Color.YELLOW, ChatColor.YELLOW + "Yellow", FireworkEffect.Type.STAR, "Yellow_Yellow_Star"),
    YELLOW_AQUA_STAR(Color.YELLOW, ChatColor.YELLOW + "Yellow", Color.AQUA, ChatColor.AQUA + "Aqua", FireworkEffect.Type.STAR, "Yellow_Aqua_Star"),
    GRAY_GRAY_STAR(Color.GRAY, ChatColor.GRAY + "Gray", Color.GRAY, ChatColor.GRAY + "Gray", FireworkEffect.Type.STAR, "Gray_Gray_Star"),
    YELLOW_WHITE_STAR(Color.YELLOW, ChatColor.YELLOW + "Yellow", Color.WHITE, ChatColor.WHITE + "White", FireworkEffect.Type.STAR, "Yellow_White_Star"),

    YELLOW_AQUA_BURST(Color.YELLOW, ChatColor.YELLOW + "Yellow", Color.AQUA, ChatColor.AQUA +"Aqua", FireworkEffect.Type.BURST, "Yellow_Aqua_Burst"),
    RED_AQUA_BURST(Color.RED, ChatColor.RED + "Red", Color.AQUA, ChatColor.AQUA + "Aqua", FireworkEffect.Type.BURST, "Red_Aqua_Burst"),
    RED_BLUE_BURST(Color.RED, ChatColor.RED + "Red", Color.BLUE, ChatColor.BLUE + "Blue", FireworkEffect.Type.BURST, "Red_Blue_Burst"),
    PURPLE_AQUA_BURST(Color.PURPLE, ChatColor.DARK_PURPLE + "Purple", Color.AQUA, ChatColor.AQUA +"Aqua", FireworkEffect.Type.BURST, "Purple_Aqua_Burst"),
    PURPLE_PURPLE_BURST(Color.PURPLE, ChatColor.DARK_PURPLE + "Purple",Color.PURPLE, ChatColor.DARK_PURPLE + "Purple", FireworkEffect.Type.BURST, "Purple_Purple_Burst"),
    GREEN_GREEN_BURST(Color.GREEN, ChatColor.GREEN + "Green", Color.GREEN, ChatColor.GREEN + "Green", FireworkEffect.Type.BURST, "Green_Green_Burst"),
    RED_GREEN_BURST(Color.RED, ChatColor.RED + "Red", Color.GREEN, ChatColor.GREEN + "Green", FireworkEffect.Type.BURST, "Red_Green_Burst"),
    YELLOW_YELLOW_BURST(Color.YELLOW, ChatColor.YELLOW + "Yellow", Color.YELLOW, ChatColor.YELLOW + "Yellow", FireworkEffect.Type.BURST, "Yellow_Yellow_Burst"),
    AQUA_AQUA_BURST(Color.AQUA, ChatColor.AQUA + "Aqua", Color.AQUA, ChatColor.AQUA + "Aqua", FireworkEffect.Type.BURST, "Aqua_Aqua_Burst");



    private Color color, fade;
    private FireworkEffect.Type  type;
    private String name, colorname, fadename;


    public static FireworkTypes fromName(String name){
        for(FireworkTypes fireworkTypes:values()){
            if(name.contains(fireworkTypes.getName()))
                return fireworkTypes;
        }
        return RED_GREEN_BALL;
    }

    FireworkTypes(Color color, String colorname, Color fade, String fadename, FireworkEffect.Type type, String name){
       this.color = color;
        this.fade = fade;
        this.type = type;
        this.name = name;
        this.colorname = colorname;
        this.fadename = fadename;
    }

    public Color getColor() {
        return color;

    }

    public String getColorName() {
        return colorname;
    }

    public String getFadeName() {
        return fadename;
    }

    public static FireworkTypes[] getValues(){
        return values();
    }

    public Color getFade() {
        return fade;
    }

    public FireworkEffect.Type getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
