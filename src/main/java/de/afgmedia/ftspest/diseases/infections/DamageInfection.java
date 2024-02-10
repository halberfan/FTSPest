package de.afgmedia.ftspest.diseases.infections;

import de.afgmedia.ftspest.diseases.Disease;

import java.util.ArrayList;

import de.afgmedia.ftspest.misc.PestUser;
import org.bukkit.event.entity.EntityDamageEvent;

public class DamageInfection implements Infection {
    private final ArrayList<EntityDamageEvent.DamageCause> damageCauses;
    private final double chance;
    private Disease disease;

    public DamageInfection(double chance) {
        this.chance = chance;
        this.damageCauses = new ArrayList<>();
    }

    public InfectionType getType() {
        return InfectionType.DAMAGE;
    }

    public double getChanceOfInfection() {
        return this.chance;
    }

    public Infection.InfectReturnType getsInfected(PestUser u, Object obj) {
        if (obj instanceof EntityDamageEvent e) {
            if (this.damageCauses.contains(e.getCause())) {
                if(e.getCause().equals(EntityDamageEvent.DamageCause.FALL)) {
                    if(e.getDamage() < 4)
                        return InfectReturnType.NOT_INFECTED;
                }
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

    public void addDamageCause(EntityDamageEvent.DamageCause cause) {
        this.damageCauses.add(cause);
    }

    public Disease getDisease() {
        return this.disease;
    }

    public void setDisease(Disease disease) {
        this.disease = disease;
    }

}
