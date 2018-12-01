package me.skywars.tasks;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Chest;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import me.skywars.Main;
import me.skywars.game.GameManager.Modes;
import me.skywars.holograma.Hologram;
import me.skywars.holograma.HologramLibrary;
import me.skywars.scoreboard.Scoreboarding;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.TileEntityChest;
import net.minecraft.server.v1_8_R3.World;

public class RefilEvents extends BukkitRunnable {

	public static int tempo;
	public static ArrayList<String> refil = new ArrayList<>();
	
	public RefilEvents(int tempo) {
		RefilEvents.tempo = tempo;
	}
	
	@Override
	public void run() {
		for (Chest chest : Main.getGameManager().getOpened()) {
			playChestAction(chest, true);
		}
		if (refil.size() == 0) {
			if (tempo <= 0) {
				cancel();
				for (Player players : Main.getInstance().getOnlinePlayers()) {
					players.playSound(players.getLocation(), Sound.CHEST_CLOSE, 1.0f, 1.0f);
				}
				for (Chest chest : Main.getGameManager().getOpened()) {
					playChestAction(chest, false);
				}
				for (Hologram armor : HologramLibrary.listHolograms()) {
					armor.despawn();
				}
				Main.getGameManager().getOpened().clear();
				refil.add("1");
				new RefilEvents(60).runTaskTimer(Main.getInstance(), 0, 20);
				for (Player player : Main.getInstance().getOnlinePlayers()) {
					Scoreboarding.setScoreboard(player);
				}
			}
		}
		if (refil.size() == 1) {
			if (tempo <= 0) {
				cancel();
				for (Player players : Main.getInstance().getOnlinePlayers()) {
					players.playSound(players.getLocation(), Sound.CHEST_CLOSE, 1.0f, 1.0f);
				}
				for (Chest chest : Main.getGameManager().getOpened()) {
					playChestAction(chest, false);
				}
				for (Hologram armor : HologramLibrary.listHolograms()) {
					armor.despawn();
				}
				Main.getGameManager().modes = Modes.END;
				new FinalEvent(300).runTaskTimer(Main.getInstance(), 0, 20);
				Main.getGameManager().getOpened().clear();
				for (Player player : Main.getInstance().getOnlinePlayers()) {
					Scoreboarding.setScoreboard(player);
				}
			}
		}
		tempo-=1;
		for (Player player : Main.getInstance().getOnlinePlayers()) {
			Scoreboarding.updateTimer(player);
		}
	}
	
	public void playChestAction(Chest chest, boolean open) {
		Location location = chest.getLocation();
		World world = ((CraftWorld) location.getWorld()).getHandle();
		BlockPosition position = new BlockPosition(location.getX(), location.getY(), location.getZ());
		TileEntityChest tileChest = (TileEntityChest) world.getTileEntity(position);
		world.playBlockAction(position, tileChest.w(), 1, open ? 1 : 0);
	}
}
