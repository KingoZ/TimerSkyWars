package me.skywars.kit;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;

public class KitAPI {
	
	private Map<UUID, String> habilidade = new HashMap<>();
	
	public void set(Player player, String name) {
		habilidade.put(player.getUniqueId(), name);
	}
	
	public String getName(Player player) {
		return habilidade.get(player.getUniqueId());
	}
	
	public void remove(Player player) {
		habilidade.remove(player.getUniqueId());
	}

}
