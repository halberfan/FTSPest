package de.afgmedia.ftspest.events;

import de.afgmedia.ftspest.main.FTSPest;
import de.afgmedia.ftspest.misc.InfectionManager;
import de.afgmedia.ftspest.misc.PestUser;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathListener implements Listener {

    private final FTSPest plugin;

    public DeathListener(FTSPest plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {

        Player p = event.getEntity();
        PestUser u = plugin.getInfectionManager().getUser(p);

        if (u == null) {
            return;
        }

        u.reduceSicknessLevel();

        if (InfectionManager.loosingImmunities())
            u.resetImmunity();

    }

}
