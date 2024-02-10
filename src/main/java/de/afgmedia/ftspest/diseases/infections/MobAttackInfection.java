package de.afgmedia.ftspest.diseases.infections;

import de.afgmedia.ftspest.diseases.Disease;

import java.util.ArrayList;

import de.afgmedia.ftspest.misc.PestUser;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class MobAttackInfection implements Infection {
    private double chance;

    private Disease disease;

    private ArrayList<EntityType> mobs;

    public MobAttackInfection(double chance) {
        this.chance = chance;
        this.mobs = new ArrayList<>();
    }

    public InfectionType getType() {
        return InfectionType.MOB_ATTACK;
    }

    public double getChanceOfInfection() {
        return this.chance;
    }

    public Infection.InfectReturnType getsInfected(PestUser u, Object obj) {
        if (obj instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent e = (EntityDamageByEntityEvent) obj;
            if (this.mobs.contains(e.getDamager().getType())) {
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

    public void addMob(EntityType mobType) {
        this.mobs.add(mobType);
    }

    public Disease getDisease() {
        return this.disease;
    }

    public void setDisease(Disease disease) {
        this.disease = disease;
    }
}
