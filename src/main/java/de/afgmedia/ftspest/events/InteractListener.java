package de.afgmedia.ftspest.events;

import de.afgmedia.ftspest.diseases.Cure;
import de.afgmedia.ftspest.main.FTSPest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class InteractListener implements Listener {
    private FTSPest plugin;

    public InteractListener(FTSPest plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, (Plugin) plugin);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_AIR &&
                event.getPlayer().isSneaking()) {
            ItemStack is = event.getItem();
            if (is.getType().isEdible())
                return;
            for (Cure cure : this.plugin.getInfectionManager().getCures()) {
                if (cure.getCureItem().isSimilar(is))
                    if (cure.cure(event.getPlayer())) {
                        event.getPlayer().sendMessage("§7[§bFTS-Pest§7] Du wurdest geheilt!");
                        event.getPlayer().getInventory().getItemInMainHand().setAmount(event.getPlayer().getInventory().getItemInMainHand().getAmount() - 1);
                    }
            }
        }
    }
}

