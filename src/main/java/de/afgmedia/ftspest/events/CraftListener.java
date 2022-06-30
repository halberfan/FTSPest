package de.afgmedia.ftspest.events;

import de.afgmedia.ftspest.diseases.Cure;
import de.afgmedia.ftspest.main.FTSPest;

import java.util.Iterator;

import de.afgmedia.ftspest.misc.Values;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class CraftListener implements Listener {
    private FTSPest plugin;

    public CraftListener(FTSPest plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, (Plugin) plugin);
    }

    @EventHandler
    public void onCraft(CraftItemEvent event) {

        ItemStack result = event.getInventory().getResult();
        Player p = (Player) event.getWhoClicked();

        for (Cure cure : plugin.getInfectionManager().getCures()) {
            if (cure.getCureItem().isSimilar(result)) {
                if (!p.hasPermission("ftspest.craftcure")) {
                    p.sendMessage(Values.PREFIX + "Dieses Item können nur Leute mit dem Medicus Rang herstellen!");
                    event.setCancelled(true);
                }
            }
        }

    }
}