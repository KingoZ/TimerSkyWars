package me.skywars.inventorys;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.skywars.Main;

public class ManagerInventory {
	
	public static String spectatorSelector = "§aJogadores §7- Clique para abrir.";
	public static String habilidadeSelector = "§aHabilidades §7- Clique para abrir.";
	public static String lobby = "§cVoltar para o lobby §7- Clique.";
	
	public static void sendItens(Player player) {
		if (Main.getGameManager().getJogadores().contains(player.getUniqueId())) {
			player.getInventory().setItem(0, getItemStack(Material.ENDER_CHEST, habilidadeSelector, null));
			player.getInventory().setItem(8, getItemStack(Material.BED, lobby, null));
			player.updateInventory();
		}
		if (Main.getGameManager().getEspectadores().contains(player.getUniqueId())) {
			player.getInventory().setItem(0, getItemStack(Material.BOOK, spectatorSelector, null));
			player.getInventory().setItem(8, getItemStack(Material.BED, lobby, null));
			player.updateInventory();
		}
	}
	
	public static boolean checkItem(Player player, Material material) {
		return player.getInventory().getItemInHand().getType().equals(material);
	}
	
	public static boolean checkName(Player player, String displayName) {
		if (player.getInventory().getItemInHand().hasItemMeta() && player.getInventory().getItemInHand().getItemMeta().hasDisplayName())
			if (player.getInventory().getItemInHand().getItemMeta().getDisplayName().equals(displayName))
				return true;
		return false;
			
	}
	
	private static ItemStack getItemStack(Material material, String name, String[] lore) {
		ItemStack a = new ItemStack(material);
		ItemMeta m = a.getItemMeta();
		m.setDisplayName(name);
		if (lore != null)
			m.setLore(Arrays.asList(lore));
		a.setItemMeta(m);
		return a;
	}
	
}
