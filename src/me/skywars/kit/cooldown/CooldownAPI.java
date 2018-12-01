package me.skywars.kit.cooldown;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import me.skywars.Main;
import me.skywars.manager.TimeFormat;

public class CooldownAPI {
	
	private Map<UUID, Integer> cooldown = new HashMap<>();
	
	public void setCooldown(Player player, Integer time) {
		cooldown.put(player.getUniqueId(), time);
		new BukkitRunnable() {
			
			@Override
			public void run() {
				if (cooldown.get(player.getUniqueId()) >= 1 && isCooldown(player)) {
					cooldown.put(player.getUniqueId(), cooldown.get(player.getUniqueId())-1);
				} else {
					removeCooldown(player);
					cancel();
				}
			}
		}.runTaskTimer(Main.getInstance(), 0L, 20L);
	}
	
	public String getMessage(Player player) {
		return "§cA habilidade com cooldown de " + TimeFormat.getTimerChat(cooldown.get(player.getUniqueId())) + "!";
	}
	
	public void removeCooldown(Player player) {
		cooldown.remove(player.getUniqueId());
	}
	
	public boolean isCooldown(Player player) {
		return cooldown.containsKey(player.getUniqueId());
	}
	
	public Integer getCooldown(Player player) {
		return cooldown.get(player.getUniqueId());
	}

}
