package me.skywars.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;

import me.skywars.Main;
import me.skywars.automatic.TeleportServer;
import me.skywars.inventorys.ManagerInventory;
import me.skywars.kit.KitAPI;
import me.skywars.manager.ItemManager;
import me.skywars.nms.TitleAPI;
import me.skywars.scoreboard.Scoreboarding;
import me.skywars.tasks.GameCountdownTask;
import me.skywars.tasks.GameRunTask;
import me.skywars.tasks.RefilEvents;

public class GameManager {

	public enum Modes {
		REFIL, END
	}
	
	// ArrayList's
	private ArrayList<UUID> jogadores = new ArrayList<>();
	private ArrayList<UUID> espectadores = new ArrayList<>();
	private ArrayList<Location> spawnPoints = new ArrayList<>();
	private ArrayList<Location> miniFeastLocation = new ArrayList<>();

	// Set's
	private Set<Chest> opened = new HashSet<>();
	private Set<Chest> miniFeastChest = new HashSet<>();
	private Set<Block> miniFeastBlock = new HashSet<>();

	private Set<Chest> feastChest = new HashSet<>();
	private Set<Block> feastBlock = new HashSet<>();
	
	private Set<Block> jailsBlock = new HashSet<>();
	
	// Map's
	private Map<UUID, Location> gamePlayerToSpawnPoint = new HashMap<>();
	private Map<UUID, Integer> kills = new HashMap<>();

	private String mapName;
	private Location lobbyLocation;
	private Location feast;
	public Modes modes;
	private KitAPI kit;

	private TitleAPI title;

	public GameManager() {
		kit = new KitAPI();
		Main.getInstance().setState(GameState.LOBBY);
		this.mapName = Main.getInstance().getConfig().getString("nameMap");
		try {
			String[] values = Main.getInstance().getConfig().getString("lobbyPoint").split(","); // [X:0, Y:0, Z:0]
			double x = Double.parseDouble(values[0].split(":")[1]); // X:0 -> X, 0 -> 0
			double y = Double.parseDouble(values[1].split(":")[1]);
			double z = Double.parseDouble(values[2].split(":")[1]);
			Location location = new Location(Bukkit.getWorld("world"), x, y, z);
			this.lobbyLocation = location;
			// Bukkit.getConsoleSender().sendMessage(""+lobbyLocation);
		} catch (Exception ex) {
			Main.getInstance().getLogger().severe("Falha ao carregar o spawnpoit com metadata lobby para o mapa: '"
					+ mapName + "'. Tipo de exceção: " + ex);
		}

		try {
			String[] values = Main.getInstance().getConfig().getString("feast").split(","); // [X:0, Y:0, Z:0]
			double x = Double.parseDouble(values[0].split(":")[1]); // X:0 -> X, 0 -> 0
			double y = Double.parseDouble(values[1].split(":")[1]);
			double z = Double.parseDouble(values[2].split(":")[1]);
			Location location = new Location(Bukkit.getWorld(mapName), x, y, z);
			this.feast = location;
			// Bukkit.getConsoleSender().sendMessage(""+feast);
		} catch (Exception ex) {
			Main.getInstance().getLogger().severe("Falha ao carregar o spawnpoit com metadata feast para o mapa: '"
					+ mapName + "'. Tipo de exceção: " + ex);
		}

		for (String point : Main.getInstance().getConfig().getStringList("spawnPoints")) {
			// X:0,Y:0,Z:0
			try {
				String[] values = point.split(","); // [X:0, Y:0, Z:0]
				double x = Double.parseDouble(values[0].split(":")[1]); // X:0 -> X, 0 -> 0
				double y = Double.parseDouble(values[1].split(":")[1]);
				double z = Double.parseDouble(values[2].split(":")[1]);
				Location location = new Location(Bukkit.getWorld(mapName), x, y, z);
				spawnPoints.add(location);
				// Bukkit.getConsoleSender().sendMessage("Tem " + spawnPoints.size() + "
				// spawns!");
			} catch (Exception ex) {
				Main.getInstance().getLogger().severe("Falha ao carregar o spawnpoit com metadata " + point
						+ " para o mapa: '" + mapName + "'. Tipo de exceção: " + ex);
			}
		}

		for (String miniFeast : Main.getInstance().getConfig().getStringList("miniFeast")) {
			// X:0,Y:0,Z:0
			try {
				String[] values = miniFeast.split(","); // [X:0, Y:0, Z:0]
				double x = Double.parseDouble(values[0].split(":")[1]); // X:0 -> X, 0 -> 0
				double y = Double.parseDouble(values[1].split(":")[1]);
				double z = Double.parseDouble(values[2].split(":")[1]);
				Location location = new Location(Bukkit.getWorld(mapName), x, y, z);
				miniFeastLocation.add(location);
				// Bukkit.getConsoleSender().sendMessage("Tem " + miniFeastLocation.size() + "
				// miniFeast!");
			} catch (Exception ex) {
				Main.getInstance().getLogger().severe("Falha ao carregar o miniFeast com metadata " + miniFeast
						+ " para o mapa: '" + mapName + "'. Tipo de exceção: " + ex);
			}
		}

		setMiniFeast();
		inializeMiniFeast();
		setFeast();
		inializeFeast();
		createJail();
		startCountdown();
	}

