package de.afgmedia.ftspest.events;

import de.afgmedia.ftspest.diseases.Cure;
import de.afgmedia.ftspest.diseases.infections.InfectionType;
import de.afgmedia.ftspest.main.FTSPest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.plugin.Plugin;

public class PlayerItemConsumeListener implements Listener {
    private FTSPest plugin;

    public PlayerItemConsumeListener(FTSPest plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, (Plugin) plugin);
    }

    @EventHandler
    public void onItemConsume(PlayerItemConsumeEvent event) {
        for (Cure cure : this.plugin.getInfectionManager().getCures()) {
            if (cure.getCureItem().isSimilar(event.getItem())) {
                if (cure.cure(event.getPlayer())) {
                    event.getPlayer().sendMessage("§7[§bFTS-Pest§7] Du wurdest geheilt!");
                    return;
                }
                event.setCancelled(true);
                event.getPlayer().sendMessage("§7[§bFTS-Pest§7] Du legst die Medizin wieder weg als du bemerkst dass es nicht die ist, die du brauchst");
                return;
            }
        }
        this.plugin.getInfectionManager().handleEvent(InfectionType.CONSUME, event.getPlayer(), event);
    }
}
