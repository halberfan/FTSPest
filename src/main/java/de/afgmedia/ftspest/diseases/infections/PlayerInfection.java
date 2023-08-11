package de.afgmedia.ftspest.diseases.infections;

import de.afgmedia.ftspest.diseases.Disease;
import de.afgmedia.ftspest.misc.PestUser;

public class PlayerInfection implements Infection {

    private double chance;
    private Disease disease;

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
    public InfectReturnType getsInfected(PestUser paramUser, Object paramObject) {
        return null;
    }

    @Override
    public void setDisease(Disease paramDisease) {

    }
}
