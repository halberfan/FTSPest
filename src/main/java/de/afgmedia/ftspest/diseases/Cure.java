package de.afgmedia.ftspest.diseases;

import de.afgmedia.ftspest.main.FTSPest;
import de.afgmedia.ftspest.misc.PestUser;

import java.util.Arrays;

import de.ftscraft.ftsutils.items.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

public class Cure {
    private final FTSPest plugin;

    private ItemStack cureItem;

    private final String name;

    private final String description;

    private final Disease disease;

    public Cure(FTSPest plugin, String name, String description, Disease disease) {
        this.plugin = plugin;
        this.name = name;
        this.description = description;
        this.disease = disease;
        disease.setCure(this);
        plugin.getInfectionManager().getCures().add(this);
    }

    public void createCraftingRecipe(String recipeString) {
        String[] s = recipeString.split(",");
        Recipe finalRecipe = null;
        if (s[0].equalsIgnoreCase("s")) {
            if (s.length == 10) {
                ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(this.plugin, "FTSPEST-" + this.name.toUpperCase().replace("§", "")), this.cureItem);
                recipe.shape("ABC", "DEF", "GHI");
                recipe.setIngredient('A', Material.getMaterial(s[1]));
                recipe.setIngredient('B', Material.getMaterial(s[2]));
                recipe.setIngredient('C', Material.getMaterial(s[3]));
                recipe.setIngredient('D', Material.getMaterial(s[4]));
                recipe.setIngredient('E', Material.getMaterial(s[5]));
                recipe.setIngredient('F', Material.getMaterial(s[6]));
                recipe.setIngredient('G', Material.getMaterial(s[7]));
                recipe.setIngredient('H', Material.getMaterial(s[8]));
                recipe.setIngredient('I', Material.getMaterial(s[9]));
                finalRecipe = recipe;
            }
        } else if (s[0].equalsIgnoreCase("u")) {
            ShapelessRecipe recipe = new ShapelessRecipe(new NamespacedKey(this.plugin, "FTSPEST-" + this.name.toUpperCase().replace("§", "")), this.cureItem);
            for (int i = 0; i < s.length; i++) {
                if (i != 0)
                    recipe.addIngredient(Material.getMaterial(s[i]));
            }
            finalRecipe = recipe;
        }

        if (finalRecipe != null && this.plugin.getServer().getRecipe(new NamespacedKey(this.plugin, "FTSPEST-" + this.name.toUpperCase().replace("§", ""))) == null)
            this.plugin.getServer().addRecipe(finalRecipe);

    }

    public ItemStack getCureItem() {
        return this.cureItem;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public Disease getDisease() {
        return this.disease;
    }

    public boolean cure(Player player) {
        PestUser user = (PestUser) this.plugin.getInfectionManager().getUsers().get(player);
        if (!user.isInfected())
            return false;
        if (user.getDisease().equals(this.disease)) {
            user.cure();
            return true;
        }
        return false;
    }

    public void createItem(String cureItemName, String cureItemLore, Material mat) {
        this.cureItem = new ItemBuilder(mat)
                .lore(cureItemLore.split("&n"))
                .name(cureItemName)
                .sign("FTSPEST-"+name.toUpperCase())
                .build();
    }
}

