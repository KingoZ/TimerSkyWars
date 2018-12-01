package me.skywars.events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import me.skywars.Main;
import me.skywars.game.GameManager;
import me.skywars.game.GameState;
import me.skywars.holograma.Hologram;
import me.skywars.holograma.HologramLibrary;
import me.skywars.manager.ItemChest;
import me.skywars.manager.TimeFormat;
import me.skywars.manager.ItemChest.Items;
import me.skywars.manager.ItemChest.Type;
import me.skywars.tasks.RefilEvents;

public class ChestEvent implements Listener {
	
	public enum typeChest {
		NORMAL, MINIFEAST, FEAST
	}
	
	public HashMap<Chest, typeChest> Types = new HashMap<>();

	@EventHandler
	public void onChestOpen(PlayerInteractEvent e) {
		Player player = e.getPlayer();
		if (Main.getInstance().checkState(GameState.LOBBY)) {
			e.setCancelled(true);
			return;
		}
		if (!Main.getGameManager().getJogadores().contains(player.getUniqueId()))
			e.setCancelled(true);
		handle(e, Main.getGameManager());
	}

	private void handle(PlayerInteractEvent event, GameManager game) {
		if (event.hasBlock() && event.getClickedBlock() != null
				&& event.getClickedBlock().getState() instanceof Chest) {
			Chest chest = (Chest) event.getClickedBlock().getState();
			if (game.getOpened().contains(chest)) {
				return;
			}
			if (game.getMiniFeastChest().contains(chest)) {
				Types.put(chest, typeChest.MINIFEAST);
			} else if (game.getFeastChest().contains(chest)) {
				Types.put(chest, typeChest.FEAST);
			} else {
				Types.put(chest, typeChest.NORMAL);
			}
			final Hologram holo = HologramLibrary.createHologram(chest.getLocation().clone().add(0, 2, 0), TimeFormat.getTimerFormat(RefilEvents.tempo));
			holo.spawn();
			new BukkitRunnable() {
				@Override
				public void run() {
					holo.updateLine(0, TimeFormat.getTimerFormat(RefilEvents.tempo));
				}
			}.runTaskTimer(Main.getInstance(), 0L, 20L);
			setChest(chest);
		}
	}

	public List<String> buildItens() {
		List<String> array = new ArrayList<>();
		
		//Blocks
		array.add("4,20,36,true");
		array.add("17,16,32,true");
		
		//Sword
		array.add("272,1,1,true");
		array.add("267,1,1,false");
		
		//Leather
		array.add("298,1,1,false");
		array.add("299,1,1,true");
		array.add("300,1,1,false");
		array.add("301,1,1,true");
		
		//Iron
		array.add("307,1,1,true");
		array.add("306,1,1,false");
		array.add("308,1,1,true");
		array.add("309,1,1,false");
		
		//Diamond
		array.add("310,1,1,false");
		array.add("311,1,1,false");
		array.add("312,1,1,false");
		array.add("313,1,1,false");
		
		//Food
		array.add("322,4,6,true");
		
		array.add("373;16458,1,1,true");
		return array;
	}
	
	public void setChest(Chest chest) {
		if (Types.containsKey(chest)) {
			GameManager game = Main.getGameManager();
			typeChest type = Types.get(chest);
			if (type == typeChest.NORMAL)
				encherBau(chest);
			if (type == typeChest.MINIFEAST)
				encherMiniFeast(chest);
			if (type == typeChest.FEAST)
				encherFeast(chest);
			game.getOpened().add(chest);
		}
	}

	private void encherBau(Chest chest) {
		//int slot = chest.getInventory().firstEmpty();
		ItemChest item = new ItemChest();
		
		ItemStack stackH = item.getItem(Items.HELMET);
		chest.getInventory().addItem(stackH);
		
		ItemStack stackC = item.getItem(Items.CHESTPLATE);
		chest.getInventory().addItem(stackC);
		
		ItemStack stackL = item.getItem(Items.LEGGINGS);
		chest.getInventory().addItem(stackL);
		
		ItemStack stackB = item.getItem(Items.BOOTS);
		chest.getInventory().addItem(stackB);
		
		ItemStack stackS = item.getItem(Items.SWORD);
		chest.getInventory().addItem(stackS);
		
		ItemStack stackBl = item.getItem(Items.BLOCKS);
		chest.getInventory().addItem(stackBl);
		
		ItemStack stackF = item.getItem(Items.FOOD);
		chest.getInventory().addItem(stackF);
		
		ItemStack stackP1 = item.getItem(Items.POTION);
		chest.getInventory().addItem(stackP1);
		
		ItemStack stackP2 = item.getItem(Items.POTION);
		chest.getInventory().addItem(stackP2);
		
		ItemStack stackO = item.getItem(Items.OTHER);
		chest.getInventory().addItem(stackO);
		
		chest.update();
	}
	
