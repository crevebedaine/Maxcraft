package fr.maxcraft.player.moderation;

import fr.maxcraft.Main;
import fr.maxcraft.player.User;
import fr.maxcraft.server.world.marker.Marker;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.Date;

/**
 * Created by Lu27600 on 07/02/16.
 */
public class ModerationListener implements Listener {

    public ModerationListener(Main main) {
        main.getServer().getPluginManager().registerEvents(this, main);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerAttempsToJoin(PlayerLoginEvent e){
        User u = User.get(e.getPlayer());
        if (!u.getModeration().isBan()) {
            e.allow();
            return;
        }
        if (!(u.getModeration().getBanend() < new Date().getTime())){
            u.getModeration().setBan(false, -1);
            e.allow();
            return;
        }
        e.disallow(e.getResult(), u.getModeration().getBanReason());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onMutedPlayerChat(AsyncPlayerChatEvent e){
        User u = User.get(e.getPlayer());
        if (!u.getModeration().isMute()) return;
        e.setCancelled(true);
        u.sendNotifMessage(ChatColor.RED+"Vous êtes muet, vous ne pouvez pas parler !");
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerMoveEvent(PlayerMoveEvent e){
        User u = User.get(e.getPlayer());
        if (!u.getModeration().isJail()) return;
        if ( !(u.getModeration().getJailend() < new Date().getTime())){
            u.getModeration().setJail(false, -1);
            u.getPlayer().teleport(Marker.getMarker("spawn"));
            u.sendNotifMessage(ChatColor.GREEN+"Vous avez été libéré, le temps de votre peine s'est écoulé!");
            return;
        }
        return;
    }
}
