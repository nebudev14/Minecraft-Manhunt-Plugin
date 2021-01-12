package manhuntv2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener{
	
	@Override
	public void onEnable() {
		this.getServer().getPluginManager().registerEvents(this, this);
	}
	
	@Override
	public void onDisable() {
		
	}
	
	
	List<Player> p = new ArrayList<Player>();
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(label.equalsIgnoreCase("runner")) {
			if(!(sender instanceof Player)) {
				sender.sendMessage("bad, cant use this");
				return true;
			}
			Player s = (Player) sender;
			for(Player i : s.getWorld().getPlayers()) {
				if(i.getDisplayName().equals(args[0])) {
					p.add(i);
				}
			}
			for(Player i : p) {
				s.sendMessage(i.getDisplayName());
			}
		}
		return false;
	}
	
	@EventHandler
	public void updateCompass(PlayerMoveEvent event) {
		Player player = (Player) event.getPlayer();
		if(p != null && p.contains(player)) { // as long as runner list isnt empty
			for(Player x : player.getWorld().getPlayers()) { // for every player in world
				if(!(p.contains(x))) { // as long as the player isnt a runner
					List<Double> distance = new ArrayList<Double>(); // distance list
					for(Player y : p) { // for every runner in runner list
						distance.add(x.getLocation().distance(y.getLocation())); // add the location of runner from hunter
					}
					int small = distance.indexOf(Collections.min(distance));
					x.setCompassTarget(p.get(small).getLocation());
				}
			}
		}
	}
}
