package de.afgmedia.ftspest.main;

import de.afgmedia.ftspest.diseases.cmds.CMDpest;
import de.afgmedia.ftspest.diseases.cmds.CMDpestadmin;
import de.afgmedia.ftspest.events.*;
import de.afgmedia.ftspest.misc.InfectionManager;
import de.afgmedia.ftspest.misc.PestIO;
import de.afgmedia.ftspest.misc.PestRunner;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class FTSPest extends JavaPlugin {

    private InfectionManager infectionManager;

    private PestIO pestIO;

    public void onEnable() {

        this.infectionManager = new InfectionManager(this);
        this.pestIO = new PestIO(this);

        new PlayerItemConsumeListener(this);
        new DamageListener(this);
        new CraftListener(this);
        new PlayerListener(this);
        new DeathListener(this);
        new InteractListener(this);

        new CMDpest(this);
        new CMDpestadmin(this);

        Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin) this, (Runnable) new PestRunner(this), 20L, 20L);

        for (Player onlinePlayer : Bukkit.getOnlinePlayers())
            this.pestIO.loadPlayerData(onlinePlayer);

    }

    public void onDisable() {
        this.pestIO.saveAllPlayerData();
    }

    public InfectionManager getInfectionManager() {
        return this.infectionManager;
    }

    public PestIO getPestIO() {
        return this.pestIO;
    }

}

