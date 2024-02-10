package de.afgmedia.ftspest.misc;

import de.afgmedia.ftspest.diseases.Cure;
import de.afgmedia.ftspest.diseases.Disease;
import de.afgmedia.ftspest.diseases.infections.*;
import de.afgmedia.ftspest.main.FTSPest;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

public class PestIO {
    private final FTSPest plugin;

    private final File diseasesFolder;

    public PestIO(FTSPest plugin) {
        this.plugin = plugin;
        this.diseasesFolder = new File(plugin.getDataFolder() + "//diseases//");
        if (!this.diseasesFolder.exists())
            this.diseasesFolder.mkdirs();

        loadDiseases();
    }

    public void loadDiseases() {
        if (this.diseasesFolder.listFiles() == null)
            return;
        for (File file : this.diseasesFolder.listFiles()) {
            YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);
            String name = yamlConfiguration.getString("name");
            String desc = yamlConfiguration.getString("description");
            String infectionMessage = yamlConfiguration.getString("message");
            boolean spreading = yamlConfiguration.getBoolean("spreading");
            int spreadRadius = yamlConfiguration.getInt("spread-radius");
            double spreadChance = yamlConfiguration.getDouble("spread-chance");
            String lightSymptoms = yamlConfiguration.getString("symptoms.light");
            String debuffs = yamlConfiguration.getString("symptoms.debuff");
            String lethal = yamlConfiguration.getString("symptoms.lethal");
            Disease disease = new Disease(name, desc, infectionMessage, spreading, spreadRadius, spreadChance, lightSymptoms, debuffs, lethal, this.plugin);
            for (String infectionsKeys : yamlConfiguration.getConfigurationSection("infections").getKeys(false)) {
                ConsumeInfection consumeInfection;
                DamageInfection damageInfection;
                MobAttackInfection attackInfection;
                BiomeInfection biomeInfection;
                Infection infection = null;
                InfectionType infectionType = InfectionType.valueOf(yamlConfiguration.getString("infections." + infectionsKeys + ".type"));
                double chance = yamlConfiguration.getDouble("infections." + infectionsKeys + ".chance");
                List<String> list = (List<String>) yamlConfiguration.getList("infections." + infectionsKeys + ".items");
                switch (infectionType) {
                    case CONSUME:
                        consumeInfection = new ConsumeInfection(chance);
                        for (String s : list)
                            consumeInfection.addConsumable(Material.valueOf(s));
                        infection = consumeInfection;
                        break;
                    case DAMAGE:
                        damageInfection = new DamageInfection(chance);
                        for (String s : list)
                            damageInfection.addDamageCause(EntityDamageEvent.DamageCause.valueOf(s));
                        infection = damageInfection;
                        break;
                    case MOB_ATTACK:
                        attackInfection = new MobAttackInfection(chance);
                        for (String s : list)
                            attackInfection.addMob(EntityType.valueOf(s));
                        infection = attackInfection;
                        break;
                    case BIOME:
                        biomeInfection = new BiomeInfection(chance);
                        for (String s : list)
                            biomeInfection.addBiome(Biome.valueOf(s));
                        infection = biomeInfection;
                        break;
                }

                disease.addInfection(infection);

            }

            if (spreading) {
                PlayerInfection playerInfection = new PlayerInfection(spreadChance, spreadRadius);
                disease.addInfection(playerInfection);
            }

            String cureName = yamlConfiguration.getString("cure.name");
            String cureDescription = yamlConfiguration.getString("cure.description");
            Cure cure = new Cure(this.plugin, cureName, cureDescription, disease);
            String cureItemName = yamlConfiguration.getString("cure.item.name");
            String cureItemLore = yamlConfiguration.getString("cure.item.lore");
            Material mat = Material.getMaterial(yamlConfiguration.getString("cure.item.material"));
            cure.createItem(cureItemName, cureItemLore, mat);
            cure.createCraftingRecipe(yamlConfiguration.getString("cure.craft"));
        }
    }



    public void savePlayerData(PestUser user) {
        String disease, uuid = user.getPlayer().getUniqueId().toString();
        int sicknessLevel = user.getSicknessLevel();
        if (user.getDisease() == null) {
            disease = "null";
        } else {
            disease = user.getDisease().getName();
        }
        File file = new File(this.plugin.getDataFolder() + "//user//" + uuid + ".yml");
        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);
        yamlConfiguration.set("disease", disease);
        yamlConfiguration.set("sicknesslevel", sicknessLevel);
        yamlConfiguration.set("uuid", uuid);

        for (Disease immunityDisease : user.getImmunity().keySet()) {
            yamlConfiguration.set("immunity." + immunityDisease.getName(), user.getImmunity().get(immunityDisease));
        }

        try {
            yamlConfiguration.save(file);
        } catch (IOException e) {
            plugin.getLogger().log(Level.WARNING, "Error while saving user file");
        }
    }

    public void saveAllPlayerData() {
        for (PestUser value : this.plugin.getInfectionManager().getUsers().values())
            savePlayerData(value);
    }

    public void loadPlayerData(Player player) {
        String uuid = player.getUniqueId().toString();
        File file = new File(this.plugin.getDataFolder() + "//user//" + uuid + ".yml");
        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);
        String diseaseString = yamlConfiguration.getString("disease");
        int sicknessLevel = yamlConfiguration.getInt("sicknesslevel");
        Disease disease = this.plugin.getInfectionManager().getDisease(diseaseString);
        PestUser user = new PestUser(player, disease, sicknessLevel);

        if (yamlConfiguration.contains("immunity")) {
            for (String diseaseImmunity : yamlConfiguration.getConfigurationSection("immunity").getKeys(false)) {
                Disease iDisease = this.plugin.getInfectionManager().getDisease(diseaseImmunity);
                double percentage = yamlConfiguration.getDouble("immunity." + diseaseImmunity);

                if (iDisease != null) {
                    user.addImmunity(iDisease, percentage);
                }

            }
        }


    }
}

