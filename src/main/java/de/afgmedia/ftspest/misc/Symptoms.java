package de.afgmedia.ftspest.misc;

import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Entity;
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
                allplayer.sendMessage("§7Du bemerkst wie §c" + player.getName() + " stark schwitzt");
        }
        player.sendMessage("§7Du bemerkst wie du stark schwitzt");
    }

    public static void sneeze(Player player) {
        for (Player allplayer : Bukkit.getOnlinePlayers()) {
            if (allplayer == player)
                continue;
            if(!allplayer.getWorld().getName().equalsIgnoreCase(player.getWorld().getName()))
                continue;
            if (allplayer.getLocation().distance(player.getLocation()) < 7.0D) {
                allplayer.playSound(player, Sound.ENTITY_PANDA_SNEEZE, SoundCategory.PLAYERS, 1f, 0.1f);
            }
        }
        player.sendMessage("§7Du niest");
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
                allplayer.sendMessage("§7Du bemerkst wie §c" + player.getName() + " hustet");
        }
        player.sendMessage("§7Du hustest");
        player.getLocation().getWorld().spawnParticle(Particle.ASH, player.getLocation(), 20);
    }

    public static void damage(Player p, int damage) {
        p.damage(damage);
    }

}

