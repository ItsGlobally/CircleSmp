package me.itsglobally.circleSmp.data;

import me.itsglobally.circleSmp.CircleSmp;
import me.itsglobally.circleSmp.utils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;

public class data {
    private static JavaPlugin plugin;
    private static CircleSmp instance;

    private static final Map<UUID, List<HashMap<UUID, BukkitTask>>> tpa = new HashMap<>();
    private static final Map<UUID, List<HashMap<UUID, BukkitTask>>> tpaHere = new HashMap<>();

    public static void setPlugin(JavaPlugin plugin) {
        data.plugin = plugin;
    }

    public static JavaPlugin getPlugin() {
        return plugin;
    }

    public static void setInstance(CircleSmp instance) {
        data.instance = instance;
    }

    public static CircleSmp getInstance() {
        return instance;
    }

    // Economy
    public static double getCoins(UUID u) {
        OfflinePlayer p = Bukkit.getOfflinePlayer(u);
        return CircleSmp.getEconomy().getBalance(p);
    }
    public static List<HashMap<UUID, BukkitTask>> getTpa(Player p) {
        return getTpa(p.getUniqueId());
    }
    public static List<HashMap<UUID, BukkitTask>> getTpa(UUID p) {
        return tpa.getOrDefault(p, List.of());
    }
    public static List<HashMap<UUID, BukkitTask>> getTpaHere(Player p) {
        return getTpaHere(p.getUniqueId());
    }
    public static List<HashMap<UUID, BukkitTask>> getTpaHere(UUID p) {
        return tpaHere.getOrDefault(p, List.of());
    }
    public static void addTpa(UUID p, UUID tg, tpaType tT) {
        switch (tT) {
            case tpaType.tpa -> {
                List<HashMap<UUID, BukkitTask>> cL = getTpa(p);
                BukkitTask newRq = new BukkitRunnable() {
                    @Override
                    public void run() {
                        remTpa(p, tg, true, tT);
                        Player p1 = Bukkit.getPlayer(p);
                        if (p1 != null) utils.send(p1, "&c你對" + Bukkit.getOfflinePlayer(tg).getName() + "發出的tpa請求已過期!");
                    }
                }.runTaskLater(plugin, 20 * 5 * 60L);

                HashMap<UUID, BukkitTask> newHm = new HashMap<>();
                newHm.put(tg, newRq);
                cL.add(newHm);
                tpa.put(p, cL);
            }
            case tpaType.tpahere -> {
                List<HashMap<UUID, BukkitTask>> cL = getTpaHere(p);
                BukkitTask newRq = new BukkitRunnable() {
                    @Override
                    public void run() {
                        remTpa(p, tg, true, tT);
                        Player p1 = Bukkit.getPlayer(p);
                        if (p1 != null) utils.send(p1, "&c你對" + Bukkit.getOfflinePlayer(tg).getName() + "發出的tpahere請求已過期!");
                    }
                }.runTaskLater(plugin, 20 * 5 * 60L);

                HashMap<UUID, BukkitTask> newHm = new HashMap<>();
                newHm.put(tg, newRq);
                cL.add(newHm);
                tpaHere.put(p, cL);
            }
        }
    }
    public static void addTpa(Player p, Player tg, tpaType tT) {
        addTpa(p.getUniqueId(), tg.getUniqueId(), tT);
    }
    public static void remTpa(UUID p, UUID tg, Boolean ended, tpaType tT) {
       switch (tT) {
           case tpaType.tpa -> {
               List<HashMap<UUID, BukkitTask>> cL = getTpa(p);
               if (cL.isEmpty()) return;
               BukkitTask found = null;
               HashMap<UUID, BukkitTask> map = null;
               for (HashMap<UUID, BukkitTask> map1 : cL) {
                   if (map1.containsKey(tg)) {
                       map = map1;
                       found = map1.get(tg);
                       break;
                   }
               }
               if (map == null || found == null) return;
               if (ended) found.cancel();
               cL.remove(map);
               tpa.put(p, cL);
           }
           case tpaType.tpahere -> {
               List<HashMap<UUID, BukkitTask>> cL = getTpaHere(p);
               if (cL.isEmpty()) return;
               BukkitTask found = null;
               HashMap<UUID, BukkitTask> map = null;
               for (HashMap<UUID, BukkitTask> map1 : cL) {
                   if (map1.containsKey(tg)) {
                       map = map1;
                       found = map1.get(tg);
                       break;
                   }
               }
               if (map == null || found == null) return;
               if (ended) found.cancel();
               cL.remove(map);
               tpaHere.put(p, cL);
           }
       }
    }
    public static void remTpa(Player p, Player tg, Boolean ended, tpaType tT) {
        remTpa(p.getUniqueId(), tg.getUniqueId(), ended, tT);
    }
}
