package me.skywars.events;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import me.skywars.Main;
import me.skywars.inventorys.KitInventory;
import me.skywars.scoreboard.Scoreboarding;

public class ClickInventory implements Listener {
	
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		Player player = (Player)e.getWhoClicked();
		if (e.getCurrentItem() == null) return;
		if (e.getInventory() == null) return;
		if (e.getInventory().getTitle() == null) return;
		if (e.getInventory().getTitle().equals(KitInventory.title)) {
			e.setCancelled(true);
			if (e.getCurrentItem().getType().equals(Material.NETHER_STAR) &&
					e.getCurrentItem().getItemMeta().getDisplayName().equals("§aFênix")) {
				Main.getGameManager().getHabilidade().set(player, "Fênix");
				player.sendMessage("§aVocê selecionou o kit §fFênix§a!");
				Scoreboarding.updateHabilidade(player);
				player.closeInventory();
			}
			if (e.getCurrentItem().getType().equals(Material.LAVA_BUCKET) &&
					e.getCurrentItem().getItemMeta().getDisplayName().equals("§aKingHell")) {
				Main.getGameManager().getHabilidade().set(player, "KingHell");
				player.sendMessage("§aVocê selecionou o kit §fKingHell§a!");
				Scoreboarding.updateHabilidade(player);
				player.closeInventory();
			}
			if (e.getCurrentItem().getType().equals(Material.SPONGE) &&
					e.getCurrentItem().getItemMeta().getDisplayName().equals("§aLucky Block")) {
				Main.getGameManager().getHabilidade().set(player, "LuckyBlock");
				player.sendMessage("§aVocê selecionou o kit §fLuckyBlock§a!");
				Scoreboarding.updateHabilidade(player);
				player.closeInventory();
			}
			if (e.getCurrentItem().getType().equals(Material.DIAMOND_CHESTPLATE) &&
					e.getCurrentItem().getItemMeta().getDisplayName().equals("§aGuerreiro")) {
				Main.getGameManager().getHabilidade().set(player, "Guerreiro");
				player.sendMessage("§aVocê selecionou o kit §fGuerreiro§a!");
				Scoreboarding.updateHabilidade(player);
				player.closeInventory();
			}
			if (e.getCurrentItem().getType().equals(Material.DIAMOND_CHESTPLATE) &&
					e.getCurrentItem().getItemMeta().getDisplayName().equals("§aBuilder")) {
				Main.getGameManager().getHabilidade().set(player, "Builder");
				player.sendMessage("§aVocê selecionou o kit §fBuilder§a!");
				Scoreboarding.updateHabilidade(player);
				player.closeInventory();
			}
		}
	}

}
