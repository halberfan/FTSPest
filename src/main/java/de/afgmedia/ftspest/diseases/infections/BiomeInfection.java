package de.afgmedia.ftspest.diseases.infections;

import de.afgmedia.ftspest.diseases.Disease;

import java.util.ArrayList;

import de.afgmedia.ftspest.misc.PestUser;
import org.bukkit.Location;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;

public class BiomeInfection implements Infection {

    private ArrayList<Biome> biomes;

    private Disease disease;

    private double chance;

    public BiomeInfection(double chance) {
        this.chance = chance;
        this.biomes = new ArrayList<Biome>();
    }

    public InfectionType getType() {
        return InfectionType.BIOME;
    }

    public double getChanceOfInfection() {
        return this.chance;
    }

    public Infection.InfectReturnType getsInfected(PestUser u, Object obj) {
        if (obj instanceof Location) {
            Location loc = (Location) obj;
            if (this.biomes.contains(loc.getBlock().getBiome())) {
                double rdm = Math.random();
                double chanceWithImmunity = chance * (1 - u.getImmunity().get(disease));
                if (rdm <= chanceWithImmunity)
                    return Infection.InfectReturnType.INFECTED;
                return Infection.InfectReturnType.NOT_INFECTED;
            }
            return Infection.InfectReturnType.NOT_INFECTED;
        }
        return Infection.InfectReturnType.ERROR;
    }

    public Disease getDisease() {
        return this.disease;
    }

    public void addBiome(Biome biome) {
        this.biomes.add(biome);
    }

    public void setDisease(Disease disease) {
        this.disease = disease;
    }


}