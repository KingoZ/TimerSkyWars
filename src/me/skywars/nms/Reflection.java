package me.skywars.nms;

import java.lang.reflect.Field;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.skywars.Main;
import net.minecraft.server.v1_8_R3.Packet;

public class Reflection {
	
	public Class<?> getNMSClass(String nmsClassName) {
	    String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
	    try {
	        return Class.forName("net.minecraft.server." + version + "." + nmsClassName);
	    } catch (ClassNotFoundException | ArrayIndexOutOfBoundsException e) {
	        return null;
	    }
	}
	
	public void setValue(Object obj, String name, Object value) {
		try {
			Field field = obj.getClass().getDeclaredField(name);
			field.setAccessible(true);
			field.set(obj, value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Object getValue(Object obj, String name) {
		try {
			Field field = obj.getClass().getDeclaredField(name);
			field.setAccessible(true);
			return field.get(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void sendPacket(Packet<?> packet) {
		for (Player player : Main.getInstance().getOnlinePlayers())
			sendPacket(player, packet);
	}

	public void sendPacket(Player player, Object packet) {
	    try {
	        Object entityPlayer = player.getClass().getMethod("getHandle").invoke(player);
	        Object playerConnection = entityPlayer.getClass().getField("playerConnection").get(entityPlayer);
	        playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(playerConnection, packet);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

}
