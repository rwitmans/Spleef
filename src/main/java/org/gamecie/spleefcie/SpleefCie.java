package org.gamecie.spleefcie;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.eyedevelop.prometheus.abstracts.command.Command;
import org.gamecie.spleefcie.arena.ArenaManager;
import org.gamecie.spleefcie.commands.CommandSpleefCie;
import org.gamecie.spleefcie.commands.SubCreate;
import org.gamecie.spleefcie.commands.SubJoin;

import java.util.HashMap;

public class SpleefCie extends JavaPlugin {

    public static SpleefCie plugin;

    public SpleefCie() {
        plugin = this;
    }
    public void onLoad() {
        super.onLoad();
    }

    public void onEnable() {
        super.onEnable();

        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }

        if (getConfig() == null) {
            saveDefaultConfig();
        }

        new ArenaManager(this);
        ArenaManager.getManager().loadGames();

        getServer().getPluginManager().registerEvents(new GameListener(this), this);

        Command commandSpleefCie = new CommandSpleefCie("spleef", "spleef.use");

        new SubCreate("create", commandSpleefCie, "spleef.admin");
        new SubJoin("join", commandSpleefCie, "spleef.use");
        Bukkit.getPluginCommand("spleef").setExecutor(commandSpleefCie);

        // Register Listeners.
        Bukkit.getPluginManager().registerEvents(new GameListener(this), this);

        getLogger().info("[SPLEEFCIE] LOADED AND ACTIVATED");

    }

    public void onDisable() {
        super.onDisable();

        saveConfig();
    }
}
