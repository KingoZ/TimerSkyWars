package me.skywars.automatic;

import org.bukkit.entity.Player;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import me.skywars.Main;

public class TeleportServer {
	
	public static void sendTeleport(Player player, String server) {
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeUTF("Connect");
		out.writeUTF(server);
		player.sendPluginMessage(Main.getInstance(), "BungeeCord", out.toByteArray());
		player.sendMessage("§aRedirecionando ao " + server.toUpperCase() + "...");
	}

}
