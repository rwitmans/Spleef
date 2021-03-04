package org.gamecie.spleefcie.player;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.gamecie.spleefcie.SpleefCie;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

public class SpleefPlayerManager {

    private static SpleefCie plugin;
    private HashMap<OfflinePlayer, SpleefPlayer> spleefPlayerHashMap = new HashMap<>();

    public SpleefPlayerManager(SpleefCie plugin) {
        this.plugin = plugin;
        this.plugin.getLogger().log(Level.INFO, "Loading data of all players...");
        for (OfflinePlayer offlinePlayer : Bukkit.getOfflinePlayers()) {
            registerPlayer(offlinePlayer);
        }
        plugin.getLogger().log(Level.INFO, "Succesfully logged all players.");
    }

    public SpleefPlayer registerPlayer(OfflinePlayer p){
        if(spleefPlayerHashMap.containsKey(p)){
            return spleefPlayerHashMap.get(p);
        }
        SpleefPlayer spleefPlayer = new SpleefPlayer(plugin, p.getUniqueId());
        spleefPlayerHashMap.put(p, spleefPlayer);
        return spleefPlayer;
    }

    public SpleefPlayer getSpleefPlayer(OfflinePlayer p){
        if(spleefPlayerHashMap.containsKey(p)){
            return spleefPlayerHashMap.get(p);
        }
        return registerPlayer(p);
    }

    public HashMap<OfflinePlayer, SpleefPlayer> getSpleefPlayerHashMap() {
        return spleefPlayerHashMap;
    }


}
