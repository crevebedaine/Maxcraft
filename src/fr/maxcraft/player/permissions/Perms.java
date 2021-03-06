package fr.maxcraft.player.permissions;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

import fr.maxcraft.Main;
import fr.maxcraft.player.User;
import fr.maxcraft.utils.MySQLSaver;
import fr.maxcraft.utils.Serialize;
import org.bukkit.ChatColor;

public class Perms {

	
	private Group group;
	public ArrayList<String> perms;
	private UUID uuid;

	public Perms(UUID uuid,String group, ArrayList<String> arrayList){
		try {
			this.uuid = uuid;
			this.perms = arrayList;
			this.group = (Group) Class.forName("fr.maxcraft.player.permissions.groups."+group).getConstructor().newInstance();
			for (String p : this.group.getPermissions())
				this.perms.add(p);
		
	} catch (Exception e) {
		e.printStackTrace();
		Main.logError("Erreur au chargement des perms");
	}
	}

	public static Perms load(UUID uuid) {
		ResultSet r = MySQLSaver.mysql_query("SELECT * FROM `perms` WHERE `perms`.`id` = '"+uuid.toString()+"'",true);
		try {
			assert r != null;
			if (!r.isFirst())
				return new Perms(uuid,"Citoyen",Serialize.ArrayStringFromString(""));
		return new Perms(uuid,r.getString("group"),Serialize.ArrayStringFromString(r.getString("perms")));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new Perms(uuid,"Citoyen",Serialize.ArrayStringFromString(""));
	}

	public Group getGroup() {
		return group;
	}

    public ArrayList<String> getPerms() {
        return perms;
    }

    public void setGroup(Group group) {
		this.group = group;
	}
	
	public boolean hasPerms(String s){
        if (User.get(this.uuid).getPlayer().isOp())
            return true;
		if( this.perms.contains(s))
			return true;
		return false;
	}
	
	public String dysplayName(){
        return this.getGroup().getColor()+""+ChatColor.BOLD+this.getGroup().getPrefix()+ChatColor.RESET+this.getGroup().getColor()+User.get(this.uuid).getName();
	}

    public void save() {
        MySQLSaver.mysql_update("UPDATE `perms` SET `group` = '"+group.getClass().getName()+"' , `perms` = '"+Serialize.ArrayStringToString(this.getPerms())+"' " +
                " WHERE 'id' = '"+uuid+"'");

    }
}
