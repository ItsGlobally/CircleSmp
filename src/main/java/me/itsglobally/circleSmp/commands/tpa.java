package me.itsglobally.circleSmp.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class tpa implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        if (!(commandSender instanceof Player p)) {
            return true;
        }
        switch (s) {
            case "tpa" -> {
                tpa(p);
            }
            case "tpaccept" -> {
                tpaccept(p);
            }
            case "tpdeny" -> {
                tpdeny(p);
            }
            case "tpcancel" -> {
                tpcancel(p);
            }

        }
        return true;
    }
    private static void tpa(Player p) {

    }
    private static void tpaccept(Player p) {

    }
    private static void tpdeny(Player p) {

    }
    private static void tpcancel(Player p) {

    }
}
