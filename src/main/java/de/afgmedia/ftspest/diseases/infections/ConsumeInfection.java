package de.afgmedia.ftspest.diseases.infections;

import de.afgmedia.ftspest.diseases.Disease;

import java.util.ArrayList;

import de.afgmedia.ftspest.misc.PestUser;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerItemConsumeEvent;

public class ConsumeInfection implements Infection {
    private ArrayList<Material> consumables;

    private Disease disease;

    private double chance;

    public ConsumeInfection(double chance) {
        this.chance = chance;
        this.consumables = new ArrayList<>();
    }

    public void addConsumable(Material consumable) {
        this.consumables.add(consumable);
    }

    public InfectionType getType() {
        return InfectionType.CONSUME;
    }

    public double getChanceOfInfection() {
        return this.chance;
    }

    public Infection.InfectReturnType getsInfected(PestUser u, Object obj) {
        if (obj instanceof PlayerItemConsumeEvent) {
            PlayerItemConsumeEvent e = (PlayerItemConsumeEvent) obj;
            if (this.consumables.contains(e.getItem().getType())) {
                double rdm = Math.random();
                double chanceWithImmunity = chance * (1 - u.getImmunity().get(disease));
                if (rdm <= chanceWithImmunity)
                    return Infection.InfectReturnType.INFECTED;
                return Infection.InfectReturnType.NOT_INFECTED;
            }
            return Infection.InfectReturnType.NOT_INFECTED;
        }
        return Infection.InfectReturnType.ERROR;
    }

    public Disease getDisease() {
        return this.disease;
    }

    public void setDisease(Disease disease) {
        this.disease = disease;
    }

}