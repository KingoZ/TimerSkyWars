package me.skywars.kit.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import me.skywars.Main;
import me.skywars.game.GameState;

public class KingHell implements Listener {
	
	@EventHandler
	public void damage(EntityDamageEvent e) {
		if (!Main.getInstance().checkState(GameState.JOGO))
			return;
		if ((e.getEntity() instanceof Player)) {
			Player p = (Player) e.getEntity();
			if ((Main.getGameManager().getHabilidade().getName(p).equals("KingHell")) && ((e.getCause() == EntityDamageEvent.DamageCause.LAVA) || (e.getCause() == EntityDamageEvent.DamageCause.FIRE) || (e.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK))) e.setCancelled(true);
		}
	}

}
