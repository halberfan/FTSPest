package de.afgmedia.ftspest.main;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PestPlaceholderExpansion extends PlaceholderExpansion {

    private final FTSPest plugin;

    public PestPlaceholderExpansion(FTSPest plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "pest";
    }

    @Override
    public @Nullable String onRequest(OfflinePlayer player, @NotNull String params) {
        if (player.isOnline()) {
            if (params.equalsIgnoreCase("disease"))
                return plugin.getInfectionManager().getUser(player.getPlayer()).getDiseaseName();
            else if (params.equalsIgnoreCase("level"))
                return String.valueOf(plugin.getInfectionManager().getUser(player.getPlayer()).getSicknessLevel());
        }
        return null;
    }

    @Override
    public @NotNull String getAuthor() {
        return plugin.getPluginMeta().getAuthors().toString();
    }

    @Override
    public @NotNull String getVersion() {
        return plugin.getPluginMeta().getVersion();
    }

}
