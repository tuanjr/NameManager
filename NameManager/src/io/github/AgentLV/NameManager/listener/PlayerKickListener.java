package io.github.AgentLV.NameManager.listener;

import io.github.AgentLV.NameManager.Commands;
import io.github.AgentLV.NameManager.NameManager;
import io.github.AgentLV.NameManager.Rainbow;
import io.github.AgentLV.NameManager.API.NameManagerAPI;
import io.github.AgentLV.NameManager.Files.ConfigAccessor;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.scoreboard.Team;

public class PlayerKickListener implements Listener {

	ConfigAccessor cConfig;
	
	public PlayerKickListener(NameManager plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		cConfig = NameManager.cConfig;
	}
	
	@EventHandler
	public void onPlayerKick(PlayerKickEvent e) {
		Player p = e.getPlayer();
		
		if(cConfig.getConfig().getBoolean("Messages")) {
			
			if(cConfig.getConfig().getBoolean("CustomNameForMessages")) {
				e.setLeaveMessage(ChatColor.translateAlternateColorCodes('&', cConfig.getConfig().getString("Leave").replaceAll("%player%", NameManagerAPI.getNametag(p))));
			} else {
				e.setLeaveMessage(ChatColor.translateAlternateColorCodes('&', cConfig.getConfig().getString("Leave").replaceAll("%player%", p.getName())));
			}
			
		}
		
		if (Commands.map.containsKey(p) || Commands.teams.contains(NameManager.board.getEntryTeam(p.getName()))) {
			
			Rainbow.disableRainbow(p);
			Team team = Commands.map.get(p);
			
			if (team != null) {
				team.removeEntry(p.getName());
			} else {
				NameManager.rainbow.removeEntry(p.getName());
			}
			
			Commands.map.remove(p);
		}
		
		if (NameManager.board.getEntryTeam(p.getName()) != null)
			NameManager.board.getEntryTeam(p.getName()).removeEntry(p.getName());
	}
	
	
}
