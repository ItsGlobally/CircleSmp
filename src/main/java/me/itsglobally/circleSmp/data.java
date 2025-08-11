package me.itsglobally.circleSmp;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public class data {
    private static HashMap<UUID, Boolean> tpYesOrNo = new HashMap<>();
    public static void setTpYesOrNo(UUID uuid, Boolean status) {
        tpYesOrNo.put(uuid, status);
    }
    public static Boolean getTpYesOrNo(UUID uuid) {
        return tpYesOrNo.getOrDefault(uuid, false);
    }
    private static HashMap<UUID, Boolean> tpAuto = new HashMap<>();
    public static void settpAuto(UUID uuid, Boolean status) {
        tpAuto.put(uuid, status);
    }
    public static Boolean gettpAuto(UUID uuid) {
        return tpAuto.getOrDefault(uuid, false);
    }
    public static JavaPlugin plugin;

    public static void setPlugin(JavaPlugin plugin) {
        data.plugin = plugin;
    }

    public static JavaPlugin getPlugin() {
        return plugin;
    }
}
