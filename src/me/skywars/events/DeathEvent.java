package me.skywars.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.scheduler.BukkitRunnable;

import me.skywars.Main;
import me.skywars.game.GameState;
import me.skywars.scoreboard.Scoreboarding;

public class DeathEvent implements Listener {
	
	@SuppressWarnings("unlikely-arg-type")
	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		e.setDeathMessage(null);
		if (Main.getInstance().checkState(GameState.JOGO)) {
			if (e.getEntity() instanceof Player) {
				Player morreu = (Player) e.getEntity();
				String cause = "";
				
				if (e.getEntity().getKiller() instanceof Player) {
					Player matou = (Player) e.getEntity().getKiller();
					cause = "foi morto pelo(a) §7" + matou.getName();
					Main.getGameManager().getKills().put(matou.getUniqueId(), Main.getGameManager().getKills().get(matou.getUniqueId()) + 1);
					Scoreboarding.updateKills(matou);
					deathMessage(morreu, cause, e);
				} else if (e.getEntity().getLastDamageCause().equals(DamageCause.BLOCK_EXPLOSION)) {
					cause = "morreu explodido";
					deathMessage(morreu, cause, e);
				} else if (e.getEntity().getLastDamageCause().equals(DamageCause.DROWNING)) {
					cause = "morreu afocado";
					deathMessage(morreu, cause, e);
				}else if (e.getEntity().getLastDamageCause().equals(DamageCause.FALL)) {
					cause = "morreu caindo de um lugar alto";
					deathMessage(morreu, cause, e);
				} else if (e.getEntity().getLastDamageCause().equals(DamageCause.FIRE) || e.getEntity().getLastDamageCause().equals(DamageCause.FIRE_TICK)) {
					cause = "morreu mexendo com fogo";
					deathMessage(morreu, cause, e);
				} else if (e.getEntity().getLastDamageCause().equals(DamageCause.LAVA)) {
					cause = "morreu na lava";
					deathMessage(morreu, cause, e);
				} else if (e.getEntity().getLastDamageCause().equals(DamageCause.MAGIC)) {
					cause = "Morreu para a magia";
					deathMessage(morreu, cause, e);
				} else if (e.getEntity().getLastDamageCause().equals(DamageCause.POISON)) {
					cause = "Morreu envenenado";
					deathMessage(morreu, cause, e);
				} else if (e.getEntity().getLastDamageCause().equals(DamageCause.PROJECTILE)) {
					cause = "morreu por causa de um projetil";
					deathMessage(morreu, cause, e);
				} else if (e.getEntity().getLastDamageCause().equals(DamageCause.SUFFOCATION)) {
					cause = "morreu sufocado";
					deathMessage(morreu, cause, e);
				}
				
				if (Main.getGameManager().getJogadores().contains(morreu.getUniqueId()) && !Main.getGameManager().getHabilidade().getName(morreu).equals("DoubleLife")) {
					new BukkitRunnable() {
						
						@Override
						public void run() {
							Main.getGameManager().getJogadores().remove(morreu.getUniqueId());
							Main.getGameManager().checkWinner();
							morreu.spigot().respawn();
						}
					}.runTaskLater(Main.getInstance(), 1L);
				}
			}
		}
	}
	
	@EventHandler
	public void onRespawn(PlayerRespawnEvent e) {
		Player player = e.getPlayer();
		if (!Main.getGameManager().getJogadores().contains(player.getUniqueId())) {
			Main.getGameManager().switchEspectador(player);
		}
	}
	
	@EventHandler
	public void onDamageEvent(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player) {
			if (Main.getInstance().checkState(GameState.JOGO)) {
				if (e.getCause() == DamageCause.VOID) {
					Player player = (Player)e.getEntity();
					player.teleport(Main.getGameManager().getFeast());
					if (!CombatEvent.inCombat(player)) {
						Bukkit.broadcastMessage("§7" + player.getName() + "§ecaiu no void.");
					} else {
						Player matou = CombatEvent.getPlayer(player);
						Main.getGameManager().getKills().put(matou.getUniqueId(), Main.getGameManager().getKills().get(matou.getUniqueId()) + 1);
						Scoreboarding.updateKills(matou);
						Bukkit.broadcastMessage("§7" + player.getName() + "§e caiu no void por causa do(a) §7"+matou.getName()+"§e.");
					}
					if (Main.getGameManager().getJogadores().contains(player.getUniqueId()) && !Main.getGameManager().getHabilidade().getName(player).equals("DoubleLife")) {
						new BukkitRunnable() {
							
							@Override
							public void run() {
								Main.getGameManager().switchEspectador(player);
								Main.getGameManager().checkWinner();
								player.spigot().respawn();
							}
						}.runTaskLater(Main.getInstance(), 1L);
					}
				}
				e.setCancelled(true);
			}
		}
	}
	
	public void deathMessage(Player player, String cause, PlayerDeathEvent e) {
		if ((e.getEntity().getKiller() instanceof Player)) {
			Bukkit.broadcastMessage("§7" + player.getName() + "§e" + cause.toLowerCase() + "§e.");
		} else {
			if (!CombatEvent.inCombat(player)) {
				Bukkit.broadcastMessage("§7" + player.getName() + "§e" + cause.toLowerCase() + "§e.");
			} else {
				Player matou = CombatEvent.getPlayer(player);
				Main.getGameManager().getKills().put(matou.getUniqueId(), Main.getGameManager().getKills().get(matou.getUniqueId()) + 1);
				Scoreboarding.updateKills(matou);
				Bukkit.broadcastMessage("§7" + player.getName() + "§e" + cause.toLowerCase() + " por causa do(a) §7"+matou.getName()+"§e.");
			}
		}
	}

}
