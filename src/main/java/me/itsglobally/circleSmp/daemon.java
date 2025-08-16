package me.itsglobally.circleSmp;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.UUID;

public class daemon extends WebSocketClient {

    private final Gson gson = new Gson();

    public daemon(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        Bukkit.getLogger().info("Connected to server");
        JsonObject obj = basic(null);
        obj.addProperty("message", "connected");
        send(gson.toJson(obj));
    }

    @Override
    public void onMessage(String message) {
        try {
            JsonObject json = gson.fromJson(message, JsonObject.class);
            String server = json.has("server") ? json.get("server").getAsString() : "";
            String cmd = json.has("cmd") ? json.get("cmd").getAsString() : "";
            if (cmd.isEmpty()) return;
            switch (cmd) {
                case "ban" :
                    UUID u1;
                    if (json.has("player")) {
                        try {
                            u1 = UUID.fromString(json.get("player").getAsString());
                        } catch (Exception e) {
                            JsonObject obj1 = basic("1");
                            obj1.addProperty("error", "notuuid");
                            obj1.addProperty("code", "1");
                            return;
                        }
                        OfflinePlayer p1 = Bukkit.getOfflinePlayer(u1);
                        Bukkit.getBanList(BanList.Type.NAME).addBan(
                                p1.getName(),
                                "Banned",
                                null, // null = no expiration
                                null  // null = no source
                        );
                        JsonObject obj1 = basic();
                        obj1.addProperty("player", u1.toString());
                        obj1.addProperty("banned", p1.isBanned());
                        send(gson.toJson(obj1));
                    }
                case "unban" :
                    UUID u2;
                    if (json.has("player")) {
                        try {
                            u2 = UUID.fromString(json.get("player").getAsString());
                        } catch (Exception e) {
                            JsonObject obj2 = basic("1");
                            obj2.addProperty("error", "notuuid");
                            obj2.addProperty("code", "1");
                            return;
                        }
                        OfflinePlayer p2 = Bukkit.getOfflinePlayer(u2);
                        Bukkit.getBanList(BanList.Type.NAME).pardon(
                                p2.getName()
                        );
                        JsonObject obj2 = basic();
                        obj2.addProperty("player", u2.toString());
                        obj2.addProperty("banned", p2.isBanned());
                        send(gson.toJson(obj2));
                    }
                case "coins":
                    UUID u3;
                    if (json.has("player")) {
                        try {
                            u3 = UUID.fromString(json.get("player").getAsString());
                        } catch (Exception e) {
                            JsonObject obj3 = basic("1");
                            obj3.addProperty("error", "notuuid");
                            obj3.addProperty("code", "1");
                            return;
                        }
                        OfflinePlayer p3 = Bukkit.getOfflinePlayer(u3);
                        JsonObject obj3 = basic();
                        obj3.addProperty("player", u3.toString());
                        obj3.addProperty("coins", data.getCoins(p3.getUniqueId()));
                        send(gson.toJson(obj3));
                    }
                default:
                    return;
            }
        } catch (Exception e) {
            Bukkit.getLogger().info("Invalid JSON: " + message);
            e.printStackTrace();
            JsonObject obj = basic(null);
            obj.addProperty("message", "fuck u not json r u retarded");
            send(gson.toJson(obj));
        }
    }
    private static JsonObject basic(String code) {
        JsonObject obj = new JsonObject();
        obj.addProperty("server", "smp");
        if (code != null) {
            obj.addProperty("code", "0");
        }
        return obj;
    }
    private static JsonObject basic() {
        JsonObject obj = new JsonObject();
        obj.addProperty("server", "smp");
        obj.addProperty("code", "0");
        return obj;
    }
    @Override
    public void onClose(int code, String reason, boolean remote) {
        Bukkit.getLogger().warning("Connection closed: " + reason);
    }

    @Override
    public void onError(Exception ex) {
        ex.printStackTrace();
    }

    public static void main(String[] args) throws Exception {
        daemon client = new daemon(new URI("ws://localhost:8080"));
        client.connectBlocking();
    }
}
