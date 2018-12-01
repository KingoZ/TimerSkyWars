package me.skywars.events;

import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.server.ServerListPingEvent;

import me.skywars.Main;
import me.skywars.game.GameState;

public class EventsMain implements Listener {
	
	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		Player player = e.getPlayer();
		if (Main.getInstance().checkState(GameState.LOBBY)) {
			e.setCancelled(true);
		} else if (Main.getInstance().checkState(GameState.JOGO)) {
			if (e.getBlock() instanceof Chest && e.getBlock().getType().equals(Material.CHEST)) {
				Main.getGameManager().getOpened().remove((Chest)e.getBlock());
			}
			if (Main.getGameManager().getEspectadores().contains(player.getUniqueId())) {
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onMotd(ServerListPingEvent e) {
		if (Main.getInstance().checkState(GameState.LOBBY)) {
			e.setMotd("iniciando");
		} else {
			e.setMotd("jogo");
		}
	}
	
	@EventHandler
	public void onPlace(BlockPlaceEvent e) {
		Player player = e.getPlayer();
		if (Main.getInstance().checkState(GameState.LOBBY)) {
			e.setCancelled(true);
		} else if (Main.getInstance().checkState(GameState.JOGO)) {
			if (Main.getGameManager().getEspectadores().contains(player.getUniqueId())) {
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void entitySpawn(EntitySpawnEvent e) {
		if (e.getEntityType() != EntityType.PLAYER && e.getEntityType() != EntityType.DROPPED_ITEM
				&& e.getEntityType() != EntityType.PRIMED_TNT)
			e.setCancelled(true);
	}
	
	@EventHandler
	public void chatEvent(AsyncPlayerChatEvent e) {
		e.setFormat(e.getPlayer().getDisplayName() + " §7» " + e.getMessage());
	}

}
