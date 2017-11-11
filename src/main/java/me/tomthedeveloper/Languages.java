package me.tomthedeveloper;

import me.tomthedeveloper.managers.ConfigurationManager;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by Tom on 9/09/2014.
 */
public class Languages {

    private static HashMap<String, String>  messages = new HashMap<String, String>();


    private static FileConfiguration config;

    public Languages(){
       config = ConfigurationManager.getConfig("language");
    }


    public void setup(){

    }

    public static String getMessage(String ID, String defaultmessage){
        if(messages.containsKey(ID)){
            return formatMessage(messages.get(ID));
        }else{
            config.set(ID, defaultmessage);
            try {
                config.save(ConfigurationManager.getFile("language"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            messages.put(ID, defaultmessage);
            return messages.get(ID);
        }
    }

    private static String formatMessage(String s){
        return s.replaceAll("(&([a-f0-9]))", "\u00A7$2");
    }


    public void loadMessages(){
        for(String path: config.getKeys(false)){
            ;
            String string = config.getString(path);
            messages.put(path, string);
        }

    }

    public static String getMessage(String ID){
        return messages.get(ID);
    }
}
