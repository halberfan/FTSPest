package de.afgmedia.ftspest.diseases.cmds;

import de.afgmedia.ftspest.diseases.Disease;
import de.afgmedia.ftspest.main.FTSPest;
import de.afgmedia.ftspest.misc.PestUser;
import de.afgmedia.ftspest.misc.Values;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CMDpest implements CommandExecutor, TabCompleter {
    private FTSPest plugin;

    ArrayList<String> arguments;
    ArrayList<String> diseases;

    public CMDpest(FTSPest plugin) {
        this.plugin = plugin;
        plugin.getCommand("pest").setExecutor(this);
        plugin.getCommand("pest").setTabCompleter(this);

        arguments = new ArrayList<>();
        arguments.addAll(Arrays.asList("immunity", "list", "info"));

        diseases = new ArrayList<>();
        diseases.addAll(plugin.getInfectionManager().getDiseases().keySet());
    }

    public boolean onCommand(@NotNull CommandSender cs, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (!(cs instanceof Player))
            cs.sendMessage("Dieser Befehl ist nur für Spieler");
        Player p = (Player) cs;
        if (args.length == 0) {
            sendFormattedDiseaseOverview(p, this.plugin.getInfectionManager().getUser(p));
            return true;
        }
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("immunity")) {
                sendFormattedImmunityOverview(p, this.plugin.getInfectionManager().getUser(p));
                return true;
            } else if (args[0].equalsIgnoreCase("list")) {
                sendDiseaseList(p);
                return true;
            } else if(args[0].equalsIgnoreCase("info")) {
                p.sendMessage(Values.PREFIX + "Bitte gebe noch eine Krankheit an von der du Infos haben möchtest.");
                return true;
            }
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("info")) {
                sendDiseaseInfo(p, args[1]);
                return true;
            }
        }

        p.sendMessage(Values.PREFIX + "Unbekannter Befehl! Versuche /pest [List/Info/Immunity]");

        return false;
    }

    private void sendDiseaseInfo(Player p, String diseaseName) {

        Disease disease = plugin.getInfectionManager().getDisease(diseaseName);

        if (disease == null) {
            p.sendMessage(Values.PREFIX + "Diese Krankheit wurde nicht gefunden. Mit /pest list siehst du alle Krankheiten");
            return;
        }

        p.sendMessage(Values.PREFIX + "Informationen zu §e" + disease.getName() + "§7:");
        p.sendMessage(Values.PREFIX + "Beschreibung: §e" + disease.getDescription());
        p.sendMessage(Values.PREFIX + "Medizin: §e" + disease.getCure().getName()
                .replace("ue", "ü")
                .replace("ss", "ß")
                .replace("ae", "ä")
                .replace("oe", "ö")
                .replace("_", " "));
        p.sendMessage(Values.PREFIX + "Symptome: §e" + disease.getDebuff().toLowerCase().replace("_", " "));

    }

    private void sendDiseaseList(Player p) {

        p.sendMessage("§e----------------------------");
        p.sendMessage("§eEs gibt folgende Krankheiten: ");
        p.sendMessage(" ");

        for (Disease disease : plugin.getInfectionManager().getDiseases().values()) {
            p.sendMessage("§e - " + disease.getName());
        }

        p.sendMessage(" ");
        p.sendMessage("§eFür weitere Infos zu einer gewissen Krankheit gebe §o/pest info [Krankheit] §r§eein!");
        p.sendMessage("§e----------------------------");

    }

    private void sendFormattedDiseaseOverview(Player p, PestUser user) {
        p.sendMessage("§e----------------------------");
        p.sendMessage("§eKrankheit: §b" + user.getDiseaseName());
        p.sendMessage("§eFortschritt: §b" + user.getSicknessLevel());
        if (user.isInfected())
            p.sendMessage("§eMedizin: §b" + user.getDisease().getCure().getName()
                    .replace("ue", "ü")
                    .replace("ss", "ß")
                    .replace("ae", "ä")
                    .replace("oe", "ö")
                    .replace("_", " "));
        p.sendMessage("§e----------------------------");
    }

    private void sendFormattedImmunityOverview(Player p, PestUser user) {

        p.sendMessage("§e----------------------------");

        p.sendMessage("§6Deine Immunitäten:");

        for (Disease disease : user.getImmunity().keySet()) {
            int percentage = (int) (user.getImmunity().get(disease) * 100);
            if (percentage == 80) {
                p.sendMessage("§e" + disease.getName() + ": §b" + percentage + "% (MAX)");
                continue;
            }
            p.sendMessage("§e" + disease.getName() + ": §b" + percentage + "%");
        }

        p.sendMessage("§e----------------------------");

    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender cs, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {

        ArrayList<String> result = new ArrayList<>();

        if (args.length == 1) {
            for (int i = 0; i < arguments.size(); i++) {
                if (arguments.get(i).toLowerCase().startsWith(args[0].toLowerCase()))
                    result.add(arguments.get(i));
            }
            return result;
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("info")) {
                for (String a : diseases) {
                    if (a.toLowerCase().startsWith(args[1].toLowerCase()))
                        result.add(a);
                }
            }
            return result;
        }

        return result;
    }
}

