package me.skywars.tasks;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import me.skywars.Main;
import me.skywars.game.GameState;
import me.skywars.scoreboard.Scoreboarding;

public class GameRunTask extends BukkitRunnable {
	
	public static int startIn = 10;

	public GameRunTask() {
		Main.getGameManager().assignSpawnPositions();
		Main.getInstance().setState(GameState.PREPARAR);
	}
	
	@Override
	public void run() {
		if (startIn <= 0) {
			cancel();
			Main.getInstance().setState(GameState.JOGO);
			Main.getGameManager().refilStart();
			Main.getGameManager().removeJails();
			for (Player player : Main.getInstance().getOnlinePlayers()) {
				player.getInventory().clear();
				player.getInventory().setArmorContents(null);
				player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 5*20, 999));
			}
			Main.getGameManager().checkItens();
		}
		startIn-=1;
		for (Player player : Main.getInstance().getOnlinePlayers()) {
			Scoreboarding.updateTimer(player);
		}
	}
	
}
