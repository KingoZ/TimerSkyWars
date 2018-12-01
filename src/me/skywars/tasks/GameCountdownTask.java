package me.skywars.tasks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import me.skywars.Main;
import me.skywars.manager.TimeFormat;
import me.skywars.nms.TitleAPI;
import me.skywars.scoreboard.Scoreboarding;

public class GameCountdownTask extends BukkitRunnable {

	public static int time = 30;
	
	@Override
	public void run() {
		if (Main.getGameManager().getJogadores().size() >= 10) {
			time -= 1;
			for (Player player : Main.getInstance().getOnlinePlayers()) {
				Scoreboarding.updateTimer(player);
			}
			if (time % 5 == 0 && time != 0) {
				Bukkit.broadcastMessage("§6Iniciando a partida em §f" + TimeFormat.getTimerChat(time));
			}
			if (time == 0) {
				cancel();
				Main.getGameManager().startGame();
			}
			if (time <= 5) {
				for (Player players : Main.getInstance().getOnlinePlayers()) {
					TitleAPI title = new TitleAPI("§c§l" + time, "", 10, 20, 5);
					title.send(players);
				}
			}
		} else {
			time = 20;
		}

	}

}
