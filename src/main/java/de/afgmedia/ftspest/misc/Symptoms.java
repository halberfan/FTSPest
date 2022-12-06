package de.afgmedia.ftspest.misc;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Symptoms {

    public static void sweat(Player player) {
        for (Player allplayer : Bukkit.getOnlinePlayers()) {
            if (allplayer == player)
                continue;
            if(!allplayer.getWorld().getName().equalsIgnoreCase(player.getWorld().getName()))
                continue;
            if (allplayer.getLocation().distance(player.getLocation()) < 7.0D)
                allplayer.sendMessage(ChatColor.GRAY + "Du bemerkst wie " + ChatColor.RED + player.getName() + " stark schwitzt");
        }
        player.sendMessage(ChatColor.GRAY + "Du bemerkst wie du stark schwitzt");
    }

    public static void applyEffect(Player player, PotionEffectType type, int amplifier, int seconds) {
        PotionEffect effect = new PotionEffect(type, seconds * 20, amplifier);
        player.addPotionEffect(effect);
    }

    public static void applyEffect(Player player, PotionEffectType type, int amplifier) {
        applyEffect(player, type, amplifier, 20);
    }

    public static void cough(Player player) {
        for (Player allplayer : Bukkit.getOnlinePlayers()) {
            if (allplayer == player)
                continue;
            if(!allplayer.getWorld().getName().equalsIgnoreCase(player.getWorld().getName()))
                continue;
            if (allplayer.getLocation().distance(player.getLocation()) < 7.0D)
                allplayer.sendMessage(ChatColor.GRAY + "Du bemerkst wie " + ChatColor.RED + player.getName() + " hustet");
        }
        player.sendMessage(ChatColor.GRAY + "Du hustest");
        player.getLocation().getWorld().spawnParticle(Particle.ASH, player.getLocation(), 20);
    }

    public static void damage(Player p, int damage) {
        p.damage(damage);
    }

}

