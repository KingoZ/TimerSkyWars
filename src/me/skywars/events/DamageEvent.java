package me.skywars.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import me.skywars.Main;
import me.skywars.game.GameState;

public class DamageEvent implements Listener {
	
	@EventHandler
	public void onDamageEvent(EntityDamageEvent e) {
		if (Main.getInstance().checkState(GameState.LOBBY) || Main.getInstance().checkState(GameState.PREPARAR)) {
			e.setCancelled(true);
		} else if (Main.getInstance().checkState(GameState.JOGO)) {
			if (e.getEntity() instanceof Player)
				if (Main.getGameManager().getEspectadores().contains(((Player)e.getEntity()).getUniqueId())) {
					e.setCancelled(true);
				}
		}
	}
	
	@EventHandler
	public void onDamageEvent(EntityDamageByEntityEvent e) {
		if ((e.getEntity() instanceof Player) && (e.getDamager() instanceof Player)) {
			if (Main.getInstance().checkState(GameState.LOBBY) || Main.getInstance().checkState(GameState.PREPARAR)) {
				e.setCancelled(true);
			} else if (Main.getInstance().checkState(GameState.JOGO)) {
				if (Main.getGameManager().getEspectadores().contains(((Player)e.getEntity()).getUniqueId()) || Main.getGameManager().getEspectadores().contains(((Player)e.getDamager()).getUniqueId())) {
					e.setCancelled(true);
				}
			}
		}
	}

}
