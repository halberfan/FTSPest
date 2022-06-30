package de.afgmedia.ftspest.diseases;

import de.afgmedia.ftspest.diseases.infections.Infection;
import de.afgmedia.ftspest.main.FTSPest;

import java.util.ArrayList;
import java.util.List;

public class Disease {
    private FTSPest plugin;

    private List<Infection> infections;

    private String name;

    private String description;

    private Cure cure;

    private String infectionMessage;

    private boolean spreading;

    private int spreadRadius;

    private double spreadChance;

    private String lightSymptoms;

    private String debuff;

    private String lethal;

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
        this.infections = new ArrayList<Infection>();
        plugin.getInfectionManager().getDiseases().put(name, this);
    }

    public void addInfection(Infection infection) {
        if (infection != null) {
            this.infections.add(infection);
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
