package me.skywars.scoreboard;

import org.bukkit.entity.Player;

import me.skywars.Main;
import me.skywars.game.GameManager.Modes;
import me.skywars.game.GameState;
import me.skywars.manager.TimeFormat;
import me.skywars.tasks.FinalEvent;
import me.skywars.tasks.GameCountdownTask;
import me.skywars.tasks.GameRunTask;
import me.skywars.tasks.RefilEvents;

public class Scoreboarding {
	
	public static void setScoreboard(Player player) {
		ScoreboardAPI score = new ScoreboardAPI("§6§lSKYWARS");
		score.create();
		if (Main.getInstance().checkState(GameState.LOBBY)) {
			score.breakLine(8);
			score.addLine("timer", "§fIniciando em: ", "§a" + TimeFormat.getTimerFormat(GameCountdownTask.time), null, 7);
			score.breakLine(6);
			score.addLine("map", "§fMapa: ", "§a" + Main.getInstance().getConfig().getString("nameMap"), null, 5);
			score.breakLine(4);
			score.addLine("skills", "§fKit:", " §a"+Main.getGameManager().getHabilidade().getName(player), null, 3);
			score.addLine("players", "§fJogadores: ", "§a" + Main.getGameManager().getJogadores().size(), null, 2);
			score.breakLine(1);
			score.addLine("site", "§6redefreaky.com", null, null, 0);
			score.setScoreboard(player);
			return;
		}
		if (Main.getInstance().checkState(GameState.PREPARAR)) {
			score.breakLine(8);
			score.addLine("timer", "§fIniciando em: ", "§a" + TimeFormat.getTimerFormat(GameRunTask.startIn), null, 7);
			score.breakLine(6);
			score.addLine("map", "§fMapa: ", "§a" + Main.getInstance().getConfig().getString("nameMap"), null, 5);
			score.breakLine(4);
			score.addLine("skills", "§fKit:", " §a"+Main.getGameManager().getHabilidade().getName(player), null, 3);
			score.addLine("players", "§fJogadores: ", "§a" + Main.getGameManager().getJogadores().size(), null, 2);
			score.breakLine(1);
			score.addLine("site", "§6redefreaky.com", null, null, 0);
			score.setScoreboard(player);
			return;
		}
		if (Main.getInstance().checkState(GameState.JOGO)) {
			score.breakLine(9);
			score.addLine("next", "§fPróximo evento", null, null, 8);
			if (Main.getGameManager().modes == Modes.REFIL) {
				score.addLine("timer", "§f- Refil: ", TimeFormat.getTimerFormat(RefilEvents.tempo), null, 7);
			} else if (Main.getGameManager().modes == Modes.END) {
				score.addLine("timer", "§f- Finalizando: ", TimeFormat.getTimerFormat(FinalEvent.tempo), null, 7);
			}
			score.breakLine(6);
			score.addLine("map", "§fMapa: ", "§a" + Main.getInstance().getConfig().getString("nameMap"), null, 5);
			score.breakLine(4);
			score.addLine("kills", "§fAbates: ", "§a"+Main.getGameManager().getKills().get(player.getUniqueId()), null, 3);
			score.addLine("skills", "§fKit:", " §a"+Main.getGameManager().getHabilidade().getName(player), null, 3);
			score.addLine("players", "§fJogadores: ", "§a" + Main.getGameManager().getJogadores().size(), null, 2);
			score.breakLine(1);
			score.addLine("site", "§6redefreaky.com", null, null, 0);
			score.setScoreboard(player);
			return;
		}
	}
	
	public static void updateHabilidade(Player player) {
		ScoreboardAPI score = new ScoreboardAPI("§6§lSKYWARS");
		score.update(player, "skills", " §a"+Main.getGameManager().getHabilidade().getName(player));
	}
	
	public static void updateJogadores(Player player) {
		ScoreboardAPI score = new ScoreboardAPI("§6§lSKYWARS");
		score.update(player, "players", "§a"+Main.getGameManager().getJogadores().size());
	}
	
	public static void updateKills(Player player) {
		ScoreboardAPI score = new ScoreboardAPI("§6§lSKYWARS");
		score.update(player, "kills", "§a"+Main.getGameManager().getKills().get(player.getUniqueId()));
	}
	
	public static void updateTimer(Player player) {
		ScoreboardAPI score = new ScoreboardAPI("§6§lSKYWARS");
		switch (Main.state) {
		case LOBBY:
			score.update(player, "timer", "§a" + TimeFormat.getTimerFormat(GameCountdownTask.time));
			break;
		case PREPARAR:
			score.update(player, "timer", "§a" + TimeFormat.getTimerFormat(GameRunTask.startIn));
			break;
		case JOGO:
			if (Main.getGameManager().modes == Modes.REFIL)
				score.update(player, "timer", TimeFormat.getTimerFormat(RefilEvents.tempo));
			else
				score.update(player, "timer", TimeFormat.getTimerFormat(FinalEvent.tempo));
			break;
		default:
			score.update(player, "timer", "§a" + TimeFormat.getTimerFormat(GameCountdownTask.time));
			break;
		}
	}
	

}
