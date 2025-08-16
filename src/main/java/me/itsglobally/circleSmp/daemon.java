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
                    if (json.has("player")) {
                        Bukkit.getScheduler().runTask(data.getPlugin(), () -> {
                            Bukkit.dispatchCommand(
                                    Bukkit.getConsoleSender(),
                                    "ban " + json.get("player").getAsString()
                            );
                        });
                    }
                    break;
                case "unban" :
                    if (json.has("player")) {
                        Bukkit.getScheduler().runTask(data.getPlugin(), () -> {
                            Bukkit.dispatchCommand(
                                    Bukkit.getConsoleSender(),
                                    "pardon " + json.get("player").getAsString()
                            );
                        });
                    }
                    break;
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