	public int maxAndMin(int max, int min) {
		int r = new Random().nextInt(max);
		if (r < min) { // Min = 2 ; r = 1 | 1 < 2 | r = 2;
			r = min;
		}
		return r;
	}

	public void onJoin(Player player) {
		if (!getJogadores().contains(player.getUniqueId())) {
			getJogadores().add(player.getUniqueId());
			Bukkit.broadcastMessage("§7" + player.getName() + " §eentrou na partida. §6(" + getJogadores().size() + "/"
					+ Bukkit.getMaxPlayers() + ")");
			Scoreboarding.setScoreboard(player);
			ManagerInventory.sendItens(player);
			for (Player players : Main.getInstance().getOnlinePlayers()) {
				Scoreboarding.updateJogadores(players);
			}
			kills.put(player.getUniqueId(), 0);
			player.teleport(lobbyLocation);
		}
	}

	public void onQuit(Player player) {
		if (getJogadores().contains(player.getUniqueId())) {
			if (Main.getInstance().checkState(GameState.LOBBY)) {
				getJogadores().remove(player.getUniqueId());
				Bukkit.broadcastMessage("§7" + player.getName() + " §esaiu da partida. §6(" + getJogadores().size()
						+ "/" + Bukkit.getMaxPlayers() + ")");
				if (getJogadores().size() >= 1) {
					for (Player players : Main.getInstance().getOnlinePlayers()) {
						Scoreboarding.updateJogadores(players);
					}
				}
			} else {
				getJogadores().remove(player.getUniqueId());
				if (getJogadores().size() >= 1) {
					for (Player players : Main.getInstance().getOnlinePlayers()) {
						Scoreboarding.updateJogadores(players);
					}
				}
				checkWinner();
			}
		}
	}

	public void switchEspectador(Player player) {
		player.getInventory().clear();
		player.getInventory().setArmorContents(null);
		if (getJogadores().contains(player.getUniqueId()))
			getJogadores().remove(player.getUniqueId());
		if (!getEspectadores().contains(player.getUniqueId()))
			getEspectadores().add(player.getUniqueId());
		for (Player players : Main.getInstance().getOnlinePlayers()) {
			// Hide the players(deaths) from players(lives)
			players.hidePlayer(player);
		}
		player.teleport(Main.getGameManager().getFeast());
		kills.put(player.getUniqueId(), Integer.valueOf(0));
		if (getJogadores().size() >= 1) {
			for (Player players : Main.getInstance().getOnlinePlayers()) {
				Scoreboarding.updateJogadores(players);
			}
		}
		player.setHealth(player.getMaxHealth());
		player.setAllowFlight(true);
		player.setFlying(true);
		ManagerInventory.sendItens(player);
		player.updateInventory();
	}
	
