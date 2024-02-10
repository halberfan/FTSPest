package de.afgmedia.ftspest.misc;

import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Symptoms {

    public static void applyEffect(Player player, PotionEffectType type, int amplifier, int seconds) {
        PotionEffect effect = new PotionEffect(type, seconds * 20, amplifier);
        player.addPotionEffect(effect);
    }

    public static void applyEffect(Player player, PotionEffectType type, int amplifier) {
        applyEffect(player, type, amplifier, 20);
    }

    public static void sweat(Player player) {
        symptomMessage(player, "§7" + "Du bemerkst wie " + "§4" + player.getName() + " stark schwitzt", "§7" + "Du bemerkst wie du stark schwitzt");
    }

    public static void cough(Player player) {
        symptomMessage(player, "§7" + "Du bemerkst wie " + "§4" + player.getName() + " hustet", "§7" + "Du hustest");
        player.getLocation().getWorld().spawnParticle(Particle.ASH, player.getLocation(), 20);
    }

    public static void sneeze(Player player) {
        symptomMessage(player, "§7" + "Du bemerkst wie " + "§4" + player.getName() + " niest", "§7" + "Du niest");
    }

    private static void symptomMessage(Player player, String nearbyPlayers, String playerMessage) {
        for (Player allplayer : Bukkit.getOnlinePlayers()) {
            if (allplayer == player) continue;
            if (!allplayer.getWorld().getName().equalsIgnoreCase(player.getWorld().getName())) continue;
            if (allplayer.getLocation().distance(player.getLocation()) < 7.0D)
                allplayer.sendPlainMessage(nearbyPlayers);
        }
        player.sendPlainMessage(playerMessage);
    }

    public static void damage(Player p, int damage) {
        p.damage(damage);
    }

}

