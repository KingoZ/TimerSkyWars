package me.skywars.group;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import me.skywars.Main;

public class GroupManager {
	
	public static void onJoin(Player player) {
		Groups group = Groups.getGroup(player);
		Scoreboard s = player.getScoreboard();
		player.setDisplayName(group.getTag() + player.getName());
		
		for (Player players : Main.getInstance().getOnlinePlayers()) {
			Scoreboard sb = players.getScoreboard();
			Team team = sb.getTeam(group.getTeamName());
			if (team == null) {
				team = sb.registerNewTeam(group.getTeamName());
				team.setPrefix(group.getTag());
			}

			team.addEntry(player.getName());

			if (player.equals(players)) {
				continue;
			}

			Groups g2 = Groups.getGroup(players);
			team = s.getTeam(g2.getTeamName());
			if (team == null) {
				team = s.registerNewTeam(g2.getTeamName());
				team.setPrefix(g2.getTag());
			}

			team.addEntry(players.getName());
		}
	}
	
	public static void setTag(Player player, Groups groups) {
		Groups group = groups;
		Scoreboard s = player.getScoreboard();
		player.setDisplayName(group.getTag() + player.getName());

		for (Player players : Main.getInstance().getOnlinePlayers()) {
			Scoreboard sb = players.getScoreboard();

			Team team = sb.getTeam(group.getTeamName());
			if (team == null) {
				team = sb.registerNewTeam(group.getTeamName());
				team.setPrefix(group.getTag());
			}

			team.addEntry(player.getName());

			if (player.equals(players)) {
				continue;
			}

			Groups g2 = Groups.getGroup(players);
			team = s.getTeam(g2.getTeamName());
			if (team == null) {
				team = s.registerNewTeam(g2.getTeamName());
				team.setPrefix(g2.getTag());
			}

			team.addEntry(players.getName());
		}
	}

	public static void onQuit(Player player) {
		Groups group = Groups.getGroup(player);

		Scoreboard sb = player.getScoreboard();
		Team team = sb.getTeam(group.getTeamName());
		if (team != null && team.hasEntry(player.getName())) {
			team.removeEntry(player.getName());
		}
	}

}