	private void encherMiniFeast(Chest chest) {
		//int slot = chest.getInventory().firstEmpty();
		ItemChest item = new ItemChest();
		
		boolean bH = new Random().nextBoolean();
		if (bH == true) {
			ItemStack stackH = item.getItem(Items.HELMET, Type.IRON, 1);
			chest.getInventory().addItem(stackH);
		} else {
			ItemStack stackH = new ItemStack(Material.AIR);
			chest.getInventory().addItem(stackH);
		}
		
		boolean bC = new Random().nextBoolean();
		if (bC == true) {
			ItemStack stackC = item.getItem(Items.CHESTPLATE, Type.IRON, 1);
			chest.getInventory().addItem(stackC);
		}
		
		boolean bL = new Random().nextBoolean();
		if (bL == true) {
			ItemStack stackL = item.getItem(Items.LEGGINGS, Type.IRON, 1);
			chest.getInventory().addItem(stackL);
		}
		
		boolean bB = new Random().nextBoolean();
		if (bB == true) {
			ItemStack stackB = item.getItem(Items.BOOTS, Type.IRON, 1);
			chest.getInventory().addItem(stackB);
		}
		
		boolean bS = new Random().nextBoolean();
		if (bS == true) {
			ItemStack stackS = item.getItem(Items.SWORD, Type.DIAMOND, 1);
			chest.getInventory().addItem(stackS);
		}
		
		ItemStack stackBl = item.getItem(Items.BLOCKS);
		chest.getInventory().addItem(stackBl);
		ItemStack stackF = item.getItem(Items.FOOD);
		chest.getInventory().addItem(stackF);
		ItemStack stackO = item.getItem(Items.OTHER);
		chest.getInventory().addItem(stackO);
		
		boolean bE = new Random().nextBoolean();
		if (bE == true) {
			ItemStack stackE = item.getItem(Items.OTHER, Type.ENDER_PEARL, Main.getGameManager().maxAndMin(1, 3));
			chest.getInventory().addItem(stackE);
		}
		
		chest.update();
	}
	
