package fr.maxcraft.server.command;

import fr.maxcraft.Main;
import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.List;


public abstract class Command extends org.bukkit.command.Command{

    private HashMap<String,List<String>> completer = new HashMap<String,List<String>>();

    protected Command(String name) {
		super(name);
		super.setName(name);
	}

	public void register() {
		Main.getCmap().register(Main.getPlugin().getDescription().getName(), this);
		Main.log("Command "+this.getName()+" registered");
	}
	public Command setAliases(List<String> s) {
		super.setAliases(s);
		return this;
	}
	public Command setPerms(String s) {
		super.setPermission(s);
		return this;
	}

    public void tabComplete(String lastargs, List<String> als){
        this.completer.put(lastargs,als);
    }
	@Override
	public abstract boolean execute(CommandSender sender, String cmd, String[] args);

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        if (args.length==1&&this.completer.get(this.getName())!=null)
            return this.completer.get(this.getName());
        if (this.completer.containsKey(args[args.length-1]))
            return this.completer.get(args[args.length-1]);
        return super.tabComplete(sender,alias,args);
    }
}
