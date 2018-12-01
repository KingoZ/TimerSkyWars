package me.skywars.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import me.skywars.Main;
import me.skywars.group.GroupManager;

public class LeaveEvent implements Listener {
	
	@EventHandler
	public void onJoin(PlayerQuitEvent e) {
		e.setQuitMessage(null);
		Player player = e.getPlayer();
		GroupManager.onQuit(player);
		Main.getGameManager().onQuit(player);
	}

}
