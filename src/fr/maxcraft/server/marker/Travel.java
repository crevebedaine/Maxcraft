package fr.maxcraft.server.marker;

import fr.maxcraft.Main;
import fr.maxcraft.utils.MySQLSaver;
import fr.maxcraft.utils.Serialize;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Crevebedaine on 08/02/2016.
 */
public class Travel {

    private final Marker m1;
    private final Marker temp;
    private final Marker m2;
    public static ArrayList<Travel> travelslist = new ArrayList<Travel>();

    public Travel(Marker m1, Marker temp, Marker m2){
        this.m1 = m1;
        this.temp = temp;
        this.m2 = m2;
        travelslist.add(this);
    }

    public boolean contains(Marker m){
        if (m1.equals(m)||m2.equals(m)||temp.equals(m))
            return true;
        return false;
    }

    public static void load() {
        ResultSet r = MySQLSaver.mysql_query("SELECT * FROM  `travel` ",false);
        try {
            while (r.next())
                new Travel(Marker.getMarker(r.getString("marker1")),Marker.getMarker(r.getString("tempmarker")),Marker.getMarker(r.getString("marker2")));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insert(){
        MySQLSaver.mysql_update("INSERT INTO `travel` (`marker1`, `tempmarker`, `marker2`) VALUES ('"+this.m1.getName()+"', '"+this.temp.getName()+"', '"+this.m2.getName()+"');");
    }

    public void remove(){
        travelslist.remove(this);
        MySQLSaver.mysql_update("DELETE FROM `travel` WHERE `marker1`= "+this.m1.getName());
    }
    public boolean isReadyToTravel(Player p){
        if (p.getLocation().distance(m1)<5||p.getLocation().distance(m2)<5)
            return true;
        return false;
    }

    public void travel(Player p){
        p.sendMessage(ChatColor.GRAY+"Patientez ici quelques secondes le temps de larguer les ammarres");
        if (p.getLocation().distance(m1)<5)
            new Task(m1,temp,m2,p).runTaskLater(Main.getPlugin(),100);
        if (p.getLocation().distance(m2)<5)
            new Task(m2,temp,m1,p).runTaskLater(Main.getPlugin(),100);
    }

    public class Task extends BukkitRunnable{

        private final Marker temp;
        private final Player p;
        private final Marker to;
        private final Marker from;

        public Task(Marker from,Marker temp, Marker to, Player p){
            this.from = from;
            this.temp = temp;
            this.p = p;
            this.to = to;
        }

        @Override
        public void run() {
            if (p.getLocation().distance(from)<5) {
                p.teleport(temp);
                p.sendMessage(ChatColor.GRAY + "Profitez du voyage pour admirer la vue !");
                new Task(from,temp,to,p).runTaskLater(Main.getPlugin(),600);
            }
            if (p.getLocation().distance(temp)<20){
                p.teleport(to);
                p.sendMessage(ChatColor.GRAY + "Vous pouvez descendre du bateau, allez y avant qu'il ne reparte !");
            }
        }
    }


}
