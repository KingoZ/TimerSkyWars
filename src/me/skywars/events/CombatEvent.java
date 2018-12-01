package me.skywars.events;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import me.skywars.Main;
import me.skywars.game.GameState;

public class CombatEvent {
	
	public static HashMap<UUID, Player> combat = new HashMap<>();

	public static boolean inCombat(Player player) {
		return combat.containsKey(player.getUniqueId());
	}
	
	public static Player getPlayer(Player player) {
		return combat.get(player.getUniqueId());
	}

	public static void setCombat(Player player, Player hitted) {
		combat.put(player.getUniqueId(), hitted);
		combat.put(hitted.getUniqueId(), player);
	}

	public static void removeCombat(Player player) {
		Player hitted = combat.get(player.getUniqueId());
		combat.remove(player.getUniqueId());
		combat.remove(hitted.getUniqueId());
	}

	@EventHandler
	public void entityDamageByEntity(EntityDamageByEntityEvent event) {
		if (!(event.getEntity() instanceof Player) || (!(event.getDamager() instanceof Player))) {
			return;
		}
		Player hitter = (Player) event.getDamager();
		Player hitted = (Player) event.getEntity();
		if (Main.getInstance().checkState(GameState.JOGO))
			return;
		if (!(Main.getGameManager().getJogadores().contains(hitted.getUniqueId()) && Main.getGameManager().getJogadores().contains(hitter.getUniqueId())))
			return;
		if (!inCombat(hitted) && !inCombat(hitter)) {
			setCombat(hitter, hitted);

			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
				public void run() {
					if (inCombat(hitted) || inCombat(hitter)) {
						removeCombat(hitter);
					}
				}
			}, 200L);
		}
	}

}
