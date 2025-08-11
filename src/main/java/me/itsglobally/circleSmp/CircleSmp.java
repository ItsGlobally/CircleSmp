package me.itsglobally.circleSmp;

import org.bukkit.plugin.java.JavaPlugin;

import java.net.URI;


public final class CircleSmp extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        daemon wssrv;
        data.setPlugin(this);
        try {
            wssrv = new daemon(new URI("ws://172.18.0.1:25502"));
            wssrv.connectBlocking();
        } catch (Exception e) {
            getServer().getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
