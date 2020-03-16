package org.gamecie.spleefcie;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.gamecie.spleefcie.commands.CreateArenaCMD;

import java.util.HashMap;

public class SpleefCie extends JavaPlugin implements Listener {

    public HashMap<String, ArenaManager> arenaManagerHashMap = new HashMap<>();
    public static SpleefCie plugin;

    @Override
    public void onEnable() {
        getLogger().info("Configuring SpleefCie");
        plugin = this;
        plugin.saveConfig();
        this.getServer().getPluginManager().registerEvents(this, this);
        this.getCommand("arena").setExecutor(new CreateArenaCMD());
    }

}
