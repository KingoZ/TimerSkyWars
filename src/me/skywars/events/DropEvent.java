package me.skywars.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

import me.skywars.Main;
import me.skywars.game.GameState;

public class DropEvent implements Listener {
	
	@EventHandler
	public void onDrop(PlayerDropItemEvent e) {
		Player player = e.getPlayer();
		if (Main.getInstance().checkState(GameState.LOBBY)) {
			e.setCancelled(true);
			player.updateInventory();
		} else if (Main.getInstance().checkState(GameState.JOGO)) {
			if (Main.getGameManager().getEspectadores().contains(player.getUniqueId())) {
				e.setCancelled(true);
				player.updateInventory();
			}
		}
	}

}
