package de.afgmedia.ftspest.misc;

import de.afgmedia.ftspest.diseases.Cure;
import de.afgmedia.ftspest.diseases.Disease;
import de.afgmedia.ftspest.diseases.infections.Infection;
import de.afgmedia.ftspest.diseases.infections.InfectionType;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InfectionManager {

    private final HashMap<InfectionType, List<Infection>> infections;

    private final HashMap<String, Disease> diseases;

    private final ArrayList<Cure> cures;

    private final HashMap<Player, PestUser> users;

    private static boolean loseImmunities = true;

    public InfectionManager() {
        this.infections = new HashMap<>();
        this.cures = new ArrayList<>();
        this.diseases = new HashMap<>();
        this.users = new HashMap<>();
    }

    public void handleEvent(InfectionType infectionType, Player p, Object obj) {

        double hours = p.getStatistic(Statistic.PLAY_ONE_MINUTE) / 72000d;

        if (hours < 30) {
            return;
        }

        Infection returnInfection = null;
        PestUser user = this.users.get(p);
        if (user == null || user.getDisease() != null)
            return;
        if (this.infections.get(infectionType) == null) {
            return;
        }
        for (Infection infection : this.infections.get(infectionType)) {
            Infection.InfectReturnType returnType = infection.getsInfected(user, obj);
            if (returnType == Infection.InfectReturnType.INFECTED) {
                returnInfection = infection;
                break;
            }
        }
        if (returnInfection != null)
            user.infectWith(returnInfection);
    }

    public void addInfection(Infection infection) {
        if (this.infections.get(infection.getType()) == null) {
            List<Infection> list = new ArrayList<>();
            list.add(infection);
            this.infections.put(infection.getType(), list);
            return;
        }
        this.infections.get(infection.getType()).add(infection);
    }

    public HashMap<Player, PestUser> getUsers() {
        return this.users;
    }

    public ArrayList<Cure> getCures() {
        return this.cures;
    }

    public Disease getDisease(String diseaseString) {
        for (Disease value : diseases.values()) {
            if (value.getName().equalsIgnoreCase(diseaseString))
                return value;
        }
        return null;
    }

    public HashMap<String, Disease> getDiseases() {
        return this.diseases;
    }

    public PestUser getUser(Player p) {
        return this.users.get(p);
    }

    public static boolean toggleLosingImmunities() {
        loseImmunities = !loseImmunities;
        return loseImmunities;
    }

    public static boolean loosingImmunities() {
        return loseImmunities;
    }

    public static void setLoseImmunities(boolean loseImmunities) {
        InfectionManager.loseImmunities = loseImmunities;
    }
}

