package fr.maxcraft.server.things;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * Created by admin on 14/02/16.
 */
public class Things {

    public static String message() {
        return ChatColor.GRAY+"";
    }

    public static String socialspyMessage(Player sender, Player p, String message){
        return ChatColor.GRAY+"["+ChatColor.GOLD+"Socialspy"+ChatColor.GRAY+"]"+ " "+sender.getName()+" --> "+p.getName()+" : "+message;
    }

    public static String alertMessage(){
        return ChatColor.RED+"["+ChatColor.GRAY+"!"+ChatColor.RED+"]"+ChatColor.GRAY+ChatColor.ITALIC+" ";
    }

}
