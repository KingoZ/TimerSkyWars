package me.skywars.nms;

import java.lang.reflect.Constructor;

import org.bukkit.entity.Player;

public class TitleAPI extends Reflection {

    private String title, subTitle;
    private int fadeIn, fadeOut, time;

    public TitleAPI(String title, String subTitle, int fadeIn, int time, int fadeOut) {
        this.fadeIn = fadeIn;
        this.fadeOut = fadeOut;
        this.subTitle = subTitle;
        this.time = time;
        this.title = title;
    }

    public String[] getMessages() {
        return new String[] {title, subTitle};
    }

    public void send(Player player) {
        try {
            Object enumTitle = getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0]
                    .getField("TITLE").get(null);
            Object enumSubTitle = getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0]
                    .getField("SUBTITLE").get(null);

            Object titleChat = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0]
                    .getMethod("a", String.class).invoke(null, "{\"text\":\"" + title + "\"}");
            Object subTitleChat = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0]
                    .getMethod("a", String.class).invoke(null, "{\"text\":\"" + subTitle + "\"}");

            Constructor<?> titleConstructor = getNMSClass("PacketPlayOutTitle")
                    .getConstructor(getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0],
                            getNMSClass("IChatBaseComponent"), int.class, int.class, int.class);

            Object packetTitle = titleConstructor.newInstance(enumTitle, titleChat, fadeIn, time, fadeOut);
            Object packetSubTitle = titleConstructor.newInstance(enumSubTitle, subTitleChat, fadeIn, time, fadeOut);

            sendPacket(player, packetTitle);
            sendPacket(player, packetSubTitle);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
