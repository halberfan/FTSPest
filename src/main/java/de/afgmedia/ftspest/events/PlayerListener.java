package de.afgmedia.ftspest.events;

import de.afgmedia.ftspest.diseases.Disease;
import de.afgmedia.ftspest.main.FTSPest;
import de.afgmedia.ftspest.misc.PestUser;
import de.afgmedia.ftspest.misc.Values;
import de.ftscraft.ftsutils.items.ItemBuilder;
import de.ftscraft.ftsutils.items.ItemReader;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import java.util.logging.Level;

public class PlayerListener implements Listener {
    private final FTSPest plugin;

    public PlayerListener(FTSPest plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        this.plugin.getPestIO().loadPlayerData(event.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        PestUser user = this.plugin.getInfectionManager().getUsers().get(event.getPlayer());
        this.plugin.getPestIO().savePlayerData(user);
        this.plugin.getInfectionManager().getUsers().remove(event.getPlayer());
    }

    @EventHandler
    public void onClick(PlayerInteractEntityEvent event) {

        if (event.getRightClicked() instanceof Player) {

            Player p = event.getPlayer();
            ItemStack is = p.getInventory().getItemInMainHand();

            String sign = ItemReader.getSign(is.getItemMeta());
            if (sign != null && sign.equals("FTSPEST-SYRINGE-EMPTY")) {
                Player t = (Player) event.getRightClicked();

                PestUser targetUser = plugin.getInfectionManager().getUser(t);
                if (targetUser.isInfected()) {

                    Disease disease = targetUser.getDisease();
                    ItemBuilder syringe = new ItemBuilder(p.getInventory().getItemInMainHand())
                            .name("§6Spritze mit " + disease.getName() + "-Blut")
                            .lore("§7Blut von " + p.getName(), "§7Kann mit Serum zu einer Impfung verwandelt werden")
                            .sign("FTSPEST-SYRINGE-"+disease.getName());
                    p.getInventory().setItemInMainHand(syringe.build());
                    t.damage(3);

                }
            } else if(sign != null && sign.startsWith("FTSPEST-VACCINE-")) {

                Player t = (Player) event.getRightClicked();

                PestUser targetUser = plugin.getInfectionManager().getUser(t);

                String diseaseName = sign.replace("FTSPEST-VACCINE-", "");
                Disease disease = plugin.getInfectionManager().getDisease(diseaseName);
                if (disease == null) {
                    plugin.getLogger().log(Level.WARNING, "Tried to use Vaccine " + diseaseName + " but the disease doesn't exist.");
                    return;
                }
                targetUser.addImmunity(disease, 0.5);
                is.setAmount(0);
                t.sendMessage(Values.PREFIX + "Du wurdest gegen §c" + disease.getName() + " §7geimpft.");
                p.sendMessage(Values.PREFIX + "Du hast §c" + t.getName() + " §7geimpft.");

            }

        }

    }

}
