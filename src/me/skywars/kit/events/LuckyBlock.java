package me.skywars.kit.events;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import me.skywars.manager.ItemManager;

public class LuckyBlock implements Listener {
	
	private static ArrayList<Block> luckyBlock = new ArrayList<>();
	private static ArrayList<ItemStack> luckyItems = new ArrayList<>();
	
	public static void setItens() {
		luckyItems.add(new ItemStack(Material.DIAMOND));
		luckyItems.add(new ItemStack(Material.IRON_SWORD));
		luckyItems.add(new ItemStack(Material.EGG));
		luckyItems.add(new ItemStack(Material.DIAMOND_SWORD));
		luckyItems.add(new ItemStack(Material.APPLE));
		luckyItems.add(new ItemStack(Material.GOLDEN_APPLE));
		luckyItems.add(new ItemStack(Material.DIAMOND_BLOCK));
		luckyItems.add(new ItemStack(Material.ENDER_PEARL));
		luckyItems.add(new ItemStack(Material.EXP_BOTTLE, 36));
		luckyItems.add(new ItemStack(Material.DIRT, 30));
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {
		Player player = e.getPlayer();
		if (ItemManager.hasItem(player, Material.SPONGE, "§6Lucky Block")) {
			luckyBlock.add(e.getBlock());
		}
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		if (luckyBlock.contains(e.getBlock())) {
			Random ram = new Random();
			
			int item = ram.nextInt(luckyItems.size());
			ItemStack itemStack = luckyItems.get(item);
			e.getBlock().setType(Material.AIR);
			e.getBlock().getDrops().clear();
			e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), itemStack);
		}
	}

}
