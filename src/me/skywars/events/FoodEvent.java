package me.skywars.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

import me.skywars.Main;
import me.skywars.game.GameState;

public class FoodEvent implements Listener {
	
	@EventHandler
	public void onFood(FoodLevelChangeEvent e) {
		if (Main.getInstance().checkState(GameState.LOBBY) || Main.getInstance().checkState(GameState.PREPARAR)) {
			e.setCancelled(true);
			e.setFoodLevel(20);
		} else {
			Player p = (Player)e.getEntity();
			if (Main.getGameManager().getEspectadores().contains(p.getUniqueId())) {
				e.setCancelled(true);
				return;
			}
			p.setSaturation(4.2F);
		}
	}

}
