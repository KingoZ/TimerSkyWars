package me.skywars.nms;

import java.util.List;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import net.minecraft.server.v1_8_R3.Packet;

public class PacketReader {

	Player player;
	Channel channel;

	public PacketReader(Player player) {
		this.player = player;
	}

	public void inject() {
		CraftPlayer craftPlayer = (CraftPlayer)player;
		channel = craftPlayer.getHandle().playerConnection.networkManager.channel;
		channel.pipeline().addAfter("decoder", "PacketInjector", new MessageToMessageDecoder<Packet<?>>() {
			@Override
			protected void decode(ChannelHandlerContext context, Packet<?> packet, List<Object> list) throws Exception {
				list.add(packet);
				readPacket(packet);
				
			}
		});
	}
	
	public void uninject() {
		channel.pipeline().remove("PacketInjector");
	}
	
	public void readPacket(Packet<?> packet) {
		
	}
	
}
