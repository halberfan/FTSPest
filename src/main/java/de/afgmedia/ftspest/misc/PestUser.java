package de.afgmedia.ftspest.misc;

import de.afgmedia.ftspest.diseases.Disease;
import de.afgmedia.ftspest.diseases.infections.Infection;
import de.afgmedia.ftspest.diseases.infections.InfectionType;
import de.afgmedia.ftspest.diseases.infections.PlayerInfection;
import de.afgmedia.ftspest.main.FTSPest;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;

public class PestUser {

    private final Player player;

    private Disease disease;

    private int sicknessLevel;

    private final HashMap<Disease, Double> immunity;

    public PestUser(Player player, Disease disease, int sicknessLevel) {

        this.player = player;
        this.disease = disease;
        this.sicknessLevel = sicknessLevel;
        this.immunity = new HashMap<>();

        FTSPest.getInstance().getInfectionManager().getUsers().put(player, this);

        for (Disease value : FTSPest.getInstance().getInfectionManager().getDiseases().values()) {
            immunity.put(value, 0d);
        }

    }

    public void infectWith(Infection infection) {
        if (this.disease == null) {
            Disease disease = infection.getDisease();
            this.disease = disease;
            this.sicknessLevel = 0;
            if (infection.getType() == InfectionType.PLAYER) {
                this.player.sendMessage(Values.PREFIX + "Du wurdest mit §c" + disease.getName() + " §7angesteckt.");
            } else {
                this.player.sendMessage(Values.PREFIX + disease.getInfectionMessage());
            }
        }
    }

    public void forceInfect(Disease disease) {
        this.disease = disease;
        this.sicknessLevel = 0;
        this.player.sendMessage(Values.PREFIX + disease.getInfectionMessage());
    }

    public void addSicknessLevel() {
        if (this.disease != null) {
            if (this.sicknessLevel != 100)
                this.sicknessLevel++;
        } else {
            this.sicknessLevel = 0;
        }
    }

    public Player getPlayer() {
        return this.player;
    }

    public void applySymptoms() {
        if (this.sicknessLevel >= 20 && this.sicknessLevel <= 50) {
            if (Math.random() >= 0.95f)
                if (this.disease.getLightSymptoms().equalsIgnoreCase("sweat")) {
                    Symptoms.sweat(this.player);
                } else if (this.disease.getLightSymptoms().equalsIgnoreCase("cough")) {
                    Symptoms.cough(this.player);
                } else if (this.disease.getLightSymptoms().equalsIgnoreCase("sneeze")) {
                    Symptoms.sneeze(this.player);
                }
        } else if (this.sicknessLevel >= 51 && this.sicknessLevel <= 90) {
            if (this.sicknessLevel > 75) {
                Symptoms.applyEffect(this.player, PotionEffectType.getByName(this.disease.getDebuff()), 1);
            } else {
                Symptoms.applyEffect(this.player, PotionEffectType.getByName(this.disease.getDebuff()), 0);
            }
        } else if (this.sicknessLevel > 90 && Math.random() >= 0.2f && this.player.getHealth() > 3) {
            Symptoms.damage(this.player, Integer.parseInt(this.disease.getLethal()));
        }
    }

    public Disease getDisease() {
        return this.disease;
    }

    public void cure() {

        double immunity = getImmunity().get(disease);
        double immunityPercentage = 0;
        if (sicknessLevel < 20) {
            immunityPercentage = immunity + 0.03;
        } else if (sicknessLevel < 50) {
            immunityPercentage = immunity + 0.05;
        } else if (sicknessLevel < 75) {
            immunityPercentage = immunity + 0.06;
        } else if (sicknessLevel < 90) {
            immunityPercentage = immunity + 0.08;
        } else if (sicknessLevel < 101) {
            immunityPercentage = immunity + 0.1;
        }

        addImmunity(disease, immunityPercentage);

        this.disease = null;
        this.sicknessLevel = 0;
    }

    public void cure(boolean override) {

        if (override) {
            this.disease = null;
            this.sicknessLevel = 0;
        } else {
            cure();
        }

    }

    public int getSicknessLevel() {
        return this.sicknessLevel;
    }

    public boolean isInfected() {
        return (this.disease != null);
    }

    public String getDiseaseName() {
        if (isInfected())
            return this.disease.getName();
        return "Keine";
    }

    public void addImmunity(Disease disease, double immunityPercentage) {

        if (immunityPercentage > 0.8)
            immunityPercentage = 0.8;

        this.immunity.put(disease, immunityPercentage);
    }

    public HashMap<Disease, Double> getImmunity() {
        return immunity;
    }

    public void resetImmunity() {

        for (Disease value : FTSPest.getInstance().getInfectionManager().getDiseases().values()) {
            immunity.put(value, 0d);
        }

    }

    public void reduceSicknessLevel() {
        if (sicknessLevel >= 90) {
            sicknessLevel = 60;
        }
    }
}

