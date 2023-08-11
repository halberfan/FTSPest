package de.afgmedia.ftspest.main;

import de.afgmedia.ftspest.cmds.CMDpest;
import de.afgmedia.ftspest.cmds.CMDpestadmin;
import de.afgmedia.ftspest.events.*;
import de.afgmedia.ftspest.misc.InfectionManager;
import de.afgmedia.ftspest.misc.PestIO;
import de.afgmedia.ftspest.misc.PestRunner;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class FTSPest extends JavaPlugin {

    private InfectionManager infectionManager;

    private PestIO pestIO;

    public void onEnable() {

        this.infectionManager = new InfectionManager(this);
        this.pestIO = new PestIO(this);
        loadConfigData();

        new PlayerItemConsumeListener(this);
        new DamageListener(this);
        new CraftListener(this);
        new PlayerListener(this);
        new DeathListener(this);
        new InteractListener(this);

        new CMDpest(this);
        new CMDpestadmin(this);

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new PestRunner(this), 20, 20);

        for (Player onlinePlayer : Bukkit.getOnlinePlayers())
            this.pestIO.loadPlayerData(onlinePlayer);
    }

    public void onDisable() {
        this.pestIO.saveAllPlayerData();
        this.pestIO.saveCauldrons();
        saveConfigData();
    }

    public InfectionManager getInfectionManager() {
        return this.infectionManager;
    }

    public PestIO getPestIO() {
        return this.pestIO;
    }

    private void loadConfigData() {
        if(getConfig().contains("loseImmunities")) {
            InfectionManager.setLoseImmunities(getConfig().getBoolean("loseImmunities"));
        }
    }

    private void saveConfigData() {
        getConfig().set("loseImmunities", InfectionManager.loosingImmunities());
        saveConfig();
    }

}

