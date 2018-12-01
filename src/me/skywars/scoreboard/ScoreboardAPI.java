package me.skywars.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class ScoreboardAPI {

	private String title;
	private Scoreboard scoreboard;
	private int breakLines = 0;

	public ScoreboardAPI(String title) {
		this.title = title;
	}

	public void create() {
		scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
		Objective obj = scoreboard.registerNewObjective("score", "dummy");
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		obj.setDisplayName(this.title);
	}
	
	public void breakLine(Integer line) {
		String value = "§" + breakLines;
		Objective obj = scoreboard.getObjective("score");
		Score money = obj.getScore(value);
        money.setScore(line);
        breakLines+=1;
	}
	
	public void addLine(String teamName, String string, String suffix, String prefix, Integer line) {
		if (string == null)
			return;
		if (line < 0 || line == null)
			return;
		if (teamName != null) {
			Team team = scoreboard.registerNewTeam(teamName);
			team.addEntry(string);
			if (prefix != null)
				team.setPrefix(prefix);
			if (suffix != null)
				team.setSuffix(suffix);
			Objective obj = scoreboard.getObjective("score");
			obj.getScore(string).setScore(line);
		}
	}
	
	public void update(Player player, String teamName, String suffix) {
		Scoreboard score = player.getScoreboard();
		score.getTeam(teamName).setSuffix(suffix);
	}
	
	public void setScoreboard(Player player) {
		player.setScoreboard(scoreboard);
	}

}
