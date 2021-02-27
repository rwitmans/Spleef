package org.gamecie.spleefcie.arena;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;
import org.gamecie.spleefcie.player.SpleefPlayer;

import java.util.ArrayList;
import java.util.List;

public class Arena extends BukkitRunnable implements Listener {

    private int id;
    private String name;
    private Location spawn;
    private boolean isOngoing;

    private List<SpleefPlayer> playersInGame = new ArrayList<>();
    List<SpleefPlayer> spectators = new ArrayList<>();
    private List<Block> blocks = new ArrayList<>();


    public Arena(Location loc, int id) {
        this.spawn = loc;
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Location getSpawn() {
        return this.spawn;
    }

    public List<SpleefPlayer> getPlayers() {
        return this.playersInGame;
    }

    public void startGame() {
        for (SpleefPlayer sp : playersInGame) {

        }
    }

    @Override
    public void run() {

    }
}
