package de.afgmedia.ftspest.events;

import de.afgmedia.ftspest.diseases.Cure;
import de.afgmedia.ftspest.main.FTSPest;

import de.afgmedia.ftspest.misc.Values;
import de.ftscraft.ftsutils.items.ItemBuilder;
import de.ftscraft.ftsutils.items.ItemReader;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;

public class CraftListener implements Listener {
    private final FTSPest plugin;

    public CraftListener(FTSPest plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onCraft(CraftItemEvent event) {

        ItemStack result = event.getInventory().getResult();
        Player p = (Player) event.getWhoClicked();

        for (Cure cure : plugin.getInfectionManager().getCures()) {
            if (cure.getCureItem().getItemMeta().getDisplayName().equals(result.getItemMeta().getDisplayName()) && !cure.getDisease().getName().equals("Beinbruch") && !cure.getDisease().getName().equals("Verbrennung")) {
                if (!p.hasPermission("ftspest.craftcure")) {
                    p.sendMessage(Values.PREFIX + "Dieses Item können nur Leute mit dem Medicus Rang herstellen!");
                    event.setCancelled(true);
                }
            }
        }

    }

    @EventHandler
    public void onCraftPrepare(PrepareItemCraftEvent event) {
        ItemStack[] matrix = event.getInventory().getMatrix();
        int c = 0;
        String blood = null;
        boolean serum = false;
        for (ItemStack itemStack : matrix) {
            if (itemStack != null) {
                c++;
                String sign = ItemReader.getSign(itemStack);
                if (sign != null) {
                    if (sign.equals("FTSPEST-SERUM")) {
                        serum = true;
                    } else if(sign.startsWith("FTSPEST-SYRINGE") && !sign.equals("FTSPEST-SYRINGE-EMPTY")) {
                        blood = sign.replace("FTSPEST-SYRINGE-", "");
                    }
                }
            }
        }
        if(c == 2 && blood != null && serum) {
            ItemStack result = new ItemBuilder(Material.TRIPWIRE_HOOK)
                    .sign("FTSPEST-VACCINE-"+blood)
                    .name("§6"+blood+"-Impfung")
                    .lore("§7Impft gegen " + blood)
                    .shiny()
                    .build();
            event.getInventory().setResult(result);
        }
    }

}