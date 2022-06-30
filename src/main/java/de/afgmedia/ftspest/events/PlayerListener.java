package de.afgmedia.ftspest.events;

import de.afgmedia.ftspest.main.FTSPest;
import de.afgmedia.ftspest.misc.PestUser;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

public class PlayerListener implements Listener {
  private FTSPest plugin;
  
  public PlayerListener(FTSPest plugin) {
    this.plugin = plugin;
    plugin.getServer().getPluginManager().registerEvents(this, (Plugin)plugin);
  }
  
  @EventHandler
  public void onJoin(PlayerJoinEvent event) {
    this.plugin.getPestIO().loadPlayerData(event.getPlayer());
  }
  
  @EventHandler
  public void onQuit(PlayerQuitEvent event) {
    PestUser user = (PestUser)this.plugin.getInfectionManager().getUsers().get(event.getPlayer());
    this.plugin.getPestIO().savePlayerData(user);
    this.plugin.getInfectionManager().getUsers().remove(event.getPlayer());
  }
}
