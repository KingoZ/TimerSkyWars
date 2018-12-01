package me.skywars.nms;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_8_R3.MathHelper;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityTeleport;

public class PacketEntityTeleport extends Reflection {
	
	int entityID;
	Player player;
	
	public PacketEntityTeleport(Player player) {
		this.player = player;
	}
	
	public void teleport(Location location) {
		entityID = player.getEntityId();
		PacketPlayOutEntityTeleport packet = new PacketPlayOutEntityTeleport();
		setValue(packet, "a", entityID);
		Bukkit.getConsoleSender().sendMessage("ID: " + getValue(packet, "a"));
		setValue(packet, "b", getFixLocation(location.getX()));
		Bukkit.getConsoleSender().sendMessage("Teleport: " + getValue(packet, "b"));
		setValue(packet, "c", getFixLocation(location.getY()));
		Bukkit.getConsoleSender().sendMessage("Teleport: " + getValue(packet, "c"));
		setValue(packet, "d", getFixLocation(location.getZ()));
		Bukkit.getConsoleSender().sendMessage("Teleport: " + getValue(packet, "d"));
		setValue(packet, "e", getFixRotation(location.getYaw()));
		Bukkit.getConsoleSender().sendMessage("Teleport: " + getValue(packet, "e"));
		setValue(packet, "f", getFixRotation(location.getPitch()));
		Bukkit.getConsoleSender().sendMessage("Teleport: " + getValue(packet, "f"));
		sendPacket(player, packet);
	}
	
	private int getFixLocation(double pos) {
		return (int)MathHelper.floor(pos*32.0D);
	}
	
	private byte getFixRotation(double yawpitch) {
		return (byte)((int)(yawpitch * 256.0F / 360.0F));
	}

}
