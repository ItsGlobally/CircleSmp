package me.itsglobally.circleSmp.commands;

import me.itsglobally.circleSmp.data;
import me.itsglobally.circleSmp.utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class tpaCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        if (!(commandSender instanceof Player p)) {
            return true;
        }
        Player tg = Bukkit.getPlayerExact(strings[0]);
        if (tg == null) {
            return true;
        }
        switch (s) {
            case "tpa" -> {
                tpa(p, tg);
            }
            case "tpahere" -> {
                tpahere(p, tg);
            }
            case "tpaccept" -> {
                tpaccept(p, tg);
            }
            case "tpdeny" -> {
                tpdeny(p, tg);
            }
            case "tpcancel" -> {
                tpcancel(p, tg);
            }
            case "tpyes" -> {
                tpyes(p, tg);
            }
            case "tpno" -> {
                tpno(p, tg);
            }
            case "tpauto" -> {
                tpauto(p, tg);
            }

        }
        return true;
    }

    private void tpahere(Player p, Player tg) {

    }

    private void tpcancel(Player p, Player tg) {

    }

    private void tpdeny(Player p, Player tg) {
        data.remTpa(p, tg, false);
        utils.sendActionBar(p,"&c" + tg.getName() + "已拒絕請求!");
        utils.sendActionBar(tg,"&a拒絕" + p.getName() + "的請求!");
    }

    private void tpaccept(Player p, Player tg) {
        List<HashMap<UUID, BukkitTask>> cL= data.getTpa(p);
        for (HashMap<UUID, BukkitTask> L : cL) {
            if (L.containsKey(tg.getUniqueId())) data.remTpa(p, tg, false);
            tg.teleport(p.getLocation());
            utils.sendActionBar(p,"&a" + tg.getName() + "已傳送到你的位置!");
            utils.sendActionBar(tg,"&a你已傳送到"+ p.getName() + "的位置!");
            return;
        }
        utils.send(p, "&c" + tg.getName() + "沒有對你發送請求!");
    }

    private void tpauto(Player p, Player tg) {

    }

    private void tpno(Player p, Player tg) {

    }

    private void tpyes(Player p, Player tg) {

    }
    private static void tpa(Player p, Player tg) {
        if (p == tg) {
            utils.send(p, "&c你不能對自己發送請求!");
        }
        data.addTpa(p, tg);
        utils.sendActionBar(p,"&a已發送請求! 他有5分鐘的時間接受!");
    }
}
