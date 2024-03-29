package de.afgmedia.ftspest.cmds;

import de.afgmedia.ftspest.diseases.Disease;
import de.afgmedia.ftspest.main.FTSPest;
import de.afgmedia.ftspest.misc.InfectionManager;
import de.afgmedia.ftspest.misc.PestUser;
import de.afgmedia.ftspest.misc.Values;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CMDpestadmin implements CommandExecutor {

    private final FTSPest plugin;

    public CMDpestadmin(FTSPest plugin) {
        this.plugin = plugin;
        plugin.getCommand("pestadmin").setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender cs, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!cs.hasPermission("ftspest.admin")) {
            cs.sendPlainMessage(Values.PREFIX + "Dafür hast du keine Rechte!");
            return true;
        }

        if (args.length < 1) {
            cs.sendPlainMessage(help());
            return true;
        }

        if (args[0].equalsIgnoreCase("heal")) {
            if (args.length == 2) {
                Player target = Bukkit.getPlayer(args[1]);
                if (target == null) {
                    cs.sendPlainMessage(Values.PREFIX + "Dieser Spieler ist nicht online!");
                    return true;
                }

                PestUser tUser = plugin.getInfectionManager().getUser(target);

                tUser.cure();
                target.sendPlainMessage(Values.PREFIX + "Du wurdest von einem Teamler geheilt!");
                cs.sendPlainMessage(Values.PREFIX + "Du hast " + target.getName() + " geheilt.");

            }
        } else if (args[0].equalsIgnoreCase("infect")) {
            if (args.length == 3) {
                Player target = Bukkit.getPlayer(args[1]);
                if (target == null) {
                    cs.sendPlainMessage(Values.PREFIX + "Dieser Spieler ist nicht online!");
                    return true;
                }

                Disease targetDisease = plugin.getInfectionManager().getDisease(args[2]);

                if (targetDisease == null) {
                    cs.sendPlainMessage(Values.PREFIX + "Diese Krankheit existiert nicht!");
                    return true;
                }

                PestUser tUser = plugin.getInfectionManager().getUser(target);
                tUser.forceInfect(targetDisease);
                target.sendPlainMessage(Values.PREFIX + "Ein Teamler hat dich angehustet!");
                cs.sendPlainMessage(Values.PREFIX + "Du hast erfolgreich §c" + target.getName() + " §7mit §c" + targetDisease.getName() + " §7infiziert!");

            }
        } else if (args[0].equalsIgnoreCase("toggleimmunities")) {
            cs.sendPlainMessage(Values.PREFIX + "Dass man Immunitäten nach einem Tod verliert ist nun: " + InfectionManager.toggleLosingImmunities());
        }

        return true;
    }

    private String help() {
        return """
                §7/pestadmin heal [Spieler] - Heilt einen Spieler
                §7/pestadmin infect [Spieler] [Krankheit] - Infiziert einen Spieler mit einer Krankheit
                §7/pestadmin toggleimmunities - Schalte um ob man nach dem Tod seine Immunitäten verliert""";
    }
}
