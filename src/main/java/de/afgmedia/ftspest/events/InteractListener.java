package de.afgmedia.ftspest.events;

import de.afgmedia.ftspest.diseases.Cure;
import de.afgmedia.ftspest.diseases.Disease;
import de.afgmedia.ftspest.main.FTSPest;
import de.afgmedia.ftspest.misc.Misc;
import de.afgmedia.ftspest.misc.Values;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.CauldronLevelChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;

public class InteractListener implements Listener {
    private FTSPest plugin;

    public InteractListener(FTSPest plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, (Plugin) plugin);
    }

    @EventHandler
    public void onCauldronFill(CauldronLevelChangeEvent event) {

        if(event.getEntity() instanceof Player) {

            Player p = (Player) event.getEntity();

            ItemStack itemInHand = p.getInventory().getItemInMainHand();

            Misc.PestItemType pestItemType = Misc.isPestItem(itemInHand);

            if(pestItemType == Misc.PestItemType.SERUM) {
                plugin.getInfectionManager().addCauldron(event.getBlock().getLocation());
            } else if(pestItemType == Misc.PestItemType.SYRINGE_WITH_BLOOD) {

                if(!plugin.getInfectionManager().getCauldrons().contains(event.getBlock().getLocation())) {
                    return;
                }



            }


        }

    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {

        if(event.getItem() == null) {
            return;
        }

        if(event.getItem().getType() == Material.STONE_SWORD) {
            ItemMeta im = event.getItem().getItemMeta();
            im.setDisplayName(Values.NAME_ITEM_VACCINE);
            im.setLore(Arrays.asList("Pest",Values.LORE_IDENTIFIER));
            event.getItem().setItemMeta(im);
        }


        if (event.getAction() == Action.RIGHT_CLICK_AIR &&
                event.getPlayer().isSneaking()) {
            ItemStack is = event.getItem();
            if (is.getType().isEdible())
                return;

            if(Misc.isPestItem(is) == Misc.PestItemType.NOT_PEST_ITEM) {
                System.out.println("Stop!");
                return;
            }

            for (Cure cure : this.plugin.getInfectionManager().getCures()) {
                if (cure.getCureItem().isSimilar(is)) {

                    event.getPlayer().sendMessage("§7[§bFTS-Pest§7] Du wurdest geheilt!");
                    event.getPlayer().getInventory().getItemInMainHand().setAmount(event.getPlayer().getInventory().getItemInMainHand().getAmount() - 1);
                }
                if (cure.cure(event.getPlayer())) {
                }
            }

            //Impfungen
            if(Misc.isPestItem(is) == Misc.PestItemType.VACCINE) {
                System.out.println("hi");
                String pestName = is.getItemMeta().getLore().get(is.getItemMeta().getLore().size()-2);
                System.out.println(pestName);
                Disease disease = plugin.getInfectionManager().getDisease(pestName);

                if(disease != null) {
                    System.out.println(disease.getName());
                    plugin.getInfectionManager().getUser(event.getPlayer()).addImmunity(disease, 0.5);
                    is.setAmount(is.getAmount()-1);

                } else System.out.println("tschüssi");
            } else System.out.println("bye");

        }
    }
}

