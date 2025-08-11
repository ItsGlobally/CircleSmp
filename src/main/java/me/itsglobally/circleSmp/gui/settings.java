package me.itsglobally.circleSmp.gui;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.components.GuiType;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import me.itsglobally.circleSmp.data;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class settings implements gui {

    @Override
    public void open(Player p) {
        boolean tpaStatus = data.getTpYesOrNo(p.getUniqueId());

        Material woolMaterial = tpaStatus ? Material.GREEN_WOOL : Material.RED_WOOL;
        String woolName = tpaStatus ? "Other players can send tpa request to you." : "Other players can't send tpa request to you.";

        Gui gui = Gui.gui()
                .title(Component.text("Settings"))
                .type(GuiType.CHEST)
                .rows(3)
                .create();
        GuiItem acceptTpa = ItemBuilder.from(woolMaterial)
                .name(Component.text(woolName))
                .asGuiItem(event -> {
                    event.setCancelled(true);
                    boolean newStatus = !data.getTpYesOrNo(p.getUniqueId());
                    data.setTpYesOrNo(p.getUniqueId(), newStatus);
                    p.sendMessage(woolName);
                    open(p);
                });
        boolean tpautos = data.gettpAuto(p.getUniqueId());

        Material tpautowool = tpautos ? Material.GREEN_WOOL : Material.RED_WOOL;
        String tpautomsg = tpautos ? "Automatically accept tpa requests." : "Don't automatically accept tpa requests.";
        GuiItem tpAuto = ItemBuilder.from(tpautowool)
                .name(Component.text(woolName))
                .asGuiItem(event -> {
                    event.setCancelled(true);
                    boolean newStatus = !data.gettpAuto(p.getUniqueId());
                    data.settpAuto(p.getUniqueId(), newStatus);
                    p.sendMessage(tpautomsg);
                    open(p);
                });

        gui.getFiller().fill(ItemBuilder.from(Material.BLACK_STAINED_GLASS_PANE)
                .name(Component.text(" "))
                .asGuiItem(e -> e.setCancelled(true)));

        gui.setItem(1, 1, acceptTpa);
        gui.setItem(1, 3, tpAuto);

        gui.open(p);
    }
}