	public void checkItens() {
		for (Player player : Main.getInstance().getOnlinePlayers()) {
			if (getHabilidade().getName(player).equals("LuckyBlock")) {
				player.getInventory().setItem(0, ItemManager.createItem(Material.SPONGE, "§6Lucky Block", 36));
			}
			if (getHabilidade().getName(player).equals("Guerreiro")) {
				player.getInventory().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
			}
			if (getHabilidade().getName(player).equals("Builder")) {
				player.getInventory().setItem(0, ItemManager.createItem(Material.WOOD, "§aBuilder", 64));
				player.getInventory().setItem(1, ItemManager.createItem(Material.IRON_AXE, "§aBuilder"));
			}
		}
	}

	public void checkWinner() {
		if (getJogadores().size() > 1)
			return;
		if (getJogadores().size() == 0)
			Bukkit.shutdown();
		if (getJogadores().size() == 1) {
			Main.getInstance().setState(GameState.FINAL);
			for (Player todos : Main.getInstance().getOnlinePlayers()) {
				for (Entity entity : todos.getWorld().getEntities()) {
					if (entity instanceof Item) {
						entity.remove();
					}
				}
			}
			Player player = Bukkit.getPlayer(getJogadores().get(0));
			player.setAllowFlight(true);
			player.setFlying(true);
			player.updateInventory();
			TitleAPI title = new TitleAPI("§a§lJOGO FINALIZADO!", "§6Vencedor(a): §f" + player.getName(), 20, 30, 20);
			for (Player players : Main.getInstance().getOnlinePlayers())
				title.send(players);
			Bukkit.broadcastMessage("     ");
			int i = 10*getKills().get(player.getUniqueId());
			Bukkit.broadcastMessage(" §6+" + i + "§f por matar " + getKills().get(player.getUniqueId()) + " jogadores.");
			Bukkit.broadcastMessage("     ");
			new BukkitRunnable() {

				@Override
				public void run() {
					firework(player);
				}
			}.runTaskTimer(Main.getInstance(), 0L, 1 * 20);
			new BukkitRunnable() {

				@Override
				public void run() {
					for (Player todos : Main.getInstance().getOnlinePlayers()) {
						TeleportServer.sendTeleport(todos, "lobby");
					}
				}
			}.runTaskLater(Main.getInstance(), 20 * 10);
			new BukkitRunnable() {
				public void run() {
					if (getJogadores().size() >= 0) {
						cancel();
						Bukkit.shutdown();
					}
				}
			}.runTaskLater(Main.getInstance(), 20 * 15);
		}
	}
	
	public void checkEmpate() {
		if (getJogadores().size() > 2) {
			new BukkitRunnable() {

				@Override
				public void run() {
					for (Player todos : Main.getInstance().getOnlinePlayers()) {
						TeleportServer.sendTeleport(todos, "lobby");
					}
				}
			}.runTaskLater(Main.getInstance(), 20 * 5);
			new BukkitRunnable() {
				public void run() {
					if (getJogadores().size() >= 0) {
						cancel();
						Bukkit.shutdown();
					}
				}
			}.runTaskLater(Main.getInstance(), 20 * 10);
		}
		if (getJogadores().size() == 0)
			Bukkit.shutdown();
		if (getJogadores().size() == 2) {
			Main.getInstance().setState(GameState.FINAL);
			Player player = Bukkit.getPlayer(getJogadores().get(0));
			Player player1 = Bukkit.getPlayer(getJogadores().get(1));
			player.setAllowFlight(true);
			player.setFlying(true);
			player1.setAllowFlight(true);
			player1.setFlying(true);
			player.updateInventory();
			new BukkitRunnable() {

				@Override
				public void run() {
					firework(player);
					firework(player1);
				}
			}.runTaskTimer(Main.getInstance(), 0L, 1 * 20);
			new BukkitRunnable() {

				@Override
				public void run() {
					for (Player todos : Main.getInstance().getOnlinePlayers()) {
						TeleportServer.sendTeleport(todos, "lobby");
					}
				}
			}.runTaskLater(Main.getInstance(), 20 * 10);
			new BukkitRunnable() {
				public void run() {
					if (getJogadores().size() >= 0) {
						cancel();
						Bukkit.shutdown();
					}
				}
			}.runTaskLater(Main.getInstance(), 20 * 15);
		}
	}

