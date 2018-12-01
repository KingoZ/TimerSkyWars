package me.skywars.inventorys;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.skywars.manager.ItemManager;

public class KitInventory {

	/*
	 * /give @p skull 1 3 {display:{Name:"Question"},SkullOwner:{Id:
	 * "808ac216-799a-4d42-bd68-7c9f0c1e89d1",Properties:{textures:[{Value:
	 * "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2FhYjI3Mjg0MGQ3OTBjMmVkMmJlNWM4NjAyODlmOTVkODhlMzE2YjY1YzQ1NmZmNmEzNTE4MGQyZTViZmY2In19fQ=="
	 * }]}}}
	 */
	
	public static String title = "§6Kits:";

	public static void open(Player player) {
		Inventory inventory = Bukkit.createInventory(player, 54, title);

		inventory.setItem(4, ItemManager.createItem("§5Seus kits:", "42db67b6-9dd1-4bfe-b478-8829c1",
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTZjYzQ4NmMyYmUxY2I5ZGZjYjJlNTNkZDlhM2U5YTg4M2JmYWRiMjdjYjk1NmYxODk2ZDYwMmI0MDY3In19fQ=="));

		ItemStack cabecaEmBreve = ItemManager.createItem("§7Em breve", 
				"808ac216-799a-4d42-bd68-7c9f0c1e89d1", 
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2FhYjI3Mjg0MGQ3OTBjMmVkMmJlNWM4NjAyODlmOTVkODhlMzE2YjY1YzQ1NmZmNmEzNTE4MGQyZTViZmY2In19fQ==");

		if (player.hasPermission("skywars.kit.fenix") || player.isOp()) {
			inventory.setItem(10, ItemManager.createItem(Material.NETHER_STAR, "§aFênix"));
		} else {
			inventory.setItem(10, ItemManager.createItem(Material.INK_SACK, 8, "§cFênix"));
		}
		if (player.hasPermission("skywars.kit.kinghell") || player.isOp()) {
			inventory.setItem(11, ItemManager.createItem(Material.LAVA_BUCKET, "§aKingHell"));
		} else {
			inventory.setItem(11, ItemManager.createItem(Material.INK_SACK, 8, "§cKingHell"));
		}
		if (player.hasPermission("skywars.kit.luckyblock") || player.isOp()) {
			inventory.setItem(12, ItemManager.createItem(Material.SPONGE, "§aLucky Block"));
		} else {
			inventory.setItem(12, ItemManager.createItem(Material.INK_SACK, 8, "§cLucky Block"));
		}
		if (player.hasPermission("skywars.kit.guerreiro") || player.isOp()) {
			inventory.setItem(13, ItemManager.createItem(Material.DIAMOND_CHESTPLATE, "§aGuerreiro"));
		} else {
			inventory.setItem(13, ItemManager.createItem(Material.INK_SACK, 8, "§cGuerreiro"));
		}
		if (player.hasPermission("skywars.kit.builder") || player.isOp()) {
			inventory.setItem(14, ItemManager.createItem(Material.DIAMOND_PICKAXE, "§aBuilder"));
		} else {
			inventory.setItem(14, ItemManager.createItem(Material.INK_SACK, 8, "§cBuilder"));
		}
		inventory.setItem(15, cabecaEmBreve);
		inventory.setItem(16, cabecaEmBreve);
		inventory.setItem(19, cabecaEmBreve);
		inventory.setItem(20, cabecaEmBreve);
		inventory.setItem(21, cabecaEmBreve);
		inventory.setItem(22, cabecaEmBreve);
		inventory.setItem(23, cabecaEmBreve);
		inventory.setItem(24, cabecaEmBreve);
		inventory.setItem(25, cabecaEmBreve);
		inventory.setItem(28, cabecaEmBreve);
		inventory.setItem(29, cabecaEmBreve);
		inventory.setItem(30, cabecaEmBreve);
		inventory.setItem(31, cabecaEmBreve);
		inventory.setItem(32, cabecaEmBreve);
		inventory.setItem(33, cabecaEmBreve);
		inventory.setItem(34, cabecaEmBreve);
		inventory.setItem(37, cabecaEmBreve);
		inventory.setItem(38, cabecaEmBreve);
		inventory.setItem(39, cabecaEmBreve);
		inventory.setItem(40, cabecaEmBreve);
		inventory.setItem(41, cabecaEmBreve);
		inventory.setItem(42, cabecaEmBreve);
		inventory.setItem(43, cabecaEmBreve);
		
		player.openInventory(inventory);
	}

}
