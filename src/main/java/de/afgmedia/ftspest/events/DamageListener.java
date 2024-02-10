package de.afgmedia.ftspest.events;

import de.afgmedia.ftspest.diseases.infections.InfectionType;
import de.afgmedia.ftspest.main.FTSPest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class DamageListener implements Listener {
    private final FTSPest plugin;

    public DamageListener(FTSPest plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            if (event instanceof org.bukkit.event.entity.EntityDamageByEntityEvent) {
                this.plugin.getInfectionManager().handleEvent(InfectionType.MOB_ATTACK, (Player) event.getEntity(), event);
                return;
            }
            if (event.getEntity() instanceof Player)
                this.plugin.getInfectionManager().handleEvent(InfectionType.DAMAGE, (Player) event.getEntity(), event);
        }
    }
}
