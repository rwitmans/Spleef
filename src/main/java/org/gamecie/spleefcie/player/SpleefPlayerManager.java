package org.gamecie.spleefcie.player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.gamecie.spleefcie.SpleefCie;

import java.util.ArrayList;
import java.util.List;

public class SpleefPlayerManager implements Listener {

    private static SpleefCie plugin;
    private List<SpleefPlayer> spleefPlayers = new ArrayList<>();


    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        if (!e.getPlayer().hasPlayedBefore()) {
            spleefPlayers.add(new SpleefPlayer(plugin, e.getPlayer().getUniqueId()));
        }
    }
}
