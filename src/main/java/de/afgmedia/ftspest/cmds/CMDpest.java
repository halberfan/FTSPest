package de.afgmedia.ftspest.cmds;

import de.afgmedia.ftspest.diseases.Disease;
import de.afgmedia.ftspest.main.FTSPest;
import de.afgmedia.ftspest.misc.PestUser;
import de.afgmedia.ftspest.misc.Values;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CMDpest implements CommandExecutor, TabCompleter {
    private final FTSPest plugin;

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
            cs.sendPlainMessage("Dieser Befehl ist nur für Spieler");
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
                p.sendPlainMessage(Values.PREFIX + "Bitte gebe noch eine Krankheit an von der du Infos haben möchtest.");
                return true;
            }
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("info")) {
                sendDiseaseInfo(p, args[1]);
                return true;
            }
        }

        p.sendPlainMessage(Values.PREFIX + "Unbekannter Befehl! Versuche /pest [List/Info/Immunity]");

        return false;
    }

    private void sendDiseaseInfo(Player p, String diseaseName) {

        Disease disease = plugin.getInfectionManager().getDisease(diseaseName);

        if (disease == null) {
            p.sendMessage(Values.PREFIX + "Diese Krankheit wurde nicht gefunden. Mit /pest list siehst du alle Krankheiten");
            return;
        }

        p.sendMessage(Values.PREFIX + "Informationen zu " + "§e" + disease.getName() + "§7" + ":");
        p.sendMessage(Values.PREFIX + "Beschreibung: " + "§e" + disease.getDescription());
        p.sendMessage(Values.PREFIX + "Medizin: " + "§e" + disease.getCure().getName()
                .replace("ue", "ü")
                .replace("ss", "ß")
                .replace("ae", "ä")
                .replace("oe", "ö")
                .replace("_", " "));
        p.sendMessage(Values.PREFIX + "Symptome: " + "§e" + disease.getDebuff().toLowerCase().replace("_", " "));

    }

    private void sendDiseaseList(Player p) {

        p.sendPlainMessage("§e" + "---------------------------");
        p.sendPlainMessage("§e" + "Es gibt folgende Krankheiten: ");
        p.sendPlainMessage(" ");

        for (Disease disease : plugin.getInfectionManager().getDiseases().values()) {
            p.sendPlainMessage("§e" + disease.getName());
        }

        p.sendPlainMessage(" ");
        p.sendPlainMessage("§e" + "Für weitere Infos zu einer gewissen Krankheit gebe" + TextDecoration.ITALIC + "/pest info [Krankheit]" + "§e" + "in!");
        p.sendPlainMessage("§e" + "---------------------------");

    }

    private void sendFormattedDiseaseOverview(Player p, PestUser user) {
        p.sendPlainMessage("§e" + "---------------------------");
        p.sendPlainMessage("§e" + "Krankheit: " + "§b" + user.getDiseaseName());
        p.sendPlainMessage("§e" + "Fortschritt: " + "§b" + user.getSicknessLevel());
        if (user.isInfected())
            p.sendPlainMessage("§e" + "Medizin: " + "§b" + user.getDisease().getCure().getName()
                    .replace("ue", "ü")
                    .replace("ss", "ß")
                    .replace("ae", "ä")
                    .replace("oe", "ö")
                    .replace("_", " "));
        p.sendPlainMessage("§e" + "---------------------------");
    }

    private void sendFormattedImmunityOverview(Player p, PestUser user) {

        p.sendPlainMessage("§e" + "---------------------------");

        p.sendPlainMessage("§6" + "Deine Immunitäten:");

        for (Disease disease : user.getImmunity().keySet()) {
            int percentage = (int) (user.getImmunity().get(disease) * 100);
            if (percentage == 80) {
                p.sendPlainMessage("§e" + disease.getName() + ": " + "§b" + percentage + "% (MAX)");
                continue;
            }
            p.sendPlainMessage("§e" + disease.getName() + ": " + "§b" + percentage + "%");
        }

        p.sendPlainMessage("§e" + "---------------------------");

    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender cs, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {

        ArrayList<String> result = new ArrayList<>();

        if (args.length == 1) {
            for (String argument : arguments) {
                if (argument.toLowerCase().startsWith(args[0].toLowerCase()))
                    result.add(argument);
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

