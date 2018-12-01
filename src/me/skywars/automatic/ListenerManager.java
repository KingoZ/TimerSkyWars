package me.skywars.automatic;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

import me.skywars.Main;

public class ListenerManager {
	
	public static void loadListener() {
		for (Class<?> Classes : ClassGetter.getClassesForPackage(Main.getPlugin(Main.class), "me.skywars")) {
			try {
				if (Listener.class.isAssignableFrom(Classes)) {
					Listener listener = (Listener) Classes.newInstance();
					Bukkit.getPluginManager().registerEvents(listener, Main.getPlugin(Main.class));
				}
			} catch (Exception e) {
			}
		}
	}
}
