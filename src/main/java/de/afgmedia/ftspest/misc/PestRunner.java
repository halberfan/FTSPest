package de.afgmedia.ftspest.misc;

import de.afgmedia.ftspest.diseases.infections.InfectionType;
import de.afgmedia.ftspest.main.FTSPest;

import java.util.Iterator;
import java.util.Random;

public class PestRunner implements Runnable {
    private FTSPest plugin;

    private static final int SECONDS_TILL_SICKNESS_LEVEL_RISE = 90;

    private static final int SECONDS_TILL_POSITION_CHECK = 180;

    private static final int SECONDS_TILL_SYMPTOMS_APPLY = 10;

    private int sicknessLevel;

    private int positionCheck;

    private int symptomApply;

    public PestRunner(FTSPest plugin) {

        this.plugin = plugin;
        this.sicknessLevel = 0;
        this.positionCheck = 0;
        this.symptomApply = 0;

    }

    public void run() {
        this.sicknessLevel++;
        this.positionCheck++;
        this.symptomApply++;
        if (this.sicknessLevel >= SECONDS_TILL_SICKNESS_LEVEL_RISE || this.positionCheck >= SECONDS_TILL_POSITION_CHECK || this.symptomApply >= SECONDS_TILL_SYMPTOMS_APPLY) {
            Iterator<PestUser> pestUserIterator = this.plugin.getInfectionManager().getUsers().values().iterator();
            while (pestUserIterator.hasNext()) {
                PestUser user = pestUserIterator.next();
                if (this.sicknessLevel >= SECONDS_TILL_SICKNESS_LEVEL_RISE) {
                    user.addSicknessLevel();
                }
                if (this.positionCheck >= SECONDS_TILL_POSITION_CHECK) {
                    if (Math.random() < 0.5) {
                        this.plugin.getInfectionManager().handleEvent(InfectionType.BIOME, user.getPlayer(), user.getPlayer().getLocation());
                    } else {
                        this.plugin.getInfectionManager().handleEvent(InfectionType.PLAYER, user.getPlayer(), user.getPlayer().getLocation());
                    }
                }
                if (this.symptomApply >= SECONDS_TILL_SYMPTOMS_APPLY) {
                    user.applySymptoms();
                }
            }
        }

        if (this.sicknessLevel >= SECONDS_TILL_SICKNESS_LEVEL_RISE) {
            sicknessLevel = 0;
        }
        if (this.positionCheck >= SECONDS_TILL_POSITION_CHECK) {
            positionCheck = 0;
        }
        if (this.symptomApply >= SECONDS_TILL_SYMPTOMS_APPLY) {
            symptomApply = 0;
        }

    }
}