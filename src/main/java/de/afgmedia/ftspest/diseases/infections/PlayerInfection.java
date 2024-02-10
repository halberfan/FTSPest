package de.afgmedia.ftspest.diseases.infections;

import de.afgmedia.ftspest.diseases.Disease;
import de.afgmedia.ftspest.main.FTSPest;
import de.afgmedia.ftspest.misc.PestUser;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class PlayerInfection implements Infection {

    private final double chance;
    private Disease disease;
    private final int radius;

    public PlayerInfection(double chance, int radius) {
        this.chance = chance;
        this.radius = radius;
    }

    @Override
    public InfectionType getType() {
        return InfectionType.PLAYER;
    }

    @Override
    public double getChanceOfInfection() {
        return chance;
    }

    @Override
    public Disease getDisease() {
        return disease;
    }

    @Override
    public InfectReturnType getsInfected(PestUser user, Object obj) {
        if (!(obj instanceof Location loc)) {
            throw new IllegalArgumentException("Argument not a location");
        }

        for (Entity nearbyEntity : loc.getNearbyEntities(radius, radius, radius)) {
            if (nearbyEntity instanceof Player target) {
                PestUser targetUser = FTSPest.getInstance().getInfectionManager().getUser(target);
                if (targetUser.getDisease() == disease) {
                    if (Math.random() <= chance * (1 - user.getImmunity().get(disease))) {
                        return InfectReturnType.INFECTED;
                    }
                }
            }
        }

        return InfectReturnType.NOT_INFECTED;
    }

    @Override
    public void setDisease(Disease disease) {
        this.disease = disease;
    }
}
