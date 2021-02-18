package org.gamecie.spleefcie.arena;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Arena {

    private int id;
    private Location spawn;
    List<Player> players = new ArrayList<>();

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

    public List<Player> getPlayers() {
        return this.players;
    }

    public void startGame() {
        for (Player p : players) {

        }
    }
}
