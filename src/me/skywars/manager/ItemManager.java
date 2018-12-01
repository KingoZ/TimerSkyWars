package me.skywars.manager;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class ItemManager {
	
	public static ItemStack createItem(Material material, String name) {
		ItemStack i = new ItemStack(material);
		ItemMeta m = i.getItemMeta();
		m.setDisplayName(name);
		i.setItemMeta(m);
		return i;
	}
	
	// LetterHead
	public static ItemStack createItem(String nome, String id, String textura) {
		ItemStack i = new LetterHead(nome, id, textura).getItemStack();
        SkullMeta s = (SkullMeta) i.getItemMeta();
        i.setItemMeta(s);
        return i;
	}

	// Lore
	public static ItemStack createItem(Material material, String name, String[] lore) {
		ItemStack i = new ItemStack(material);
		ItemMeta m = i.getItemMeta();
		m.setDisplayName(name);
		m.setLore(Arrays.asList(lore));
		i.setItemMeta(m);
		return i;
	}

	// Durability
	public static ItemStack createItem(Material material, int durability, String name) {
		ItemStack i = new ItemStack(material, 1, (short) durability);
		ItemMeta m = i.getItemMeta();
		m.setDisplayName(name);
		i.setItemMeta(m);
		return i;
	}

	public static ItemStack createItem(Material material, String name, int durability, String[] lore) {
		ItemStack i = new ItemStack(material, 1, (short) durability);
		ItemMeta m = i.getItemMeta();
		m.setDisplayName(name);
		i.setItemMeta(m);
		return i;
	}

	// Size
	public static ItemStack createItem(Material material, String name, int size) {
		ItemStack i = new ItemStack(material, size);
		ItemMeta m = i.getItemMeta();
		m.setDisplayName(name);
		i.setItemMeta(m);
		return i;
	}

	public static ItemStack createItem(Material material, String name, int size, int durability) {
		ItemStack i = new ItemStack(material, size, (short) durability);
		ItemMeta m = i.getItemMeta();
		m.setDisplayName(name);
		i.setItemMeta(m);
		return i;
	}

	public static ItemStack createItem(Material material, String name, int size, int durability, String[] lore) {
		ItemStack i = new ItemStack(material, size, (short) durability);
		ItemMeta m = i.getItemMeta();
		m.setDisplayName(name);
		m.setLore(Arrays.asList(lore));
		i.setItemMeta(m);
		return i;
	}
	
	public static boolean hasItem(Player player, Material material, String name) {
		return player.getInventory().getItemInHand().getType().equals(material) && player.getInventory().getItemInHand().getItemMeta().getDisplayName().equals(name);
	}

}