	public void refilStart() {
		modes = Modes.REFIL;
		new RefilEvents(120).runTaskTimer(Main.getInstance(), 0, 20);
		for (Player player : Main.getInstance().getOnlinePlayers()) {
			Scoreboarding.setScoreboard(player);
		}
	}

	public void startGame() {
		new GameRunTask().runTaskTimer(Main.getInstance(), 0, 20);
		for (Player player : Main.getInstance().getOnlinePlayers()) {
			Scoreboarding.setScoreboard(player);
		}
	}

	public void startCountdown() {
		new GameCountdownTask().runTaskTimer(Main.getInstance(), 0, 20);
	}

	public void createJail() {
		for (Location location : spawnPoints) {
			createJail(location);
		}
	}

	public void inializeMiniFeast() {
		for (Iterator<?> iterator = miniFeastBlock.iterator(); iterator.hasNext();) {
			Block block = (Block) iterator.next();
			if (block.getType() == Material.CHEST) {
				Chest chest = (Chest) block.getState();
				miniFeastChest.add(chest);
			}
		}
	}

	public void setMiniFeast() {
		for (Location location : miniFeastLocation) {
			for (Block block : getNearbyBlocks(location, 10)) {
				miniFeastBlock.add(block);
			}
		}
	}

	public void inializeFeast() {
		for (Iterator<?> iterator = feastBlock.iterator(); iterator.hasNext();) {
			Block block = (Block) iterator.next();
			if (block.getType() == Material.CHEST) {
				Chest chest = (Chest) block.getState();
				feastChest.add(chest);
				// Bukkit.getConsoleSender().sendMessage("§aBaus do feast adicionado com
				// sucesso!");
			}
		}
	}

	public void setFeast() {
		// Bukkit.getConsoleSender().sendMessage("§a" + feast);
		for (Block block : getNearbyBlocks(feast, 15)) {
			feastBlock.add(block);
		}
	}

	public void assignSpawnPositions() {
		int id = 0;
		for (UUID uuid : getJogadores()) {
			try {
				Player gamePlayer = Bukkit.getPlayer(uuid);
				gamePlayerToSpawnPoint.put(uuid, spawnPoints.get(id));
				double x = spawnPoints.get(id).getX() + 0.6, z = spawnPoints.get(id).getZ() + 0.5;
				double y = spawnPoints.get(id).getY() - 2.5;
				Location location = new Location(spawnPoints.get(id).getWorld(), x, y, z);
				gamePlayer.teleport(location.clone());
				id += 1;
				gamePlayer.getPlayer().setGameMode(GameMode.SURVIVAL);
				gamePlayer.getPlayer().setHealth(gamePlayer.getPlayer().getMaxHealth());
			} catch (IndexOutOfBoundsException ex) {
				ex.printStackTrace();
			}
		}
	}

	public List<Block> getNearbyBlocks(Location location, int radius) {
		List<Block> blocks = new ArrayList<Block>();
		for (int x = location.getBlockX() - radius; x <= location.getBlockX() + radius; x++) {
			for (int y = location.getBlockY() - radius; y <= location.getBlockY() + radius; y++) {
				for (int z = location.getBlockZ() - radius; z <= location.getBlockZ() + radius; z++) {
					blocks.add(location.getWorld().getBlockAt(x, y, z));
				}
			}
		}
		return blocks;
	}

	public void firework(Player p) {
		Location loc = p.getLocation();

		final Firework f = (Firework) loc.getWorld().spawnEntity(loc, EntityType.FIREWORK);
		FireworkMeta fm = f.getFireworkMeta();
		FireworkEffect.Type t = FireworkEffect.Type.STAR;
		Color c1 = null;
		Color c2 = null;
		Color c3 = null;
		int random = new Random().nextInt(2);
		switch (random) {
		case 0:
			c1 = Color.WHITE;
			c2 = Color.WHITE;
			c3 = Color.WHITE;
			break;
		case 1:
			c1 = Color.AQUA;
			c2 = Color.AQUA;
			c3 = Color.AQUA;
			break;
		case 2:
			c1 = Color.LIME;
			c2 = Color.LIME;
			c3 = Color.LIME;
			break;
		}
		FireworkEffect effect = FireworkEffect.builder().flicker(new Random().nextBoolean()).withColor(c1).withColor(c2)
				.withFade(c3).with(t).trail(new Random().nextBoolean()).build();
		fm.addEffect(effect);
		fm.setPower(0);
		f.setFireworkMeta(fm);
		new BukkitRunnable() {
			public void run() {
				f.detonate();
			}
		}.runTaskLater(Main.getInstance(), 4);
	}

