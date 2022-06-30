package de.afgmedia.ftspest.diseases.infections;

import de.afgmedia.ftspest.diseases.Disease;
import de.afgmedia.ftspest.misc.PestUser;
import org.bukkit.entity.Player;

public interface Infection {
    InfectionType getType();

    double getChanceOfInfection();

    Disease getDisease();

    InfectReturnType getsInfected(PestUser paramUser, Object paramObject);

    void setDisease(Disease paramDisease);

    public enum InfectReturnType {
        INFECTED, NOT_INFECTED, ERROR;
    }
}

