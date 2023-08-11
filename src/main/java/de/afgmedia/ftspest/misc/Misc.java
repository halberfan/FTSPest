package de.afgmedia.ftspest.misc;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Misc {

    public static PestItemType isPestItem(ItemStack is) {

        ItemMeta im = is.getItemMeta();

        if(im.hasLore()) {

            String lastComponent = im.getLore().get(im.getLore().size()-1);

            System.out.println(lastComponent);
            if(lastComponent.equalsIgnoreCase(Values.LORE_IDENTIFIER)) {
                return getPestItemType(im);
            }

        }

        return PestItemType.NOT_PEST_ITEM;

    }

    private static PestItemType getPestItemType(ItemMeta im) {

        switch (im.getDisplayName()) {
            case Values.NAME_ITEM_VACCINE:
                return PestItemType.VACCINE;
            case Values.NAME_ITEM_SERUM:
                return PestItemType.SERUM;
            case Values.NAME_ITEM_SYRINGE:
                return PestItemType.SYRINGE;
            case Values.NAME_ITEM_SYRINGE_WITH_BLOOD:
                return PestItemType.SYRINGE_WITH_BLOOD;
            default:
                return PestItemType.OTHER;
        }

    }

    public enum PestItemType {
        SYRINGE, VACCINE, SERUM, OTHER, SYRINGE_WITH_BLOOD, NOT_PEST_ITEM
    }

}
