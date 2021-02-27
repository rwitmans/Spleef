package org.gamecie.spleefcie.player;

import org.bukkit.Location;
import org.gamecie.spleefcie.SpleefCie;
import org.gamecie.spleefcie.arena.Arena;

import java.util.UUID;

public class SpleefPlayer {

    private final SpleefCie plugin;
    private Arena currentArena;
    private final UUID uuid;
    private Location location;

    public SpleefPlayer(SpleefCie plugin, UUID uuid) {
        this.plugin = plugin;
        this.uuid = uuid;
    }
}
