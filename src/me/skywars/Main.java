package me.skywars;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import me.skywars.automatic.ListenerManager;
import me.skywars.game.GameManager;
import me.skywars.game.GameState;
import me.skywars.manager.RollbackHandler;

public class Main extends JavaPlugin {
	
	private static Main instance;
	public static Main getInstance() {
		return instance;
	}
	
	private static GameManager gameManager;
	public static GameManager getGameManager() {
		return gameManager;
	}
	
	public static GameState state;
	
	@Override
	public void onEnable() {
		instance=this;
		saveDefaultConfig();
		RollbackHandler.getRollbackHandler().register(Main.getInstance().getConfig().getString("nameMap"));
		Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		ListenerManager.loadListener();
		Bukkit.getConsoleSender().sendMessage("§a---------------------");
		Bukkit.getConsoleSender().sendMessage("§a kSkyWars - §61.0");
		Bukkit.getConsoleSender().sendMessage("§a Author - §6KingTimer");
		Bukkit.getConsoleSender().sendMessage("§a---------------------");
		gameManager = new GameManager();
		loadChunks();
	}
	
	@Override
	public void onDisable() {
		instance=null;
		HandlerList.unregisterAll(this);
	}
	
	public List<Player> getOnlinePlayers() {
		final List<Player> list = new ArrayList<>();
		for (World world : getServer().getWorlds()) {
			for (Player player : world.getPlayers()) {
				list.add(player);
			}
		}
		return list;
	}
	
	public void loadChunks() {
		Bukkit.getConsoleSender().sendMessage("§aCarregando chunks...");
		World world = Bukkit.getWorld("world");
		World worldmap = Bukkit.getWorld(Main.getInstance().getConfig().getString("nameMap"));

		for (int x = -60; x <= 60; x += 16) {
			for (int z = -60; z <= 60; z += 16) {
				Location location = new Location(world, x, world.getHighestBlockYAt(x, z), z);
				world.getChunkAt(location).load(true);
			}
		}
		for (int x = -80; x <= 80; x += 16) {
			for (int z = -80; z <= 80; z += 16) {
				Location location = new Location(world, x, world.getHighestBlockYAt(x, z), z);
				worldmap.getChunkAt(location).load(true);
			}
		}
	}
	
	public boolean checkState(GameState state) {
		return Main.state == state;
	}
	
	public void setState(GameState state) {
		Main.state = state;
	}

}
