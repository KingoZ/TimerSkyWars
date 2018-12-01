package me.skywars.tasks;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import me.skywars.Main;
import me.skywars.scoreboard.Scoreboarding;

public class FinalEvent extends BukkitRunnable {

	public static int tempo;

	public FinalEvent(int tempo) {
		FinalEvent.tempo = tempo;
	}

	@Override
	public void run() {
		if (tempo <= 0) {
			Main.getGameManager().checkEmpate();
		}
		tempo -= 1;
		for (Player player : Main.getInstance().getOnlinePlayers()) {
			Scoreboarding.updateTimer(player);
		}
	}

}
