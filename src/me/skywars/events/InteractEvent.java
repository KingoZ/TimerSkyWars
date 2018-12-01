package me.skywars.events;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import me.skywars.Main;
import me.skywars.automatic.TeleportServer;
import me.skywars.inventorys.KitInventory;
import me.skywars.inventorys.ManagerInventory;
import me.skywars.inventorys.SpectatorInventory;

public class InteractEvent implements Listener {
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		Player player = e.getPlayer();
		if (ManagerInventory.checkItem(player, Material.BED) && ManagerInventory.checkName(player, ManagerInventory.lobby)) {
			e.setCancelled(true);
			if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
				TeleportServer.sendTeleport(player, "lobby");
			}
		}
		if (ManagerInventory.checkItem(player, Material.ENDER_CHEST) && ManagerInventory.checkName(player, ManagerInventory.habilidadeSelector)) {
			e.setCancelled(true);
			if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
				KitInventory.open(player);
			}
		}
		if (Main.getGameManager().getEspectadores().contains(player.getUniqueId())) {
			e.setCancelled(true);
			if (ManagerInventory.checkName(player, ManagerInventory.spectatorSelector))
				if (ManagerInventory.checkItem(player, Material.BOOK)) {
					if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
						SpectatorInventory.openSpectateGUI(player, 6, new ItemStack(Material.SKULL_ITEM, 1, (short) 3));
					}
					return;
				}
		}
	}

}
