package de.afgmedia.ftspest.misc;

import de.afgmedia.ftspest.main.FTSPest;
import de.ftscraft.ftsutils.items.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

public class Misc {

    public static boolean CHECK_SIGN() {
        return false;
    }

    public static void initRecipes() {

        ItemStack syringe = new ItemBuilder(Material.TRIPWIRE_HOOK)
                .name("§6Sprize")
                .lore("§7Unbenutzt", "§7Kann einer Person blut abnehmen.")
                .sign("FTSPEST-SYRINGE-EMPTY")
                .build();

        ShapedRecipe syringeRecipe = new ShapedRecipe(new NamespacedKey(FTSPest.getInstance(), "FTSPEST-SYRINGE"), syringe)
                .shape("AAF", "AGA", "IAA")
                .setIngredient('A', Material.AIR)
                .setIngredient('F', Material.FLINT)
                .setIngredient('G', Material.GLASS)
                .setIngredient('I', Material.IRON_INGOT);
        Bukkit.addRecipe(syringeRecipe);

        ItemStack serum = new ItemBuilder(Material.WATER_BUCKET)
                .name("§6Serum")
                .lore("§7Kann zusammen mit Blut zu einer Impfung werden")
                .sign("FTSPEST-SERUM")
                .build();

        ShapedRecipe serumRecipe = new ShapedRecipe(new NamespacedKey(FTSPest.getInstance(), "FTSPEST-SERUM"), serum)
                .shape("AAC", "ACA", "BAA")
                .setIngredient('A', Material.AIR)
                .setIngredient('C', Material.COAL)
                .setIngredient('B', Material.BUCKET);
        Bukkit.addRecipe(serumRecipe);

    }

}
