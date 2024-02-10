package de.afgmedia.ftspest.diseases;

import de.afgmedia.ftspest.diseases.infections.Infection;
import de.afgmedia.ftspest.main.FTSPest;

public class Disease {
    private final FTSPest plugin;

    private final String name;

    private final String description;

    private Cure cure;

    private final String infectionMessage;

    private final boolean spreading;

    private final int spreadRadius;

    private final double spreadChance;

    private final String lightSymptoms;

    private final String debuff;

    private final String lethal;

    public Disease(String name, String description, String infectionMessage, boolean spreading, int spreadRadius, double spreadChance, String lightSymptoms, String debuff, String lethal, FTSPest plugin) {
        this.name = name;
        this.description = description;
        this.infectionMessage = infectionMessage;
        this.spreading = spreading;
        this.spreadRadius = spreadRadius;
        this.spreadChance = spreadChance;
        this.lethal = lethal;
        this.debuff = debuff;
        this.lightSymptoms = lightSymptoms;
        this.plugin = plugin;
        plugin.getInfectionManager().getDiseases().put(name, this);
    }

    public void addInfection(Infection infection) {
        if (infection != null) {
            this.plugin.getInfectionManager().addInfection(infection);
            infection.setDisease(this);
        }
    }

    public Cure getCure() {
        return this.cure;
    }

    public void setCure(Cure cure) {
        this.cure = cure;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public String getInfectionMessage() {
        return this.infectionMessage;
    }

    public boolean isSpreading() {
        return this.spreading;
    }

    public int getSpreadRadius() {
        return this.spreadRadius;
    }

    public double getSpreadChance() {
        return this.spreadChance;
    }

    public String getLightSymptoms() {
        return this.lightSymptoms;
    }

    public String getDebuff() {
        return this.debuff;
    }

    public String getLethal() {
        return this.lethal;
    }
}
