package de.afgmedia.ftspest.misc;

import de.afgmedia.ftspest.diseases.Cure;
import de.afgmedia.ftspest.diseases.Disease;
import de.afgmedia.ftspest.diseases.infections.Infection;
import de.afgmedia.ftspest.diseases.infections.InfectionType;
import de.afgmedia.ftspest.main.FTSPest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Statistic;
import org.bukkit.entity.Player;

public class InfectionManager {
    private FTSPest plugin;

    private HashMap<InfectionType, List<Infection>> infections;

    private HashMap<String, Disease> diseases;

    private ArrayList<Cure> cures;

    private HashMap<Player, PestUser> users;

    public InfectionManager(FTSPest plugin) {
        this.plugin = plugin;
        this.infections = new HashMap<InfectionType, List<Infection>>();
        this.cures = new ArrayList<Cure>();
        this.diseases = new HashMap<String, Disease>();
        this.users = new HashMap<Player, PestUser>();
    }

    public void handleEvent(InfectionType infectionType, Player p, Object obj) {

        double ticks = p.getStatistic(Statistic.PLAY_ONE_MINUTE);
        double seconds = ticks / 20;
        double minutes = seconds / 60;
        double hours = minutes / 60;
        
        if(hours < 50) {
            return;
        }

        Disease disease = null;
        PestUser user = this.users.get(p);
        if (user == null || user.getDisease() != null)
            return;
        if (this.infections.get(infectionType) == null) {
            return;
        }
        for (Infection infection : this.infections.get(infectionType)) {
            Infection.InfectReturnType returnType = infection.getsInfected(user, obj);
            if (returnType == Infection.InfectReturnType.INFECTED) {
                disease = infection.getDisease();
                break;
            }
        }
        if (disease != null)
            user.infectWith(disease);
    }

    public void addInfection(Infection infection) {
        if (this.infections.get(infection.getType()) == null) {
            List<Infection> list = new ArrayList<>();
            list.add(infection);
            this.infections.put(infection.getType(), list);
            return;
        }
        ((List<Infection>) this.infections.get(infection.getType())).add(infection);
    }

    public HashMap<Player, PestUser> getUsers() {
        return this.users;
    }

    public ArrayList<Cure> getCures() {
        return this.cures;
    }

    public Disease getDisease(String diseaseString) {
        for (Disease value : diseases.values()) {
            if(value.getName().equalsIgnoreCase(diseaseString))
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
}

