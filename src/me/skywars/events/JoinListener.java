package me.skywars.events;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import me.skywars.Main;
import me.skywars.game.GameState;
import me.skywars.group.Groups;
import me.skywars.nms.PacketReader;

public class JoinListener implements Listener {
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		e.setJoinMessage(null);
		Player player = e.getPlayer();
		PacketReader pR = new PacketReader(player);
		pR.inject();
		player.setFireTicks(0);
		player.setAllowFlight(false);
		player.setFlying(false);
		player.setGameMode(GameMode.SURVIVAL);
		player.setExp(0);
		player.setFoodLevel(20);
		player.setHealth(player.getMaxHealth());
		player.updateInventory();
		Main.getGameManager().getHabilidade().set(player, "Nenhum");
		for (Player players : Main.getInstance().getOnlinePlayers()) {
			if (Main.getGameManager().getEspectadores().contains(player.getUniqueId())) {
				// Hide the player(spectador) from player(join)
				players.hidePlayer(player);
			}
		}
		if (Main.getInstance().checkState(GameState.LOBBY)) {
			player.getInventory().clear();
			player.getInventory().setArmorContents(null);
			Main.getGameManager().onJoin(player);
		} else {
			Main.getGameManager().switchEspectador(player);
		}
		onJoin(player);
	}
	
	public void onJoin(Player player) {
		Groups group = Groups.getGroup(player);
		setGroup(player, group);
	}
	
	@SuppressWarnings("deprecation")
	public void setGroup(Player player, Groups groups) {
		Scoreboard scoreboard = player.getScoreboard();
		Groups group = groups;
		player.setDisplayName(group.getTag() + player.getName());
		
		Team team = null;
		if (scoreboard.getTeam(group.getTeamName()) == null) {
			team = scoreboard.registerNewTeam(group.getTeamName());
		} else {
			team = scoreboard.getTeam(group.getTeamName());
		}
		team.setPrefix(group.getTag());
		team.addPlayer(player);
	}

}
