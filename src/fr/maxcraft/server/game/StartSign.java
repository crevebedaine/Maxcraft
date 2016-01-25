package fr.maxcraft.server.game;

import fr.maxcraft.Main;
import fr.maxcraft.player.User;
import fr.maxcraft.player.menu.Menu;
import fr.maxcraft.player.menu.game.Instance;
import fr.maxcraft.utils.MySQLSaver;
import org.bukkit.inventory.Inventory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Crevebedaine on 25/01/2016.
 */
public class StartSign {

    public static ArrayList<StartSign> sslist = new ArrayList<StartSign>();
    private final Game game;
    private final int nbrInstances;
    private ArrayList<GameInstance> instances = new ArrayList<GameInstance>();
    private final UUID npcUUID;
    private final boolean open;

    public StartSign(UUID pnj,int instances,boolean open,String name, String source, String entrance, int max,int life){
        this.npcUUID = pnj;
        this.open = open;
        this.game = new Game(name,source,entrance,max,life);
        this.nbrInstances = instances;
        for (int i = 1;i<=instances;i++)
            this.instances.add(new GameInstance(game,name+i));
        sslist.add(this);
    }

    public static void load(){
        ResultSet r = MySQLSaver.mysql_query("SELECT * FROM `startsign`;",false);
        try {
            while (r.next()){
                new StartSign(UUID.fromString(r.getString("uuid")),r.getInt("nbrInstances"),r.getBoolean("open"),r.getString("name")
                        ,r.getString("source"),r.getString("entrance"),r.getInt("max"),r.getInt("life"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Main.log("StartSigns chargés ("+sslist.size()+" StartSigns).");
    }

    public void clic(User u){
        Inventory i = Menu.getInventory("Instances");
        int j = 0;
        for (GameInstance g : instances) {
            i.setItem(j, new Instance(u, g).getItem(u));
            j++;
        }
        u.getPlayer().openInventory(i);
    }

    public UUID getNPCUUID(){
        return npcUUID;
    }

    public void save(){
        MySQLSaver.mysql_update("INSER INTO `startsign` (`uuid`, `nbrInstances`,`open`, `name`, `source`,  `entrance`, `max`, `life`) VALUES"
                + " ('"+this.npcUUID+"', "+this.nbrInstances+", "+this.open+", '"+this.game.getName()+"', '"+this.game.getSource()+"', '"+this.game.getEntrance()+", "+this.game.getMax()+", "+this.game.getLife()+"');");
    }
}
