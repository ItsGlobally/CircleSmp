package me.itsglobally.circleSmp;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.entity.Player;

public class utils {

    public static void send(Player p, String s) {
        Component message = LegacyComponentSerializer.legacyAmpersand().deserialize(s);
        p.sendMessage(message);
    }
    public static void sendComponent(Player p, Component s) {
        p.sendMessage(s);
    }
    public static void sendActionBar(Player p, String s) {
        Component message = LegacyComponentSerializer.legacyAmpersand().deserialize(s);
        p.sendActionBar(message);
    }
    public static void sendActionBarComponent(Player p, Component s) {
        p.sendActionBar(s);
    }
    public static String convertLegacyColorCode(String s) {
        return LegacyComponentSerializer.legacyAmpersand().deserialize(s).content();
    }
}
