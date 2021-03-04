package org.gamecie.spleefcie.arena;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.gamecie.spleefcie.SpleefCie;
import org.gamecie.spleefcie.player.SpleefPlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArenaManager {

    // Save where the player is teleported
    private Map<Player, Location> locs = new HashMap<>();
    // Make a new instance of the class
    private static ArenaManager am = new ArenaManager();
    // Inventory of the player
    private Map<Player, ItemStack[]> inv = new HashMap<>();
    // List of arena's
    private List<Arena> arenas = new ArrayList<>();
    private int arenaSize = 0;

    private static SpleefCie plugin;

    public ArenaManager(SpleefCie plugin) {
        this.plugin = plugin;
    }

    private ArenaManager() {

    }

    // We want to get an instance of the manager to work with the manager?????
    public static ArenaManager getManager() {
        return am;
    }

    /**
     * Return the arena with a given ID.
     *
     * @param id The id of the arena you want to get.
     */
    public Arena getArena(int id) {
        for (Arena a : this.arenas) {
            if (a.getId() == id) {
                return a;
            }
        }
        return null;
    }

    /**
     * Add player p to the arena with id i
     *
     * @param p The player to add.
     * @param i The id of the arena the player wants to join.
     */
    public void addPlayer(Player p, int i) {
        Arena a = getArena(i);
        if (a == null) {
            p.sendMessage("Invalid arena");
            return;
        }

        a.getPlayers().add();
        inv.put(p, p.getInventory().getContents());

        p.getInventory().getContents();
        p.getInventory().clear();

        locs.put(p, p.getLocation());
        p.teleport(a.getSpawn());
    }

    /**
     * Remove player p from the arena with id i
     *
     * @param p The player to remove.
     * @param i The id of the arena the player needs to be removed from.
     */
    public void removePlayer(Player p) {
        Arena a = null;
        for (Arena arena : arenas) {
            if (arena.getPlayers().contains(p)) {
                a = arena;
            }
        }
        if (a == null) {
            p.sendMessage("Invalid operation");
            return;
        }

        a.getPlayers().remove(p);

        p.getInventory().clear();

        p.getInventory().setContents(inv.get(p));

        inv.remove(p);
        p.teleport(locs.get(p));
        locs.remove(p);
    }

    public Arena createArena(Location l) {
        int num = arenaSize + 1;
        arenaSize++;

        Arena a = new Arena(l, num);
        arenas.add(a);

        plugin.getConfig().set("Arenas." + num, serializeLoc(l));
        List<Integer> list = plugin.getConfig().getIntegerList("Arena.Arenas");
        list.add(num);
        plugin.getConfig().set("Arena.Arenas", list);
        plugin.saveConfig();

        return a;
    }

    /**
     * Used for readding all the arena's after reloading or restarting the server.
     *
     * @param l The location of the arena.
     * @return The reloaded arena.
     */
    public Arena reloadArena(Location l) {
        int num = arenaSize + 1;
        arenaSize++;

        Arena a = new Arena(l, num);
        arenas.add(a);

        return a;
    }

    public void removeArena(int i ) {
        Arena a = getArena(i);
        if (a == null) {
            return;
        }
        arenas.remove(a);

        plugin.getConfig().set("Arenas." + i, null);
        List<Integer> list = plugin.getConfig().getIntegerList("Arenas.Arenas");
        list.remove(i);
        plugin.getConfig().set("Arenas.Arenas", list);
        plugin.saveConfig();
    }

    public boolean isInGame(Player p) {
        for (Arena a : arenas) {
            if (a.getPlayers().contains(p)) {
                return true;
            }
        }
        return false;
    }

    public void loadGames() {
        arenaSize = 0;

        if (plugin.getConfig().getIntegerList("Arenas.Arenas").isEmpty()) {
            return;
        }

        for (int i : plugin.getConfig().getIntegerList("Arenas.Arenas")) {
            Arena a = reloadArena(deserializeLoc(plugin.getConfig().getString("Arenas." + i)));
            a.setId(i);
        }
    }

    // Used to store the location of the arena in the config
    public String serializeLoc(Location l) {
        return l.getWorld().getName() + "," + l.getBlockX() + "," + l.getBlockY() + "," + l.getBlockZ();
    }

    // Retrieve the location of the arena from the config
    public Location deserializeLoc(String s) {
        String[] st = s.split(",");
        return new Location(Bukkit.getWorld(st[0]), Integer.parseInt(st[1]),
                Integer.parseInt(st[2]), Integer.parseInt(st[3]));
    }
}
