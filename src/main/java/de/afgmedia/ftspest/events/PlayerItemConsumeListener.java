package de.afgmedia.ftspest.events;

import de.afgmedia.ftspest.diseases.Cure;
import de.afgmedia.ftspest.diseases.infections.InfectionType;
import de.afgmedia.ftspest.main.FTSPest;
import de.afgmedia.ftspest.misc.Misc;
import de.afgmedia.ftspest.misc.Values;
import de.ftscraft.ftsutils.items.ItemReader;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;

public class PlayerItemConsumeListener implements Listener {
    private final FTSPest plugin;

    public PlayerItemConsumeListener(FTSPest plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onItemConsume(PlayerItemConsumeEvent event) {
        for (Cure cure : this.plugin.getInfectionManager().getCures()) {
            String sign;
            if (Misc.CHECK_SIGN()) {
                if ((sign = ItemReader.getSign(event.getItem())) != null && sign.equals("FTSPEST-" + cure.getName().toUpperCase())) {
                    if (cure.cure(event.getPlayer())) {
                        event.getPlayer().sendMessage(Values.MESSAGE_HEALED);
                        return;
                    }
                    event.setCancelled(true);
                    event.getPlayer().sendMessage(Values.PREFIX + "Du legst die Medizin wieder weg als du bemerkst dass es nicht die ist, die du brauchst");
                    return;
                }
            } else {
                if (cure.getCureItem().getItemMeta().getDisplayName().equals(event.getItem().getItemMeta().getDisplayName())) {
                    if (cure.cure(event.getPlayer())) {
                        event.getPlayer().sendMessage(Values.MESSAGE_HEALED);
                        return;
                    }
                    event.setCancelled(true);
                    event.getPlayer().sendMessage(Values.PREFIX + "Du legst die Medizin wieder weg als du bemerkst dass es nicht die ist, die du brauchst");
                    return;
                }
            }
        }
        this.plugin.getInfectionManager().handleEvent(InfectionType.CONSUME, event.getPlayer(), event);
    }
}
