package org.gamecie.spleefcie.player;

import org.gamecie.spleefcie.SpleefCie;
import org.gamecie.spleefcie.arena.Arena;

import java.util.UUID;

public class SpleefPlayer {

    private final SpleefCie plugin;
    private Arena currentArena;
    private final UUID uuid;
    private boolean inArena = false;

    public Arena getCurrentArena() {
        return currentArena;
    }

    public UUID getUuid() {
        return uuid;
    }

    public boolean isInArena() {
        return inArena;
    }

    public void toggleInArena() {
        this.inArena = !this.inArena;
    }

    public void setCurrentArena(Arena currentArena) {
        this.currentArena = currentArena;
    }

    public SpleefPlayer(SpleefCie plugin, UUID uuid) {
        this.plugin = plugin;
        this.uuid = uuid;
    }
}