	public void createJail(Location location) {
		Block cima = location.clone().add(0, 3, 0).getBlock();
		cima.setType(Material.GLASS);
		jailsBlock.add(cima);
		
		Block baixo = location.clone().add(0, -1, 0).getBlock();
		baixo.setType(Material.GLASS);
		jailsBlock.add(baixo);
		
		//1
		Block ba1 = location.clone().add(1, 2, 0).getBlock();
		ba1.setType(Material.GLASS);
		jailsBlock.add(ba1);
		
		Block ba2 = location.clone().add(-1, 2, 0).getBlock();
		ba2.setType(Material.GLASS);
		jailsBlock.add(ba2);
		
		Block ba3 = location.clone().add(0, 2, 1).getBlock();
		ba3.setType(Material.GLASS);
		jailsBlock.add(ba3);
		
		Block ba4 = location.clone().add(0, 2, -1).getBlock();
		ba4.setType(Material.GLASS);
		jailsBlock.add(ba4);
		
		//1
		Block bb1 = location.clone().add(1, 1, 0).getBlock();
		bb1.setType(Material.GLASS);
		jailsBlock.add(bb1);
		
		Block bb2 = location.clone().add(-1, 1, 0).getBlock();
		bb2.setType(Material.GLASS);
		jailsBlock.add(bb2);
		
		Block bb3 = location.clone().add(0, 1, 1).getBlock();
		bb3.setType(Material.GLASS);
		jailsBlock.add(bb3);
		
		Block bb4 = location.clone().add(0, 1, -1).getBlock();
		bb4.setType(Material.GLASS);
		jailsBlock.add(bb4);
		
		//0
		Block bc1 = location.clone().add(1, 0, 0).getBlock();
		bc1.setType(Material.GLASS);
		jailsBlock.add(bc1);
		
		Block bc2 = location.clone().add(-1, 0, 0).getBlock();
		bc2.setType(Material.GLASS);
		jailsBlock.add(bc2);
		
		Block bc3 = location.clone().add(0, 0, 1).getBlock();
		bc3.setType(Material.GLASS);
		jailsBlock.add(bc3);
		
		Block bc4 = location.clone().add(0, 0, -1).getBlock();
		bc4.setType(Material.GLASS);
		jailsBlock.add(bc4);

		location.clone().add(0, 0, 0).getBlock().setType(Material.AIR);
		location.clone().add(0, 1, 0).getBlock().setType(Material.AIR);
		location.clone().add(0, 2, 0).getBlock().setType(Material.AIR);
	}
	
	public void removeJails() {
		for (Iterator<?> iterator = jailsBlock.iterator(); iterator.hasNext();) {
			Block block = (Block) iterator.next();
			block.setType(Material.AIR);
		}
	}
	
	public Map<UUID, Integer> getKills() {
		return kills;
	}

	public Set<Chest> getOpened() {
		return opened;
	}

	public TitleAPI getTitle() {
		return title;
	}

	public ArrayList<UUID> getEspectadores() {
		return espectadores;
	}

	public Set<Chest> getMiniFeastChest() {
		return miniFeastChest;
	}

	public Set<Chest> getFeastChest() {
		return feastChest;
	}

	public Location getFeast() {
		return feast;
	}
	
	public Modes getModes() {
		return modes;
	}

	public ArrayList<UUID> getJogadores() {
		return jogadores;
	}
	
	public Map<UUID, Location> getGamePlayerToSpawnPoint() {
		return gamePlayerToSpawnPoint;
	}
	
	public KitAPI getHabilidade() {
		return kit;
	}

}