	private void encherFeast(Chest chest) {
		ItemChest item = new ItemChest();
		
		int slotH = new Random().nextInt(27);
		int bH = new Random().nextInt(5);
		if (bH == 3) {
			ItemStack stackH = item.getItem(Items.HELMET, Type.DIAMOND, 1);
			ItemMeta stackHm = stackH.getItemMeta();
			stackHm.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
			stackH.setItemMeta(stackHm);
			chest.getInventory().setItem(slotH, stackH);
		} if (bH > 3) {
			ItemStack stackH = item.getItem(Items.HELMET, Type.DIAMOND, 1);
			chest.getInventory().setItem(slotH, stackH);
		} if (bH < 3 && bH != 0) {
			ItemStack stackH = new ItemStack(Material.AIR);
			chest.getInventory().setItem(slotH, stackH);
		} if (bH < 3 && bH == 0) {
			ItemStack stackH = item.getItem(Items.HELMET, Type.IRON, 1);
			chest.getInventory().setItem(slotH, stackH);
		}
		
		int slotC = new Random().nextInt(27);
		int bC = new Random().nextInt(7);
		if (bC > 5 && bC == 7) {
			ItemStack stackH = item.getItem(Items.CHESTPLATE, Type.DIAMOND, 1);
			ItemMeta stackHm = stackH.getItemMeta();
			stackHm.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
			stackH.setItemMeta(stackHm);
			chest.getInventory().setItem(slotC, stackH);
		} if (bC > 5 && bC != 7) {
			ItemStack stackH = item.getItem(Items.CHESTPLATE, Type.DIAMOND, 1);
			chest.getInventory().setItem(slotC,stackH);
		} if (bC < 5 && bC != 0) {
			ItemStack stackH = new ItemStack(Material.AIR);
			chest.getInventory().setItem(slotC,stackH);
		} if (bC < 5 && bC == 0) {
			ItemStack stackH = item.getItem(Items.CHESTPLATE, Type.IRON, 1);
			chest.getInventory().setItem(slotC,stackH);
		}
		
		int slotL = new Random().nextInt(27);
		int bL = new Random().nextInt(5);
		if (bL == 3) {
			ItemStack stackH = item.getItem(Items.LEGGINGS, Type.DIAMOND, 1);
			ItemMeta stackHm = stackH.getItemMeta();
			stackHm.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
			stackH.setItemMeta(stackHm);
			chest.getInventory().setItem(slotL, stackH);
		} if (bL > 3) {
			ItemStack stackH = item.getItem(Items.LEGGINGS, Type.DIAMOND, 1);
			chest.getInventory().setItem(slotL, stackH);
		} if (bL < 3 && bL != 0) {
			ItemStack stackH = new ItemStack(Material.AIR);
			chest.getInventory().setItem(slotL, stackH);
		} if (bL < 3 && bL == 0) {
			ItemStack stackH = item.getItem(Items.LEGGINGS, Type.IRON, 1);
			chest.getInventory().setItem(slotL, stackH);
		}
		
		int slotB = new Random().nextInt(27);
		int bB = new Random().nextInt(3);
		if (bB == 2) {
			ItemStack stackH = item.getItem(Items.BOOTS, Type.DIAMOND, 1);
			ItemMeta stackHm = stackH.getItemMeta();
			stackHm.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
			stackH.setItemMeta(stackHm);
			chest.getInventory().setItem(slotB,stackH);
		} if (bB > 2) {
			ItemStack stackH = item.getItem(Items.BOOTS, Type.DIAMOND, 1);
			chest.getInventory().setItem(slotB,stackH);
		} if (bB < 2 && bB != 0) {
			ItemStack stackH = new ItemStack(Material.AIR);
			chest.getInventory().setItem(slotB,stackH);
		} if (bB < 2 && bB == 0) {
			ItemStack stackH = item.getItem(Items.BOOTS, Type.IRON, 1);
			chest.getInventory().setItem(slotB,stackH);
		}
		
		int slotS = new Random().nextInt(27);
		int bS = new Random().nextInt(4);
		if (bS == 4) {
			ItemStack stackH = item.getItem(Items.SWORD, Type.IRON, 1);
			chest.getInventory().setItem(slotS,stackH);
		}if (bS == 3) {
			ItemStack stackH = item.getItem(Items.SWORD, Type.DIAMOND, 1);
			chest.getInventory().setItem(slotS,stackH);
		} if (bS == 2) {
			ItemStack stackH = item.getItem(Items.SWORD, Type.DIAMOND, 1);
			ItemMeta stackHm = stackH.getItemMeta();
			stackHm.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
			stackH.setItemMeta(stackHm);
			chest.getInventory().setItem(slotS,stackH);
		} if (bS == 1) {
			ItemStack stackH = item.getItem(Items.SWORD, Type.IRON, 1);
			ItemMeta stackHm = stackH.getItemMeta();
			stackHm.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
			stackH.setItemMeta(stackHm);
			chest.getInventory().setItem(slotS,stackH);
		} if (bS == 0) {
			ItemStack stackH = new ItemStack(Material.AIR);
			chest.getInventory().setItem(slotS,stackH);
		}
		
		int slotO = new Random().nextInt(27);
		int bO = new Random().nextInt(10);
		if (bO == 10) {
			ItemStack stackH = item.getItem(Items.OTHER, Type.EGG, Main.getGameManager().maxAndMin(30, 10));
			chest.getInventory().setItem(slotO,stackH);
		}
		if (bO == 9) {
			ItemStack stackH = item.getItem(Items.FOOD, Type.GOLDEN_APPLE, Main.getGameManager().maxAndMin(3, 2));
			chest.getInventory().setItem(slotO,stackH);
		}
		if (bO == 8) {
			ItemStack stackH = item.getItem(Items.POTION);
			chest.getInventory().setItem(slotO,stackH);
		}
		if (bO == 7) {
			ItemStack stackH = item.getItem(Items.OTHER, Type.ENDER_PEARL, Main.getGameManager().maxAndMin(5, 2));
			chest.getInventory().setItem(slotO,stackH);
		}
		if (bO == 6) {
			ItemStack stackH = new ItemStack(Material.LAVA_BUCKET);
			chest.getInventory().setItem(slotO,stackH);
		}
		if (bO == 5) {
			ItemStack stackH = new ItemStack(Material.TNT, Main.getGameManager().maxAndMin(10, 3));
			chest.getInventory().setItem(slotO,stackH);
		}
		if (bO == 4) {
			ItemStack stackH = new ItemStack(Material.WEB, Main.getGameManager().maxAndMin(15, 6));
			chest.getInventory().setItem(slotO,stackH);
		}
		if (bO == 3) {
			ItemStack stackH = new ItemStack(Material.WATER_BUCKET, Main.getGameManager().maxAndMin(15, 6));
			chest.getInventory().setItem(slotO,stackH);
		}
		if (bO == 2) {
			ItemStack stackH = new ItemStack(Material.EXP_BOTTLE, Main.getGameManager().maxAndMin(15, 6));
			chest.getInventory().setItem(slotO,stackH);
		}
		if (bO == 1) {
			ItemStack stackH = new ItemStack(Material.FISHING_ROD);
			chest.getInventory().setItem(slotO,stackH);
		}
		if (bO == 0) {
			ItemStack stackH = new ItemStack(Material.AIR);
			chest.getInventory().setItem(slotO,stackH);
		}
		
		int slotO1 = new Random().nextInt(27);
		int bO1 = new Random().nextInt(10);
		if (bO1 == 10) {
			ItemStack stackH = item.getItem(Items.OTHER, Type.EGG, Main.getGameManager().maxAndMin(30, 10));
			chest.getInventory().setItem(slotO1,stackH);
		}
		if (bO1 == 9) {
			ItemStack stackH = item.getItem(Items.FOOD, Type.GOLDEN_APPLE, Main.getGameManager().maxAndMin(3, 2));
			chest.getInventory().setItem(slotO1,stackH);
		}
		if (bO1 == 8) {
			ItemStack stackH = item.getItem(Items.POTION);
			chest.getInventory().setItem(slotO1,stackH);
		}
		if (bO1 == 7) {
			ItemStack stackH = item.getItem(Items.OTHER, Type.ENDER_PEARL, Main.getGameManager().maxAndMin(5, 2));
			chest.getInventory().setItem(slotO1,stackH);
		}
		if (bO1 == 6) {
			ItemStack stackH = new ItemStack(Material.LAVA_BUCKET);
			chest.getInventory().setItem(slotO1,stackH);
		}
		if (bO1 == 5) {
			ItemStack stackH = new ItemStack(Material.TNT, Main.getGameManager().maxAndMin(10, 3));
			chest.getInventory().setItem(slotO1,stackH);
		}
		if (bO1 == 4) {
			ItemStack stackH = new ItemStack(Material.WEB, Main.getGameManager().maxAndMin(15, 6));
			chest.getInventory().setItem(slotO1,stackH);
		}
		if (bO1 == 3) {
			ItemStack stackH = new ItemStack(Material.WATER_BUCKET, Main.getGameManager().maxAndMin(15, 6));
			chest.getInventory().setItem(slotO1,stackH);
		}
		if (bO1 == 2) {
			ItemStack stackH = new ItemStack(Material.EXP_BOTTLE, Main.getGameManager().maxAndMin(15, 6));
			chest.getInventory().setItem(slotO1,stackH);
		}
		if (bO1 == 1) {
			ItemStack stackH = new ItemStack(Material.FISHING_ROD);
			chest.getInventory().setItem(slotO1,stackH);
		}
		if (bO1 == 0) {
			ItemStack stackH = new ItemStack(Material.AIR);
			chest.getInventory().setItem(slotO1,stackH);
		}
		chest.update();
	}

}
