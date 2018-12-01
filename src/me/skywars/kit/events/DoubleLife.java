package me.skywars.kit.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerRespawnEvent;

import me.skywars.Main;
import me.skywars.game.GameState;
import me.skywars.scoreboard.Scoreboarding;

public class DoubleLife implements Listener {
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onRespawn(PlayerRespawnEvent e) {
		Player player = e.getPlayer();
		if (Main.getGameManager().getHabilidade().getName(player).equals("Fênix")) {
			System.out.println("Fênix");
			Main.getGameManager().getHabilidade().remove(player);
			Scoreboarding.updateHabilidade(player);
			player.teleport(Main.getGameManager().getGamePlayerToSpawnPoint().get(player.getUniqueId()));
		}
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onDamageEvent(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player) {
			if (Main.getInstance().checkState(GameState.JOGO)) {
				if (e.getCause() == DamageCause.VOID) {
					Player player = (Player)e.getEntity();
					if (Main.getGameManager().getHabilidade().getName(player).equals("Fênix")) {
						System.out.println("Fênix");
						Main.getGameManager().getHabilidade().remove(player);
						Scoreboarding.updateHabilidade(player);
						player.teleport(Main.getGameManager().getGamePlayerToSpawnPoint().get(player.getUniqueId()));
					}
				}
				e.setCancelled(true);
			}
		}
	}

}
